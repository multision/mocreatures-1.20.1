/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelWyvern<T extends MoCEntityWyvern> extends EntityModel<T> {

    private final float radianF = 57.29578F;
    ModelRenderer back4;
    ModelRenderer back3;
    ModelRenderer back2;
    ModelRenderer back1;
    ModelRenderer Tail;
    ModelRenderer tail1;
    ModelRenderer tail2;
    ModelRenderer tail3;
    ModelRenderer tail4;
    ModelRenderer tail5;
    ModelRenderer chest;
    ModelRenderer headplate;
    ModelRenderer neckplate3;
    ModelRenderer neck3;
    ModelRenderer neckplate2;
    ModelRenderer neck2;
    ModelRenderer neckplate1;
    ModelRenderer neck1;
    ModelRenderer rightupleg;
    ModelRenderer rightmidleg;
    ModelRenderer rightlowleg;
    ModelRenderer rightfoot;
    ModelRenderer leftupleg;
    ModelRenderer leftmidleg;
    ModelRenderer leftlowleg;
    ModelRenderer leftupjaw;
    ModelRenderer rightupjaw;
    ModelRenderer righteyesock;
    ModelRenderer lefteyesock;
    ModelRenderer beak;
    ModelRenderer snout;
    ModelRenderer torso;
    ModelRenderer rightshoulder;
    ModelRenderer leftshoulder;
    ModelRenderer leftfoot;
    ModelRenderer righttoe3;
    ModelRenderer righttoe2;
    ModelRenderer righttoe1;
    ModelRenderer rightclaw1;
    ModelRenderer rightclaw2;
    ModelRenderer rightclaw3;
    ModelRenderer lefttoe3;
    ModelRenderer lefttoe2;
    ModelRenderer lefttoe1;
    ModelRenderer leftclaw1;
    ModelRenderer leftclaw2;
    ModelRenderer leftclaw3;
    ModelRenderer head;
    ModelRenderer Jaw;
    ModelRenderer saddle;
    ModelRenderer mouthrod;
    ModelRenderer controlrope1;
    ModelRenderer controlrope2;
    ModelRenderer storage;
    ModelRenderer helmetstrap1;
    ModelRenderer helmetstrap2;
    ModelRenderer leftspine1;
    ModelRenderer leftspine2;
    ModelRenderer leftspine3;
    ModelRenderer leftearskin;
    ModelRenderer rightspine3;
    ModelRenderer rightspine2;
    ModelRenderer rightspine1;
    ModelRenderer rightearskin;
    ModelRenderer chestbelt;
    ModelRenderer stomachbelt;
    ModelRenderer ironhelmethorn1;
    ModelRenderer ironhelmethorn2;
    ModelRenderer ironhelmet;
    ModelRenderer ironhelmetsnout;
    ModelRenderer ironrightlegarmor;
    ModelRenderer ironleftlegarmor;
    ModelRenderer ironchestarmor;
    ModelRenderer ironrightshoulderpad;
    ModelRenderer ironleftshoulderpad;
    ModelRenderer goldleftshoulder;
    ModelRenderer goldchestarmor;
    ModelRenderer goldrightshoulder;
    ModelRenderer goldleftlegarmor;
    ModelRenderer goldrightlegarmor;
    ModelRenderer goldhelmethorn1;
    ModelRenderer goldhelmethorn2;
    ModelRenderer goldhelmet;
    ModelRenderer goldhelmetsnout;
    ModelRenderer diamondleftshoulder;
    ModelRenderer diamondrightshoulder;
    ModelRenderer diamondchestarmor;
    ModelRenderer diamondleftlegarmor;
    ModelRenderer diamondrightlegarmor;
    ModelRenderer diamondhelmet;
    ModelRenderer diamondhelmethorn2;
    ModelRenderer diamondhelmethorn1;
    ModelRenderer diamondhelmetsnout;
    ModelRenderer rightfing1a;
    ModelRenderer rightfing2a;
    ModelRenderer rightfing3a;
    ModelRenderer rightfing1b;
    ModelRenderer rightfing2b;
    ModelRenderer rightfing3b;
    ModelRenderer leftfing3a;
    ModelRenderer leftfing2a;
    ModelRenderer leftfing1a;
    ModelRenderer leftfing3b;
    ModelRenderer leftfing2b;
    ModelRenderer leftfing1b;
    ModelRenderer leftlowarm;
    ModelRenderer rightlowarm;
    ModelRenderer rightuparm;
    ModelRenderer leftuparm;
    ModelRenderer LeftWing;
    ModelRenderer RightWing;
    ModelRenderer MainHead;
    ModelRenderer leftwingflap1;
    ModelRenderer leftwingflap2;
    ModelRenderer leftwingflap3;
    ModelRenderer rightwingflap1;
    ModelRenderer rightwingflap2;
    ModelRenderer rightwingflap3;
    private int armor;
    private boolean isRidden;
    private boolean isChested;
    private boolean isSaddled;
    private boolean flapwings;
    private boolean onAir;
    private boolean diving;
    private boolean isSitting;
    private boolean isGhost;
    private int openMouth;
    private float yOffset;
    private float transparency;

    public MoCModelWyvern() {
        this.textureWidth = 128;
        this.textureHeight = 256;

        //tail
        this.Tail = new ModelRenderer(this);
        this.Tail.setRotationPoint(0F, 0F, 0F);

        this.back1 = new ModelRenderer(this, 92, 0);
        this.back1.addBox(-3F, -2F, -12F, 6, 2, 12);
        this.back1.setRotationPoint(0F, 0F, 0F);

        this.tail1 = new ModelRenderer(this, 0, 22);
        this.tail1.addBox(-4F, 0F, 0F, 8, 8, 10);
        this.tail1.setRotationPoint(0F, 0F, 0F);
        this.Tail.addChild(this.tail1);

        this.back2 = new ModelRenderer(this, 100, 14);
        this.back2.addBox(-2F, -2F, 0F, 4, 2, 10);
        this.back2.setRotationPoint(0F, 0F, 0F);
        this.tail1.addChild(this.back2);

        this.tail2 = new ModelRenderer(this, 0, 40);
        this.tail2.addBox(-3F, 0F, 0F, 6, 6, 9);
        this.tail2.setRotationPoint(0F, 0F, 10F);
        this.tail1.addChild(this.tail2);

        this.back3 = new ModelRenderer(this, 104, 26);
        this.back3.addBox(-1.5F, -2F, 0F, 3, 2, 9);
        this.back3.setRotationPoint(0F, 0F, 0F);
        this.tail2.addChild(this.back3);

        this.tail3 = new ModelRenderer(this, 0, 55);
        this.tail3.addBox(-2F, 0F, 0F, 4, 5, 8);
        this.tail3.setRotationPoint(0F, 0F, 8F);
        this.tail2.addChild(this.tail3);

        this.back4 = new ModelRenderer(this, 108, 37);
        this.back4.addBox(-1F, -2F, 0F, 2, 2, 8);
        this.back4.setRotationPoint(0F, 0F, 0F);
        this.tail3.addChild(this.back4);

        this.tail4 = new ModelRenderer(this, 0, 68);
        this.tail4.addBox(-1F, 0F, 0F, 2, 5, 7);
        this.tail4.setRotationPoint(0F, -1F, 7F);
        this.tail3.addChild(this.tail4);

        this.tail5 = new ModelRenderer(this, 0, 80);
        this.tail5.addBox(-0.5F, 0F, 0F, 1, 3, 7);
        this.tail5.setRotationPoint(0F, 1F, 6F);
        this.tail4.addChild(this.tail5);

        this.chest = new ModelRenderer(this, 44, 0);
        this.chest.addBox(-4.5F, 2.7F, -13F, 9, 10, 4);
        this.chest.setRotationPoint(0F, 0F, 0F);
        setRotation(this.chest, -0.2602503F, 0F, 0F);

        this.neckplate3 = new ModelRenderer(this, 112, 64);
        this.neckplate3.addBox(-2F, -2F, -2F, 4, 2, 4);
        this.neckplate3.setRotationPoint(0F, 0F, -12F);
        setRotation(this.neckplate3, -0.669215F, 0F, 0F);

        this.neck3 = new ModelRenderer(this, 100, 113);
        this.neck3.addBox(-3F, 0F, -2F, 6, 7, 8);
        this.neck3.setRotationPoint(0F, 0F, -12F);
        setRotation(this.neck3, -0.669215F, 0F, 0F);

        //head
        this.MainHead = new ModelRenderer(this);
        this.MainHead.setRotationPoint(0F, 3F, -15F);

        this.neck2 = new ModelRenderer(this, 102, 99);
        this.neck2.addBox(-2.5F, -3F, -8F, 5, 6, 8);
        //neck2.addBox(-2.5F, -3F, -4F, 5, 6, 8);
        this.neck2.setRotationPoint(0F, 0F, 0F);
        //setRotation(neck2, -66F/radianF, 0F, 0F);
        this.MainHead.addChild(this.neck2);

        this.neckplate2 = new ModelRenderer(this, 106, 54);
        this.neckplate2.addBox(-1.5F, -2F, -8F, 3, 2, 8);
        this.neckplate2.setRotationPoint(0F, -3F, 0F);
        this.neck2.addChild(this.neckplate2);

        this.neck1 = new ModelRenderer(this, 104, 85);
        this.neck1.addBox(-2F, -3F, -8F, 4, 6, 8);
        //neck1.addBox(-2F, -3F, -4F, 4, 6, 8);
        this.neck1.setRotationPoint(0F, -0.5F, -5.5F);
        //setRotation(neck1, 30F/radianF, 0F, 0F);
        this.neck2.addChild(this.neck1);

        this.neckplate1 = new ModelRenderer(this, 80, 108);
        this.neckplate1.addBox(-1F, -2F, -8F, 2, 2, 8);
        this.neckplate1.setRotationPoint(0F, -3F, 0F);
        this.neck1.addChild(this.neckplate1);

        this.head = new ModelRenderer(this, 98, 70);
        this.head.addBox(-3.5F, -3.5F, -8F, 7, 7, 8);
        this.head.setRotationPoint(0F, 0F, -7F);
        //setRotation(head, 45F/radianF, 0F, 0F);
        this.neck1.addChild(this.head);

        this.snout = new ModelRenderer(this, 72, 70);
        this.snout.addBox(-2F, -1.5F, -9F, 4, 3, 9);
        this.snout.setRotationPoint(0F, -1.5F, -8F);
        setRotation(this.snout, 2F / this.radianF, 0F, 0F);
        this.head.addChild(this.snout);

        this.headplate = new ModelRenderer(this, 80, 118);
        this.headplate.addBox(-1F, -1F, -4F, 2, 2, 8);
        this.headplate.setRotationPoint(0F, -3F, -1F);
        setRotation(this.headplate, 10F / this.radianF, 0F, 0F);
        this.head.addChild(this.headplate);

        this.beak = new ModelRenderer(this, 60, 85);
        this.beak.addBox(-1.5F, -2.5F, -1.5F, 3, 5, 3);
        this.beak.setRotationPoint(0F, 0.8F, -8.0F);
        setRotation(this.beak, -6F / this.radianF, 45F / this.radianF, -6F / this.radianF);
        this.snout.addChild(this.beak);

        this.righteyesock = new ModelRenderer(this, 70, 108);
        this.righteyesock.addBox(0F, 0F, 0F, 1, 2, 4);
        this.righteyesock.setRotationPoint(-3.5F, -2.5F, -8F);
        this.head.addChild(this.righteyesock);

        this.lefteyesock = new ModelRenderer(this, 70, 114);
        this.lefteyesock.addBox(0F, 0F, 0F, 1, 2, 4);
        this.lefteyesock.setRotationPoint(2.5F, -2.5F, -8F);
        this.head.addChild(this.lefteyesock);

        this.Jaw = new ModelRenderer(this, 72, 82);
        this.Jaw.addBox(-2F, -1F, -9F, 4, 2, 9);
        this.Jaw.setRotationPoint(0F, 2.5F, -7.5F);
        setRotation(this.Jaw, -10F / this.radianF, 0F, 0F);
        this.head.addChild(this.Jaw);

        this.leftupjaw = new ModelRenderer(this, 42, 93);
        this.leftupjaw.addBox(-1F, -1F, -6.5F, 2, 2, 13);
        this.leftupjaw.setRotationPoint(2F, 0F, -10.5F);
        setRotation(this.leftupjaw, -10F / this.radianF, 10F / this.radianF, 0F);
        this.head.addChild(this.leftupjaw);

        this.rightupjaw = new ModelRenderer(this, 72, 93);
        this.rightupjaw.addBox(-1F, -1F, -6.5F, 2, 2, 13);
        this.rightupjaw.setRotationPoint(-2F, 0F, -10.5F);
        setRotation(this.rightupjaw, -10F / this.radianF, -10F / this.radianF, 0F);
        this.head.addChild(this.rightupjaw);

        this.mouthrod = new ModelRenderer(this, 104, 50);
        this.mouthrod.addBox(-5F, -1F, -1F, 10, 2, 2);
        this.mouthrod.setRotationPoint(0F, 1F, -8F);
        this.head.addChild(this.mouthrod);

        this.helmetstrap1 = new ModelRenderer(this, 32, 146);
        this.helmetstrap1.addBox(-4F, -2F, 0F, 8, 4, 1);
        this.helmetstrap1.setRotationPoint(0F, 2F, -7.5F);
        this.head.addChild(this.helmetstrap1);

        this.helmetstrap2 = new ModelRenderer(this, 32, 141);
        this.helmetstrap2.addBox(-4F, -2F, 0F, 8, 4, 1);
        this.helmetstrap2.setRotationPoint(0F, 2F, -3.5F);
        this.head.addChild(this.helmetstrap2);

        this.controlrope1 = new ModelRenderer(this, 66, 43);
        this.controlrope1.addBox(0F, -2F, 0F, 0, 4, 23);
        this.controlrope1.setRotationPoint(4.5F, 1F, 0F);
        this.mouthrod.addChild(this.controlrope1);

        this.controlrope2 = new ModelRenderer(this, 66, 43);
        this.controlrope2.addBox(0F, -2F, 0F, 0, 4, 23);
        this.controlrope2.setRotationPoint(-4.5F, 1F, 0F);
        this.mouthrod.addChild(this.controlrope2);

        this.rightearskin = new ModelRenderer(this, 112, 201);
        this.rightearskin.addBox(0F, -4F, 0F, 0, 8, 8);
        this.rightearskin.setRotationPoint(-3F, -0.5F, 0F);
        this.head.addChild(this.rightearskin);

        this.leftearskin = new ModelRenderer(this, 96, 201);
        this.leftearskin.addBox(0F, -4F, 0F, 0, 8, 8);
        this.leftearskin.setRotationPoint(3F, -0.5F, 0F);
        this.head.addChild(this.leftearskin);

        this.rightspine1 = new ModelRenderer(this, 50, 141);
        this.rightspine1.addBox(-0.5F, -1F, 0F, 1, 2, 8);
        this.rightspine1.setRotationPoint(0F, -2F, 0F);
        setRotation(this.rightspine1, 15F / this.radianF, 0F, 0F);
        this.rightearskin.addChild(this.rightspine1);

        this.rightspine2 = new ModelRenderer(this, 50, 141);
        this.rightspine2.addBox(-0.5F, -1F, 0F, 1, 2, 8);
        this.rightspine2.setRotationPoint(0F, 0F, 0F);
        //setRotation(rightspine2, 0F, 0F, 0F);
        this.rightearskin.addChild(this.rightspine2);

        this.rightspine3 = new ModelRenderer(this, 50, 141);
        this.rightspine3.addBox(-0.5F, -1F, 0F, 1, 2, 8);
        this.rightspine3.setRotationPoint(0F, 2F, 0F);
        setRotation(this.rightspine3, -15F / this.radianF, 0F, 0F);
        this.rightearskin.addChild(this.rightspine3);

        this.leftspine1 = new ModelRenderer(this, 68, 141);
        this.leftspine1.addBox(-0.5F, -1F, 0F, 1, 2, 8);
        this.leftspine1.setRotationPoint(0F, -2F, 0F);
        setRotation(this.leftspine1, 15F / this.radianF, 0F, 0F);
        this.leftearskin.addChild(this.leftspine1);

        this.leftspine2 = new ModelRenderer(this, 68, 141);
        this.leftspine2.addBox(-0.5F, -1F, 0F, 1, 2, 8);
        this.leftspine2.setRotationPoint(0F, 0F, 0F);
        this.leftearskin.addChild(this.leftspine2);

        this.leftspine3 = new ModelRenderer(this, 68, 141);
        this.leftspine3.addBox(-0.5F, -1F, 0F, 1, 2, 8);
        this.leftspine3.setRotationPoint(0F, 2F, 0F);
        setRotation(this.leftspine3, -15F / this.radianF, 0F, 0F);
        this.leftearskin.addChild(this.leftspine3);

        //helmet
        this.ironhelmethorn1 = new ModelRenderer(this, 106, 139);
        this.ironhelmethorn1.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
        this.ironhelmethorn1.setRotationPoint(-0.5F, 0F, 0.1F);
        this.leftspine1.addChild(this.ironhelmethorn1);

        this.ironhelmethorn2 = new ModelRenderer(this, 106, 128);
        this.ironhelmethorn2.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
        this.ironhelmethorn2.setRotationPoint(0.5F, 0F, 0.1F);
        this.rightspine1.addChild(this.ironhelmethorn2);

        this.ironhelmet = new ModelRenderer(this, 32, 128);
        this.ironhelmet.addBox(-4F, -4F, -9F, 8, 4, 9);
        this.ironhelmet.setRotationPoint(0F, 0F, 0F);
        this.head.addChild(this.ironhelmet);

        this.ironhelmetsnout = new ModelRenderer(this, 0, 144);
        this.ironhelmetsnout.addBox(-2.5F, -2F, -7F, 5, 2, 7);
        this.ironhelmetsnout.setRotationPoint(0F, 0F, -1F);
        this.snout.addChild(this.ironhelmetsnout);

        this.goldhelmethorn1 = new ModelRenderer(this, 106, 161);
        this.goldhelmethorn1.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
        this.goldhelmethorn1.setRotationPoint(-0.5F, 0F, 0.1F);
        this.leftspine1.addChild(this.goldhelmethorn1);

        this.goldhelmethorn2 = new ModelRenderer(this, 106, 150);
        this.goldhelmethorn2.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
        this.goldhelmethorn2.setRotationPoint(0.5F, 0F, 0.1F);
        this.rightspine1.addChild(this.goldhelmethorn2);

        this.goldhelmet = new ModelRenderer(this, 94, 226);
        this.goldhelmet.addBox(-4F, -4F, -9F, 8, 4, 9);
        this.goldhelmet.setRotationPoint(0F, 0F, 0F);
        this.head.addChild(this.goldhelmet);

        this.goldhelmetsnout = new ModelRenderer(this, 71, 235);
        this.goldhelmetsnout.addBox(-2.5F, -2F, -7F, 5, 2, 7);
        this.goldhelmetsnout.setRotationPoint(0F, 0F, -1F);
        this.snout.addChild(this.goldhelmetsnout);

        this.diamondhelmet = new ModelRenderer(this, 23, 226);
        this.diamondhelmet.addBox(-4F, -4F, -9F, 8, 4, 9);
        this.diamondhelmet.setRotationPoint(0F, 0F, 0F);
        this.head.addChild(this.diamondhelmet);

        this.diamondhelmethorn2 = new ModelRenderer(this, 49, 234);
        this.diamondhelmethorn2.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
        this.diamondhelmethorn2.setRotationPoint(0.5F, 0F, 0.1F);
        this.rightspine1.addChild(this.diamondhelmethorn2);

        this.diamondhelmethorn1 = new ModelRenderer(this, 49, 245);
        this.diamondhelmethorn1.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
        this.diamondhelmethorn1.setRotationPoint(-0.5F, 0F, 0.1F);
        this.leftspine1.addChild(this.diamondhelmethorn1);

        this.diamondhelmetsnout = new ModelRenderer(this, 0, 235);
        this.diamondhelmetsnout.addBox(-2.5F, -2F, -7F, 5, 2, 7);
        this.diamondhelmetsnout.setRotationPoint(0F, 0F, -1F);
        this.snout.addChild(this.diamondhelmetsnout);

        this.torso = new ModelRenderer(this, 0, 0);
        this.torso.addBox(-5F, 0F, -12F, 10, 10, 12);
        this.torso.setRotationPoint(0F, 0F, 0F);

        this.saddle = new ModelRenderer(this, 38, 70);
        this.saddle.addBox(-3.5F, -2.5F, -8F, 7, 3, 10);
        this.saddle.setRotationPoint(0F, 0F, 0F);

        this.rightshoulder = new ModelRenderer(this, 42, 83);
        this.rightshoulder.addBox(-6F, 1F, -12.5F, 4, 5, 5);
        this.rightshoulder.setRotationPoint(0F, 0F, 0F);
        setRotation(this.rightshoulder, -0.2617994F, 0F, 0F);

        this.leftshoulder = new ModelRenderer(this, 24, 83);
        this.leftshoulder.addBox(2F, 1F, -12.5F, 4, 5, 5);
        this.leftshoulder.setRotationPoint(0F, 0F, 0F);
        setRotation(this.leftshoulder, -0.2617994F, 0F, 0F);

        //left wing
        this.LeftWing = new ModelRenderer(this);
        this.LeftWing.setRotationPoint(4F, 1F, -11F);

        this.leftuparm = new ModelRenderer(this, 44, 14);
        this.leftuparm.addBox(0F, -2F, -2F, 10, 4, 4);
        this.leftuparm.setRotationPoint(0F, 0F, 0F);
        setRotation(this.leftuparm, 0F, -10F / this.radianF, 0F);
        this.LeftWing.addChild(this.leftuparm);

        this.leftlowarm = new ModelRenderer(this, 72, 14);
        this.leftlowarm.addBox(0F, -2F, -2F, 10, 4, 4);
        this.leftlowarm.setRotationPoint(9F, 0F, 0F);
        setRotation(this.leftlowarm, 0F, 10F / this.radianF, 0F);
        this.leftuparm.addChild(this.leftlowarm);

        this.leftfing1a = new ModelRenderer(this, 52, 30);
        this.leftfing1a.addBox(0F, 0F, -1F, 2, 15, 2);
        this.leftfing1a.setRotationPoint(9F, 1F, 0F);
        setRotation(this.leftfing1a, 90F / this.radianF, 70F / this.radianF, 0F);
        this.leftlowarm.addChild(this.leftfing1a);

        this.leftfing1b = new ModelRenderer(this, 52, 47);
        this.leftfing1b.addBox(0F, 0F, -1F, 2, 10, 2);
        this.leftfing1b.setRotationPoint(0F, 14F, 0F);
        setRotation(this.leftfing1b, 0F, 0F, 35F / this.radianF);
        this.leftfing1a.addChild(this.leftfing1b);

        this.leftfing2a = new ModelRenderer(this, 44, 30);
        this.leftfing2a.addBox(-1F, 0F, 0F, 2, 15, 2);
        this.leftfing2a.setRotationPoint(9F, 1F, 0F);
        setRotation(this.leftfing2a, 90F / this.radianF, 35F / this.radianF, 0F);
        this.leftlowarm.addChild(this.leftfing2a);

        this.leftfing2b = new ModelRenderer(this, 44, 47);
        this.leftfing2b.addBox(-1F, 0F, 0F, 2, 10, 2);
        this.leftfing2b.setRotationPoint(0F, 14F, 0F);
        setRotation(this.leftfing2b, 0F, 0F, 30F / this.radianF);
        this.leftfing2a.addChild(this.leftfing2b);

        this.leftfing3a = new ModelRenderer(this, 36, 30);
        this.leftfing3a.addBox(-1F, 0F, 1F, 2, 15, 2);
        this.leftfing3a.setRotationPoint(9F, 1F, 0F);
        setRotation(this.leftfing3a, 90F / this.radianF, -5F / this.radianF, 0F);
        this.leftlowarm.addChild(this.leftfing3a);

        this.leftfing3b = new ModelRenderer(this, 36, 47);
        this.leftfing3b.addBox(-1F, 0F, 1F, 2, 10, 2);
        this.leftfing3b.setRotationPoint(0F, 14F, 0F);
        setRotation(this.leftfing3b, 0F, 0F, 30F / this.radianF);
        this.leftfing3a.addChild(this.leftfing3b);

        this.leftwingflap1 = new ModelRenderer(this, 74, 153);
        this.leftwingflap1.addBox(3.5F, -3F, 0.95F, 14, 24, 0);
        this.leftwingflap1.setRotationPoint(0F, 0F, 0F);
        setRotation(this.leftwingflap1, 0F, 0F, 70F / this.radianF);
        this.leftfing1a.addChild(this.leftwingflap1);

        this.leftwingflap2 = new ModelRenderer(this, 36, 153);
        this.leftwingflap2.addBox(-7F, 1.05F, 1.05F, 19, 24, 0);
        this.leftwingflap2.setRotationPoint(0F, 0F, 0F);
        setRotation(this.leftwingflap2, 0F, 0F, 40F / this.radianF);
        this.leftfing2a.addChild(this.leftwingflap2);

        this.leftwingflap3 = new ModelRenderer(this, 0, 153);
        this.leftwingflap3.addBox(-17.5F, 1F, 1.1F, 18, 24, 0);
        this.leftwingflap3.setRotationPoint(0F, 0F, 0F);
        this.leftfing3a.addChild(this.leftwingflap3);

        //right wing
        this.RightWing = new ModelRenderer(this);
        this.RightWing.setRotationPoint(-4F, 1F, -11F);

        this.rightuparm = new ModelRenderer(this, 44, 22);
        this.rightuparm.addBox(-10F, -2F, -2F, 10, 4, 4);
        this.rightuparm.setRotationPoint(0F, 0F, 0F);
        setRotation(this.rightuparm, 0F, 10F / this.radianF, 0F);
        this.RightWing.addChild(this.rightuparm);

        this.rightlowarm = new ModelRenderer(this, 72, 22);
        this.rightlowarm.addBox(-10F, -2F, -2F, 10, 4, 4);
        this.rightlowarm.setRotationPoint(-9F, 0F, 0F);
        setRotation(this.rightlowarm, 0F, -10F / this.radianF, 0F);
        this.rightuparm.addChild(this.rightlowarm);

        this.rightfing1a = new ModelRenderer(this, 36, 30);
        this.rightfing1a.addBox(-1F, 0F, -1F, 2, 15, 2);
        this.rightfing1a.setRotationPoint(-9F, 1F, -1F);
        setRotation(this.rightfing1a, 90F / this.radianF, -70F / this.radianF, 0F);
        this.rightlowarm.addChild(this.rightfing1a);

        this.rightfing1b = new ModelRenderer(this, 36, 47);
        this.rightfing1b.addBox(-1F, 0F, -1F, 2, 10, 2);
        this.rightfing1b.setRotationPoint(0F, 14F, 0F);
        setRotation(this.rightfing1b, 0F, 0F, -35F / this.radianF);
        this.rightfing1a.addChild(this.rightfing1b);

        this.rightwingflap1 = new ModelRenderer(this, 74, 177);
        this.rightwingflap1.addBox(-17.5F, -3F, 0.95F, 14, 24, 0);
        this.rightwingflap1.setRotationPoint(0F, 0F, 0F);
        setRotation(this.rightwingflap1, 0F, 0F, -70F / this.radianF);
        this.rightfing1a.addChild(this.rightwingflap1);

        this.rightfing2a = new ModelRenderer(this, 44, 30);
        this.rightfing2a.addBox(-1F, 0F, 0F, 2, 15, 2);
        this.rightfing2a.setRotationPoint(-9F, 1F, 0F);
        setRotation(this.rightfing2a, 90F / this.radianF, -35F / this.radianF, 0F);
        this.rightlowarm.addChild(this.rightfing2a);

        this.rightfing2b = new ModelRenderer(this, 44, 47);
        this.rightfing2b.addBox(-1F, 0F, 0F, 2, 10, 2);
        this.rightfing2b.setRotationPoint(0F, 14F, 0F);
        setRotation(this.rightfing2b, 0F, 0F, -30F / this.radianF);
        this.rightfing2a.addChild(this.rightfing2b);

        this.rightwingflap2 = new ModelRenderer(this, 36, 177);
        this.rightwingflap2.addBox(-19F, 1.05F, 1.05F, 19, 24, 0);
        this.rightwingflap2.setRotationPoint(0F, 0F, 0F);
        setRotation(this.rightwingflap2, 0F, 0F, -40F / this.radianF);
        this.rightfing2a.addChild(this.rightwingflap2);

        this.rightfing3a = new ModelRenderer(this, 52, 30);
        this.rightfing3a.addBox(-1F, 0F, 1F, 2, 15, 2);
        this.rightfing3a.setRotationPoint(-9F, 1F, 0F);
        setRotation(this.rightfing3a, 90F / this.radianF, 5F / this.radianF, 0F);
        this.rightlowarm.addChild(this.rightfing3a);

        this.rightfing3b = new ModelRenderer(this, 52, 47);
        this.rightfing3b.addBox(-1F, 0F, 1F, 2, 10, 2);
        this.rightfing3b.setRotationPoint(0F, 14F, 0F);
        setRotation(this.rightfing3b, 0F, 0F, -30F / this.radianF);
        this.rightfing3a.addChild(this.rightfing3b);

        this.rightwingflap3 = new ModelRenderer(this, 0, 177);
        this.rightwingflap3.addBox(-0.5F, 1F, 1.1F, 18, 24, 0);
        this.rightwingflap3.setRotationPoint(0F, 0F, 0F);
        this.rightfing3a.addChild(this.rightwingflap3);

        //legs
        this.leftupleg = new ModelRenderer(this, 0, 111);
        this.leftupleg.addBox(-2F, -3F, -3F, 4, 10, 7);
        this.leftupleg.setRotationPoint(5F, 6F, -5F);
        setRotation(this.leftupleg, -25F / this.radianF, 0F, 0F);

        this.leftmidleg = new ModelRenderer(this, 0, 102);
        this.leftmidleg.addBox(-1.5F, -2F, 0F, 3, 4, 5);
        this.leftmidleg.setRotationPoint(0F, 5F, 4F);
        this.leftupleg.addChild(this.leftmidleg);

        this.leftlowleg = new ModelRenderer(this, 0, 91);
        this.leftlowleg.addBox(-1.5F, 0F, -1.5F, 3, 8, 3);
        this.leftlowleg.setRotationPoint(0F, 2F, 3.5F);
        this.leftmidleg.addChild(this.leftlowleg);

        this.leftfoot = new ModelRenderer(this, 44, 121);
        this.leftfoot.addBox(-2F, -1F, -3F, 4, 3, 4);
        this.leftfoot.setRotationPoint(0F, 7F, 0.5F);
        setRotation(this.leftfoot, 25F / this.radianF, 0F, 0F);
        this.leftlowleg.addChild(this.leftfoot);

        this.lefttoe1 = new ModelRenderer(this, 96, 35);
        this.lefttoe1.addBox(-0.5F, -1F, -3F, 1, 2, 3);
        this.lefttoe1.setRotationPoint(-1.5F, 1F, -3F);
        this.leftfoot.addChild(this.lefttoe1);

        this.lefttoe3 = new ModelRenderer(this, 96, 30);
        this.lefttoe3.addBox(-0.5F, -1F, -3F, 1, 2, 3);
        this.lefttoe3.setRotationPoint(1.5F, 1F, -3F);
        this.leftfoot.addChild(this.lefttoe3);

        this.lefttoe2 = new ModelRenderer(this, 84, 30);
        this.lefttoe2.addBox(-1F, -1.5F, -4F, 2, 3, 4);
        this.lefttoe2.setRotationPoint(0F, 0.5F, -3F);
        this.leftfoot.addChild(this.lefttoe2);

        this.leftclaw1 = new ModelRenderer(this, 100, 26);
        this.leftclaw1.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        this.leftclaw1.setRotationPoint(0.5F, -0.5F, -2.5F);
        setRotation(this.leftclaw1, -25F / this.radianF, 0F, 0F);
        this.lefttoe1.addChild(this.leftclaw1);

        this.leftclaw2 = new ModelRenderer(this, 100, 26);
        this.leftclaw2.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
        this.leftclaw2.setRotationPoint(0F, -1F, -3.5F);
        setRotation(this.leftclaw2, -25F / this.radianF, 0F, 0F);
        this.lefttoe2.addChild(this.leftclaw2);

        this.leftclaw3 = new ModelRenderer(this, 100, 26);
        this.leftclaw3.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        this.leftclaw3.setRotationPoint(-0.5F, -0.5F, -2.5F);
        setRotation(this.leftclaw3, -25F / this.radianF, 0F, 0F);
        this.lefttoe3.addChild(this.leftclaw3);

        this.ironleftlegarmor = new ModelRenderer(this, 39, 97);
        this.ironleftlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
        this.ironleftlegarmor.setRotationPoint(0F, 2.5F, 0F);
        this.leftlowleg.addChild(this.ironleftlegarmor);

        this.goldleftlegarmor = new ModelRenderer(this, 112, 181);
        this.goldleftlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
        this.goldleftlegarmor.setRotationPoint(0F, 2.5F, 0F);
        this.leftlowleg.addChild(this.goldleftlegarmor);

        this.diamondleftlegarmor = new ModelRenderer(this, 43, 215);
        this.diamondleftlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
        this.diamondleftlegarmor.setRotationPoint(0F, 2.5F, 0F);
        this.leftlowleg.addChild(this.diamondleftlegarmor);

        //right leg
        this.rightupleg = new ModelRenderer(this, 0, 111);
        this.rightupleg.addBox(-2F, -3F, -3F, 4, 10, 7);
        this.rightupleg.setRotationPoint(-5F, 6F, -5F);
        setRotation(this.rightupleg, -25F / this.radianF, 0F, 0F);

        this.rightmidleg = new ModelRenderer(this, 0, 102);
        this.rightmidleg.addBox(-1.5F, -2F, 0F, 3, 4, 5);
        this.rightmidleg.setRotationPoint(0F, 5F, 4F);
        this.rightupleg.addChild(this.rightmidleg);

        this.rightlowleg = new ModelRenderer(this, 0, 91);
        this.rightlowleg.addBox(-1.5F, 0F, -1.5F, 3, 8, 3);
        this.rightlowleg.setRotationPoint(0F, 2F, 3.5F);
        this.rightmidleg.addChild(this.rightlowleg);

        this.rightfoot = new ModelRenderer(this, 44, 121);
        this.rightfoot.addBox(-2F, -1F, -3F, 4, 3, 4);
        this.rightfoot.setRotationPoint(0F, 7F, 0.5F);
        setRotation(this.rightfoot, 25F / this.radianF, 0F, 0F);
        this.rightlowleg.addChild(this.rightfoot);

        this.righttoe1 = new ModelRenderer(this, 96, 35);
        this.righttoe1.addBox(-0.5F, -1F, -3F, 1, 2, 3);
        this.righttoe1.setRotationPoint(-1.5F, 1F, -3F);
        this.rightfoot.addChild(this.righttoe1);

        this.righttoe3 = new ModelRenderer(this, 96, 30);
        this.righttoe3.addBox(-0.5F, -1F, -3F, 1, 2, 3);
        this.righttoe3.setRotationPoint(1.5F, 1F, -3F);
        this.rightfoot.addChild(this.righttoe3);

        this.righttoe2 = new ModelRenderer(this, 84, 30);
        this.righttoe2.addBox(-1F, -1.5F, -4F, 2, 3, 4);
        this.righttoe2.setRotationPoint(0F, 0.5F, -3F);
        this.rightfoot.addChild(this.righttoe2);

        this.rightclaw1 = new ModelRenderer(this, 100, 26);
        this.rightclaw1.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        this.rightclaw1.setRotationPoint(0.5F, -0.5F, -2.5F);
        setRotation(this.rightclaw1, -25F / this.radianF, 0F, 0F);
        this.righttoe1.addChild(this.rightclaw1);

        this.rightclaw2 = new ModelRenderer(this, 100, 26);
        this.rightclaw2.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
        this.rightclaw2.setRotationPoint(0F, -1F, -3.5F);
        setRotation(this.rightclaw2, -25F / this.radianF, 0F, 0F);
        this.righttoe2.addChild(this.rightclaw2);

        this.rightclaw3 = new ModelRenderer(this, 100, 26);
        this.rightclaw3.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        this.rightclaw3.setRotationPoint(-0.5F, -0.5F, -2.5F);
        setRotation(this.rightclaw3, -25F / this.radianF, 0F, 0F);
        this.righttoe3.addChild(this.rightclaw3);

        this.ironrightlegarmor = new ModelRenderer(this, 39, 97);
        this.ironrightlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
        this.ironrightlegarmor.setRotationPoint(0F, 2.5F, 0F);
        this.rightlowleg.addChild(this.ironrightlegarmor);

        this.goldrightlegarmor = new ModelRenderer(this, 112, 181);
        this.goldrightlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
        this.goldrightlegarmor.setRotationPoint(0F, 2.5F, 0F);
        this.rightlowleg.addChild(this.goldrightlegarmor);

        this.diamondrightlegarmor = new ModelRenderer(this, 43, 215);
        this.diamondrightlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
        this.diamondrightlegarmor.setRotationPoint(0F, 2.5F, 0F);
        this.rightlowleg.addChild(this.diamondrightlegarmor);

        this.storage = new ModelRenderer(this, 28, 59);
        this.storage.addBox(-5F, -4.5F, 1.5F, 10, 5, 6);
        this.storage.setRotationPoint(0F, 0F, 0F);
        setRotation(this.storage, -0.2268928F, 0F, 0F);

        this.chestbelt = new ModelRenderer(this, 0, 201);
        this.chestbelt.addBox(-5.5F, -0.5F, -9F, 11, 11, 2);
        this.chestbelt.setRotationPoint(0F, 0F, 0F);

        this.stomachbelt = new ModelRenderer(this, 0, 201);
        this.stomachbelt.addBox(-5.5F, -0.5F, -3F, 11, 11, 2);
        this.stomachbelt.setRotationPoint(0F, 0F, 0F);

        this.ironchestarmor = new ModelRenderer(this, 0, 128);
        this.ironchestarmor.addBox(-5.5F, 2.2F, -13.5F, 11, 11, 5);
        this.ironchestarmor.setRotationPoint(0F, 0F, 0F);
        setRotation(this.ironchestarmor, -0.2602503F, 0F, 0F);

        this.ironrightshoulderpad = new ModelRenderer(this, 74, 201);
        this.ironrightshoulderpad.addBox(-6.5F, 0.5F, -13F, 5, 6, 6);
        this.ironrightshoulderpad.setRotationPoint(0F, 0F, 0F);
        setRotation(this.ironrightshoulderpad, -0.2617994F, 0F, 0F);

        this.ironleftshoulderpad = new ModelRenderer(this, 26, 201);
        this.ironleftshoulderpad.addBox(1.5F, 0.5F, -13F, 5, 6, 6);
        this.ironleftshoulderpad.setRotationPoint(0F, 0F, 0F);
        setRotation(this.ironleftshoulderpad, -0.2617994F, 0F, 0F);

        this.goldleftshoulder = new ModelRenderer(this, 71, 244);
        this.goldleftshoulder.addBox(1.5F, 0.5F, -13F, 5, 6, 6);
        this.goldleftshoulder.setRotationPoint(0F, 0F, 0F);
        setRotation(this.goldleftshoulder, -0.2617994F, 0F, 0F);

        this.goldchestarmor = new ModelRenderer(this, 71, 219);
        this.goldchestarmor.addBox(-5.5F, 2.2F, -13.5F, 11, 11, 5);
        this.goldchestarmor.setRotationPoint(0F, 0F, 0F);
        setRotation(this.goldchestarmor, -0.2602503F, 0F, 0F);

        this.goldrightshoulder = new ModelRenderer(this, 93, 244);
        this.goldrightshoulder.addBox(-6.5F, 0.5F, -13F, 5, 6, 6);
        this.goldrightshoulder.setRotationPoint(0F, 0F, 0F);
        setRotation(this.goldrightshoulder, -0.2617994F, 0F, 0F);

        this.diamondleftshoulder = new ModelRenderer(this, 0, 244);
        this.diamondleftshoulder.addBox(1.5F, 0.5F, -13F, 5, 6, 6);
        this.diamondleftshoulder.setRotationPoint(0F, 0F, 0F);
        setRotation(this.diamondleftshoulder, -0.2617994F, 0F, 0F);

        this.diamondrightshoulder = new ModelRenderer(this, 22, 244);
        this.diamondrightshoulder.addBox(-6.5F, 0.5F, -13F, 5, 6, 6);
        this.diamondrightshoulder.setRotationPoint(0F, 0F, 0F);
        setRotation(this.diamondrightshoulder, -0.2617994F, 0F, 0F);

        this.diamondchestarmor = new ModelRenderer(this, 0, 219);
        this.diamondchestarmor.addBox(-5.5F, 2.2F, -13.5F, 11, 11, 5);
        this.diamondchestarmor.setRotationPoint(0F, 0F, 0F);
        setRotation(this.diamondchestarmor, -0.2602503F, 0F, 0F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.armor = entityIn.getArmorType();
        this.isRidden = (entityIn.isBeingRidden());
        this.isChested = entityIn.getIsChested();
        this.isSaddled = entityIn.getIsRideable();
        this.flapwings = (entityIn.wingFlapCounter != 0);
        this.onAir = entityIn.isOnAir() || entityIn.getIsFlying();
        this.diving = (entityIn.diveCounter != 0);
        this.isSitting = entityIn.getIsSitting();
        this.isGhost = entityIn.getIsGhost();
        this.openMouth = entityIn.mouthCounter;
        this.yOffset = entityIn.getAdjustedYOffset();
        this.transparency = entityIn.tFloat();
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.push();
        matrixStackIn.translate(0F, yOffset, 0F);

        if (isGhost) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(0.8F, 0.8F, 0.8F, transparency);
            //matrixStackIn.scale(1.3F, 1.0F, 1.3F);
        }
        this.back1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        this.chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        this.LeftWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        this.rightshoulder.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leftshoulder.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        this.neckplate3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.neck3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.torso.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        if (isChested) {
            this.storage.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        if (isSaddled) {
            this.saddle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.mouthrod.showModel = !false; //render(f5);
            this.helmetstrap1.showModel = !false; //render(f5);
            this.helmetstrap2.showModel = !false; //render(f5);
            this.chestbelt.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.stomachbelt.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            if (isRidden) {
                this.controlrope1.showModel = !false;
                this.controlrope2.showModel = !false;
            } else {
                this.controlrope1.showModel = !true;
                this.controlrope2.showModel = !true;
            }
        } else {
            this.mouthrod.showModel = !true;
            this.helmetstrap1.showModel = !true;
            this.helmetstrap2.showModel = !true;
        }

        this.ironhelmethorn1.showModel = !true;
        this.ironhelmethorn2.showModel = !true;
        this.ironhelmet.showModel = !true;
        this.ironhelmetsnout.showModel = !true;
        this.ironrightlegarmor.showModel = !true;
        this.ironleftlegarmor.showModel = !true;
        this.ironchestarmor.showModel = !true;
        this.ironrightshoulderpad.showModel = !true;
        this.ironleftshoulderpad.showModel = !true;

        this.goldleftshoulder.showModel = !true;
        this.goldchestarmor.showModel = !true;
        this.goldrightshoulder.showModel = !true;
        this.goldleftlegarmor.showModel = !true;
        this.goldrightlegarmor.showModel = !true;
        this.goldhelmethorn1.showModel = !true;
        this.goldhelmethorn2.showModel = !true;
        this.goldhelmet.showModel = !true;
        this.goldhelmetsnout.showModel = !true;

        this.diamondleftshoulder.showModel = !true;
        this.diamondrightshoulder.showModel = !true;
        this.diamondchestarmor.showModel = !true;
        this.diamondleftlegarmor.showModel = !true;
        this.diamondrightlegarmor.showModel = !true;
        this.diamondhelmet.showModel = !true;
        this.diamondhelmethorn2.showModel = !true;
        this.diamondhelmethorn1.showModel = !true;
        this.diamondhelmetsnout.showModel = !true;

        switch (armor) {
            case 1:
                this.ironhelmethorn1.showModel = !false;
                this.ironhelmethorn2.showModel = !false;
                this.ironhelmet.showModel = !false;
                this.ironhelmetsnout.showModel = !false;
                this.ironrightlegarmor.showModel = !false;
                this.ironleftlegarmor.showModel = !false;
                this.ironchestarmor.showModel = !false;
                this.ironrightshoulderpad.showModel = !false;
                this.ironleftshoulderpad.showModel = !false;
                break;
            case 2:
                this.goldleftshoulder.showModel = !false;
                this.goldchestarmor.showModel = !false;
                this.goldrightshoulder.showModel = !false;
                this.goldleftlegarmor.showModel = !false;
                this.goldrightlegarmor.showModel = !false;
                this.goldhelmethorn1.showModel = !false;
                this.goldhelmethorn2.showModel = !false;
                this.goldhelmet.showModel = !false;
                this.goldhelmetsnout.showModel = !false;
                break;
            case 3:
                this.diamondleftshoulder.showModel = !false;
                this.diamondrightshoulder.showModel = !false;
                this.diamondchestarmor.showModel = !false;
                this.diamondleftlegarmor.showModel = !false;
                this.diamondrightlegarmor.showModel = !false;
                this.diamondhelmet.showModel = !false;
                this.diamondhelmethorn2.showModel = !false;
                this.diamondhelmethorn1.showModel = !false;
                this.diamondhelmetsnout.showModel = !false;
                break;

        }

        this.MainHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leftupleg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.rightupleg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        this.ironchestarmor.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.ironrightshoulderpad.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.ironleftshoulderpad.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.goldleftshoulder.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.goldchestarmor.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.goldrightshoulder.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.diamondleftshoulder.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.diamondrightshoulder.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.diamondchestarmor.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (isGhost) {
            RenderSystem.disableBlend();
        }
        matrixStackIn.pop();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @SuppressWarnings("unused")
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float RLegXRot = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 0.8F * limbSwingAmount;
        float LLegXRot = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;

        netHeadYaw = MoCTools.realAngle(netHeadYaw);

        float f10 = 60F;
        if (netHeadYaw > f10) {
            netHeadYaw = f10;
        }
        if (netHeadYaw < -f10) {
            netHeadYaw = -f10;
        }
        this.neck2.rotateAngleX = -66F / this.radianF + (headPitch * 1 / 3F / this.radianF);
        this.neck1.rotateAngleX = 30F / this.radianF + (headPitch * 2 / 3F / this.radianF);

        this.head.rotateAngleX = 45F / this.radianF;

        this.neck2.rotateAngleY = (netHeadYaw * 2 / 3F) / this.radianF;
        this.neck1.rotateAngleY = (netHeadYaw * 1 / 3F) / this.radianF;

        this.head.rotateAngleY = 0F;
        this.head.rotateAngleZ = 0F;

        if (isRidden) {
            //straight neck when mounted
            this.neck1.rotateAngleY = 0F;
            this.neck2.rotateAngleY = 0F;

            if (onAir) {
                this.neck1.rotateAngleX = 0F;
                this.neck2.rotateAngleX = 0F;

            } else {

                this.neck2.rotateAngleX = -66F / this.radianF + (RLegXRot * 1 / 60F);
                this.neck1.rotateAngleX = 30F / this.radianF + (RLegXRot * 2 / 60F);
            }
        }
        float TailXRot = MathHelper.cos(limbSwing * 0.4F) * 0.2F * limbSwingAmount;
        this.tail1.rotateAngleX = (-19F / this.radianF);// - TailXRot;
        this.tail2.rotateAngleX = (-16F / this.radianF);// + TailXRot;
        this.tail3.rotateAngleX = (7F / this.radianF);// + TailXRot;
        this.tail4.rotateAngleX = (11F / this.radianF);// + TailXRot;
        this.tail5.rotateAngleX = (8F / this.radianF);

        float t = limbSwing / 2;

        /*
         * if (movetail) { t = ageInTicks/4F; }
         */
        float A = 0.15F;//0.8F;
        float w = 0.9F;
        float k = 0.6F;

        int i = 0;
        float tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.tail1.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.tail2.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.tail3.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.tail4.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.tail5.rotateAngleY = tailLat;

        float WingSpread;

        /*
         * flapping wings or cruising. IF flapping wings, move up and down. if
         * cruising, movement depends on speed
         */
        //float WingRot = 0F;
        if (flapwings && !isGhost) {
            WingSpread = MathHelper.cos((ageInTicks * 0.3F) + 3.141593F) * 1.2F;// * limbSwingAmount;
        } else
        //cruising
        {
            //WingRot = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.2F * limbSwingAmount;
            WingSpread = MathHelper.cos((limbSwing * 0.5F)) * 0.1F;//* 1.2F * limbSwingAmount;
        }

        if (onAir || isGhost) {
            //rightfing1a.rotateAngleY = -70F/radianF+ WingSpread;

            float speedMov = (limbSwingAmount * 0.5F);
            if (isGhost) {
                speedMov = 0.5F;
            }

            this.leftuparm.rotateAngleZ = WingSpread * 2 / 3F;
            this.rightuparm.rotateAngleZ = -WingSpread * 2 / 3F;
            this.leftlowarm.rotateAngleZ = WingSpread * 0.1F;
            this.leftfing1a.rotateAngleZ = WingSpread;
            this.leftfing2a.rotateAngleZ = WingSpread * 0.8F;

            this.rightlowarm.rotateAngleZ = -WingSpread * 0.1F;
            this.rightfing1a.rotateAngleZ = -WingSpread;
            this.rightfing2a.rotateAngleZ = -WingSpread * 0.8F;

            //System.out.println("WingSpread = " + WingSpread);
            this.leftuparm.rotateAngleY = -10F / this.radianF - (WingSpread / 2.5F);
            this.leftlowarm.rotateAngleY = 15F / this.radianF + (WingSpread / 2.5F);
            this.leftfing1a.rotateAngleY = 70F / this.radianF;
            this.leftfing2a.rotateAngleY = 35F / this.radianF;
            this.leftfing3a.rotateAngleY = -5F / this.radianF;
            this.rightuparm.rotateAngleY = 10F / this.radianF + (WingSpread / 2.5F);
            this.rightlowarm.rotateAngleY = -15F / this.radianF - (WingSpread / 2.5F);
            this.rightfing1a.rotateAngleY = -70F / this.radianF;
            this.rightfing2a.rotateAngleY = -35F / this.radianF;
            this.rightfing3a.rotateAngleY = 5F / this.radianF;

            this.leftupleg.rotateAngleX = speedMov;
            this.leftmidleg.rotateAngleX = speedMov;
            this.leftfoot.rotateAngleX = 25F / this.radianF;
            this.lefttoe1.rotateAngleX = speedMov;
            this.lefttoe2.rotateAngleX = speedMov;
            this.lefttoe3.rotateAngleX = speedMov;
            this.rightfoot.rotateAngleX = 25F / this.radianF;
            this.rightupleg.rotateAngleX = speedMov;
            this.rightmidleg.rotateAngleX = speedMov;
            this.righttoe1.rotateAngleX = speedMov;
            this.righttoe2.rotateAngleX = speedMov;
            this.righttoe3.rotateAngleX = speedMov;

        } else {

            this.leftlowarm.rotateAngleZ = 0F;
            this.leftfing1a.rotateAngleZ = 0F;
            this.leftfing2a.rotateAngleZ = 0F;
            this.rightlowarm.rotateAngleZ = 0F;
            this.rightfing1a.rotateAngleZ = 0F;
            this.rightfing2a.rotateAngleZ = 0F;
            //leftuparm.rotateAngleX = RLegXRot;
            //rightuparm.rotateAngleX = LLegXRot;

            this.leftuparm.rotateAngleZ = 30F / this.radianF;
            this.leftuparm.rotateAngleY = -60F / this.radianF + (LLegXRot / 5F);

            this.leftlowarm.rotateAngleY = 105F / this.radianF;
            this.leftfing1a.rotateAngleY = -20F / this.radianF;
            this.leftfing2a.rotateAngleY = -26F / this.radianF;
            this.leftfing3a.rotateAngleY = -32F / this.radianF;

            this.rightuparm.rotateAngleY = 60F / this.radianF - (RLegXRot / 5F);
            this.rightuparm.rotateAngleZ = -30F / this.radianF;

            this.rightlowarm.rotateAngleY = -105F / this.radianF;
            this.rightfing1a.rotateAngleY = 16F / this.radianF;
            this.rightfing2a.rotateAngleY = 26F / this.radianF;
            this.rightfing3a.rotateAngleY = 32F / this.radianF;

            //test mouth opening

            this.leftupleg.rotateAngleX = -25F / this.radianF + LLegXRot;
            this.rightupleg.rotateAngleX = -25F / this.radianF + RLegXRot;

            this.leftmidleg.rotateAngleX = 0F;
            this.leftlowleg.rotateAngleX = 0F;
            this.leftfoot.rotateAngleX = 25F / this.radianF - (LLegXRot);
            this.lefttoe1.rotateAngleX = LLegXRot;
            this.lefttoe2.rotateAngleX = LLegXRot;
            this.lefttoe3.rotateAngleX = LLegXRot;

            this.rightmidleg.rotateAngleX = 0F;
            this.rightlowleg.rotateAngleX = 0F;
            this.rightfoot.rotateAngleX = 25F / this.radianF - (RLegXRot);
            this.righttoe1.rotateAngleX = RLegXRot;
            this.righttoe2.rotateAngleX = RLegXRot;
            this.righttoe3.rotateAngleX = RLegXRot;

        }

        if (isSitting) {
            this.leftupleg.rotateAngleX = 45F / this.radianF + LLegXRot;
            this.rightupleg.rotateAngleX = 45F / this.radianF + RLegXRot;
            this.leftmidleg.rotateAngleX = 30F;
            this.rightmidleg.rotateAngleX = 30F;
            this.neck2.rotateAngleX = -36F / this.radianF + (headPitch * 1 / 3F / this.radianF);
            this.neck1.rotateAngleX = 30F / this.radianF + (headPitch * 2 / 3F / this.radianF);
        }
        if (diving) {
            this.leftuparm.rotateAngleZ = -40F / this.radianF;
            this.rightuparm.rotateAngleZ = 40F / this.radianF;
            this.leftlowarm.rotateAngleZ = 0F;
            this.leftfing1a.rotateAngleZ = 0F;
            this.leftfing2a.rotateAngleZ = 0F;

            this.rightlowarm.rotateAngleZ = 0F;
            this.rightfing1a.rotateAngleZ = 0F;
            this.rightfing2a.rotateAngleZ = 0F;

            //leftuparm.rotateAngleZ = 30F/radianF ;
            this.leftuparm.rotateAngleY = -50F / this.radianF;// + (LLegXRot /5F);
            this.leftlowarm.rotateAngleY = 30F / this.radianF;
            this.leftfing1a.rotateAngleY = 50F / this.radianF;
            this.leftfing2a.rotateAngleY = 30F / this.radianF;
            this.leftfing3a.rotateAngleY = 10F / this.radianF;

            this.rightuparm.rotateAngleY = 50F / this.radianF;// - (RLegXRot /5F);
            //rightuparm.rotateAngleZ = -30F/radianF;

            this.rightlowarm.rotateAngleY = -30F / this.radianF;
            this.rightfing1a.rotateAngleY = -50F / this.radianF;
            this.rightfing2a.rotateAngleY = -30F / this.radianF;
            this.rightfing3a.rotateAngleY = -10F / this.radianF;
        }

        if (openMouth != 0) {
            float mouthMov = (MathHelper.cos((openMouth - 15) * 0.11F) * 0.8F);
            this.Jaw.rotateAngleX = (-10F / this.radianF) + mouthMov;
            this.leftearskin.rotateAngleY = mouthMov;
            this.rightearskin.rotateAngleY = -mouthMov;

        } else {
            this.Jaw.rotateAngleX = -10F / this.radianF;
            this.leftearskin.rotateAngleY = 0F;
            this.rightearskin.rotateAngleY = 0F;
        }
    }
}
