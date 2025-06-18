package drzhark.mocreatures.world;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages mob spawn configurations for Mo' Creatures
 */
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
public class MoCSpawnConfig {
    
    // List of all spawn configurations
    private static final List<MoCSpawnData> SPAWN_DATA = new ArrayList<>();
    
    /**
     * Initialize all spawn configurations
     */
    public static void init() {
        MoCreatures.LOGGER.info("Initializing Mo' Creatures spawn configurations");
        
        // Clear existing configurations
        SPAWN_DATA.clear();
        
        // Register creatures by category
        registerAnimalSpawns();
        registerMonsterSpawns();
        registerAquaticSpawns();
        registerAmbientSpawns();
    }
    
    /**
     * Register animal spawns
     */
    private static void registerAnimalSpawns() {
        // Bears
        addSpawn("BlackBear", 4, MoCEntities.BLACK_BEAR.get(), 8, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_CONIFEROUS);
            
        addSpawn("GrizzlyBear", 4, MoCEntities.GRIZZLY_BEAR.get(), 7, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST);
            
        addSpawn("PolarBear", 4, MoCEntities.POLAR_BEAR.get(), 8, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_SNOWY);
            
        addSpawn("PandaBear", 4, MoCEntities.PANDA_BEAR.get(), 7, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_JUNGLE);
        
        // Birds
        addSpawn("Bird", 4, MoCEntities.BIRD.get(), 16, 2, 3)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_PLAINS, 
                MoCBiomeTags.IS_BADLANDS, MoCBiomeTags.IS_LUSH, MoCBiomeTags.IS_STEEP);
        
        // Common land animals
        addSpawn("Boar", 3, MoCEntities.BOAR.get(), 12, 2, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_PLAINS);
            
