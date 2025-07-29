package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Entity;

/**
 * Interface for models that need partial transparency.
 * This allows models to specify which parts should be rendered with transparency
 * and which should remain opaque.
 */
public interface IPartialTransparencyModel<T extends Entity> {
    
    /**
     * Renders opaque parts of the model
     */
    void renderOpaqueParts(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
    
    /**
     * Renders transparent parts of the model
     */
    void renderTransparentParts(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
    
    /**
     * Gets the transparency value for transparent parts
     */
    float getTransparencyValue();
    
    /**
     * Gets the color tint for transparent parts
     */
    default float[] getTransparencyColor() {
        return new float[]{0.8F, 0.8F, 0.8F}; // Default grayish tint
    }
    
    /**
     * Checks if the model should render with partial transparency
     */
    boolean shouldRenderPartialTransparency();
} 