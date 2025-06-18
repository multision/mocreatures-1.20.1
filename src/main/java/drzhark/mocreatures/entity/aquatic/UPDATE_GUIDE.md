# Aquatic Entities Update Guide (1.16.5 to 1.20.1)

This guide covers specific changes needed for updating the aquatic entities in Mo' Creatures from Minecraft 1.16.5 to 1.20.1.

## Common Import Changes

```java
// Old imports
import net.minecraft.entity.*;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
```

## Entity Data Field Changes

```java
// Old
private static final DataParameter<Boolean> DATA_FIELD = EntityDataManager.createKey(MoCEntityFish.class, DataSerializers.BOOLEAN);

// New
private static final EntityDataAccessor<Boolean> DATA_FIELD = SynchedEntityData.defineId(MoCEntityFish.class, EntityDataSerializers.BOOLEAN);
```

## Constructor Updates

```java
// Old
public MoCEntityFish(EntityType<? extends MoCEntityFish> type, World world) {
    super(type, world);
    // Implementation
}

// New
public MoCEntityFish(EntityType<? extends MoCEntityFish> type, Level world) {
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
| `isBeingRidden()` | `isVehicle()` |
| `getRidingEntity()` | `getVehicle()` |
| `player.abilities.isCreativeMode` | `player.isCreative()` |
| `player.setHeldItem(hand, stack)` | `player.setItemInHand(hand, stack)` |
| `player.getHeldItem(hand)` | `player.getItemInHand(hand)` |
| `ActionResultType.SUCCESS` | `InteractionResult.SUCCESS` |
| `ActionResultType.PASS` | `InteractionResult.PASS` |
| `remove(keepData)` | `remove(Entity.RemovalReason reason)` |
| `getAIMoveSpeed()` | `getSpeed()` (remove method - use attributes) |
| `world.addEntity()` | `level().addFreshEntity()` |

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
public void writeAdditional(CompoundTag nbttagcompound) {
    super.addAdditionalSaveData(nbttagcompound);
    // Implementation
}

@Override
public void readAdditional(CompoundTag nbttagcompound) {
    super.readAdditionalSaveData(nbttagcompound);
    // Implementation
}
```

## Entity Remove Method

```java
// Old
@Override
public void remove(boolean keepData) {
    if (!this.world.isRemote && getIsTamed() && getHealth() > 0) {
        // Implementation
    } else {
        super.remove(keepData);
    }
}

// New
@Override
public void remove(Entity.RemovalReason reason) {
    if (!this.level().isClientSide() && getIsTamed() && getHealth() > 0) {
        // Implementation
    } else {
        super.remove(reason);
    }
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
    return MoCLootTables.FISH;
}

// New
@Override
protected ResourceLocation getDefaultLootTable() {
    return MoCLootTables.FISH;
}
```

## PacketDistributor Changes

```java
// Old
MoCMessageHandler.INSTANCE.send(
    PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
        this.getPosX(), this.getPosY(), this.getPosZ(), 64, this.world.getDimensionKey()
    )), 
    new MoCMessageHeart(this.getEntityId())
);

// New
MoCMessageHandler.INSTANCE.send(
    PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
        this.getX(), this.getY(), this.getZ(), 64, this.level().dimension()
    )), 
    new MoCMessageHeart(this.getId())
);
```

## Animation System Changes

```java
// Old
this.limbSwingAmount = 1.5F;

// New
this.walkAnimation.setSpeed(1.5F);
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

## Entity Removal

```java
// Old
entity.removed = true;

// New
entity.remove(Entity.RemovalReason.DISCARDED);
```

## Testing Tips

1. Pay special attention to breeding logic which often involves position and dimension calls
2. Check that entity riding works correctly (dolphins that can be ridden)
3. Test all entity interactions (feeding, taming, etc.)
4. Verify inventory interactions if any aquatic has storage
5. Test aquatic movement patterns and animations 