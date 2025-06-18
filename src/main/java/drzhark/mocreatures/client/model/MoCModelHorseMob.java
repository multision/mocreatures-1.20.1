/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import drzhark.mocreatures.entity.hostile.MoCEntityHorseMob;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Port of MoCModelHorseMob (1.16.5) to Minecraft 1.20.1.
 * Extends MoCModelAbstractHorse, reusing its horse geometry.
 */
@OnlyIn(Dist.CLIENT)
public class MoCModelHorseMob<T extends MoCEntityHorseMob> extends MoCModelAbstractHorse<T> {

    // Reuse the same layer location as the regular horse
    @SuppressWarnings("removal")
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("mocreatures", "horse"), "main"
    );

    // Dynamic state fields, set each tick in setupAnim
    private int typeMob;
    private boolean openMouth;
    private boolean flyer;
    private boolean isUnicorned;

    public MoCModelHorseMob(ModelPart root) {
        super(root);
    }

    /**
     * Copies entity state (typeMob, openMouth, flyer, isUnicorned, etc.) from MoCEntityHorseMob
     * into model fields. Called before animations are applied.
     */
    @Override
    public void setupAnim(
            T entityIn,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        // Always treat as base type 0 (hostile variant)
        type = 0;
        typeMob     = entityIn.getTypeMoC();
        wings = entityIn.isFlyer();
        flyer       = entityIn.isFlyer();
        eating      = (entityIn.eatingCounter != 0);
        standing    = (entityIn.standCounter != 0 && entityIn.getVehicle() == null);
        openMouth   = (entityIn.mouthCounter != 0);
        moveTail    = (entityIn.tailCounter != 0);
        flapwings   = (entityIn.wingFlapCounter != 0);
        rider       = entityIn.isPassenger();
        floating    = (entityIn.isFlyer() && entityIn.isOnAir());
        isUnicorned = entityIn.isUnicorned();
        saddled     = false;
        shuffling   = false;

        // Delegate to parent for head/neck/leg/tail animations
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    /**
     * Renders the hostile horse model without saddles or chest bags.
     * Omits any saddle-specific parts and adjusts for hostile-only features.
     */
    @Override
    public void renderToBuffer(
            PoseStack poseStack,
            VertexConsumer buffer,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        // Ears (always render)
        this.ear1.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.ear2.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        // Neck and head
        this.neck.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        // Mouth (open or closed)
        if (openMouth) {
            this.uMouth2.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.lMouth2.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        } else {
            this.uMouth.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.lMouth.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }

        // Mane, body, and tail segments
        this.mane.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tailA.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tailB.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.tailC.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        // Legs (each in three segments)
        this.leg1A.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg1B.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg1C.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        this.leg2A.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg2B.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg2C.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        this.leg3A.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg3B.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg3C.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        this.leg4A.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg4B.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg4C.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        // Unicorn horn (if applicable)
        if (isUnicorned) {
            this.unicorn.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }

        // Pegasus wings (only if flyer and not specific mob types 34 or 36)
        if (flyer && typeMob != 34 && typeMob != 36) {
            this.midWing.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.innerWing.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.outerWing.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.innerWingR.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.midWingR.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.outerWingR.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }

    /**
     * Since MoCModelHorseMob reuses the same geometry as a regular horse,
     * simply return the LayerDefinition from MoCModelAbstractHorse.
     */
    public static LayerDefinition createBodyLayer() {
        return MoCModelAbstractHorse.createBodyLayer();
    }
}
