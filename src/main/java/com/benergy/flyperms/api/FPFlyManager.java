package com.benergy.flyperms.api;

import com.benergy.flyperms.Constants.FlyState;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface FPFlyManager {
    FlyState applyFlyState(Player player);

    boolean isIgnoreWorld(World world);

    boolean haveIgnoreWorld();
}
