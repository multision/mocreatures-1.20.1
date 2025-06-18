/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public class MoCEntityRay extends MoCEntityTameableAquatic {

    public MoCEntityRay(EntityType<? extends MoCEntityRay> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    public boolean isPoisoning() {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        final InteractionResult tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        if (!this.isVehicle() && getTypeMoC() == 1) {
            if (!this.level().isClientSide() && player.startRiding(this)) {
                player.setYRot(this.getYRot());
                player.setXRot(this.getXRot());
                player.setPos(player.getX(), this.getY(), player.getZ());
            }

            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInWater()) {
            return 0.09F;
        }
        return 0.15F;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    public boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.15D * getSizeFactor();
    }

    @Override
    public float getSizeFactor() {
        float f = getMoCAge() * 0.01F;
        if (f > 1.5F) {
            f = 1.5F;
        }
        return f;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getSpeed() {
        return 0.06F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 3D;
    }

    @Override
    protected double maxDivingDepth() {
        return 6.0D;
    }

    @Override
    public int getMaxAge() {
        return 90;
    }

    public boolean isMantaRay() {
        return false;
    }
}
