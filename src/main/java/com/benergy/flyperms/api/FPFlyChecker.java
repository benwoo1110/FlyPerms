package com.benergy.flyperms.api;

import com.benergy.flyperms.Constants.FlyState;
import org.bukkit.entity.Player;

import java.util.List;

public interface FPFlyChecker {
    FlyState calculateFlyState(Player player);

    boolean baseAllow(Player player);

    List<String> allowInGameModes(Player player);

    List<String> allowInWorlds(Player player);
}
