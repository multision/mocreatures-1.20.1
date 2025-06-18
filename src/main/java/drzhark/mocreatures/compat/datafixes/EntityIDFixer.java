/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat.datafixes;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

public class EntityIDFixer {

    public EntityIDFixer() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Called manually when loading legacy entities to remap old IDs.
     */
    public CompoundTag fixTagCompound(CompoundTag compound) {
        String entityId = compound.getString("id");
        if (entityId.equals(MoCConstants.MOD_PREFIX + "scorpion")) {
            int entityType = compound.getInt("TypeInt");
            switch (entityType) {
                case 2:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "cavescorpion");
                    break;
                case 3:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "firescorpion");
                    break;
                case 4:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "frostscorpion");
                    break;
                default:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "dirtscorpion");
            }
            compound.putInt("TypeInt", 1);
        }

        if (entityId.equals(MoCConstants.MOD_PREFIX + "manticore")) {
            int entityType = compound.getInt("TypeInt");
            switch (entityType) {
                case 2:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "darkmanticore");
                    break;
                case 3:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "frostmanticore");
                    break;
                case 4:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "toxicmanticore");
                    break;
                default:
                    compound.putString("id", MoCConstants.MOD_PREFIX + "firemanticore");
            }
            compound.putInt("TypeInt", 1);
        }

        return compound;
    }

    @SuppressWarnings("removal")
    @SubscribeEvent
    public void onMissingEntityMappings(MissingMappingsEvent event) {
        ResourceLocation scorpion = new ResourceLocation(MoCConstants.MOD_ID, "scorpion");
        ResourceLocation manticore = new ResourceLocation(MoCConstants.MOD_ID, "manticore");

        event.getMappings(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), MoCConstants.MOD_ID).forEach(mapping -> {
            if (mapping.getKey().equals(scorpion) || mapping.getKey().equals(manticore)) {
                mapping.ignore(); // Ignore missing legacy IDs
            }
        });
    }
}
