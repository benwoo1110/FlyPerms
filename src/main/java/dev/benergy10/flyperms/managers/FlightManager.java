package dev.benergy10.flyperms.managers;

import dev.benergy10.flyperms.constants.FlyState;
import dev.benergy10.flyperms.constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.FPFlightManager;
import dev.benergy10.flyperms.api.MessageProvider;
import dev.benergy10.flyperms.utils.Formatter;
import dev.benergy10.minecrafttools.utils.Logging;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
public class FlightManager implements FPFlightManager {

    private final FlyPerms plugin;
    private final MessageProvider messager;
    private final Set<UUID> playersToStopFly;

    private static final int SPEED_MODIFIER = 10;

    public FlightManager(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
        this.messager = plugin.getMessageProvider();
        this. playersToStopFly = new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    public @NotNull FlyState applyFlyState(@NotNull Player player) {
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
    private void modifyFlyAbility(@NotNull Player player,
                                  @NotNull FlyState state) {

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
    private void stopFly(@NotNull Player player) {
        if (this.playersToStopFly.contains(player.getUniqueId())) {
            return;
        }

        this.messager.send(player, MessageKey.FLY_ABILITY_LOST);

        if (!player.isFlying()) {
            player.setAllowFlight(false);
            Logging.debug("Disallowed flight ability for %s.", player.getName());
            return;
        }

        this.playersToStopFly.add(player.getUniqueId());

        int coolDown = this.plugin.getFPConfig().getCoolDown();
        if (coolDown <= 0) {
            stopFlyRunnable(player).run();
            return;
        }

        Logging.debug("Cool down of %s before drop.", coolDown);
        this.messager.send(player, MessageKey.FLY_DROP_COOLDOWN, Formatter.millisecondsToSeconds(coolDown));
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
    private @NotNull Runnable stopFlyRunnable(@NotNull Player player) {
        return () -> {
            Logging.debug("Running scheduled stop fly for " + player.getName());
            if (!player.isOnline()
                    || !player.isFlying()
                    || !this.plugin.getCheckManager().calculateFlyState(player).equals(FlyState.NO)) {
                Logging.debug("Stop fly for %s aborted!", player.getName());
                return;
            }

            player.setFlying(false);
            player.setAllowFlight(false);
            this.playersToStopFly.remove(player.getUniqueId());

            this.messager.send(player, MessageKey.FLY_DROP_NOW);
            Logging.debug("Disallowed flight for %s.", player.getName());
        };
    }

    /**
     * {@inheritDoc}
     */
    public boolean applyFlySpeed(@NotNull Player player,
                                 double speed) {

        if (!this.plugin.getCheckManager().canChangeSpeedTo(player, speed)) {
            return false;
        }

        player.setFlySpeed((float) speed / SPEED_MODIFIER);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean applyAutoFlyInAir(@NotNull Player player) {
        if (this.plugin.getFPConfig().isAutoFlyOnAirTeleport()
                && player.getAllowFlight()
                && !player.isFlying()
                && playerInAir(player)) {

            player.setFlying(true);
            Logging.info("Auto flight applied to %s.", player.getName());
            return true;
        }

        return false;
    }

    /**
     * Check if player is in air.
     *
     * @param player    Target player to check.
     * @return True if player is in air, otherwise false.
     */
    private boolean playerInAir(@NotNull Player player) {
        Location location = player.getLocation();
        World world = location.getWorld();
        if (world == null) {
            Logging.warning("%s is in null world for some reason!", player.getName());
            return false;
        }

        return world.getBlockAt(location.getBlockX(), location.getBlockY() - 1, location.getBlockZ())
                .getBlockData()
                .getMaterial()
                .isAir();
    }
}
