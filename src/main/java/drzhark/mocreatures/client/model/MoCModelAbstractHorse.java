package drzhark.mocreatures.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class MoCModelAbstractHorse <T extends CreatureEntity> extends EntityModel<T> {

    ModelRenderer Head;
    ModelRenderer UMouth;
    ModelRenderer LMouth;
    ModelRenderer UMouth2;
    ModelRenderer LMouth2;
    ModelRenderer Unicorn;
    ModelRenderer Ear1;
    ModelRenderer Ear2;
    ModelRenderer MuleEarL;
    ModelRenderer MuleEarR;
    ModelRenderer Neck;
    ModelRenderer HeadSaddle;
    ModelRenderer Mane;

    ModelRenderer Body;
    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer TailC;

    ModelRenderer Leg1A;
    ModelRenderer Leg1B;
    ModelRenderer Leg1C;

    ModelRenderer Leg2A;
    ModelRenderer Leg2B;
    ModelRenderer Leg2C;

    ModelRenderer Leg3A;
    ModelRenderer Leg3B;
    ModelRenderer Leg3C;

    ModelRenderer Leg4A;
    ModelRenderer Leg4B;
    ModelRenderer Leg4C;

    ModelRenderer Bag1;
    ModelRenderer Bag2;

    ModelRenderer Saddle;
    ModelRenderer SaddleB;
    ModelRenderer SaddleC;

    ModelRenderer SaddleL;
    ModelRenderer SaddleL2;

    ModelRenderer SaddleR;
    ModelRenderer SaddleR2;

    ModelRenderer SaddleMouthL;
    ModelRenderer SaddleMouthR;

    ModelRenderer SaddleMouthLine;
    ModelRenderer SaddleMouthLineR;

    ModelRenderer MidWing;
    ModelRenderer InnerWing;
    ModelRenderer OuterWing;

    ModelRenderer InnerWingR;
    ModelRenderer MidWingR;
    ModelRenderer OuterWingR;

    ModelRenderer ButterflyL;
    ModelRenderer ButterflyR;

    //private float fMov1;
    //private float fMov2;
    //private boolean kneeSwitch;
    protected int type;

    protected boolean saddled;
    protected boolean rider;
    protected boolean flapwings;
    protected boolean shuffling;
    protected boolean wings;
    protected boolean eating;
    protected boolean standing;
    protected boolean moveTail;
    protected boolean floating;

    public MoCModelAbstractHorse() {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.Body = new ModelRenderer(this, 0, 34);
        this.Body.addBox(-5F, -8F, -19F, 10, 10, 24);
        this.Body.setRotationPoint(0F, 11F, 9F);

        /*
         * Tail = new ModelRenderer(this, 24, 0); Tail.addBox(-1.5F, -2F, 3F, 3,
         * 4, 14); Tail.setRotationPoint(0F, 3F, 14F); setRotation(Tail,
         * -1.134464F, 0F, 0F);
         */

        this.TailA = new ModelRenderer(this, 44, 0);
        this.TailA.addBox(-1F, -1F, 0F, 2, 2, 3);
        this.TailA.setRotationPoint(0F, 3F, 14F);
        setRotation(this.TailA, -1.134464F, 0F, 0F);

        this.TailB = new ModelRenderer(this, 38, 7);
        this.TailB.addBox(-1.5F, -2F, 3F, 3, 4, 7);
        this.TailB.setRotationPoint(0F, 3F, 14F);
        setRotation(this.TailB, -1.134464F, 0F, 0F);

        this.TailC = new ModelRenderer(this, 24, 3);
        this.TailC.addBox(-1.5F, -4.5F, 9F, 3, 4, 7);
        this.TailC.setRotationPoint(0F, 3F, 14F);
        setRotation(this.TailC, -1.40215F, 0F, 0F);

        this.Leg1A = new ModelRenderer(this, 78, 29);
        this.Leg1A.addBox(-2.5F, -2F, -2.5F, 4, 9, 5);
        this.Leg1A.setRotationPoint(4F, 9F, 11F);

        this.Leg1B = new ModelRenderer(this, 78, 43);
        this.Leg1B.addBox(-2F, 0F, -1.5F, 3, 5, 3);
        this.Leg1B.setRotationPoint(4F, 16F, 11F);

        this.Leg1C = new ModelRenderer(this, 78, 51);
        this.Leg1C.addBox(-2.5F, 5.1F, -2F, 4, 3, 4);
        this.Leg1C.setRotationPoint(4F, 16F, 11F);

        this.Leg2A = new ModelRenderer(this, 96, 29);
        this.Leg2A.addBox(-1.5F, -2F, -2.5F, 4, 9, 5);
        this.Leg2A.setRotationPoint(-4F, 9F, 11F);

        this.Leg2B = new ModelRenderer(this, 96, 43);
        this.Leg2B.addBox(-1F, 0F, -1.5F, 3, 5, 3);
        this.Leg2B.setRotationPoint(-4F, 16F, 11F);

        this.Leg2C = new ModelRenderer(this, 96, 51);
        this.Leg2C.addBox(-1.5F, 5.1F, -2F, 4, 3, 4);
        this.Leg2C.setRotationPoint(-4F, 16F, 11F);

        this.Leg3A = new ModelRenderer(this, 44, 29);
        this.Leg3A.addBox(-1.9F, -1F, -2.1F, 3, 8, 4);
        this.Leg3A.setRotationPoint(4F, 9F, -8F);

        this.Leg3B = new ModelRenderer(this, 44, 41);
        this.Leg3B.addBox(-1.9F, 0F, -1.6F, 3, 5, 3);
        this.Leg3B.setRotationPoint(4F, 16F, -8F);

        this.Leg3C = new ModelRenderer(this, 44, 51);
        this.Leg3C.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4);
        this.Leg3C.setRotationPoint(4F, 16F, -8F);

        this.Leg4A = new ModelRenderer(this, 60, 29);
        this.Leg4A.addBox(-1.1F, -1F, -2.1F, 3, 8, 4);
        this.Leg4A.setRotationPoint(-4F, 9F, -8F);

        this.Leg4B = new ModelRenderer(this, 60, 41);
        this.Leg4B.addBox(-1.1F, 0F, -1.6F, 3, 5, 3);
        this.Leg4B.setRotationPoint(-4F, 16F, -8F);

        this.Leg4C = new ModelRenderer(this, 60, 51);
        this.Leg4C.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
        this.Leg4C.setRotationPoint(-4F, 16F, -8F);

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-2.5F, -10F, -1.5F, 5, 5, 7);
        this.Head.setRotationPoint(0F, 4F, -10F);
        setRotation(this.Head, 0.5235988F, 0F, 0F);

        this.UMouth = new ModelRenderer(this, 24, 18);
        this.UMouth.addBox(-2F, -10F, -7F, 4, 3, 6);
        this.UMouth.setRotationPoint(0F, 4F, -10F);
        setRotation(this.UMouth, 0.5235988F, 0F, 0F);

        this.LMouth = new ModelRenderer(this, 24, 27);
        this.LMouth.addBox(-2F, -7F, -6.5F, 4, 2, 5);
        this.LMouth.setRotationPoint(0F, 4F, -10F);
        setRotation(this.LMouth, 0.5235988F, 0F, 0F);

        this.UMouth2 = new ModelRenderer(this, 24, 18);
        this.UMouth2.addBox(-2F, -10F, -8F, 4, 3, 6);
        this.UMouth2.setRotationPoint(0F, 4F, -10F);
        setRotation(this.UMouth2, 0.4363323F, 0F, 0F);

        this.LMouth2 = new ModelRenderer(this, 24, 27);
        this.LMouth2.addBox(-2F, -7F, -5.5F, 4, 2, 5);
        this.LMouth2.setRotationPoint(0F, 4F, -10F);
        setRotation(this.LMouth2, 0.7853982F, 0F, 0F);

        this.Unicorn = new ModelRenderer(this, 24, 0);
        this.Unicorn.addBox(-0.5F, -18F, 2F, 1, 8, 1);
        this.Unicorn.setRotationPoint(0F, 4F, -10F);
        setRotation(this.Unicorn, 0.5235988F, 0F, 0F);

        this.Ear1 = new ModelRenderer(this, 0, 0);
        this.Ear1.addBox(0.45F, -12F, 4F, 2, 3, 1);
        this.Ear1.setRotationPoint(0F, 4F, -10F);
        setRotation(this.Ear1, 0.5235988F, 0F, 0F);

        this.Ear2 = new ModelRenderer(this, 0, 0);
        this.Ear2.addBox(-2.45F, -12F, 4F, 2, 3, 1);
        this.Ear2.setRotationPoint(0F, 4F, -10F);
        setRotation(this.Ear2, 0.5235988F, 0F, 0F);

        this.MuleEarL = new ModelRenderer(this, 0, 12);
        this.MuleEarL.addBox(-2F, -16F, 4F, 2, 7, 1);
        this.MuleEarL.setRotationPoint(0F, 4F, -10F);
        setRotation(this.MuleEarL, 0.5235988F, 0F, 0.2617994F);

        this.MuleEarR = new ModelRenderer(this, 0, 12);
        this.MuleEarR.addBox(0F, -16F, 4F, 2, 7, 1);
        this.MuleEarR.setRotationPoint(0F, 4F, -10F);
        setRotation(this.MuleEarR, 0.5235988F, 0F, -0.2617994F);

        this.Neck = new ModelRenderer(this, 0, 12);
        this.Neck.addBox(-2.05F, -9.8F, -2F, 4, 14, 8);
        this.Neck.setRotationPoint(0F, 4F, -10F);
        setRotation(this.Neck, 0.5235988F, 0F, 0F);

        this.Bag1 = new ModelRenderer(this, 0, 34);
        this.Bag1.addBox(-3F, 0F, 0F, 8, 8, 3);
        this.Bag1.setRotationPoint(-7.5F, 3F, 10F);
        setRotation(this.Bag1, 0F, 1.570796F, 0F);

        this.Bag2 = new ModelRenderer(this, 0, 47);
        this.Bag2.addBox(-3F, 0F, 0F, 8, 8, 3);
        this.Bag2.setRotationPoint(4.5F, 3F, 10F);
        setRotation(this.Bag2, 0F, 1.570796F, 0F);

        this.Saddle = new ModelRenderer(this, 80, 0);
        this.Saddle.addBox(-5F, 0F, -3F, 10, 1, 8);
        this.Saddle.setRotationPoint(0F, 2F, 2F);

        this.SaddleB = new ModelRenderer(this, 106, 9);
        this.SaddleB.addBox(-1.5F, -1F, -3F, 3, 1, 2);
        this.SaddleB.setRotationPoint(0F, 2F, 2F);

        this.SaddleC = new ModelRenderer(this, 80, 9);
        this.SaddleC.addBox(-4F, -1F, 3F, 8, 1, 2);
        this.SaddleC.setRotationPoint(0F, 2F, 2F);

        this.SaddleL2 = new ModelRenderer(this, 74, 0);
        this.SaddleL2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
        this.SaddleL2.setRotationPoint(5F, 3F, 2F);

        this.SaddleL = new ModelRenderer(this, 70, 0);
        this.SaddleL.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        this.SaddleL.setRotationPoint(5F, 3F, 2F);

        this.SaddleR2 = new ModelRenderer(this, 74, 4);
        this.SaddleR2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
        this.SaddleR2.setRotationPoint(-5F, 3F, 2F);

        this.SaddleR = new ModelRenderer(this, 80, 0);
        this.SaddleR.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        this.SaddleR.setRotationPoint(-5F, 3F, 2F);

        this.SaddleMouthL = new ModelRenderer(this, 74, 13);
        this.SaddleMouthL.addBox(1.5F, -8F, -4F, 1, 2, 2);
        this.SaddleMouthL.setRotationPoint(0F, 4F, -10F);
        setRotation(this.SaddleMouthL, 0.5235988F, 0F, 0F);

        this.SaddleMouthR = new ModelRenderer(this, 74, 13);
        this.SaddleMouthR.addBox(-2.5F, -8F, -4F, 1, 2, 2);
        this.SaddleMouthR.setRotationPoint(0F, 4F, -10F);
        setRotation(this.SaddleMouthR, 0.5235988F, 0F, 0F);

        this.SaddleMouthLine = new ModelRenderer(this, 44, 10);
        this.SaddleMouthLine.addBox(2.6F, -6F, -6F, 0, 3, 16);
        this.SaddleMouthLine.setRotationPoint(0F, 4F, -10F);

        this.SaddleMouthLineR = new ModelRenderer(this, 44, 5);
        this.SaddleMouthLineR.addBox(-2.6F, -6F, -6F, 0, 3, 16);
        this.SaddleMouthLineR.setRotationPoint(0F, 4F, -10F);

        this.Mane = new ModelRenderer(this, 58, 0);
        this.Mane.addBox(-1F, -11.5F, 5F, 2, 16, 4);
        this.Mane.setRotationPoint(0F, 4F, -10F);
        //Mane.addBox(-1F, -9.5F, 6F, 2, 16, 4);
        //Mane.setRotationPoint(0F, 3F, -12F);
        setRotation(this.Mane, 0.5235988F, 0F, 0F);

        this.HeadSaddle = new ModelRenderer(this, 80, 12);
        this.HeadSaddle.addBox(-2.5F, -10.1F, -7F, 5, 5, 12, 0.2F);
        this.HeadSaddle.setRotationPoint(0F, 4F, -10F);
        setRotation(this.HeadSaddle, 0.5235988F, 0F, 0F);

        this.MidWing = new ModelRenderer(this, 82, 68);
        this.MidWing.addBox(1F, 0.1F, 1F, 12, 2, 11);
        this.MidWing.setRotationPoint(5F, 3F, -6F);
        setRotation(this.MidWing, 0F, 0.0872665F, 0F);

        this.InnerWing = new ModelRenderer(this, 0, 96);
        this.InnerWing.addBox(0F, 0F, 0F, 7, 2, 11);
        this.InnerWing.setRotationPoint(5F, 3F, -6F);
        setRotation(this.InnerWing, 0F, -0.3490659F, 0F);

        this.OuterWing = new ModelRenderer(this, 0, 68);
        this.OuterWing.addBox(0F, 0F, 0F, 22, 2, 11);
        this.OuterWing.setRotationPoint(17F, 3F, -6F);
        setRotation(this.OuterWing, 0F, -0.3228859F, 0F);

        this.InnerWingR = new ModelRenderer(this, 0, 110);
        this.InnerWingR.addBox(-7F, 0F, 0F, 7, 2, 11);
        this.InnerWingR.setRotationPoint(-5F, 3F, -6F);
        setRotation(this.InnerWingR, 0F, 0.3490659F, 0F);

        this.MidWingR = new ModelRenderer(this, 82, 82);
        this.MidWingR.addBox(-13F, 0.1F, 1F, 12, 2, 11);
        this.MidWingR.setRotationPoint(-5F, 3F, -6F);
        setRotation(this.MidWingR, 0F, -0.0872665F, 0F);

        this.OuterWingR = new ModelRenderer(this, 0, 82);
        this.OuterWingR.addBox(-22F, 0F, 0F, 22, 2, 11);
        this.OuterWingR.setRotationPoint(-17F, 3F, -6F);
        setRotation(this.OuterWingR, 0F, 0.3228859F, 0F);

        this.ButterflyL = new ModelRenderer(this, 0, 98);
        this.ButterflyL.addBox(-1F, 0F, -14F, 26, 0, 30);
        this.ButterflyL.setRotationPoint(4.5F, 3F, -2F);
        setRotation(this.ButterflyL, 0F, 0F, -0.78539F);

        this.ButterflyR = new ModelRenderer(this, 0, 68);
        this.ButterflyR.addBox(-25F, 0F, -14F, 26, 0, 30);
        this.ButterflyR.setRotationPoint(-4.5F, 3F, -2F);
        setRotation(this.ButterflyR, 0F, 0F, 0.78539F);

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float RLegXRot = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 0.8F * limbSwingAmount;
        float LLegXRot = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;
        float HeadXRot = (headPitch / 57.29578F);
        if (netHeadYaw > 20F) {
            netHeadYaw = 20F;
        }
        if (netHeadYaw < -20F) {
            netHeadYaw = -20F;
        }

        /*
         * limbSwing = distance walked limbSwingAmount = speed 0 - 1 ageInTicks = timer
         */

        if (shuffling) {
            HeadXRot = HeadXRot + (MathHelper.cos(ageInTicks * 0.4F) * 0.15F);
        } else if (limbSwingAmount > 0.2F && !floating) {
            HeadXRot = HeadXRot + (MathHelper.cos(limbSwing * 0.4F) * 0.15F * limbSwingAmount);
        }

        this.Head.rotationPointY = 4.0F;
        this.Head.rotationPointZ = -10F;
        this.Head.rotateAngleX = 0.5235988F + (HeadXRot);
        this.Head.rotateAngleY = (netHeadYaw / 57.29578F);//fixes SMP bug
        this.TailA.rotationPointY = 3F;
        this.TailB.rotationPointZ = 14F;
        this.Bag2.rotationPointY = 3F;
        this.Bag2.rotationPointZ = 10F;
        this.Body.rotateAngleX = 0F;

        if (standing && !shuffling) //standing on hind legs
        {
            this.Head.rotationPointY = -6F;
            this.Head.rotationPointZ = -1F;
            this.Head.rotateAngleX = (15 / 57.29578F) + (HeadXRot);
            //120 degrees
            this.Head.rotateAngleY = (netHeadYaw / 57.29578F);
            this.TailA.rotationPointY = 9F;
            this.TailB.rotationPointZ = 18F;
            this.Bag2.rotationPointY = 5.5F;
            this.Bag2.rotationPointZ = 15F;
            this.Body.rotateAngleX = -45 / 57.29578F;

        } else if (eating && !shuffling)//neck down
        {
            this.Head.rotationPointY = 11.0F; //new lower position
            this.Head.rotationPointZ = -10F;
            this.Head.rotateAngleX = 2.18166F;//120 degrees
            this.Head.rotateAngleY = 0.0F;//don't twist your neck if eating

        }

        this.Ear1.rotationPointY = this.Head.rotationPointY;
        this.Ear2.rotationPointY = this.Head.rotationPointY;
        this.MuleEarL.rotationPointY = this.Head.rotationPointY;
        this.MuleEarR.rotationPointY = this.Head.rotationPointY;
        this.Neck.rotationPointY = this.Head.rotationPointY;
        this.UMouth.rotationPointY = this.Head.rotationPointY;
        this.UMouth2.rotationPointY = this.Head.rotationPointY;
        this.LMouth.rotationPointY = this.Head.rotationPointY;
        this.LMouth2.rotationPointY = this.Head.rotationPointY;
        this.Mane.rotationPointY = this.Head.rotationPointY;
        this.Unicorn.rotationPointY = this.Head.rotationPointY;

        this.Ear1.rotationPointZ = this.Head.rotationPointZ;
        this.Ear2.rotationPointZ = this.Head.rotationPointZ;
        this.MuleEarL.rotationPointZ = this.Head.rotationPointZ;
        this.MuleEarR.rotationPointZ = this.Head.rotationPointZ;
        this.Neck.rotationPointZ = this.Head.rotationPointZ;
        this.UMouth.rotationPointZ = this.Head.rotationPointZ;
        this.UMouth2.rotationPointZ = this.Head.rotationPointZ;
        this.LMouth.rotationPointZ = this.Head.rotationPointZ;
        this.LMouth2.rotationPointZ = this.Head.rotationPointZ;
        this.Mane.rotationPointZ = this.Head.rotationPointZ;
        this.Unicorn.rotationPointZ = this.Head.rotationPointZ;

        this.Ear1.rotateAngleX = this.Head.rotateAngleX;
        this.Ear2.rotateAngleX = this.Head.rotateAngleX;
        this.MuleEarL.rotateAngleX = this.Head.rotateAngleX;
        this.MuleEarR.rotateAngleX = this.Head.rotateAngleX;
        this.Neck.rotateAngleX = this.Head.rotateAngleX;
        this.UMouth.rotateAngleX = this.Head.rotateAngleX;
        this.UMouth2.rotateAngleX = this.Head.rotateAngleX - 0.0872664F;
        this.LMouth.rotateAngleX = this.Head.rotateAngleX;
        this.LMouth2.rotateAngleX = this.Head.rotateAngleX + 0.261799F;
        this.Mane.rotateAngleX = this.Head.rotateAngleX;
        this.Unicorn.rotateAngleX = this.Head.rotateAngleX;

        this.Ear1.rotateAngleY = this.Head.rotateAngleY;
        this.Ear2.rotateAngleY = this.Head.rotateAngleY;
        this.MuleEarL.rotateAngleY = this.Head.rotateAngleY;
        this.MuleEarR.rotateAngleY = this.Head.rotateAngleY;
        this.Neck.rotateAngleY = this.Head.rotateAngleY;
        this.UMouth.rotateAngleY = this.Head.rotateAngleY;
        this.LMouth.rotateAngleY = this.Head.rotateAngleY;
        this.UMouth2.rotateAngleY = this.Head.rotateAngleY;
        this.LMouth2.rotateAngleY = this.Head.rotateAngleY;
        this.Mane.rotateAngleY = this.Head.rotateAngleY;
        this.Unicorn.rotateAngleY = this.Head.rotateAngleY;

        //(if chested)
        this.Bag1.rotateAngleX = RLegXRot / 5F;//(MathHelper.cos(limbSwing * 0.6662F) * 1.4F * ageInTicks) / 10F;
        this.Bag2.rotateAngleX = -RLegXRot / 5F;//(MathHelper.cos(limbSwing * 0.6662F) * 1.4F * ageInTicks) / 10F;

        if (wings) {
            this.InnerWing.rotateAngleX = this.Body.rotateAngleX;
            this.MidWing.rotateAngleX = this.Body.rotateAngleX;
            this.OuterWing.rotateAngleX = this.Body.rotateAngleX;
            this.InnerWingR.rotateAngleX = this.Body.rotateAngleX;
            this.MidWingR.rotateAngleX = this.Body.rotateAngleX;
            this.OuterWingR.rotateAngleX = this.Body.rotateAngleX;

            if (standing) {
                this.InnerWing.rotationPointY = -5F;
                this.InnerWing.rotationPointZ = 4F;
            } else {
                this.InnerWing.rotationPointY = 3F;
                this.InnerWing.rotationPointZ = -6F;
            }

            /*
             * flapping wings or cruising. IF flapping wings, move up and down.
             * if cruising, movement depends on speed
             */
            float WingRot;
            if (flapwings) {
                WingRot = MathHelper.cos((ageInTicks * 0.3F) + 3.141593F) * 1.2F;// * limbSwingAmount;
            } else
            //cruising
            {
                //WingRot = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.2F * limbSwingAmount;
                WingRot = MathHelper.cos((limbSwing * 0.5F)) * 0.1F;//* 1.2F * limbSwingAmount;
            }

            //float WingRot = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * limbSwingAmount * 1.2F;
            //InnerWing.setRotationPoint(5F, 3F, -6F);
            //setRotation(InnerWing, 0F, -0.3490659F, 0F);
            //X dist = 12
            //OuterWing.setRotationPoint(17F, 3F, -6F);
            //setRotation(OuterWing, 0F, -0.3228859F, 0F);

            if (floating) {
                this.OuterWing.rotateAngleY = -0.3228859F + (WingRot / 2F);
                this.OuterWingR.rotateAngleY = 0.3228859F - (WingRot / 2F);

            } else {
                WingRot = 60 / 57.29578F;//0.7854F;
                this.OuterWing.rotateAngleY = -90 / 57.29578F;//-1.396F;
                this.OuterWingR.rotateAngleY = 90 / 57.29578F;//1.396F;
            }

            this.InnerWingR.rotationPointY = this.InnerWing.rotationPointY;
            this.InnerWingR.rotationPointZ = this.InnerWing.rotationPointZ;

            //OuterWing.rotationPointX = InnerWing.rotationPointX + (MathHelper.cos(WingRot)*12F);
            //the rotation point X rotates depending on the cos of rotation times the distance of the other block:
            //cos (WingRot) * 12F
            //the rotation PointX of InnerWing = 5
            //the rotation PointX of OuterWing = 17
            //the difference = 12.
            // for the rotation point Y, sin is used instead.
            //OuterWing.rotationPointX = InnerWing.rotationPointX + (MathHelper.cos(WingRot)*12F);
            //OuterWing.rotationPointY = InnerWing.rotationPointY + (MathHelper.sin(WingRot)*12F);

            this.OuterWing.rotationPointX = this.InnerWing.rotationPointX + (MathHelper.cos(WingRot) * 12F);
            this.OuterWingR.rotationPointX = this.InnerWingR.rotationPointX - (MathHelper.cos(WingRot) * 12F);

            this.MidWing.rotationPointY = this.InnerWing.rotationPointY;
            this.MidWingR.rotationPointY = this.InnerWing.rotationPointY;
            this.OuterWing.rotationPointY = this.InnerWing.rotationPointY + (MathHelper.sin(WingRot) * 12F);
            this.OuterWingR.rotationPointY = this.InnerWingR.rotationPointY + (MathHelper.sin(WingRot) * 12F);

            this.MidWing.rotationPointZ = this.InnerWing.rotationPointZ;
            this.MidWingR.rotationPointZ = this.InnerWing.rotationPointZ;
            this.OuterWing.rotationPointZ = this.InnerWing.rotationPointZ;
            this.OuterWingR.rotationPointZ = this.InnerWing.rotationPointZ;

            this.MidWing.rotateAngleZ = WingRot;
            this.InnerWing.rotateAngleZ = WingRot;
            this.OuterWing.rotateAngleZ = WingRot;

            this.InnerWingR.rotateAngleZ = -WingRot;
            this.MidWingR.rotateAngleZ = -WingRot;
            this.OuterWingR.rotateAngleZ = -WingRot;

            //45deg = 0.7854F
            //1.396 (80degrees folded)
            /*
             * //rear left. -4X(ignored), 9Y, 11Z the distance is 7Y
             * Leg1B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) +
             * LLegXRot )*7F); Leg1B.rotationPointZ = 11F +
             * (MathHelper.cos((270/ 57.29578F) + LLegXRot )*7F); //rear right
             * Leg2B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) +
             * RLegXRot )*7F); Leg2B.rotationPointZ = 11F +
             * (MathHelper.cos((270/ 57.29578F) + RLegXRot )*7F); //front left
             * 4X(ign), 9Y, -8Z, the distance is again 7Y Leg3B.rotationPointY =
             * 9F + (MathHelper.sin((90/ 57.29578F) + RLegXRot )*7F);
             * Leg3B.rotationPointZ = -8F + (MathHelper.cos((270/ 57.29578F) +
             * RLegXRot )*7F); //front right -4X(ign), 9Y, -8Z, the distance is
             * again 7Y Leg4B.rotationPointY = 9F + (MathHelper.sin((90/
             * 57.29578F) + LLegXRot )*7F); Leg4B.rotationPointZ = -8F +
             * (MathHelper.cos((270/ 57.29578F) + LLegXRot )*7F);
             */

        }

        if (type > 44 && type < 60 || type == 21) //butterfly horses or ghost horse
        {
            /*
             * buttefly to have two / 3 movs: 1 slow movement when idle on
             * ground has to be random from closing up to horizontal 2 fast wing
             * flapping flying movement, short range close to 0 degree RLegXRot
             * = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 0.8F * limbSwingAmount;
             */

            /*
             * limbSwing = distance walked limbSwingAmount = speed 0 - 1 ageInTicks = timer
             */

            float f2a = ageInTicks % 100F;
            float WingRot = 0F;

            if (type != 21) {
                //for butterfly horses
                if (flapwings) //when user hits space or randomly
                {
                    WingRot = MathHelper.cos((ageInTicks * 0.9F)) * 0.9F;

                } else
                //default movement
                {
                    if (floating) //cruising
                    {
                        WingRot = MathHelper.cos((ageInTicks * 0.6662F)) * 0.5F;
                    } else {
                        if (f2a > 40 & f2a < 60) //random movement
                        {
                            WingRot = MathHelper.cos((ageInTicks * 0.15F)) * 1.20F;
                        }
                    }

                }
            } else
            //for ghost horse
            {
                WingRot = MathHelper.cos((ageInTicks * 0.1F));//* 0.2F;
            }

            //from regular horse
            /*
             * if (flapwings) { WingRot = MathHelper.cos((ageInTicks * 0.3F) +
             * 3.141593F) * 1.2F;// * limbSwingAmount; }else //cruising { //WingRot =
             * MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.2F * limbSwingAmount; WingRot =
             * MathHelper.cos((limbSwing * 0.5F)) *0.1F ;//* 1.2F * limbSwingAmount; }
             */

            /*
             * this part is needed for position and angle of the butterfly wings
             * and ghost horse wings
             */
            if (standing) {
                this.ButterflyL.rotationPointY = -2.5F;
                this.ButterflyL.rotationPointZ = 6.5F;
            } else {
                this.ButterflyL.rotationPointY = 3F;
                this.ButterflyL.rotationPointZ = -2F;
            }

            this.ButterflyR.rotationPointY = this.ButterflyL.rotationPointY;
            this.ButterflyR.rotationPointZ = this.ButterflyL.rotationPointZ;
            this.ButterflyL.rotateAngleX = this.Body.rotateAngleX;
            this.ButterflyR.rotateAngleX = this.Body.rotateAngleX;

            //this to be added for the ghost or adjusted
            //ButterflyL.rotateAngleZ = -0.52359F + RLegXRot;
            //ButterflyR.rotateAngleZ = 0.52359F -RLegXRot;

            float baseAngle = 0.52359F;
            if (type == 21) {
                baseAngle = 0F;
            }
            this.ButterflyL.rotateAngleZ = -baseAngle + WingRot;
            this.ButterflyR.rotateAngleZ = baseAngle - WingRot;
        }

        /*
         * knee joints Leg1 and Leg4 use LLegXRot Leg2 and Leg3 use RLegXRot
         */
        //RLegXRot = 45/57.29578F;
        //fMov1 = RLegXRot;
        float RLegXRotB = RLegXRot;
        float LLegXRotB = LLegXRot;
        float RLegXRotC = RLegXRot;
        float LLegXRotC = LLegXRot;

        if (floating) {
            RLegXRot = (15 / 57.29578F);
            LLegXRot = RLegXRot;
            RLegXRotB = (45 / 57.29578F);
            RLegXRotC = RLegXRotB;
            LLegXRotB = RLegXRotB;
            LLegXRotC = RLegXRotB;
        }

        if (standing) {
            this.Leg3A.rotationPointY = -2F;
            this.Leg3A.rotationPointZ = -2F;
            this.Leg4A.rotationPointY = this.Leg3A.rotationPointY;
            this.Leg4A.rotationPointZ = this.Leg3A.rotationPointZ;

            RLegXRot = (-60 / 57.29578F) + MathHelper.cos((ageInTicks * 0.4F) + 3.141593F);
            LLegXRot = (-60 / 57.29578F) + MathHelper.cos(ageInTicks * 0.4F);

            RLegXRotB = (45 / 57.29578F);
            LLegXRotB = RLegXRotB;

            RLegXRotC = (-15 / 57.29578F);
            LLegXRotC = (15 / 57.29578F);

            this.Leg3B.rotationPointY = this.Leg3A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            this.Leg3B.rotationPointZ = this.Leg3A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
            this.Leg4B.rotationPointY = this.Leg4A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            this.Leg4B.rotationPointZ = this.Leg4A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            this.Leg1B.rotationPointY = this.Leg1A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRotC) * 7F);
            this.Leg1B.rotationPointZ = this.Leg1A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRotC) * 7F);

            //rear right
            this.Leg2B.rotationPointY = this.Leg1B.rotationPointY;// Leg2A.rotationPointY + (MathHelper.sin((90/ 57.29578F) + RLegXRotC )*7F);
            this.Leg2B.rotationPointZ = this.Leg1B.rotationPointZ;//Leg2A.rotationPointZ + (MathHelper.cos((270/ 57.29578F) + RLegXRotC )*7F);

            this.Leg1A.rotateAngleX = RLegXRotC;
            this.Leg1B.rotateAngleX = LLegXRotC;
            this.Leg1C.rotateAngleX = this.Leg1B.rotateAngleX;

            this.Leg2A.rotateAngleX = RLegXRotC;
            this.Leg2B.rotateAngleX = LLegXRotC;
            this.Leg2C.rotateAngleX = this.Leg2B.rotateAngleX;
        } else
        //not standing
        {
            this.Leg3A.rotationPointY = 9F;
            this.Leg3A.rotationPointZ = -8F;
            this.Leg4A.rotationPointY = this.Leg3A.rotationPointY;
            this.Leg4A.rotationPointZ = this.Leg3A.rotationPointZ;

            if (!floating && limbSwingAmount > 0.2F) {

                float RLegXRot2 = MathHelper.cos(((limbSwing + 0.1F) * 0.6662F) + 3.141593F) * 0.8F * limbSwingAmount;
                float LLegXRot2 = MathHelper.cos((limbSwing + 0.1F) * 0.6662F) * 0.8F * limbSwingAmount;
                if (RLegXRot > RLegXRot2) // - - >
                {
                    RLegXRotB = RLegXRot + (55 / 57.29578F);
                    //LLegXRotB = LLegXRot + (55/57.29578F);

                }
                if (RLegXRot < RLegXRot2) // < - -
                {
                    RLegXRotC = RLegXRot + (15 / 57.29578F);
                    //LLegXRotC = LLegXRot - (15/57.29578F);

                }
                if (LLegXRot > LLegXRot2) // - - >
                {
                    LLegXRotB = LLegXRot + (55 / 57.29578F);
                }
                if (LLegXRot < LLegXRot2) // < - -
                {
                    LLegXRotC = LLegXRot + (15 / 57.29578F);
                }

            }

            this.Leg1B.rotationPointY = this.Leg1A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            this.Leg1B.rotationPointZ = this.Leg1A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            //rear right
            this.Leg2B.rotationPointY = this.Leg2A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            this.Leg2B.rotationPointZ = this.Leg2A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front left 4X(ign), 9Y, -8Z, the distance is again 7Y
            this.Leg3B.rotationPointY = this.Leg3A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            this.Leg3B.rotationPointZ = this.Leg3A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
            this.Leg4B.rotationPointY = this.Leg4A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            this.Leg4B.rotationPointZ = this.Leg4A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            this.Leg1A.rotateAngleX = LLegXRot;
            this.Leg1B.rotateAngleX = LLegXRotC;
            this.Leg1C.rotateAngleX = LLegXRotC;

            this.Leg2A.rotateAngleX = RLegXRot;
            this.Leg2B.rotateAngleX = RLegXRotC;
            this.Leg2C.rotateAngleX = RLegXRotC;

        }
        this.Leg3A.rotateAngleX = RLegXRot;
        this.Leg3B.rotateAngleX = RLegXRotB;
        this.Leg3C.rotateAngleX = RLegXRotB;
        this.Leg4A.rotateAngleX = LLegXRot;
        this.Leg4B.rotateAngleX = LLegXRotB;
        this.Leg4C.rotateAngleX = LLegXRotB;

        if (type == 60 && shuffling) {
            this.Leg3A.rotationPointY = 9F;
            this.Leg3A.rotationPointZ = -8F;
            this.Leg4A.rotationPointY = this.Leg3A.rotationPointY;
            this.Leg4A.rotationPointZ = this.Leg3A.rotationPointZ;

            if (!floating)//&& limbSwingAmount > 0.2F)
            {

                //float RLegXRot2 = MathHelper.cos(ageInTicks * 0.4F);//MathHelper.cos(((limbSwing+0.1F) * 0.6662F) + 3.141593F) * 1.4F * limbSwingAmount;
                RLegXRot = MathHelper.cos(ageInTicks * 0.4F);
                if (RLegXRot > 0.1F) {
                    RLegXRot = 0.3F;
                }
                //if (RLegXRot < -0.5F) RLegXRotB = RLegXRot + (45/57.29578F);
                LLegXRot = MathHelper.cos((ageInTicks * 0.4F) + 3.141593F);
                if (LLegXRot > 0.1F) {
                    LLegXRot = 0.3F;
                }

                /*
                 * if (RLegXRot > RLegXRot2) // - - > { RLegXRotB = RLegXRot +
                 * (55/57.29578F); LLegXRotB = LLegXRot + (55/57.29578F); } if
                 * (RLegXRot < RLegXRot2) // < - - { RLegXRotC = RLegXRot -
                 * (15/57.29578F); LLegXRotC = LLegXRot - (15/57.29578F); }
                 */

                /*
                 * float RLegXRot2 = MathHelper.cos(((limbSwing+0.1F) * 0.6662F) +
                 * 3.141593F) * 1.4F * limbSwingAmount; if (RLegXRot > RLegXRot2) // - - > {
                 * RLegXRotB = RLegXRot + (55/57.29578F); LLegXRotB = LLegXRot +
                 * (55/57.29578F); } if (RLegXRot < RLegXRot2) // < - - {
                 * RLegXRotC = RLegXRot - (15/57.29578F); LLegXRotC = LLegXRot -
                 * (15/57.29578F); }
                 */
            }

            this.Leg1B.rotationPointY = this.Leg1A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            this.Leg1B.rotationPointZ = this.Leg1A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            //rear right
            this.Leg2B.rotationPointY = this.Leg2A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            this.Leg2B.rotationPointZ = this.Leg2A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front left 4X(ign), 9Y, -8Z, the distance is again 7Y
            this.Leg3B.rotationPointY = this.Leg3A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            this.Leg3B.rotationPointZ = this.Leg3A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
            this.Leg4B.rotationPointY = this.Leg4A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            this.Leg4B.rotationPointZ = this.Leg4A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            this.Leg1A.rotateAngleX = LLegXRot;// + rand.nextFloat();
            this.Leg1B.rotateAngleX = LLegXRotB;
            this.Leg1C.rotateAngleX = LLegXRotB;

            this.Leg3A.rotateAngleX = LLegXRot;
            this.Leg3B.rotateAngleX = LLegXRotB;
            this.Leg3C.rotateAngleX = LLegXRotB;

            this.Leg2A.rotateAngleX = RLegXRot;
            this.Leg2B.rotateAngleX = RLegXRotB;
            this.Leg2C.rotateAngleX = RLegXRotB;

            this.Leg4A.rotateAngleX = RLegXRot;
            this.Leg4B.rotateAngleX = RLegXRotB;
            this.Leg4C.rotateAngleX = RLegXRotB;

        }

        this.Leg1C.rotationPointY = this.Leg1B.rotationPointY;
        this.Leg1C.rotationPointZ = this.Leg1B.rotationPointZ;
        this.Leg2C.rotationPointY = this.Leg2B.rotationPointY;
        this.Leg2C.rotationPointZ = this.Leg2B.rotationPointZ;
        this.Leg3C.rotationPointY = this.Leg3B.rotationPointY;
        this.Leg3C.rotationPointZ = this.Leg3B.rotationPointZ;
        this.Leg4C.rotationPointY = this.Leg4B.rotationPointY;
        this.Leg4C.rotationPointZ = this.Leg4B.rotationPointZ;

        /*
         * //rear left. -4X(ignored), 9Y, 11Z the distance is 7Y
         * Leg1B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) +
         * LLegXRot )*7F); Leg1B.rotationPointZ = 11F + (MathHelper.cos((270/
         * 57.29578F) + LLegXRot )*7F); //rear right Leg2B.rotationPointY = 9F +
         * (MathHelper.sin((90/ 57.29578F) + RLegXRot )*7F);
         * Leg2B.rotationPointZ = 11F + (MathHelper.cos((270/ 57.29578F) +
         * RLegXRot )*7F); //front left 4X(ign), 9Y, -8Z, the distance is again
         * 7Y Leg3B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) +
         * RLegXRot )*7F); Leg3B.rotationPointZ = -8F + (MathHelper.cos((270/
         * 57.29578F) + RLegXRot )*7F); //front right -4X(ign), 9Y, -8Z, the
         * distance is again 7Y Leg4B.rotationPointY = 9F + (MathHelper.sin((90/
         * 57.29578F) + LLegXRot )*7F); Leg4B.rotationPointZ = -8F +
         * (MathHelper.cos((270/ 57.29578F) + LLegXRot )*7F);
         */

        if (saddled) {

            if (standing) {
                this.Saddle.rotationPointY = 0.5F;
                this.Saddle.rotationPointZ = 11F;

            } else {
                this.Saddle.rotationPointY = 2F;
                this.Saddle.rotationPointZ = 2F;

            }

            this.SaddleB.rotationPointY = this.Saddle.rotationPointY;
            this.SaddleC.rotationPointY = this.Saddle.rotationPointY;
            this.SaddleL.rotationPointY = this.Saddle.rotationPointY;
            this.SaddleR.rotationPointY = this.Saddle.rotationPointY;
            this.SaddleL2.rotationPointY = this.Saddle.rotationPointY;
            this.SaddleR2.rotationPointY = this.Saddle.rotationPointY;
            this.Bag1.rotationPointY = this.Bag2.rotationPointY;

            this.SaddleB.rotationPointZ = this.Saddle.rotationPointZ;
            this.SaddleC.rotationPointZ = this.Saddle.rotationPointZ;
            this.SaddleL.rotationPointZ = this.Saddle.rotationPointZ;
            this.SaddleR.rotationPointZ = this.Saddle.rotationPointZ;
            this.SaddleL2.rotationPointZ = this.Saddle.rotationPointZ;
            this.SaddleR2.rotationPointZ = this.Saddle.rotationPointZ;
            this.Bag1.rotationPointZ = this.Bag2.rotationPointZ;

            this.Saddle.rotateAngleX = this.Body.rotateAngleX;
            this.SaddleB.rotateAngleX = this.Body.rotateAngleX;
            this.SaddleC.rotateAngleX = this.Body.rotateAngleX;

            this.SaddleMouthLine.rotationPointY = this.Head.rotationPointY;
            this.SaddleMouthLineR.rotationPointY = this.Head.rotationPointY;
            this.HeadSaddle.rotationPointY = this.Head.rotationPointY;
            this.SaddleMouthL.rotationPointY = this.Head.rotationPointY;
            this.SaddleMouthR.rotationPointY = this.Head.rotationPointY;

            this.SaddleMouthLine.rotationPointZ = this.Head.rotationPointZ;
            this.SaddleMouthLineR.rotationPointZ = this.Head.rotationPointZ;
            this.HeadSaddle.rotationPointZ = this.Head.rotationPointZ;
            this.SaddleMouthL.rotationPointZ = this.Head.rotationPointZ;
            this.SaddleMouthR.rotationPointZ = this.Head.rotationPointZ;

            this.SaddleMouthLine.rotateAngleX = HeadXRot;
            this.SaddleMouthLineR.rotateAngleX = HeadXRot;
            this.HeadSaddle.rotateAngleX = this.Head.rotateAngleX;
            this.SaddleMouthL.rotateAngleX = this.Head.rotateAngleX;
            this.SaddleMouthR.rotateAngleX = this.Head.rotateAngleX;
            this.HeadSaddle.rotateAngleY = this.Head.rotateAngleY;
            this.SaddleMouthL.rotateAngleY = this.Head.rotateAngleY;
            this.SaddleMouthLine.rotateAngleY = this.Head.rotateAngleY;
            this.SaddleMouthR.rotateAngleY = this.Head.rotateAngleY;
            this.SaddleMouthLineR.rotateAngleY = this.Head.rotateAngleY;

            if (rider) {
                this.SaddleL.rotateAngleX = -60 / 57.29578F;
                this.SaddleL2.rotateAngleX = -60 / 57.29578F;
                this.SaddleR.rotateAngleX = -60 / 57.29578F;
                this.SaddleR2.rotateAngleX = -60 / 57.29578F;

                this.SaddleL.rotateAngleZ = 0F;
                this.SaddleL2.rotateAngleZ = 0F;
                this.SaddleR.rotateAngleZ = 0F;
                this.SaddleR2.rotateAngleZ = 0F;
            } else {
                this.SaddleL.rotateAngleX = RLegXRot / 3F;
                this.SaddleL2.rotateAngleX = RLegXRot / 3F;
                this.SaddleR.rotateAngleX = RLegXRot / 3F;
                this.SaddleR2.rotateAngleX = RLegXRot / 3F;

                this.SaddleL.rotateAngleZ = RLegXRot / 5F;
                this.SaddleL2.rotateAngleZ = RLegXRot / 5F;
                this.SaddleR.rotateAngleZ = -RLegXRot / 5F;
                this.SaddleR2.rotateAngleZ = -RLegXRot / 5F;
            }
        }

        //limbSwingAmount = movement speed!
        //ageInTicks = timer!

        float tailMov = -1.3089F + (limbSwingAmount * 1.5F);
        if (tailMov > 0) {
            tailMov = 0;
        }

        if (moveTail) {
            this.TailA.rotateAngleY = MathHelper.cos(ageInTicks * 0.7F);
            tailMov = 0;
        } else {
            this.TailA.rotateAngleY = 0F;
        }
        this.TailB.rotateAngleY = this.TailA.rotateAngleY;
        this.TailC.rotateAngleY = this.TailA.rotateAngleY;

        this.TailB.rotationPointY = this.TailA.rotationPointY;
        this.TailC.rotationPointY = this.TailA.rotationPointY;
        this.TailB.rotationPointZ = this.TailA.rotationPointZ;
        this.TailC.rotationPointZ = this.TailA.rotationPointZ;

        this.TailA.rotateAngleX = tailMov;//-1.3089F+(limbSwingAmount*1.5F);
        this.TailB.rotateAngleX = tailMov;//-1.3089F+(limbSwingAmount*1.5F);
        this.TailC.rotateAngleX = -0.2618F + tailMov;//-1.5707F -tailMov;
    }
}
