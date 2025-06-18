/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MoCBlockPlanks extends Block {

    public MoCBlockPlanks(BlockBehaviour.Properties properties) {
        super(properties
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD));
    }
}
