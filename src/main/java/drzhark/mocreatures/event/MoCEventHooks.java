/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.event;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.entity.tameable.MoCPetMapData;
import drzhark.mocreatures.MoCTools;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class MoCEventHooks {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onWorldUnload(LevelEvent.Unload event) {
        // if overworld has been deleted or unloaded, reset our flag
        if (event.getLevel() instanceof Level && ((Level)event.getLevel()).dimension() == Level.OVERWORLD) {
            MoCreatures.proxy.worldInitDone = false;
        }
    }

    @SubscribeEvent
    public void onWorldLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel)) return;

        ServerLevel serverLevel = (ServerLevel) event.getLevel();
        if (!serverLevel.dimension().equals(Level.OVERWORLD)) return;

        if (!MoCreatures.proxy.worldInitDone) {
            MoCPetMapData data = serverLevel.getDataStorage().computeIfAbsent(
                    MoCPetMapData::load, 
                    () -> new MoCPetMapData(MoCConstants.MOD_ID), 
                    MoCConstants.MOD_ID);
            MoCreatures.instance.mapData = data;
            MoCreatures.proxy.worldInitDone = true;
        }
    }

    @SubscribeEvent
    public void onLivingSpawnEvent(MobSpawnEvent.FinalizeSpawn event) {
        LivingEntity entity = event.getEntity();
        Class<? extends LivingEntity> entityClass = entity.getClass();
        MoCEntityData data = MoCreatures.entityMap.get(entityClass);
        if (data == null) return; // not a MoC entity
        
        Level level = entity.level();
        List<ResourceKey<Level>> dimensionIDs = Arrays.asList(data.getDimensions());
        
        // Check if we're in the Wyvern dimension
        if (MoCTools.isInWyvernLair(entity)) {
            // If this is not an allowed mob for the Wyvern dimension, cancel spawning
            if (!MoCTools.canSpawnInWyvernLair(entity)) {
                event.setSpawnCancelled(true);
                return;
            }
            // Explicitly allow Wyvern Lair mobs
            event.setResult(Event.Result.ALLOW);
            return;
        }
        
        if (!dimensionIDs.contains(level.dimension())) {
            event.setSpawnCancelled(true);
        } else if (data.getFrequency() <= 0) {
            event.setSpawnCancelled(true);
        } else if (dimensionIDs.contains(MoCreatures.proxy.wyvernDimension) && level.dimension().equals(MoCreatures.proxy.wyvernDimension)) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            if (IMoCTameable.class.isAssignableFrom(event.getEntity().getClass())) {
                IMoCTameable mocEntity = (IMoCTameable) event.getEntity();
                if (mocEntity.getIsTamed() && mocEntity.getPetHealth() > 0 && !mocEntity.isRiderDisconnecting()) {
                    return;
                }

                if (mocEntity.getOwnerPetId() != -1) // required since getInt will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(mocEntity, mocEntity.getOwnerPetId());
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (player.getVehicle() instanceof IMoCTameable) {
            IMoCTameable mocEntity = (IMoCTameable) player.getVehicle();
            mocEntity.setRiderDisconnecting(true);
        }
    }

    private BlockPos getSafeSpawnPos(LivingEntity entity, BlockPos near) {
        int radius = 6;
        int maxTries = 24;
        BlockPos testing;
        for (int i = 0; i < maxTries; i++) {
            int x = near.getX() + entity.level().getRandom().nextInt(radius * 2) - radius;
            int z = near.getZ() + entity.level().getRandom().nextInt(radius * 2) - radius;
            int y = entity.level().getHeight(Heightmap.Types.MOTION_BLOCKING, x, z) + 16;
            testing = new BlockPos(x, y, z);
            while (entity.level().isEmptyBlock(testing) && testing.getY() > 0) {
                testing = testing.below();
            }
            BlockState blockstate = entity.level().getBlockState(testing);
            if (blockstate.isValidSpawn(entity.level(), testing, EntityType.PIG)) {
                return testing.above();
            }
        }
        return null;
    }
}
