/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import drzhark.mocreatures.client.model.MoCModelHorse;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderHorse extends MoCRenderMoC<MoCEntityHorse, MoCModelHorse<MoCEntityHorse>> {

    public MoCRenderHorse(EntityRendererProvider.Context context, MoCModelHorse<MoCEntityHorse> modelbase) {
        super(context, modelbase, 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityHorse entityhorse) {
        return entityhorse.getTexture();
    }

    protected void adjustHeight(MoCEntityHorse entityhorse, float FHeight, PoseStack poseStack) {
        poseStack.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void scale(MoCEntityHorse entityhorse, PoseStack poseStack, float partialTicks) {
        if (!entityhorse.getIsAdult() || entityhorse.getTypeMoC() > 64) {
            stretch(entityhorse, poseStack);
        }
        if (entityhorse.getIsGhost()) {
            adjustHeight(entityhorse, -0.3F + (entityhorse.tFloat() / 5F), poseStack);
        }
        super.scale(entityhorse, poseStack, partialTicks);
    }

    protected void stretch(MoCEntityHorse entityhorse, PoseStack poseStack) {
        float sizeFactor = entityhorse.getMoCAge() * 0.01F;
        if (entityhorse.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        if (entityhorse.getTypeMoC() > 64) //donkey
        {
            sizeFactor *= 0.9F;
        }
        poseStack.scale(sizeFactor, sizeFactor, sizeFactor);
    }
}
