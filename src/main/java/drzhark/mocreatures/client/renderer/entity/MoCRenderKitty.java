/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelKitty;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Axis;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.lang.reflect.Field;

import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class MoCRenderKitty extends MoCRenderMoC<MoCEntityKitty, MoCModelKitty<MoCEntityKitty>> {

    public MoCModelKitty kitty;
    private Field isSittingField;
    private Field isSwingingField;
    private Field swingProgressField;
    private Field kittystateField;

    public MoCRenderKitty(EntityRendererProvider.Context renderManagerIn, MoCModelKitty modelkitty, float f) {
        super(renderManagerIn, modelkitty, f);
        this.kitty = modelkitty;

        // Try to get fields via reflection
        try {
            isSittingField = MoCModelKitty.class.getDeclaredField("isSitting");
            isSittingField.setAccessible(true);

            isSwingingField = MoCModelKitty.class.getDeclaredField("isSwinging");
            isSwingingField.setAccessible(true);

            swingProgressField = MoCModelKitty.class.getDeclaredField("swingProgress");
            swingProgressField.setAccessible(true);

            kittystateField = MoCModelKitty.class.getDeclaredField("kittystate");
            kittystateField.setAccessible(true);
        } catch (Exception e) {
            // Fields not found, we'll use direct setting approach
        }
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityKitty entitykitty) {
        return entitykitty.getTexture();
    }

    @Override
    public void render(MoCEntityKitty entitykitty, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int packedLightIn) {
        super.render(entitykitty, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
        boolean displayPetIcons = MoCreatures.proxy.getDisplayPetIcons();
        if (entitykitty.getIsTamed()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entitykitty.distanceTo(this.entityRenderDispatcher.camera.getEntity());
            if (f4 < 12F) {
                float f5 = 0.2F;
                if (entitykitty.getIsSitting()) {
                    f5 = 0.4F;
                }

                poseStack.pushPose();
                poseStack.translate(0.0F, f5, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(-this.entityRenderDispatcher.camera.getYRot()));
                poseStack.scale(-f3, -f3, f3);

                if (displayPetIcons && entitykitty.getShowEmoteIcon()) {
                    int i = -90;
                    int k = 32;
                    int l = (k / 2) * -1;
                    Matrix4f matrix = poseStack.last().pose();
                    VertexConsumer vc = buffer.getBuffer(RenderType.text(entitykitty.getEmoteIcon()));

                    // white tint, no overlay, full bright
                    int packedLight = 0xF000F0;
                    int packedOverlay = OverlayTexture.NO_OVERLAY;

                    // lower-left
                    vc.vertex(matrix, l, i + k, 0f)
                            .color(255, 255, 255, 255)
                            .uv(0.0F, 1.0F)
                            .overlayCoords(packedOverlay)
                            .uv2(packedLight)
                            .endVertex();

                    // lower-right
                    vc.vertex(matrix, l + k, i + k, 0f)
                            .color(255, 255, 255, 255)
                            .uv(1.0F, 1.0F)
                            .overlayCoords(packedOverlay)
                            .uv2(packedLight)
                            .endVertex();

                    // upper-right
                    vc.vertex(matrix, l + k, i, 0f)
                            .color(255, 255, 255, 255)
                            .uv(1.0F, 0.0F)
                            .overlayCoords(packedOverlay)
                            .uv2(packedLight)
                            .endVertex();

                    // upper-left
                    vc.vertex(matrix, l, i, 0f)
                            .color(255, 255, 255, 255)
                            .uv(0.0F, 0.0F)
                            .overlayCoords(packedOverlay)
                            .uv2(packedLight)
                            .endVertex();
                }

                poseStack.popPose();
            }
        }
    }

    protected void onMaBack(MoCEntityKitty entitykitty, PoseStack poseStack) {
        poseStack.mulPose(Axis.ZN.rotationDegrees(90F));
        if (!entitykitty.level().isClientSide() && (entitykitty.getVehicle() != null)) {
            poseStack.translate(-1.5F, 1.2F, -0.2F);
        } else {
            poseStack.translate(0.1F, 1.2F, -0.2F);
        }
    }

    protected void onTheSide(MoCEntityKitty entityliving, PoseStack poseStack) {
        poseStack.mulPose(Axis.ZN.rotationDegrees(90F));
        poseStack.translate(1.2F, 1.5F, -0.2F);
    }

    @Override
    protected void scale(MoCEntityKitty entitykitty, PoseStack poseStack, float f) {
        // Fix model positioning - translate down to match entity position
        poseStack.translate(0.0F, 1.0F, 0.0F);

        // Update model state from entity
        try {
            if (isSittingField != null) {
                isSittingField.set(this.kitty, entitykitty.getIsSitting());
                isSwingingField.set(this.kitty, entitykitty.getIsSwinging());
                swingProgressField.set(this.kitty, entitykitty.attackAnim);
                kittystateField.set(this.kitty, entitykitty.getKittyState());
            }
        } catch (Exception e) {
            // Ignore reflection errors
        }

        if (!entitykitty.getIsAdult()) {
            stretch(entitykitty, poseStack);
        }
        if (entitykitty.getKittyState() == 20) {
            onTheSide(entitykitty, poseStack);
        }
        if (entitykitty.climbingTree()) {
            rotateAnimal(entitykitty, poseStack);
        }
        if (entitykitty.upsideDown()) {
            upsideDown(entitykitty, poseStack);
        }
        if (entitykitty.onMaBack()) {
            onMaBack(entitykitty, poseStack);
        }
    }

    protected void rotateAnimal(MoCEntityKitty entitykitty, PoseStack poseStack) {
        poseStack.mulPose(Axis.XN.rotationDegrees(90F));
        poseStack.translate(0.0F, 1.5F, -1.5F);
    }

    protected void stretch(MoCEntityKitty entitykitty, PoseStack poseStack) {
        poseStack.scale(entitykitty.getMoCAge() * 0.01F, entitykitty.getMoCAge() * 0.01F,
                entitykitty.getMoCAge() * 0.01F);
    }

    protected void upsideDown(MoCEntityKitty entitykitty, PoseStack poseStack) {
        poseStack.mulPose(Axis.ZN.rotationDegrees(180F));
        poseStack.translate(0F, 2.75F, 0F);
    }
}
