package com.benergy.flyperms.listeners;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.permissions.FlyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.logging.Level;

public class FPFlightListener implements Listener {
    private final FlyPerms plugin;

    public FPFlightListener(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerFly(PlayerToggleFlightEvent event) {
        if (!event.isFlying()) {
            this.plugin.getLogger().log(Level.FINE,event.getPlayer().getName() + " stopped flying!");
            return;
        }

        FlyState flyCheckResult = this.plugin.getFPFly().canFly(event.getPlayer());
        switch (flyCheckResult) {
            case CREATIVE_BYPASS:
                this.plugin.getLogger().log(Level.FINE,"Allowing creative flight for " + event.getPlayer().getName() + " as defined in config.");
                break;
            case IGNORED:
                this.plugin.getLogger().log(Level.FINE,"Flight check ignored for " + event.getPlayer().getName() +
                        " at world " + event.getPlayer().getWorld().getName() + ".");
                break;
            case NO:
                event.setCancelled(true);
                this.plugin.getLogger().log(Level.FINE,"Flight canceled for " + event.getPlayer().getName() + "!");
                break;
            case YES:
                this.plugin.getLogger().log(Level.FINE,"Starting flight for " + event.getPlayer().getName() + "...");
                break;
        }
    }
}
