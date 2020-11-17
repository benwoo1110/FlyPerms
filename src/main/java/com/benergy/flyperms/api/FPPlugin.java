package com.benergy.flyperms.api;

import com.benergy.flyperms.FlyPermsConfig;
import com.benergy.flyperms.permissions.FlyChecker;
import com.benergy.flyperms.permissions.PermissionTools;
import com.benergy.flyperms.utils.FlyCheckScheduler;
import org.bukkit.World;

public interface FPPlugin {
    boolean reload();

    boolean isIgnoreWorld(World world);

    FlyPermsConfig getFPConfig();

    FlyChecker getFlyChecker();

    FlyCheckScheduler getFlyCheckScheduler();
}
