package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.hostile.MoCEntityWraith;
import drzhark.mocreatures.entity.hostile.MoCEntityFlameWraith;
import net.minecraft.world.entity.Mob;

/**
 * Transparency handler specifically for wraiths.
 * Wraiths are always transparent with different values for different types.
 */
public class WraithTransparencyHandler extends BaseTransparencyHandler<MoCEntityWraith> {
    
    @Override
    public boolean shouldRenderTransparent(MoCEntityWraith entity) {
        // Wraiths are always transparent
        return true;
    }
    
    @Override
    public float getTransparency(MoCEntityWraith entity) {
        if (entity instanceof MoCEntityFlameWraith) {
            return 0.4F;
        } else {
            return 0.6F;
        }
    }
    
    @Override
    public float[] getTransparencyColor(MoCEntityWraith entity) {
        if (entity instanceof MoCEntityFlameWraith) {
            return new float[]{1.0F, 0.6F, 0.6F}; // Reddish tint for flame wraiths
        } else {
            return new float[]{0.8F, 0.8F, 0.8F}; // Grayish tint for regular wraiths
        }
    }
    
    @Override
    public TransparencyType getTransparencyType(MoCEntityWraith entity) {
        return TransparencyType.FULL_MODEL;
    }
} 