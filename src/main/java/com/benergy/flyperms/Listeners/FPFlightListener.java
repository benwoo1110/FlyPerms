package com.benergy.flyperms.Listeners;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.Permissions.FPPermissions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class FPFlightListener implements Listener {
    private FlyPerms plugin;

    public FPFlightListener(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerFly(PlayerToggleFlightEvent event) {
        this.plugin.getLog().info("Starting flight for " + event.getPlayer().getName() + "...");

        if (!this.plugin.getFPPerms().canFly(event.getPlayer())) {
            event.setCancelled(true);
            this.plugin.getLog().info("Flight canceled for " + event.getPlayer().getName() + "!");
        }
    }
}
