package com.benergy.flyperms.api;

import com.benergy.flyperms.utils.SpeedGroup;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface FPSpeedChecker {
    boolean canChangeSpeedTo(Player player, double speed);

    Collection<String> inSpeedGroups(Player player);

    Boolean hasSpeedGroupPerm(Player player, String groupName);

    Boolean hasSpeedGroupPerm(Player player, SpeedGroup group);
}
