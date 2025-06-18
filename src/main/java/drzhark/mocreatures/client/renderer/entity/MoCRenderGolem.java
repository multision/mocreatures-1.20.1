/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import drzhark.mocreatures.client.model.MoCModelGolem;
import drzhark.mocreatures.entity.hostile.MoCEntityGolem;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderGolem extends MoCRenderMoC<MoCEntityGolem, MoCModelGolem<MoCEntityGolem>> {

    public MoCRenderGolem(EntityRendererProvider.Context renderManagerIn, MoCModelGolem modelbase, float f) {
        super(renderManagerIn, modelbase, f);
        this.addLayer(new LayerMoCGolem(this));
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityGolem par1Entity) {
        return par1Entity.getTexture();
    }

    // Simple layer renderer that applies an energy swirl effect
    private class LayerMoCGolem extends RenderLayer<MoCEntityGolem, MoCModelGolem<MoCEntityGolem>> {
        public LayerMoCGolem(MoCRenderGolem render) {
            super(render);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLightIn, 
                           MoCEntityGolem entity, float limbSwing, float limbSwingAmount, 
                           float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            ResourceLocation effectTexture = entity.getEffectTexture();
            if (effectTexture != null) {
                float f = entity.tickCount + partialTicks;
                getParentModel().setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.energySwirl(effectTexture, f * 0.01F, f * 0.01F));
                getParentModel().prepareMobModel(entity, netHeadYaw, headPitch, f);
                getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
            }
        }
    }
}
