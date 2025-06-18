/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import drzhark.mocreatures.client.model.MoCModelWraith;
import drzhark.mocreatures.entity.hostile.MoCEntityWraith;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWraith extends MobRenderer<MoCEntityWraith, MoCModelWraith<MoCEntityWraith>> {

    public MoCRenderWraith(EntityRendererProvider.Context renderManagerIn, MoCModelWraith modelbiped, float f) {
        super(renderManagerIn, modelbiped, f);
    }

    @Override
    public void render(MoCEntityWraith wraith, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn) {
        // Assume non-glowing as default (transparent)
        boolean isGlowing = false;
        
        // Check for any wraith-specific data that might indicate glowing
        // This could be based on health, state, etc.
        
        poseStack.pushPose();
        RenderSystem.enableBlend();
        if (!isGlowing) {
            float transparency = 0.6F;
            RenderSystem.defaultBlendFunc();
            RenderSystem.clearColor(0.8F, 0.8F, 0.8F, transparency);
        } else {
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }
        super.render(wraith, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityWraith wraith) {
        return wraith.getTexture();
    }
}
