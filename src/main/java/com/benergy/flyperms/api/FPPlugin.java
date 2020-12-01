package com.benergy.flyperms.api;

import com.benergy.flyperms.FlyPermsConfig;
import com.benergy.flyperms.check_old.FlyChecker;
import com.benergy.flyperms.check_old.SpeedChecker;
import com.benergy.flyperms.utils.FlyApplyScheduler;
import com.benergy.flyperms.utils.FlightManager;
import com.benergy.flyperms.utils.SpeedManager;

public interface FPPlugin {
    boolean reload();

    FlyPermsConfig getFPConfig();

    FlyChecker getFlyChecker();

    SpeedChecker getSpeedChecker();

    FlyApplyScheduler getFlyApplyScheduler();

    FlightManager getFlyManager();

    SpeedManager getSpeedManager();
}
