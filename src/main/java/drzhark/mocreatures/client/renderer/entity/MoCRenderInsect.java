/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.EntityModel;
import com.mojang.math.Axis;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderInsect<T extends MoCEntityInsect, M extends EntityModel<T>> extends MoCRenderMoC<T, M> {

    public MoCRenderInsect(EntityRendererProvider.Context renderManagerIn, M modelbase) {
        super(renderManagerIn, modelbase, 0.0F);

    }

    @Override
    protected void scale(T entityinsect, PoseStack poseStack, float par2) {
        if (entityinsect.climbing()) {
            rotateAnimal(entityinsect, poseStack);
        }

        stretch(entityinsect, poseStack);
    }

    protected void rotateAnimal(T entityinsect, PoseStack poseStack) {
        poseStack.mulPose(Axis.XN.rotationDegrees(90F));
    }

    protected void stretch(T entityinsect, PoseStack poseStack) {
        float sizeFactor = entityinsect.getSizeFactor();
        poseStack.scale(sizeFactor, sizeFactor, sizeFactor);
    }
}
