# Hostile Mobs Update Progress (1.16.5 to 1.20.1)

This document tracks the progress of updating the hostile mobs in the Mo' Creatures mod from 1.16.5 to 1.20.1.

## Updated Classes

| Class | Status | Notes |
|-------|--------|-------|
| MoCEntityRat.java | ✅ Updated | Uses BiomeDictionary temporarily replaced with biome path check |
| MoCEntityWraith.java | ✅ Updated | Removed invalid checkFallDamage override |
| MoCEntityFlameWraith.java | ✅ Updated | Changed IMob to Enemy interface |
| MoCEntityScorpion.java | ✅ Updated | Fixed rider positioning with new positionRider method |
| MoCEntityFireScorpion.java | ✅ Updated | Updated to use fireImmune and new attribute system |
| MoCEntityCaveScorpion.java | ✅ Updated | Updated with modern entity and spawn methods |
| MoCEntityDirtScorpion.java | ✅ Updated | Updated poison effect to use modern names |
| MoCEntityFrostScorpion.java | ✅ Updated | Updated effect system from 1.16.5 to 1.20.1 |
| MoCEntityUndeadScorpion.java | ✅ Updated | Updated effect and general entity methods |
| MoCEntityOgre.java | ✅ Updated | Updated base ogre class with modern methods |
| MoCEntityFireOgre.java | ✅ Updated | Updated to use fireImmune and isInWaterRainOrBubble |
| MoCEntityCaveOgre.java | ✅ Updated | Updated with isDaylightSensitive and ServerLevel |
| MoCEntityGreenOgre.java | ✅ Updated | Updated attributes and loot table methods |
| MoCEntityManticore.java | ✅ Updated | Updated with proper rider positioning and tick methods |
| MoCEntityDarkManticore.java | ✅ Updated | Updated potion effects with MobEffects and new attribute system |
| MoCEntityFireManticore.java | ✅ Updated | Updated with fireImmune and setSecondsOnFire methods |
| MoCEntityFrostManticore.java | ✅ Updated | Updated with modern potion effects (MOVEMENT_SLOWDOWN) |
| MoCEntityPlainManticore.java | ✅ Updated | Updated with modern effect system |
| MoCEntityToxicManticore.java | ✅ Updated | Updated effect system and pattern matching for instanceof |
| MoCEntityHorseMob.java | ✅ Updated | Updated BlockState.liquid() and rider positioning |
| MoCEntityWerewolf.java | ✅ Updated | Updated transform method and entity dimensions handling |
| MoCEntityWWolf.java | ✅ Updated | Replaced BiomeDictionary with biome path check |
| MoCEntityGolem.java | ✅ Updated | Updated with 1.20.1 methods and fixed getEntities handling |
| MoCEntityThrowableRock.java | ✅ Updated | Updated item entity to implement IEntityAdditionalSpawnData |
| MoCEntityMiniGolem.java | ✅ Updated | Updated with modern AI goal classes and attribute system |
| MoCEntitySilverSkeleton.java | ✅ Updated | Updated animations, network packets and mob attributes |
| MoCEntityHellRat.java | ✅ Updated | Updated to use fireImmune and particle rendering |

## Common Update Patterns

1. **Package Changes**
   - `net.minecraft.entity` → `net.minecraft.world.entity`
   - `net.minecraft.entity.ai.goal` → `net.minecraft.world.entity.ai.goal`
   - `net.minecraft.entity.ai.goal.HurtByTargetGoal` → `net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal`
   - `net.minecraft.util.DamageSource` → `net.minecraft.world.damagesource.DamageSource`

2. **Class Name Changes**
   - `EntityDataManager` → `SynchedEntityData`
   - `DataParameter` → `EntityDataAccessor`
   - `PlayerEntity` → `Player`
   - `IronGolemEntity` → `IronGolem`
   - `World` → `Level`
   - `StringTextComponent` → `Component.literal()`
   - `EntitySize` → `EntityDimensions`
   - `CreatureAttribute` → `MobType`
   - `IMob` → `Enemy`
   - `SwimGoal` → `FloatGoal`
   - `LookAtGoal` → `LookAtPlayerGoal`
   - `LookRandomlyGoal` → `RandomLookAroundGoal`
   - `CompoundNBT` → `CompoundTag`
   - `ToolItem` → `TieredItem`
   - `MonsterEntity` → `Monster`
   - `ZombieEntity` → `Zombie`
   - `SkeletonEntity` → `Skeleton`
   - `CowEntity` → `Cow`
   - `WolfEntity` → `Wolf`
   - `IServerWorld` → `ServerLevelAccessor`
   - `PacketBuffer` → `FriendlyByteBuf`

