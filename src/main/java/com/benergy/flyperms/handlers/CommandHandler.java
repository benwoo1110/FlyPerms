package com.benergy.flyperms.handlers;

import com.benergy.flyperms.FlyPerms;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import org.bukkit.command.Command;

public class CommandHandler {

    FlyPerms plugin;

    public CommandHandler(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public boolean registerCommands(Command pluginCommand) {
        // Register auto-complete
        // check if brigadier is supported
        if (CommodoreProvider.isSupported()) {
            // register completions
            Commodore commodore = CommodoreProvider.getCommodore(plugin);

            final LiteralCommandNode<?> commandCompletion = LiteralArgumentBuilder.literal("flyperms").requires(o -> plugin.getFPCommand().hasAnyPerms(commodore.getBukkitSender(o)))
                    .then(LiteralArgumentBuilder.literal("seeallowed").requires(o -> commodore.getBukkitSender(o).hasPermission("flyperms.seeallowed"))
                            .then(RequiredArgumentBuilder.argument("user", StringArgumentType.string()).requires(o -> commodore.getBukkitSender(o).hasPermission("flyperms.seeallowed.others"))))
                    .then(LiteralArgumentBuilder.literal("info").requires(o -> commodore.getBukkitSender(o).hasPermission("flyperms.info")))
                    .then(LiteralArgumentBuilder.literal("reload").requires(o -> commodore.getBukkitSender(o).hasPermission("flyperms.reload")))
                    .build();

            commodore.register(pluginCommand, commandCompletion);
            return true;
        }
        return false;
    }


}
