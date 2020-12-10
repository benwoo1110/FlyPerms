package com.benergy.flyperms.api;

import com.benergy.flyperms.Constants.FlyState;
import com.benergy.flyperms.checkers.GameModeChecker;
import com.benergy.flyperms.checkers.SpeedChecker;
import com.benergy.flyperms.checkers.WorldChecker;
import org.bukkit.entity.Player;

/**
 * Used for permission checking.
 */
public interface FPCheckManager {
    /**
     *
     * @param player A bukkit {@link Player} entity.
     * @return The {@link FlyState} that player should be in.
     */
    FlyState calculateFlyState(Player player);

    /**
     *
     * @param player A bukkit {@link Player} entity.
     * @param speed  Target fly speed the player want to change to.
     * @return True if the player is allowed to change to the given speed.
     */
    boolean canChangeSpeedTo(Player player, double speed);

    /**
     *
     * @return {@link Checker} for {@link com.benergy.flyperms.utils.SpeedGroup} object.
     */
    SpeedChecker getSpeedChecker();

    /**
     *
     * @return {@link PlayerChecker} for {@link org.bukkit.World} object.
     */
    WorldChecker getWorldChecker();

    /**
     *
     * @return {@link PlayerChecker} for {@link org.bukkit.GameMode} object.
     */
    GameModeChecker getGameModeChecker();
}
