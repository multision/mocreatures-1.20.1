/*
 * GNU LESSER GENERAL PUBLIC LICENSE
 * Citadel: sbom_xela
 */
package drzhark.mocreatures.config.biome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BiomeSpawnConfig {
    
    public static class CreatureSpawnData {
        public boolean enabled = true;
        public int weight = 10;
        public int minCount = 1;
        public int maxCount = 3;
        public String category = "CREATURE"; // CREATURE, MONSTER, WATER_CREATURE, WATER_AMBIENT, AMBIENT
        public SpawnBiomeData biomes = new SpawnBiomeData();
        
        public CreatureSpawnData() {}
        
        public CreatureSpawnData(boolean enabled, int weight, int minCount, int maxCount, String category, SpawnBiomeData biomes) {
            this.enabled = enabled;
            this.weight = weight;
            this.minCount = minCount;
            this.maxCount = maxCount;
            this.category = category;
            this.biomes = biomes;
        }
    }
    
    private static final Map<String, CreatureSpawnData> spawnConfig = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static File configFile;
    private static boolean initialized = false;
    
    public static void init() {
        if (initialized) return;
        
        try {
            // Create config directory
            Path configDir = FMLPaths.CONFIGDIR.get().resolve("MoCreatures");
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }
            
            configFile = configDir.resolve("MoCreatures.json").toFile();
            
            // Load existing config or create default
            if (configFile.exists()) {
                loadConfig();
            } else {
                createDefaultConfig();
                saveConfig();
            }
            
            initialized = true;
            MoCreatures.LOGGER.info("BiomeSpawnConfig initialized with {} creatures", spawnConfig.size());
            
        } catch (Exception e) {
            MoCreatures.LOGGER.error("Failed to initialize BiomeSpawnConfig", e);
        }
    }
    
    private static void createDefaultConfig() {
        // Bears
        spawnConfig.put("black_bear", new CreatureSpawnData(true, MoCConfig.blackBearSpawnWeight, 1, 3, "CREATURE", DefaultBiomes.BLACK_BEAR));
        spawnConfig.put("grizzly_bear", new CreatureSpawnData(true, MoCConfig.grizzlyBearSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.GRIZZLY_BEAR));
        spawnConfig.put("polar_bear", new CreatureSpawnData(true, MoCConfig.polarBearSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.POLAR_BEAR));
        spawnConfig.put("panda_bear", new CreatureSpawnData(true, MoCConfig.pandaBearSpawnWeight, 1, 3, "CREATURE", DefaultBiomes.PANDA_BEAR));
        
        // Birds
        spawnConfig.put("bird", new CreatureSpawnData(true, MoCConfig.birdSpawnWeight, 2, 3, "CREATURE", DefaultBiomes.BIRD));
        spawnConfig.put("duck", new CreatureSpawnData(true, MoCConfig.duckSpawnWeight, 2, 4, "CREATURE", DefaultBiomes.DUCK));
        spawnConfig.put("turkey", new CreatureSpawnData(true, MoCConfig.turkeySpawnWeight, 1, 2, "CREATURE", DefaultBiomes.TURKEY));
        
        // Land animals
        spawnConfig.put("boar", new CreatureSpawnData(true, MoCConfig.boarSpawnWeight, 2, 2, "CREATURE", DefaultBiomes.BOAR));
        spawnConfig.put("bunny", new CreatureSpawnData(true, MoCConfig.bunnySpawnWeight, 2, 3, "CREATURE", DefaultBiomes.BUNNY));
        spawnConfig.put("deer", new CreatureSpawnData(true, MoCConfig.deerSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.DEER));
        spawnConfig.put("goat", new CreatureSpawnData(true, MoCConfig.goatSpawnWeight, 1, 3, "CREATURE", DefaultBiomes.GOAT));
        spawnConfig.put("kitty", new CreatureSpawnData(true, MoCConfig.kittySpawnWeight, 1, 2, "CREATURE", DefaultBiomes.KITTY));
        
        // Semi-aquatic creatures
        spawnConfig.put("crocodile", new CreatureSpawnData(true, MoCConfig.crocodileSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.MOC_CROCODILE));
        spawnConfig.put("turtle", new CreatureSpawnData(true, MoCConfig.turtleSpawnWeight, 1, 3, "CREATURE", DefaultBiomes.TURTLE));
        
        // Desert/savanna creatures
        spawnConfig.put("elephant", new CreatureSpawnData(true, MoCConfig.elephantSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.MOC_ELEPHANT));
        spawnConfig.put("filch_lizard", new CreatureSpawnData(true, MoCConfig.filchLizardSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.FILCH_LIZARD));
        spawnConfig.put("ostrich", new CreatureSpawnData(true, MoCConfig.ostrichSpawnWeight, 1, 1, "CREATURE", DefaultBiomes.OSTRICH));
        
        // Foxes
        spawnConfig.put("fox", new CreatureSpawnData(true, MoCConfig.foxSpawnWeight, 1, 1, "CREATURE", DefaultBiomes.MOC_FOX));
        
        // Reptiles
        spawnConfig.put("komodo_dragon", new CreatureSpawnData(true, MoCConfig.komodoDragonSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.MOC_KOMODO_DRAGON));
        spawnConfig.put("snake", new CreatureSpawnData(true, MoCConfig.snakeSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.SNAKE));
        
        // Big cats
        spawnConfig.put("leopard", new CreatureSpawnData(true, MoCConfig.leopardSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.LEOPARD));
        spawnConfig.put("lion", new CreatureSpawnData(true, MoCConfig.lionSpawnWeight, 1, 3, "CREATURE", DefaultBiomes.LION));
        spawnConfig.put("panther", new CreatureSpawnData(true, MoCConfig.pantherSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.PANTHER));
        spawnConfig.put("tiger", new CreatureSpawnData(true, MoCConfig.tigerSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.MOC_TIGER));
        
        // Big cat hybrids
        spawnConfig.put("liger", new CreatureSpawnData(false, MoCConfig.ligerSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.LIGER));
        spawnConfig.put("lither", new CreatureSpawnData(false, MoCConfig.litherSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.LITHER));
        spawnConfig.put("panthger", new CreatureSpawnData(false, MoCConfig.panthgerSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.PANTHGER));
        spawnConfig.put("panthard", new CreatureSpawnData(false, MoCConfig.panthardSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.PANTHARD));
        spawnConfig.put("leoger", new CreatureSpawnData(false, MoCConfig.leogerSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.LEOGER));
        
        // Small mammals
        spawnConfig.put("mouse", new CreatureSpawnData(true, MoCConfig.mouseSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.MOUSE));
        spawnConfig.put("mole", new CreatureSpawnData(true, MoCConfig.moleSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.MOLE));
        spawnConfig.put("raccoon", new CreatureSpawnData(true, MoCConfig.raccoonSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.MOC_RACCOON));
        
        // Horses
        spawnConfig.put("wild_horse", new CreatureSpawnData(true, MoCConfig.wildHorseSpawnWeight, 1, 4, "CREATURE", DefaultBiomes.WILD_HORSE));
        
        // Magical creatures
        spawnConfig.put("ent", new CreatureSpawnData(true, MoCConfig.entSpawnWeight, 1, 2, "CREATURE", DefaultBiomes.ENT));
        spawnConfig.put("wyvern", new CreatureSpawnData(true, MoCConfig.wyvernSpawnWeight, 1, 3, "CREATURE", DefaultBiomes.WYVERN));
        
        // Monsters
        spawnConfig.put("green_ogre", new CreatureSpawnData(true, MoCConfig.greenOgreSpawnWeight, 1, 2, "MONSTER", DefaultBiomes.GREEN_OGRE));
        spawnConfig.put("cave_ogre", new CreatureSpawnData(true, MoCConfig.caveOgreSpawnWeight, 1, 2, "MONSTER", DefaultBiomes.CAVE_OGRE));
        spawnConfig.put("fire_ogre", new CreatureSpawnData(true, MoCConfig.fireOgreSpawnWeight, 1, 2, "MONSTER", DefaultBiomes.FIRE_OGRE));
        
        // Scorpions
        spawnConfig.put("cave_scorpion", new CreatureSpawnData(true, MoCConfig.caveScorpionSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.CAVE_SCORPION));
        spawnConfig.put("dirt_scorpion", new CreatureSpawnData(true, MoCConfig.dirtScorpionSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.DIRT_SCORPION));
        spawnConfig.put("fire_scorpion", new CreatureSpawnData(true, MoCConfig.fireScorpionSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.FIRE_SCORPION));
        spawnConfig.put("frost_scorpion", new CreatureSpawnData(true, MoCConfig.frostScorpionSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.FROST_SCORPION));
        spawnConfig.put("undead_scorpion", new CreatureSpawnData(true, MoCConfig.undeadScorpionSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.UNDEAD_SCORPION));
        
        // Golems
        spawnConfig.put("big_golem", new CreatureSpawnData(true, MoCConfig.bigGolemSpawnWeight, 1, 1, "MONSTER", DefaultBiomes.BIG_GOLEM));
        spawnConfig.put("mini_golem", new CreatureSpawnData(true, MoCConfig.miniGolemSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.MINI_GOLEM));
        
        // Manticores
        spawnConfig.put("dark_manticore", new CreatureSpawnData(true, MoCConfig.darkManticoreSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.DARK_MANTICORE));
        spawnConfig.put("fire_manticore", new CreatureSpawnData(true, MoCConfig.fireManticoreSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.FIRE_MANTICORE));
        spawnConfig.put("frost_manticore", new CreatureSpawnData(true, MoCConfig.frostManticoreSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.FROST_MANTICORE));
        spawnConfig.put("plain_manticore", new CreatureSpawnData(true, MoCConfig.plainManticoreSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.PLAIN_MANTICORE));
        spawnConfig.put("toxic_manticore", new CreatureSpawnData(true, MoCConfig.toxicManticoreSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.TOXIC_MANTICORE));
        
        // Werewolves and variants
        spawnConfig.put("werewolf", new CreatureSpawnData(true, MoCConfig.werewolfSpawnWeight, 1, 4, "MONSTER", DefaultBiomes.WEREWOLF));
        spawnConfig.put("wwolf", new CreatureSpawnData(true, MoCConfig.wwolfSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.WWOLF));
        
        // Rats
        spawnConfig.put("rat", new CreatureSpawnData(true, MoCConfig.ratSpawnWeight, 1, 2, "MONSTER", DefaultBiomes.RAT));
        spawnConfig.put("hell_rat", new CreatureSpawnData(true, MoCConfig.hellRatSpawnWeight, 1, 4, "MONSTER", DefaultBiomes.HELL_RAT));
        
        // Undead
        spawnConfig.put("silver_skeleton", new CreatureSpawnData(true, MoCConfig.silverSkeletonSpawnWeight, 1, 4, "MONSTER", DefaultBiomes.SILVER_SKELETON));
        
        // Wraiths
        spawnConfig.put("wraith", new CreatureSpawnData(true, MoCConfig.wraithSpawnWeight, 1, 4, "MONSTER", DefaultBiomes.WRAITH));
        spawnConfig.put("flame_wraith", new CreatureSpawnData(true, MoCConfig.flameWraithSpawnWeight, 1, 2, "MONSTER", DefaultBiomes.FLAME_WRAITH));
        
        // Nether mounts
        spawnConfig.put("horse_mob", new CreatureSpawnData(true, MoCConfig.horseMobSpawnWeight, 1, 3, "MONSTER", DefaultBiomes.HORSE_MOB));
        
        // Aquatic creatures
        spawnConfig.put("dolphin", new CreatureSpawnData(true, MoCConfig.dolphinSpawnWeight, 2, 4, "WATER_CREATURE", DefaultBiomes.DOLPHIN));
        spawnConfig.put("shark", new CreatureSpawnData(true, MoCConfig.sharkSpawnWeight, 1, 2, "WATER_CREATURE", DefaultBiomes.SHARK));
        spawnConfig.put("manta_ray", new CreatureSpawnData(true, MoCConfig.mantaRaySpawnWeight, 1, 2, "WATER_CREATURE", DefaultBiomes.MANTA_RAY));
        spawnConfig.put("jellyfish", new CreatureSpawnData(true, MoCConfig.jellyfishSpawnWeight, 1, 4, "WATER_CREATURE", DefaultBiomes.JELLYFISH));
        
        // River/swamp creatures
        spawnConfig.put("bass", new CreatureSpawnData(true, MoCConfig.bassSpawnWeight, 1, 4, "WATER_CREATURE", DefaultBiomes.BASS));
        spawnConfig.put("sting_ray", new CreatureSpawnData(true, MoCConfig.stingRaySpawnWeight, 1, 2, "WATER_CREATURE", DefaultBiomes.STING_RAY));
        
        // Small fish
        spawnConfig.put("anchovy", new CreatureSpawnData(true, MoCConfig.anchovySpawnWeight, 1, 6, "WATER_CREATURE", DefaultBiomes.ANCHOVY));
        spawnConfig.put("angelfish", new CreatureSpawnData(true, MoCConfig.angelfishSpawnWeight, 1, 6, "WATER_CREATURE", DefaultBiomes.ANGELFISH));
        spawnConfig.put("angler", new CreatureSpawnData(true, MoCConfig.anglerSpawnWeight, 1, 6, "WATER_CREATURE", DefaultBiomes.ANGLER));
        spawnConfig.put("clownfish", new CreatureSpawnData(true, MoCConfig.clownfishSpawnWeight, 1, 6, "WATER_CREATURE", DefaultBiomes.CLOWNFISH));
        spawnConfig.put("goldfish", new CreatureSpawnData(true, MoCConfig.goldfishSpawnWeight, 1, 6, "WATER_CREATURE", DefaultBiomes.GOLDFISH));
        spawnConfig.put("hippotang", new CreatureSpawnData(true, MoCConfig.hippotangSpawnWeight, 1, 6, "WATER_CREATURE", DefaultBiomes.HIPPOTANG));
        spawnConfig.put("manderin", new CreatureSpawnData(true, MoCConfig.manderinSpawnWeight, 1, 6, "WATER_CREATURE", DefaultBiomes.MANDERIN));
        
        // Medium fish
        spawnConfig.put("cod", new CreatureSpawnData(true, MoCConfig.codSpawnWeight, 1, 4, "WATER_CREATURE", DefaultBiomes.COD));
        spawnConfig.put("salmon", new CreatureSpawnData(true, MoCConfig.salmonSpawnWeight, 1, 4, "WATER_CREATURE", DefaultBiomes.SALMON));
        
        // Predatory fish
        spawnConfig.put("piranha", new CreatureSpawnData(true, MoCConfig.piranhaSpawnWeight, 1, 3, "WATER_CREATURE", DefaultBiomes.PIRANHA));
        
        // General fish
        spawnConfig.put("fishy", new CreatureSpawnData(true, MoCConfig.fishySpawnWeight, 1, 6, "WATER_CREATURE", DefaultBiomes.FISHY));
        
        // Ambient creatures
        spawnConfig.put("ant", new CreatureSpawnData(true, MoCConfig.antSpawnWeight, 1, 4, "AMBIENT", DefaultBiomes.ANT));
        spawnConfig.put("bee", new CreatureSpawnData(true, MoCConfig.beeSpawnWeight, 1, 2, "AMBIENT", DefaultBiomes.BEE));
        spawnConfig.put("butterfly", new CreatureSpawnData(true, MoCConfig.butterflySpawnWeight, 1, 4, "AMBIENT", DefaultBiomes.BUTTERFLY));
        spawnConfig.put("cricket", new CreatureSpawnData(true, MoCConfig.cricketSpawnWeight, 1, 2, "AMBIENT", DefaultBiomes.CRICKET));
        spawnConfig.put("dragonfly", new CreatureSpawnData(true, MoCConfig.dragonflySpawnWeight, 1, 2, "AMBIENT", DefaultBiomes.DRAGONFLY));
        spawnConfig.put("firefly", new CreatureSpawnData(true, MoCConfig.fireflySpawnWeight, 1, 2, "AMBIENT", DefaultBiomes.FIREFLY));
        spawnConfig.put("fly", new CreatureSpawnData(true, MoCConfig.flySpawnWeight, 1, 2, "AMBIENT", DefaultBiomes.MOC_FLY));
        spawnConfig.put("grasshopper", new CreatureSpawnData(true, MoCConfig.grasshopperSpawnWeight, 1, 2, "AMBIENT", DefaultBiomes.GRASSHOPPER));
        spawnConfig.put("maggot", new CreatureSpawnData(true, MoCConfig.maggotSpawnWeight, 1, 2, "AMBIENT", DefaultBiomes.MAGGOT));
        spawnConfig.put("roach", new CreatureSpawnData(true, MoCConfig.roachSpawnWeight, 1, 2, "AMBIENT", DefaultBiomes.ROACH));
        
        // Beach/aquatic ambient
        spawnConfig.put("crab", new CreatureSpawnData(true, MoCConfig.crabSpawnWeight, 1, 2, "AMBIENT", DefaultBiomes.CRAB));
        spawnConfig.put("snail", new CreatureSpawnData(true, MoCConfig.snailSpawnWeight, 1, 2, "AMBIENT", DefaultBiomes.SNAIL));
    }
    
    private static void saveConfig() throws IOException {
        JsonObject root = new JsonObject();
        
        // Add header comment
        root.addProperty("_comment", "Mo Creatures Spawn Configuration - Edit spawn weights, counts, and biome conditions for all creatures");
        
        JsonObject creatures = new JsonObject();
        
        for (Map.Entry<String, CreatureSpawnData> entry : spawnConfig.entrySet()) {
            JsonObject creature = new JsonObject();
            CreatureSpawnData data = entry.getValue();
            
            creature.addProperty("enabled", data.enabled);
            creature.addProperty("weight", data.weight);
            creature.addProperty("minCount", data.minCount);
            creature.addProperty("maxCount", data.maxCount);
            creature.addProperty("category", data.category);
            
            // Convert SpawnBiomeData to JSON
            creature.add("biomes", spawnBiomeDataToJson(data.biomes));
            
            creatures.add(entry.getKey(), creature);
        }
        
        root.add("creatures", creatures);
        
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(root, writer);
        }
        
        MoCreatures.LOGGER.info("Saved creature spawn config to {}", configFile.getAbsolutePath());
    }
    
    private static void loadConfig() throws IOException {
        try (FileReader reader = new FileReader(configFile)) {
            JsonObject root = gson.fromJson(reader, JsonObject.class);
            JsonObject creatures = root.getAsJsonObject("creatures");
            
            if (creatures != null) {
                for (Map.Entry<String, JsonElement> entry : creatures.entrySet()) {
                    JsonObject creature = entry.getValue().getAsJsonObject();
                    
                    boolean enabled = creature.has("enabled") ? creature.get("enabled").getAsBoolean() : true;
                    int weight = creature.has("weight") ? creature.get("weight").getAsInt() : 10;
                    int minCount = creature.has("minCount") ? creature.get("minCount").getAsInt() : 1;
                    int maxCount = creature.has("maxCount") ? creature.get("maxCount").getAsInt() : 3;
                    String category = creature.has("category") ? creature.get("category").getAsString() : "CREATURE";
                    
                    SpawnBiomeData biomes = new SpawnBiomeData();
                    if (creature.has("biomes")) {
                        biomes = jsonToSpawnBiomeData(creature.getAsJsonObject("biomes"));
                    }
                    
                    spawnConfig.put(entry.getKey(), new CreatureSpawnData(enabled, weight, minCount, maxCount, category, biomes));
                }
            }
            
            MoCreatures.LOGGER.info("Loaded creature spawn config from {}", configFile.getAbsolutePath());
        }
    }
    
    private static JsonObject spawnBiomeDataToJson(SpawnBiomeData biomeData) {
        // For now, use the existing serializer from SpawnBiomeData
        // This will serialize the data in the format compatible with the existing system
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(SpawnBiomeData.class, new SpawnBiomeData.Deserializer())
            .create();
        
        JsonElement element = gson.toJsonTree(biomeData);
        return element.getAsJsonObject();
    }
    
    private static SpawnBiomeData jsonToSpawnBiomeData(JsonObject biomesObj) {
        // Use the existing deserializer from SpawnBiomeData
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(SpawnBiomeData.class, new SpawnBiomeData.Deserializer())
            .create();
        
        return gson.fromJson(biomesObj, SpawnBiomeData.class);
    }
    
    public static CreatureSpawnData getSpawnData(String creatureName) {
        if (!initialized) {
            init();
        }
        return spawnConfig.get(creatureName);
    }
    
    public static boolean testBiome(String creatureName, Holder<Biome> biome, ResourceLocation biomeName) {
        CreatureSpawnData data = getSpawnData(creatureName);
        return data != null && data.enabled && data.biomes.matches(biome, biomeName);
    }
    
    public static void reloadConfig() {
        try {
            loadConfig();
            MoCreatures.LOGGER.info("Reloaded creature spawn configuration");
        } catch (Exception e) {
            MoCreatures.LOGGER.error("Failed to reload spawn configuration", e);
        }
    }
    
    public static SpawnBiomeData create(ResourceLocation id, SpawnBiomeData data) {
        // This method maintains compatibility with the old system
        return data;
    }
} 