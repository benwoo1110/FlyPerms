package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.api.FPFlyChecker;
import com.benergy.flyperms.enums.FlyState;
import com.benergy.flyperms.utils.Logging;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static com.benergy.flyperms.enums.FlyState.*;

public class FlyChecker extends Checker implements FPFlyChecker {

    public FlyChecker(FlyPerms plugin) {
        super(plugin);
    }

    public FlyState calculateFlyState(Player player) {
        return calculateFlyState(player, player.getGameMode(), player.getWorld());
    }

    public FlyState calculateFlyState(Player player, GameMode gameMode) {
        return calculateFlyState(player, gameMode, player.getWorld());
    }

    public FlyState calculateFlyState(Player player, World world) {
        return calculateFlyState(player, player.getGameMode(), world);
    }

    public FlyState calculateFlyState(Player player, GameMode gameMode, World world) {
        if (this.plugin.isIgnoreWorld(world)) {
            return IGNORED;
        }

        if (gameMode.equals(GameMode.SPECTATOR)) {
            player.setAllowFlight(true);
            return SPECTATOR;
        }

        if (this.plugin.getFPConfig().isAllowCreative() && gameMode.equals(GameMode.CREATIVE)) {
            if (!player.getAllowFlight()) {
                player.setAllowFlight(true);
            }
            return CREATIVE_BYPASS;
        }

        boolean allowedToFly = baseAllow(player)
                && hasGameModePerm(player, gameMode)
                && hasWorldPerm(player, world);

        if (player.isFlying() && !allowedToFly) {
            this.plugin.getFlyCheckScheduler().stopFly(player);
            return NO;
        }

        if (!player.getAllowFlight() && allowedToFly) {
            player.setAllowFlight(true);
            Logging.log(Level.FINE, "Allowing flight for " + player.getName());
        }
        else if (player.getAllowFlight() && !allowedToFly) {
            player.setAllowFlight(false);
            Logging.log(Level.FINE,"Disallowing flight for " + player.getName());
        }

        return allowedToFly ? YES : NO;
    }

    public boolean baseAllow(Player player) {
        return this.plugin.getFPConfig().isCheckGameMode()
                || this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow");
    }

    public List<String> allowInGameModes(Player player) {
        return Arrays.stream(GameMode.values())
                .filter(mode -> mode != GameMode.SPECTATOR && hasGameModePerm(player, mode))
                .map(mode -> mode.name().toLowerCase())
                .collect(Collectors.toList());
    }

    public List<String> allowInWorlds(Player player) {
        return this.plugin.getServer().getWorlds()
                .stream()
                .filter(world -> !this.plugin.isIgnoreWorld(world) && hasWorldPerm(player, world))
                .map(World::getName)
                .collect(Collectors.toList());
    }

    public boolean hasGameModePerm(Player player) {
        return hasGameModePerm(player, player.getGameMode());
    }

    public boolean hasGameModePerm(Player player, GameMode gameMode) {
        return hasGameModePerm(player, gameMode.name());
    }

    public boolean hasGameModePerm(Player player, String gameModeName) {
        return !this.plugin.getFPConfig().isCheckGameMode()
                || player.hasPermission("flyperms.allow.gamemode." + gameModeName.toLowerCase());
    }

    public boolean hasWorldPerm(Player player) {
        return hasWorldPerm(player, player.getWorld());
    }

    public boolean hasWorldPerm(Player player, World world) {
        return hasWorldPerm(player, world.getName());
    }

    public boolean hasWorldPerm(Player player, String worldName) {
        return !this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow.world." + worldName);
    }
}
