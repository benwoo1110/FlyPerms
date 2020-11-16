package com.benergy.flyperms.listeners;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.permissions.FlyState;
import com.benergy.flyperms.utils.FPLogger;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void join(PlayerJoinEvent event) {
        logFlyState("joined", event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void changeGameMode(PlayerGameModeChangeEvent event) {
        logFlyState("changed gamemode to " + event.getNewGameMode(), event.getPlayer(), event.getNewGameMode());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void changeWorld(PlayerChangedWorldEvent event) {
        if (this.plugin.isIgnoreWorld(event.getPlayer().getWorld())) {
            event.getPlayer().setAllowFlight(false);
            FPLogger.log(Level.FINE,"Flight check ignored for " + event.getPlayer().getName() +
                    " at world " + event.getPlayer().getWorld().getName() + ".");
            return;
        }

        logFlyState("changed world to '" + event.getPlayer().getWorld().getName() + "'", event.getPlayer());
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

    private void logFlyState(String actionInfo, Player player) {
        logFlyState(actionInfo, player, player.getGameMode());
    }

    private void logFlyState(String actionInfo, Player player, GameMode gameMode) {
        FPLogger.log(Level.FINE, player.getName() + " " + actionInfo +
                ". Fly state is now: " + this.plugin.getFPFly().canFly(player, gameMode));
    }
}
