package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.api.FPSpeedChecker;
import com.benergy.flyperms.utils.SpeedGroup;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SpeedChecker implements FPSpeedChecker {

    private final FlyPerms plugin;

    public SpeedChecker(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public boolean canChangeSpeedTo(Player player, double speed) {
        return this.plugin.getFPConfig().getSpeedGroups()
                .stream()
                .anyMatch(group -> player.hasPermission(group.getPermission()) && group.isInRange(speed));
    }

    public List<String> inSpeedGroups(Player player) {
        return this.plugin.getFPConfig().getSpeedGroups()
                .stream()
                .filter(group -> player.hasPermission(group.getPermission()))
                .map(SpeedGroup::getName)
                .collect(Collectors.toList());
    }
}
