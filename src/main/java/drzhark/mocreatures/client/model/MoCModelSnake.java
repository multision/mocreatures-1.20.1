/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Port of MoCModelSnake from 1.16.5 → 1.20.1.
 */
@OnlyIn(Dist.CLIENT)
public class MoCModelSnake<T extends MoCEntitySnake> extends EntityModel<T> {

    @SuppressWarnings("removal")
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("mocreatures", "snake"), "main"
    );

    private static final int BODY_PARTS = 40;

    private final ModelPart[] bodySnake = new ModelPart[BODY_PARTS];
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart lNose;
    private final ModelPart teethUR;
    private final ModelPart teethUL;
    private final ModelPart tongue0;
    private final ModelPart tongue;
    private final ModelPart tongue1;
    private final ModelPart tail;
    private final ModelPart wing1L;
    private final ModelPart wing1R;
    private final ModelPart wing2L;
    private final ModelPart wing2R;
    private final ModelPart wing3L;
    private final ModelPart wing3R;
    private final ModelPart wing4L;
    private final ModelPart wing4R;
    private final ModelPart wing5L;
    private final ModelPart wing5R;

    // Animation state fields
    private int typeI;
    private float tongueOff;
    private float mouthOff;
    private float rattleOff;
    private boolean climbing;
    private boolean isResting;
    private int movInt;
    private float f6;
    private boolean nearPlayer;
    private boolean picked;
    private float limbSwing;

    public MoCModelSnake(ModelPart root) {
        // Grab each child from the baked root:
        for (int i = 0; i < BODY_PARTS; i++) {
            this.bodySnake[i] = root.getChild("bodySnake" + i);
        }
        this.head    = root.getChild("head");
        this.nose    = root.getChild("nose");
        this.lNose   = root.getChild("lNose");
        this.teethUR = root.getChild("teethUR");
        this.teethUL = root.getChild("teethUL");
        this.tongue0 = root.getChild("tongue0");
        this.tongue  = root.getChild("tongue");
        this.tongue1 = root.getChild("tongue1");
        this.tail    = root.getChild("tail");
        this.wing1L  = root.getChild("wing1L");
        this.wing1R  = root.getChild("wing1R");
        this.wing2L  = root.getChild("wing2L");
        this.wing2R  = root.getChild("wing2R");
        this.wing3L  = root.getChild("wing3L");
        this.wing3R  = root.getChild("wing3R");
        this.wing4L  = root.getChild("wing4L");
        this.wing4R  = root.getChild("wing4R");
        this.wing5L  = root.getChild("wing5L");
        this.wing5R  = root.getChild("wing5R");
    }

    /**
     * Build all 40 body segments, plus head/nose/tongue/teeth/tail/wing parts.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Each segment sits at z = flength = (((BODY_PARTS/2) - i) * -1.6F)
        float fsegm = 1F / 8F;
        float fsep = -1.6F;

        for (int i = 0; i < BODY_PARTS; i++) {
            // Determine “fport” fraction to decide width factor
            float fport = (i + 1F) / BODY_PARTS;
            float factor;
            if (fport < fsegm) {
                factor = -0.20F;       // front narrower
            } else if (fport < (fsegm * 2F)) {
                factor = -0.15F;       // front narrow
            } else if (fport < (fsegm * 4F)) {
                factor = 0.0F;         // middle
            } else if (fport < (fsegm * 6F)) {
                factor = 0.0F;         // middle
            } else if (fport < (fsegm * 7F)) {
                factor = -0.15F;       // back narrow
            } else {
                factor = -0.20F;       // back narrower
            }

            // Alternate texture‐offset column (j = 0 or 4) every other segment
            int j = (i % 2 == 0) ? 0 : 4;

            // Compute flength for segment i
            float flength = (((BODY_PARTS / 2F) - i) * fsep);

            // Create “bodySnake[i]”
            root.addOrReplaceChild(
                    "bodySnake" + i,
                    CubeListBuilder.create()
                            .texOffs(8, j)
                            .addBox(-1F, -0.5F, 0F, 2, 2, 2, new CubeDeformation(factor)),
                    PartPose.offset(0F, 23F, flength)
            );
        }

        // TAIL (extending from final segment)
        float flengthTail = (((BODY_PARTS / 2F) - (BODY_PARTS - 1)) * fsep);
        root.addOrReplaceChild(
                "tail",
                CubeListBuilder.create()
                        .texOffs(36, 0)
                        .addBox(-0.5F, 0.5F, -1.0F, 1, 1, 5),
                PartPose.offset(0F, 23F, flengthTail)
        );

        // HEAD (at flength = ((BODY_PARTS/2) * fsep))
        float flengthHead = ((BODY_PARTS / 2F) * fsep);
        root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1F, -0.5F, -2F, 2, 2, 2),
                PartPose.offset(0F, 23F, flengthHead)
        );

        // NOSE
        root.addOrReplaceChild(
                "nose",
                CubeListBuilder.create()
                        .texOffs(16, 0)
                        .addBox(-0.5F, -0.3F, -4F, 1, 1, 2),
                PartPose.offset(0F, 23F, flengthHead)
        );

        // LNOSE (bottom part)
        root.addOrReplaceChild(
                "lNose",
                CubeListBuilder.create()
                        .texOffs(22, 0)
                        .addBox(-0.5F, 0.3F, -4F, 1, 1, 2),
                PartPose.offset(0F, 23F, flengthHead)
        );

        // TEETH Upper Right
        root.addOrReplaceChild(
                "teethUR",
                CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-0.4F, 0.3F, -3.8F, 0, 1, 1),
                PartPose.offset(0F, 23F, flengthHead)
        );

        // TEETH Upper Left
        root.addOrReplaceChild(
                "teethUL",
                CubeListBuilder.create()
                        .texOffs(44, 0)
                        .addBox(0.4F, 0.3F, -3.8F, 0, 1, 1),
                PartPose.offset(0F, 23F, flengthHead)
        );

        // TONGUE strips (tongue0 = retracted, tongue = middle, tongue1 = extended)
        root.addOrReplaceChild(
                "tongue0",
                CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(-0.5F, 0.25F, -4F, 1, 0, 3),
                PartPose.offset(0F, 23F, flengthHead)
        );
        root.addOrReplaceChild(
                "tongue",
                CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(-0.5F, 0.5F, -6F, 1, 0, 3),
                PartPose.offset(0F, 23F, flengthHead)
        );
        root.addOrReplaceChild(
                "tongue1",
                CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(-0.5F, 0.5F, -5F, 1, 0, 3),
                PartPose.offset(0F, 23F, flengthHead)
        );

        // “Wing” blocks appear on a cobra (type 6) — 5 pairs of blocks along segments 1–5
        // Wing1 at segment index 1:
        float z1 = (((BODY_PARTS / 2F) - 1) * fsep);
        root.addOrReplaceChild(
                "wing1L",
                CubeListBuilder.create()
                        .texOffs(8, 4)
                        .addBox(0F, -0.5F, 0F, 2, 2, 2),
                PartPose.offset(0F, 23F, z1)
        );
        root.addOrReplaceChild(
                "wing1R",
                CubeListBuilder.create()
                        .texOffs(8, 4)
                        .mirror()
                        .addBox(-2F, -0.5F, 0F, 2, 2, 2),
                PartPose.offset(0F, 23F, z1)
        );

        // Wing2 at segment 2:
        float z2 = (((BODY_PARTS / 2F) - 2) * fsep);
        root.addOrReplaceChild(
                "wing2L",
                CubeListBuilder.create()
                        .texOffs(8, 4)
                        .addBox(0.5F, -0.5F, 0F, 2, 2, 2),
                PartPose.offset(0F, 23F, z2)
        );
        root.addOrReplaceChild(
                "wing2R",
                CubeListBuilder.create()
                        .texOffs(8, 4)
                        .mirror()
                        .addBox(-2.5F, -0.5F, 0F, 2, 2, 2),
                PartPose.offset(0F, 23F, z2)
        );

        // Wing3 at segment 3:
        float z3 = (((BODY_PARTS / 2F) - 3) * fsep);
        root.addOrReplaceChild(
                "wing3L",
                CubeListBuilder.create()
                        .texOffs(16, 4)
                        .addBox(1F, -0.5F, 0F, 2, 2, 2),
                PartPose.offset(0F, 23F, z3)
        );
        root.addOrReplaceChild(
                "wing3R",
                CubeListBuilder.create()
                        .texOffs(16, 4)
                        .mirror()
                        .addBox(-3F, -0.5F, 0F, 2, 2, 2),
                PartPose.offset(0F, 23F, z3)
        );

        // Wing4 at segment 4:
        float z4 = (((BODY_PARTS / 2F) - 4) * fsep);
        root.addOrReplaceChild(
                "wing4L",
                CubeListBuilder.create()
                        .texOffs(16, 8)
                        .addBox(0.5F, -0.5F, 0F, 2, 2, 2),
                PartPose.offset(0F, 23F, z4)
        );
        root.addOrReplaceChild(
                "wing4R",
                CubeListBuilder.create()
                        .texOffs(16, 8)
                        .mirror()
                        .addBox(-2.5F, -0.5F, 0F, 2, 2, 2),
                PartPose.offset(0F, 23F, z4)
        );

        // Wing5 at segment 5:
        float z5 = (((BODY_PARTS / 2F) - 5) * fsep);
        root.addOrReplaceChild(
                "wing5L",
                CubeListBuilder.create()
                        .texOffs(16, 8)
                        .addBox(0F, -0.5F, 0F, 2, 2, 2),
                PartPose.offset(0F, 23F, z5)
        );
        root.addOrReplaceChild(
                "wing5R",
                CubeListBuilder.create()
                        .texOffs(16, 8)
                        .mirror()
                        .addBox(-2F, -0.5F, 0F, 2, 2, 2),
                PartPose.offset(0F, 23F, z5)
        );

        return LayerDefinition.create(mesh, 64, 32);
    }

    /**
     * Copy the 1.16.5 setLivingAnimations(...) fields into our state at render time.
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
        this.typeI     = entity.getTypeMoC();
        this.tongueOff = entity.getfTongue();
        this.mouthOff  = entity.getfMouth();
        this.rattleOff = entity.getfRattle();
        this.climbing  = entity.isClimbing();
        this.isResting = entity.isResting();
        this.movInt    = entity.getMovInt();
        this.f6        = entity.bodyswing;
        this.nearPlayer= entity.getNearPlayer();
        this.picked    = entity.pickedUp();
        this.limbSwing = limbSwing;

        // HEAD pitch/yaw
        float rAX = headPitch / 57.29578F;
        float rAY = netHeadYaw / 57.29578F;
        this.head.xRot = rAX;
        this.head.yRot = rAY;

        // Distribute head's pitch into the first five segments (X‐rot only)
        this.bodySnake[0].xRot = rAX * 0.95F;
        this.bodySnake[1].xRot = rAX * 0.90F;
        this.bodySnake[2].xRot = rAX * 0.85F;
        this.bodySnake[3].xRot = rAX * 0.80F;
        this.bodySnake[4].xRot = rAX * 0.75F;

        // Tongue flick offset
        float f8 = (Mth.cos(tongueOff * 10F) / 40F);

        this.nose.xRot   = this.head.xRot - mouthOff;
        this.lNose.xRot  = this.head.xRot + mouthOff;
        this.tongue1.xRot = this.head.xRot + f8;
        this.tongue.xRot  = this.head.xRot + f8;
        this.tongue0.xRot = this.lNose.xRot;
        this.teethUR.xRot = this.head.xRot - mouthOff;
        this.teethUL.xRot = this.head.xRot - mouthOff;

        // Y‐rot distribution down first five segments
        this.bodySnake[0].yRot = rAY * 0.85F;
        this.bodySnake[1].yRot = rAY * 0.65F;
        this.bodySnake[2].yRot = rAY * 0.45F;
        this.bodySnake[3].yRot = rAY * 0.25F;
        this.bodySnake[4].yRot = rAY * 0.10F;

        // Synchronize nose/tongue orientation to head
        this.nose.yRot    = this.head.yRot;
        this.lNose.yRot   = this.head.yRot;
        this.tongue0.yRot = this.head.yRot;
        this.tongue.yRot  = this.head.yRot;
        this.tongue1.yRot = this.head.yRot;
        this.teethUR.yRot = this.head.yRot;
        this.teethUL.yRot = this.head.yRot;

        // Cobra‐“wings” (type 6) simply copy segment rotations:
        if (typeI == 6) {
            // wing1 at segment 1
            this.wing1L.xRot = this.bodySnake[1].xRot;
            this.wing1L.yRot = this.bodySnake[1].yRot;
            this.wing1R.xRot = this.bodySnake[1].xRot;
            this.wing1R.yRot = this.bodySnake[1].yRot;
            // wing2 at segment 2
            this.wing2L.xRot = this.bodySnake[2].xRot;
            this.wing2L.yRot = this.bodySnake[2].yRot;
            this.wing2R.xRot = this.bodySnake[2].xRot;
            this.wing2R.yRot = this.bodySnake[2].yRot;
            // wing3 at segment 3
            this.wing3L.xRot = this.bodySnake[3].xRot;
            this.wing3L.yRot = this.bodySnake[3].yRot;
            this.wing3R.xRot = this.bodySnake[3].xRot;
            this.wing3R.yRot = this.bodySnake[3].yRot;
            // wing4 at segment 4
            this.wing4L.xRot = this.bodySnake[4].xRot;
            this.wing4L.yRot = this.bodySnake[4].yRot;
            this.wing4R.xRot = this.bodySnake[4].xRot;
            this.wing4R.yRot = this.bodySnake[4].yRot;
            // wing5 at segment 5
            this.wing5L.xRot = this.bodySnake[4].xRot;
            this.wing5L.yRot = this.bodySnake[4].yRot;
            this.wing5R.xRot = this.bodySnake[4].xRot;
            this.wing5R.yRot = this.bodySnake[4].yRot;
        }

        // Rattlesnake tail‐lift (type 7)
        if (typeI == 7) {
            if (nearPlayer || rattleOff != 0.0F) {
                // tail pitches upward toward player
                this.tail.xRot = ((Mth.cos(netHeadYaw * 10F) * 20F) + 90F) / 57.29578F;
            } else {
                this.tail.xRot = 0.0F;
            }
        }
    }

    /**
     * Main render loop: apply the same “push/pop” transforms that 1.16.5 did per‐segment, then render each piece.
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
        // The 1.16.5 code used:
        //   float A = 0.4F; float w = 1.5F; float t = limbSwing / 2;
        //   float sidef = 0.5F * sin(w*t - 0.3F*i) - (movInt/20F)*sin(0.8F*t - 0.2F*i);
        //   sidef *= sideperf;  translate(sidef, 0, 0), then render bodySnake[i], then head/nose/teeth/tongue if i==0,
        //   then wings if type==6 & i in [1..5], then tail if type==7 & i==bodyparts-1.
        //
        // We'll replicate that exactly.

        float A = 0.4F;
        float w = 1.5F;
        float t = this.limbSwing / 2F;

        for (int i = 0; i < BODY_PARTS; i++) {
            float sideperf = 1F;
            float yOff;

            poseStack.pushPose();

            // Resting animation: nothing special done (commented out in original)
            if (this.isResting) {
                // (No active transform was applied in 1.16.5; kept as placeholder.)
            }
            // Climbing animation on the front half
            else if (this.climbing && i < BODY_PARTS / 2) {
                yOff = (i - (BODY_PARTS / 2F)) * 0.08F;
                poseStack.translate(0.0F, yOff / 3.0F, -yOff * 1.2F);
            }
            // Raise head if near player or picked up
            else if ((this.nearPlayer || this.picked) && i < BODY_PARTS / 3) {
                yOff = (i - (BODY_PARTS / 3F)) * 0.09F;
                float zOff = (i - (BODY_PARTS / 3F)) * 0.065F;
                poseStack.translate(0.0F, yOff / 1.5F, -zOff * this.f6);

                if (i < BODY_PARTS / 6) {
                    sideperf = 0.0F;
                } else {
                    sideperf = ((i - 7) / (BODY_PARTS / 3F));
                    if (sideperf > 1.0F) {
                        sideperf = 1.0F;
                    }
                }
            }

            // Raise tail of rattlesnakes when near player & not picked
            if (this.typeI == 7 && this.nearPlayer && i > (5 * BODY_PARTS / 6) && !this.picked) {
                yOff = 0.55F + ((i - BODY_PARTS) * 0.08F);
                poseStack.translate(0.0F, -yOff / 1.5F, 0.0F);
            }

            // If “picked” and segment in back half, lift it slightly
            if (this.picked && i > BODY_PARTS / 2) {
                yOff = (i - (BODY_PARTS / 2F)) * 0.08F;
                poseStack.translate(0.0F, yOff / 1.5F, -yOff);
            }

            // The main side‐to‐side wave:
            {
                float sidef = 0.5F * Mth.sin(w * t - 0.3F * i) - (movInt / 20F) * Mth.sin(0.8F * t - 0.2F * i);
                sidef *= sideperf;
                poseStack.translate(sidef, 0.0F, 0.0F);
            }

            // Render this body segment
            this.bodySnake[i].render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

            // i == 0 ⇒ also render head, nose, teeth, tongue
            if (i == 0) {
                this.head.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                this.nose.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                this.lNose.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                this.teethUR.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                this.teethUL.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

                // Tongue logic:
                if (this.tongueOff != 0.0F) {
                    if (this.mouthOff != 0.0F || this.tongueOff < 2.0F || this.tongueOff > 7.0F) {
                        this.tongue1.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                    } else {
                        this.tongue.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                    }
                } else {
                    this.tongue0.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                }
            }

            // Cobra “hood” wings (type 6), shown on segments 1..5
            if (this.typeI == 6 && this.nearPlayer) {
                if (i == 1) {
                    this.wing1L.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                    this.wing1R.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                }
                if (i == 2) {
                    this.wing2L.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                    this.wing2R.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                }
                if (i == 3) {
                    this.wing3L.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                    this.wing3R.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                }
                if (i == 4) {
                    this.wing4L.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                    this.wing4R.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                }
                if (i == 5) {
                    this.wing5L.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                    this.wing5R.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                }
            }

            // Rattlesnake tail piece on last segment if type 7
            if (i == BODY_PARTS - 1 && this.typeI == 7) {
                this.tail.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            }

            poseStack.popPose();
        }
    }
}
