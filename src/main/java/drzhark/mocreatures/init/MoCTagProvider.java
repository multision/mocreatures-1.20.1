/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class MoCTagProvider extends BlockTagsProvider {

    public MoCTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MoCConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Add MoC ore blocks to their specific forge ore tags
        this.tag(Tags.Blocks.ORES_DIAMOND)
            .add(MoCBlocks.wyvernDiamondOre.get());
        this.tag(Tags.Blocks.ORES_EMERALD)
            .add(MoCBlocks.wyvernEmeraldOre.get());
        this.tag(Tags.Blocks.ORES_GOLD)
            .add(MoCBlocks.wyvernGoldOre.get());
        this.tag(Tags.Blocks.ORES_IRON)
            .add(MoCBlocks.wyvernIronOre.get());
        this.tag(Tags.Blocks.ORES_LAPIS)
            .add(MoCBlocks.wyvernLapisOre.get());

        // Add ancient ore to the general ores tag since it's a custom ore type
        this.tag(Tags.Blocks.ORES)
            .add(MoCBlocks.ancientOre.get());

        // Add MoC leaves to the minecraft:leaves tag
        this.tag(BlockTags.LEAVES)
            .add(MoCBlocks.wyvwoodLeaves.get());
    }
}