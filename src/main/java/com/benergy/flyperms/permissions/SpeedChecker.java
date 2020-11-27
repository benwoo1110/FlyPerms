package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.api.FPSpeedChecker;
import com.benergy.flyperms.enums.Permissions;
import com.benergy.flyperms.utils.SpeedGroup;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SpeedChecker extends Checker implements FPSpeedChecker {

    public SpeedChecker(FlyPerms plugin) {
        super(plugin);
    }

    public boolean canChangeSpeedTo(Player player, double speed) {
        return this.plugin.getFPConfig()
                .getSpeedGroups()
                .stream()
                .anyMatch(group -> checkSpeedGroup(player, group) && group.isInRange(speed));
    }

    public List<String> inSpeedGroups(Player player) {
        return this.plugin.getFPConfig()
                .getSpeedGroups()
                .stream()
                .filter(group -> checkSpeedGroup(player, group))
                .map(SpeedGroup::getName)
                .collect(Collectors.toList());
    }

    public boolean checkSpeedGroup(Player player, SpeedGroup group) {
        return checkSpeedGroup(player, group.getName());
    }

    public boolean checkSpeedGroup(Player player, String groupName) {
        return player.hasPermission(Permissions.SPEED_GROUP + groupName);
    }
}
