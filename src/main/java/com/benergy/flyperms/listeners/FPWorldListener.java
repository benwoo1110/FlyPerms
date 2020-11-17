package com.benergy.flyperms.listeners;

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
        if (this.plugin.isIgnoreWorld(event.getWorld())) {
            return;
        }
        this.plugin.getPermissionTools().addWorldPerm(event.getWorld());
    }

    @EventHandler
    public void worldUnloaded(WorldUnloadEvent event) {
        if (this.plugin.isIgnoreWorld(event.getWorld())) {
            return;
        }
        this.plugin.getPermissionTools().removeWorldPerm(event.getWorld());
    }
}
