# Mo' Creatures JSON Spawn Configuration System

## Overview

Mo' Creatures now uses a comprehensive JSON-based spawn configuration system that allows users to easily modify creature spawn settings without needing to restart the server or deal with complex config files.

## Location

The main configuration file is located at:
```
config/MoCreatures/MoCreatures.json
```

## Configuration Structure

Each creature has the following configurable properties:

### Basic Properties
- **`enabled`** (true/false): Whether this creature can spawn at all
- **`weight`** (1-50): Higher numbers = more common spawns (vanilla mobs typically use 1-15)
- **`minCount`** (1+): Minimum number spawned per spawn attempt
- **`maxCount`** (1+): Maximum number spawned per spawn attempt
- **`category`**: Spawn category - one of:
  - `CREATURE` - Peaceful animals
  - `MONSTER` - Hostile mobs
  - `WATER_CREATURE` - Large aquatic creatures
  - `WATER_AMBIENT` - Small fish and water creatures
  - `AMBIENT` - Insects and ambient creatures

### Biome Configuration

The `biomes` section uses a flexible array-based system:

```json
"biomes": {
  "biomes": [
    [
      {"type": "BIOME_TAG", "negate": false, "value": "minecraft:is_forest"}
    ],
    [
      {"type": "BIOME_TAG", "negate": false, "value": "minecraft:is_jungle"}
    ],
    [
      {"type": "BIOME_TAG", "negate": false, "value": "forge:is_plains"}
    ]
  ]
}
```

#### Logic Rules:
- **OR Logic**: Each sub-array represents an OR condition
- **AND Logic**: Within each sub-array, all conditions must be true
- **Example**: `[[forest], [plains], [jungle]]` means spawn in forest OR plains OR jungle
- **Example**: `[[forest, hills]]` means spawn in biomes that are BOTH forest AND hills

#### Biome Entry Properties:
- **`type`**: 
  - `BIOME_TAG` - Modern biome tags (recommended)
  - `BIOME` - Specific biome names
- **`negate`**:
  - `false` - Must have this tag/be this biome
  - `true` - Must NOT have this tag/be this biome
- **`value`**: The biome tag or biome name

## Common Biome Tags

### Minecraft Tags:
- `minecraft:is_forest`
- `minecraft:is_jungle`
- `minecraft:is_ocean`
- `minecraft:is_river`
- `minecraft:is_beach`
- `minecraft:is_nether`
- `minecraft:is_end`
- `minecraft:is_badlands`
- `minecraft:is_savanna`

### Forge Tags:
- `forge:is_plains`
- `forge:is_coniferous`
- `forge:is_snowy`
- `forge:is_lush`
- `forge:is_steep`
- `forge:is_swamp`
- `forge:is_sandy`
- `forge:is_mountain`
- `forge:is_hill`
- `forge:is_dead`
- `forge:is_spooky`
- `forge:is_hot`
- `forge:is_dry`
- `forge:is_dense`
- `forge:is_water`

### Mo' Creatures Tags:
- `mocreatures:is_wyvern_lair`

## Example Configurations

### Simple Single Biome
```json
"polar_bear": {
  "enabled": true,
  "weight": 8,
  "minCount": 1,
  "maxCount": 2,
  "category": "CREATURE",
  "biomes": {
    "biomes": [
      [
        {"type": "BIOME_TAG", "negate": false, "value": "forge:is_snowy"}
      ]
    ]
  }
}
```

### Multiple OR Conditions
```json
"bird": {
  "enabled": true,
  "weight": 16,
  "minCount": 2,
  "maxCount": 4,
  "category": "CREATURE",
  "biomes": {
    "biomes": [
      [{"type": "BIOME_TAG", "negate": false, "value": "minecraft:is_forest"}],
      [{"type": "BIOME_TAG", "negate": false, "value": "minecraft:is_jungle"}],
      [{"type": "BIOME_TAG", "negate": false, "value": "forge:is_plains"}]
    ]
  }
}
```

### Complex AND/OR Logic
```json
"black_bear": {
  "enabled": true,
  "weight": 8,
  "minCount": 1,
  "maxCount": 2,
  "category": "CREATURE",
  "biomes": {
    "biomes": [
      [
        {"type": "BIOME_TAG", "negate": false, "value": "minecraft:is_forest"},
        {"type": "BIOME_TAG", "negate": false, "value": "forge:is_coniferous"}
      ]
    ]
  }
}
```
This spawns in biomes that are BOTH forest AND coniferous.

### Exclusion Example
```json
"desert_creature": {
  "enabled": true,
  "weight": 10,
  "minCount": 1,
  "maxCount": 3,
  "category": "CREATURE",
  "biomes": {
    "biomes": [
      [
        {"type": "BIOME_TAG", "negate": false, "value": "forge:is_sandy"},
        {"type": "BIOME_TAG", "negate": true, "value": "minecraft:is_ocean"}
      ]
    ]
  }
}
```
This spawns in sandy biomes that are NOT ocean biomes.

## Commands

### Reload Configuration
```
/mocreloadspawns
```
Reloads the spawn configuration without restarting the server (requires OP level 2).

## Tips

1. **Backup**: Always backup your configuration before making major changes
2. **Testing**: Use creative mode and biome-finding tools to test spawn changes
3. **Weight Guidelines**: 
   - 1-5: Very rare
   - 6-10: Rare
   - 11-15: Common (vanilla level)
   - 16-25: Very common
   - 26+: Extremely common
4. **Performance**: Very high spawn weights can impact server performance
5. **Validation**: Invalid JSON will prevent the config from loading - use a JSON validator if needed

## Migration from Old System

The new system automatically creates a default configuration file on first run. Due to the complexity of mob spawning in 1.20.1, MoCreatures.cfg will no longer be useable.