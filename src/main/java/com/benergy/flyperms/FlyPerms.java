package com.benergy.flyperms;

import com.benergy.flyperms.listeners.FPFlightListener;
import com.benergy.flyperms.listeners.FPPlayerListener;
import com.benergy.flyperms.listeners.FPWorldListener;
import com.benergy.flyperms.commands.FlyPermsCommand;
import com.benergy.flyperms.handlers.CommandHandler;
import com.benergy.flyperms.permissions.FPPermissions;
import com.benergy.flyperms.utils.MetricsUtil;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class FlyPerms extends JavaPlugin {

    // Config
    private FlyPermsConfig FPConfig = new FlyPermsConfig(this);;

    // Permissions
    FPPermissions FPPerms = new FPPermissions(this);

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
        this.FPConfig.loadConfigValues();

        // Init bstats
        MetricsUtil.configureMetrics(this);

        // Set log level
        if (this.FPConfig.isDebugMode()) {
            log.setLevel(Level.FINEST);
        } else {
            log.setLevel(Level.INFO);
        }

        // Register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.flightListener, this);
        pm.registerEvents(this.playerListener, this);
        pm.registerEvents(this.worldListener, this);

        // Register permission nodes
        this.FPPerms.registerPerms();

        // Register commands
        PluginCommand pluginCommand = this.getCommand("flyperms");
        pluginCommand.setExecutor(new FlyPermsCommand(this));
        CommandHandler.registerCommands(this, pluginCommand);
    }

    public boolean reload() {
        if (!this.FPConfig.reloadConfigValues()) {
            this.log.severe("Error reloading config!");
            return false;
        }
        // TODO: Handle perms and fly changes on reload

        log.fine("FlyPerms was successfully reloaded!");
        return true;
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
