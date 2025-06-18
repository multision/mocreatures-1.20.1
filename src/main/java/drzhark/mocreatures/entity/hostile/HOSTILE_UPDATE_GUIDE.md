# Hostile Entities Update Guide (1.16.5 to 1.20.1)

This guide covers specific changes needed for updating the hostile entities in Mo' Creatures from Minecraft 1.16.5 to 1.20.1.

## Common Import Changes

```java
// Old imports
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

// New imports
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
```

## Entity Data Field Changes

```java
// Old
private static final DataParameter<Boolean> DATA_FIELD = EntityDataManager.createKey(MoCEntityRat.class, DataSerializers.BOOLEAN);

// New
private static final EntityDataAccessor<Boolean> DATA_FIELD = SynchedEntityData.defineId(MoCEntityRat.class, EntityDataSerializers.BOOLEAN);
```

## Constructor Updates

```java
// Old
public MoCEntityRat(EntityType<? extends MoCEntityRat> type, World world) {
    super(type, world);
    // Implementation
}

// New
public MoCEntityRat(EntityType<? extends MoCEntityRat> type, Level world) {
    super(type, world);
    // Implementation
}
```

## Method Renames

| Old Method | New Method |
|------------|------------|
| `registerData()` | `defineSynchedData()` |
| `dataManager.register()` | `entityData.define()` |
| `dataManager.get()` | `entityData.get()` |
| `dataManager.set()` | `entityData.set()` |
| `livingTick()` | `tick()` |
| `getEntityInteractionResult()` | `mobInteract()` |
| `attackEntityFrom()` | `hurt()` |
| `world.isRemote` | `level().isClientSide()` |
| `world` | `level()` |
| `getPosX()`, `getPosY()`, `getPosZ()` | `getX()`, `getY()`, `getZ()` |
| `getMotion()` | `getDeltaMovement()` |
| `setMotion()` | `setDeltaMovement()` |
| `isBeingRidden()` | `isVehicle()` |
| `getRidingEntity()` | `getVehicle()` |
| `player.abilities.isCreativeMode` | `player.isCreative()` |
| `player.setHeldItem(hand, stack)` | `player.setItemInHand(hand, stack)` |
| `player.getHeldItem(hand)` | `player.getItemInHand(hand)` |
| `ActionResultType.SUCCESS` | `InteractionResult.SUCCESS` |
| `ActionResultType.PASS` | `InteractionResult.PASS` |
| `remove(keepData)` | `remove(Entity.RemovalReason reason)` |
| `getAIMoveSpeed()` | `getSpeed()` |
| `world.addEntity()` | `level().addFreshEntity()` |
| `rotationPitch`, `rotationYaw` | `getXRot()`, `getYRot()` |
| `prevRotationPitch`, `prevRotationYaw` | `xRotO`, `yRotO` |
| `setPosition()` | `setPos()` |
| `setAttackTarget()` | `setTarget()` |
| `getAttackTarget()` | `getTarget()` |
| `getExperiencePoints()` | `getExperienceReward()` |
| `damagesource.getTrueSource()` | `damagesource.getEntity()` |

## AI Goal Package Changes

```java
// Old
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

// New
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
```

## Attribute System Updates

```java
// Old
public static AttributeModifierMap.MutableAttribute registerAttributes() {
    return MobEntity.registerAttributes()
        .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
        .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
        .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
}

// New
public static AttributeSupplier.Builder createAttributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 20.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.25D)
        .add(Attributes.ATTACK_DAMAGE, 2.0D);
}
```

## NBT Changes

```java
// Old
@Override
public void writeAdditional(CompoundNBT nbttagcompound) {
    super.writeAdditional(nbttagcompound);
    // Implementation
}

@Override
public void readAdditional(CompoundNBT nbttagcompound) {
    super.readAdditional(nbttagcompound);
    // Implementation
}

// New
@Override
public void addAdditionalSaveData(CompoundTag nbttagcompound) {
    super.addAdditionalSaveData(nbttagcompound);
    // Implementation
}

@Override
public void readAdditionalSaveData(CompoundTag nbttagcompound) {
    super.readAdditionalSaveData(nbttagcompound);
    // Implementation
}
```

## Entity Query Methods

```java
// Old
List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(8D, 3D, 8D));

// New
List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(8D, 3D, 8D), entity -> entity != this);
```

## ResourceLocation for getLootTable

```java
// Old
@Nullable
@Override
protected ResourceLocation getLootTable() {
    return MoCLootTables.RAT;
}

// New
@Override
protected ResourceLocation getDefaultLootTable() {
    return MoCLootTables.RAT;
}
```

## DamageSource Changes

```java
// Old
DamageSource.GENERIC
source.getTrueSource()

// New
this.damageSources().generic()
source.getEntity()
```

## Entity Dimensions and Eye Height

```java
// Old
protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
    return this.getHeight() * 0.85F;
}

// New
@Override
protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
    return this.getBbHeight() * 0.85F;
}
```

## Sound Events

```java
// Old
SoundEvents.ENTITY_ZOMBIE_AMBIENT

// New
SoundEvents.ZOMBIE_AMBIENT
```

## Animation System Changes

```java
// Old
this.limbSwingAmount = 1.5F;
this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw;

// New
this.walkAnimation.setSpeed(1.5F);
this.yBodyRot = this.getYRot();
```

## Testing Tips

1. Pay special attention to attacking logic and AI systems
2. Test pathfinding, especially for custom hostile mobs
3. Test combat with players and other mobs
4. Verify any special abilities like special attacks
5. Check for proper hostile mob targeting behavior 