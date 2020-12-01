package com.benergy.flyperms.api;

import com.benergy.flyperms.FlyPermsConfig;
import com.benergy.flyperms.utils.FlyApplyScheduler;
import com.benergy.flyperms.utils.FlightManager;

public interface FPPlugin {
    FlyPermsConfig getFPConfig();

    FlyApplyScheduler getFlyApplyScheduler();

    FlightManager getFlyManager();
}
