package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.block.*;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoCConstants.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoCConstants.MOD_ID);

    public static final RegistryObject<Block> ancientSilverBlock = register("ancient_silver_block", () ->
            new MoCBlockMetal(BlockBehaviour.Properties.of().strength(3.0F, 10.0F).mapColor(MapColor.METAL)));

    public static final RegistryObject<Block> cobbledWyvstone = register("cobbled_wyvstone", () ->
            new MoCBlockRock(BlockBehaviour.Properties.of().strength(2.0F, 10.0F).mapColor(MapColor.STONE)));

    public static final RegistryObject<Block> cobbledDeepWyvstone = register("cobbled_deep_wyvstone", () ->
            new MoCBlockRock(BlockBehaviour.Properties.of().strength(3.5F, 10.0F).mapColor(MapColor.STONE)));

    public static final RegistryObject<Block> wyvstone = register("wyvstone", () ->
            new MoCBlockRock(BlockBehaviour.Properties.of().strength(1.5F, 10.0F).mapColor(MapColor.STONE)));

    public static final RegistryObject<Block> deepWyvstone = register("deep_wyvstone", () ->
            new MoCBlockRock(BlockBehaviour.Properties.of().strength(3.0F, 10.0F).mapColor(MapColor.STONE)));

    public static final RegistryObject<Block> mossyCobbledWyvstone = register("mossy_cobbled_wyvstone", () ->
            new MoCBlockRock(BlockBehaviour.Properties.of().strength(1.5F, 10.0F).mapColor(MapColor.STONE)));

    public static final RegistryObject<Block> mossyCobbledDeepWyvstone = register("mossy_cobbled_deep_wyvstone", () ->
            new MoCBlockRock(BlockBehaviour.Properties.of().strength(1.5F, 10.0F).mapColor(MapColor.STONE)));

    public static final RegistryObject<Block> gleamingGlass = register("gleaming_glass", () ->
            new MoCBlockGlass(BlockBehaviour.Properties.of().strength(0.4F).mapColor(MapColor.COLOR_LIGHT_GRAY).noOcclusion()));

    public static final RegistryObject<Block> silverSand = register("silver_sand", () ->
            new MoCBlockSand(BlockBehaviour.Properties.of().strength(0.6F).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final RegistryObject<Block> silverSandstone = register("silver_sandstone", () ->
            new MoCBlockRock(BlockBehaviour.Properties.of().strength(1.2F).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final RegistryObject<Block> carvedSilverSandstone = register("carved_silver_sandstone", () ->
            new MoCBlockRock(BlockBehaviour.Properties.of().strength(1.2F).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final RegistryObject<Block> smoothSilverSandstone = register("smooth_silver_sandstone", () ->
            new MoCBlockRock(BlockBehaviour.Properties.of().strength(1.2F).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final RegistryObject<Block> ancientOre = register("ancient_ore", () ->
            new MoCBlockOre(BlockBehaviour.Properties.of().strength(3.0F, 5.0F).mapColor(MapColor.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> firestone = register("firestone", () ->
            new MoCBlockFirestone(BlockBehaviour.Properties.of().strength(3.0F).lightLevel(state -> 7).mapColor(MapColor.COLOR_ORANGE)));

    public static final RegistryObject<Block> wyvernDiamondOre = register("wyvern_diamond_ore", () ->
            new MoCBlockOre(BlockBehaviour.Properties.of().strength(3.0F, 5.0F).mapColor(MapColor.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> wyvernEmeraldOre = register("wyvern_emerald_ore", () ->
            new MoCBlockOre(BlockBehaviour.Properties.of().strength(3.0F, 5.0F).mapColor(MapColor.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> wyvernGoldOre = register("wyvern_gold_ore", () ->
            new MoCBlockOre(BlockBehaviour.Properties.of().strength(3.0F, 5.0F).mapColor(MapColor.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> wyvernIronOre = register("wyvern_iron_ore", () ->
            new MoCBlockOre(BlockBehaviour.Properties.of().strength(3.0F, 5.0F).mapColor(MapColor.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> wyvernLapisOre = register("wyvern_lapis_ore", () ->
            new MoCBlockOre(BlockBehaviour.Properties.of().strength(1.5F, 5.0F).mapColor(MapColor.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> wyvgrass = register("wyvgrass", () ->
            new MoCBlockGrass(BlockBehaviour.Properties.of().strength(0.7F).mapColor(MapColor.COLOR_CYAN)));

    public static final RegistryObject<Block> wyvdirt = register("wyvdirt", () ->
            new MoCBlockDirt(BlockBehaviour.Properties.of().strength(0.6F).mapColor(MapColor.DIRT)));

    public static final RegistryObject<Block> wyvwoodLeaves = register("wyvwood_leaves", () ->
            new MoCBlockLeaf(BlockBehaviour.Properties.of().strength(0.2F).mapColor(MapColor.COLOR_LIGHT_BLUE).noOcclusion()));

    public static final RegistryObject<Block> wyvwoodSapling = register("wyvwood_sapling", () ->
            new MoCBlockSapling(BlockBehaviour.Properties.of().noCollission().randomTicks().strength(0.0F).mapColor(MapColor.PLANT)));

    public static final RegistryObject<Block> wyvwoodLog = register("wyvwood_log", () ->
            new MoCBlockLog(BlockBehaviour.Properties.of().strength(2.0F).mapColor(MapColor.COLOR_CYAN)));

    public static final RegistryObject<Block> tallWyvgrass = register("tall_wyvgrass", () ->
            new MoCBlockTallGrass(BlockBehaviour.Properties.of().noCollission().strength(0.0F).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final RegistryObject<Block> wyvwoodPlanks = register("wyvwood_planks", () ->
            new MoCBlockPlanks(BlockBehaviour.Properties.of().strength(2.0F, 5.0F).mapColor(MapColor.COLOR_BLUE)));

    public static final RegistryObject<Block> wyvernNestBlock = register("wyvern_nest_block", () ->
            new MoCBlockNest(BlockBehaviour.Properties.of().strength(0.5F).mapColor(MapColor.COLOR_YELLOW)));


    private static RegistryObject<Block> register(String name, Supplier<Block> blockSupplier) {
        RegistryObject<Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> {
            return new BlockItem(block.get(), new Item.Properties());
        });
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEvents {
        @SubscribeEvent
        public static void registerRenderLayers(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(wyvwoodLeaves.get(), RenderType.cutoutMipped());
                ItemBlockRenderTypes.setRenderLayer(wyvwoodSapling.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(tallWyvgrass.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(gleamingGlass.get(), RenderType.translucent());
            });
        }
    }
}
