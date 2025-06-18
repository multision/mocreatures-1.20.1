/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.proxy;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.config.MoCConfigCategory;
import drzhark.mocreatures.config.MoCConfiguration;
import drzhark.mocreatures.config.MoCProperty;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.entity.hostile.MoCEntityGolem;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.tags.TagKey;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("removal")
public class MoCProxy {

    protected static final String CATEGORY_MOC_GENERAL_SETTINGS = "global-settings";
    protected static final String CATEGORY_MOC_CREATURE_GENERAL_SETTINGS = "creature-general-settings";
    protected static final String CATEGORY_MOC_MONSTER_GENERAL_SETTINGS = "monster-general-settings";
    protected static final String CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS = "water-mob-general-settings";
    protected static final String CATEGORY_MOC_AMBIENT_GENERAL_SETTINGS = "ambient-general-settings";
    protected static final String CATEGORY_MOC_ID_SETTINGS = "custom-id-settings";
    private static final String CATEGORY_OWNERSHIP_SETTINGS = "ownership-settings";
    public static String ARMOR_TEXTURE = "textures/armor/";
    public static String BLOCK_TEXTURE = "textures/blocks/";
    public static String ITEM_TEXTURE = "textures/items/";
    public static String MODEL_TEXTURE = "textures/models/";
    public static String GUI_TEXTURE = "textures/gui/";
    public static String MISC_TEXTURE = "textures/misc/";

    // CONFIG VARIABLES
    public boolean allowInstaSpawn;
    public boolean alphaWraithEyes;
    public boolean alwaysNamePets;
    public boolean animateTextures;
    public boolean armorSetEffects;
    public boolean attackHorses;
    public boolean attackWolves;
    public boolean debug;
    public boolean destroyDrops;
    public boolean displayPetHealth;
    public boolean displayPetIcons;
    public boolean displayPetName;
    public boolean easterEggs;
    public boolean easyHorseBreeding;
    public boolean elephantBulldozer;
    public boolean enableHunters;
    public boolean enableOwnership;
    public boolean enableResetOwnership;
    public boolean foggyWyvernLair;
    public boolean golemDestroyBlocks;
    public boolean legacyBigCatModels;
    public boolean legacyBunnyTextures;
    public boolean legacyRatDeathSound;
    public boolean legacyWerehumanSounds;
    public boolean legacyWraithSounds;
    public boolean legacyWyvernLairSky;
    public boolean staticBed;
    public boolean staticLitter;
    public boolean verboseEntityNames;
    public boolean weaponEffects;
    public boolean worldInitDone;
    public double spawnMultiplier;
    public float ogreCaveStrength;
    public float ogreFireStrength;
    public float ogreStrength;
    public int filchLizardSpawnItemChance;
    public int kittyVillageChance;
    public int maxOPTamed;
    public int maxTamed;
    public int motherWyvernEggDropChance;
    public int ostrichEggDropChance;
    public int particleFX;
    public int rareItemDropChance;
    public ResourceKey<Level> wyvernDimension;
    public int wyvernEggDropChance;
    public short ogreAttackRange;

    public MoCConfiguration mocSettingsConfig;
    public MoCConfiguration mocEntityConfig;


    public void resetAllData() {
        this.readGlobalConfigValues();
    }

    //----------------CONFIG INITIALIZATION
    public void configInit() {
        this.mocSettingsConfig = new MoCConfiguration(new File(FMLPaths.CONFIGDIR.get().toString(), "MoCreatures" + File.separator + "MoCSettings.cfg"));
        this.mocEntityConfig = new MoCConfiguration(new File(FMLPaths.CONFIGDIR.get().toString(), "MoCreatures" + File.separator + "MoCreatures.cfg"));
        this.mocSettingsConfig.load();
        this.mocEntityConfig.load();
        this.readGlobalConfigValues();
        if (this.debug) {
            MoCreatures.LOGGER.info("Initializing MoCreatures Config File at " + FMLPaths.CONFIGDIR.get().toString() + "MoCSettings.cfg");
        }
    }

    //-----------------THE FOLLOWING ARE CLIENT SIDE ONLY, NOT TO BE USED IN SERVER AS THEY AFFECT ONLY DISPLAY / SOUNDS

    public void UndeadFX(Entity entity) {
    }

    public void StarFX(MoCEntityHorse moCEntityHorse) {
    }

    public void LavaFX(Entity entity) {
    }

    public void VanishFX(MoCEntityHorse entity) {
    }

    public void MaterializeFX(MoCEntityHorse entity) {
    }

    public void VacuumFX(MoCEntityGolem entity) {
    }

    public void hammerFX(Player entityplayer) {
    }

    public void teleportFX(Player entity) {
    }

    public boolean getAnimateTextures() {
        return false;
    }

    public boolean getDisplayPetName() {
        return this.displayPetName;
    }

    public boolean getDisplayPetIcons() {
        return this.displayPetIcons;
    }

    public boolean getDisplayPetHealth() {
        return this.displayPetHealth;
    }

    public int getParticleFX() {
        return 0;
    }

    public ResourceLocation getArmorTexture(String texture) {
        return null;
    }

