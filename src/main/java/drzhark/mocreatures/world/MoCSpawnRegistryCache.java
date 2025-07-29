package drzhark.mocreatures.world;

import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.world.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class MoCSpawnRegistryCache {
    public static final Map<String, EntityType<?>> ENTITIES = new HashMap<>();

    public static void prepare() {
        ENTITIES.put("black_bear", MoCEntities.BLACK_BEAR.get());
        ENTITIES.put("grizzly_bear", MoCEntities.GRIZZLY_BEAR.get());
        ENTITIES.put("polar_bear", MoCEntities.POLAR_BEAR.get());
        ENTITIES.put("panda_bear", MoCEntities.PANDA_BEAR.get());

        ENTITIES.put("bird", MoCEntities.BIRD.get());
        ENTITIES.put("duck", MoCEntities.DUCK.get());
        ENTITIES.put("turkey", MoCEntities.TURKEY.get());

        ENTITIES.put("boar", MoCEntities.BOAR.get());
        ENTITIES.put("bunny", MoCEntities.BUNNY.get());
        ENTITIES.put("deer", MoCEntities.DEER.get());
        ENTITIES.put("goat", MoCEntities.GOAT.get());
        ENTITIES.put("kitty", MoCEntities.KITTY.get());

        ENTITIES.put("crocodile", MoCEntities.CROCODILE.get());
        ENTITIES.put("turtle", MoCEntities.TURTLE.get());

        ENTITIES.put("elephant", MoCEntities.ELEPHANT.get());
        ENTITIES.put("filch_lizard", MoCEntities.FILCH_LIZARD.get());
        ENTITIES.put("ostrich", MoCEntities.OSTRICH.get());

        ENTITIES.put("fox", MoCEntities.FOX.get());

        ENTITIES.put("komodo_dragon", MoCEntities.KOMODO_DRAGON.get());
        ENTITIES.put("snake", MoCEntities.SNAKE.get());

        ENTITIES.put("leopard", MoCEntities.LEOPARD.get());
        ENTITIES.put("lion", MoCEntities.LION.get());
        ENTITIES.put("panther", MoCEntities.PANTHER.get());
        ENTITIES.put("tiger", MoCEntities.TIGER.get());

        ENTITIES.put("liger", MoCEntities.LIGER.get());
        ENTITIES.put("lither", MoCEntities.LITHER.get());
        ENTITIES.put("panthger", MoCEntities.PANTHGER.get());
        ENTITIES.put("panthard", MoCEntities.PANTHARD.get());
        ENTITIES.put("leoger", MoCEntities.LEOGER.get());

        ENTITIES.put("mouse", MoCEntities.MOUSE.get());
        ENTITIES.put("mole", MoCEntities.MOLE.get());
        ENTITIES.put("raccoon", MoCEntities.RACCOON.get());

        ENTITIES.put("wild_horse", MoCEntities.WILDHORSE.get());

        ENTITIES.put("ent", MoCEntities.ENT.get());
        ENTITIES.put("wyvern", MoCEntities.WYVERN.get());

        ENTITIES.put("green_ogre", MoCEntities.GREEN_OGRE.get());
        ENTITIES.put("cave_ogre", MoCEntities.CAVE_OGRE.get());
        ENTITIES.put("fire_ogre", MoCEntities.FIRE_OGRE.get());

        ENTITIES.put("cave_scorpion", MoCEntities.CAVE_SCORPION.get());
        ENTITIES.put("dirt_scorpion", MoCEntities.DIRT_SCORPION.get());
        ENTITIES.put("fire_scorpion", MoCEntities.FIRE_SCORPION.get());
        ENTITIES.put("frost_scorpion", MoCEntities.FROST_SCORPION.get());
        ENTITIES.put("undead_scorpion", MoCEntities.UNDEAD_SCORPION.get());

        ENTITIES.put("big_golem", MoCEntities.BIG_GOLEM.get());
        ENTITIES.put("mini_golem", MoCEntities.MINI_GOLEM.get());

        ENTITIES.put("dark_manticore", MoCEntities.DARK_MANTICORE.get());
        ENTITIES.put("fire_manticore", MoCEntities.FIRE_MANTICORE.get());
        ENTITIES.put("frost_manticore", MoCEntities.FROST_MANTICORE.get());
        ENTITIES.put("plain_manticore", MoCEntities.PLAIN_MANTICORE.get());
        ENTITIES.put("toxic_manticore", MoCEntities.TOXIC_MANTICORE.get());

        ENTITIES.put("werewolf", MoCEntities.WEREWOLF.get());
        ENTITIES.put("wwolf", MoCEntities.WWOLF.get());

        ENTITIES.put("rat", MoCEntities.RAT.get());
        ENTITIES.put("hell_rat", MoCEntities.HELL_RAT.get());

        ENTITIES.put("silver_skeleton", MoCEntities.SILVER_SKELETON.get());

        ENTITIES.put("wraith", MoCEntities.WRAITH.get());
        ENTITIES.put("flame_wraith", MoCEntities.FLAME_WRAITH.get());

        ENTITIES.put("horse_mob", MoCEntities.HORSE_MOB.get());

        ENTITIES.put("dolphin", MoCEntities.DOLPHIN.get());
        ENTITIES.put("shark", MoCEntities.SHARK.get());
        ENTITIES.put("manta_ray", MoCEntities.MANTA_RAY.get());
        ENTITIES.put("jellyfish", MoCEntities.JELLYFISH.get());

        ENTITIES.put("bass", MoCEntities.BASS.get());
        ENTITIES.put("sting_ray", MoCEntities.STING_RAY.get());

        ENTITIES.put("anchovy", MoCEntities.ANCHOVY.get());
        ENTITIES.put("angelfish", MoCEntities.ANGELFISH.get());
        ENTITIES.put("angler", MoCEntities.ANGLER.get());
        ENTITIES.put("clownfish", MoCEntities.CLOWNFISH.get());
        ENTITIES.put("goldfish", MoCEntities.GOLDFISH.get());
        ENTITIES.put("hippotang", MoCEntities.HIPPOTANG.get());
        ENTITIES.put("manderin", MoCEntities.MANDERIN.get());
        ENTITIES.put("cod", MoCEntities.COD.get());
        ENTITIES.put("salmon", MoCEntities.SALMON.get());
        ENTITIES.put("piranha", MoCEntities.PIRANHA.get());
        ENTITIES.put("fishy", MoCEntities.FISHY.get());

        ENTITIES.put("ant", MoCEntities.ANT.get());
        ENTITIES.put("bee", MoCEntities.BEE.get());
        ENTITIES.put("butterfly", MoCEntities.BUTTERFLY.get());
        ENTITIES.put("cricket", MoCEntities.CRICKET.get());
        ENTITIES.put("dragonfly", MoCEntities.DRAGONFLY.get());
        ENTITIES.put("firefly", MoCEntities.FIREFLY.get());
        ENTITIES.put("fly", MoCEntities.FLY.get());
        ENTITIES.put("grasshopper", MoCEntities.GRASSHOPPER.get());
        ENTITIES.put("maggot", MoCEntities.MAGGOT.get());
        ENTITIES.put("roach", MoCEntities.ROACH.get());
        ENTITIES.put("crab", MoCEntities.CRAB.get());
        ENTITIES.put("snail", MoCEntities.SNAIL.get());
    }
    
    /**
     * Get the creature name from an entity type by looking up the reverse mapping
     * @param entityType The entity type to look up
     * @return The creature name, or null if not found
     */
    public static String getCreatureName(EntityType<?> entityType) {
        for (Map.Entry<String, EntityType<?>> entry : ENTITIES.entrySet()) {
            if (entry.getValue() == entityType) {
                return entry.getKey();
            }
        }
        return null;
    }
}
