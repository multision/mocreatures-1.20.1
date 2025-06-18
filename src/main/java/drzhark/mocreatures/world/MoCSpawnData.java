package drzhark.mocreatures.world;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds data about entity spawning for Mo' Creatures
 */
public class MoCSpawnData {
    // Custom biome tags
    public static final TagKey<Biome> STEEP = TagKey.create(Registries.BIOME, new ResourceLocation("forge", "is_steep"));
    public static final TagKey<Biome> WYVERN_LAIR = TagKey.create(Registries.BIOME, new ResourceLocation("mocreatures", "is_wyvern_lair"));
    
    private final String entityName;
    private final int frequency;
    private final MobCategory category;
    private final EntityType<?> entityType;
    private final int weight;
    private final int minCount;
    private final int maxCount;
    private final List<TagKey<Biome>> includeBiomeTags = new ArrayList<>();
    private final List<TagKey<Biome>> excludeBiomeTags = new ArrayList<>();

    /**
     * Create a new spawn data entry
     */
    public MoCSpawnData(String entityName, int frequency, MobCategory category, EntityType<?> entityType, int weight, int minCount, int maxCount) {
        this.entityName = entityName;
        this.frequency = frequency;
        this.category = category;
        this.entityType = entityType;
        this.weight = weight;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    /**
     * Add biome tags where this entity should spawn
     */
    public MoCSpawnData addBiomeTags(TagKey<Biome>... tags) {
        includeBiomeTags.addAll(Arrays.asList(tags));
        return this;
    }

    /**
     * Add biome tags where this entity should NOT spawn
     */
    public MoCSpawnData excludeBiomeTags(TagKey<Biome>... tags) {
        excludeBiomeTags.addAll(Arrays.asList(tags));
        return this;
    }

    /**
     * Check if this entity can spawn in a biome
     */
    public boolean canSpawnIn(Holder<Biome> biome) {
        // Skip if spawning is disabled
        if (frequency <= 0) {
            return false;
        }

        // Check if biome is in any excluded tags
        for (TagKey<Biome> tag : excludeBiomeTags) {
            if (biome.is(tag)) {
                return false;
            }
        }

        // Check if biome is in any included tags
        for (TagKey<Biome> tag : includeBiomeTags) {
            if (biome.is(tag)) {
                return true;
            }
        }

        // Not in any included tags
        return includeBiomeTags.isEmpty();
    }

    /**
     * Get a spawner data object for this entity
     */
    public MobSpawnSettings.SpawnerData toSpawnerData() {
        return new MobSpawnSettings.SpawnerData(entityType, calculateWeight(), minCount, maxCount);
    }

    /**
     * Calculate the actual spawn weight based on frequency
     */
    private int calculateWeight() {
        return Math.max(1, (int)(weight * (frequency / 10.0)));
    }

    // Getters
    public String getEntityName() {
        return entityName;
    }

    public int getFrequency() {
        return frequency;
    }

    public MobCategory getCategory() {
        return category;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public int getWeight() {
        return weight;
    }

    public int getMinCount() {
        return minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public List<TagKey<Biome>> getIncludeBiomeTags() {
        return includeBiomeTags;
    }

    public List<TagKey<Biome>> getExcludeBiomeTags() {
        return excludeBiomeTags;
    }
} 