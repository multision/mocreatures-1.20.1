package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.world.entity.Mob;

/**
 * Transparency handler specifically for horses.
 * Handles both ghost states and vanishing effects.
 */
public class HorseTransparencyHandler extends BaseTransparencyHandler<MoCEntityHorse> {
    
    @Override
    public boolean hasVanishingEffect(MoCEntityHorse entity) {
        return entity.getVanishC() != 0;
    }
    
    @Override
    public float getVanishingTransparency(MoCEntityHorse entity) {
        return 1.0F - (((float) (entity.getVanishC())) / 100);
    }
    
    @Override
    public float getGhostTransparency(MoCEntityHorse entity) {
        return entity.tFloat();
    }
    
    @Override
    public float[] getTransparencyColor(MoCEntityHorse entity) {
        // Horses use white color for transparency
        return new float[]{1.0F, 1.0F, 1.0F};
    }
} 