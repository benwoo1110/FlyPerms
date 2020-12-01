package com.benergy.flyperms.api;

import com.benergy.flyperms.FlyPermsConfig;
import com.benergy.flyperms.permissions.FlyChecker;
import com.benergy.flyperms.permissions.SpeedChecker;
import com.benergy.flyperms.utils.FlyApplyScheduler;
import com.benergy.flyperms.utils.FlyManager;
import com.benergy.flyperms.utils.SpeedManager;
import org.bukkit.World;

public interface FPPlugin {
    boolean reload();

    FlyPermsConfig getFPConfig();

    FlyChecker getFlyChecker();

    SpeedChecker getSpeedChecker();

    FlyApplyScheduler getFlyApplyScheduler();

    FlyManager getFlyManager();

    SpeedManager getSpeedManager();
}
