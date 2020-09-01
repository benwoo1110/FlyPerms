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

    private static final LiteralCommandNode<?> commandCompletion = LiteralArgumentBuilder.literal("flyperms")
            .then(LiteralArgumentBuilder.literal("seeallowed")
                    .then(RequiredArgumentBuilder.argument("user", StringArgumentType.string())))
            .then(LiteralArgumentBuilder.literal("info"))
            .build();

    public static void registerCommands(FlyPerms plugin, Command pluginCommand) {
        // Register auto-complete
        // check if brigadier is supported
        if (CommodoreProvider.isSupported()) {
            // register completions
            Commodore commodore = CommodoreProvider.getCommodore(plugin);
            commodore.register(pluginCommand, commandCompletion);
        }
    }


}
