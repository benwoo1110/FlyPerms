package com.benergy.flyperms.api;

import com.benergy.flyperms.enums.FlyState;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public interface FPFlyChecker {
    FlyState calculateFlyState(Player player);

    FlyState calculateFlyState(Player player, GameMode gameMode);

    FlyState calculateFlyState(Player player, World world);

    FlyState calculateFlyState(Player player, GameMode gameMode, World world);

    boolean baseAllow(Player player);

    List<String> allowInGameModes(Player player);

    List<String> allowInWorlds(Player player);
}
