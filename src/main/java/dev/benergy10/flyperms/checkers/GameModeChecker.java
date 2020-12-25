package dev.benergy10.flyperms.checkers;

import dev.benergy10.flyperms.Constants.Permissions;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.PlayerChecker;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameModeChecker implements PlayerChecker<GameMode> {

    private final FlyPerms plugin;

    public GameModeChecker(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() {
        return this.plugin.getFPConfig().isCheckGameMode();
    }

    @Override
    public List<GameMode> getAllowed(Player player) {
        if (!isEnabled()) {
            return null;
        }

        return Arrays.stream(GameMode.values())
                .filter(mode -> mode != GameMode.SPECTATOR && hasPerm(player, mode))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllowedNames(Player player) {
        if (!isEnabled()) {
            return null;
        }

        return Arrays.stream(GameMode.values())
                .filter(mode -> mode != GameMode.SPECTATOR && hasPerm(player, mode))
                .map(this::parseToString)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean hasPerm(Player player) {
        return hasPerm(player, player.getGameMode());
    }

    @Override
    public Boolean hasPerm(Player player, String modeName) {
        GameMode targetMode;
        try {
            targetMode = GameMode.valueOf(modeName);
        }
        catch (IllegalArgumentException ignored) {
            return null;
        }

        return hasPerm(player, targetMode);
    }

    @Override
    public Boolean hasPerm(Player player, GameMode mode) {
        return isEnabled()
                ? player.hasPermission(Permissions.ALLOW_GAMEMODE + parseToString(mode))
                : null;
    }

    @NotNull
    private String parseToString(GameMode mode) {
        return mode.toString().toLowerCase();
    }
}
