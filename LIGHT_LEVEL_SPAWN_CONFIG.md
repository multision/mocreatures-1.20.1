# Mo' Creatures Light Level Spawn Controls

## Overview

Mo' Creatures now supports light level controls in the JSON spawn configuration system. This allows you to specify minimum and maximum light levels for creature spawning, giving you fine-grained control over when and where creatures can spawn.

## Light Level Properties

Each creature in the `MoCreatures.json` configuration file can now include these additional properties:

### `minLightLevel` (optional)
- **Type**: Integer
- **Range**: 0-15 or -1 (no minimum)
- **Default**: -1 (no minimum)
- **Description**: Minimum light level required for spawning

### `maxLightLevel` (optional)
- **Type**: Integer  
- **Range**: 0-15 or -1 (no maximum)
- **Default**: -1 (no maximum)
- **Description**: Maximum light level allowed for spawning

### `useSkyLight` (optional)
- **Type**: Boolean
- **Default**: false
- **Description**: 
  - `false` = Use block light (torches, glowstone, etc.)
  - `true` = Use sky light (sunlight, moonlight)

## Light Level Values

Light levels in Minecraft range from 0 to 15:
- **0**: Complete darkness
- **15**: Maximum brightness
- **-1**: No restriction (default)

## Examples

### Nocturnal Creatures (Spawn in Darkness)
```json
"wraith": {
  "enabled": true,
  "weight": 6,
  "minCount": 1,
  "maxCount": 4,
  "category": "MONSTER",
  "minLightLevel": 0,
  "maxLightLevel": 7,
  "useSkyLight": false,
  "biomes": {
    "biomes": [
      [
        {"type": "BIOME_TAG", "negate": false, "value": "minecraft:is_forest"}
      ]
    ]
  }
}
```

### Daytime Creatures (Spawn in Light)
```json
"bird": {
  "enabled": true,
  "weight": 16,
  "minCount": 2,
  "maxCount": 4,
  "category": "CREATURE",
  "minLightLevel": 8,
  "maxLightLevel": 15,
  "useSkyLight": true,
  "biomes": {
    "biomes": [
      [{"type": "BIOME_TAG", "negate": false, "value": "minecraft:is_forest"}],
      [{"type": "BIOME_TAG", "negate": false, "value": "minecraft:is_jungle"}],
      [{"type": "BIOME_TAG", "negate": false, "value": "forge:is_plains"}]
    ]
  }
}
```

### Cave Creatures (Spawn Underground)
```json
"cave_scorpion": {
  "enabled": true,
  "weight": 4,
  "minCount": 1,
  "maxCount": 3,
  "category": "MONSTER",
  "minLightLevel": 0,
  "maxLightLevel": 3,
  "useSkyLight": false,
  "biomes": {
    "biomes": [
      [
        {"type": "BIOME_TAG", "negate": false, "value": "minecraft:is_forest"}
      ]
    ]
  }
}
```

### Fireflies (Spawn at Dusk/Dawn)
```json
"firefly": {
  "enabled": true,
  "weight": 9,
  "minCount": 1,
  "maxCount": 2,
  "category": "AMBIENT",
  "minLightLevel": 4,
  "maxLightLevel": 8,
  "useSkyLight": true,
  "biomes": {
    "biomes": [
      [
        {"type": "BIOME_TAG", "negate": false, "value": "minecraft:is_forest"}
      ]
    ]
  }
}
```

### Default Behavior (No Light Restrictions)
```json
"bunny": {
  "enabled": true,
  "weight": 12,
  "minCount": 2,
  "maxCount": 3,
  "category": "CREATURE",
  "biomes": {
    "biomes": [
      [
        {"type": "BIOME_TAG", "negate": false, "value": "forge:is_plains"}
      ]
    ]
  }
}
```

## Common Use Cases

### 1. **Nocturnal Monsters**
- Set `minLightLevel: 0, maxLightLevel: 7`
- Use `useSkyLight: false` for block light
- Examples: Wraiths, werewolves, some scorpions

### 2. **Daytime Animals**
- Set `minLightLevel: 8, maxLightLevel: 15`
- Use `useSkyLight: true` for natural light
- Examples: Birds, butterflies, bees

### 3. **Cave Dwellers**
- Set `minLightLevel: 0, maxLightLevel: 3`
- Use `useSkyLight: false` for block light
- Examples: Cave scorpions, cave ogres

### 4. **Dusk/Dawn Creatures**
- Set `minLightLevel: 4, maxLightLevel: 8`
- Use `useSkyLight: true` for natural light
- Examples: Fireflies, some insects

### 5. **Underground Only**
- Set `minLightLevel: 0, maxLightLevel: 0`
- Use `useSkyLight: false`
- Examples: Deep cave creatures

## Tips

1. **Block Light vs Sky Light**: 
   - Use `useSkyLight: false` for creatures that spawn near light sources (torches, glowstone)
   - Use `useSkyLight: true` for creatures that depend on natural light cycles

2. **Light Level Ranges**:
   - 0-3: Very dark (caves, night)
   - 4-7: Low light (dusk, dawn, dim areas)
   - 8-11: Moderate light (daytime, well-lit areas)
   - 12-15: Bright light (full daylight, very bright areas)

3. **Testing**: Use the `/mocdebug spawn` command to check light levels at your current position

4. **Performance**: Light level checks are performed during spawn attempts, so very restrictive settings may slightly impact performance

## Migration

Existing configurations will continue to work without modification. Light level properties are optional and default to no restrictions (-1).

To add light level controls to existing creatures, simply add the desired properties to their configuration entries. 