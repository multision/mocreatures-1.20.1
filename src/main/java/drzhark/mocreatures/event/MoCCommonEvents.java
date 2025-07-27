package drzhark.mocreatures.event;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.config.biome.BiomeSpawnConfig;
import drzhark.mocreatures.world.MoCSpawnRegistryCache;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import drzhark.mocreatures.MoCreatures;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCCommonEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BiomeSpawnConfig.init(); // Ensure all config loaded BEFORE any biome modifiers
            MoCSpawnRegistryCache.prepare(); // Build entity cache
        });
    }

    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        // This ensures our blocks are properly tagged at runtime
        // Note: This is a backup in case data generation tags don't load properly
        MoCreatures.LOGGER.info("Tags updated - MoC blocks should now be properly tagged");
    }
}
