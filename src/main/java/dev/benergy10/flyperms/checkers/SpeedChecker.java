package dev.benergy10.flyperms.checkers;

import dev.benergy10.flyperms.utils.ConfigOptions;
import dev.benergy10.flyperms.constants.Permissions;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.Checker;
import dev.benergy10.flyperms.utils.SpeedGroup;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class SpeedChecker implements Checker<SpeedGroup> {

    private final FlyPerms plugin;

    public SpeedChecker(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<SpeedGroup> getAllowed(@NotNull Player player) {
        return this.plugin.getFPConfig()
                .getValue(ConfigOptions.SPEED_GROUPS)
                .stream()
                .filter(group -> hasPerm(player, group))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> getAllowedNames(@NotNull Player player) {
        return this.plugin.getFPConfig()
                .getValue(ConfigOptions.SPEED_GROUPS)
                .stream()
                .filter(group -> hasPerm(player, group))
                .map(SpeedGroup::getName)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean hasPerm(@NotNull Player player, String groupName) {
        SpeedGroup targetGroup = this.plugin.getFPConfig().getValue(ConfigOptions.SPEED_GROUPS).stream()
                .filter(speedGroup -> speedGroup.getName().equalsIgnoreCase(groupName))
                .findFirst()
                .orElse(null);

        if (targetGroup == null) {
            return null;
        }
        return hasPerm(player, targetGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean hasPerm(@NotNull Player player, SpeedGroup group) {
        if (group == null) {
            return null;
        }

        return player.hasPermission(Permissions.SPEED_GROUP + group.getName());
    }
}
