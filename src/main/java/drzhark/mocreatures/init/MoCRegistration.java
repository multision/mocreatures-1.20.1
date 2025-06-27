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