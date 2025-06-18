// MoCWyvernCommand.java
package drzhark.mocreatures.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class MoCWyvernCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        /*dispatcher.register(Commands.literal("tpwyvern")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayer();
                    ServerLevel wyvernWorld = player.getServer().getLevel(WyvernLairWorldDimension.WYVERNLAIR_WORLD_KEY);
                    if (wyvernWorld != null) {
                        player.teleportTo(wyvernWorld, 
                            WyvernLairWorldDimension.TELEPORT_X,
                            WyvernLairWorldDimension.TELEPORT_Y,
                            WyvernLairWorldDimension.TELEPORT_Z,
                            player.getYRot(), player.getXRot());
                        context.getSource().sendSuccess(() -> Component.literal("Teleported to Wyvern Dimension"), true);
                        return 1;
                    } else {
                        context.getSource().sendFailure(Component.literal("Wyvern dimension not found."));
                        return 0;
                    }
                }));*/
    }
}
