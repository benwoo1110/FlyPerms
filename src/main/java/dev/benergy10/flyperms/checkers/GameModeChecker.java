package dev.benergy10.flyperms.checkers;

import dev.benergy10.flyperms.configuration.ConfigOptions;
import dev.benergy10.flyperms.constants.Permissions;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.PlayerChecker;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GameModeChecker implements PlayerChecker<GameMode> {

    private final FlyPerms plugin;

    public GameModeChecker(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return this.plugin.getFPConfig().getValue(ConfigOptions.CHECK_GAMEMODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<GameMode> getAllowed(@NotNull Player player) {
        if (!isEnabled()) {
            return Collections.emptyList();
        }

        return Arrays.stream(GameMode.values())
                .filter(mode -> mode != GameMode.SPECTATOR && hasPerm(player, mode))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> getAllowedNames(@NotNull Player player) {
        if (!isEnabled()) {
            return Collections.emptyList();
        }

        return Arrays.stream(GameMode.values())
                .filter(mode -> mode != GameMode.SPECTATOR && hasPerm(player, mode))
                .map(this::parseToString)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean hasPerm(@NotNull Player player) {
        return hasPerm(player, player.getGameMode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean hasPerm(@NotNull Player player, String modeName) {
        GameMode targetMode;
        try {
            targetMode = GameMode.valueOf(modeName);
        }
        catch (IllegalArgumentException ignored) {
            return null;
        }

        return hasPerm(player, targetMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean hasPerm(@NotNull Player player, GameMode mode) {
        return isEnabled()
                ? player.hasPermission(Permissions.ALLOW_GAMEMODE + parseToString(mode))
                : null;
    }

    @NotNull
    private String parseToString(GameMode mode) {
        return mode.toString().toLowerCase();
    }
}