    public ResourceLocation getBlockTexture(String texture) {
        return null;
    }

    public ResourceLocation getItemTexture(String texture) {
        return null;
    }

    public ResourceLocation getModelTexture(String texture) {
        return null;
    }

    public ResourceLocation getGuiTexture(String texture) {
        return null;
    }

    public ResourceLocation getMiscTexture(String texture) {
        return null;
    }

    public Player getPlayer() {
        return null;
    }

    // Client side only
    public void printMessageToPlayer(String msg) {
    }

    public List<TagKey<Biome>> parseBiomeTypes(String[] biomeNames) {
        List<TagKey<Biome>> biomeTypes = new ArrayList<>();
        for (String biomeName : biomeNames) {
            try {
                TagKey<Biome> biomeTag = TagKey.create(Registries.BIOME, new ResourceLocation(biomeName));
                biomeTypes.add(biomeTag);
            } catch (Exception e) {
                MoCreatures.LOGGER.error("Error parsing biome tag: " + biomeName, e);
            }
        }
        return biomeTypes;
    }

    public void readMocConfigValues() {
        if (MoCreatures.mocEntityMap != null && !MoCreatures.mocEntityMap.isEmpty()) {
            for (MoCEntityData entityData : MoCreatures.mocEntityMap.values()) {
                MoCConfigCategory cat = this.mocEntityConfig.getCategory(entityData.getEntityName().toLowerCase());
                if (!cat.containsKey("biomeTypes")) {
                    cat.put("biomeTypes", new MoCProperty("biomeTypes", Arrays.toString(entityData.getBiomeTags().toArray()), MoCProperty.Type.STRING));
                } else {
                    entityData.setBiomeTags(parseBiomeTypes(cat.get("biomeTypes").value.replaceAll(" ", "").replaceAll("\\[", "").replaceAll("]", "").split(",")));
                }
                if (!cat.containsKey("blockedBiomeTypes")) {
                    cat.put("blockedBiomeTypes", new MoCProperty("blockedBiomeTypes", Arrays.toString(entityData.getBlockedBiomeTags().toArray()), MoCProperty.Type.STRING));
                } else {
                    entityData.setBlockedBiomeTags(parseBiomeTypes(cat.get("blockedBiomeTypes").value.replaceAll(" ", "").replaceAll("\\[", "").replaceAll("]", "").split(",")));
                }
                if (!cat.containsKey("canSpawn")) {
                    cat.put("canSpawn", new MoCProperty("canSpawn", Boolean.toString(entityData.getCanSpawn()), MoCProperty.Type.BOOLEAN));
                } else {
                    entityData.setCanSpawn(Boolean.parseBoolean(cat.get("canSpawn").value));
                }
                if (!cat.containsKey("frequency")) {
                    cat.put("frequency", new MoCProperty("frequency", Integer.toString(entityData.getFrequency()), MoCProperty.Type.INTEGER));
                } else {
                    entityData.setFrequency(Integer.parseInt(cat.get("frequency").value));
                }
                if (!cat.containsKey("maxSpawn")) {
                    cat.put("maxSpawn", new MoCProperty("maxSpawn", Integer.toString(entityData.getMaxSpawn()), MoCProperty.Type.INTEGER));
                } else {
                    entityData.setMaxSpawn(Integer.parseInt(cat.get("maxSpawn").value));
                }
                if (!cat.containsKey("minSpawn")) {
                    cat.put("minSpawn", new MoCProperty("minSpawn", Integer.toString(entityData.getMinSpawn()), MoCProperty.Type.INTEGER));
                } else {
                    entityData.setMinSpawn(Integer.parseInt(cat.get("minSpawn").value));
                }
            }
        }
        this.mocEntityConfig.save();
    }

