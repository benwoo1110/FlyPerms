package dev.benergy10.flyperms.listeners;

import dev.benergy10.flyperms.FlyPerms;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.jetbrains.annotations.NotNull;

public class WorldListener implements Listener {

    private final FlyPerms plugin;

    public WorldListener(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void worldLoaded(@NotNull WorldLoadEvent event) {
        if (this.plugin.getFPConfig().isIgnoreWorld(event.getWorld())) {
            return;
        }
        this.plugin.getPermissionTools().addWorldPerm(event.getWorld());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void worldUnloaded(@NotNull WorldUnloadEvent event) {
        if (event.isCancelled() || this.plugin.getFPConfig().isIgnoreWorld(event.getWorld())) {
            return;
        }
        this.plugin.getPermissionTools().removeWorldPerm(event.getWorld());
    }
}
