package com.benergy.flyperms;

import com.benergy.flyperms.Listeners.FPFlightListener;
import com.benergy.flyperms.Listeners.FPPlayerListener;
import com.benergy.flyperms.Listeners.FPWorldListener;
import com.benergy.flyperms.Permissions.FPPermissions;
import org.bukkit.World;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
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
