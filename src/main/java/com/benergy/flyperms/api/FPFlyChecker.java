package com.benergy.flyperms.api;

import com.benergy.flyperms.enums.FlyState;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public interface FPFlyChecker {
    FlyState canFly(Player player);

    FlyState canFly(Player player, GameMode gameMode);

    FlyState canFly(Player player, World world);

    FlyState canFly(Player player, GameMode gameMode, World world);

    boolean baseAllow(Player player);

    List<String> allowInGameModes(Player player);

    List<String> allowInWorlds(Player player);

    boolean canChangeSpeedTo(Player player, double speed);

    List<String> inSpeedGroups(Player player);
}
