package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.api.FPSpeedChecker;
import com.benergy.flyperms.Constants.Permissions;
import com.benergy.flyperms.utils.SpeedGroup;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class SpeedChecker extends Checker implements FPSpeedChecker {

    public SpeedChecker(FlyPerms plugin) {
        super(plugin);
    }

    public boolean canChangeSpeedTo(Player player, double speed) {
        return this.plugin.getFPConfig()
                .getSpeedGroups()
                .stream()
                .anyMatch(group -> hasSpeedGroupPerm(player, group) && group.isInRange(speed));
    }

    public Collection<String> inSpeedGroups(Player player) {
        return Collections.unmodifiableList(this.plugin.getFPConfig().getSpeedGroups()
                .stream()
                .filter(group -> hasSpeedGroupPerm(player, group))
                .map(SpeedGroup::getName)
                .collect(Collectors.toList()));
    }

    public boolean hasSpeedGroupPerm(Player player, SpeedGroup group) {
        return hasSpeedGroupPerm(player, group.getName());
    }

    public boolean hasSpeedGroupPerm(Player player, String groupName) {
        return player.hasPermission(Permissions.SPEED_GROUP + groupName);
    }
}
