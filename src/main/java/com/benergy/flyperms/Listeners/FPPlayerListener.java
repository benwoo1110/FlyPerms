package com.benergy.flyperms.Listeners;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FPPlayerListener implements Listener {
    private final FlyPerms plugin;

    public FPPlayerListener(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(PlayerJoinEvent event) {
        // Ignore this world
        if (this.plugin.ignoreWorld(event.getPlayer().getWorld())) {
            return;
        }

        event.getPlayer().setAllowFlight(true);
        this.plugin.getLog().info("[Join] Letting " + event.getPlayer().getName() + " fly.");

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChangeGameMode(PlayerGameModeChangeEvent event) {
        // Ignore this world
        if (this.plugin.ignoreWorld(event.getPlayer().getWorld())) {
            event.getPlayer().setAllowFlight(false);
            return;
        }

        this.plugin.getFPPerms().canFly(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChangeWorld(PlayerChangedWorldEvent event) {
        // Ignore this world
        if (this.plugin.ignoreWorld(event.getPlayer().getWorld())) {
            event.getPlayer().setAllowFlight(false);
            return;
        }

        event.getPlayer().setAllowFlight(this.plugin.getFPPerms().canFly(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerMovement(PlayerMoveEvent event) {
        // Ignore this world
        if (this.plugin.ignoreWorld(event.getPlayer().getWorld())) {
            event.getPlayer().setAllowFlight(false);
            return;
        }

        this.plugin.getFPPerms().canFly(event.getPlayer());
    }
}