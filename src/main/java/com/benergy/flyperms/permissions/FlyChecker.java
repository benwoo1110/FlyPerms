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

public class FlyChecker extends Checker implements FPFlyChecker {

    public FlyChecker(FlyPerms plugin) {
        super(plugin);
    }

    public FlyState canFly(Player player) {
        return canFly(player, player.getGameMode(), player.getWorld());
    }

    public FlyState canFly(Player player, GameMode gameMode) {
        return canFly(player, gameMode, player.getWorld());
    }

    public FlyState canFly(Player player, World world) {
        return canFly(player, player.getGameMode(), world);
    }

    public FlyState canFly(Player player, GameMode gameMode, World world) {
        if (this.plugin.isIgnoreWorld(world)) {
            return FlyState.IGNORED;
        }

        if (gameMode.equals(GameMode.SPECTATOR)) {
            player.setAllowFlight(true);
            return FlyState.SPECTATOR;
        }

        if (this.plugin.getFPConfig().isAllowCreative() && gameMode.equals(GameMode.CREATIVE)) {
            if (!player.getAllowFlight()) {
                player.setAllowFlight(true);
            }
            return FlyState.CREATIVE_BYPASS;
        }

        boolean allowedToFly = baseAllow(player)
                && checkGameMode(player, gameMode)
                && checkWorld(player, world);

        if (player.isFlying() && !allowedToFly) {
            this.plugin.getFlyCheckScheduler().stopFly(player);
            return FlyState.NO;
        }

        if (!player.getAllowFlight() && allowedToFly) {
            player.setAllowFlight(true);
            Logging.log(Level.FINE, "Allowing flight for " + player.getName());
        }
        else if (player.getAllowFlight() && !allowedToFly) {
            player.setAllowFlight(false);
            Logging.log(Level.FINE,"Disallowing flight for " + player.getName());
        }

        return allowedToFly ? FlyState.YES : FlyState.NO;
    }

    public boolean baseAllow(Player player) {
        return this.plugin.getFPConfig().isCheckGameMode()
                || this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow");
    }

    public List<String> allowInGameModes(Player player) {
        return Arrays.stream(GameMode.values())
                .filter(mode -> mode != GameMode.SPECTATOR && checkGameMode(player, mode))
                .map(mode -> mode.name().toLowerCase())
                .collect(Collectors.toList());
    }

    public List<String> allowInWorlds(Player player) {
        return this.plugin.getServer().getWorlds()
                .stream()
                .filter(world -> !this.plugin.isIgnoreWorld(world) && checkWorld(player, world))
                .map(World::getName)
                .collect(Collectors.toList());
    }

    public boolean checkGameMode(Player player) {
        return checkGameMode(player, player.getGameMode());
    }

    public boolean checkGameMode(Player player, GameMode gameMode) {
        return checkGameMode(player, gameMode.name());
    }

    public boolean checkGameMode(Player player, String gameModeName) {
        return !this.plugin.getFPConfig().isCheckGameMode()
                || player.hasPermission("flyperms.allow.gamemode." + gameModeName.toLowerCase());
    }

    public boolean checkWorld(Player player) {
        return checkWorld(player, player.getWorld());
    }

    public boolean checkWorld(Player player, World world) {
        return checkWorld(player, world.getName());
    }

    public boolean checkWorld(Player player, String worldName) {
        return !this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow.world." + worldName);
    }
}
