/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.tameable.IMoCTameable;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook; // in 1.20.1, FishingBobberEntity is now FishingHook
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * Updated for Minecraft 1.20.1 (Forge/Yarn).  Most of the "old" 1.16.5 methods
 * and classes have been swapped out for their 1.20.1 equivalents.
 */
public abstract class MoCEntityAquatic extends WaterAnimal implements IMoCEntity {

    // === Data parameters (formerly EntityDataManager + DataSerializers) ===
    private static final EntityDataAccessor<Boolean> ADULT =
            SynchedEntityData.defineId(MoCEntityAquatic.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TYPE =
            SynchedEntityData.defineId(MoCEntityAquatic.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AGE =
            SynchedEntityData.defineId(MoCEntityAquatic.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> NAME_STR =
            SynchedEntityData.defineId(MoCEntityAquatic.class, EntityDataSerializers.STRING);

    protected boolean fishHooked;
    protected boolean divePending;
    protected boolean jumpPending;
    protected boolean isEntityJumping;
    protected int outOfWater;
    protected boolean riderIsDisconnecting;
    protected float moveSpeed;
    protected String texture;
    protected PathNavigation navigatorWater;
    protected int temper;
    private boolean diving;
    private int divingCount;
    private int mountCount;
    private boolean updateDivingDepth = false;
    private double divingDepth;

    public MoCEntityAquatic(EntityType<? extends MoCEntityAquatic> type, Level level) {
        super(type, level);
        this.outOfWater = 0;
        setTemper(50);
        this.setNewDivingDepth();
        this.riderIsDisconnecting = false;
        this.texture = "blank.jpg";
        this.navigatorWater = new WaterBoundPathNavigation(this, level);
        this.moveControl = new EntityAIMoverHelperMoC(this);
    }

    // === ATTRIBUTE REGISTRATION ===
    /**
     * In 1.20.1, attributes are registered via a static method returning
     * AttributeSupplier.Builder.  Forge will pick this up from your
     * DeferredRegister<EntityType<?>> setup.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.7D)
                .add(Attributes.MAX_HEALTH, 6.0D);
    }

    // === SYNCHED DATA DEFINITION ===
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ADULT, false);
        this.entityData.define(TYPE, 0);
        this.entityData.define(AGE, 45);
        this.entityData.define(NAME_STR, "");
    }

    // === NAMING / VERBOSE NAMES (CLIENT‐ONLY) ===
    @OnlyIn(Dist.CLIENT)
    @Override
    public Component getName() {
        String entityKey = this.getType().getDescriptionId();
        if (!MoCreatures.proxy.verboseEntityNames || entityKey == null) {
            return super.getName();
        }
        String translationKey = "entity." + entityKey + ".verbose.name";
        String localized = I18n.get(translationKey);
        if (!localized.equals(translationKey)) {
            return Component.translatable(translationKey);
        }
        return super.getName();
    }

    // === SPAWN / TYPE SELECTION ===
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag
    ) {
        this.selectType();
        return super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
    }

    @Override
    public void selectType() {
        setTypeMoC(1);
    }

    // === DATA ACCESSORS (formerly getTypeMoC(), setTypeMoC(), etc.) ===
    @Override
    public int getTypeMoC() {
        return this.entityData.get(TYPE);
    }

    @Override
    public void setTypeMoC(int i) {
        this.entityData.set(TYPE, i);
    }

    @Override
    public int getOwnerPetId() {
        return -1;
    }

    @Override
    public void setOwnerPetId(int i) { }

    @Nullable
    @Override
    public UUID getOwnerId() {
        return null;
    }

    @Override
    public boolean getIsTamed() {
        return false;
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
    public String getPetName() {
        return this.entityData.get(NAME_STR);
    }

    @Override
    public void setPetName(String name) {
        this.entityData.set(NAME_STR, name);
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

    public int getTemper() {
        return this.temper;
    }

    public void setTemper(int i) {
        this.temper = i;
    }

    public int getMaxTemper() {
        return 100;
    }

    // === HELPER FOR SMOOTH ROTATION ===
    public float clampAngle(float current, float target, float maxDelta) {
        float delta = target - current;
        while (delta < -180F) {
            delta += 360F;
        }
        while (delta >= 180F) {
            delta -= 360F;
        }
        if (delta > maxDelta) {
            delta = maxDelta;
        }
        if (delta < -maxDelta) {
            delta = -maxDelta;
        }
        return current + delta;
    }

    public void faceItem(double x, double y, double z, float maxAngleDiff) {
        double dx = x - this.getX();
        double dy = z - this.getZ();
        double dz = y - this.getY();
        double horizontalDist = Math.sqrt(dx * dx + dy * dy);
        float yawTarget = (float) (Math.atan2(dy, dx) * (180D / Math.PI)) - 90F;
        float pitchTarget = (float) (Math.atan2(dz, horizontalDist) * (180D / Math.PI));
        this.setXRot(-clampAngle(this.getXRot(), pitchTarget, maxAngleDiff));
        this.setYRot(clampAngle(this.getYRot(), yawTarget, maxAngleDiff));
    }

    // === BIOME & STEP SOUND OVERRIDES ===
    @Override
    public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
        // Always allow spawn eggs to work
        if (reason == MobSpawnType.SPAWN_EGG) {
            return true;
        }
        // Check if the entity is in water
        BlockPos pos = this.blockPosition();
        return world.getFluidState(pos).is(FluidTags.WATER);
    }

    @Override
    protected void playStepSound(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        // no step sound for aquatic
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        return false;
    }

    // === FINDS CLOSEST FISH ITEM ENTITY IN WATER ===
    /**
     * Find the nearest ItemEntity within `radius` that is tagged as a fish and is in water.
     */
    public ItemEntity getClosestFish(Entity seeker, double radius) {
        double closestSq = -1D;
        ItemEntity closest = null;

        // Build an AABB around this entity with the given radius
        AABB searchBox = this.getBoundingBox().inflate(radius);

        // Get all ItemEntity instances in the box
        List<ItemEntity> itemsInRange = this.level().getEntitiesOfClass(
                ItemEntity.class,
                searchBox,
                // Filter lambda: only keep those whose ItemStack is in the FISHES tag and that are in water
                itemEntity -> {
                    ItemStack stack = itemEntity.getItem();
                    return stack.is(ItemTags.FISHES) && itemEntity.isInFluidType(ForgeMod.WATER_TYPE.get());
                }
        );

        // Iterate to find the absolute closest one
        for (ItemEntity fishEntity : itemsInRange) {
            double sqDist = fishEntity.distanceToSqr(seeker);
            if ((radius < 0.0D || sqDist < (radius * radius)) &&
                    (closest == null || sqDist < closestSq)) {
                closestSq = sqDist;
                closest = fishEntity;
            }
        }

        return closest;
    }


    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    // === JUMP & JUMP FLAGS ===
    public double getCustomJump() {
        return 0.4D;
    }

    public boolean getIsJumping() {
        return this.isEntityJumping;
    }

    public void setIsJumping(boolean flag) {
        this.isEntityJumping = flag;
    }

    @Override
    public void jumpFromGround() {
        this.jumpPending = true;
    }

    protected void moveToNextEntity(Entity target) {
        if (target != null) {
            int i = Mth.floor(target.getX());
            int j = Mth.floor(target.getY());
            int k = Mth.floor(target.getZ());
            faceItem(i, j, k, 30F);

            Vec3 motion = this.getDeltaMovement();
            if (this.getX() < i) {
                double dx = target.getX() - this.getX();
                if (dx > 0.5D) {
                    motion = motion.add(0.05D, 0.0D, 0.0D);
                }
            } else {
                double dx = this.getX() - target.getX();
                if (dx > 0.5D) {
                    motion = motion.subtract(0.05D, 0.0D, 0.0D);
                }
            }
            if (this.getZ() < k) {
                double dz = target.getZ() - this.getZ();
                if (dz > 0.5D) {
                    motion = motion.add(0.0D, 0.0D, 0.05D);
                }
            } else {
                double dz = this.getZ() - target.getZ();
                if (dz > 0.5D) {
                    motion = motion.subtract(0.0D, 0.0D, 0.05D);
                }
            }
            this.setDeltaMovement(motion);
        }
    }

    public double getCustomSpeed() {
        return 1.5D;
    }

    @Override
    public boolean isDiving() {
        return this.diving;
    }

    @Override
    public void makeEntityJump() {
        // Default no-op; override if your mob needs special behavior
    }

    public void riding() {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player) {
            Player rider = (Player) this.getControllingPassenger();
            List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(1.0D, 0.0D, 1.0D));
            for (Entity ent : list) {
                if (ent.isRemoved()) {
                    continue;
                }
                ent.playerTouch(rider);
                if (!(ent instanceof Monster)) {
                    continue;
                }
                float dist = (float) this.distanceTo(ent);
                if (dist < 2.0F && this.random.nextInt(10) == 0) {
                    ent.hurt(
                            ent.level().damageSources().mobAttack((LivingEntity) ent),
                            (float) ((Monster) ent).getAttributeValue(Attributes.ATTACK_DAMAGE)
                    );
                }
            }
            if (rider.isSecondaryUseActive()) {
                this.makeEntityDive();
            }
        }
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            if (this.isVehicle() && this.getControllingPassenger() instanceof Player) {
                riding();
                this.mountCount = 1;
            }

            if (this.mountCount > 0 && ++this.mountCount > 50) {
                this.mountCount = 0;
            }
            if (getMoCAge() == 0) {
                setMoCAge(getMaxAge() - 10); // fixes tiny creatures spawned by error
            }
            if (!getIsAdult() && this.random.nextInt(300) == 0) {
                setMoCAge(getMoCAge() + 1);
                if (getMoCAge() >= getMaxAge()) {
                    setAdult(true);
                }
            }

            this.getNavigation().tick();

            // Update diving depth after movement
            if (!this.getNavigation().isDone()) {
                if (!this.updateDivingDepth) {
                    float targetDepth = MoCTools.distanceToSurface(
                            this.moveControl.getWantedX(),
                            this.moveControl.getWantedY(),
                            this.moveControl.getWantedZ(),
                            this.level()
                    );
                    setNewDivingDepth(targetDepth);
                    this.updateDivingDepth = true;
                }
            } else {
                this.updateDivingDepth = false;
            }

            if (isMovementCeased() || this.random.nextInt(200) == 0) {
                this.getNavigation().stop();
            }

            if (isFisheable() && !this.fishHooked && this.random.nextInt(30) == 0) {
                getFished();
            }

            if (this.fishHooked && this.random.nextInt(200) == 0) {
                this.fishHooked = false;
                List<FishingHook> hooks = this.level().getEntitiesOfClass(
                        FishingHook.class,
                        this.getBoundingBox().inflate(2.0D),
                        hook -> hook.getHookedIn() == this
                );
                for (FishingHook hook : hooks) {
                    // TODO: Call hook.onHit (Reflection might be needed, as onEntityHit is private).
                    // hook.onEntityHit(new EntityRayTraceResult(this));
                }
            }
        }

        this.moveSpeed = 0.7F;

        if (isSwimming()) {
            this.outOfWater = 0;
            this.setAirSupply(800);
        } else {
            this.outOfWater++;
            Vec3 motion = this.getDeltaMovement().subtract(0.0, 0.1, 0.0);
            this.setDeltaMovement(motion);

            if (this.onGround() && this.outOfWater % 20 == 0) {
                // Flop around if out of water
                Vec3 randomMotion = new Vec3(
                        (this.random.nextDouble() - 0.5D) * 0.5D,
                        0.4D,
                        (this.random.nextDouble() - 0.5D) * 0.5D
                );
                this.setDeltaMovement(randomMotion);
                this.setOnGround(false);
            }

            if (this.outOfWater > 20) {
                this.getNavigation().stop();
            }
            if (this.outOfWater > 300 && (this.outOfWater % 40) == 0) {
                Vec3 randMotion = this.getDeltaMovement().add(
                        (this.random.nextDouble() * 0.2D - 0.1D),
                        0.3D,
                        (this.random.nextDouble() * 0.2D - 0.1D)
                );
                this.setDeltaMovement(randMotion);
                this.hurt(this.damageSources().drown(), 1.0F);
            }
        }

        if (!this.diving) {
            if (!this.isVehicle() && this.getTarget() == null && !this.getNavigation().isDone() && this.random.nextInt(500) == 0) {
                this.diving = true;
            }
        } else {
            this.divingCount++;
            if (this.divingCount > 100 || this.isVehicle()) {
                this.diving = false;
                this.divingCount = 0;
            }
        }

        super.tick();
    }

