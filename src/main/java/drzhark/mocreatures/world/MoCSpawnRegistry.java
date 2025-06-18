package drzhark.mocreatures.world;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.tags.FluidTags;
import drzhark.mocreatures.MoCTools;
import net.minecraft.util.RandomSource;

/**
 * Registry for Mo' Creatures spawns
 */
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
public class MoCSpawnRegistry {

    /**
     * Register spawn placements for entities
     */
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        // Make sure config values are loaded
        MoCreatures.proxy.readMocConfigValues();
        
        // Register spawn placements for land animals
        registerLandAnimalPlacements(event);
        
        // Register spawn placements for aquatic creatures
        registerAquaticPlacements(event);
        
        // Register spawn placements for monster mobs
        registerMonsterPlacements(event);
        
        // Register spawn placements for ambient creatures
        registerAmbientPlacements(event);
    }
    
    /**
     * Register land animal spawn placements
     */
    private static void registerLandAnimalPlacements(SpawnPlacementRegisterEvent event) {
        // Bears
        event.register(
            MoCEntities.BLACK_BEAR.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_CONIFEROUS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Kitties - spawn in villages
        event.register(
            MoCEntities.KITTY.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DESERT) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SAVANNA) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_TAIGA) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SNOWY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.GRIZZLY_BEAR.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.POLAR_BEAR.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SNOWY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.PANDA_BEAR.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Birds
        event.register(
            MoCEntities.BIRD.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Other land animals
        event.register(
            MoCEntities.BOAR.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.BUNNY.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> {
                // Always allow spawn eggs
                if (spawnType == MobSpawnType.SPAWN_EGG) return true;
                
                // In Wyvern dimension, allow spawning regardless of light level
                if (world.getBiome(pos).is(MoCBiomeTags.IS_WYVERN_LAIR)) {
                    return true;
                }
                
                // Normal biome checks for other dimensions
                return world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_LUSH);
            },
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.DEER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.DUCK.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_RIVER) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_LUSH),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Elephants and variants
        event.register(
            MoCEntities.ELEPHANT.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SAVANNA) || 
                (world.getBiome(pos).is(MoCBiomeTags.IS_SANDY) && !world.getBiome(pos).is(MoCBiomeTags.IS_BADLANDS)),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Exotic creatures
        event.register(
            MoCEntities.SNAKE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> {
                // Always allow spawn eggs
                if (spawnType == MobSpawnType.SPAWN_EGG) return true;
                
                // In Wyvern dimension, allow spawning regardless of light level
                if (world.getBiome(pos).is(MoCBiomeTags.IS_WYVERN_LAIR)) {
                    return true;
                }
                
                // Normal biome checks for other dimensions
                return world.getBiome(pos).is(MoCBiomeTags.IS_SANDY) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_BADLANDS) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_LUSH) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_STEEP);
            },
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Big cats
        event.register(
            MoCEntities.LION.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SAVANNA) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SANDY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_BADLANDS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.TIGER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Farm animals
        event.register(
            MoCEntities.TURKEY.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Horses
        event.register(
            MoCEntities.WILDHORSE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SAVANNA),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Big cat hybrids
        event.register(
            MoCEntities.LIGER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SAVANNA),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.LITHER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.PANTHGER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.PANTHARD.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.LEOGER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SAVANNA),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Leopard
        event.register(
            MoCEntities.LEOPARD.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SNOWY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SAVANNA),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Panther
        event.register(
            MoCEntities.PANTHER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Komodo Dragon
        event.register(
            MoCEntities.KOMODO_DRAGON.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SAVANNA),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Filch Lizard
        event.register(
            MoCEntities.FILCH_LIZARD.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> {
                // Always allow spawn eggs
                if (spawnType == MobSpawnType.SPAWN_EGG) return true;
                
                // In Wyvern dimension, allow spawning regardless of light level
                if (world.getBiome(pos).is(MoCBiomeTags.IS_WYVERN_LAIR)) {
                    return true;
                }
                
                // Normal biome checks for other dimensions
                return world.getBiome(pos).is(MoCBiomeTags.IS_SAVANNA) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_SANDY) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_BADLANDS);
            },
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Mole
        event.register(
            MoCEntities.MOLE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_LUSH) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Ent
        event.register(
            MoCEntities.ENT.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> 
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Wyvern (in Wyvern dimension)
        event.register(
            MoCEntities.WYVERN.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> {
                // Always allow spawn eggs
                if (spawnType == MobSpawnType.SPAWN_EGG) return true;
                
                // In Wyvern dimension, allow spawning regardless of light level
                if (world.getBiome(pos).is(MoCBiomeTags.IS_WYVERN_LAIR)) {
                    return true;
                }
                return false;
            },
            SpawnPlacementRegisterEvent.Operation.AND
        );
    }
    
    /**
     * Register aquatic creature spawn placements
     */
    private static void registerAquaticPlacements(SpawnPlacementRegisterEvent event) {
        // Ocean creatures
        event.register(
            MoCEntities.DOLPHIN.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.SHARK.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Rays
        event.register(
            MoCEntities.MANTA_RAY.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.STING_RAY.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_RIVER)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Fish
        event.register(
            MoCEntities.PIRANHA.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.BASS.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_RIVER) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Small colorful fish
        event.register(
            MoCEntities.FISHY.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_RIVER)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Jellyfish
        event.register(
            MoCEntities.JELLYFISH.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Additional fish species
        event.register(
            MoCEntities.ANCHOVY.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_BEACH) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_RIVER)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.ANGELFISH.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_RIVER) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.ANGLER.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_BEACH) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.CLOWNFISH.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_BEACH) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.COD.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_BEACH) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.GOLDFISH.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  world.getBiome(pos).is(MoCBiomeTags.IS_RIVER))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.HIPPOTANG.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_BEACH) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.MANDERIN.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_BEACH) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.SALMON.get(),
            SpawnPlacements.Type.IN_WATER,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                (spawnType == MobSpawnType.SPAWN_EGG || 
                 (world.getFluidState(pos).is(FluidTags.WATER) && 
                  (world.getBiome(pos).is(MoCBiomeTags.IS_BEACH) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_WATER) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_OCEAN) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_RIVER) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                   world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS)))),
            SpawnPlacementRegisterEvent.Operation.AND
        );
    }
    
    /**
     * Register monster spawn placements
     */
    private static void registerMonsterPlacements(SpawnPlacementRegisterEvent event) {
        // Ogres
        event.register(
            MoCEntities.GREEN_OGRE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.FIRE_OGRE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_NETHER),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.CAVE_OGRE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_MOUNTAIN),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Golems
        event.register(
            MoCEntities.BIG_GOLEM.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SANDY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_HILL) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_BADLANDS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_MOUNTAIN) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DEAD),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.MINI_GOLEM.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SANDY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_BADLANDS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_MOUNTAIN) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DEAD),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Horse Mob
        event.register(
            MoCEntities.HORSE_MOB.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_NETHER) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SAVANNA) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DEAD) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SPOOKY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Hell Rat
        event.register(
            MoCEntities.HELL_RAT.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_NETHER),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Scorpions
        event.register(
            MoCEntities.CAVE_SCORPION.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_MOUNTAIN),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.DIRT_SCORPION.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SANDY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DRY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.FROST_SCORPION.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SNOWY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.FIRE_SCORPION.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_NETHER),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.UNDEAD_SCORPION.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DEAD) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SPOOKY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Manticores
        event.register(
            MoCEntities.DARK_MANTICORE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SANDY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_MOUNTAIN) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SNOWY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DEAD) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SPOOKY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.FIRE_MANTICORE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_NETHER),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.FROST_MANTICORE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SNOWY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.PLAIN_MANTICORE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SANDY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_MOUNTAIN) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.TOXIC_MANTICORE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DEAD) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SPOOKY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Silver Skeleton
        event.register(
            MoCEntities.SILVER_SKELETON.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SANDY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SNOWY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_BADLANDS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DEAD) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SPOOKY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Werewolves
        event.register(
            MoCEntities.WEREWOLF.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_CONIFEROUS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Wraiths
        event.register(
            MoCEntities.WRAITH.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SPOOKY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.FLAME_WRAITH.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_NETHER),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Rats
        event.register(
            MoCEntities.RAT.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_STEEP),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.HELL_RAT.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_NETHER),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Wild Wolves
        event.register(
            MoCEntities.WWOLF.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SNOWY) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DEAD),
            SpawnPlacementRegisterEvent.Operation.AND
        );
    }
    
    /**
     * Register ambient creature spawn placements
     */
    private static void registerAmbientPlacements(SpawnPlacementRegisterEvent event) {
        // Insects
        event.register(
            MoCEntities.ANT.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.BEE.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.BUTTERFLY.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_LUSH),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.DRAGONFLY.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> {
                // Always allow spawn eggs
                if (spawnType == MobSpawnType.SPAWN_EGG) return true;
                
                // In Wyvern dimension, allow spawning regardless of light level
                if (world.getBiome(pos).is(MoCBiomeTags.IS_WYVERN_LAIR)) {
                    return true;
                }
                
                // Normal biome checks for other dimensions
                return world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP) ||
                       world.getBiome(pos).is(MoCBiomeTags.IS_LUSH) ||
                       world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE);
            },
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.FIREFLY.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> {
                // Always allow spawn eggs
                if (spawnType == MobSpawnType.SPAWN_EGG) return true;
                
                // In Wyvern dimension, allow spawning regardless of light level
                if (world.getBiome(pos).is(MoCBiomeTags.IS_WYVERN_LAIR)) {
                    return true;
                }
                
                // Normal biome checks for other dimensions
                return world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_CONIFEROUS) ||
                       world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS);
            },
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.FLY.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Beach creatures
        event.register(
            MoCEntities.CRAB.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_BEACH),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Snails
        event.register(
            MoCEntities.SNAIL.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_LUSH),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Maggots and Roaches
        event.register(
            MoCEntities.MAGGOT.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DEAD) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SPOOKY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        event.register(
            MoCEntities.ROACH.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_DEAD) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SPOOKY),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Cricket
        event.register(
            MoCEntities.CRICKET.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) ->
                spawnType == MobSpawnType.SPAWN_EGG || 
                world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_JUNGLE) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS) || 
                world.getBiome(pos).is(MoCBiomeTags.IS_SWAMP),
            SpawnPlacementRegisterEvent.Operation.AND
        );
        
        // Grasshopper
        event.register(
            MoCEntities.GRASSHOPPER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            (entityType, world, spawnType, pos, random) -> {
                // Always allow spawn eggs
                if (spawnType == MobSpawnType.SPAWN_EGG) return true;
                
                // In Wyvern dimension, allow spawning regardless of light level
                if (world.getBiome(pos).is(MoCBiomeTags.IS_WYVERN_LAIR)) {
                    return true;
                }
                
                // Normal biome checks for other dimensions
                return world.getBiome(pos).is(MoCBiomeTags.IS_FOREST) || 
                       world.getBiome(pos).is(MoCBiomeTags.IS_PLAINS);
            },
            SpawnPlacementRegisterEvent.Operation.AND
        );
    }
    
    /**
     * Register all spawn configurations
     */
    public static void register() {
        // Read the config values first
        MoCreatures.proxy.readMocConfigValues();
        
        // In 1.20+, actual spawn registration is handled by Forge through JSON biome modifiers
        // rather than through code. The spawn placements are still handled through the event
        // handler in this class.
        MoCreatures.LOGGER.info("Registering Mo' Creatures spawn system");
    }
    
    /**
     * Initialize spawn registry when the server is about to start
     */
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        init();
    }
    
    /**
     * Initialize spawn registry during common setup
     */
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(MoCSpawnRegistry::init);
    }

    /**
     * Debug method to spawn a specific entity
     */
    public static boolean debugSpawn(EntityType<? extends Mob> entityType, ServerLevelAccessor level, 
            BlockPos pos, Random random) {
        try {
            Mob entity = entityType.create(level.getLevel());
            if (entity == null) {
                return false;
            }
            
            entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            return level.addFreshEntity(entity);
        } catch (Exception e) {
            MoCreatures.LOGGER.error("Failed to debug spawn entity", e);
            return false;
        }
    }

    /**
     * Initialize entity attributes
     */
    public static void init() {
        // Will be filled with attribute registrations
        MoCreatures.LOGGER.info("Initializing Mo' Creatures spawn registry");
    }
    
    /**
     * Register entity attributes
     */
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        // Register entity attributes here
        init();
    }
} 