        addSpawn("Bunny", 4, MoCEntities.BUNNY.get(), 12, 2, 3)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_SNOWY, 
                MoCBiomeTags.IS_CONIFEROUS, MoCBiomeTags.IS_STEEP, MoCBiomeTags.IS_WYVERN_LAIR);
                
        addSpawn("Deer", 2, MoCEntities.DEER.get(), 10, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_CONIFEROUS);
            
        addSpawn("Duck", 3, MoCEntities.DUCK.get(), 12, 2, 4)
            .addBiomeTags(MoCBiomeTags.IS_RIVER, MoCBiomeTags.IS_LUSH);
        
        // Semi-aquatic creatures
        addSpawn("Crocodile", 2, MoCEntities.CROCODILE.get(), 10, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP);
            
        addSpawn("Turtle", 3, MoCEntities.TURTLE.get(), 12, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_RIVER);
        
        // Desert/savanna creatures
        addSpawn("Elephant", 3, MoCEntities.ELEPHANT.get(), 6, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_SAVANNA, MoCBiomeTags.IS_SNOWY)
            .excludeBiomeTags(MoCBiomeTags.IS_BADLANDS);
            
        addSpawn("FilchLizard", 2, MoCEntities.FILCH_LIZARD.get(), 6, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_SAVANNA, MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_BADLANDS, MoCBiomeTags.IS_WYVERN_LAIR);
            
        addSpawn("Ostrich", 3, MoCEntities.OSTRICH.get(), 7, 1, 1)
            .addBiomeTags(MoCBiomeTags.IS_SAVANNA, MoCBiomeTags.IS_SANDY)
            .excludeBiomeTags(MoCBiomeTags.IS_BADLANDS);
        
        // Foxes and wild canines
        addSpawn("Fox", 2, MoCEntities.FOX.get(), 10, 1, 1)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_SNOWY, MoCBiomeTags.IS_CONIFEROUS);
            
        // Hoofed animals
        addSpawn("Goat", 2, MoCEntities.GOAT.get(), 12, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_STEEP);
            
        // Village animals
        addSpawn("Kitty", 3, MoCEntities.KITTY.get(), 8, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_FOREST);
            
        // Reptiles
        addSpawn("KomodoDragon", 2, MoCEntities.KOMODO_DRAGON.get(), 12, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_SAVANNA);
            
        // Big cats
        addSpawn("Leopard", 4, MoCEntities.LEOPARD.get(), 7, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_SNOWY, MoCBiomeTags.IS_SAVANNA);
            
        addSpawn("Lion", 4, MoCEntities.LION.get(), 8, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SAVANNA, MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_BADLANDS);
            
        addSpawn("Panther", 4, MoCEntities.PANTHER.get(), 6, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_JUNGLE);
            
        addSpawn("Tiger", 4, MoCEntities.TIGER.get(), 7, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_JUNGLE);
        
        // Big cat hybrids
        addSpawn("Liger", 4, MoCEntities.LIGER.get(), 8, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_SAVANNA);
            
        addSpawn("Lither", 4, MoCEntities.LITHER.get(), 7, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_JUNGLE);
            
        addSpawn("Panthger", 4, MoCEntities.PANTHGER.get(), 7, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_JUNGLE);
            
        addSpawn("Panthard", 4, MoCEntities.PANTHARD.get(), 7, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_JUNGLE);
            
        addSpawn("Leoger", 4, MoCEntities.LEOGER.get(), 7, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_SAVANNA);
        
        // Small mammals
        addSpawn("Mouse", 2, MoCEntities.MOUSE.get(), 10, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_BADLANDS, MoCBiomeTags.IS_STEEP);
            
        addSpawn("Mole", 3, MoCEntities.MOLE.get(), 10, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_LUSH, MoCBiomeTags.IS_PLAINS);
            
        addSpawn("Raccoon", 2, MoCEntities.RACCOON.get(), 12, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST);
            
        // Reptiles
        addSpawn("Snake", 3, MoCEntities.SNAKE.get(), 14, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_BADLANDS, MoCBiomeTags.IS_PLAINS, 
                MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_LUSH, 
                MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_STEEP, MoCBiomeTags.IS_WYVERN_LAIR);
                
        // Farm animals
        addSpawn("Turkey", 2, MoCEntities.TURKEY.get(), 12, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_PLAINS);
            
        // Horses
        addSpawn("WildHorse", 4, MoCEntities.WILDHORSE.get(), 12, 1, 4)
            .addBiomeTags(MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_SAVANNA);
            
        // Magical creatures
        addSpawn("Ent", 3, MoCEntities.ENT.get(), 5, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST);
            
        // Wyvern dimension
        addSpawn("Wyvern", 3, MoCEntities.WYVERN.get(), 12, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_WYVERN_LAIR);
    }
    
    /**
     * Register monster spawns
     */
    private static void registerMonsterSpawns() {
        // Ogres
        addSpawn("GreenOgre", 3, MoCCategory.MONSTER, MoCEntities.GREEN_OGRE.get(), 8, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_LUSH, 
                MoCBiomeTags.IS_DEAD, MoCBiomeTags.IS_SPOOKY);
                
        addSpawn("CaveOgre", 3, MoCCategory.MONSTER, MoCEntities.CAVE_OGRE.get(), 5, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_MOUNTAIN);
            
        addSpawn("FireOgre", 3, MoCCategory.MONSTER, MoCEntities.FIRE_OGRE.get(), 6, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_NETHER);
        
        // Scorpions
        addSpawn("CaveScorpion", 3, MoCCategory.MONSTER, MoCEntities.CAVE_SCORPION.get(), 4, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_MOUNTAIN, MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_SNOWY, 
                MoCBiomeTags.IS_BADLANDS, MoCBiomeTags.IS_DRY, MoCBiomeTags.IS_HOT, 
                MoCBiomeTags.IS_DEAD, MoCBiomeTags.IS_SPOOKY);
                
        addSpawn("DirtScorpion", 3, MoCCategory.MONSTER, MoCEntities.DIRT_SCORPION.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_BADLANDS, MoCBiomeTags.IS_DRY, MoCBiomeTags.IS_HOT);
            
        addSpawn("FireScorpion", 3, MoCCategory.MONSTER, MoCEntities.FIRE_SCORPION.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_NETHER);
            
        addSpawn("FrostScorpion", 3, MoCCategory.MONSTER, MoCEntities.FROST_SCORPION.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SNOWY);
            
        addSpawn("UndeadScorpion", 3, MoCCategory.MONSTER, MoCEntities.UNDEAD_SCORPION.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_DEAD, MoCBiomeTags.IS_SPOOKY);
            
        // Golem variants
        addSpawn("BigGolem", 1, MoCCategory.MONSTER, MoCEntities.BIG_GOLEM.get(), 3, 1, 1)
            .addBiomeTags(MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_HILL, MoCBiomeTags.IS_BADLANDS, 
                MoCBiomeTags.IS_MOUNTAIN, MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_DEAD);
                
        addSpawn("MiniGolem", 2, MoCCategory.MONSTER, MoCEntities.MINI_GOLEM.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_BADLANDS, MoCBiomeTags.IS_MOUNTAIN, 
                MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_DEAD);
        
        // Manticores
        addSpawn("DarkManticore", 3, MoCCategory.MONSTER, MoCEntities.DARK_MANTICORE.get(), 5, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_MOUNTAIN, MoCBiomeTags.IS_PLAINS, 
                MoCBiomeTags.IS_SNOWY, MoCBiomeTags.IS_DEAD, MoCBiomeTags.IS_SPOOKY);
                
        addSpawn("FireManticore", 3, MoCCategory.MONSTER, MoCEntities.FIRE_MANTICORE.get(), 8, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_NETHER);
            
        addSpawn("FrostManticore", 3, MoCCategory.MONSTER, MoCEntities.FROST_MANTICORE.get(), 8, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SNOWY);
            
        addSpawn("PlainManticore", 3, MoCCategory.MONSTER, MoCEntities.PLAIN_MANTICORE.get(), 8, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_MOUNTAIN, MoCBiomeTags.IS_PLAINS);
            
        addSpawn("ToxicManticore", 3, MoCCategory.MONSTER, MoCEntities.TOXIC_MANTICORE.get(), 8, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_DEAD, MoCBiomeTags.IS_SPOOKY);
            
        // Werewolves and variants
        addSpawn("Werewolf", 3, MoCCategory.MONSTER, MoCEntities.WEREWOLF.get(), 8, 1, 4)
            .addBiomeTags(MoCBiomeTags.IS_CONIFEROUS, MoCBiomeTags.IS_FOREST);
            
        addSpawn("WWolf", 3, MoCCategory.MONSTER, MoCEntities.WWOLF.get(), 8, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_SNOWY, MoCBiomeTags.IS_DEAD);
            
        // Rats
        addSpawn("Rat", 2, MoCCategory.MONSTER, MoCEntities.RAT.get(), 7, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_BADLANDS, MoCBiomeTags.IS_STEEP);
            
        addSpawn("HellRat", 4, MoCCategory.MONSTER, MoCEntities.HELL_RAT.get(), 6, 1, 4)
            .addBiomeTags(MoCBiomeTags.IS_NETHER);
            
        // Undead
        addSpawn("SilverSkeleton", 4, MoCCategory.MONSTER, MoCEntities.SILVER_SKELETON.get(), 6, 1, 4)
            .addBiomeTags(MoCBiomeTags.IS_SANDY, MoCBiomeTags.IS_SNOWY, MoCBiomeTags.IS_BADLANDS, 
                MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_DEAD, MoCBiomeTags.IS_SPOOKY);
                
        // Wraiths
        addSpawn("Wraith", 3, MoCCategory.MONSTER, MoCEntities.WRAITH.get(), 6, 1, 4)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_CONIFEROUS, MoCBiomeTags.IS_DEAD, 
                MoCBiomeTags.IS_DENSE, MoCBiomeTags.IS_SPOOKY);
                
        addSpawn("FlameWraith", 3, MoCCategory.MONSTER, MoCEntities.FLAME_WRAITH.get(), 5, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_NETHER);
            
        // Nether mounts
        addSpawn("HorseMob", 3, MoCCategory.MONSTER, MoCEntities.HORSE_MOB.get(), 8, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_NETHER, MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_SAVANNA, 
                MoCBiomeTags.IS_DEAD, MoCBiomeTags.IS_SPOOKY);
    }
    
    /**
     * Register aquatic spawns
     */
    private static void registerAquaticSpawns() {
        // Ocean creatures
        addSpawn("Dolphin", 3, MoCCategory.WATER_CREATURE, MoCEntities.DOLPHIN.get(), 6, 2, 4)
            .addBiomeTags(MoCBiomeTags.IS_OCEAN);
            
        addSpawn("Shark", 3, MoCCategory.WATER_CREATURE, MoCEntities.SHARK.get(), 6, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_OCEAN);
            
        addSpawn("MantaRay", 3, MoCCategory.WATER_CREATURE, MoCEntities.MANTA_RAY.get(), 10, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_OCEAN);
            
        // River/swamp creatures
        addSpawn("Bass", 4, MoCCategory.WATER_CREATURE, MoCEntities.BASS.get(), 10, 1, 4)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_RIVER, MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_PLAINS);
            
        addSpawn("StingRay", 3, MoCCategory.WATER_CREATURE, MoCEntities.STING_RAY.get(), 10, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_RIVER);
            
        // Small fish
        addSpawn("Anchovy", 6, MoCCategory.WATER_CREATURE, MoCEntities.ANCHOVY.get(), 12, 1, 6)
            .addBiomeTags(MoCBiomeTags.IS_BEACH, MoCBiomeTags.IS_OCEAN, MoCBiomeTags.IS_RIVER);
            
        addSpawn("AngelFish", 6, MoCCategory.WATER_CREATURE, MoCEntities.ANGELFISH.get(), 12, 1, 6)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_RIVER, MoCBiomeTags.IS_JUNGLE);
            
        addSpawn("Angler", 6, MoCCategory.WATER_CREATURE, MoCEntities.ANGLER.get(), 12, 1, 6)
            .addBiomeTags(MoCBiomeTags.IS_BEACH, MoCBiomeTags.IS_OCEAN);
            
        addSpawn("ClownFish", 6, MoCCategory.WATER_CREATURE, MoCEntities.CLOWNFISH.get(), 12, 1, 6)
            .addBiomeTags(MoCBiomeTags.IS_BEACH, MoCBiomeTags.IS_OCEAN);
            
        addSpawn("GoldFish", 6, MoCCategory.WATER_CREATURE, MoCEntities.GOLDFISH.get(), 12, 1, 6)
            .addBiomeTags(MoCBiomeTags.IS_RIVER);
            
        addSpawn("HippoTang", 6, MoCCategory.WATER_CREATURE, MoCEntities.HIPPOTANG.get(), 12, 1, 6)
            .addBiomeTags(MoCBiomeTags.IS_BEACH, MoCBiomeTags.IS_OCEAN);
            
        addSpawn("Manderin", 6, MoCCategory.WATER_CREATURE, MoCEntities.MANDERIN.get(), 12, 1, 6)
            .addBiomeTags(MoCBiomeTags.IS_BEACH, MoCBiomeTags.IS_OCEAN);
            
        // Medium fish
        addSpawn("Cod", 4, MoCCategory.WATER_CREATURE, MoCEntities.COD.get(), 10, 1, 4)
            .addBiomeTags(MoCBiomeTags.IS_BEACH, MoCBiomeTags.IS_OCEAN);
            
        addSpawn("Salmon", 4, MoCCategory.WATER_CREATURE, MoCEntities.SALMON.get(), 10, 1, 4)
            .addBiomeTags(MoCBiomeTags.IS_BEACH, MoCBiomeTags.IS_WATER, MoCBiomeTags.IS_OCEAN, 
                MoCBiomeTags.IS_RIVER, MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_PLAINS);
                
        // Predatory fish
        addSpawn("Piranha", 4, MoCCategory.WATER_CREATURE, MoCEntities.PIRANHA.get(), 4, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_LUSH);
            
        // Other aquatic creatures
        addSpawn("JellyFish", 4, MoCCategory.WATER_CREATURE, MoCEntities.JELLYFISH.get(), 8, 1, 4)
            .addBiomeTags(MoCBiomeTags.IS_OCEAN);
            
        // General fish
        addSpawn("Fishy", 6, MoCCategory.WATER_CREATURE, MoCEntities.FISHY.get(), 12, 1, 6)
            .addBiomeTags(MoCBiomeTags.IS_BEACH, MoCBiomeTags.IS_WATER, MoCBiomeTags.IS_OCEAN, 
                MoCBiomeTags.IS_RIVER, MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_PLAINS);
    }
    
    /**
     * Register ambient spawns
     */
    private static void registerAmbientSpawns() {
        // Insects
        addSpawn("Ant", 4, MoCCategory.AMBIENT, MoCEntities.ANT.get(), 12, 1, 4)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_BADLANDS, 
                MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_HOT, 
                MoCBiomeTags.IS_DRY, MoCBiomeTags.IS_LUSH, MoCBiomeTags.IS_SPARSE, MoCBiomeTags.IS_STEEP);
                
        // Fireflies
        addSpawn("Firefly", 3, MoCCategory.AMBIENT, MoCEntities.FIREFLY.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_JUNGLE, 
                MoCBiomeTags.IS_LUSH, MoCBiomeTags.IS_WYVERN_LAIR);
            
        // Crabs
        addSpawn("Crab", 3, MoCCategory.AMBIENT, MoCEntities.CRAB.get(), 6, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_BEACH);
            
        // Snails
        addSpawn("Snail", 2, MoCCategory.AMBIENT, MoCEntities.SNAIL.get(), 8, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_LUSH);
            
        // Flying insects
        addSpawn("Dragonfly", 3, MoCCategory.AMBIENT, MoCEntities.DRAGONFLY.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_JUNGLE, 
                MoCBiomeTags.IS_LUSH, MoCBiomeTags.IS_WYVERN_LAIR);
            
        addSpawn("Butterfly", 3, MoCCategory.AMBIENT, MoCEntities.BUTTERFLY.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_PLAINS, 
                MoCBiomeTags.IS_LUSH, MoCBiomeTags.IS_SAVANNA);
                
        addSpawn("Bee", 3, MoCCategory.AMBIENT, MoCEntities.BEE.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_PLAINS, 
                MoCBiomeTags.IS_LUSH, MoCBiomeTags.IS_SAVANNA);
                
        addSpawn("Fly", 3, MoCCategory.AMBIENT, MoCEntities.FLY.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_FOREST);
            
        // Cricket and Grasshopper
        addSpawn("Cricket", 2, MoCCategory.AMBIENT, MoCEntities.CRICKET.get(), 10, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_PLAINS, MoCBiomeTags.IS_SWAMP);
            
        addSpawn("Grasshopper", 2, MoCCategory.AMBIENT, MoCEntities.GRASSHOPPER.get(), 10, 1, 2)
            .addBiomeTags(MoCBiomeTags.IS_FOREST, MoCBiomeTags.IS_JUNGLE, MoCBiomeTags.IS_PLAINS, 
                MoCBiomeTags.IS_SAVANNA, MoCBiomeTags.IS_WYVERN_LAIR);
            
        // Maggots
        addSpawn("Maggot", 2, MoCCategory.AMBIENT, MoCEntities.MAGGOT.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_SWAMP, MoCBiomeTags.IS_DEAD, MoCBiomeTags.IS_SPOOKY);
            
        // Roaches
        addSpawn("Roach", 2, MoCCategory.AMBIENT, MoCEntities.ROACH.get(), 6, 1, 3)
            .addBiomeTags(MoCBiomeTags.IS_DEAD, MoCBiomeTags.IS_SPOOKY, MoCBiomeTags.IS_HOT);
    }
    
    /**
     * Add a spawn configuration for a creature
     */
    private static MoCSpawnData addSpawn(String name, int frequency, MobCategory category, 
            net.minecraft.world.entity.EntityType<?> entityType, int weight, int minCount, int maxCount) {
        MoCSpawnData data = new MoCSpawnData(name, frequency, category, entityType, weight, minCount, maxCount);
        SPAWN_DATA.add(data);
        return data;
    }
    
    /**
     * Shorthand for adding creature category spawns
     */
    private static MoCSpawnData addSpawn(String name, int frequency, 
            net.minecraft.world.entity.EntityType<?> entityType, int weight, int minCount, int maxCount) {
        return addSpawn(name, frequency, MobCategory.CREATURE, entityType, weight, minCount, maxCount);
    }
    
    /**
     * Get all spawn configurations
     */
    public static List<MoCSpawnData> getSpawnData() {
        if (SPAWN_DATA.isEmpty()) {
            init();
        }
        return SPAWN_DATA;
    }
    
    /**
     * Helper class for mob categories
     */
    private static class MoCCategory {
        public static final MobCategory CREATURE = MobCategory.CREATURE;
        public static final MobCategory MONSTER = MobCategory.MONSTER;
        public static final MobCategory AMBIENT = MobCategory.AMBIENT;
        public static final MobCategory WATER_CREATURE = MobCategory.WATER_CREATURE;
        public static final MobCategory UNDERGROUND_WATER_CREATURE = MobCategory.UNDERGROUND_WATER_CREATURE;
    }
    
    /**
     * Register all spawn configurations
     */
    public static void register() {
        registerAmbientSpawns();
        registerAquaticSpawns();
        registerMonsterSpawns();
        registerAnimalSpawns();
    }
} 