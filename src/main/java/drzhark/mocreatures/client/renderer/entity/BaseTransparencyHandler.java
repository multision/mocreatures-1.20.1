package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import net.minecraft.world.entity.Mob;

/**
 * Base implementation of ITransparencyHandler for common ghost entity functionality.
 * Provides default implementations for entities that have ghost states.
 */
public abstract class BaseTransparencyHandler<T extends Mob> implements ITransparencyHandler<T> {
    
    @Override
    public boolean shouldRenderTransparent(T entity) {
        if (!(entity instanceof IMoCEntity)) {
            return false;
        }
        
        IMoCEntity entityMoC = (IMoCEntity) entity;
        return entityMoC.getIsGhost() || hasVanishingEffect(entity);
    }
    
    @Override
    public float getTransparency(T entity) {
        if (!(entity instanceof IMoCEntity)) {
            return 1.0F;
        }
        
        IMoCEntity entityMoC = (IMoCEntity) entity;
        
        // Check vanishing effect first
        if (hasVanishingEffect(entity)) {
            return getVanishingTransparency(entity);
        }
        
        // Check ghost state
        if (entityMoC.getIsGhost()) {
            return getGhostTransparency(entity);
        }
        
        return 1.0F; // Fully opaque
    }
    
    @Override
    public TransparencyType getTransparencyType(T entity) {
        if (!shouldRenderTransparent(entity)) {
            return TransparencyType.NONE;
        }
        
        // Default to full model transparency for ghost entities
        return TransparencyType.FULL_MODEL;
    }
    
    /**
     * Gets the transparency value for ghost entities.
     * Override this method to provide custom ghost transparency logic.
     */
    protected float getGhostTransparency(T entity) {
        // Default implementations for known entity types
        if (entity instanceof MoCEntityHorse) {
            return ((MoCEntityHorse) entity).tFloat();
        } else if (entity instanceof MoCEntityBigCat) {
            return ((MoCEntityBigCat) entity).tFloat();
        } else if (entity instanceof MoCEntityWyvern) {
            return ((MoCEntityWyvern) entity).tFloat();
        }
        
        // Default ghost transparency
        return 0.6F;
    }
    
    /**
     * Gets the color tint for ghost entities.
     * Override this method to provide custom color tinting.
     */
    @Override
    public float[] getTransparencyColor(T entity) {
        // Default ghost color (slightly grayed out)
        return new float[]{0.8F, 0.8F, 0.8F};
    }
} 