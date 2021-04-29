package dev.benergy10.flyperms.managers;

import dev.benergy10.flyperms.checkers.WorldGuardChecker;
import dev.benergy10.flyperms.utils.ConfigOptions;
import dev.benergy10.flyperms.constants.FlyState;
import dev.benergy10.flyperms.constants.Permissions;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.FPCheckManager;
import dev.benergy10.flyperms.checkers.GameModeChecker;
import dev.benergy10.flyperms.api.PlayerChecker;
import dev.benergy10.flyperms.checkers.SpeedChecker;
import dev.benergy10.flyperms.checkers.WorldChecker;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.benergy10.flyperms.constants.FlyState.*;

/**
 * {@inheritDoc}
 */
public class CheckManager implements FPCheckManager {

    private final FlyPerms plugin;

    private final SpeedChecker speedChecker;
    private final WorldChecker worldChecker;
    private final GameModeChecker gameModeChecker;
    private final WorldGuardChecker worldGuardChecker;

    public CheckManager(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
        this.speedChecker = new SpeedChecker(plugin);
        this.worldChecker = new WorldChecker(plugin);
        this.gameModeChecker = new GameModeChecker(plugin);
        this.worldGuardChecker = new WorldGuardChecker(plugin);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public FlyState calculateFlyState(@NotNull Player player) {
        if (this.plugin.getFPConfig().getValue(ConfigOptions.IGNORE_WORLDS).contains(player.getWorld().getName())) {
            return IGNORED;
        }
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            return SPECTATOR;
        }
        if (this.plugin.getFPConfig().getValue(ConfigOptions.ALLOW_IN_CREATIVE) && player.getGameMode().equals(GameMode.CREATIVE)) {
            return CREATIVE_BYPASS;
        }

        return isAllowedToFly(player) ? YES : NO;
    }

    private boolean isAllowedToFly(@NotNull Player player) {
        if (!gameModeChecker.isEnabled() && !worldChecker.isEnabled()) {
            return checkBaseAllow(player);
        }

        return runPlayerChecker(player, worldChecker)
                && runPlayerChecker(player, gameModeChecker);
    }

    private boolean checkBaseAllow(@NotNull Player player) {
        return player.hasPermission(Permissions.ALLOW_BASE);
    }

    private <T> boolean runPlayerChecker(@NotNull Player player,
                                         @NotNull PlayerChecker<T> checker) {

        return !checker.isEnabled() || checker.hasPerm(player);
    }

    /**
     * {@inheritDoc}
     */
    public boolean canChangeSpeedTo(@NotNull Player player,
                                    double speed) {

        return this.plugin.getFPConfig()
                .getValue(ConfigOptions.SPEED_GROUPS)
                .stream()
                .anyMatch(group -> speedChecker.hasPerm(player, group) && group.isInRange(speed));
    }

    /**
     * {@inheritDoc}
     */

    public @NotNull SpeedChecker getSpeedChecker() {
        return speedChecker;
    }

    /**
     * {@inheritDoc}
     */
    public @NotNull WorldChecker getWorldChecker() {
        return worldChecker;
    }

    /**
     * {@inheritDoc}
     */
    public @NotNull GameModeChecker getGameModeChecker() {
        return gameModeChecker;
    }
}
