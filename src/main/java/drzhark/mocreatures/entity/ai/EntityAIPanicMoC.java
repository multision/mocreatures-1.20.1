/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class EntityAIPanicMoC extends PanicGoal {

    private final PathfinderMob mob;

    public EntityAIPanicMoC(PathfinderMob creature, double speedIn) {
        super(creature, speedIn);
        this.mob = creature;
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (this.mob instanceof IMoCEntity && ((IMoCEntity) this.mob).isNotScared()) {
            return false;
        }
        return super.canUse();
    }
}
