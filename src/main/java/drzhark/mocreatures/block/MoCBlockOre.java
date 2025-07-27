/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MoCBlockOre extends Block {

    public MoCBlockOre(BlockBehaviour.Properties properties) {
        super(properties
                .strength(3.0F, 3.0F)
                .sound(SoundType.STONE));
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
        return silkTouchLevel == 0 ? getExperience(randomSource) : 0;
    }

    private int getExperience(RandomSource rand) {
        if (this == MoCBlocks.ancientOre.get()) {
            return Mth.nextInt(rand, 2, 5);
        } else if (this == MoCBlocks.wyvernDiamondOre.get()) {
            return Mth.nextInt(rand, 4, 8);
        } else if (this == MoCBlocks.wyvernEmeraldOre.get()) {
            return Mth.nextInt(rand, 4, 8);
        } else if (this == MoCBlocks.wyvernLapisOre.get()) {
            return Mth.nextInt(rand, 3, 6);
        } else {
            return 0;
        }
    }
}
