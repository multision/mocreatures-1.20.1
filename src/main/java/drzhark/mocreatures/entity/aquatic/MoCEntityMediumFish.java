/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCEntityMediumFish extends MoCEntityTameableAquatic {

    public static final String[] fishNames = {"Salmon", "Cod", "Bass"};

    public MoCEntityMediumFish(EntityType<? extends MoCEntityMediumFish> type, Level world) {
        super(type, world);
        //setSize(0.7f, 0.45f);
        // TODO: Make hitboxes adjust depending on size
        //setAge(30 + this.random.nextInt(70));
        setMoCAge(100);
    }

    public static MoCEntityMediumFish createEntity(Level world, int type) {
        if (type == 1) {
            return (MoCEntityMediumFish) MoCEntities.SALMON.get().create(world);
        }
        if (type == 2) {
            return (MoCEntityMediumFish) MoCEntities.COD.get().create(world);
        }
        if (type == 3) {
            return (MoCEntityMediumFish) MoCEntities.BASS.get().create(world);
        }
        return (MoCEntityMediumFish) MoCEntities.SALMON.get().create(world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new EntityAIFleeFromEntityMoC(this, entity -> (entity.getBbHeight() > 0.6F && entity.getBbWidth() > 0.3F), 2.0F, 0.6D, 1.5D));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 1.0D, 50));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return MoCEntityTameableAquatic.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 7.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.random.nextInt(fishNames.length) + 1);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide()) {
            if (getIsTamed() && this.random.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
        }
        if (!this.isEyeInFluid(FluidTags.WATER)) {
            this.yBodyRot = this.getYRot();
            this.setXRot(this.getXRot());
        }
    }

    @Override
    public float getSizeFactor() {
        return getMoCAge() * 0.0081F;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isEyeInFluid(FluidTags.WATER)) {
            return 1F;
        }
        return 0.5F;
    }

    @Override
    protected boolean isFisheable() {
        return !getIsTamed();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float yawRotationOffset() {
        if (!this.isEyeInFluid(FluidTags.WATER)) {
            return 90F;
        }
        return 90F + super.yawRotationOffset();
    }

    @Override
    public float rollRotationOffset() {
        if (!isInWater() && this.onGround()) {
            return -90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        return -30;
    }

    @Override
    public float getAdjustedZOffset() {
        if (!isInWater()) {
            return 0.2F;
        }
        return 0F;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getSpeed() {
        return 0.15F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 0.5D;
    }

    @Override
    protected double maxDivingDepth() {
        return 4.0D;
    }

    @Override
    public int getMaxAge() {
        return 120;
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.getBbHeight() * 0.775F;
    }
}
