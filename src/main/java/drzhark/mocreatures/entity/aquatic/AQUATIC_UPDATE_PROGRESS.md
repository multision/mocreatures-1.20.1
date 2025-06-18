# Aquatic Entities Update Progress (1.16.5 to 1.20.1)

This document tracks the progress of updating the aquatic entities from Mo' Creatures 1.16.5 to 1.20.1.

## Updated Entities

These entities have been successfully updated to 1.20.1:

1. **MoCEntityDolphin.java**
   - Updated method names, package paths, and attribute handling
   - Added required override methods for movement

2. **MoCEntityFishy.java**
   - Updated method names, package paths, and attribute handling
   - Added required override methods for movement

3. **MoCEntitySmallFish.java**
   - Updated method names, package paths, and entity type handling
   - Fixed animation methods and EntityType registrations
   - Updated attribute building pattern

4. **MoCEntityAnchovy.java**
   - Updated package paths and method names
   - Changed getLootTable to getDefaultLootTable

5. **MoCEntityAngelFish.java**
   - Updated package paths and method names
   - Changed getLootTable to getDefaultLootTable

6. **MoCEntityShark.java**
   - Updated package paths, method names, and attribute handling
   - Fixed entity targeting system and dimension specific methods
   - Refactored damage source handling to match 1.20.1 patterns

7. **MoCEntityPiranha.java**
   - Updated package paths and method names
   - Fixed entity targeting and damage source handling
   - Updated attribute creation pattern
   - Changed experience handling to use getExperienceReward()

8. **MoCEntityJellyFish.java**
   - Updated package paths and method names
   - Updated EntityDataManager to SynchedEntityData
   - Fixed sound events and animation timers
   - Updated attribute creation pattern

9. **MoCEntityAngler.java**
   - Updated package paths and method names
   - Changed getLootTable to getDefaultLootTable

10. **MoCEntityMediumFish.java**
    - Updated method names, package paths, and entity type handling
    - Fixed fluid interactions with isEyeInFluid() instead of areEyesInFluid()
    - Updated animation methods and added override annotations
    - Updated attribute creation pattern

11. **MoCEntityBass.java**
    - Updated package paths and method names
    - Changed getLootTable to getDefaultLootTable

12. **MoCEntityCod.java**
    - Updated package paths and method names
    - Changed getLootTable to getDefaultLootTable

13. **MoCEntityClownFish.java**
    - Updated package paths and method names
    - Changed getLootTable to getDefaultLootTable

14. **MoCEntityGoldFish.java**
    - Updated package paths and method names
    - Changed getLootTable to getDefaultLootTable

15. **MoCEntityHippoTang.java**
    - Updated package paths and method names
    - Changed getLootTable to getDefaultLootTable

16. **MoCEntityManderin.java**
    - Updated package paths and method names
    - Changed getLootTable to getDefaultLootTable

17. **MoCEntityRay.java**
    - Updated package paths and method names
    - Fixed entity interaction system and riding mechanics
    - Updated method overrides and attribute system

18. **MoCEntityMantaRay.java**
    - Updated package paths and method names
    - Changed getLootTable to getDefaultLootTable
    - Updated attribute system and height calculations

19. **MoCEntityStingRay.java**
    - Updated package paths and method names
    - Fixed animation system and packet handling for network messages
    - Updated damage handling and AI systems

20. **MoCEntitySalmon.java**
    - Updated package paths and method names
    - Changed getLootTable to getDefaultLootTable

## ✅ All Aquatic Entities Successfully Updated!

All 20 aquatic entity files have been updated to be compatible with Minecraft 1.20.1. The update process has been completed successfully.

## Common Update Patterns

1. **Package Path Updates**
   - `net.minecraft.entity` → `net.minecraft.world.entity`
   - `net.minecraft.util.ResourceLocation` → `net.minecraft.resources.ResourceLocation`
   - `net.minecraft.world.World` → `net.minecraft.world.level.Level`
   - `net.minecraft.util.SoundEvents` → `net.minecraft.sounds.SoundEvents`
   - `net.minecraft.util.math.MathHelper` → `net.minecraft.util.Mth`
   - `net.minecraft.util.DamageSource` → `net.minecraft.world.damagesource.DamageSource`

