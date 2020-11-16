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
        logFlyState(event.getPlayer().getName() + " changed joined.", this.plugin.getFPFly().canFly(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void changeGameMode(PlayerGameModeChangeEvent event) {
        logFlyState(event.getPlayer().getName() + " changed gamemode.", this.plugin.getFPFly().canFly(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void changeWorld(PlayerChangedWorldEvent event) {
        FPLogger.log(Level.FINEST, event.getPlayer().getName() + " changed from world '" + event.getFrom().getName() +
                "' to world '" + event.getPlayer().getWorld().getName() + "'");

        if (this.plugin.isIgnoreWorld(event.getPlayer().getWorld())) {
            event.getPlayer().setAllowFlight(false);
            FPLogger.log(Level.FINE,"Flight check ignored for " + event.getPlayer().getName() +
                    " at world " + event.getPlayer().getWorld().getName() + ".");
            return;
        }

        logFlyState(event.getPlayer().getName() + " changed world.", this.plugin.getFPFly().canFly(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void fly(PlayerToggleFlightEvent event) {
        if (!event.isFlying()) {
            FPLogger.log(Level.FINE,event.getPlayer().getName() + " stopped flying!");
            return;
        }

        switch (this.plugin.getFPFly().canFly(event.getPlayer())) {
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

    private void logFlyState(String actionInfo, FlyState flyState) {
        FPLogger.log(Level.FINE, actionInfo + " Fly state is now: " + flyState.toString());
    }
}
