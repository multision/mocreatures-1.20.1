package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Handles spawn egg registration statically during the registry phase
 */
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCSpawnEggs {
    public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS, MoCConstants.MOD_ID);
    
    // Animal entities
    public static final RegistryObject<Item> BIRD_SPAWN_EGG = registerSpawnEgg("bird", 
            () -> MoCEntities.BIRD.get(), 37109, 4609629);
    
    public static final RegistryObject<Item> BLACK_BEAR_SPAWN_EGG = registerSpawnEgg("blackbear", 
            () -> MoCEntities.BLACK_BEAR.get(), 986897, 8609347);
    
    public static final RegistryObject<Item> BOAR_SPAWN_EGG = registerSpawnEgg("boar", 
            () -> MoCEntities.BOAR.get(), 2037783, 4995892);
            
    public static final RegistryObject<Item> BUNNY_SPAWN_EGG = registerSpawnEgg("bunny", 
            () -> MoCEntities.BUNNY.get(), 8741934, 14527570);
            
    public static final RegistryObject<Item> CROCODILE_SPAWN_EGG = registerSpawnEgg("crocodile", 
            () -> MoCEntities.CROCODILE.get(), 2698525, 10720356);
            
    public static final RegistryObject<Item> DUCK_SPAWN_EGG = registerSpawnEgg("duck", 
            () -> MoCEntities.DUCK.get(), 3161353, 14011565);
            
    public static final RegistryObject<Item> DEER_SPAWN_EGG = registerSpawnEgg("deer", 
            () -> MoCEntities.DEER.get(), 11572843, 13752020);
            
    public static final RegistryObject<Item> ELEPHANT_SPAWN_EGG = registerSpawnEgg("elephant", 
            () -> MoCEntities.ELEPHANT.get(), 4274216, 9337176);
            
    public static final RegistryObject<Item> ENT_SPAWN_EGG = registerSpawnEgg("ent", 
            () -> MoCEntities.ENT.get(), 9794886, 5800509);
            
    public static final RegistryObject<Item> FILCHLIZARD_SPAWN_EGG = registerSpawnEgg("filchlizard", 
            () -> MoCEntities.FILCH_LIZARD.get(), 9930060, 5580310);
            
    public static final RegistryObject<Item> FOX_SPAWN_EGG = registerSpawnEgg("fox", 
            () -> MoCEntities.FOX.get(), 15966491, 4009236);
            
    public static final RegistryObject<Item> GOAT_SPAWN_EGG = registerSpawnEgg("goat", 
            () -> MoCEntities.GOAT.get(), 15262682, 4404517);
            
    public static final RegistryObject<Item> GRIZZLYBEAR_SPAWN_EGG = registerSpawnEgg("grizzlybear", 
            () -> MoCEntities.GRIZZLY_BEAR.get(), 3547151, 11371099);
            
    public static final RegistryObject<Item> KITTY_SPAWN_EGG = registerSpawnEgg("kitty", 
            () -> MoCEntities.KITTY.get(), 16707009, 14861419);
            
    public static final RegistryObject<Item> KOMODODRAGON_SPAWN_EGG = registerSpawnEgg("komododragon", 
            () -> MoCEntities.KOMODO_DRAGON.get(), 8615512, 3025185);
            
    public static final RegistryObject<Item> LEOGER_SPAWN_EGG = registerSpawnEgg("leoger", 
            () -> MoCEntities.LEOGER.get(), 13274957, 6638124);
            
    public static final RegistryObject<Item> LEOPARD_SPAWN_EGG = registerSpawnEgg("leopard", 
            () -> MoCEntities.LEOPARD.get(), 13478009, 3682085);
            
    public static final RegistryObject<Item> LIARD_SPAWN_EGG = registerSpawnEgg("liard", 
            () -> MoCEntities.LIARD.get(), 11965543, 8215850);
            
    public static final RegistryObject<Item> LION_SPAWN_EGG = registerSpawnEgg("lion", 
            () -> MoCEntities.LION.get(), 11503958, 2234383);
            
    public static final RegistryObject<Item> LIGER_SPAWN_EGG = registerSpawnEgg("liger", 
            () -> MoCEntities.LIGER.get(), 13347170, 9068088);
            
    public static final RegistryObject<Item> LITHER_SPAWN_EGG = registerSpawnEgg("lither", 
            () -> MoCEntities.LITHER.get(), 2234897, 7821878);
            
    public static final RegistryObject<Item> MOLE_SPAWN_EGG = registerSpawnEgg("mole", 
            () -> MoCEntities.MOLE.get(), 263173, 10646113);
            
    public static final RegistryObject<Item> MOUSE_SPAWN_EGG = registerSpawnEgg("mouse", 
            () -> MoCEntities.MOUSE.get(), 7428164, 15510186);
            
    public static final RegistryObject<Item> OSTRICH_SPAWN_EGG = registerSpawnEgg("ostrich", 
            () -> MoCEntities.OSTRICH.get(), 12884106, 10646377);
            
    public static final RegistryObject<Item> PANDABEAR_SPAWN_EGG = registerSpawnEgg("pandabear", 
            () -> MoCEntities.PANDA_BEAR.get(), 13354393, 789516);
            
    public static final RegistryObject<Item> PANTHARD_SPAWN_EGG = registerSpawnEgg("panthard", 
            () -> MoCEntities.PANTHARD.get(), 591108, 9005068);
            
    public static final RegistryObject<Item> PANTHER_SPAWN_EGG = registerSpawnEgg("panther", 
            () -> MoCEntities.PANTHER.get(), 1709584, 16768078);
            
    public static final RegistryObject<Item> PANTHGER_SPAWN_EGG = registerSpawnEgg("panthger", 
            () -> MoCEntities.PANTHGER.get(), 2826517, 14348086);
    
    public static final RegistryObject<Item> WILDPOLARBEAR_SPAWN_EGG = registerSpawnEgg("wildpolarbear", 
            () -> MoCEntities.POLAR_BEAR.get(), 15131867, 11380879);
            
    public static final RegistryObject<Item> RACCOON_SPAWN_EGG = registerSpawnEgg("raccoon", 
            () -> MoCEntities.RACCOON.get(), 6115913, 1578001);
            
    public static final RegistryObject<Item> SNAKE_SPAWN_EGG = registerSpawnEgg("snake", 
            () -> MoCEntities.SNAKE.get(), 670976, 11309312);
            
    public static final RegistryObject<Item> TIGER_SPAWN_EGG = registerSpawnEgg("tiger", 
            () -> MoCEntities.TIGER.get(), 12476160, 2956299);
            
    public static final RegistryObject<Item> TURTLE_SPAWN_EGG = registerSpawnEgg("turtle", 
            () -> MoCEntities.TURTLE.get(), 6505237, 10524955);
            
    public static final RegistryObject<Item> TURKEY_SPAWN_EGG = registerSpawnEgg("turkey", 
            () -> MoCEntities.TURKEY.get(), 12268098, 6991322);
            
    public static final RegistryObject<Item> WILDHORSE_SPAWN_EGG = registerSpawnEgg("wildhorse", 
            () -> MoCEntities.WILDHORSE.get(), 9204829, 11379712);
            
    public static final RegistryObject<Item> WYVERN_SPAWN_EGG = registerSpawnEgg("wyvern", 
            () -> MoCEntities.WYVERN.get(), 11440923, 15526339);
            
    // Monster entities
    public static final RegistryObject<Item> CAVEOGRE_SPAWN_EGG = registerSpawnEgg("caveogre", 
            () -> MoCEntities.CAVE_OGRE.get(), 5079480, 12581631);
            
    public static final RegistryObject<Item> FIREOGRE_SPAWN_EGG = registerSpawnEgg("fireogre", 
            () -> MoCEntities.FIRE_OGRE.get(), 9440256, 14336256);
            
    public static final RegistryObject<Item> FLAMEWRAITH_SPAWN_EGG = registerSpawnEgg("flamewraith", 
            () -> MoCEntities.FLAME_WRAITH.get(), 16777216, 16744064);
            
    public static final RegistryObject<Item> BIGGOLEM_SPAWN_EGG = registerSpawnEgg("biggolem", 
            () -> MoCEntities.BIG_GOLEM.get(), 4868682, 52411);
            
    public static final RegistryObject<Item> GREENOGRE_SPAWN_EGG = registerSpawnEgg("greenogre", 
            () -> MoCEntities.GREEN_OGRE.get(), 3355443, 6553856);
            
    public static final RegistryObject<Item> HORSEMOB_SPAWN_EGG = registerSpawnEgg("horsemob", 
            () -> MoCEntities.HORSE_MOB.get(), 2763306, 10579012);
            
    public static final RegistryObject<Item> HELLRAT_SPAWN_EGG = registerSpawnEgg("hellrat", 
            () -> MoCEntities.HELL_RAT.get(), 5064201, 10354944);
            
    public static final RegistryObject<Item> DARKMANTICORE_SPAWN_EGG = registerSpawnEgg("darkmanticore", 
            () -> MoCEntities.DARK_MANTICORE.get(), 3289650, 657930);
            
    public static final RegistryObject<Item> FIREMANTICORE_SPAWN_EGG = registerSpawnEgg("firemanticore", 
            () -> MoCEntities.FIRE_MANTICORE.get(), 7148552, 2819585);
            
    public static final RegistryObject<Item> FROSTMANTICORE_SPAWN_EGG = registerSpawnEgg("frostmanticore", 
            () -> MoCEntities.FROST_MANTICORE.get(), 3559006, 2041389);
            
    public static final RegistryObject<Item> PLAINMANTICORE_SPAWN_EGG = registerSpawnEgg("plainmanticore", 
            () -> MoCEntities.PLAIN_MANTICORE.get(), 7623465, 5510656);
            
    public static final RegistryObject<Item> TOXICMANTICORE_SPAWN_EGG = registerSpawnEgg("toxicmanticore", 
            () -> MoCEntities.TOXIC_MANTICORE.get(), 6252034, 3365689);
            
    public static final RegistryObject<Item> MINIGOLEM_SPAWN_EGG = registerSpawnEgg("minigolem", 
            () -> MoCEntities.MINI_GOLEM.get(), 4734789, 12763842);
            
    public static final RegistryObject<Item> RAT_SPAWN_EGG = registerSpawnEgg("rat", 
            () -> MoCEntities.RAT.get(), 3685435, 15838633);
            
    public static final RegistryObject<Item> CAVESCORPION_SPAWN_EGG = registerSpawnEgg("cavescorpion", 
            () -> MoCEntities.CAVE_SCORPION.get(), 3289650, 855309);
            
    public static final RegistryObject<Item> DIRTSCORPION_SPAWN_EGG = registerSpawnEgg("dirtscorpion", 
            () -> MoCEntities.DIRT_SCORPION.get(), 6838816, 855309);
            
    public static final RegistryObject<Item> FIRESCORPION_SPAWN_EGG = registerSpawnEgg("firescorpion", 
            () -> MoCEntities.FIRE_SCORPION.get(), 16711680, 855309);
            
    public static final RegistryObject<Item> FROSTSCORPION_SPAWN_EGG = registerSpawnEgg("frostscorpion", 
            () -> MoCEntities.FROST_SCORPION.get(), 12632256, 855309);
            
    public static final RegistryObject<Item> UNDEADSCORPION_SPAWN_EGG = registerSpawnEgg("undeadscorpion", 
            () -> MoCEntities.UNDEAD_SCORPION.get(), 1710618, 855309);
            
    public static final RegistryObject<Item> SILVERSKELETON_SPAWN_EGG = registerSpawnEgg("silverskeleton", 
            () -> MoCEntities.SILVER_SKELETON.get(), 13882323, 15658734);
            
    public static final RegistryObject<Item> WEREWOLF_SPAWN_EGG = registerSpawnEgg("werewolf", 
            () -> MoCEntities.WEREWOLF.get(), 7631459, 14408667);
            
    public static final RegistryObject<Item> WRAITH_SPAWN_EGG = registerSpawnEgg("wraith", 
            () -> MoCEntities.WRAITH.get(), 2764582, 11579568);
            
    public static final RegistryObject<Item> WWOLF_SPAWN_EGG = registerSpawnEgg("wwolf", 
            () -> MoCEntities.WWOLF.get(), 4868682, 9996056);
            
    // Aquatic entities
    public static final RegistryObject<Item> ANCHOVY_SPAWN_EGG = registerSpawnEgg("anchovy", 
            () -> MoCEntities.ANCHOVY.get(), 7039838, 12763545);
            
    public static final RegistryObject<Item> ANGELFISH_SPAWN_EGG = registerSpawnEgg("angelfish", 
            () -> MoCEntities.ANGELFISH.get(), 12040119, 15970609);
            
    public static final RegistryObject<Item> ANGLER_SPAWN_EGG = registerSpawnEgg("angler", 
            () -> MoCEntities.ANGLER.get(), 2961195, 11972077);
            
    public static final RegistryObject<Item> CLOWNFISH_SPAWN_EGG = registerSpawnEgg("clownfish", 
            () -> MoCEntities.CLOWNFISH.get(), 16439491, 15425029);
            
    public static final RegistryObject<Item> GOLDFISH_SPAWN_EGG = registerSpawnEgg("goldfish", 
            () -> MoCEntities.GOLDFISH.get(), 16750848, 16776960);
            
    public static final RegistryObject<Item> HIPPOTANG_SPAWN_EGG = registerSpawnEgg("hippotang", 
            () -> MoCEntities.HIPPOTANG.get(), 4280267, 12893441);
            
    public static final RegistryObject<Item> MANDERIN_SPAWN_EGG = registerSpawnEgg("manderin", 
            () -> MoCEntities.MANDERIN.get(), 14764801, 5935359);
            
    public static final RegistryObject<Item> BASS_SPAWN_EGG = registerSpawnEgg("bass", 
            () -> MoCEntities.BASS.get(), 5854242, 10066177);
            
    public static final RegistryObject<Item> COD_SPAWN_EGG = registerSpawnEgg("cod", 
            () -> MoCEntities.COD.get(), 8355712, 12169216);
            
    public static final RegistryObject<Item> SALMON_SPAWN_EGG = registerSpawnEgg("salmon", 
            () -> MoCEntities.SALMON.get(), 10489616, 951424);
            
    public static final RegistryObject<Item> DOLPHIN_SPAWN_EGG = registerSpawnEgg("dolphin", 
            () -> MoCEntities.DOLPHIN.get(), 4086148, 11251396);
            
    public static final RegistryObject<Item> MANTARAY_SPAWN_EGG = registerSpawnEgg("mantaray", 
            () -> MoCEntities.MANTA_RAY.get(), 592137, 2434341);
            
    public static final RegistryObject<Item> SHARK_SPAWN_EGG = registerSpawnEgg("shark", 
            () -> MoCEntities.SHARK.get(), 3817558, 11580358);
            
    public static final RegistryObject<Item> STINGRAY_SPAWN_EGG = registerSpawnEgg("stingray", 
            () -> MoCEntities.STING_RAY.get(), 6770509, 13947080);
            
    public static final RegistryObject<Item> FISHY_SPAWN_EGG = registerSpawnEgg("fishy", 
            () -> MoCEntities.FISHY.get(), 16684800, 13382400);
            
    public static final RegistryObject<Item> JELLYFISH_SPAWN_EGG = registerSpawnEgg("jellyfish", 
            () -> MoCEntities.JELLYFISH.get(), 3618615, 10066177);
            
    public static final RegistryObject<Item> PIRANHA_SPAWN_EGG = registerSpawnEgg("piranha", 
            () -> MoCEntities.PIRANHA.get(), 16684800, 13107200);
            
    // Ambient entities
    public static final RegistryObject<Item> ANT_SPAWN_EGG = registerSpawnEgg("ant", 
            () -> MoCEntities.ANT.get(), 5915945, 2693905);
            
    public static final RegistryObject<Item> BEE_SPAWN_EGG = registerSpawnEgg("bee", 
            () -> MoCEntities.BEE.get(), 15912747, 526604);
            
    public static final RegistryObject<Item> BUTTERFLY_SPAWN_EGG = registerSpawnEgg("butterfly", 
            () -> MoCEntities.BUTTERFLY.get(), 15912747, 526604);
            
    public static final RegistryObject<Item> CRAB_SPAWN_EGG = registerSpawnEgg("crab", 
            () -> MoCEntities.CRAB.get(), 11880978, 15514213);
            
    public static final RegistryObject<Item> CRICKET_SPAWN_EGG = registerSpawnEgg("cricket", 
            () -> MoCEntities.CRICKET.get(), 1644825, 6513507);
            
    public static final RegistryObject<Item> DRAGONFLY_SPAWN_EGG = registerSpawnEgg("dragonfly", 
            () -> MoCEntities.DRAGONFLY.get(), 2302755, 8224125);
            
    public static final RegistryObject<Item> FIREFLY_SPAWN_EGG = registerSpawnEgg("firefly", 
            () -> MoCEntities.FIREFLY.get(), 15921152, 1315840);
            
    public static final RegistryObject<Item> FLY_SPAWN_EGG = registerSpawnEgg("fly", 
            () -> MoCEntities.FLY.get(), 1644825, 6513507);
            
    public static final RegistryObject<Item> GRASSHOPPER_SPAWN_EGG = registerSpawnEgg("grasshopper", 
            () -> MoCEntities.GRASSHOPPER.get(), 7830593, 3747075);
            
    public static final RegistryObject<Item> MAGGOT_SPAWN_EGG = registerSpawnEgg("maggot", 
            () -> MoCEntities.MAGGOT.get(), 14737632, 16777215);
            
    public static final RegistryObject<Item> ROACH_SPAWN_EGG = registerSpawnEgg("roach", 
            () -> MoCEntities.ROACH.get(), 1644825, 6513507);
            
    public static final RegistryObject<Item> SNAIL_SPAWN_EGG = registerSpawnEgg("snail", 
            () -> MoCEntities.SNAIL.get(), 8618883, 15658734);
    
    /**
     * Helper method to register a spawn egg
     */
    private static RegistryObject<Item> registerSpawnEgg(String entityName, 
            Supplier<EntityType<? extends Mob>> entityTypeSupplier, int primaryColor, int secondaryColor) {
        return SPAWN_EGGS.register(entityName + "_spawn_egg", 
                () -> new ForgeSpawnEggItem(entityTypeSupplier, primaryColor, secondaryColor, new Item.Properties()));
    }
    
    /**
     * Setup creative tabs for spawn eggs
     */
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MoCreatures.LOGGER.info("Setting up Mo'Creatures spawn eggs");
            // Any additional setup can go here (not registration)
        });
    }
} 