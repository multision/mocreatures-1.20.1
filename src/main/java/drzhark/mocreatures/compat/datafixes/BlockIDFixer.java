/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat.datafixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import drzhark.mocreatures.MoCConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("removal")
public class BlockIDFixer extends DataFix {
    private static final Map<ResourceLocation, ResourceLocation> BLOCK_NAME_MAPPINGS = new HashMap<>();

    static {
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(MoCConstants.MOD_ID, "MoCStone"), new ResourceLocation(MoCConstants.MOD_ID, "wyvstone"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(MoCConstants.MOD_ID, "MoCGrass"), new ResourceLocation(MoCConstants.MOD_ID, "wyvgrass"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(MoCConstants.MOD_ID, "MoCDirt"), new ResourceLocation(MoCConstants.MOD_ID, "wyvdirt"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(MoCConstants.MOD_ID, "MoCLeaves"), new ResourceLocation(MoCConstants.MOD_ID, "wyvwood_leaves"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(MoCConstants.MOD_ID, "MoCLog"), new ResourceLocation(MoCConstants.MOD_ID, "wyvwood_log"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(MoCConstants.MOD_ID, "MoCTallGrass"), new ResourceLocation(MoCConstants.MOD_ID, "tall_wyvgrass"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(MoCConstants.MOD_ID, "MoCWoodPlank"), new ResourceLocation(MoCConstants.MOD_ID, "wyvwood_planks"));
    }

    public BlockIDFixer(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return null;
    }

    @SubscribeEvent
    public void onMissingBlockMappings(MissingMappingsEvent event) {
        event.getMappings(ForgeRegistries.BLOCKS.getRegistryKey(), MoCConstants.MOD_ID).forEach(mapping -> {
            ResourceLocation oldName = mapping.getKey();
            ResourceLocation newName = BLOCK_NAME_MAPPINGS.get(oldName);
            if (newName != null) {
                Block newBlock = ForgeRegistries.BLOCKS.getValue(newName);
                if (newBlock != null) {
                    mapping.remap(newBlock);
                }
            }
        });
    }

    @SubscribeEvent
    public void onMissingItemMappings(MissingMappingsEvent event) {
        event.getMappings(ForgeRegistries.ITEMS.getRegistryKey(), MoCConstants.MOD_ID).forEach(mapping -> {
            ResourceLocation oldName = mapping.getKey();
            ResourceLocation newName = BLOCK_NAME_MAPPINGS.get(oldName);
            if (newName != null) {
                Item newItem = ForgeRegistries.ITEMS.getValue(newName);
                if (newItem != null) {
                    mapping.remap(newItem);
                }
            }
        });
    }
}
