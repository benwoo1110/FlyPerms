package com.benergy.flyperms.utils;

import com.benergy.flyperms.Constants.FlyState;
import com.benergy.flyperms.Constants.Permissions;
import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.checkers.GameModeChecker;
import com.benergy.flyperms.checkers.PlayerChecker;
import com.benergy.flyperms.checkers.SpeedChecker;
import com.benergy.flyperms.checkers.WorldChecker;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import static com.benergy.flyperms.Constants.FlyState.*;

public class CheckManager {

    private final FlyPerms plugin;

    private final SpeedChecker speedChecker;
    private final WorldChecker worldChecker;
    private final GameModeChecker gameModeChecker;

    public CheckManager(FlyPerms plugin) {
        this.plugin = plugin;
        this.speedChecker = new SpeedChecker(plugin);
        this.worldChecker = new WorldChecker(plugin);
        this.gameModeChecker = new GameModeChecker(plugin);
    }

    public FlyState calculateFlyState(Player player) {
        if (this.plugin.getFPConfig().isIgnoreWorld(player.getWorld())) {
            return IGNORED;
        }
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            return SPECTATOR;
        }
        if (this.plugin.getFPConfig().isAllowCreative() && player.getGameMode().equals(GameMode.CREATIVE)) {
            return CREATIVE_BYPASS;
        }

        return isAllowedToFly(player) ? YES : NO;
    }

    private boolean isAllowedToFly(Player player) {
        if (!gameModeChecker.isEnabled() && !worldChecker.isEnabled()) {
            return checkBaseAllow(player);
        }

        return runPlayerChecker(player, worldChecker)
                && runPlayerChecker(player, gameModeChecker);
    }

    private boolean checkBaseAllow(Player player) {
        return player.hasPermission(Permissions.ALLOW_BASE);
    }

    private <T> boolean runPlayerChecker(Player player, PlayerChecker<T> checker) {
        return !checker.isEnabled() || checker.hasPerm(player);
    }

    public boolean canChangeSpeedTo(Player player, double speed) {
        return this.plugin.getFPConfig()
                .getSpeedGroups()
                .stream()
                .anyMatch(group -> speedChecker.hasPerm(player, group) && group.isInRange(speed));
    }

    public SpeedChecker getSpeedChecker() {
        return speedChecker;
    }

    public WorldChecker getWorldChecker() {
        return worldChecker;
    }

    public GameModeChecker getGameModeChecker() {
        return gameModeChecker;
    }
}
