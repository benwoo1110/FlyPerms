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
        return this.plugin.getSpeedManager()
                .getGroups()
                .stream()
                .anyMatch(group -> hasSpeedGroupPerm(player, group) && group.isInRange(speed));
    }

    public Collection<String> inSpeedGroups(Player player) {
        return Collections.unmodifiableList(this.plugin.getSpeedManager()
                .getGroups()
                .stream()
                .filter(group -> hasSpeedGroupPerm(player, group))
                .map(SpeedGroup::getName)
                .collect(Collectors.toList()));
    }

    public Boolean hasSpeedGroupPerm(Player player, String groupName) {
        SpeedGroup targetGroup = this.plugin.getSpeedManager().getGroupOf(groupName);
        if (targetGroup == null) {
            return null;
        }

        return hasSpeedGroupPerm(player, targetGroup);
    }

    public Boolean hasSpeedGroupPerm(Player player, SpeedGroup group) {
        if (group == null) {
            return null;
        }

        return player.hasPermission(Permissions.SPEED_GROUP + group.getName());
    }
}
