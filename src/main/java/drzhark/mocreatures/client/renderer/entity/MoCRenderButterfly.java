/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import drzhark.mocreatures.client.model.MoCModelButterfly;
import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderButterfly extends MoCRenderInsect<MoCEntityButterfly, MoCModelButterfly<MoCEntityButterfly>> {

    public MoCRenderButterfly(EntityRendererProvider.Context renderManagerIn, MoCModelButterfly modelbase) {
        super(renderManagerIn, modelbase);

    }

    @Override
    protected void scale(MoCEntityButterfly entitybutterfly, PoseStack poseStack, float par2) {
        if (entitybutterfly.isOnAir() || !entitybutterfly.onGround()) {
            adjustHeight(entitybutterfly, entitybutterfly.tFloat(), poseStack);
        }
        if (entitybutterfly.climbing()) {
            rotateAnimal(entitybutterfly, poseStack);
        }
        stretch(entitybutterfly, poseStack);
    }

    protected void adjustHeight(MoCEntityButterfly entitybutterfly, float FHeight, PoseStack poseStack) {
        poseStack.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityButterfly entitybutterfly) {
        return entitybutterfly.getTexture();
    }
}
