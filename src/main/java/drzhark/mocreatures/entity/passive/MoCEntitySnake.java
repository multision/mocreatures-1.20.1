/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

// Just here for makeshift MIA compatibility
public class MoCEntitySnake extends MoCEntityTameableAnimal {
    public MoCEntitySnake(EntityType<? extends MoCEntitySnake> type, Level world) {
        super(type, world);
    }
}