    /**
     * Reads values from file
     */
    public void readGlobalConfigValues() {
        // Client side only
        this.animateTextures = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "AnimateTextures", true, "Enables animated textures.").getBoolean(true);
        this.displayPetHealth = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "DisplayPetHealth", true, "Shows the health of pets.").getBoolean(true);
        this.displayPetIcons = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "DisplayPetIcons", true, "Shows the emotes of pets.").getBoolean(true);
        this.displayPetName = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "DisplayPetName", true, "Shows the name of pets.").getBoolean(true);

        // General
        this.alphaWraithEyes = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AlphaWraithEyes", false, "Enables different eye colors for wraiths and flame wraiths like in alpha versions.").getBoolean(false);
        this.alwaysNamePets = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "AlwaysNamePets", true, "Displays a GUI to name a pet when taming.").getBoolean(true);
        this.armorSetEffects = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "ArmorSetEffects", true, "Applies potion effects when wearing full scorpion armor sets.").getBoolean(true);
        this.attackHorses = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackHorses", false, "When enabled, tamed horses can be attacked.").getBoolean(false);
        this.attackWolves = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackWolves", false, "When enabled, tamed wolves can be attacked.").getBoolean(false);
        this.debug = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "DebugMode", false, "When enabled, enables debugging logs.").getBoolean(false);
        this.destroyDrops = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "DestroyDrops", true, "When enabled, if tamed entities find drops, they will destroy them.").getBoolean(true);
        this.easterEggs = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "EasterEggs", false, "Spawns certain entities on Easter.").getBoolean(false);
        this.easyHorseBreeding = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "EasyHorseBreeding", false, "Makes breeding horses easier with one click.").getBoolean(false);
        this.elephantBulldozer = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ElephantBulldozer", true, "When enabled, elephants will destroy all non-solid blocks.").getBoolean(true);
        this.enableHunters = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "EnableHunters", true, "When disabled, hunters will not attack your pets.").getBoolean(true);
        this.enableOwnership = this.mocSettingsConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "EnableOwnership", true, "Enables ownership for other players.").getBoolean(true);
        this.enableResetOwnership = this.mocSettingsConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "EnableResetOwnership", true, "Reset ownership when loading animal from chunk.").getBoolean(true);
        this.filchLizardSpawnItemChance = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "FilchLizardSpawnItemChance", 50, "Percentage chance of a filch lizard to steal a dropped item.").getInt(50);
        this.foggyWyvernLair = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "FoggyWyvernLair", true, "Enables fog in the Wyvern Lair dimension.").getBoolean(true);
        this.golemDestroyBlocks = this.mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "GolemDestroyBlocks", true, "When enabled, golems will destroy blocks.").getBoolean(true);
        this.kittyVillageChance = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "KittyVillageChance", 25, "Percentage chance of a kitty to spawn in a village.").getInt(25);
        this.legacyBigCatModels = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "LegacyBigCatModels", false, "Use the old big cat model when true.").getBoolean(false);
        this.legacyBunnyTextures = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "LegacyBunnyTextures", false, "Use the old bunny textures when true.").getBoolean(false);
        this.legacyRatDeathSound = this.mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "LegacyRatDeathSound", false, "Use the old rat death sound when true.").getBoolean(false);
        this.legacyWerehumanSounds = this.mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "LegacyWerehumanSounds", false, "Use the old werehuman sounds when true.").getBoolean(false);
        this.legacyWraithSounds = this.mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "LegacyWraithSounds", false, "Use the old wraith sounds when true.").getBoolean(false);
        this.legacyWyvernLairSky = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "LegacyWyvernLairSky", false, "Use the old wyvern lair sky when true.").getBoolean(false);
        this.maxOPTamed = this.mocSettingsConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "MaxOPTamed", 15, "Max amount of pets an OP player can own.").getInt(15);
        this.maxTamed = this.mocSettingsConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "MaxTamed", 10, "Max amount of pets a player can own.").getInt(10);
        this.motherWyvernEggDropChance = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "MotherWyvernEggDropChance", 10, "Percentage chance of a mother wyvern to drop an egg when killed.").getInt(10);
        this.ogreAttackRange = (short) this.mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreAttackRange", 12, "Maximum block distance for ogres to destroy.").getInt(12);
        this.ogreCaveStrength = (float) this.mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreCaveStrength", 2.5D, "Strength value for cave ogres.").getDouble(2.5D);
        this.ogreFireStrength = (float) this.mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreFireStrength", 3.0D, "Strength value for fire ogres.").getDouble(3.0D);
        this.ogreStrength = (float) this.mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreStrength", 2.0D, "Strength value for ogres.").getDouble(2.0D);
        this.ostrichEggDropChance = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "OstrichEggDropChance", 10, "Percentage chance of a ostrich to drop an egg when killed.").getInt(10);
        this.particleFX = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "ParticleFX", 3, "Particle FX. 0 = off, 1 = minimal, 2 = normal, 3 = maximal.").getInt(3);
        this.rareItemDropChance = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "RareItemDropChance", 25, "Percentage chance of dropping a rare item.").getInt(25);
        this.spawnMultiplier = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "SpawnMultiplier", 1.0D, "Multiplier for spawn frequency.").getDouble(1.0D);
        this.staticBed = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticBed", false, "When enabled, kitty beds cannot be pushed.").getBoolean(false);
        this.staticLitter = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticLitter", false, "When enabled, litter boxes cannot be pushed.").getBoolean(false);
        this.verboseEntityNames = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "VerboseEntityNames", false, "Enables displaying a verbose name of type on pets.").getBoolean(false);
        this.weaponEffects = this.mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "WeaponEffects", true, "Applies weapon effects when attacking with silver weapons.").getBoolean(true);
        this.wyvernEggDropChance = this.mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "WyvernEggDropChance", 5, "Percentage chance of a wyvern to drop an egg when killed.").getInt(5);
        this.wyvernDimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("mocreatures:wyvernlair"));

        // Custom ID settings
        this.allowInstaSpawn = this.mocSettingsConfig.get(CATEGORY_MOC_ID_SETTINGS, "AllowInstaSpawn", false, "Used for debugging purposes.").getBoolean(false);

        if (this.debug) {
            MoCreatures.LOGGER.info("Settings loaded.");
        }
        this.mocSettingsConfig.save();
    }

    public void registerRenderers() {
    }

    public void registerRenderInformation() {
    }

    public int getProxyMode() {
        return 0;
    }

    public void setName(Player player, IMoCEntity mocanimal) {
    }
}
