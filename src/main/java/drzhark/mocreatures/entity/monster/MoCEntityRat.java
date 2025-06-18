/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.entity.MoCEntityMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

// Just here for makeshift MIA compatibility
public class MoCEntityRat extends MoCEntityMob {
    public MoCEntityRat(EntityType<? extends MoCEntityRat> type, Level world) {
        super(type, world);
    }
}
