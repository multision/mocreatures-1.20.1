/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MoCBlockRock extends Block {

    public MoCBlockRock(BlockBehaviour.Properties properties) {
        super(properties
                .strength(1.5F, 6.0F)
                .sound(SoundType.STONE));
    }
}
