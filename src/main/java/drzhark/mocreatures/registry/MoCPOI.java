package drzhark.mocreatures.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

import drzhark.mocreatures.MoCConstants;

public class MoCPOI {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, MoCConstants.MOD_ID);

    // Wyvern Portal POI used for teleportation targeting
    public static final ResourceKey<PoiType> WYVERN_PORTAL_KEY = ResourceKey.create(
            Registries.POINT_OF_INTEREST_TYPE,
            new ResourceLocation(MoCConstants.MOD_ID, "wyvern_portal"));
    
    public static final RegistryObject<PoiType> WYVERN_PORTAL = POI_TYPES.register("wyvern_portal", 
            () -> new PoiType(
                    Set.of(Blocks.QUARTZ_BLOCK.defaultBlockState()), // Quartz block at center of portal
                    1,  // Only 1 entity can use this POI at once
                    1   // Can detect this POI from 1 block away
            ));

    // Initialize the registry
    public static void init() {
        POI_TYPES.register(net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus());
    }
} 