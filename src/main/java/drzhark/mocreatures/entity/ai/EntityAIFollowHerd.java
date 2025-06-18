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

public class EntityAIFollowHerd extends Goal {

    /**
     * The child that is following its parent.
     */
    Mob theAnimal;
    Mob herdAnimal;
    double moveSpeed;
    double maxRange;
    double minRange;
    private int delayCounter;
    private int executionChance;

    public EntityAIFollowHerd(Mob animal, double speed, double minRangeIn, double maxRangeIn, int chance) {
        this.theAnimal = animal;
        this.moveSpeed = speed;
        this.minRange = minRangeIn; //4D;
        this.maxRange = maxRangeIn; //20D;
        this.executionChance = chance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public EntityAIFollowHerd(Mob animal, double speed) {
        this(animal, speed, 4D, 20D, 120);
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {

        if (this.theAnimal.getRandom().nextInt(this.executionChance) != 0) {
            return false;
        }

        // Calculate the search box
        AABB searchBox = this.theAnimal.getBoundingBox().inflate(this.maxRange, 4.0D, this.maxRange);
        
        // Use raw type to avoid generic issues
        List<?> list = this.theAnimal.level().getEntitiesOfClass(this.theAnimal.getClass(), searchBox);
        Mob entityliving = null;
        double d0 = Double.MAX_VALUE;

        for (Object entity : list) {
            if (entity instanceof Mob) {
                Mob entityliving1 = (Mob) entity;
                double d1 = this.theAnimal.distanceToSqr(entityliving1);
                if (d1 >= this.minRange && this.theAnimal != entityliving1) {
                    d0 = d1;
                    entityliving = entityliving1;
                }
            }
        }

        if (entityliving == null) {
            //System.out.println("didn't find any herd");
            return false;

        } else if (d0 < this.maxRange) {
            //System.out.println("herd is too close: " + d0);

            return false;
        } else {
            this.herdAnimal = entityliving;
            //System.out.println("found herd " + entityliving);
            return true;
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        if (this.theAnimal instanceof IMoCEntity && ((IMoCEntity) this.theAnimal).isMovementCeased()) { //System.out.println("returning, movement ceased");
            return false;
        } else if (!this.herdAnimal.isAlive()) {
            return false;
        } else {
            double d0 = this.theAnimal.distanceToSqr(this.herdAnimal);
            return d0 >= this.minRange && d0 <= this.maxRange;
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
        this.herdAnimal = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 20;
            //System.out.println("moving " + this + " to " + this.herdAnimal);
            this.theAnimal.getNavigation().moveTo(this.herdAnimal, this.moveSpeed);
        }
    }

    /**
     * Changes task random possibility for execution
     */
    public void setExecutionChance(int newchance) {
        this.executionChance = newchance;
    }
}
