package drzhark.mocreatures;

import com.mojang.authlib.GameProfile;
import drzhark.mocreatures.client.MoCKeyHandler;
import drzhark.mocreatures.client.renderer.fx.MoCParticles;
import drzhark.mocreatures.compat.CompatHandler;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.entity.tameable.MoCPetMapData;
import drzhark.mocreatures.event.MoCEventHooks;
import drzhark.mocreatures.event.MoCEventHooksClient;
import drzhark.mocreatures.event.MoCEventHooksTerrain;
import drzhark.mocreatures.init.MoCBlocks;
import drzhark.mocreatures.init.MoCCreativeTabs;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCFeatures;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.init.MoCSpawnEggs;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.proxy.MoCProxy;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.registry.MoCPOI;
import drzhark.mocreatures.world.MoCSpawnConfig;
import drzhark.mocreatures.world.MoCSpawnRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import drzhark.mocreatures.command.MoCDebugCommand;
import drzhark.mocreatures.event.MoCWyvernDimensionHandler;

import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MoCConstants.MOD_ID)
public class MoCreatures {

    public static final Logger LOGGER = LogManager.getLogger(MoCConstants.MOD_ID);
    private static boolean debug = false;

    public static final String MOC_LOGO = ChatFormatting.WHITE + "[" + ChatFormatting.AQUA + MoCConstants.MOD_NAME + ChatFormatting.WHITE + "]";
    public static MoCreatures instance;
    public static MoCProxy proxy;
    public static GameProfile MOCFAKEPLAYER = new GameProfile(UUID.fromString("6E379B45-1111-2222-3333-2FE1A88BCD66"), "[MoCreatures]");
    // Wyvern dimension key
    //public static ResourceKey<Level> wyvernDimension = WyvernLairWorldDimension.WYVERNLAIR_WORLD_KEY;
    public static Object2ObjectLinkedOpenHashMap<String, MoCEntityData> mocEntityMap = new Object2ObjectLinkedOpenHashMap<>();
    public static Object2ObjectOpenHashMap<EntityType<?>, MoCEntityData> entityMap = new Object2ObjectOpenHashMap<>();
    public static Int2ObjectOpenHashMap<Class<? extends Mob>> instaSpawnerMap = new Int2ObjectOpenHashMap<>();
    public MoCPetMapData mapData;

    @SuppressWarnings("removal")
    public MoCreatures() {
        instance = this;

        this.proxy = DistExecutor.unsafeRunForDist(() -> MoCProxyClient::new, () -> MoCProxy::new);
        MoCMessageHandler.init();
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        MinecraftForge.EVENT_BUS.register(new MoCEventHooksTerrain());
        //proxy.configInit();
        if (true) {
            MinecraftForge.EVENT_BUS.register(new MoCEventHooksClient());
            MinecraftForge.EVENT_BUS.register(new MoCKeyHandler());
        }
        // Register the Wyvern dimension handler
        MinecraftForge.EVENT_BUS.register(MoCWyvernDimensionHandler.class);
        
        CompatHandler.preInit();

        proxy.configInit();
        proxy.registerRenderers();
        proxy.registerRenderInformation();

        MoCEventHooksTerrain.addBiomeTypes();

        CompatHandler.init();
        registerDeferredRegistries(eventBus);
        
        // Register the setup method for mod loading
        eventBus.addListener(this::setup);
        
        // Register our spawn registry
        MinecraftForge.EVENT_BUS.register(MoCSpawnRegistry.class);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        // This is called after registry events - safe to build spawn lists
        event.enqueueWork(() -> {
            LOGGER.info("Building Mo'Creatures world gen spawn lists");
            MoCEventHooksTerrain.buildWorldGenSpawnLists();
            
            // Initialize the spawn registry
            MoCSpawnRegistry.init();
            
            // Initialize the POI registry
            MoCPOI.init();
        });
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Register spawn data
            MoCSpawnConfig.register();
            // MoCSpawnRegistry is handled via event handlers, no need to call register()
        });
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        // Register commands
        MoCDebugCommand.register(event.getServer().getCommands().getDispatcher());
    }
    
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        // Register commands
        MoCDebugCommand.register(event.getDispatcher());
    }

    public static boolean isServer(Level world) {
        return !world.isClientSide();
    }
    
    /**
     * Check if debug mode is enabled
     */
    public static boolean isDebug() {
        return debug;
    }
    
    /**
     * Set debug mode
     */
    public static void setDebug(boolean debug) {
        debug = debug;
        if (debug) {
            LOGGER.info("Mo'Creatures debug mode enabled");
        }
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        MoCSoundEvents.SOUND_DEFERRED.register(modBus);
        MoCParticles.PARTICLES.register(modBus);
        MoCBlocks.BLOCKS.register(modBus);
        MoCBlocks.ITEMS.register(modBus);
        MoCEntities.ENTITY_TYPES.register(modBus);
        MoCSpawnEggs.SPAWN_EGGS.register(modBus);
        MoCItems.ITEMS.register(modBus);
        MoCFeatures.FEATURES.register(modBus);
        MoCCreativeTabs.register(modBus);
        MoCPOI.POI_TYPES.register(modBus);
    }
}
