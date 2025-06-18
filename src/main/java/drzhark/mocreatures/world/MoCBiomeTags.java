package drzhark.mocreatures.world;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

/**
 * Defines custom biome tags for Mo' Creatures
 */
public class MoCBiomeTags {
    // Standard Minecraft Tags
    public static final TagKey<Biome> IS_OVERWORLD = BiomeTags.IS_OVERWORLD;
    public static final TagKey<Biome> IS_NETHER = BiomeTags.IS_NETHER;
    public static final TagKey<Biome> IS_END = BiomeTags.IS_END;
    
    // Vanilla biome types
    public static final TagKey<Biome> IS_JUNGLE = BiomeTags.IS_JUNGLE;
    public static final TagKey<Biome> IS_FOREST = BiomeTags.IS_FOREST;
    public static final TagKey<Biome> IS_TAIGA = BiomeTags.IS_TAIGA;
    public static final TagKey<Biome> IS_DESERT = TagKey.create(Registries.BIOME, new ResourceLocation("minecraft", "is_desert"));
    public static final TagKey<Biome> IS_OCEAN = BiomeTags.IS_OCEAN;
    public static final TagKey<Biome> IS_RIVER = BiomeTags.IS_RIVER;
    public static final TagKey<Biome> IS_BEACH = BiomeTags.IS_BEACH;
    public static final TagKey<Biome> IS_MOUNTAIN = BiomeTags.IS_MOUNTAIN;
    public static final TagKey<Biome> IS_BADLANDS = BiomeTags.IS_BADLANDS;
    public static final TagKey<Biome> IS_HILL = BiomeTags.IS_HILL;
    public static final TagKey<Biome> IS_SAVANNA = BiomeTags.IS_SAVANNA;
    public static final TagKey<Biome> IS_PLAINS = TagKey.create(Registries.BIOME, new ResourceLocation("minecraft", "is_plains"));
    public static final TagKey<Biome> IS_SNOWY = TagKey.create(Registries.BIOME, new ResourceLocation("minecraft", "is_snowy"));
    public static final TagKey<Biome> IS_SWAMP = TagKey.create(Registries.BIOME, new ResourceLocation("minecraft", "is_swamp"));
    public static final TagKey<Biome> IS_WATER = TagKey.create(Registries.BIOME, new ResourceLocation("forge", "is_water"));
    
    // Forge biome tags
    public static final TagKey<Biome> IS_HOT = createForgeTag("is_hot");
    public static final TagKey<Biome> IS_COLD = createForgeTag("is_cold");
    public static final TagKey<Biome> IS_WET = createForgeTag("is_wet");
    public static final TagKey<Biome> IS_DRY = createForgeTag("is_dry");
    public static final TagKey<Biome> IS_SPARSE = createForgeTag("is_sparse");
    public static final TagKey<Biome> IS_DENSE = createForgeTag("is_dense");
    public static final TagKey<Biome> IS_LUSH = createForgeTag("is_lush");
    public static final TagKey<Biome> IS_DEAD = createForgeTag("is_dead");
    public static final TagKey<Biome> IS_SPOOKY = createForgeTag("is_spooky");
    public static final TagKey<Biome> IS_STEEP = createForgeTag("is_steep");
    public static final TagKey<Biome> IS_PLATEAU = createForgeTag("is_plateau");
    public static final TagKey<Biome> IS_SANDY = createForgeTag("is_sandy");
    public static final TagKey<Biome> IS_CONIFEROUS = createForgeTag("is_coniferous");
    public static final TagKey<Biome> IS_MESA = BiomeTags.IS_BADLANDS; // For backward compatibility
    
    // Mo' Creatures biome tags
    public static final TagKey<Biome> IS_WYVERN_LAIR = createMoCTag("is_wyvern_lair");
    
    // Village biome tags
    public static final TagKey<Biome> IS_VILLAGE = TagKey.create(Registries.BIOME, new ResourceLocation("mocreatures", "is_village"));
    
    /**
     * Create a tag key for a Forge biome tag
     */
    private static TagKey<Biome> createForgeTag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation("forge", name));
    }
    
    /**
     * Create a tag key for a Mo' Creatures biome tag
     */
    private static TagKey<Biome> createMoCTag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(MoCConstants.MOD_ID, name));
    }
} 