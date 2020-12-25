package dev.benergy10.flyperms.api;

import dev.benergy10.flyperms.Constants.FlyState;
import dev.benergy10.flyperms.checkers.GameModeChecker;
import dev.benergy10.flyperms.checkers.SpeedChecker;
import dev.benergy10.flyperms.checkers.WorldChecker;
import dev.benergy10.flyperms.utils.SpeedGroup;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Used for permission checking.
 */
public interface FPCheckManager {
    /**
     * Derive the {@link FlyState} the target is currently in.
     *
     * @param player A bukkit {@link Player} entity.
     * @return The {@link FlyState} that player should be in.
     */
    FlyState calculateFlyState(Player player);

    /**
     * Change speed for a given player, if allowed based on {@link SpeedGroup} permissions they have.
     *
     * @param player A bukkit {@link Player} entity.
     * @param speed  Target fly speed the player want to change to.
     * @return True if the player is allowed to change to the given speed.
     */
    boolean canChangeSpeedTo(Player player, double speed);

    /**
     * Gets the {@link SpeedChecker}.
     *
     * @return {@link Checker} for {@link SpeedGroup} object.
     */
    SpeedChecker getSpeedChecker();
    
    /**
     * Gets the {@link WorldChecker}.
     *
     * @return {@link PlayerChecker} for {@link World} object.
     */
    WorldChecker getWorldChecker();

    /**
     * Gets the {@link GameModeChecker}.
     *
     * @return {@link PlayerChecker} for {@link GameMode} object.
     */
    GameModeChecker getGameModeChecker();
}
