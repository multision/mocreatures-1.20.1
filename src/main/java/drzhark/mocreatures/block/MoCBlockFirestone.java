/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MoCBlockFirestone extends Block {

    public MoCBlockFirestone(BlockBehaviour.Properties properties) {
        super(properties
                .strength(0.3F)
                .sound(SoundType.GLASS));
    }
}
