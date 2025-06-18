/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class MoCBlockLog extends RotatedPillarBlock {

    public MoCBlockLog(BlockBehaviour.Properties properties) {
        super(properties
                .mapColor(MapColor.WOOD)
                .strength(2.0F)
                .sound(SoundType.WOOD));
    }
}
