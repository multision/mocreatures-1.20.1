/*
 * GNU LESSER GENERAL PUBLIC LICENSE
 * Citadel: sbom_xela
 */
package drzhark.mocreatures.config.biome;

public enum BiomeEntryType {
    REGISTRY_NAME(false), BIOME_TAG(false), BIOME_DICT(true), BIOME_CATEGORY(true);

    private boolean depreciated;

    BiomeEntryType(boolean depreciated){
        this.depreciated = depreciated;
    }

    public boolean isDepreciated() {
        return depreciated;
    }
}