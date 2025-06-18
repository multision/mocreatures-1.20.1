/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHeart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

public class MoCEntityFishy extends MoCEntityTameableAquatic {

    public static final String[] fishNames = {"Blue", "Orange", "Light Blue", "Lime", "Green", "Purple", "Yellow", "Cyan", "Striped", "Red"};
    private static final EntityDataAccessor<Boolean> HAS_EATEN = SynchedEntityData.defineId(MoCEntityFishy.class, EntityDataSerializers.BOOLEAN);
    public int gestationtime;

    public MoCEntityFishy(EntityType<? extends MoCEntityFishy> type, Level world) {
        super(type, world);
        //setSize(0.5f, 0.3f);
        setAdult(true);
        //setAge(50 + this.random.nextInt(50));
        setMoCAge(100);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.3D));
        this.goalSelector.addGoal(3, new EntityAIFleeFromEntityMoC(this, entity -> (entity.getBbHeight() > 0.3F || entity.getBbWidth() > 0.3F), 2.0F, 0.6D, 1.5D));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 3.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.random.nextInt(fishNames.length) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("fishy_orange.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("fishy_light_blue.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("fishy_lime.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("fishy_green.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("fishy_purple.png");
            case 7:
                return MoCreatures.proxy.getModelTexture("fishy_yellow.png");
            case 8:
                return MoCreatures.proxy.getModelTexture("fishy_cyan.png");
            case 9:
                return MoCreatures.proxy.getModelTexture("fishy_striped.png");
            case 10:
                return MoCreatures.proxy.getModelTexture("fishy_red.png");
            default:
                return MoCreatures.proxy.getModelTexture("fishy_blue.png");
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_EATEN, Boolean.FALSE);
    }

    public boolean getHasEaten() {
        return this.entityData.get(HAS_EATEN);
    }

    public void setHasEaten(boolean flag) {
        this.entityData.set(HAS_EATEN, flag);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        int i = this.random.nextInt(100);
        if (i < 70) {
            Item[] fishTypes = new Item[] { Items.COD, Items.SALMON, Items.TROPICAL_FISH };
            Item fish = fishTypes[this.random.nextInt(fishTypes.length)];
            spawnAtLocation(new ItemStack(fish), 0.0F);
        } else {
            int j = this.random.nextInt(2);

            int fishyEggType = getTypeMoC();
            ItemStack fishyEgg = new ItemStack(MoCItems.MOC_EGG.get(), j + 1);

            fishyEgg.getOrCreateTag().putInt("EggType", fishyEggType);

            spawnAtLocation(fishyEgg, 0.0F);
        }
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return MoCLootTables.FISHY;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.isEyeInFluid(FluidTags.WATER)) {
            this.yBodyRot = this.getYRot();
            this.setXRot(this.getXRot());
        }

        if (!this.level().isClientSide()) {
            if (getIsTamed() && this.random.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }

            if (!ReadyforParenting(this)) {
                return;
            }
            int i = 0;
            List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(4D, 3D, 4D), entity -> entity != this);
            for (Entity entity : list) {
                if (entity instanceof MoCEntityFishy) {
                    i++;
                }
            }

            if (i > 1) {
                return;
            }
            List<Entity> list1 = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(4D, 2D, 4D), entity -> entity != this);
            for (int k = 0; k < list1.size(); k++) {
                Entity entity1 = list1.get(k);
                if (!(entity1 instanceof MoCEntityFishy) || (entity1 == this)) {
                    continue;
                }
                MoCEntityFishy entityfishy = (MoCEntityFishy) entity1;
                if (!ReadyforParenting(this) || !ReadyforParenting(entityfishy) || (this.getTypeMoC() != entityfishy.getTypeMoC())) {
                    continue;
                }
                if (this.random.nextInt(100) == 0) {
                    this.gestationtime++;
                }
                if (this.gestationtime % 3 == 0) {
                    MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().dimension())), new MoCMessageHeart(this.getId()));
                }
                if (this.gestationtime <= 50) {
                    continue;
                }
                int l = this.random.nextInt(3) + 1;
                for (int i1 = 0; i1 < l; i1++) {
                    // Create the baby entity using a safer approach
                    Entity babyEntity = MoCEntities.FISHY.get().create(this.level());
                    if (babyEntity instanceof MoCEntityFishy) {
                        MoCEntityFishy entityfishy1 = (MoCEntityFishy) babyEntity;
                        entityfishy1.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                        this.level().addFreshEntity(entityfishy1);
                        MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
                        setHasEaten(false);
                        entityfishy.setHasEaten(false);
                        this.gestationtime = 0;
                        entityfishy.gestationtime = 0;

                        Player entityplayer = this.level().getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityfishy1);
                        }

                        entityfishy1.setMoCAge(20);
                        entityfishy1.setAdult(false);
                        entityfishy1.setTypeInt(getTypeMoC());
                    }
                }
                break;
            }
        }
    }

    public boolean ReadyforParenting(MoCEntityFishy entityfishy) {
        return false; //TOOD pending overhaul of breeding
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    public float rollRotationOffset() {
        if (!this.isEyeInFluid(FluidTags.WATER)) {
            return -90F;
        }
        return 0F;
    }

    @Override
    protected boolean isFisheable() {
        return !getIsTamed();
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getSpeed() {
        return 0.10F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double maxDivingDepth() {
        return 2D;
    }

    @Override
    public float getSizeFactor() {
        return 0.6F;
    }

    @Override
    public float getAdjustedXOffset() {
        if (!this.isEyeInFluid(FluidTags.WATER)) {
            return -0.1F;
        }
        return 0F;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isEyeInFluid(FluidTags.WATER)) {
            return 0.2F;
        }
        return -0.5F;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height * 0.65F;
    }
}
