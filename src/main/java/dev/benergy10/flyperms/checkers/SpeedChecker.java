package dev.benergy10.flyperms.checkers;

import dev.benergy10.flyperms.Constants.Permissions;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.Checker;
import dev.benergy10.flyperms.utils.SpeedGroup;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SpeedChecker implements Checker<SpeedGroup> {

    private final FlyPerms plugin;

    public SpeedChecker(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public List<SpeedGroup> getAllowed(Player player) {
        return this.plugin.getFPConfig()
                .getSpeedGroups()
                .stream()
                .filter(group -> hasPerm(player, group))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllowedNames(Player player) {
        return this.plugin.getFPConfig()
                .getSpeedGroups()
                .stream()
                .filter(group -> hasPerm(player, group))
                .map(SpeedGroup::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean hasPerm(Player player, String groupName) {
        SpeedGroup targetGroup = this.plugin.getFPConfig().getSpeedGroupOf(groupName);
        if (targetGroup == null) {
            return null;
        }

        return hasPerm(player, targetGroup);
    }

    @Override
    public Boolean hasPerm(Player player, SpeedGroup group) {
        if (group == null) {
            return null;
        }

        return player.hasPermission(Permissions.SPEED_GROUP + group.getName());
    }
}
