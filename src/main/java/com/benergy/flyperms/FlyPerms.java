package com.benergy.flyperms;

import com.benergy.flyperms.listeners.FPPlayerListener;
import com.benergy.flyperms.listeners.FPWorldListener;
import com.benergy.flyperms.commands.FlyPermsCommand;
import com.benergy.flyperms.handlers.CommandHandler;
import com.benergy.flyperms.permissions.PermsCommand;
import com.benergy.flyperms.permissions.PermsFly;
import com.benergy.flyperms.permissions.PermsRegister;
import com.benergy.flyperms.utils.LoggerUtil;
import com.benergy.flyperms.utils.MetricsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class FlyPerms extends JavaPlugin {

    // Logging
    private final LoggerUtil logger = new LoggerUtil(this);

    // Config
    private final FlyPermsConfig FPConfig = new FlyPermsConfig(this);

    // Permissions
    private final PermsRegister FPRegister = new PermsRegister(this);
    private final PermsCommand FPCommand = new PermsCommand(this);
    private final PermsFly FPFly = new PermsFly(this);

    // Handlers
    private final CommandHandler commandHandler = new CommandHandler(this);

    // Listeners
    private final FPPlayerListener playerListener = new FPPlayerListener(this);
    private final FPWorldListener worldListener = new FPWorldListener(this);

    @Override
    public void onEnable() {
        logger.startUpText();

        // Get config
        this.saveDefaultConfig();
        this.FPConfig.loadConfigValues();

        // Init bstats
        MetricsUtil.configureMetrics(this);

        // Register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.playerListener, this);
        pm.registerEvents(this.worldListener, this);

        // Register permission nodes
        this.FPRegister.registerPerms();

        // Register commands
        PluginCommand pluginCommand = this.getCommand("flyperms");
        pluginCommand.setExecutor(new FlyPermsCommand(this));
        if (!commandHandler.registerCommands(pluginCommand)) {
            this.getFPLogger().log(Level.WARNING, "Unable to register commodore auto complete. You can ignore this if you are using <1.13.");
        } else {
            this.getFPLogger().log(Level.INFO, "Registered commodore auto-complete.");
        }

        this.getFPLogger().log(Level.INFO, "Started!");
    }

    public boolean reload() {
        if (!this.FPConfig.reloadConfigValues()) {
            return false;
        }
        Bukkit.getOnlinePlayers().forEach(FPFly::canFly);
        this.getFPLogger().log(Level.FINE, "FlyPerms was successfully reloaded!");
        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getFPLogger().log(Level.INFO, "Stopped. Happy flying!");
    }

    public void setLogLevel() {
        if (this.FPConfig.isDebugMode()) {
            this.getFPLogger().setLogLevel(Level.FINEST);
            this.getFPLogger().log(Level.FINE, "Debug logging enabled.");
        } else {
            this.getFPLogger().log(Level.FINE, "Debug logging disabled.");
            this.getFPLogger().setLogLevel(Level.INFO);
        }
    }

    public boolean isIgnoreWorld(World world) {
        return (FPConfig.getDisabledWorlds().contains(world.getName()));
    }

    public FlyPermsConfig getFPConfig() {
        return FPConfig;
    }

    public PermsRegister getFPRegister() {
        return FPRegister;
    }

    public PermsCommand getFPCommand() {
        return FPCommand;
    }

    public PermsFly getFPFly() {
        return FPFly;
    }

    public LoggerUtil getFPLogger() {
        return logger;
    }

}
