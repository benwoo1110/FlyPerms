package com.benergy.flyperms.Listeners;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class FPFlightListener implements Listener {
    private FlyPerms plugin;

    public FPFlightListener(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerFly(PlayerToggleFlightEvent event) {
        this.plugin.getLog().info("Starting flight");
        if (!event.getPlayer().hasPermission("flyperms.allow")) {
            event.setCancelled(true);
        }
    }
}
