package com.benergy.flyperms;

import com.benergy.flyperms.Commands.TestCommand;
import com.benergy.flyperms.Listeners.FPFlightListener;
import com.benergy.flyperms.Listeners.FPPlayerListener;
import com.benergy.flyperms.Listeners.FPWorldListener;
import com.benergy.flyperms.Permissions.FPPermissions;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class FlyPerms extends JavaPlugin {

    // Config
    private FlyPermsConfig FPConfig;

    // Permissions
    FPPermissions FPPerms = new FPPermissions(this);;

    // Listeners
    private final FPFlightListener flightListener = new FPFlightListener(this);
    private final FPPlayerListener playerListener = new FPPlayerListener(this);
    private final FPWorldListener worldListener = new FPWorldListener(this);

    // Logger
    private final Logger log = Logger.getLogger(this.getName());

    @Override
    public void onEnable() {
        // Get config
        saveDefaultConfig();
        this.FPConfig = new FlyPermsConfig(this.getConfig());

        // Register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.flightListener, this);
        pm.registerEvents(this.playerListener, this);
        pm.registerEvents(this.worldListener, this);

        // Register world permission nodes
        this.FPPerms.registerPerms();

        // Resgister commands
        // register your command executor as normal.
        PluginCommand command = getCommand("flyperms");
        command.setExecutor(new TestCommand());

        // check if brigadier is supported
        if (CommodoreProvider.isSupported()) {

            // get a commodore instance
            Commodore commodore = CommodoreProvider.getCommodore(this);

            // register your completions.
            registerCompletions(commodore, command);
        }
    }

    // You will need to put this method inside another class to prevent classloading
    // errors when your plugin loads on pre 1.13 versions.
    private static void registerCompletions(Commodore commodore, PluginCommand command) {
        LiteralCommandNode<?> testCommand = LiteralArgumentBuilder.literal("flyperms")
                .then(LiteralArgumentBuilder.literal("set")
                        .then(LiteralArgumentBuilder.literal("day"))
                        .then(LiteralArgumentBuilder.literal("noon"))
                        .then(LiteralArgumentBuilder.literal("night"))
                        .then(LiteralArgumentBuilder.literal("midnight"))
                        .then(RequiredArgumentBuilder.argument("time", IntegerArgumentType.integer())))
                .then(LiteralArgumentBuilder.literal("add")
                        .then(RequiredArgumentBuilder.argument("time", IntegerArgumentType.integer())))
                .then(LiteralArgumentBuilder.literal("query")
                        .then(LiteralArgumentBuilder.literal("daytime"))
                        .then(LiteralArgumentBuilder.literal("gametime"))
                        .then(LiteralArgumentBuilder.literal("day"))
                ).build();

        commodore.register(command, testCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Logger getLog() {
        return log;
    }

    public FlyPermsConfig getFPConfig() {
        return FPConfig;
    }

    public FPPermissions getFPPerms() {
        return FPPerms;
    }

    public boolean ignoreWorld(World world) {
        return (FPConfig.getDisabledWorlds().contains(world.getName()));
    }
}
