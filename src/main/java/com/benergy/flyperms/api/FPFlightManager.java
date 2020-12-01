package com.benergy.flyperms.api;

import com.benergy.flyperms.Constants.FlyState;
import org.bukkit.entity.Player;

public interface FPFlightManager {
    FlyState applyFlyState(Player player);

    boolean applyFlySpeed(Player player, double speed);
}
