/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import drzhark.mocreatures.client.model.MoCModelRat;
import drzhark.mocreatures.entity.hostile.MoCEntityRat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Axis;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderRat<T extends MoCEntityRat, M extends MoCModelRat<T>> extends MobRenderer<T, M> {

    public MoCRenderRat(EntityRendererProvider.Context renderManagerIn, M modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    public void render(T entityrat, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn) {
        super.render(entityrat, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
    }

    @Override
    protected void scale(T entityrat, PoseStack poseStack, float f) {
        stretch(entityrat, poseStack);
        if (entityrat.onClimbable()) {
            rotateAnimal(entityrat, poseStack);
        }
    }

    protected void rotateAnimal(T entityrat, PoseStack poseStack) {
        poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
        poseStack.translate(0.0F, 0.4F, 0.0F);
    }

    protected void stretch(T entityrat, PoseStack poseStack) {
        float f = 0.8F;
        poseStack.scale(f, f, f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entityrat) {
        return entityrat.getTexture();
    }
}
