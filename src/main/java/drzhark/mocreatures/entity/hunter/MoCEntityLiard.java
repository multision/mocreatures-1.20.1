/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class MoCEntityLiard extends MoCEntityBigCat {

    public MoCEntityLiard(EntityType<? extends MoCEntityLiard> type, Level world) {
        super(type, world);
        //setSize(1.175F, 1.065F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return MoCEntityBigCat.createAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 6.5D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(1);
        }
        super.selectType();
    }

    @Override
    public ResourceLocation getTexture() {
        if (MoCreatures.proxy.legacyBigCatModels) return MoCreatures.proxy.getModelTexture("big_cat_liard_legacy.png");
        return MoCreatures.proxy.getModelTexture("big_cat_liard.png");
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        final InteractionResult tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isShiftKeyDown()) && !this.isVehicle()) {
            if (!this.level().isClientSide() && player.startRiding(this)) {
                player.setYRot(this.getYRot());
                player.setXRot(this.getXRot());
                setSitting(false);
            }

            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return MoCLootTables.LIARD;
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        return "Liard";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return false;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        if (!this.getIsAdult() && (this.getMoCAge() < this.getMoCMaxAge() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityLiard) {
            return false;
        }
        return entity.getBbHeight() < 2F && entity.getBbWidth() < 2F;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.getBbHeight() * 0.92F;
    }
}
