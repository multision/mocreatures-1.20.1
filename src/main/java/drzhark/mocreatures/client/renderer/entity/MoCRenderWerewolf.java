/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import drzhark.mocreatures.MoCreatures;
import org.slf4j.Logger;
import drzhark.mocreatures.client.model.MoCModelWerehuman;
import drzhark.mocreatures.client.model.MoCModelWerewolf;
import drzhark.mocreatures.entity.hostile.MoCEntityWerewolf;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWerewolf<M extends EntityModel<MoCEntityWerewolf>> extends MobRenderer<MoCEntityWerewolf, M> {

    private final MoCModelWerewolf tempWerewolf;

    public MoCRenderWerewolf(EntityRendererProvider.Context context, MoCModelWerehuman<MoCEntityWerewolf> modelwerehuman, M modelbase, float f) {
        super(context, modelbase, f);
        this.tempWerewolf = (MoCModelWerewolf) modelbase;
        this.addLayer(new LayerMoCWereHuman(this));
    }

    @Override
    public void render(MoCEntityWerewolf entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLightIn) {
        this.tempWerewolf.hunched = entity.getIsHunched();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityWerewolf entity) {
        return entity.getTexture();
    }

    private class LayerMoCWereHuman extends RenderLayer<MoCEntityWerewolf, M> {

        private final MoCRenderWerewolf<M> renderer;
        private MoCModelWerehuman<MoCEntityWerewolf> humanModel;

        public LayerMoCWereHuman(MoCRenderWerewolf<M> renderer) {
            super(renderer);
            this.renderer = renderer;
            
            // Initialize with a fallback model to prevent null pointer exceptions
            try {
                EntityRendererProvider.Context context = null;
                // Get the model factory from entity renderer provider - this is a fallback approach
                // In a complete implementation, this should use the proper context
                ModelPart root = MoCModelWerehuman.createBodyLayer().bakeRoot();
                this.humanModel = new MoCModelWerehuman<>(root);
            } catch (Exception e) {
                MoCreatures.LOGGER.error("Failed to initialize werehuman model: {}", e.getMessage());
                // Create empty model to prevent NPE
                ModelPart root = MoCModelWerehuman.createBodyLayer().bakeRoot();
                this.humanModel = new MoCModelWerehuman<>(root);
            }
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLightIn,
                          MoCEntityWerewolf entity, float limbSwing, float limbSwingAmount, float partialTicks,
                          float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entity.getIsHumanForm() || this.humanModel == null) return;

            // Pick correct texture
            ResourceLocation texture;
            switch (entity.getTypeMoC()) {
                case 1:
                    texture = MoCreatures.proxy.getModelTexture("werehuman_dude.png");
                    break;
                case 2:
                    texture = MoCreatures.proxy.getModelTexture("werehuman_classic.png");
                    break;
                case 4:
                    texture = MoCreatures.proxy.getModelTexture("werehuman_woman.png");
                    break;
                default:
                    texture = MoCreatures.proxy.getModelTexture("werehuman_oldie.png");
            }

            try {
                // Sync animation and pose
                humanModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                this.getParentModel().copyPropertiesTo(humanModel);

                poseStack.pushPose();

                // Optional scale fix
                //poseStack.translate(0.0D, -0.05D, 0.0D); // Try -0.1D or -0.0625D if needed

                poseStack.scale(1.0F, 1.0F, 1.0F); // Adjust if needed

                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
                humanModel.renderToBuffer(poseStack, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY,
                        1.0F, 1.0F, 1.0F, 1.0F);

                poseStack.popPose();
            } catch (Exception e) {
                MoCreatures.LOGGER.error("Error rendering werehuman model: {}", e.getMessage());
            }
        }
    }
}
