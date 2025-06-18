/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.network.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class CommandMoCreatures {

    /*private static final List<String> commands = new ArrayList<>();
    private static final List<String> aliases = new ArrayList<>();
    private static final List<String> tabCompletionStrings = new ArrayList<>();

    static {
        commands.add("/moc attackdolphins <boolean>");
        commands.add("/moc attackhorses <boolean>");
        commands.add("/moc attackwolves <boolean>");
        commands.add("/moc canspawn <boolean>");
        commands.add("/moc caveogrechance <float>");
        commands.add("/moc caveogrestrength <float>");
        commands.add("/moc debug <boolean>");
        commands.add("/moc destroydrops <boolean>");
        commands.add("/moc enablehunters <boolean>");
        commands.add("/moc easybreeding <boolean>");
        commands.add("/moc elephantbulldozer <boolean>");
        commands.add("/moc enableownership <boolean>");
        commands.add("/moc enableresetownerscroll <boolean>");
        commands.add("/moc fireogrechance <int>");
        commands.add("/moc fireogrestrength <float>");
        commands.add("/moc frequency <entity> <int>");
        commands.add("/moc golemdestroyblocks <boolean>");
        commands.add("/moc tamed");
        commands.add("/moc tamed <playername>");
        commands.add("/moc maxchunk <entity> <int>");
        commands.add("/moc maxspawn <entity> <int>");
        commands.add("/moc maxtamedperop <int>");
        commands.add("/moc maxtamedperplayer <int>");
        commands.add("/moc minspawn <entity> <int>");
        commands.add("/moc motherwyverneggdropchance <int>");
        commands.add("/moc ogreattackrange <int>");
        commands.add("/moc ogrestrength <float>");
        commands.add("/moc ostricheggdropchance <int>");
        commands.add("/moc rareitemdropchance <int>");
        commands.add("/moc spawnhorse <int>");
        commands.add("/moc spawnwyvern <int>");
        commands.add("/moc tamedcount <playername>");
        commands.add("/moc tp <petid> <playername>");
        commands.add("/moc <command> value");
        commands.add("/moc wyverneggdropchance <int>");
        commands.add("/moc zebrachance <int>");
        
        aliases.add("moc");
        
        tabCompletionStrings.add("attackdolphins");
        tabCompletionStrings.add("attackhorses");
        tabCompletionStrings.add("attackwolves");
        tabCompletionStrings.add("canspawn");
        tabCompletionStrings.add("caveogrechance");
        tabCompletionStrings.add("caveogrestrength");
        tabCompletionStrings.add("debug");
        tabCompletionStrings.add("destroydrops");
        tabCompletionStrings.add("easybreeding");
        tabCompletionStrings.add("elephantbulldozer");
        tabCompletionStrings.add("enableownership");
        tabCompletionStrings.add("enableresetownerscroll");
        tabCompletionStrings.add("fireogrechance");
        tabCompletionStrings.add("fireogrestrength");
        tabCompletionStrings.add("forcedespawns");
        tabCompletionStrings.add("frequency");
        tabCompletionStrings.add("golemdestroyblocks");
        tabCompletionStrings.add("tamed");
        tabCompletionStrings.add("maxchunk");
        tabCompletionStrings.add("maxspawn");
        tabCompletionStrings.add("maxtamedperop");
        tabCompletionStrings.add("maxtamedperplayer");
        tabCompletionStrings.add("minspawn");
        tabCompletionStrings.add("motherwyverneggdropchance");
        tabCompletionStrings.add("ogreattackrange");
        tabCompletionStrings.add("ogreattackstrength");
        tabCompletionStrings.add("ostricheggdropchance");
        tabCompletionStrings.add("rareitemdropchance");
        tabCompletionStrings.add("spawnhorse");
        tabCompletionStrings.add("spawnwyvern");
        tabCompletionStrings.add("tamedcount");
        tabCompletionStrings.add("tp");
        tabCompletionStrings.add("wyverneggdropchance");
        tabCompletionStrings.add("zebrachance");
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Root command
        var mocCommand = Commands.literal("moc")
            .requires(source -> source.hasPermission(2));
            
        // Boolean settings
        for (String setting : List.of("attackdolphins", "attackhorses", "attackwolves", "canspawn", 
                "debug", "destroydrops", "enablehunters", "easybreeding", "elephantbulldozer", 
                "enableownership", "enableresetownerscroll", "golemdestroyblocks")) {
            mocCommand.then(Commands.literal(setting)
                .then(Commands.argument("value", BoolArgumentType.bool())
                    .executes(context -> {
                        boolean value = BoolArgumentType.getBool(context, "value");
                        return setBooleanSetting(context.getSource(), setting, value);
                    })
                )
            );
        }
        
        // Float settings
        for (String setting : List.of("caveogrechance", "caveogrestrength", "fireogrestrength", "ogrestrength")) {
            mocCommand.then(Commands.literal(setting)
                .then(Commands.argument("value", FloatArgumentType.floatArg(0))
                    .executes(context -> {
                        float value = FloatArgumentType.getFloat(context, "value");
                        return setFloatSetting(context.getSource(), setting, value);
                    })
                )
            );
        }
        
        // Integer settings
        for (String setting : List.of("fireogrechance", "motherwyverneggdropchance", "ogreattackrange", 
                "ostricheggdropchance", "rareitemdropchance", "maxtamedperop", "maxtamedperplayer",
                "wyverneggdropchance", "zebrachance")) {
            mocCommand.then(Commands.literal(setting)
                .then(Commands.argument("value", IntegerArgumentType.integer(0))
                    .executes(context -> {
                        int value = IntegerArgumentType.getInteger(context, "value");
                        return setIntSetting(context.getSource(), setting, value);
                    })
                )
            );
        }
        
        // Entity-specific settings
        for (String setting : List.of("frequency", "maxchunk", "maxspawn", "minspawn")) {
            mocCommand.then(Commands.literal(setting)
                .then(Commands.argument("entity", StringArgumentType.word())
                    .then(Commands.argument("value", IntegerArgumentType.integer(0))
                        .executes(context -> {
                            String entity = StringArgumentType.getString(context, "entity");
                            int value = IntegerArgumentType.getInteger(context, "value");
                            return setEntitySetting(context.getSource(), setting, entity, value);
                        })
                    )
                )
            );
        }
        
        // Special commands
        mocCommand.then(Commands.literal("help")
            .executes(context -> {
                return showHelp(context.getSource());
            })
        );
        
        // Register the command with all subcommands
        dispatcher.register(mocCommand);
    }
    
    private static int showHelp(CommandSourceStack source) {
        source.sendSuccess(() -> Component.literal("MoCreatures Commands:"), false);
        for (String command : commands) {
            source.sendSuccess(() -> Component.literal(command), false);
        }
        return 1;
    }
    
    private static int setBooleanSetting(CommandSourceStack source, String setting, boolean value) {
        try {
            MoCConfig config = MoCreatures.proxy.getConfig();
            boolean result = false;
            
            // This is a simplified version - will need proper implementation
            switch (setting) {
                case "debug":
                    MoCreatures.proxy.debug = value;
                    result = true;
                    break;
                // Add other cases for the other boolean settings
                default:
                    source.sendFailure(Component.literal("Setting not implemented yet: " + setting));
                    return 0;
            }
            
            if (result) {
                source.sendSuccess(() -> Component.literal("Successfully set " + setting + " to " + value), true);
                return 1;
            } else {
                source.sendFailure(Component.literal("Failed to set " + setting));
                return 0;
            }
        } catch (Exception e) {
            source.sendFailure(Component.literal("Error: " + e.getMessage()));
            return 0;
        }
    }
    
    private static int setFloatSetting(CommandSourceStack source, String setting, float value) {
        // Placeholder for implementation
        source.sendFailure(Component.literal("Setting not implemented yet: " + setting));
        return 0;
    }
    
    private static int setIntSetting(CommandSourceStack source, String setting, int value) {
        // Placeholder for implementation
        source.sendFailure(Component.literal("Setting not implemented yet: " + setting));
        return 0;
    }
    
    private static int setEntitySetting(CommandSourceStack source, String setting, String entity, int value) {
        // Placeholder for implementation
        source.sendFailure(Component.literal("Entity setting not implemented yet: " + setting + " for " + entity));
        return 0;
    }
    */
}
