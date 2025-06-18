/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelDolphin;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import com.mojang.math.Axis;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderDolphin extends MobRenderer<MoCEntityDolphin, MoCModelDolphin<MoCEntityDolphin>> {

    public MoCRenderDolphin(EntityRendererProvider.Context renderManagerIn, MoCModelDolphin modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    protected void scale(MoCEntityDolphin entitydolphin, PoseStack poseStack, float par2) {
        stretch(entitydolphin, poseStack);
    }

    @SuppressWarnings("removal")
    @Override
    public void render(MoCEntityDolphin entitydolphin, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn) {
        super.render(entitydolphin, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitydolphin.getPetName().isEmpty());
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        
        if (entitydolphin.getIsTamed()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entitydolphin.distanceTo(this.entityRenderDispatcher.camera.getEntity());
            if (f4 < 16F) {
                String s = entitydolphin.getPetName();
                float f5 = 0.1F;
                Font font = this.getFont();
                
                poseStack.pushPose();
                poseStack.translate(0.0F, f5, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(-this.entityRenderDispatcher.camera.getYRot()));
                poseStack.scale(-f3, -f3, f3);

                byte byte0 = -50;
                if (flag1) {
                    if (!flag) {
                        byte0 += 8;
                    }
                    
                    // Render health bar
                    Matrix4f matrix4f = poseStack.last().pose();
                    float f6 = entitydolphin.getHealth();
                    float f7 = entitydolphin.getMaxHealth();
                    float f8 = f6 / f7;
                    float f9 = 40F * f8;
                    
                    // Red background
                    VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.text(new ResourceLocation("textures/misc/white.png")));
                    vertexconsumer.vertex(matrix4f, -20F + f9, -10 + byte0, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).uv(0, 0).endVertex();
                    vertexconsumer.vertex(matrix4f, -20F + f9, -6 + byte0, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).uv(0, 1).endVertex();
                    vertexconsumer.vertex(matrix4f, 20F, -6 + byte0, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).uv(1, 1).endVertex();
                    vertexconsumer.vertex(matrix4f, 20F, -10 + byte0, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).uv(1, 0).endVertex();
                    
                    // Green health
                    vertexconsumer = buffer.getBuffer(RenderType.text(new ResourceLocation("textures/misc/white.png")));
                    vertexconsumer.vertex(matrix4f, -20F, -10 + byte0, 0.01F).color(0.0F, 0.7F, 0.0F, 1.0F).uv(0, 0).endVertex();
                    vertexconsumer.vertex(matrix4f, -20F, -6 + byte0, 0.01F).color(0.0F, 0.7F, 0.0F, 1.0F).uv(0, 1).endVertex();
                    vertexconsumer.vertex(matrix4f, f9 - 20F, -6 + byte0, 0.01F).color(0.0F, 0.7F, 0.0F, 1.0F).uv(1, 1).endVertex();
                    vertexconsumer.vertex(matrix4f, f9 - 20F, -10 + byte0, 0.01F).color(0.0F, 0.7F, 0.0F, 1.0F).uv(1, 0).endVertex();
                }
                
                if (flag) {
                    // Render pet name
                    int textWidth = font.width(s);
                    float textScale = 1.0f;
                    float textX = -textWidth / 2.0f;
                    float textY = byte0;
                    
                    // Name background
                    Matrix4f matrix4f = poseStack.last().pose();
                    VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.textBackground());
                    vertexconsumer.vertex(matrix4f, textX - 1, textY - 1, 0.0F).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    vertexconsumer.vertex(matrix4f, textX - 1, textY + 8, 0.0F).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    vertexconsumer.vertex(matrix4f, textX + textWidth + 1, textY + 8, 0.0F).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    vertexconsumer.vertex(matrix4f, textX + textWidth + 1, textY - 1, 0.0F).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    
                    // Render text
                    font.drawInBatch(s, textX, textY, 0x20ffffff, false, matrix4f, buffer, Font.DisplayMode.SEE_THROUGH, 0, packedLightIn);
                    font.drawInBatch(s, textX, textY, -1, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 0, packedLightIn);
                }

                poseStack.popPose();
            }
        }
    }

    protected void stretch(MoCEntityDolphin entitydolphin, PoseStack poseStack) {
        poseStack.scale(entitydolphin.getMoCAge() * 0.01F, entitydolphin.getMoCAge() * 0.01F, entitydolphin.getMoCAge() * 0.01F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityDolphin entitydolphin) {
        return entitydolphin.getTexture();
    }
}
