/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import drzhark.mocreatures.client.model.MoCModelCrocodile;
import drzhark.mocreatures.entity.hunter.MoCEntityCrocodile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.tags.FluidTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import com.mojang.math.Axis;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderCrocodile extends MobRenderer<MoCEntityCrocodile, MoCModelCrocodile<MoCEntityCrocodile>> {

    public MoCModelCrocodile croc;

    public MoCRenderCrocodile(EntityRendererProvider.Context renderManagerIn, MoCModelCrocodile modelbase, float f) {
        super(renderManagerIn, modelbase, f);
        this.croc = modelbase;
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityCrocodile entitycrocodile) {
        return entitycrocodile.getTexture();
    }

    @Override
    public void render(MoCEntityCrocodile entitycrocodile, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn) {
        super.render(entitycrocodile, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
    }

    @Override
    protected void scale(MoCEntityCrocodile entitycrocodile, PoseStack poseStack, float f) {
        this.croc.biteProgress = entitycrocodile.biteProgress;
        this.croc.swimming = entitycrocodile.isSwimming();
        this.croc.resting = entitycrocodile.getIsSitting();
        if (entitycrocodile.isSpinning()) {
            spinCroc(entitycrocodile, (Mob) entitycrocodile.getVehicle(), poseStack);
        }
        stretch(entitycrocodile, poseStack);
        if (entitycrocodile.getIsSitting()) {
            if (!entitycrocodile.isEyeInFluid(FluidTags.WATER)) {
                adjustHeight(entitycrocodile, 0.2F, poseStack);
            } else {
                //adjustHeight(entitycrocodile, 0.1F);
            }

        }
        // if(!entitycrocodile.getIsAdult()) { }
    }

    protected void rotateAnimal(MoCEntityCrocodile entitycrocodile, PoseStack poseStack) {

        //float f = entitycrocodile.swingProgress *10F *entitycrocodile.getFlipDirection();
        //float f2 = entitycrocodile.swingProgress /30 *entitycrocodile.getFlipDirection();
        //poseStack.mulPose(Axis.ZN.rotationDegrees(180F + f));
        //poseStack.translate(0.0F-f2, 0.5F, 0.0F);
    }

    protected void adjustHeight(MoCEntityCrocodile entitycrocodile, float FHeight, PoseStack poseStack) {
        poseStack.translate(0.0F, FHeight, 0.0F);
    }

    protected void spinCroc(MoCEntityCrocodile entitycrocodile, Mob prey, PoseStack poseStack) {
        int intSpin = entitycrocodile.spinInt;
        int direction = 1;
        if (intSpin > 40) {
            intSpin -= 40;
            direction = -1;
        }
        int intEndSpin = intSpin;
        if (intSpin >= 20) {
            intEndSpin = (20 - (intSpin - 20));
        }
        if (intEndSpin == 0) {
            intEndSpin = 1;
        }
        float f3 = (((intEndSpin) - 1.0F) / 20F) * 1.6F;
        f3 = Mth.sqrt(f3);
        if (f3 > 1.0F) {
            f3 = 1.0F;
        }
        f3 *= direction;
        poseStack.mulPose(Axis.ZP.rotationDegrees(f3 * 90F));

        if (prey != null) {
            prey.deathTime = intEndSpin;
        }
    }

    protected void stretch(MoCEntityCrocodile entitycrocodile, PoseStack poseStack) {
        // float f = 1.3F;
        float f = entitycrocodile.getMoCAge() * 0.01F;
        // if(!entitycrocodile.getIsAdult()) { f = entitycrocodile.age; }
        poseStack.scale(f, f, f);
    }
}
