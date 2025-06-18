/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class EntityAIFleeFromPlayer extends Goal {

    private final PathfinderMob mob;
    protected double speed;
    protected double distance;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIFleeFromPlayer(PathfinderMob creature, double speedIn, double distanceToCheck) {
        this.mob = creature;
        this.distance = distanceToCheck;
        this.speed = speedIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    @Override
    public boolean canUse() {

        if (this.mob instanceof IMoCEntity) {
            if (((IMoCEntity) this.mob).isNotScared()) {
                return false;
            }
        }

        if (!this.IsNearPlayer(this.distance)) {
            return false;
        } else {
            Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 5, 4, Vec3.atBottomCenterOf(this.mob.blockPosition()));

            if (vec3 == null) {
                return false;
            } else {
                this.randPosX = vec3.x;
                this.randPosY = vec3.y;
                this.randPosZ = vec3.z;
                return true;
            }
        }
    }

    protected boolean IsNearPlayer(double d) {
        Player player = this.mob.level().getNearestPlayer(this.mob, d);
        return player != null;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }
}
