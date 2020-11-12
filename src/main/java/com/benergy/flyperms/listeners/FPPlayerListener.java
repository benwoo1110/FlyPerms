package com.benergy.flyperms.listeners;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.permissions.FlyState;
import com.benergy.flyperms.utils.FPLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.logging.Level;

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
            FPLogger.log(Level.FINE,"Flight check ignored for " + event.getPlayer().getName() +
                    " at world " + event.getPlayer().getWorld().getName() + ".");
            return;
        }
        this.plugin.getFPFly().canFly(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void movement(PlayerMoveEvent event) {
        this.plugin.getFPFly().canFly(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerFly(PlayerToggleFlightEvent event) {
        if (!event.isFlying()) {
            FPLogger.log(Level.FINE,event.getPlayer().getName() + " stopped flying!");
            return;
        }

        FlyState flyCheckResult = this.plugin.getFPFly().canFly(event.getPlayer());
        switch (flyCheckResult) {
            case CREATIVE_BYPASS:
                FPLogger.log(Level.FINE,"Allowing creative flight for " + event.getPlayer().getName() + " as defined in config.");
                break;
            case IGNORED:
                FPLogger.log(Level.FINE,"Flight check ignored for " + event.getPlayer().getName() + " at world " + event.getPlayer().getWorld().getName() + ".");
                break;
            case NO:
                event.setCancelled(true);
                FPLogger.log(Level.FINE,"Flight canceled for " + event.getPlayer().getName() + "!");
                break;
            case YES:
                FPLogger.log(Level.FINE,"Starting flight for " + event.getPlayer().getName() + "...");
                break;
        }
    }
}
