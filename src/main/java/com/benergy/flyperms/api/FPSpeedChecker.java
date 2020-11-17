package com.benergy.flyperms.api;

import org.bukkit.entity.Player;

import java.util.List;

public interface FPSpeedChecker {
    boolean canChangeSpeedTo(Player player, double speed);

    List<String> inSpeedGroups(Player player);
}
