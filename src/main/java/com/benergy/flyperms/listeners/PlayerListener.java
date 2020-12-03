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

public class PlayerListener implements Listener {

    private final FlyPerms plugin;

    public PlayerListener(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void join(PlayerJoinEvent event) {
        doApplyFly("joined", event.getPlayer(), 1L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void changeGameMode(PlayerGameModeChangeEvent event) {
        doApplyFly("changed gamemode to " + event.getNewGameMode(), event.getPlayer(),1L);
    }

    /*
    * We check ignored world here so other plugins can potentially changed fly ability.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void changeWorldIgnoreCheck(PlayerChangedWorldEvent event) {
        if (this.plugin.getFPConfig().isIgnoreWorld(event.getPlayer().getWorld())) {
            event.getPlayer().setAllowFlight(false);
            Logging.log(Level.FINE,"Flight check ignored for " + event.getPlayer().getName() +
                    " at world " + event.getPlayer().getWorld().getName() + ".");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void changeWorld(PlayerChangedWorldEvent event) {
        if (this.plugin.getFPConfig().isIgnoreWorld(event.getPlayer().getWorld())) {
            return;
        }
        doApplyFly("changed world to '" + event.getPlayer().getWorld().getName() + "'", event.getPlayer(), 1L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void fly(PlayerToggleFlightEvent event) {
        if (!event.isFlying()) {
            Logging.log(Level.FINE,event.getPlayer().getName() + " stopped flying!");
            return;
        }

        switch (this.plugin.getFlightManager().applyFlyState(event.getPlayer())) {
            case CREATIVE_BYPASS:
                Logging.log(Level.FINE,"Starting fly for " + event.getPlayer().getName()
                        + " due to creative bypass");
                break;
            case IGNORED:
                Logging.log(Level.FINE, "Starting fly for " + event.getPlayer().getName()
                                + "as player in ignored world '" + event.getPlayer().getWorld().getName() + "'.");
                break;
            case NO:
                event.setCancelled(true);
                Logging.log(Level.FINE,"Cancelled fly for " + event.getPlayer().getName() + ".");
                break;
            case YES:
                Logging.log(Level.FINE,"Starting fly for " + event.getPlayer().getName() + ".");
                break;
        }
    }

    private void doApplyFly(String actionInfo, Player player, long delay) {
        Bukkit.getScheduler().runTaskLater(
                this.plugin,
                () -> Logging.log(Level.FINE, player.getName() + " " + actionInfo + ". Fly state is now: "
                        + this.plugin.getFlightManager().applyFlyState(player)),
                delay
        );
    }
}
