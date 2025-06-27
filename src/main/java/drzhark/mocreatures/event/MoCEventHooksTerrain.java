/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.event;

import drzhark.mocreatures.MoCTools;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.Biomes;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import drzhark.mocreatures.MoCConstants;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
public class MoCEventHooksTerrain {

    public static final Object2ObjectOpenHashMap<ResourceLocation, List<MobSpawnSettings.SpawnerData>> creatureSpawnMap = new Object2ObjectOpenHashMap<>();
    public static final Object2ObjectOpenHashMap<ResourceLocation, List<MobSpawnSettings.SpawnerData>> waterCreatureSpawnMap = new Object2ObjectOpenHashMap<>();

    // This is now called from the mod initialization
    public static void buildWorldGenSpawnLists() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            ResourceLocation biomeKey = ForgeRegistries.BIOMES.getKey(biome);
            if (biomeKey == null) continue;

            List<MobSpawnSettings.SpawnerData> creatureList = new ArrayList<>(
                biome.getMobSettings().getMobs(MobCategory.CREATURE).unwrap()
            );
            
            List<MobSpawnSettings.SpawnerData> waterList = new ArrayList<>(
                biome.getMobSettings().getMobs(MobCategory.WATER_CREATURE).unwrap()
            );

            creatureSpawnMap.put(biomeKey, creatureList);
            waterCreatureSpawnMap.put(biomeKey, waterList);
        }
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel)) return;

        ServerLevel level = (ServerLevel) event.getLevel();
        LevelChunk chunk = (LevelChunk) event.getChunk();
        BlockPos pos = new BlockPos(chunk.getPos().getMinBlockX() + 8, 0, chunk.getPos().getMinBlockZ() + 8);
        Biome biome = level.getBiome(pos).value();

        ResourceLocation biomeKey = ForgeRegistries.BIOMES.getKey(biome);
        if (biomeKey == null) return;

        RandomSource random = level.getRandom();

        List<MobSpawnSettings.SpawnerData> creatureList = creatureSpawnMap.get(biomeKey);
        List<MobSpawnSettings.SpawnerData> waterList = waterCreatureSpawnMap.get(biomeKey);

        if (creatureList != null) {
            MoCTools.performCustomWorldGenSpawning(level, biome, pos.getX(), pos.getZ(), 16, 16, random, 
                creatureList, SpawnPlacements.Type.ON_GROUND);
        }

        if (waterList != null) {
            MoCTools.performCustomWorldGenSpawning(level, biome, pos.getX(), pos.getZ(), 16, 16, random, 
                waterList, SpawnPlacements.Type.IN_WATER);
        }
    }
}