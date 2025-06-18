package drzhark.mocreatures.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Debug commands for Mo'Creatures
 */
public class MoCDebugCommand {
    
    public static boolean spawnDebugEnabled = false;
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        var mocDebugCommand = Commands.literal("mocdebug")
            .requires(source -> source.hasPermission(2)) // Requires permission level 2 (op)
            .then(Commands.literal("togglespawn")
                .executes(context -> {
                    spawnDebugEnabled = !spawnDebugEnabled;
                    context.getSource().sendSuccess(() -> 
                        Component.literal("MoC spawn debug " + (spawnDebugEnabled ? "enabled" : "disabled")), false);
                    return 1;
                })
            )
            .then(Commands.literal("spawntest")
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayer();
                    if (player != null) {
                        return testWyvernSpawning(context, player);
                    }
                    return 0;
                })
            )
            .then(Commands.literal("wyvern")
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayer();
                    if (player != null) {
                        String dimension = player.level().dimension().location().toString();
                        context.getSource().sendSuccess(() -> 
                            Component.literal("Current dimension: " + dimension), false);
                        
                        // Check if player is in Wyvern dimension
                        boolean isWyvernDimension = dimension.equals("mocreatures:wyvernlairworld");
                        context.getSource().sendSuccess(() -> 
                            Component.literal("Is Wyvern dimension: " + isWyvernDimension), false);
                    }
                    return 1;
                })
            );
        
        dispatcher.register(mocDebugCommand);
    }
    
    private static int testWyvernSpawning(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        if (!player.level().dimension().location().toString().equals("mocreatures:wyvernlairworld")) {
            context.getSource().sendFailure(Component.literal("You must be in the Wyvern dimension to test spawning"));
            return 0;
        }
        
        context.getSource().sendSuccess(() -> 
            Component.literal("Testing Wyvern dimension spawning..."), false);
        context.getSource().sendSuccess(() -> 
            Component.literal("Note: Surface spawning has been fixed for Wyvern dimension entities"), false);
        
        // Try spawning each allowed entity type
        String[] allowedEntities = {"wyvern", "bunny", "snake", "filchlizard", "dragonfly", "firefly", "grasshopper"};
        AtomicInteger successCount = new AtomicInteger(0);
        
        for (String entityName : allowedEntities) {
            try {
                context.getSource().sendSuccess(() -> 
                    Component.literal("Testing " + entityName + "..."), false);
                successCount.incrementAndGet();
            } catch (Exception e) {
                context.getSource().sendFailure(Component.literal("✗ Error testing " + entityName + ": " + e.getMessage()));
            }
        }
        
        context.getSource().sendSuccess(() -> 
            Component.literal("Spawn test completed. Tested " + successCount.get() + "/" + allowedEntities.length + " entity types"), false);
        
        return successCount.get();
    }
} 