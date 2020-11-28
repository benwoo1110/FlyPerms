package com.benergy.flyperms.listeners;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.utils.Logging;
import org.bukkit.Bukkit;
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
        doFlyCheck("joined", event.getPlayer(), 1L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void changeGameMode(PlayerGameModeChangeEvent event) {
        doFlyCheck("changed gamemode to " + event.getNewGameMode(), event.getPlayer(),1L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void changeWorld(PlayerChangedWorldEvent event) {
        if (this.plugin.isIgnoreWorld(event.getPlayer().getWorld())) {
            event.getPlayer().setAllowFlight(false);
            Logging.log(Level.FINE,"Flight check ignored for " + event.getPlayer().getName() +
                    " at world " + event.getPlayer().getWorld().getName() + ".");
            return;
        }

        doFlyCheck("changed world to '" + event.getPlayer().getWorld().getName() + "'", event.getPlayer(), 1L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void fly(PlayerToggleFlightEvent event) {
        if (!event.isFlying()) {
            Logging.log(Level.FINE,event.getPlayer().getName() + " stopped flying!");
            return;
        }

        switch (this.plugin.getFlyManager().applyFlyState(event.getPlayer())) {
            case CREATIVE_BYPASS:
                Logging.log(Level.FINE,"Allowing creative flight for " + event.getPlayer().getName() + " as defined in config.");
                break;
            case IGNORED:
                Logging.log(Level.FINE,"Flight check ignored for " + event.getPlayer().getName() + " at world " + event.getPlayer().getWorld().getName() + ".");
                break;
            case NO:
                event.setCancelled(true);
                Logging.log(Level.FINE,"Flight canceled for " + event.getPlayer().getName() + "!");
                break;
            case YES:
                Logging.log(Level.FINE,"Starting flight for " + event.getPlayer().getName() + "...");
                break;
        }
    }

    private void doFlyCheck(String actionInfo, Player player, long delay) {
        Bukkit.getScheduler().runTaskLater(
                this.plugin,
                () -> Logging.log(Level.FINE, player.getName() + " " + actionInfo + ". Fly state is now: "
                        + this.plugin.getFlyManager().applyFlyState(player)),
                delay
        );
    }
}
