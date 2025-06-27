package drzhark.mocreatures.command;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.network.command.CommandMoCPets;
import drzhark.mocreatures.network.command.CommandMoCSpawn;
import drzhark.mocreatures.network.command.CommandMoCTP;
import drzhark.mocreatures.network.command.CommandMoCreatures;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.CommandDispatcher;

@Mod.EventBusSubscriber(modid = "mocreatures")
public class MoCCommandHandler {
    
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        LOGGER.info("MoCreatures registering commands");
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        
        try {
            // Register commands from the network/command package
            LOGGER.info("Registering MoCreatures commands");
            
            // Legacy/old commands that have been updated
            MoCWyvernCommand.register(dispatcher);
            CommandSpawnMoCHorse.register(dispatcher);

            ReloadSpawnConfigCommand.register(dispatcher);
            
            // Debug commands
            MoCDebugSpawnCommand.register(dispatcher);
        } catch (Exception e) {
            LOGGER.error("Error registering MoCreatures commands", e);
        }
    }
}