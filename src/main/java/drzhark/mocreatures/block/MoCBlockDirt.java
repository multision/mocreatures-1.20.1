/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MoCBlockDirt extends Block {

    public MoCBlockDirt(BlockBehaviour.Properties properties) {
        super(properties
                .strength(0.5F)
                .sound(SoundType.GRAVEL)); // Closest modern equivalent to dirt sound
    }
}