3. **Method Name Changes**
   - `registerData()` → `defineSynchedData()`
   - `world.isRemote` → `level().isClientSide()`
   - `world` → `level()`
   - `dataManager` → `entityData`
   - `attackEntityAsMob()` → `doHurtTarget()`
   - `attackEntityFrom()` → `hurt()`
   - `getLootTable()` → `getDefaultLootTable()`
   - `getCreatureAttribute()` → `getMobType()`
   - `canBePushed()` → `isPushable()`
   - `collideWithEntity()` → `doPush()`
   - `livingTick()` → `tick()` or `aiStep()`
   - `getEntityId()` → `getId()`
   - `getHeight()` → `getBbHeight()`
   - `getWidth()` → `getBbWidth()`
   - `getPosX()`, `getPosY()`, `getPosZ()` → `getX()`, `getY()`, `getZ()`
   - `getRand()` → `getRandom()`
   - `rand` → `random`
   - `experienceValue` → `xpReward`
   - `setPosition()` → `setPos()` or `moveTo()`
   - `this.rotationYaw` → `this.getYRot()`
   - `world.isDaytime()` → `level().isDay()`
   - `isImmuneToFire()` → `fireImmune()`
   - `applyEnchantments()` → `doEnchantDamageEffects()`
   - `world.getDimensionType().isUltrawarm()` → `level().dimensionType().ultraWarm()`
   - `setFire()` → `setSecondsOnFire()`
   - `isHarmedByDaylight()` → `isDaylightSensitive()`
   - `getBrightness()` → `getLightLevelDependentMagicValue()`
   - `shouldExecute()` → `canUse()`
   - `shouldContinueExecuting()` → `canContinueToUse()`
   - `updatePassenger()` → `positionRider()`
   - `setAttackTarget()` → `setTarget()`
   - `getAttackTarget()` → `getTarget()`
   - `isWet()` → `isInWaterRainOrBubble()`
   - `isBeingRidden()` → `isVehicle()`
   - `isAirBlock()` → `isEmptyBlock()`
   - `getMountedYOffset()` → `getPassengersRidingOffset()`
   - `onLivingFall()` → `causeFallDamage()`
   - `getAIMoveSpeed()` → `getSpeed()`
   - `getMaterial().isLiquid()` → `liquid()`
   - `getHeldItemMainhand()` → `getMainHandItem()`
   - `getTranslationKey()` → `getDescriptionId()`
   - `readAdditional()` → `readAdditionalSaveData()`
   - `writeAdditional()` → `addAdditionalSaveData()`
   - `recalculateSize()` → `refreshDimensions()`
   - `stepHeight` → `maxUpStep`
   - `getSize()` → `getDimensions()`
   - `getDistanceSq()` → `distanceToSqr()`
   - `idleTime` → `noActionTime`
   - `canEntityBeSeen()` → `hasLineOfSight()`
   - `getRidingEntity()` → `getVehicle()`
   - `MathHelper` → `Mth`
   - `getDefaultState()` → `defaultBlockState()`
   - `getBlockHardness()` → `getDestroySpeed()`
   - `addEntity()` → `addFreshEntity()`
   - `getNavigator()` → `getNavigation()`
   - `clearPath()` → `stop()`
   - `remove()` → `remove(RemovalReason)`
   - `getTrueSource()` → `getEntity()`
   - `getBoundingBox().grow()` → `getBoundingBox().inflate()`
   - `canEntityBeSeen()` → `hasLineOfSight()`
   - `setDefaultPickupDelay()` → `setDefaultPickUpDelay()`
   - `getEntitiesWithinAABBExcludingEntity()` → `getEntitiesOfClass()`
   - `getDimensionKey()` → `dimension()`
   - `onDeath()` → `die()`

4. **AI Changes**
   - `registerAttributes()` → `createAttributes()`
   - `createMutableAttribute()` → `add()`
   - `WaterAvoidingRandomWalkingGoal` → `RandomStrollGoal`

5. **Entity Data Changes**
   - `EntityDataManager.createKey()` → `SynchedEntityData.defineId()`
   - `this.dataManager.register()` → `this.entityData.define()`
   - `this.dataManager.get()` → `this.entityData.get()`
   - `this.dataManager.set()` → `this.entityData.set()`

6. **Effect System Changes**
   - `EffectInstance` → `MobEffectInstance`
   - `Effects` → `MobEffects`
   - `addPotionEffect()` → `addEffect()`
   - `Effects.SLOWNESS` → `MobEffects.MOVEMENT_SLOWDOWN`
   - `Effects.NAUSEA` → `MobEffects.CONFUSION`

7. **Damage Sources**
   - Direct references to `DamageSource.X` → use `this.damageSources().x()`
   - `damagesource.getTrueSource()` → `damagesource.getEntity()`
   - `DamageSource.causeMobDamage()` → `this.damageSources().mobAttack()`

8. **Entity Queries**
   - `world.getEntitiesWithinAABBExcludingEntity()` → `level().getEntitiesOfClass()`
   - `getBoundingBox().grow()` → `getBoundingBox().inflate()`

9. **BiomeDictionary Replacement**
   - Replace `BiomeDictionary.hasType(biome, Type.X)` with biome path checks like:
     ```java
     String biomePath = biome.location().getPath();
     if (biomePath.contains("snow") || biomePath.contains("frozen")) {
         // Snow biome handling
     }
     ```

10. **Block State Changes**
    - `Block.getStateById()` → `Block.stateById()`
    - `Block.getStateId()` → unchanged, but context might require changes

11. **Entity Spawn Data Handling**
    - Implement `IEntityAdditionalSpawnData` interface 
    - Use `getAddEntityPacket()` to return `NetworkHooks.getEntitySpawningPacket(this)`
    - Implement `writeSpawnData()` and `readSpawnData()` methods which will be called by NetworkHooks
    - Import correct packet classes:
      ```java
      import net.minecraft.network.protocol.Packet;
      import net.minecraft.network.protocol.game.ClientGamePacketListener;
      import net.minecraftforge.network.NetworkHooks;
      import net.minecraft.network.FriendlyByteBuf;
      import net.minecraftforge.entity.IEntityAdditionalSpawnData;
      ```

## Known Issues to Resolve

1. **Entity Types**:
   - Update entity registration system and fix entity type reference issues

2. **MoCEntityPetScorpion Entity Type**:
   - Fix the entity type reference in MoCEntityScorpion.java
   
3. **BiomeDictionary Replacement**:
   - Implement a standardized replacement for BiomeDictionary across all entities