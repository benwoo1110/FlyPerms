package dev.benergy10.flyperms.api;

import dev.benergy10.flyperms.utils.SpeedGroup;
import org.bukkit.World;

import java.util.Collection;

/**
 * Store all configuration data.
 */
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
