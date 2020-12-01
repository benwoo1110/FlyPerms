package com.benergy.flyperms.api;

import com.benergy.flyperms.Constants.FlyState;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public interface FPFlyChecker {
    FlyState calculateFlyState(Player player);

    boolean baseAllow(Player player);

    List<String> allowInGameModes(Player player);

    List<String> allowInWorlds(Player player);

    Boolean hasGameModePerm(Player player);

    Boolean hasGameModePerm(Player player, String modeName);

    Boolean hasGameModePerm(Player player, GameMode gameMode);

    Boolean hasWorldPerm(Player player);

    Boolean hasWorldPerm(Player player, World world);
}
