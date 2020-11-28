package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.api.FPFlyChecker;
import com.benergy.flyperms.Constants.FlyState;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.benergy.flyperms.Constants.FlyState.*;

public class FlyChecker extends Checker implements FPFlyChecker {

    public FlyChecker(FlyPerms plugin) {
        super(plugin);
    }

    public FlyState calculateFlyState(Player player) {
        if (this.plugin.getFlyManager().isIgnoreWorld(player.getWorld())) {
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
        return baseAllow(player) ||
                ((!this.plugin.getFPConfig().isCheckGameMode() || hasGameModePerm(player))
                && (!this.plugin.getFPConfig().isCheckWorld() || hasWorldPerm(player)));
    }

    public boolean baseAllow(Player player) {
        return player.hasPermission("flyperms.allow");
    }

    public List<String> allowInGameModes(Player player) {
        if (!this.plugin.getFPConfig().isCheckGameMode()) {
            return null;
        }

        return Arrays.stream(GameMode.values())
                .filter(mode -> mode != GameMode.SPECTATOR && hasGameModePerm(player, mode))
                .map(mode -> mode.name().toLowerCase())
                .collect(Collectors.toList());
    }

    public List<String> allowInWorlds(Player player) {
        if (!this.plugin.getFPConfig().isCheckWorld()) {
            return null;
        }

        return this.plugin.getServer().getWorlds()
                .stream()
                .filter(world -> !this.plugin.getFlyManager().isIgnoreWorld(world) && hasWorldPerm(player, world))
                .map(World::getName)
                .collect(Collectors.toList());
    }

    public Boolean hasGameModePerm(Player player) {
        return hasGameModePerm(player, player.getGameMode());
    }

    public Boolean hasGameModePerm(Player player, String modeName) {
        GameMode targetMode;
        try {
            targetMode = GameMode.valueOf(modeName);
        }
        catch (IllegalArgumentException ignored) {
           return null;
        }

        return hasGameModePerm(player, targetMode);
    }

    public Boolean hasGameModePerm(Player player, GameMode gameMode) {
        return this.plugin.getFPConfig().isCheckGameMode()
                ? player.hasPermission("flyperms.allow.gamemode." + gameMode.toString().toLowerCase())
                : null;
    }

    public Boolean hasWorldPerm(Player player) {
        return hasWorldPerm(player, player.getWorld());
    }

    public Boolean hasWorldPerm(Player player, String worldName) {
        World targetWorld = this.plugin.getServer().getWorld(worldName);
        if (targetWorld == null) {
            return null;
        }

        return hasWorldPerm(player, targetWorld);
    }

    public Boolean hasWorldPerm(Player player, World world) {
        return this.plugin.getFPConfig().isCheckWorld()
                ? player.hasPermission("flyperms.allow.world." + world.getName())
                : null;
    }
}
