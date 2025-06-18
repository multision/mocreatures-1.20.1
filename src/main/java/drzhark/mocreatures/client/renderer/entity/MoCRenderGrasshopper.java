/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import drzhark.mocreatures.client.model.MoCModelGrasshopper;
import drzhark.mocreatures.entity.ambient.MoCEntityGrasshopper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Axis;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderGrasshopper extends MoCRenderMoC<MoCEntityGrasshopper, MoCModelGrasshopper<MoCEntityGrasshopper>> {

    public MoCRenderGrasshopper(EntityRendererProvider.Context renderManagerIn, MoCModelGrasshopper modelbase) {
        super(renderManagerIn, modelbase, 0.0F);
    }

    @Override
    protected void scale(MoCEntityGrasshopper entity, PoseStack poseStack, float par2) {
        rotateGrasshopper(entity, poseStack);
    }

    protected void rotateGrasshopper(MoCEntityGrasshopper entity, PoseStack poseStack) {
        if (!entity.onGround()) {
            if (entity.getDeltaMovement().y > 0.5D) {
                poseStack.mulPose(Axis.XN.rotationDegrees(35F));
            } else if (entity.getDeltaMovement().y < -0.5D) {
                poseStack.mulPose(Axis.XN.rotationDegrees(-35F));
            } else {
                poseStack.mulPose(Axis.XN.rotationDegrees((float) (entity.getDeltaMovement().y * 70D)));
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityGrasshopper par1Entity) {
        return par1Entity.getTexture();
    }
}
