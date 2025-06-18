# Hunter Entities Update Progress (1.16.5 to 1.20.1)

## Completed Updates
- [x] MoCEntityFox.java
- [x] MoCEntityRaccoon.java  
- [x] MoCEntityLion.java
- [x] MoCEntityKomodo.java
- [x] MoCEntitySnake.java
- [x] MoCEntityCrocodile.java
- [x] MoCEntityBear.java
- [x] MoCEntityBigCat.java
- [x] MoCEntityPetScorpion.java
- [x] MoCEntityManticorePet.java
- [x] MoCEntityTiger.java
- [x] MoCEntityPolarBear.java
- [x] MoCEntityPanther.java
- [x] MoCEntityLeopard.java
- [x] MoCEntityGrizzlyBear.java
- [x] MoCEntityBlackBear.java
- [x] MoCEntityPanthard.java
- [x] MoCEntityPanthger.java
- [x] MoCEntityLither.java
- [x] MoCEntityLiger.java
- [x] MoCEntityLiard.java
- [x] MoCEntityLeoger.java

## Common Changes Applied
1. Package path updates (e.g., net.minecraft.entity → net.minecraft.world.entity)
2. Class name changes (e.g., World → Level, PlayerEntity → Player)
3. Method name updates (e.g., attackEntityFrom → hurt, world.isRemote → level().isClientSide())
4. Entity data system updates (EntityDataManager → SynchedEntityData)
5. NBT handling updates (CompoundNBT → CompoundTag)
6. Attribute system changes (registerAttributes → createAttributes)
7. Entity movement and interaction method renames
8. BiomeDictionary replacement with direct biome path checks
9. LootTable references updated
10. Entity passenger/vehicle methods updated (isBeingRidden → isVehicle, etc.)
11. Navigation methods updated (setPathToEntity → getNavigation().moveTo)
12. Effect updates (addPotionEffect → addEffect, Effects.SLOWNESS → MobEffects.MOVEMENT_SLOWDOWN)
13. Item handling method updates (getItem() == Item → is(Item))
14. Method overrides now using standard names (getLootTable → getDefaultLootTable)
15. Proper handling of entity rider positioning 
16. Player capabilities access using new methods (abilities.isCreativeMode → getAbilities().instabuild)

## Completed Issues
1. Fixed MoCEntityPetScorpion.java's method overrides and field references
2. Updated all hunter entities to handle entity riding correctly
3. Fixed item reference field naming to match 1.20.1 standards (e.g., MoCItems.essencelight → MoCItems.ESSENCE_LIGHT.get())
4. Updated dimension handling for network packets
5. Fixed method references in bear entities (isItemEdibleforCarnivores → isItemEdibleForCarnivores)
6. Updated biome detection logic to use biome path instead of BiomeDictionary 