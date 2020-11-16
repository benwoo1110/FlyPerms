package com.benergy.flyperms.api;

import com.benergy.flyperms.enums.FlyState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;

public interface FPPerms {
    FlyState canFly(Player player);

    FlyState canFly(Player player, GameMode gameMode);

    boolean checkBasicAllow(Player player);

    List<String> checkAllGameModes(Player player);

    List<String> checkAllWorlds(Player player);

    boolean canChangeSpeedTo(Player player, double speed);

    List<String> inSpeedGroups(Player player);
}
