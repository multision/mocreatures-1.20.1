# Aquatic Entities Update Summary

This document summarizes the progress made in updating the Mo' Creatures aquatic entities from 1.16.5 to 1.20.1, highlights the common patterns in the changes, and notes the remaining issues that need to be addressed.

## Key Updates Made

1. **Package and Import Updates**
   - Changed `net.minecraft.entity` → `net.minecraft.world.entity`
   - Changed `net.minecraft.entity.player.PlayerEntity` → `net.minecraft.world.entity.player.Player`
   - Changed `net.minecraft.item` → `net.minecraft.world.item`
   - Changed `net.minecraft.util.DamageSource` → `net.minecraft.world.damagesource.DamageSource`
   - Changed `net.minecraft.util.ResourceLocation` → `net.minecraft.resources.ResourceLocation`
   - Changed `net.minecraft.util.SoundEvent` → `net.minecraft.sounds.SoundEvent`
   - Changed `net.minecraft.world.World` → `net.minecraft.world.level.Level`
   - Changed `net.minecraft.network.datasync` classes → `net.minecraft.network.syncher`

2. **Entity Data Field Changes**
   - Changed `DataParameter` → `EntityDataAccessor`
   - Changed `DataSerializers` → `EntityDataSerializers`
   - Changed `EntityDataManager` → `SynchedEntityData`
   - Updated all methods: `register()` → `define()`, `get()` and `set()` remain the same but on `entityData` instead of `dataManager`

3. **Method and Field Name Changes**
   - Changed `registerData()` → `defineSynchedData()`
   - Changed `livingTick()` → `tick()`
   - Changed `world` → `level()`
   - Changed `world.isRemote` → `level().isClientSide()`
   - Changed `attackEntityFrom()` → `hurt()`
   - Changed `isBeingRidden()` → `isVehicle()`
   - Changed `getRidingEntity()` → `getVehicle()`
   - Changed `entityDropItem()` → `spawnAtLocation()`
   - Changed `areEyesInFluid()` → `isEyeInFluid()`
   - Changed `getHeight()` → `getBbHeight()`
   - Changed `getWidth()` → `getBbWidth()`
   - Changed `rand` → `random`
   - Changed `getLootTable()` → `getDefaultLootTable()`
   - Changed `getEntityInteractionResult()` → `mobInteract()`
   - Changed `getPosX()`, `getPosY()`, `getPosZ()` → `getX()`, `getY()`, `getZ()`
   - Changed `getEntityId()` → `getId()`
   - Changed `addEntity()` → `addFreshEntity()`
   - Changed `setPosition()` → `moveTo()`
   - Changed `rotationYaw`, `rotationPitch` → `getYRot()`, `getXRot()`
   - Changed `renderYawOffset` → `yBodyRot`
   - Changed `getClosestPlayer()` → `getNearestPlayer()`
   - Changed `ActionResultType` → `InteractionResult`
   - Changed `Hand` → `InteractionHand`
   - Changed `player.abilities.isCreativeMode` → `player.isCreative()`
   - Changed `remove(boolean)` → `remove(Entity.RemovalReason)`
   - Changed `updatePassenger()` → `positionRider()` (but note that you may need to use a different method name due to final method issues)

4. **Entity Attribute System**
   - Changed `AttributeModifierMap.MutableAttribute` → `AttributeSupplier.Builder`
   - Changed `registerAttributes()` → `createAttributes()`
   - Changed `.createMutableAttribute(...)` → `.add(...)`
   - Removed `getAIMoveSpeed()` in favor of using the attribute system

5. **Packet and Network Handling**
   - Updated `PacketDistributor.TargetPoint` to use the new parameters
   - Changed `world.getDimensionKey()` → `level().dimension()`

6. **Entity Creation and Spawning**
   - Updated entity creation logic to be more defensive, checking for null values
   - Used `EntityType.create(level())` with proper null checks
   - Used proper type casting

## Remaining Issues to Resolve

1. **EntityType Resolution**
   - The compiler is having trouble resolving `EntityType<MoCEntityFishy>` and similar types
   - This might require updating how entity types are registered in the mod

2. **MoCItems Access**
   - The `MOCEGG` field in `MoCItems` is not being properly resolved
   - This suggests the items registration system may need updating

3. **Method Override Conflicts**
   - Some methods like `positionRider()` are now final in the parent class
   - Alternative methods or approaches need to be found

4. **Animation System Updates**
   - The animation system changed significantly in 1.20.1
   - Need to update any code using `limbSwingAmount` to use `walkAnimation.setSpeed()`

5. **DamageSource Changes**
   - The DamageSource system has been completely overhauled
   - Need to use `this.damageSources().generic()` instead of static constants

## Next Steps

1. **Update MoCEntity Registration System**
   - Check how entities are registered in the mod and ensure it's compatible with 1.20.1

2. **Update MoCItems Registration System**
   - Verify that items are registered correctly and accessible

3. **Complete Updates for All Aquatic Entities**
   - Apply the same update patterns to all remaining aquatic entity classes
   - Special attention to entities with unique behaviors (like dolphins)

4. **Testing**
   - Test spawning, movement, and interactions for all aquatic entities
   - Verify breeding mechanics work correctly
   - Test riding mechanics for rideable aquatic mobs
   - Check that all animations display correctly

## File Update Status

- ✅ **MoCEntityTameableAquatic.java** - Updated
- ✅ **MoCEntityDolphin.java** - Updated with a few remaining issues
- ✅ **MoCEntityFishy.java** - Updated with a few remaining issues
- ⬜ MoCEntityShark.java - Not yet updated
- ⬜ MoCEntityPiranha.java - Not yet updated
- ⬜ MoCEntitySmallFish.java - Not yet updated
- ⬜ MoCEntityStingRay.java - Not yet updated
- ⬜ MoCEntityRay.java - Not yet updated
- ⬜ MoCEntitySalmon.java - Not yet updated
- ⬜ MoCEntityMediumFish.java - Not yet updated
- ⬜ MoCEntityManderin.java - Not yet updated
- ⬜ MoCEntityMantaRay.java - Not yet updated
- ⬜ MoCEntityGoldFish.java - Not yet updated
- ⬜ MoCEntityHippoTang.java - Not yet updated
- ⬜ MoCEntityJellyFish.java - Not yet updated
- ⬜ MoCEntityCod.java - Not yet updated
- ⬜ MoCEntityBass.java - Not yet updated
- ⬜ MoCEntityClownFish.java - Not yet updated
- ⬜ MoCEntityAnchovy.java - Not yet updated
- ⬜ MoCEntityAngelFish.java - Not yet updated
- ⬜ MoCEntityAngler.java - Not yet updated 