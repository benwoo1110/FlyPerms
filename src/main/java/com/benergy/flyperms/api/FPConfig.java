package com.benergy.flyperms.api;

import com.benergy.flyperms.utils.SpeedGroup;

import java.util.Collection;
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

    Collection<String> getDisabledWorlds();

    Collection<SpeedGroup> getSpeedGroups();

    Collection<String> getSpeedGroupNames();
}
