package com.benergy.flyperms.api;

import com.benergy.flyperms.utils.SpeedGroup;
import org.bukkit.World;

import java.util.Collection;

public interface FPConfig {
    boolean isCheckGameMode();

    boolean isCheckWorld();

    boolean isAllowCreative();

    int getCheckInterval();

    int getCoolDown();

    boolean isAutoFlyOnAirTeleport();

    Collection<SpeedGroup> getSpeedGroups();

    boolean hasSpeedGroup(String groupName);

    SpeedGroup getSpeedGroupOf(String groupName);

    Collection<String> getSpeedGroupNames();

    boolean isResetSpeedOnWorldChange();

    boolean isResetSpeedOnGameModeChange();

    double getResetSpeedValue();

    Collection<String> getIgnoreWorlds();

    boolean isIgnoreWorld(World world);

    boolean isIgnoreWorld(String worldName);

    boolean haveIgnoreWorld();

    boolean isHookPapi();

    boolean isDebugMode();
}
