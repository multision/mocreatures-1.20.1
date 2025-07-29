package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.world.entity.Mob;

/**
 * Interface for handling transparency in entity renderers.
 * Provides a unified way to handle both full-model and partial-model transparency.
 */
public interface ITransparencyHandler<T extends Mob> {
    
    /**
     * Checks if the entity should be rendered with transparency
     */
    boolean shouldRenderTransparent(T entity);
    
    /**
     * Gets the transparency value (0.0 = fully transparent, 1.0 = fully opaque)
     */
    float getTransparency(T entity);
    
    /**
     * Gets the transparency type for this entity
     */
    TransparencyType getTransparencyType(T entity);
    
    /**
     * Checks if the entity has a vanishing effect
     */
    default boolean hasVanishingEffect(T entity) {
        return false;
    }
    
    /**
     * Gets transparency for vanishing effect
     */
    default float getVanishingTransparency(T entity) {
        return 1.0F;
    }
    
    /**
     * Gets the color tint for transparency (RGB values, 0.0-1.0)
     */
    default float[] getTransparencyColor(T entity) {
        return new float[]{1.0F, 1.0F, 1.0F}; // Default white
    }
    
    /**
     * Enum defining different types of transparency
     */
    enum TransparencyType {
        /** Full model transparency (like ghost entities) */
        FULL_MODEL,
        /** Partial model transparency (like wings) */
        PARTIAL_MODEL,
        /** No transparency */
        NONE
    }
} 