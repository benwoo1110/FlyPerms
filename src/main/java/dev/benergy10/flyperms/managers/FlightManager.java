package dev.benergy10.flyperms.managers;

import dev.benergy10.flyperms.Constants.FlyState;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.FPFlightManager;
import dev.benergy10.flyperms.utils.Formatter;
import dev.benergy10.flyperms.utils.Logging;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
public class FlightManager implements FPFlightManager {

    private final FlyPerms plugin;
    private final Set<UUID> playersToStopFly;

    private static final int SPEED_MODIFIER = 10;

    public FlightManager(FlyPerms plugin) {
        this.plugin = plugin;
        this. playersToStopFly = new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    public FlyState applyFlyState(Player player) {
        FlyState state = this.plugin.getCheckManager().calculateFlyState(player);
        modifyFlyAbility(player, state);
        return state;
    }

    /**
     * Actually change the player's fly attributes.
     *
     * @param player A bukkit {@link Player} entity.
     * @param state  {@link FlyState} to change to.
     */
    private void modifyFlyAbility(Player player, FlyState state) {
        switch (state) {
            case SPECTATOR:
                if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    Logging.debug( "%s in spectator mode.", player.getName());
                }
                break;
            case CREATIVE_BYPASS:
                if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    Logging.debug("Allowed flight ability for due to creative bypass.", player.getName());
                }
                break;
            case YES:
                if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    Logging.debug("Allowed flight ability for %s.", player.getName());
                }
                break;
            case NO:
                if (player.getAllowFlight() || player.isFlying()) {
                    stopFly(player);
                }
                break;
        }
    }

    /**
     * Schedule to stop fly after cooldown defined in config.
     *
     * @param player A bukkit {@link Player} entity.
     */
    private void stopFly(Player player) {
        if (!player.isFlying()) {
            player.setAllowFlight(false);
            Logging.debug("Disallowed flight ability for %s.", player.getName());
            return;
        }

        if (this.playersToStopFly.contains(player.getUniqueId())) {
            return;
        }
        this.playersToStopFly.add(player.getUniqueId());

        int coolDown = this.plugin.getFPConfig().getCoolDown();
        player.sendMessage("You have lost your ability to fly. Dropping in " + Formatter.millisecondsToSeconds(coolDown) + "sec...");
        this.plugin.getServer().getScheduler().runTaskLater(
                this.plugin,
                stopFlyRunnable(player),
                Formatter.millisecondsToTicks(coolDown)
        );
    }

    /**
     * Stop fly for a player when they lost the permission to fly.
     *
     * @param player A bukkit {@link Player} entity.
     * @return Runnable to stop fly for a player.
     */
    private Runnable stopFlyRunnable(Player player) {
        return () -> {
            Logging.debug("Running scheduled stop fly for " + player.getName());
            if (!player.isOnline()
                    || !player.isFlying()
                    || !this.plugin.getCheckManager().calculateFlyState(player).equals(FlyState.NO)) {
                Logging.debug("Stop fly for  aborted!", player.getName());
                return;
            }

            player.sendMessage("Dropping now!");
            player.setFlying(false);
            player.setAllowFlight(false);

            this.playersToStopFly.remove(player.getUniqueId());
            Logging.debug("Disallowed flight for %s after cooldown.", player.getName());
        };
    }

    /**
     * {@inheritDoc}
     */
    public boolean applyFlySpeed(Player player, double speed) {
        if (!this.plugin.getCheckManager().canChangeSpeedTo(player, speed)) {
            return false;
        }

        player.setFlySpeed((float) speed / SPEED_MODIFIER);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean applyAutoFlyInAir(Player player) {
        if (this.plugin.getFPConfig().isAutoFlyOnAirTeleport()
                && player.getAllowFlight()
                && !player.isFlying()
                && playerInAir(player)) {
            player.setFlying(true);
            return true;
        }

        return false;
    }

    private boolean playerInAir(Player player) {
        return player.getLocation().getBlock().getRelative(BlockFace.DOWN).isEmpty();
    }
}
