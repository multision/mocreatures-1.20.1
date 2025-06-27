package drzhark.mocreatures.world;

import com.mojang.serialization.Codec;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoCSpawnBiomeModifier implements BiomeModifier {
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(new ResourceLocation(MoCConstants.MOD_ID, "moc_spawns"), ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MoCConstants.MOD_ID);

    public MoCSpawnBiomeModifier() {
    }

    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            String biomeName = biome.unwrap().map(key -> key.location().toString(), obj -> "unknown");
            MoCreatures.LOGGER.info("MoCSpawnBiomeModifier: Processing biome {} in phase {}", biomeName, phase);
            MoCWorldRegistry.addBiomeSpawns(biome, builder);
        }
    }

    public Codec<? extends BiomeModifier> codec() {
        return (Codec)SERIALIZER.get();
    }

    public static Codec<MoCSpawnBiomeModifier> makeCodec() {
        MoCreatures.LOGGER.info("Making MoCSpawnBiomeModifier codec");
        return Codec.unit(MoCSpawnBiomeModifier::new);
    }
}