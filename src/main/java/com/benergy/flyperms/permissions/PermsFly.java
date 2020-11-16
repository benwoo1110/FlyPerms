package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.api.FPPerms;
import com.benergy.flyperms.enums.FlyState;
import com.benergy.flyperms.utils.FPLogger;
import com.benergy.flyperms.utils.SpeedRange;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class PermsFly implements FPPerms {

    private final FlyPerms plugin;

    public PermsFly(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public FlyState canFly(Player player) {
        return canFly(player, player.getGameMode());
    }

    public FlyState canFly(Player player, GameMode gameMode) {
        if (this.plugin.isIgnoreWorld(player.getWorld())) {
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

        boolean allowedToFly = checkBasicAllow(player)
                && checkGameMode(player)
                && checkWorld(player);

        if (player.isFlying() && !allowedToFly) {
            this.plugin.getFlyCheckScheduler().stopFly(player);
            return FlyState.NO;
        }

        if (!player.getAllowFlight() && allowedToFly) {
            player.setAllowFlight(true);
            FPLogger.log(Level.FINE, "Allowing flight for " + player.getName());
        }
        else if (player.getAllowFlight() && !allowedToFly) {
            player.setAllowFlight(false);
            FPLogger.log(Level.FINE,"Disallowing flight for " + player.getName());
        }

        return allowedToFly ? FlyState.YES : FlyState.NO;
    }

    public boolean creativeBypass(Player player) {
        return this.plugin.getFPConfig().isAllowCreative() && player.getGameMode().equals(GameMode.CREATIVE);
    }

    public boolean checkBasicAllow(Player player) {
        return this.plugin.getFPConfig().isCheckGameMode()
                || this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow");
    }

    public boolean checkGameMode(Player player) {
        return checkGameMode(player, player.getGameMode());
    }

    public boolean checkGameMode(Player player, GameMode gameMode) {
        return !this.plugin.getFPConfig().isCheckGameMode()
                || player.hasPermission("flyperms.allow.gamemode." + gameMode.name().toLowerCase());
    }

    public List<String> checkAllGameModes(Player player) {
        return Arrays.stream(GameMode.values())
                .filter(mode -> mode != GameMode.SPECTATOR && checkGameMode(player, mode))
                .map(mode -> mode.name().toLowerCase())
                .collect(Collectors.toList());
    }

    public boolean checkWorld(Player player) {
        return checkWorld(player, player.getWorld());
    }

    public boolean checkWorld(Player player, World world) {
        return !this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow.world." + world.getName());
    }

    public List<String> checkAllWorlds(Player player) {
        return this.plugin.getServer().getWorlds()
                .stream()
                .filter(world -> !this.plugin.isIgnoreWorld(world) && checkWorld(player, world))
                .map(World::getName)
                .collect(Collectors.toList());
    }

    public boolean canChangeSpeedTo(Player player, double speed) {
        return this.plugin.getFPConfig().getSpeedGroups()
                .stream()
                .anyMatch(group -> player.hasPermission(group.permission()) && group.isInRange(speed));
    }

    public List<String> inSpeedGroups(Player player) {
        return this.plugin.getFPConfig().getSpeedGroups()
                .stream()
                .filter(group -> player.hasPermission(group.permission()))
                .map(SpeedRange::getName)
                .collect(Collectors.toList());
    }
}
