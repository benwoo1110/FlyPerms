package com.benergy.flyperms.api;

import com.benergy.flyperms.Constants.FlyState;
import com.benergy.flyperms.checkers.GameModeChecker;
import com.benergy.flyperms.checkers.SpeedChecker;
import com.benergy.flyperms.checkers.WorldChecker;
import org.bukkit.entity.Player;

public interface FPCheckManager {
    FlyState calculateFlyState(Player player);

    boolean canChangeSpeedTo(Player player, double speed);

    SpeedChecker getSpeedChecker();

    WorldChecker getWorldChecker();

    GameModeChecker getGameModeChecker();
}
