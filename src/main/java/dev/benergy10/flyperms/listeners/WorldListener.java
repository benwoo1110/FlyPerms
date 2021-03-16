package dev.benergy10.flyperms.listeners;

import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.configuration.ConfigOptions;
import org.bukkit.World;
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
        World world = event.getWorld();
        if (!this.plugin.getFPConfig().getValue(ConfigOptions.IGNORE_WORLDS).contains(world.getName())) {
            return;
        }
        this.plugin.getPermissionTools().addWorldPerm(world);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void worldUnloaded(@NotNull WorldUnloadEvent event) {
        World world = event.getWorld();
        if (!this.plugin.getFPConfig().getValue(ConfigOptions.IGNORE_WORLDS).contains(world.getName())) {
            return;
        }
        this.plugin.getPermissionTools().removeWorldPerm(world);
    }
}
