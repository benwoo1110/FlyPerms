package com.benergy.flyperms.listeners;

import com.benergy.flyperms.FlyPerms;
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
    public void join(PlayerJoinEvent event) {
        this.plugin.getFPFly().canFly(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void changeGameMode(PlayerGameModeChangeEvent event) {
        this.plugin.getFPFly().canFly(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChangeWorld(PlayerChangedWorldEvent event) {
        if (this.plugin.isIgnoreWorld(event.getPlayer().getWorld())) {
            event.getPlayer().setAllowFlight(false);
            return;
        }
        this.plugin.getFPFly().canFly(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void movement(PlayerMoveEvent event) {
        this.plugin.getFPFly().canFly(event.getPlayer());
    }
}