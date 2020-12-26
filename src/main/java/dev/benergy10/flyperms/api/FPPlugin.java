package dev.benergy10.flyperms.api;

import dev.benergy10.flyperms.FlyPermsConfig;
import dev.benergy10.flyperms.utils.CheckManager;
import dev.benergy10.flyperms.utils.FlyApplyScheduler;
import dev.benergy10.flyperms.utils.FlightManager;
import dev.benergy10.flyperms.utils.PermissionTools;

/**
 * FlyPerms plugin.
 */
public interface FPPlugin {
    /**
     * Reloads the plugin.
     *
     * @return True if reload is successful, false otherwise.
     */
    boolean reload();

    /**
     * Gets class used to register permissions.
     *
     * @return {@link PermissionTools}.
     */
    PermissionTools getPermissionTools();

    /**
     * Gets class where all to config options are stored.
     *
     * @return {@link FPConfig}.
     */
    FlyPermsConfig getFPConfig();

    /**
     * Gets scheduler class that is used to
     *
     * @return {@link FPScheduler}.
     */
    FlyApplyScheduler getFlyApplyScheduler();

    /**
     * Gets main class that is used to manager the applying of the various fly abilities.
     *
     * @return {@link FPFlightManager}.
     */
    FlightManager getFlightManager();

    /**
     * Gets the check class that is responsible for calculating fly properties of player based on their permissions.
     *
     * @return {@link FPCheckManager}.
     */
    CheckManager getCheckManager();
}
