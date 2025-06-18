/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Port of MoCModelTurkey from 1.16.5 → 1.20.1 Forge.
 */
@OnlyIn(Dist.CLIENT)
public class MoCModelTurkey<T extends MoCEntityTurkey> extends EntityModel<T> {

    @SuppressWarnings("removal")
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("mocreatures", "turkey"), "main"
    );

    // All the parts that correspond to your old ModelRenderers:
    private final ModelPart beak;
    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart chest;
    private final ModelPart rWing;
    private final ModelPart lWing;
    private final ModelPart uBody;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart rLeg;
    private final ModelPart rFoot;
    private final ModelPart lLeg;
    private final ModelPart lFoot;

    // Animation state
    private boolean male;

    public MoCModelTurkey(ModelPart root) {
        // Grab each child by name from the baked root:
        this.beak   = root.getChild("beak");
        this.head   = root.getChild("head");
        this.neck   = root.getChild("neck");
        this.chest  = root.getChild("chest");
        this.rWing  = root.getChild("rWing");
        this.lWing  = root.getChild("lWing");
        this.uBody  = root.getChild("uBody");
        this.body   = root.getChild("body");
        this.tail   = root.getChild("tail");
        this.rLeg   = root.getChild("rLeg");
        this.rFoot  = root.getChild("rFoot");
        this.lLeg   = root.getChild("lLeg");
        this.lFoot  = root.getChild("lFoot");
    }

    /**
     * Defines all turkey parts (beak, head, neck, chest, wings, body, tail, legs, feet).
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // BEAK
        root.addOrReplaceChild(
                "beak",
                CubeListBuilder.create()
                        .texOffs(17, 17)
                        .addBox(-0.5F, -1.866667F, -3.366667F, 1, 1, 2),
                PartPose.offsetAndRotation(
                        0F, 9.7F, -5.1F,
                        0.7807508F, 0F, 0F
                )
        );

        // HEAD
        root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 27)
                        .addBox(-1F, -2F, -2F, 2, 2, 3),
                PartPose.offsetAndRotation(
                        0F, 9.7F, -5.1F,
                        0.4833219F, 0F, 0F
                )
        );

        // NECK
        root.addOrReplaceChild(
                "neck",
                CubeListBuilder.create()
                        .texOffs(0, 32)
                        .addBox(-1F, -6F, -1F, 2, 6, 2),
                PartPose.offsetAndRotation(
                        0F, 14.7F, -6.5F,
                        -0.2246208F, 0F, 0F
                )
        );

        // CHEST
        root.addOrReplaceChild(
                "chest",
                CubeListBuilder.create()
                        .texOffs(0, 17)
                        .addBox(-3F, 0F, -4F, 6, 6, 4),
                PartPose.offsetAndRotation(
                        0F, 12.5F, -4F,
                        0.5934119F, 0F, 0F
                )
        );

        // RIGHT WING
        root.addOrReplaceChild(
                "rWing",
                CubeListBuilder.create()
                        .texOffs(32, 30)
                        .addBox(-1F, -2F, 0F, 1, 6, 7),
                PartPose.offsetAndRotation(
                        -4F, 14F, -3F,
                        -0.3346075F, 0F, 0F
                )
        );

        // LEFT WING
        root.addOrReplaceChild(
                "lWing",
                CubeListBuilder.create()
                        .texOffs(48, 30)
                        .addBox(0F, -2F, 0F, 1, 6, 7),
                PartPose.offsetAndRotation(
                        4F, 14F, -3F,
                        -0.3346075F, 0F, 0F
                )
        );

        // UPPER BODY (male tail fan)
        root.addOrReplaceChild(
                "uBody",
                CubeListBuilder.create()
                        .texOffs(34, 0)
                        .addBox(-2.5F, -4F, 0F, 5, 7, 9),
                PartPose.offset(0F, 15F, -3F)
        );

        // BODY
        root.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4F, -4F, 0F, 8, 8, 9),
                PartPose.offset(0F, 16F, -4F)
        );

        // TAIL
        root.addOrReplaceChild(
                "tail",
                CubeListBuilder.create()
                        .texOffs(32, 17)
                        .addBox(-8F, -9F, 0F, 16, 12, 0),
                PartPose.offsetAndRotation(
                        0F, 14F, 6F,
                        -0.2974289F, 0F, 0F
                )
        );

        // RIGHT LEG
        root.addOrReplaceChild(
                "rLeg",
                CubeListBuilder.create()
                        .texOffs(27, 17)
                        .addBox(-0.5F, 0F, -0.5F, 1, 5, 1),
                PartPose.offset( -2F, 19F, 0.5F )
        );

        // RIGHT FOOT
        root.addOrReplaceChild(
                "rFoot",
                CubeListBuilder.create()
                        .texOffs(20, 23)
                        .addBox(-1.5F, 5F, -2.5F, 3, 0, 3),
                PartPose.offset( -2F, 19F, 0.5F )
        );

        // LEFT LEG
        root.addOrReplaceChild(
                "lLeg",
                CubeListBuilder.create()
                        .texOffs(23, 17)
                        .addBox(-0.5F, 0F, -0.5F, 1, 5, 1),
                PartPose.offset( 2F, 19F, 0.5F )
        );

        // LEFT FOOT
        root.addOrReplaceChild(
                "lFoot",
                CubeListBuilder.create()
                        .texOffs(20, 26)
                        .addBox(-1.5F, 5F, -2.5F, 3, 0, 3),
                PartPose.offset( 2F, 19F, 0.5F )
        );

        return LayerDefinition.create(mesh, 64, 64);
    }

    /**
     * Called once before rendering; store “male” flag.
     */
    @Override
    public void setupAnim(
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        this.male = entity.getTypeMoC() == 1;
        // NB: Other per‐frame state (like wingF, leg rotations, etc.) happens in setRotationAngles().
    }

    /**
     * Called each frame to position/rotate all parts.  Mirrors your old setRotationAngles(...) exactly.
     */
    @Override
    public void prepareMobModel(
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float partialTick
    ) {
        // We call the original “setRotationAngles” inside prepareMobModel:
        setRotationAngles(entity, limbSwing, limbSwingAmount, partialTick, entity.yHeadRot, entity.xRotO);
    }

    public void setRotationAngles(
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        // Copy your old logic exactly:
        float LLegXRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        float RLegXRot = Mth.cos((limbSwing * 0.6662F) + (float)Math.PI) * 1.4F * limbSwingAmount;
        float wingF    = (Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount) / 4F;

        // HEAD pitch & yaw
        this.head.xRot = 0.4833219F + headPitch / 57.29578F;
        this.head.yRot = netHeadYaw / 57.29578F;

        // BEAK follows head (slightly offset)
        this.beak.xRot = 0.7807508F - this.head.xRot + 0.4833219F;
        this.beak.yRot = this.head.yRot;

        // LEGS
        this.lLeg.xRot  = LLegXRot;
        this.lFoot.xRot = this.lLeg.xRot;
        this.rLeg.xRot  = RLegXRot;
        this.rFoot.xRot = this.rLeg.xRot;

        // WINGS
        this.lWing.yRot = wingF;
        this.rWing.yRot = -wingF;

        // TAIL & body‐positioning
        if (this.male) {
            this.tail.xRot = -0.2974289F + wingF;
            this.tail.y = 14F;
            this.tail.z = 6F;
            this.chest.y = 12.5F;
            this.body.y  = 16F;
            this.lWing.x = 4F;
            this.rWing.x = -4F;
        } else {
            this.tail.xRot = wingF - (110 / 57.29578F);
            this.tail.y = 17F;
            this.tail.z = 7F;
            this.chest.y = 16F;
            this.body.y  = 20F;
            this.lWing.x = 3.2F;
            this.rWing.x = -3.2F;
        }
    }

    /**
     * Renders all parts.  Mirrors your old render(...) logic, including child‐scaling.
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
        if (this.young) {
            // All children rendered as “female” roughly the same way your code did:
            poseStack.pushPose();
            poseStack.translate(0.0F, 5.0F, 2.0F);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0.0F, 24.0F, 0.0F);

            // render head/neck/wings/tail/legs/feet
            this.beak.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.head.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.neck.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.rWing.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.lWing.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.tail.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.rLeg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.rFoot.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.lLeg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.lFoot.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

            poseStack.pushPose();
            poseStack.scale(0.8F, 0.8F, 1F);
            this.body.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.chest.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();

            poseStack.popPose();
        } else {
            // Adult rendering
            this.beak.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.head.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.neck.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.rWing.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.lWing.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.tail.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.rLeg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.rFoot.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.lLeg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.lFoot.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

            if (male) {
                this.uBody.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                this.body.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                this.chest.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            } else {
                poseStack.pushPose();
                poseStack.scale(0.8F, 0.8F, 1F);
                this.body.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                this.chest.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                poseStack.popPose();
            }
        }
    }
}
