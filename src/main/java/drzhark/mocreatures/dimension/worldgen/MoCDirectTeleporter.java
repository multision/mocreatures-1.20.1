package drzhark.mocreatures.dimension.worldgen;

import drzhark.mocreatures.registry.MoCPOI;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public class MoCDirectTeleporter implements net.minecraftforge.common.util.ITeleporter {
    private final BlockPos pos;
    private final boolean generateStructure;

    public MoCDirectTeleporter(BlockPos pos, boolean generateStructure) {
        this.pos = pos;
        this.generateStructure = generateStructure;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        Entity newEntity = repositionEntity.apply(false);

        // Generate the portal structure first if needed
        if (generateStructure) {
            BlockState block = destWorld.getBlockState(pos);
            if (!block.is(Blocks.QUARTZ_BLOCK)) {
                MoCWorldGenPortal portalGen = new MoCWorldGenPortal(
                        Blocks.QUARTZ_PILLAR.defaultBlockState(),
                        Blocks.QUARTZ_STAIRS.defaultBlockState(),
                        Blocks.QUARTZ_BLOCK.defaultBlockState(),
                        Blocks.QUARTZ_BLOCK.defaultBlockState()
                );
                portalGen.generate(destWorld, destWorld.getRandom(), pos);
                
                // Register the portal as a POI
                registerPortalPOI(destWorld, pos);
                
                // Ensure there's a safe landing platform
                ensureSafeLanding(destWorld, pos);
            }
        }

        // Now teleport the entity to the prepared position
        destWorld.getServer().tell(new net.minecraft.server.TickTask(0, () -> {
            if (newEntity instanceof ServerPlayer player) {
                player.connection.teleport(
                        pos.getX() + 0.5,
                        pos.getY() + 1,
                        pos.getZ() + 0.5,
                        yaw,
                        player.getXRot()
                );
                player.fallDistance = 0.0F;
                player.setDeltaMovement(Vec3.ZERO);
                System.out.println("[MoC Portal] Teleported player to " + pos);
            } else {
                newEntity.teleportTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                newEntity.setYRot(yaw);
                newEntity.setDeltaMovement(Vec3.ZERO);
                newEntity.fallDistance = 0.0F;
            }
        }));

        return newEntity;
    }
    
    /**
     * Ensures there's a safe landing platform at the destination
     */
    private void ensureSafeLanding(ServerLevel world, BlockPos pos) {
        // Check if we need to create a platform
        boolean needsPlatform = false;
        
        // Check if there's solid ground below
        for (int y = 1; y <= 5; y++) {
            BlockPos checkPos = pos.below(y);
            if (!world.getBlockState(checkPos).isAir()) {
                // Found solid ground within 5 blocks, no need for platform
                return;
            }
        }
        
        // If we get here, we need a platform
        System.out.println("[MoC Portal] Creating safety platform at " + pos);
        
        // Create a 5x5 platform under the portal
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                BlockPos platformPos = pos.offset(dx, -1, dz);
                world.setBlock(platformPos, Blocks.QUARTZ_BLOCK.defaultBlockState(), 3);
            }
        }
    }
    
    /**
     * Registers the portal as a Point of Interest for future teleportation
     */
    private void registerPortalPOI(ServerLevel world, BlockPos pos) {
        PoiManager poiManager = world.getPoiManager();
        poiManager.ensureLoadedAndValid(world, pos, 8);
        
        // Force the POI to be registered at this position
        poiManager.add(pos, MoCPOI.WYVERN_PORTAL.getHolder().get());
        
        // Log the POI registration
        System.out.println("[MoC Portal] Registered wyvern portal POI at " + pos);
    }
} 