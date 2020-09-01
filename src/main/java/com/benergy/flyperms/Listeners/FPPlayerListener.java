package com.benergy.flyperms.Listeners;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class FPPlayerListener implements Listener {
    private FlyPerms plugin;

    public FPPlayerListener(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(PlayerJoinEvent event) {
        event.getPlayer().setAllowFlight(true);
        this.plugin.getLog().info("[Join] Letting " + event.getPlayer().getName() + " fly.");

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChangeGameMode(PlayerGameModeChangeEvent event) {
        event.getPlayer().setAllowFlight(true);
        this.plugin.getLog().info("[GameMode] Letting " + event.getPlayer().getName() + " fly.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChangeWorld(PlayerChangedWorldEvent event) {
        event.getPlayer().setAllowFlight(true);
        this.plugin.getLog().info("[World] Letting " + event.getPlayer().getName() + " fly.");
    }
}