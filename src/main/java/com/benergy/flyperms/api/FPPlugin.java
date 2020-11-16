package com.benergy.flyperms.api;

import com.benergy.flyperms.FlyPermsConfig;
import com.benergy.flyperms.permissions.PermsFly;
import com.benergy.flyperms.permissions.PermsRegister;
import com.benergy.flyperms.utils.FlyCheckScheduler;
import org.bukkit.World;

public interface FPPlugin {
    boolean reload();

    boolean isIgnoreWorld(World world);

    FlyPermsConfig getFPConfig();

    PermsRegister getFPRegister();

    PermsFly getFPFly();

    FlyCheckScheduler getFlyCheckScheduler();
}
