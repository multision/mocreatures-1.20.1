/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import drzhark.mocreatures.client.model.MoCModelLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderLitterBox extends MobRenderer<MoCEntityLitterBox, MoCModelLitterBox<MoCEntityLitterBox>> {

    public MoCModelLitterBox litterbox;

    public MoCRenderLitterBox(EntityRendererProvider.Context renderManagerIn, MoCModelLitterBox modellitterbox, float f) {
        super(renderManagerIn, modellitterbox, f);
        this.litterbox = modellitterbox;
    }

    @Override
    protected void scale(MoCEntityLitterBox entitylitterbox, PoseStack poseStack, float f) {
        this.litterbox.usedlitter = entitylitterbox.getUsedLitter();
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityLitterBox entitylitterbox) {
        return entitylitterbox.getTexture();
    }
}