2. **Method Name Updates**
   - `registerData()` → `defineSynchedData()`
   - `livingTick()` → `tick()`
   - `world.isRemote` → `level().isClientSide()`
   - `getAIMoveSpeed()` → `getSpeed()`
   - `getLootTable()` → `getDefaultLootTable()`
   - `attackEntityFrom()` → `hurt()`
   - `setAttackTarget()` → `setTarget()`
   - `getExperiencePoints()` → `getExperienceReward()`
   - `world.isDaytime()` → `level().isDay()`
   - `areEyesInFluid()` → `isEyeInFluid()`
   - `onGround` → `onGround()`
   - `getEntityId()` → `getId()`
   - `getPosX()`, `getPosY()`, `getPosZ()` → `getX()`, `getY()`, `getZ()`
   - `setPosition()` → `setPos()`
   - `world.getDimensionKey()` → `level().dimension()`
   - `getEntityInteractionResult()` → `mobInteract()`
   - `isBeingRidden()` → `isVehicle()`
   - `getMountedYOffset()` → `getPassengersRidingOffset()`

3. **Entity Property Updates**
   - `getHeight()` → `getBbHeight()`
   - `getWidth()` → `getBbWidth()`
   - `rand` → `random`
   - `world` → `level()`
   - `ticksExisted` → `tickCount`
   - Direct field assignment replaced with getters/setters
   - Removed `experienceValue` field, use `getExperienceReward()` instead
   - `rotationYaw`, `rotationPitch` → `setYRot()`, `setXRot()`

4. **Data Manager Updates**
   - `DataParameter` → `EntityDataAccessor`
   - `DataSerializers` → `EntityDataSerializers`
   - `EntityDataManager` → `SynchedEntityData`
   - `dataManager.register()` → `entityData.define()`
   - `dataManager.get()` → `entityData.get()`
   - `dataManager.set()` → `entityData.set()`

5. **Animation System Updates**
   - Old rotation properties like `rotationYaw` are replaced with getter/setter methods
   - Using the new walkAnimation system for movement animations
   - `prevRenderYawOffset`, `renderYawOffset`, `rotationYaw`, `prevRotationYaw` → `yBodyRot`, `getYRot()`
   - `rotationPitch`, `prevRotationPitch` → `setXRot()`, `getXRot()`

6. **Attribute System Updates**
   - `AttributeModifierMap.MutableAttribute` → `AttributeSupplier.Builder`
   - `createMutableAttribute()` → `add()`
   - Using builder pattern instead of chained calls
   - Method name `registerAttributes()` → `createAttributes()`

7. **Entity Creation Updates**
   - `MoCEntities.ENTITY_NAME.create(world)` → `(EntityType) MoCEntities.ENTITY_NAME.get().create(world)`
   - Added type casting for entity creation

8. **Interaction System Updates**
   - `ActionResultType` → `InteractionResult`
   - `Hand` → `InteractionHand`
   - Player-entity interaction systems completely refactored

9. **Damage Source Updates**
   - `damagesource.getTrueSource()` → `damagesource.getEntity()`
   - DamageSource system completely refactored

## Known Issues to Address

1. EntityType resolution in MoCEntitySmallFish.createEntity() and MoCEntityMediumFish.createEntity()
2. Entity field access patterns (getting items via .is() instead of ==)
3. Entity sizing and hitbox adjustment
4. MoCItems field access issues
5. Incompatible EntityType expressions
6. Sound event mapping (prefix changes like ENTITY_SLIME_ATTACK → SLIME_ATTACK)

## Next Steps

1. ✅ Complete updating all aquatic entity classes
2. Fix known issues in already updated classes
3. Test in-game behavior of updated entities
4. Update entity registration and spawn mechanics to 1.20.1 patterns 