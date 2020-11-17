package com.benergy.flyperms.api;

import com.benergy.flyperms.utils.SpeedGroup;

import java.util.List;

public interface FPConfig {
    boolean reloadConfigValues();

    boolean loadConfigValues();

    boolean isCheckGameMode();

    boolean isCheckWorld();

    boolean isAllowCreative();

    int getCheckInterval();

    int getCoolDown();

    boolean haveDisabledWorld();

    List<String> getDisabledWorlds();

    List<SpeedGroup> getSpeedGroups();
}
