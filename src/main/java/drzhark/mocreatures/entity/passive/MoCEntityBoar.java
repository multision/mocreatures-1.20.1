/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

// Just here for makeshift MIA compatibility
public class MoCEntityBoar extends MoCEntityAnimal {
    public MoCEntityBoar(EntityType<? extends MoCEntityBoar> type, Level world) {
        super(type, world);
    }
}