    public boolean isSwimming() {
        return this.isEyeInFluid(FluidTags.WATER);
    }

    // === NBT READ/WRITE (1.20.1) ===
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Adult", getIsAdult());
        compound.putInt("Edad", getMoCAge());
        compound.putString("Name", getPetName());
        compound.putInt("TypeInt", getTypeMoC());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setAdult(compound.getBoolean("Adult"));
        setMoCAge(compound.getInt("Edad"));
        setPetName(compound.getString("Name"));
        setTypeMoC(compound.getInt("TypeInt"));
    }

    public void setTypeInt(int i) {
        setTypeMoC(i);
        selectType();
    }

    @Override
    public void performAnimation(int attackType) {
    }

    @Override
    public void checkDespawn() {
        Player player = this.level().getNearestPlayer(this, -1.0D);
        if (player != null) {
            double d0 = player.distanceToSqr(this);
            int instantDist = this.getType().getCategory().getDespawnDistance();
            int j = instantDist * instantDist;
            if (d0 > (double) j && this.removeWhenFarAway(d0)) {
                this.playerTouch(player); // or simply remove
                this.remove(RemovalReason.DISCARDED);
            }
            int randomDist = this.getType().getCategory().getDespawnDistance();
            int l = randomDist * randomDist;
            if (this.tickCount > 1800 && this.random.nextInt(800) == 0 && d0 > (double) l && this.removeWhenFarAway(d0)) {
                this.remove(RemovalReason.DISCARDED);
            } else if (d0 < (double) l) {
                this.noActionTime = 0;
            }
        }
    }

    @Override
    public int nameYOffset() {
        return 0;
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName()
                && !getPetName().isEmpty()
                && !this.isVehicle();
    }

    @Override
    public void makeEntityDive() {
        this.divePending = true;
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    // === CUSTOM SPAWN CHECK ===
    public static boolean getCanSpawnHere(
            EntityType<MoCEntityAquatic> type,
            LevelAccessor world,
            MobSpawnType reason,
            BlockPos pos,
            RandomSource randomIn
    ) {
        // Always allow spawn eggs
        if (reason == MobSpawnType.SPAWN_EGG) {
            return true;
        }
        
        // Check if the spawn position is in water
        boolean isInWater = world.getFluidState(pos).is(FluidTags.WATER);
        
        // Ensure it's not too deep and is actually in water
        boolean willSpawn = isInWater && pos.getY() >= world.getSeaLevel() - 12;
        
        boolean debug = MoCreatures.proxy.debug;
        if (debug) {
            MoCreatures.LOGGER.info(
                    "Aquatic: {} at {} State: {} biome: {} isInWater: {}",
                    type.getDescription(),
                    pos,
                    world.getBlockState(pos),
                    MoCTools.biomeName((Level) world, pos),
                    isInWater
            );
        }
        return willSpawn;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (usesNewAI()) {
            return super.hurt(source, amount);
        }

        if (isNotScared()) {
            LivingEntity oldTarget = this.getTarget();
            setTarget(oldTarget);
            return super.hurt(source, amount);
        }

        return super.hurt(source, amount);
    }

    protected boolean canBeTrappedInNet() {
        return (this instanceof IMoCTameable) && getIsTamed();
    }

    protected void dropMyStuff() { }

    protected boolean isMyHealFood(ItemStack stack) {
        return false;
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

    // === FISHINGHOOK INTERACTION ===
    private void getFished() {
        Player nearest = this.level().getNearestPlayer(this, 18D);
        if (nearest != null) {
            FishingHook fishHook = nearest.fishing;
            if (fishHook != null && fishHook.getHookedIn() == null) {
                float dist = fishHook.distanceTo(this);
                if (dist > 1F) {
                    MoCTools.setPathToEntity(this, fishHook, dist);
                } else {
                    // TODO: call fishHook.onHit(new EntityRayTraceResult(this));
                    this.fishHooked = true;
                }
            }
        }
    }

    protected boolean isFisheable() {
        return false;
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
    public boolean isNotScared() {
        return false;
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
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && this.level().getDifficulty() != Difficulty.PEACEFUL;
    }

    // === TRAVEL / MOVEMENT ===
    @Override
    public void travel(Vec3 movementInput) {
        if (this.isInWater()) {
            if (this.isVehicle()) {
                Entity passenger = this.getControllingPassenger();
                if (passenger instanceof LivingEntity) {
                    this.moveWithRider((LivingEntity) passenger, movementInput);
                }
                return;
            }
            this.moveRelative(0.1F, movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));

            if (this.getTarget() == null && this.getNavigation().isDone()) {
                this.setDeltaMovement(this.getDeltaMovement().subtract(0.0, 0.005, 0.0));
            }

            this.calculateEntityAnimation(true); // sets limbSwing, etc.
        } else {
            super.travel(movementInput);
        }
    }

    /**
     * Riding code when ridden by a player or other entity.
     */
    public void moveWithRider(LivingEntity passenger, Vec3 movementInput) {
        if (passenger == null) {
            return;
        }
        // Buckle rider if out of water and untamed
        if (this.isVehicle() && !getIsTamed() && !isSwimming()) {
            this.removePassenger(passenger);
            return;
        }

        if (this.isVehicle() && !getIsTamed()) {
            this.moveWithRiderUntamed(passenger, movementInput);
            return;
        }

        if (this.isVehicle() && getIsTamed()) {
            // Align rotation with the rider
            this.yRotO = passenger.yRotO;
            this.xRotO = passenger.xRotO * 0.5F;
            this.setYHeadRot(this.yRotO);
            this.yHeadRot = this.yRotO;
            this.yBodyRot = this.yRotO;

            float forward = passenger.xxa * 0.35F;
            float yawRad = (float) (passenger.zza * (this.getCustomSpeed() / 5.0D));
            Vec3 inputMovement = new Vec3(forward, movementInput.y, yawRad);

            if (this.jumpPending) {
                if (this.isSwimming()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0, getCustomJump(), 0.0));
                }
                this.jumpPending = false;
            }
            // Prevent sinking
            if (this.getDeltaMovement().y < 0D && isSwimming()) {
                this.setDeltaMovement(this.getDeltaMovement().x, 0D, this.getDeltaMovement().z);
            }
            if (this.divePending) {
                this.divePending = false;
                this.setDeltaMovement(this.getDeltaMovement().subtract(0.0, 0.3, 0.0));
            }
            this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
            super.travel(inputMovement);
            this.moveRelative(0.1F, inputMovement);
        }
    }

    public void moveWithRiderUntamed(LivingEntity passenger, Vec3 movementInput) {
        if ((this.isVehicle()) && !getIsTamed()) {
            if ((this.random.nextInt(5) == 0) && !getIsJumping() && this.jumpPending) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, getCustomJump(), 0.0));
                setIsJumping(true);
                this.jumpPending = false;
            }
            if (this.random.nextInt(10) == 0) {
                Vec3 randomMotion = this.getDeltaMovement().add(
                        this.random.nextDouble() / 30D,
                        0.0,
                        this.random.nextDouble() / 10D
                );
                this.setDeltaMovement(randomMotion);
            }
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level().isClientSide && this.random.nextInt(100) == 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.9D, -0.3D));
                passenger.stopRiding();
            }
            if (this.onGround()) {
                setIsJumping(false);
            }
            if (!this.level().isClientSide && this instanceof IMoCTameable && passenger instanceof Player) {
                int chance = (getMaxTemper() - getTemper());
                if (chance <= 0) {
                    chance = 1;
                }
                if (this.random.nextInt(chance * 8) == 0) {
                    MoCTools.tameWithName((Player) passenger, (IMoCTameable) this);
                }
            }
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 300;
    }

    @Override
    protected void handleAirSupply(int air) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(air - 1);
            if (this.getAirSupply() == -30) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 1.0F);
                Vec3 kick = this.getDeltaMovement().add(
                        this.random.nextDouble() / 10D,
                        0.0,
                        this.random.nextDouble() / 10D
                );
                this.setDeltaMovement(kick);
            }
        } else {
            this.setAirSupply(300);
        }
    }

    protected boolean usesNewAI() {
        return false;
    }

    @Override
    public PathNavigation getNavigation() {
        if (this.isInWater()) {
            return this.navigatorWater;
        }
        return super.getNavigation();
    }

    /**
     * Distance the entity will float under the surface. 0.0 = at surface, 1.0 = one block below.
     */
    @Override
    public double getDivingDepth() {
        return this.divingDepth;
    }

    protected void setNewDivingDepth(double setDepth) {
        if (setDepth != 0.0D) {
            if (setDepth > maxDivingDepth()) {
                setDepth = maxDivingDepth();
            }
            if (setDepth < minDivingDepth()) {
                setDepth = minDivingDepth();
            }
            this.divingDepth = setDepth;
        } else {
            this.divingDepth = this.random.nextDouble() * (maxDivingDepth() - minDivingDepth()) + minDivingDepth();
        }
    }

    protected void setNewDivingDepth() {
        setNewDivingDepth(0.0D);
    }

    protected double minDivingDepth() {
        return 0.20D;
    }

    protected double maxDivingDepth() {
        return 3.0D;
    }

    @Override
    public void forceEntityJump() {
        this.makeEntityJump();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float yawRotationOffset() {
        double wobble = 0F;
        if (this.getDeltaMovement().x != 0D || this.getDeltaMovement().z != 0D) {
            wobble = Math.sin((this.tickCount) * 0.5D) * 8D;
        }
        return (float) wobble;
    }

    public int getMaxAge() {
        return 100;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!(target instanceof LivingEntity)) {
            return false;
        }
        if (!target.isInWater()) {
            return false;
        }
        boolean flag = ((LivingEntity) target).hurt(this.damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (flag) {
            this.doEnchantDamageEffects(this, target);
        }
        return flag;
    }

    @Override
    public void setLeashedTo(Entity entity, boolean sendAttachNotification) {
        if (this.getIsTamed() && entity instanceof Player) {
            Player player = (Player) entity;
            if (MoCreatures.proxy.enableOwnership && this.getOwnerId() != null &&
                    !player.getUUID().equals(this.getOwnerId()) &&
                    !MoCTools.isThisPlayerAnOP((ServerPlayer) player)) {
                return;
            }
        }
        super.canBeLeashed((Player) entity);
    }

    @Override
    public boolean canChangeDimensions() {
        return !this.isVehicle();
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0).getControllingPassenger();
    }

    @Override
    public boolean getIsGhost() {
        return false;
    }

    @Override
    public int maxFlyingHeight() {
        return 1;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    public boolean isFlyer() {
        return false;
    }

    @Override
    public boolean getIsFlying() {
        return false;
    }

    protected SoundEvent getAngrySound() {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture(this.texture);
    }

    @Override
    public boolean isMovementCeased() {
        return ((!isSwimming() && !this.isPassenger()) || this.isPassenger() || this.getIsSitting());
    }

    @Override
    public boolean checkSpawningBiome() {
        return true;
    }
}
