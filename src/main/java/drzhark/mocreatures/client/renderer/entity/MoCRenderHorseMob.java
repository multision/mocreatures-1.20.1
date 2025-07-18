/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import drzhark.mocreatures.client.model.MoCModelHorseMob;
import drzhark.mocreatures.entity.hostile.MoCEntityHorseMob;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderHorseMob extends MobRenderer<MoCEntityHorseMob, MoCModelHorseMob<MoCEntityHorseMob>> {

    public MoCRenderHorseMob(EntityRendererProvider.Context renderManagerIn, MoCModelHorseMob modelbase) {
        super(renderManagerIn, modelbase, 0.5F);

    }

    protected void adjustHeight(MoCEntityHorseMob entityhorsemob, float FHeight, PoseStack poseStack) {
        poseStack.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityHorseMob entityhorsemob) {
        return entityhorsemob.getTexture();
    }
}
