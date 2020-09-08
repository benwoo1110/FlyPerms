package com.benergy.flyperms;

import com.benergy.flyperms.listeners.FPFlightListener;
import com.benergy.flyperms.listeners.FPPlayerListener;
import com.benergy.flyperms.listeners.FPWorldListener;
import com.benergy.flyperms.commands.FlyPermsCommand;
import com.benergy.flyperms.handlers.CommandHandler;
import com.benergy.flyperms.permissions.PermsCommand;
import com.benergy.flyperms.permissions.PermsFly;
import com.benergy.flyperms.permissions.PermsRegister;
import com.benergy.flyperms.utils.MetricsUtil;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class FlyPerms extends JavaPlugin {

    // Config
    private final FlyPermsConfig FPConfig = new FlyPermsConfig(this);;

    // Permissions
    private final PermsRegister FPRegister = new PermsRegister(this);
    private final PermsCommand FPCommand = new PermsCommand(this);
    private final PermsFly FPFly = new PermsFly(this);

    // Handlers
    private final CommandHandler commandHandler = new CommandHandler(this);

    // Listeners
    private final FPFlightListener flightListener = new FPFlightListener(this);
    private final FPPlayerListener playerListener = new FPPlayerListener(this);
    private final FPWorldListener worldListener = new FPWorldListener(this);

    @Override
    public void onEnable() {
        // Get config
        saveDefaultConfig();
        this.FPConfig.loadConfigValues();

        // log
        this.setLogLevel();

        // Init bstats
        MetricsUtil.configureMetrics(this);

        // Register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.flightListener, this);
        pm.registerEvents(this.playerListener, this);
        pm.registerEvents(this.worldListener, this);

        // Register permission nodes
        this.FPRegister.registerPerms();

        // Register commands
        PluginCommand pluginCommand = this.getCommand("flyperms");
        pluginCommand.setExecutor(new FlyPermsCommand(this));
        if (!commandHandler.registerCommands(pluginCommand)) {
            this.getLogger().warning("Unable to register commodore auto complete. You can ignore this if you are using <1.13.");
        } else {
            this.getLogger().info("Registered commodore auto-complete.");
        }

        this.getLogger().info(ChatColor.GREEN.toString() + "Enabled FlyPerms v" + this.getDescription().getVersion() + "!");
    }

    public boolean reload() {
        this.setLogLevel();

        if (!this.FPConfig.reloadConfigValues()) {
            this.getLogger().info("Error reloading config!");
            return false;
        }
        // TODO: Handle perms and fly changes on reload

        this.getLogger().info("FlyPerms was successfully reloaded!");
        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("Disabled FlyPerms v" + this.getDescription().getVersion() + "!");
    }

    private void setLogLevel() {
        if (this.FPConfig.isDebugMode()) {
            this.getLogger().setLevel(Level.FINEST);
            this.getLogger().fine( "Logger set to finest.");
        } else {
            this.getLogger().setLevel(Level.INFO);
            this.getLogger().info("Logger set to info.");
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
}
