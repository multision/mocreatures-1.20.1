/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.EnumSet;

public class EntityAIFollowAdult extends Goal {

    /**
     * The child that is following its parent.
     */
    Mob childAnimal;
    Mob parentAnimal;
    double moveSpeed;
    private int delayCounter;

    public EntityAIFollowAdult(Mob animal, double speed) {
        this.childAnimal = animal;
        this.moveSpeed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {
        if (((IMoCEntity) this.childAnimal).getIsSitting()) {
            return false;
        }
        if ((!(this.childAnimal instanceof IMoCEntity)) || ((IMoCEntity) this.childAnimal).getIsAdult()) {
            return false;
        } else {
            // Get the bounding box to search
            AABB searchBox = this.childAnimal.getBoundingBox().inflate(8.0D, 4.0D, 8.0D);
            // Using raw type to avoid generic type mismatch issues
            List<?> list = this.childAnimal.level().getEntitiesOfClass(this.childAnimal.getClass(), searchBox);
            Mob entityliving = null;
            double d0 = Double.MAX_VALUE;

            for (Object entity : list) {
                if (entity instanceof Mob && entity instanceof IMoCEntity) {
                    Mob entityliving1 = (Mob) entity;
                    if (((IMoCEntity) entityliving1).getIsAdult()) {
                        double d1 = this.childAnimal.distanceToSqr(entityliving1);

                        if (d1 <= d0) {
                            d0 = d1;
                            entityliving = entityliving1;
                        }
                    }
                }
            }

            if (entityliving == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else {
                this.parentAnimal = entityliving;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        if (((IMoCEntity) this.childAnimal).getIsSitting()) {
            return false;
        }
        if (((IMoCEntity) this.childAnimal).getIsAdult()) {
            return false;
        } else if (!this.parentAnimal.isAlive()) {
            return false;
        } else {
            double d0 = this.childAnimal.distanceToSqr(this.parentAnimal);
            return d0 >= 9.0D && d0 <= 256.0D;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.delayCounter = 0;
    }

    /**
     * Resets the task
     */
    @Override
    public void stop() {
        this.parentAnimal = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            this.childAnimal.getNavigation().moveTo(this.parentAnimal, this.moveSpeed);
        }
    }
}
