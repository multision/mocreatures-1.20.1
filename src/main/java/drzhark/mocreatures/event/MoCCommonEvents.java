package drzhark.mocreatures.event;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.config.biome.BiomeSpawnConfig;
import drzhark.mocreatures.world.MoCSpawnRegistryCache;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCCommonEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BiomeSpawnConfig.init(); // Ensure all config loaded BEFORE any biome modifiers
            MoCSpawnRegistryCache.prepare(); // Build entity cache
        });
    }
}
