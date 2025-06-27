package drzhark.mocreatures.world;

import org.apache.commons.lang3.tuple.Pair;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.config.biome.BiomeConfig;
import drzhark.mocreatures.config.biome.BiomeSpawnConfig;
import drzhark.mocreatures.config.biome.MoCConfig;
import drzhark.mocreatures.config.biome.SpawnBiomeData;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.common.world.ModifiableStructureInfo;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCWorldRegistry {

    public static void modifyStructure(Holder<Structure> structure, ModifiableStructureInfo.StructureInfo.Builder builder) {
        if (structure.is(BuiltinStructures.VILLAGE_DESERT) || structure.is(BuiltinStructures.VILLAGE_PLAINS) || structure.is(BuiltinStructures.VILLAGE_SAVANNA) || structure.is(BuiltinStructures.VILLAGE_SNOWY) || structure.is(BuiltinStructures.VILLAGE_TAIGA)) {
            // Get kitty spawn data from entity map
            MoCEntityData kittyData = getEntityData(MoCEntities.KITTY.get());
            if (kittyData != null && kittyData.getCanSpawn() && kittyData.getFrequency() > 0) {
                int spawnWeight = Math.max(1, kittyData.getFrequency() / 4); // Reduced weight for village spawns
                builder.getStructureSettings().getOrAddSpawnOverrides(MobCategory.CREATURE)
                    .addSpawn(new MobSpawnSettings.SpawnerData(MoCEntities.KITTY.get(), spawnWeight, 1, 3));
            }
        }
    }

    /**
     * Helper method to get entity data from MoCreatures.entityMap
     */
    private static MoCEntityData getEntityData(EntityType<?> entityType) {
        if (MoCreatures.entityMap == null) {
            MoCreatures.LOGGER.warn("MoCWorldRegistry: entityMap is null! Spawn system not initialized.");
            return null;
        }
        return MoCreatures.entityMap.get(entityType);
    }



    private static ResourceLocation getBiomeName(Holder<Biome> biome) {
        return biome.unwrap().map((resourceKey) -> resourceKey.location(), (noKey) -> null);
    }

    public static boolean testBiome(Pair<String, SpawnBiomeData> entry, Holder<Biome> biome) {
        boolean result = false;
        try {
            result = BiomeConfig.test(entry, biome, getBiomeName(biome));
        } catch (Exception e) {
            MoCreatures.LOGGER.warn("could not test biome config for " + entry.getLeft() + ", defaulting to no spawns for mob");
            result = false;
        }
        return result;
    }

    public static void addBiomeSpawns(Holder<Biome> biome, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        String biomeName = getBiomeName(biome) != null ? getBiomeName(biome).toString() : "unknown";
        MoCreatures.LOGGER.info("MoCWorldRegistry: Adding spawns for biome {}", biomeName);
        
        // Initialize the JSON config system
        BiomeSpawnConfig.init();
        
        // MoCreatures Animal Spawns
        
        // Bears
        addCreatureSpawn("black_bear", MoCEntities.BLACK_BEAR.get(), biome, biomeName, builder);
        addCreatureSpawn("grizzly_bear", MoCEntities.GRIZZLY_BEAR.get(), biome, biomeName, builder);
        addCreatureSpawn("polar_bear", MoCEntities.POLAR_BEAR.get(), biome, biomeName, builder);
        addCreatureSpawn("panda_bear", MoCEntities.PANDA_BEAR.get(), biome, biomeName, builder);
        
        // Birds
        addCreatureSpawn("bird", MoCEntities.BIRD.get(), biome, biomeName, builder);
        addCreatureSpawn("duck", MoCEntities.DUCK.get(), biome, biomeName, builder);
        addCreatureSpawn("turkey", MoCEntities.TURKEY.get(), biome, biomeName, builder);
        
        // Land animals
        addCreatureSpawn("boar", MoCEntities.BOAR.get(), biome, biomeName, builder);
        addCreatureSpawn("bunny", MoCEntities.BUNNY.get(), biome, biomeName, builder);
        addCreatureSpawn("deer", MoCEntities.DEER.get(), biome, biomeName, builder);
        addCreatureSpawn("goat", MoCEntities.GOAT.get(), biome, biomeName, builder);
        addCreatureSpawn("kitty", MoCEntities.KITTY.get(), biome, biomeName, builder);
        
        // Semi-aquatic creatures
        addCreatureSpawn("crocodile", MoCEntities.CROCODILE.get(), biome, biomeName, builder);
        addCreatureSpawn("turtle", MoCEntities.TURTLE.get(), biome, biomeName, builder);
        
        // Desert/savanna creatures
        addCreatureSpawn("elephant", MoCEntities.ELEPHANT.get(), biome, biomeName, builder);
        addCreatureSpawn("filch_lizard", MoCEntities.FILCH_LIZARD.get(), biome, biomeName, builder);
        addCreatureSpawn("ostrich", MoCEntities.OSTRICH.get(), biome, biomeName, builder);
        
        // Foxes
        addCreatureSpawn("fox", MoCEntities.FOX.get(), biome, biomeName, builder);
        
        // Reptiles
        addCreatureSpawn("komodo_dragon", MoCEntities.KOMODO_DRAGON.get(), biome, biomeName, builder);
        addCreatureSpawn("snake", MoCEntities.SNAKE.get(), biome, biomeName, builder);
        
        // Big cats
        addCreatureSpawn("leopard", MoCEntities.LEOPARD.get(), biome, biomeName, builder);
        addCreatureSpawn("lion", MoCEntities.LION.get(), biome, biomeName, builder);
        addCreatureSpawn("panther", MoCEntities.PANTHER.get(), biome, biomeName, builder);
        addCreatureSpawn("tiger", MoCEntities.TIGER.get(), biome, biomeName, builder);
        
        // Big cat hybrids
        addCreatureSpawn("liger", MoCEntities.LIGER.get(), biome, biomeName, builder);
        addCreatureSpawn("lither", MoCEntities.LITHER.get(), biome, biomeName, builder);
        addCreatureSpawn("panthger", MoCEntities.PANTHGER.get(), biome, biomeName, builder);
        addCreatureSpawn("panthard", MoCEntities.PANTHARD.get(), biome, biomeName, builder);
        addCreatureSpawn("leoger", MoCEntities.LEOGER.get(), biome, biomeName, builder);
        
        // Small mammals
        addCreatureSpawn("mouse", MoCEntities.MOUSE.get(), biome, biomeName, builder);
        addCreatureSpawn("mole", MoCEntities.MOLE.get(), biome, biomeName, builder);
        addCreatureSpawn("raccoon", MoCEntities.RACCOON.get(), biome, biomeName, builder);
        
        // Horses
        addCreatureSpawn("wild_horse", MoCEntities.WILDHORSE.get(), biome, biomeName, builder);
        
        // Magical creatures
        addCreatureSpawn("ent", MoCEntities.ENT.get(), biome, biomeName, builder);
        addCreatureSpawn("wyvern", MoCEntities.WYVERN.get(), biome, biomeName, builder);
        
        // Monsters
        addCreatureSpawn("green_ogre", MoCEntities.GREEN_OGRE.get(), biome, biomeName, builder);
        addCreatureSpawn("cave_ogre", MoCEntities.CAVE_OGRE.get(), biome, biomeName, builder);
        addCreatureSpawn("fire_ogre", MoCEntities.FIRE_OGRE.get(), biome, biomeName, builder);
        
        // Scorpions
        addCreatureSpawn("cave_scorpion", MoCEntities.CAVE_SCORPION.get(), biome, biomeName, builder);
        addCreatureSpawn("dirt_scorpion", MoCEntities.DIRT_SCORPION.get(), biome, biomeName, builder);
        addCreatureSpawn("fire_scorpion", MoCEntities.FIRE_SCORPION.get(), biome, biomeName, builder);
        addCreatureSpawn("frost_scorpion", MoCEntities.FROST_SCORPION.get(), biome, biomeName, builder);
        addCreatureSpawn("undead_scorpion", MoCEntities.UNDEAD_SCORPION.get(), biome, biomeName, builder);
        
        // Golems
        addCreatureSpawn("big_golem", MoCEntities.BIG_GOLEM.get(), biome, biomeName, builder);
        addCreatureSpawn("mini_golem", MoCEntities.MINI_GOLEM.get(), biome, biomeName, builder);
        
        // Manticores
        addCreatureSpawn("dark_manticore", MoCEntities.DARK_MANTICORE.get(), biome, biomeName, builder);
        addCreatureSpawn("fire_manticore", MoCEntities.FIRE_MANTICORE.get(), biome, biomeName, builder);
        addCreatureSpawn("frost_manticore", MoCEntities.FROST_MANTICORE.get(), biome, biomeName, builder);
        addCreatureSpawn("plain_manticore", MoCEntities.PLAIN_MANTICORE.get(), biome, biomeName, builder);
        addCreatureSpawn("toxic_manticore", MoCEntities.TOXIC_MANTICORE.get(), biome, biomeName, builder);
        
        // Werewolves and variants
        addCreatureSpawn("werewolf", MoCEntities.WEREWOLF.get(), biome, biomeName, builder);
        addCreatureSpawn("wwolf", MoCEntities.WWOLF.get(), biome, biomeName, builder);
        
        // Rats
        addCreatureSpawn("rat", MoCEntities.RAT.get(), biome, biomeName, builder);
        addCreatureSpawn("hell_rat", MoCEntities.HELL_RAT.get(), biome, biomeName, builder);
        
        // Undead
        addCreatureSpawn("silver_skeleton", MoCEntities.SILVER_SKELETON.get(), biome, biomeName, builder);
        
        // Wraiths
        addCreatureSpawn("wraith", MoCEntities.WRAITH.get(), biome, biomeName, builder);
        addCreatureSpawn("flame_wraith", MoCEntities.FLAME_WRAITH.get(), biome, biomeName, builder);
        
        // Nether mounts
        addCreatureSpawn("horse_mob", MoCEntities.HORSE_MOB.get(), biome, biomeName, builder);
        
        // Aquatic creatures
        addCreatureSpawn("dolphin", MoCEntities.DOLPHIN.get(), biome, biomeName, builder);
        addCreatureSpawn("shark", MoCEntities.SHARK.get(), biome, biomeName, builder);
        addCreatureSpawn("manta_ray", MoCEntities.MANTA_RAY.get(), biome, biomeName, builder);
        addCreatureSpawn("jellyfish", MoCEntities.JELLYFISH.get(), biome, biomeName, builder);
        
        // River/swamp creatures
        addCreatureSpawn("bass", MoCEntities.BASS.get(), biome, biomeName, builder);
        addCreatureSpawn("sting_ray", MoCEntities.STING_RAY.get(), biome, biomeName, builder);
        
        // Fish
        addCreatureSpawn("anchovy", MoCEntities.ANCHOVY.get(), biome, biomeName, builder);
        addCreatureSpawn("angelfish", MoCEntities.ANGELFISH.get(), biome, biomeName, builder);
        addCreatureSpawn("angler", MoCEntities.ANGLER.get(), biome, biomeName, builder);
        addCreatureSpawn("clownfish", MoCEntities.CLOWNFISH.get(), biome, biomeName, builder);
        addCreatureSpawn("goldfish", MoCEntities.GOLDFISH.get(), biome, biomeName, builder);
        addCreatureSpawn("hippotang", MoCEntities.HIPPOTANG.get(), biome, biomeName, builder);
        addCreatureSpawn("manderin", MoCEntities.MANDERIN.get(), biome, biomeName, builder);
        addCreatureSpawn("cod", MoCEntities.COD.get(), biome, biomeName, builder);
        addCreatureSpawn("salmon", MoCEntities.SALMON.get(), biome, biomeName, builder);
        addCreatureSpawn("piranha", MoCEntities.PIRANHA.get(), biome, biomeName, builder);
        addCreatureSpawn("fishy", MoCEntities.FISHY.get(), biome, biomeName, builder);
        
        // Ambient creatures
        addCreatureSpawn("ant", MoCEntities.ANT.get(), biome, biomeName, builder);
        addCreatureSpawn("bee", MoCEntities.BEE.get(), biome, biomeName, builder);
        addCreatureSpawn("butterfly", MoCEntities.BUTTERFLY.get(), biome, biomeName, builder);
        addCreatureSpawn("cricket", MoCEntities.CRICKET.get(), biome, biomeName, builder);
        addCreatureSpawn("dragonfly", MoCEntities.DRAGONFLY.get(), biome, biomeName, builder);
        addCreatureSpawn("firefly", MoCEntities.FIREFLY.get(), biome, biomeName, builder);
        addCreatureSpawn("fly", MoCEntities.FLY.get(), biome, biomeName, builder);
        addCreatureSpawn("grasshopper", MoCEntities.GRASSHOPPER.get(), biome, biomeName, builder);
        addCreatureSpawn("maggot", MoCEntities.MAGGOT.get(), biome, biomeName, builder);
        addCreatureSpawn("roach", MoCEntities.ROACH.get(), biome, biomeName, builder);
        addCreatureSpawn("crab", MoCEntities.CRAB.get(), biome, biomeName, builder);
        addCreatureSpawn("snail", MoCEntities.SNAIL.get(), biome, biomeName, builder);
    }
    
    private static void addCreatureSpawn(String creatureName, EntityType<?> entityType, Holder<Biome> biome, String biomeName, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        BiomeSpawnConfig.CreatureSpawnData spawnData = BiomeSpawnConfig.getSpawnData(creatureName);
        if (spawnData == null || !spawnData.enabled || spawnData.weight <= 0) {
            return;
        }
        
        ResourceLocation biomeRL = getBiomeName(biome);
        if (!BiomeSpawnConfig.testBiome(creatureName, biome, biomeRL)) {
            return;
        }
        
        // Convert category string to MobCategory
        MobCategory category;
        try {
            category = MobCategory.valueOf(spawnData.category);
        } catch (IllegalArgumentException e) {
            MoCreatures.LOGGER.warn("Invalid mob category '{}' for creature '{}', defaulting to CREATURE", spawnData.category, creatureName);
            category = MobCategory.CREATURE;
        }
        
        // Add the spawn
        builder.getMobSpawnSettings().getSpawner(category).add(
            new MobSpawnSettings.SpawnerData(entityType, spawnData.weight, spawnData.minCount, spawnData.maxCount)
        );
        
        MoCreatures.LOGGER.debug("Added spawn for {} in biome {} (weight: {}, count: {}-{})", 
            creatureName, biomeName, spawnData.weight, spawnData.minCount, spawnData.maxCount);
    }
    
    // Legacy method - remove the old long implementation
    public static void addBiomeSpawnsOld(Holder<Biome> biome, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        String biomeName = getBiomeName(biome) != null ? getBiomeName(biome).toString() : "unknown";
        MoCreatures.LOGGER.info("MoCWorldRegistry: Adding spawns for biome {}", biomeName);
        
        // MoCreatures Animal Spawns
        
        // Bears - kept one example of old format
        if (testBiome(BiomeConfig.blackBear, biome) && MoCConfig.blackBearSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.BLACK_BEAR.get(), MoCConfig.blackBearSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.grizzlyBear, biome) && MoCConfig.grizzlyBearSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.GRIZZLY_BEAR.get(), MoCConfig.grizzlyBearSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.polarBear, biome) && MoCConfig.polarBearSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.POLAR_BEAR.get(), MoCConfig.polarBearSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.pandaBear, biome) && MoCConfig.pandaBearSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.PANDA_BEAR.get(), MoCConfig.pandaBearSpawnWeight, 1, 2));
        }
        
        // Birds
        if (testBiome(BiomeConfig.bird, biome) && MoCConfig.birdSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.BIRD.get(), MoCConfig.birdSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.duck, biome) && MoCConfig.duckSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.DUCK.get(), MoCConfig.duckSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.turkey, biome) && MoCConfig.turkeySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.TURKEY.get(), MoCConfig.turkeySpawnWeight, 1, 2));
        }
        
        // Land animals
        if (testBiome(BiomeConfig.boar, biome) && MoCConfig.boarSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.BOAR.get(), MoCConfig.boarSpawnWeight, 1, 3));
        }
        if (testBiome(BiomeConfig.bunny, biome) && MoCConfig.bunnySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.BUNNY.get(), MoCConfig.bunnySpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.deer, biome) && MoCConfig.deerSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.DEER.get(), MoCConfig.deerSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.goat, biome) && MoCConfig.goatSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.GOAT.get(), MoCConfig.goatSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.kitty, biome) && MoCConfig.kittySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.KITTY.get(), MoCConfig.kittySpawnWeight, 1, 3));
        }
        
        // Semi-aquatic creatures
        if (testBiome(BiomeConfig.crocodile, biome) && MoCConfig.crocodileSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.CROCODILE.get(), MoCConfig.crocodileSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.turtle, biome) && MoCConfig.turtleSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.TURTLE.get(), MoCConfig.turtleSpawnWeight, 1, 3));
        }
        
        // Desert/savanna creatures
        if (testBiome(BiomeConfig.elephant, biome) && MoCConfig.elephantSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.ELEPHANT.get(), MoCConfig.elephantSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.filchLizard, biome) && MoCConfig.filchLizardSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.FILCH_LIZARD.get(), MoCConfig.filchLizardSpawnWeight, 1, 3));
        }
        if (testBiome(BiomeConfig.ostrich, biome) && MoCConfig.ostrichSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.OSTRICH.get(), MoCConfig.ostrichSpawnWeight, 1, 2));
        }
        
        // Foxes
        if (testBiome(BiomeConfig.fox, biome) && MoCConfig.foxSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.FOX.get(), MoCConfig.foxSpawnWeight, 1, 2));
        }
        
        // Reptiles
        if (testBiome(BiomeConfig.komodoDragon, biome) && MoCConfig.komodoDragonSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.KOMODO_DRAGON.get(), MoCConfig.komodoDragonSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.snake, biome) && MoCConfig.snakeSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.SNAKE.get(), MoCConfig.snakeSpawnWeight, 1, 3));
        }
        
        // Big cats
        if (testBiome(BiomeConfig.leopard, biome) && MoCConfig.leopardSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.LEOPARD.get(), MoCConfig.leopardSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.lion, biome) && MoCConfig.lionSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.LION.get(), MoCConfig.lionSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.panther, biome) && MoCConfig.pantherSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.PANTHER.get(), MoCConfig.pantherSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.tiger, biome) && MoCConfig.tigerSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.TIGER.get(), MoCConfig.tigerSpawnWeight, 1, 2));
        }
        
        // Big cat hybrids
        if (testBiome(BiomeConfig.liger, biome) && MoCConfig.ligerSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.LIGER.get(), MoCConfig.ligerSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.lither, biome) && MoCConfig.litherSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.LITHER.get(), MoCConfig.litherSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.panthger, biome) && MoCConfig.panthgerSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.PANTHGER.get(), MoCConfig.panthgerSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.panthard, biome) && MoCConfig.panthardSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.PANTHARD.get(), MoCConfig.panthardSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.leoger, biome) && MoCConfig.leogerSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.LEOGER.get(), MoCConfig.leogerSpawnWeight, 1, 2));
        }
        
        // Small mammals
        if (testBiome(BiomeConfig.mouse, biome) && MoCConfig.mouseSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.MOUSE.get(), MoCConfig.mouseSpawnWeight, 2, 6));
        }
        if (testBiome(BiomeConfig.mole, biome) && MoCConfig.moleSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.MOLE.get(), MoCConfig.moleSpawnWeight, 1, 3));
        }
        if (testBiome(BiomeConfig.raccoon, biome) && MoCConfig.raccoonSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.RACCOON.get(), MoCConfig.raccoonSpawnWeight, 1, 2));
        }
        
        // Horses
        if (testBiome(BiomeConfig.wildHorse, biome) && MoCConfig.wildHorseSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.WILDHORSE.get(), MoCConfig.wildHorseSpawnWeight, 2, 4));
        }
        
        // Magical creatures
        if (testBiome(BiomeConfig.ent, biome) && MoCConfig.entSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.ENT.get(), MoCConfig.entSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.wyvern, biome) && MoCConfig.wyvernSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.WYVERN.get(), MoCConfig.wyvernSpawnWeight, 1, 1));
        }
        
        // MoCreatures Monster Spawns
        
        // Ogres
        if (testBiome(BiomeConfig.greenOgre, biome) && MoCConfig.greenOgreSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.GREEN_OGRE.get(), MoCConfig.greenOgreSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.caveOgre, biome) && MoCConfig.caveOgreSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.CAVE_OGRE.get(), MoCConfig.caveOgreSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.fireOgre, biome) && MoCConfig.fireOgreSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.FIRE_OGRE.get(), MoCConfig.fireOgreSpawnWeight, 1, 1));
        }
        
        // Scorpions
        if (testBiome(BiomeConfig.caveScorpion, biome) && MoCConfig.caveScorpionSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.CAVE_SCORPION.get(), MoCConfig.caveScorpionSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.dirtScorpion, biome) && MoCConfig.dirtScorpionSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.DIRT_SCORPION.get(), MoCConfig.dirtScorpionSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.fireScorpion, biome) && MoCConfig.fireScorpionSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.FIRE_SCORPION.get(), MoCConfig.fireScorpionSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.frostScorpion, biome) && MoCConfig.frostScorpionSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.FROST_SCORPION.get(), MoCConfig.frostScorpionSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.undeadScorpion, biome) && MoCConfig.undeadScorpionSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.UNDEAD_SCORPION.get(), MoCConfig.undeadScorpionSpawnWeight, 1, 2));
        }
        
        // Golems
        if (testBiome(BiomeConfig.bigGolem, biome) && MoCConfig.bigGolemSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.BIG_GOLEM.get(), MoCConfig.bigGolemSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.miniGolem, biome) && MoCConfig.miniGolemSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.MINI_GOLEM.get(), MoCConfig.miniGolemSpawnWeight, 1, 2));
        }
        
        // Manticores
        if (testBiome(BiomeConfig.darkManticore, biome) && MoCConfig.darkManticoreSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.DARK_MANTICORE.get(), MoCConfig.darkManticoreSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.fireManticore, biome) && MoCConfig.fireManticoreSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.FIRE_MANTICORE.get(), MoCConfig.fireManticoreSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.frostManticore, biome) && MoCConfig.frostManticoreSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.FROST_MANTICORE.get(), MoCConfig.frostManticoreSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.plainManticore, biome) && MoCConfig.plainManticoreSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.PLAIN_MANTICORE.get(), MoCConfig.plainManticoreSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.toxicManticore, biome) && MoCConfig.toxicManticoreSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.TOXIC_MANTICORE.get(), MoCConfig.toxicManticoreSpawnWeight, 1, 1));
        }
        
        // Werewolves and variants
        if (testBiome(BiomeConfig.werewolf, biome) && MoCConfig.werewolfSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.WEREWOLF.get(), MoCConfig.werewolfSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.wwolf, biome) && MoCConfig.wwolfSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.WWOLF.get(), MoCConfig.wwolfSpawnWeight, 1, 2));
        }
        
        // Rats
        if (testBiome(BiomeConfig.rat, biome) && MoCConfig.ratSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.RAT.get(), MoCConfig.ratSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.hellRat, biome) && MoCConfig.hellRatSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.HELL_RAT.get(), MoCConfig.hellRatSpawnWeight, 1, 3));
        }
        
        // Undead
        if (testBiome(BiomeConfig.silverSkeleton, biome) && MoCConfig.silverSkeletonSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.SILVER_SKELETON.get(), MoCConfig.silverSkeletonSpawnWeight, 1, 2));
        }
        
        // Wraiths
        if (testBiome(BiomeConfig.wraith, biome) && MoCConfig.wraithSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.WRAITH.get(), MoCConfig.wraithSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.flameWraith, biome) && MoCConfig.flameWraithSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.FLAME_WRAITH.get(), MoCConfig.flameWraithSpawnWeight, 1, 1));
        }
        
        // Nether mounts
        if (testBiome(BiomeConfig.horseMob, biome) && MoCConfig.horseMobSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(MoCEntities.HORSE_MOB.get(), MoCConfig.horseMobSpawnWeight, 1, 2));
        }
        
        // MoCreatures Aquatic Spawns
        
        // Ocean creatures
        if (testBiome(BiomeConfig.dolphin, biome) && MoCConfig.dolphinSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.DOLPHIN.get(), MoCConfig.dolphinSpawnWeight, 1, 3));
        }
        if (testBiome(BiomeConfig.shark, biome) && MoCConfig.sharkSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.SHARK.get(), MoCConfig.sharkSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.mantaRay, biome) && MoCConfig.mantaRaySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.MANTA_RAY.get(), MoCConfig.mantaRaySpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.jellyfish, biome) && MoCConfig.jellyfishSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.JELLYFISH.get(), MoCConfig.jellyfishSpawnWeight, 1, 3));
        }
        
        // River/swamp creatures
        if (testBiome(BiomeConfig.bass, biome) && MoCConfig.bassSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.BASS.get(), MoCConfig.bassSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.stingRay, biome) && MoCConfig.stingRaySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.STING_RAY.get(), MoCConfig.stingRaySpawnWeight, 1, 2));
        }
        
        // Small fish
        if (testBiome(BiomeConfig.anchovy, biome) && MoCConfig.anchovySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.ANCHOVY.get(), MoCConfig.anchovySpawnWeight, 3, 6));
        }
        if (testBiome(BiomeConfig.angelfish, biome) && MoCConfig.angelfishSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.ANGELFISH.get(), MoCConfig.angelfishSpawnWeight, 3, 5));
        }
        if (testBiome(BiomeConfig.angler, biome) && MoCConfig.anglerSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.ANGLER.get(), MoCConfig.anglerSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.clownfish, biome) && MoCConfig.clownfishSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.CLOWNFISH.get(), MoCConfig.clownfishSpawnWeight, 3, 5));
        }
        if (testBiome(BiomeConfig.goldfish, biome) && MoCConfig.goldfishSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.GOLDFISH.get(), MoCConfig.goldfishSpawnWeight, 3, 5));
        }
        if (testBiome(BiomeConfig.hippotang, biome) && MoCConfig.hippotangSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.HIPPOTANG.get(), MoCConfig.hippotangSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.manderin, biome) && MoCConfig.manderinSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.MANDERIN.get(), MoCConfig.manderinSpawnWeight, 2, 4));
        }
        
        // Medium fish
        if (testBiome(BiomeConfig.cod, biome) && MoCConfig.codSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.COD.get(), MoCConfig.codSpawnWeight, 3, 6));
        }
        if (testBiome(BiomeConfig.salmon, biome) && MoCConfig.salmonSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.SALMON.get(), MoCConfig.salmonSpawnWeight, 3, 6));
        }
        
        // Predatory fish
        if (testBiome(BiomeConfig.piranha, biome) && MoCConfig.piranhaSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(MoCEntities.PIRANHA.get(), MoCConfig.piranhaSpawnWeight, 3, 6));
        }
        
        // General fish
        if (testBiome(BiomeConfig.fishy, biome) && MoCConfig.fishySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.FISHY.get(), MoCConfig.fishySpawnWeight, 4, 8));
        }
        
        // MoCreatures Ambient Spawns
        
        // Insects
        if (testBiome(BiomeConfig.ant, biome) && MoCConfig.antSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.ANT.get(), MoCConfig.antSpawnWeight, 4, 8));
        }
        if (testBiome(BiomeConfig.bee, biome) && MoCConfig.beeSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.BEE.get(), MoCConfig.beeSpawnWeight, 3, 6));
        }
        if (testBiome(BiomeConfig.butterfly, biome) && MoCConfig.butterflySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.BUTTERFLY.get(), MoCConfig.butterflySpawnWeight, 3, 6));
        }
        if (testBiome(BiomeConfig.cricket, biome) && MoCConfig.cricketSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.CRICKET.get(), MoCConfig.cricketSpawnWeight, 3, 6));
        }
        if (testBiome(BiomeConfig.dragonfly, biome) && MoCConfig.dragonflySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.DRAGONFLY.get(), MoCConfig.dragonflySpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.firefly, biome) && MoCConfig.fireflySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.FIREFLY.get(), MoCConfig.fireflySpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.fly, biome) && MoCConfig.flySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.FLY.get(), MoCConfig.flySpawnWeight, 3, 6));
        }
        if (testBiome(BiomeConfig.grasshopper, biome) && MoCConfig.grasshopperSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.GRASSHOPPER.get(), MoCConfig.grasshopperSpawnWeight, 3, 6));
        }
        if (testBiome(BiomeConfig.maggot, biome) && MoCConfig.maggotSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.MAGGOT.get(), MoCConfig.maggotSpawnWeight, 2, 5));
        }
        if (testBiome(BiomeConfig.roach, biome) && MoCConfig.roachSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.ROACH.get(), MoCConfig.roachSpawnWeight, 2, 5));
        }
        
        // Beach/aquatic ambient
        if (testBiome(BiomeConfig.crab, biome) && MoCConfig.crabSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.CRAB.get(), MoCConfig.crabSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.snail, biome) && MoCConfig.snailSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(MoCEntities.SNAIL.get(), MoCConfig.snailSpawnWeight, 2, 4));
        }
    }
}