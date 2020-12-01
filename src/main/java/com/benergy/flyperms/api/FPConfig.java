package com.benergy.flyperms.api;

import com.benergy.flyperms.utils.SpeedGroup;

import java.util.Collection;
import java.util.Map;

public interface FPConfig {
    boolean isCheckGameMode();

    boolean isCheckWorld();

    boolean isAllowCreative();

    int getCheckInterval();

    int getCoolDown();

    Collection<String> getIgnoreWorlds();

    Map<String, SpeedGroup> getSpeedGroups();
}
