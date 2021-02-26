package dev.benergy10.flyperms.listeners;

import dev.benergy10.flyperms.Constants.FlyState;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.utils.Logging;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerListener implements Listener {

    private final FlyPerms plugin;
    private final Set<UUID> scheduledPlayers;

    public PlayerListener(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
        scheduledPlayers = new HashSet<>();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void join(@NotNull PlayerJoinEvent event) {
        doApplyFly("joined", event.getPlayer(), 2L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void changeGameMode(@NotNull PlayerGameModeChangeEvent event) {
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
    public void changeWorldIgnoreCheck(@NotNull PlayerChangedWorldEvent event) {
        if (!this.plugin.getFPConfig().isIgnoreWorld(event.getPlayer().getWorld())) {
            return;
        }
        
        Player player = event.getPlayer();
        player.setAllowFlight(false);
        Logging.debug("Flight check ignored for %s at world %s.", player.getName(), player.getWorld().getName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void changeWorld(@NotNull PlayerChangedWorldEvent event) {
        if (this.plugin.getFPConfig().isIgnoreWorld(event.getPlayer().getWorld())) {
            return;
        }

        if (this.plugin.getFPConfig().isResetSpeedOnWorldChange()) {
            event.getPlayer().setFlySpeed((float) (this.plugin.getFPConfig().getResetSpeedValue() / 10));
        }

        doApplyFly("changed world to '" + event.getPlayer().getWorld().getName() + "'", event.getPlayer(), 2L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void fly(@NotNull PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (!event.isFlying()) {
            Logging.debug(player.getName() + " stopped flying!");
            return;
        }

        switch (this.plugin.getFlightManager().applyFlyState(event.getPlayer())) {
            case CREATIVE_BYPASS:
                Logging.debug("Starting fly for %s due to creative bypass.", player.getName());
                break;
            case IGNORED:
                Logging.debug("Starting fly for %s as player in ignored world '%s'.",
                        player.getName(), player.getWorld().getName());
                break;
            case NO:
                event.setCancelled(true);
                Logging.debug("Cancelled fly for %s.", player.getName());
                break;
            case YES:
                Logging.debug("Starting fly for %s.", player.getName());
                break;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void teleport(@NotNull PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        // Should not happen unless some nasty plugin set it to null.
        if (event.getTo() == null) {
            Logging.warning( "%s teleported to a null location!", player.getName());
            return;
        }

        World fromWorld = event.getFrom().getWorld();
        World toWorld = event.getTo().getWorld();

        if (fromWorld == null || toWorld == null) {
            Logging.warning("%s teleport to/from a null world!", player.getName());
            return;
        }

        if (!fromWorld.equals(toWorld)) {
            Logging.debug("%s teleport to another world '%s', so fly handled by PlayerChangedWorldEvent.",
                    player.getName(), toWorld.getName());
            return;
        }

        doApplyFly("teleported within the same world '"+ toWorld.getName() + "'", player, 2L);
    }

    private void doApplyFly(@NotNull String actionInfo,
                            @NotNull Player player,
                            long delay) {

        if (scheduledPlayers.contains(player.getUniqueId())) {
            return;
        }

        scheduledPlayers.add(player.getUniqueId());
        Logging.debug("Schedule fly apply for %s.", actionInfo);

        Bukkit.getScheduler().runTaskLater(
                this.plugin,
                () -> {
                    FlyState appliedState = this.plugin.getFlightManager().applyFlyState(player);
                    Logging.debug("%s %s. Fly state is now: %s", player.getName(), actionInfo, appliedState);

                    if (this.plugin.getFlightManager().applyAutoFlyInAir(player)) {
                        Logging.debug("Enabled fly after teleport to air for %s.", player.getName());
                    }

                    scheduledPlayers.remove(player.getUniqueId());
                },
                delay
        );
    }
}
