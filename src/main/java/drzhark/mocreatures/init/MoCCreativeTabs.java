package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.item.MoCItemEgg;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoCConstants.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOC_TAB = CREATIVE_MODE_TABS.register("mocreatures_tab", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.mocreatures_tab"))
                    .icon(() -> MoCItems.AMULET_FAIRY_FULL.get() != null ? 
                           new ItemStack(MoCItems.AMULET_FAIRY_FULL.get()) : 
                           ItemStack.EMPTY)
                    .displayItems((params, output) -> {
                        // Add block items first
                        for (RegistryObject<Block> blockEntry : MoCBlocks.BLOCKS.getEntries()) {
                            try {
                                if (blockEntry.isPresent()) {
                                    // Find the corresponding item for this block
                                    String blockPath = blockEntry.getId().getPath();
                                    MoCBlocks.ITEMS.getEntries().stream()
                                        .filter(itemObj -> itemObj.getId().getPath().equals(blockPath))
                                        .findFirst()
                                        .ifPresent(itemObj -> output.accept(itemObj.get()));
                                }
                            } catch (Exception e) {
                                // Skip this entry if there's an error
                            }
                        }
                        
                        // Add standalone items (not block items)
                        for (RegistryObject<Item> itemEntry : MoCItems.ITEMS.getEntries()) {
                            try {
                                if (itemEntry.isPresent()) {
                                    Item item = itemEntry.get();
                                    
                                    // Special handling for the egg item to add all variants
                                    if (item instanceof MoCItemEgg) {
                                        MoCItemEgg eggItem = (MoCItemEgg) item;
                                        eggItem.fillItemCategory(output);
                                    } else {
                                        output.accept(item);
                                    }
                                }
                            } catch (Exception e) {
                                // Skip this entry if there's an error
                            }
                        }
                    })
                    .build()
    );

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
