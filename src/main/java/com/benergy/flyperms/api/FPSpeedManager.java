package com.benergy.flyperms.api;

import com.benergy.flyperms.utils.SpeedGroup;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface FPSpeedManager {
    boolean applyFlySpeed(Player player, double speed);

    boolean hasGroup(String groupName);

    SpeedGroup getGroupOf(String groupName);

    Collection<SpeedGroup> getGroups();

    Collection<String> getGroupNames();
}
