/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.HitResult;

public class MoCEntityKittyBed extends Mob {

    private static final EntityDataAccessor<Boolean> HAS_MILK = SynchedEntityData.defineId(MoCEntityKittyBed.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_FOOD = SynchedEntityData.defineId(MoCEntityKittyBed.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PICKED_UP = SynchedEntityData.defineId(MoCEntityKittyBed.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SHEET_COLOR = SynchedEntityData.defineId(MoCEntityKittyBed.class, EntityDataSerializers.INT);
    public float milkLevel;

    public MoCEntityKittyBed(EntityType<? extends MoCEntityKittyBed> type, Level world) {
        super(type, world);
        setNoAi(true);
        this.milkLevel = 0.0F;
    }

    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("kitty_bed.png");
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_MILK, false);
        this.entityData.define(HAS_FOOD, false);
        this.entityData.define(PICKED_UP, false);
        this.entityData.define(SHEET_COLOR, 0);
    }

    public boolean getHasFood() {
        return this.entityData.get(HAS_FOOD);
    }

    public void setHasFood(boolean flag) {
        this.entityData.set(HAS_FOOD, flag);
    }

    public boolean getHasMilk() {
        return this.entityData.get(HAS_MILK);
    }

    public void setHasMilk(boolean flag) {
        this.entityData.set(HAS_MILK, flag);
    }

    public boolean getPickedUp() {
        return this.entityData.get(PICKED_UP);
    }

    public void setPickedUp(boolean flag) {
        this.entityData.set(PICKED_UP, flag);
    }

    public int getSheetColor() {
        return this.entityData.get(SHEET_COLOR);
    }

    public void setSheetColor(int i) {
        this.entityData.set(SHEET_COLOR, i);
    }

    @Override
    public boolean isPushable() {
        return !this.isRemoved();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        return this.level().clip(new ClipContext(
            new Vec3(this.getX(), this.getY() + getEyeHeight(), this.getZ()),
            new Vec3(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ()),
            ClipContext.Block.COLLIDER,
            ClipContext.Fluid.NONE,
            this
        )).getType() == HitResult.Type.MISS;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.0D;
    }

    @Override
    public void handleEntityEvent(byte byte0) {
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && !getHasFood() && !getHasMilk()) {
            if (stack.is(MoCItems.PET_FOOD.get())) {
                if (!player.isCreative()) stack.shrink(1);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_KITTYBED_POURINGFOOD.get());
                setHasMilk(false);
                setHasFood(true);
            } else if (stack.is(Items.MILK_BUCKET)) {
                player.setItemInHand(hand, new ItemStack(Items.BUCKET, 1));
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_KITTYBED_POURINGMILK.get());
                setHasMilk(true);
                setHasFood(false);
            }
            return InteractionResult.SUCCESS;
        }
        if (this.getVehicle() == null) {
            if (player.isCrouching()) {
                final int color = getSheetColor();
                player.getInventory().add(new ItemStack(MoCItems.KITTYBED[color].get(), 1));
                if (getHasFood()) player.getInventory().add(new ItemStack(MoCItems.PET_FOOD.get(), 1));
                else if (getHasMilk()) player.getInventory().add(new ItemStack(Items.MILK_BUCKET, 1));
                MoCTools.playCustomSound(this, SoundEvents.ITEM_PICKUP, 0.2F);
                this.remove(RemovalReason.DISCARDED);
            } else {
                setYHeadRot((float) MoCTools.roundToNearest90Degrees(this.getYHeadRot()) + 90.0F);
                MoCTools.playCustomSound(this, SoundEvents.ITEM_FRAME_ROTATE_ITEM);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void move(MoverType type, Vec3 pos) {
        if (!this.level().isClientSide() && (this.getVehicle() != null || !this.onGround() || !MoCreatures.proxy.staticBed)) {
            super.move(type, pos);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround()) {
            setPickedUp(false);
        }
        if (!this.level().isClientSide() && (getHasMilk() || getHasFood()) && this.isVehicle() && getFirstPassenger() instanceof MoCEntityKitty) {
            MoCEntityKitty kitty = (MoCEntityKitty) getFirstPassenger();
            if (kitty.getKittyState() != 12) {
                this.milkLevel += 0.003F;
                if (this.milkLevel > 2.0F) {
                    this.milkLevel = 0.0F;
                    setHasMilk(false);
                    setHasFood(false);
                }
            }
        }
        if (this.isPassenger()) MoCTools.dismountSneakingPlayer(this);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        setHasMilk(compound.getBoolean("HasMilk"));
        setSheetColor(compound.getInt("SheetColour"));
        setHasFood(compound.getBoolean("HasFood"));
        this.milkLevel = compound.getFloat("MilkLevel");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("HasMilk", getHasMilk());
        compound.putInt("SheetColour", getSheetColor());
        compound.putBoolean("HasFood", getHasFood());
        compound.putFloat("MilkLevel", this.milkLevel);
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        return false;
    }
}
