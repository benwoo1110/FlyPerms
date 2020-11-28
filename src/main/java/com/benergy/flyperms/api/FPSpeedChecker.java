package com.benergy.flyperms.api;

import org.bukkit.entity.Player;

import java.util.Collection;

public interface FPSpeedChecker {
    boolean canChangeSpeedTo(Player player, double speed);

    Collection<String> inSpeedGroups(Player player);
}
