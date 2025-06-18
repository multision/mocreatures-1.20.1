package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 * Handles registration that should happen after the registries are built
 */
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCRegistration {

    /**
     * Common setup event that happens after entity registration is complete
     */
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerSpawnPlacements();
            
            // Add additional post-registration setup here
            MoCreatures.LOGGER.info("Mo'Creatures common setup complete");
        });
    }
    
    /**
     * Register spawn placements for entities
     * This is called AFTER entities are registered, so it's safe to use .get()
     */
    private static void registerSpawnPlacements() {
        // Register spawn placement rules for entities
        SpawnPlacements.register(
            MoCEntities.BIRD.get(), 
            SpawnPlacements.Type.NO_RESTRICTIONS, 
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 
            MoCEntityAnimal::checkAnimalSpawnRules
        );
        
        SpawnPlacements.register(
            MoCEntities.BLACK_BEAR.get(), 
            SpawnPlacements.Type.ON_GROUND, 
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 
            MoCEntityAnimal::checkAnimalSpawnRules
        );
        
        // Add more spawn placement registrations here
        
        MoCreatures.LOGGER.info("Registered spawn placements for Mo' Creatures entities");
    }
    
    /**
     * Register creative tabs
     */
    @SubscribeEvent
    public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            // Add spawn eggs to creative tab when they're registered
            MoCSpawnEggs.SPAWN_EGGS.getEntries().forEach(egg -> {
                event.accept(egg.get());
            });
        }
    }
    
    /**
     * Register client-specific setup
     */
    public static void clientSetup() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            // Client-specific setup that needs to happen after registration
            MoCreatures.LOGGER.info("Mo'Creatures client setup complete");
        }
    }
} 