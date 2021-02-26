package dev.benergy10.flyperms.api;

import dev.benergy10.flyperms.Constants.FlyState;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Manages the application of various flight abilities of players.
 */
public interface FPFlightManager {
    /**
     *
     * @param player A bukkit {@link Player} entity.
     * @return The {@link FlyState} that was applied to the player.
     */
    @NotNull FlyState applyFlyState(@NotNull Player player);

    /**
     *
     * @param player A bukkit {@link Player} entity.
     * @param speed  Fly speed that the player wants to change to.
     * @return True if speed is successfully applied to the player.
     */
    boolean applyFlySpeed(@NotNull Player player, double speed);

    /**
     *
     * @param player A bukkit {@link Player} entity.
     * @return  True if fly is enabled for player.
     */
    boolean applyAutoFlyInAir(@NotNull Player player);
}
