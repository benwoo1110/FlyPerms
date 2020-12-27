package dev.benergy10.flyperms.listeners;

import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.utils.Logging;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerListener implements Listener {

    private final FlyPerms plugin;
    private final Set<UUID> scheduledPlayers;

    public PlayerListener(FlyPerms plugin) {
        this.plugin = plugin;
        scheduledPlayers = new HashSet<>();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void join(PlayerJoinEvent event) {
        doApplyFly("joined", event.getPlayer(), 2L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void changeGameMode(PlayerGameModeChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (this.plugin.getFPConfig().isResetSpeedOnGameModeChange()) {
            event.getPlayer().setFlySpeed((float) (this.plugin.getFPConfig().getResetSpeedValue() / 10));
        }

        doApplyFly("changed gamemode to " + event.getNewGameMode(), event.getPlayer(),2L);
    }

    /*
    * We check ignored world here so other plugins can potentially changed fly ability.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void changeWorldIgnoreCheck(PlayerChangedWorldEvent event) {
        if (this.plugin.getFPConfig().isIgnoreWorld(event.getPlayer().getWorld())) {
            event.getPlayer().setAllowFlight(false);
            Logging.debug("Flight check ignored for " + event.getPlayer().getName() +
                    " at world " + event.getPlayer().getWorld().getName() + ".");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void changeWorld(PlayerChangedWorldEvent event) {
        if (this.plugin.getFPConfig().isIgnoreWorld(event.getPlayer().getWorld())) {
            return;
        }

        if (this.plugin.getFPConfig().isResetSpeedOnWorldChange()) {
            event.getPlayer().setFlySpeed((float) (this.plugin.getFPConfig().getResetSpeedValue() / 10));
        }

        doApplyFly("changed world to '" + event.getPlayer().getWorld().getName() + "'", event.getPlayer(), 2L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void fly(PlayerToggleFlightEvent event) {
        if (!event.isFlying()) {
            Logging.debug(event.getPlayer().getName() + " stopped flying!");
            return;
        }

        switch (this.plugin.getFlightManager().applyFlyState(event.getPlayer())) {
            case CREATIVE_BYPASS:
                Logging.debug("Starting fly for " + event.getPlayer().getName()
                        + " due to creative bypass");
                break;
            case IGNORED:
                Logging.debug("Starting fly for " + event.getPlayer().getName()
                                + "as player in ignored world '" + event.getPlayer().getWorld().getName() + "'.");
                break;
            case NO:
                event.setCancelled(true);
                Logging.debug("Cancelled fly for " + event.getPlayer().getName() + ".");
                break;
            case YES:
                Logging.debug("Starting fly for " + event.getPlayer().getName() + ".");
                break;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void teleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getTo() == null) {
            Logging.warning(event.getPlayer().getName() + " teleport to a null location!");
            return;
        }

        World fromWorld = event.getFrom().getWorld();
        World toWorld = event.getTo().getWorld();

        if (fromWorld == null || toWorld == null) {
            Logging.warning(event.getPlayer().getName() + " teleport to/from a null world!");
            return;
        }

        if (!fromWorld.equals(toWorld)) {
            Logging.debug(event.getPlayer().getName() + " teleport to another world '" + toWorld.getName() + "', so fly handled by PlayerChangedWorldEvent.");
            return;
        }

        doApplyFly("teleported within the same world '"+ toWorld.getName() + "'", event.getPlayer(), 2L);
    }

    private void doApplyFly(String actionInfo, Player player, long delay) {
        if (scheduledPlayers.contains(player.getUniqueId())) {
            return;
        }

        scheduledPlayers.add(player.getUniqueId());
        Logging.debug("Schedule fly apply for " + actionInfo + ".");

        Bukkit.getScheduler().runTaskLater(
                this.plugin,
                () -> {
                    Logging.debug(player.getName() + " " + actionInfo + ". Fly state is now: " + this.plugin.getFlightManager().applyFlyState(player));
                    if (this.plugin.getFlightManager().applyAutoFlyInAir(player)) {
                        Logging.debug("Enabled fly after teleport to air for " + player.getName());
                    }

                    scheduledPlayers.remove(player.getUniqueId());
                },
                delay
        );
    }
}
