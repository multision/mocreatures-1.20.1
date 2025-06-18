/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHealth;

import net.minecraft.client.resources.language.I18n; // was net.minecraft.client.resources.I18n :contentReference[oaicite:0]{index=0}
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;               // was CompoundNBT
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;     // same
import net.minecraft.server.level.ServerLevel;       // replaces IServerWorld :contentReference[oaicite:1]{index=1}
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource; // was net.minecraft.util.DamageSource
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;     // replaces AttributeModifierMap.MutableAttribute :contentReference[oaicite:4]{index=4}
import net.minecraft.world.entity.ai.attributes.Attributes;            // same
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;                       // replaces MonsterEntity :contentReference[oaicite:5]{index=5}
import net.minecraft.world.entity.ai.navigation.PathNavigation;          // replaces PathNavigator :contentReference[oaicite:9]{index=9}
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;     // replaces PathNavigateFlyer
import net.minecraft.world.level.Level;                                   // replaces World
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;                                     // replaces Vector3d :contentReference[oaicite:14]{index=14}
import net.minecraft.network.syncher.EntityDataAccessor;                   // replaces DataParameter :contentReference[oaicite:15]{index=15}
import net.minecraft.network.syncher.EntityDataSerializers;                // replaces DataSerializers
import net.minecraft.network.syncher.SynchedEntityData;                    // replaces EntityDataManager
import net.minecraft.world.Difficulty;                                     // same
import net.minecraft.world.DifficultyInstance;                             // same

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

/**
 * Updated for Minecraft 1.20.1 (Forge/Mojang mappings).
 */
public abstract class MoCEntityMob extends Monster implements IMoCEntity {

    protected static final EntityDataAccessor<Boolean> ADULT = SynchedEntityData.defineId(MoCEntityMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> TYPE   = SynchedEntityData.defineId(MoCEntityMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> AGE    = SynchedEntityData.defineId(MoCEntityMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<String> NAME_STR = SynchedEntityData.defineId(MoCEntityMob.class, EntityDataSerializers.STRING);
    protected boolean divePending;
    protected String texture;
    protected PathNavigation navigatorWater;
    protected PathNavigation navigatorFlyer;
    protected EntityAIWanderMoC2 wander;

    protected MoCEntityMob(EntityType<? extends MoCEntityMob> type, Level world) {
        super(type, world);
        this.texture = "blank.jpg";
        this.moveControl = new EntityAIMoverHelperMoC(this); // custom MoveControl
        this.navigatorWater = new WaterBoundPathNavigation(this, world);                 // was SwimmerPathNavigator :contentReference[oaicite:16]{index=16}
        this.navigatorFlyer = new FlyingPathNavigation(this, world);               // custom Flyer nav
        this.wander = new EntityAIWanderMoC2(this, 1.0D, 80);
        this.goalSelector.addGoal(4, this.wander);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Component getName() {
        String entityString = this.getType().getDescriptionId();
        if (!MoCreatures.proxy.verboseEntityNames || entityString == null) {
            return super.getName();
        }
        String translationKey = "entity." + entityString + ".verbose.name";
        String translatedString = I18n.get(translationKey);
        return !translatedString.equals(translationKey)
                ? Component.translatable(translationKey)
                : super.getName();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.7F)
                .add(Attributes.ATTACK_DAMAGE, 2D)
                .add(Attributes.MAX_HEALTH, 20.0D);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                   MobSpawnType reason, @Nullable SpawnGroupData spawnData,
                                   @Nullable CompoundTag dataTag) {
        selectType();
        return super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture(this.texture);
    }

    /**
     * Put your code to choose a texture / the mob type in here. Will be called by default MocEntity constructors.
     */
    @Override
    public void selectType() {
        setTypeMoC(1);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ADULT, false);
        this.entityData.define(TYPE, 0);
        this.entityData.define(AGE, 45);
        this.entityData.define(NAME_STR, "");
    }

    @Override
    public int getTypeMoC() {
        return this.entityData.get(TYPE);
    }

    @Override
    public void setTypeMoC(int i) {
        this.entityData.set(TYPE, i);
    }

    @Override
    public boolean getIsAdult() {
        return this.entityData.get(ADULT);
    }

    @Override
    public void setAdult(boolean flag) {
        this.entityData.set(ADULT, flag);
    }

    @Override
    public boolean getIsTamed() {
        return false;
    }

    @Override
    public String getPetName() {
        return this.entityData.get(NAME_STR);
    }

    @Override
    public void setPetName(String name) {
        this.entityData.set(NAME_STR, name == null ? "" : name);
    }

    @Override
    public int getMoCAge() {
        return this.entityData.get(AGE);
    }

    @Override
    public void setMoCAge(int i) {
        this.entityData.set(AGE, i);
        if (getMoCAge() >= getMaxAge()) {
            setAdult(true);
        }
    }

    @Nullable
    public UUID getOwnerId() {
        return null;
    }

    @Override
    public int getOwnerPetId() {
        return 0;
    }

    @Override
    public void setOwnerPetId(int petId) {
    }

    public static boolean getCanSpawnHere(EntityType<? extends MoCEntityMob> type, ServerLevel world, MobSpawnType reason, BlockPos pos, Random randomIn) {
        boolean willSpawn = Monster.checkAnyLightMonsterSpawnRules(type, world, reason, pos, (RandomSource) randomIn);
        boolean debug = MoCreatures.proxy.debug;
        if (willSpawn && debug) {
            MoCreatures.LOGGER.info("Mob: {} at: {} State: {} biome: {}", type.getDescription(), pos,
                    world.getBlockState(pos), MoCTools.biomeName(world, pos));
        }
        return willSpawn;
    }

    public boolean entitiesToIgnore(Entity entity) {
        return (!(entity instanceof Mob))
                || (entity instanceof Monster)
                || (entity instanceof MoCEntityEgg)
                || entity instanceof MoCEntityKittyBed
                || entity instanceof MoCEntityLitterBox
                || (this.getIsTamed() && entity instanceof IMoCEntity && ((IMoCEntity) entity).getIsTamed())
                || (entity instanceof Wolf && !MoCreatures.proxy.attackWolves)
                || (entity instanceof MoCEntityHorse && !MoCreatures.proxy.attackHorses);
    }

    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

    @SuppressWarnings("deprecated")
    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            if (getIsTamed() && this.random.nextInt(200) == 0) {
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with(
                        () -> new PacketDistributor.TargetPoint(
                                this.getX(), this.getY(), this.getZ(), 64, this.level().dimension()
                        )
                ), new MoCMessageHealth(this.getId(), this.getHealth()));
            }

            if (this.isDaylightSensitive() && this.level().isDay()) {
                float brightness = this.getLightLevelDependentMagicValue();
                if (brightness > 0.5F
                        && this.level().canSeeSky(new BlockPos(
                        Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ())))
                        && this.random.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F)
                {
                    this.setSecondsOnFire(8);
                }
            }

