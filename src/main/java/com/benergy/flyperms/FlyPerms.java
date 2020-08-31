package com.benergy.flyperms;

import com.benergy.flyperms.Listeners.FPFlightListener;
import com.benergy.flyperms.Listeners.FPPlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class FlyPerms extends JavaPlugin {

    // Listeners
    private final FPFlightListener flightListener = new FPFlightListener(this);
    private final FPPlayerListener playerListener = new FPPlayerListener(this);

    // Logger
    private final Logger log = Logger.getLogger(this.getName());

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.flightListener, this);
        pm.registerEvents(this.playerListener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Logger getLog() {
        return log;
    }
}
