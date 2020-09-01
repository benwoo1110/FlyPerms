package com.benergy.flyperms.Listeners;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class FPWorldListener implements Listener {
    private final FlyPerms plugin;

    public FPWorldListener(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void worldLoaded(WorldLoadEvent event) {
        // Ignore this world
        if (this.plugin.ignoreWorld(event.getWorld())) {
            return;
        }

        // Register new world perms
        this.plugin.getFPPerms().addWorldPerm(event.getWorld());
    }

    @EventHandler
    public void worldUnloaded(WorldUnloadEvent event) {
        // Ignore this world
        if (this.plugin.ignoreWorld(event.getWorld())) {
            return;
        }

        // Remove perms when world unloads
        this.plugin.getFPPerms().removeWorldPerm(event.getWorld());
    }
}
