package com.benergy.flyperms.utils;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.entity.Player;

import java.util.Collection;

public class SpeedManager {

    private final FlyPerms plugin;
    private static final int SPEED_MODIFIER = 10;

    public SpeedManager(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public boolean applyFlySpeed(Player player, double speed) {
        if (!this.plugin.getSpeedChecker().canChangeSpeedTo(player, speed)) {
            return false;
        }

        player.setFlySpeed((float) speed / SPEED_MODIFIER);
        return true;
    }

    public boolean hasGroup(String groupName) {
        return this.plugin.getFPConfig().getSpeedGroups().containsKey(groupName);
    }

    public SpeedGroup getGroupOf(String groupName) {
        return this.plugin.getFPConfig().getSpeedGroups().get(groupName);
    }

    public Collection<SpeedGroup> getGroups() {
        return this.plugin.getFPConfig().getSpeedGroups().values();
    }

    public Collection<String> getGroupNames() {
        return this.plugin.getFPConfig().getSpeedGroups().keySet();
    }
}
