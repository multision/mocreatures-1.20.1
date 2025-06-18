/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

import java.util.EnumSet;
import java.util.List;

public class EntityAIMateMoC extends Goal {
    private final MoCEntityTameableAnimal animal;
    private final Class<? extends MoCEntityTameableAnimal> mateClass;
    Level level;
    /**
     * Delay preventing a baby from spawning immediately when two mate-able animals find each other.
     */
    int spawnBabyDelay;
    /**
     * The speed the creature moves at during mating behavior.
     */
    double moveSpeed;
    private MoCEntityTameableAnimal targetMate;

    public EntityAIMateMoC(MoCEntityTameableAnimal animal, double speedIn) {
        this(animal, speedIn, animal.getClass());
    }

    public EntityAIMateMoC(MoCEntityTameableAnimal p_i47306_1_, double p_i47306_2_, Class<? extends MoCEntityTameableAnimal> p_i47306_4_) {
        this.animal = p_i47306_1_;
        this.level = p_i47306_1_.level();
        this.mateClass = p_i47306_4_;
        this.moveSpeed = p_i47306_2_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Returns whether the Goal should begin execution.
     */
    public boolean canUse() {
        if (!this.animal.isInLove()) {
            return false;
        } else {
            this.targetMate = this.getNearbyMate();
            return this.targetMate != null;
        }
    }

    /**
     * Returns whether an in-progress Goal should continue executing
     */
    public boolean canContinueToUse() {
        return this.targetMate.isAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.animal.getLookControl().setLookAt(this.targetMate, 10.0F, (float) this.animal.getMaxHeadXRot());
        this.animal.getNavigation().moveTo(this.targetMate, this.moveSpeed);
        ++this.spawnBabyDelay;

        if (this.spawnBabyDelay >= 60 && this.animal.distanceToSqr(this.targetMate) < 9.0D) {
            this.spawnBaby();
        }
    }

    /**
     * Loops through nearby animals and finds another animal of the same type that can be mated with. Returns the first
     * valid mate found.
     */
    private MoCEntityTameableAnimal getNearbyMate() {
        List<? extends MoCEntityTameableAnimal> list = this.level.getEntitiesOfClass(mateClass, this.animal.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        MoCEntityTameableAnimal entityanimal = null;

        for (MoCEntityTameableAnimal entityanimal1 : list) {
            // Custom canMateWith check for MoCEntityTameableAnimal
            if (this.animal.getClass() == entityanimal1.getClass() && this.animal.distanceToSqr(entityanimal1) < d0) {
                entityanimal = entityanimal1;
                d0 = this.animal.distanceToSqr(entityanimal1);
            }
        }

        return entityanimal;
    }

    /**
     * Spawns a baby animal of the same type.
     */
    private void spawnBaby() {
        AgeableMob entityageable = this.animal.getBreedOffspring((ServerLevel)this.level, this.targetMate);

        if (entityageable != null) {
            ServerPlayer entityplayermp = this.animal.getLoveCause();

            if (entityplayermp == null && this.targetMate.getLoveCause() != null) {
                entityplayermp = this.targetMate.getLoveCause();
            }

            if (entityplayermp != null) {
                entityplayermp.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this.animal, this.targetMate, entityageable);
            }

            // Exclude Males from the reset.
            if (this.animal.getTypeMoC() != 1) {
                this.animal.setMoCAge(6000);
                this.animal.resetLove();
            }

            // Exclude Males from the reset.
            if (this.targetMate.getTypeMoC() != 1) {
                this.targetMate.setMoCAge(6000);
                this.targetMate.resetLove();
            }

            entityageable.setAge(-24000);
            entityageable.moveTo(this.animal.getX(), this.animal.getY(), this.animal.getZ(), 0.0F, 0.0F);
            if (entityageable instanceof MoCEntityTurkey) {
                // Randomly select sex of spawn.
                ((MoCEntityTurkey) entityageable).selectType();
            }

            this.level.addFreshEntity(entityageable);
            RandomSource random = this.animal.getRandom();

            for (int i = 0; i < 7; ++i) {
                double d0 = random.nextGaussian() * 0.02D;
                double d1 = random.nextGaussian() * 0.02D;
                double d2 = random.nextGaussian() * 0.02D;
                double d3 = random.nextDouble() * (double) this.animal.getBbWidth() * 2.0D - (double) this.animal.getBbWidth();
                double d4 = 0.5D + random.nextDouble() * (double) this.animal.getBbHeight();
                double d5 = random.nextDouble() * (double) this.animal.getBbWidth() * 2.0D - (double) this.animal.getBbWidth();
                this.level.addParticle(ParticleTypes.HEART, this.animal.getX() + d3, this.animal.getY() + d4, this.animal.getZ() + d5, d0, d1, d2);
            }

            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }
        }
    }
}