            if (getMoCAge() == 0) setMoCAge(getMaxAge() - 10); // fixes tiny creatures spawned by error
            if (!getIsAdult() && this.random.nextInt(300) == 0) {
                setMoCAge(getMoCAge() + 1);
                if (getMoCAge() >= getMaxAge()) {
                    setAdult(true);
                }
            }

            if (getIsFlying() && this.getNavigation().isDone() && !isMovementCeased() && this.getTarget() == null && this.random.nextInt(20) == 0) {
                this.wander.makeUpdate();
            }
        }

        this.getNavigation().tick(); // was getNavigator().tick() :contentReference[oaicite:17]{index=17}
        super.tick();
    }

    protected int getMaxAge() {
        return 100;
    }

    protected boolean isDaylightSensitive() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!this.level().isClientSide && getIsTamed()) {
            MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with(
                    () -> new PacketDistributor.TargetPoint(
                            this.getX(), this.getY(), this.getZ(), 64, this.level().dimension()
                    )
            ), new MoCMessageHealth(this.getId(), this.getHealth()));
        }
        return super.hurt(source, amount);
    }

    /** Boolean used to select pathfinding behavior */
    public boolean isFlyer() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Adult", getIsAdult());
        tag.putInt("Edad", getMoCAge());
        tag.putString("Name", getPetName());
        tag.putInt("TypeInt", getTypeMoC());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setAdult(tag.getBoolean("Adult"));
        setMoCAge(tag.getInt("Edad"));
        setPetName(tag.getString("Name"));
        setTypeMoC(tag.getInt("TypeInt"));
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        if (!isFlyer()) {
            return super.causeFallDamage(distance, damageMultiplier, source);
        }
        return false;
    }

    @Override
    public boolean onClimbable() {
        return isFlyer() ? false : super.onClimbable();
    }

    @Override
    public void travel(Vec3 delta) {
        if (!isFlyer()) {
            super.travel(delta);
            return;
        }
        this.navigateFlying(delta);
    }

    protected void navigateFlying(Vec3 delta) {
        if (this.isAlive() && !this.level().isClientSide) {
            this.moveRelative(0.1F, delta);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(delta);
        }
    }

    /**
     * Used to synchronize the attack animation between server and client
     */
    @Override
    public void performAnimation(int attackType) {
    }

    @Override
    public int nameYOffset() {
        return 0;
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName()
                && !getPetName().isEmpty()
                && !this.isPassenger()
                && this.getVehicle() == null;
    }

    @Override
    public void makeEntityJump() {
        // TODO
    }

    @Override
    public void makeEntityDive() {
        this.divePending = true;
    }

    @Override
    public void remove(RemovalReason reason) {
        // Prevent tamed entities from being duplicated on the client
        if (!this.level().isClientSide && getIsTamed() && this.getHealth() > 0) {
            return;
        }
        super.remove(reason);
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    @Override
    public void setArmorType(int i) {
    }

    @Override
    public float pitchRotationOffset() {
        return 0F;
    }

    @Override
    public float rollRotationOffset() {
        return 0F;
    }

    @Override
    public float yawRotationOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedZOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedXOffset() {
        return 0F;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    public boolean isMovementCeased() {
        return false;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return this.level().getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public double getDivingDepth() {
        return 0;
    }

    @Override
    public boolean isDiving() {
        return false;
    }

    @Override
    public void forceEntityJump() {
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean flag = target.hurt(this.damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (flag) {
            this.doEnchantDamageEffects(this, target);
        }
        return flag;
    }

    @Override
    public int maxFlyingHeight() {
        return 5;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public PathNavigation getNavigation() {
        if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        if (this.isFlyer()) {
            return this.navigatorFlyer;
        }
        return super.getNavigation();
    }

    public boolean isAmphibian() {
        return false;
    }

    @Override
    public boolean getIsFlying() {
        return isFlyer();
    }

    @Override
    public boolean isInvisible() {
        return false;
    }

    @Override
    public boolean getIsGhost() {
        return false;
    }
}
