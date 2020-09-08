package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PermsFly {

    FlyPerms plugin;

    public PermsFly(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public FlyState canFly(Player player) {
        if (this.plugin.isIgnoreWorld(player.getWorld())) {
            return FlyState.IGNORED;
        }

        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            player.setAllowFlight(true);
            return FlyState.SPECTATOR;
        }

        if (creativeBypass(player)) {
            player.setAllowFlight(true);
            return FlyState.CREATIVE_BYPASS;
        }

        boolean fly = checkAllow(player)
                && checkGameMode(player)
                && checkWorld(player);

        if (!player.getAllowFlight() && fly) {
            player.setAllowFlight(true);
            this.plugin.getLogger().log(Level.FINE, "Allowed flight for " + player.getName());
        } else if (player.getAllowFlight() && !fly) {
            player.setAllowFlight(false);
            this.plugin.getLogger().log(Level.FINE,"Disallowed flight for " + player.getName());
        }

        if (player.isFlying() && !fly) {
            player.setFlying(false);
            this.plugin.getLogger().log(Level.FINE,"Force stopped flying for " + player.getName());
        }

        if (!fly) {
            return FlyState.NO;
        }

        return FlyState.YES;
    }

    public boolean creativeBypass(Player player) {
        return this.plugin.getFPConfig().isAllowCreative() && player.getGameMode().equals(GameMode.CREATIVE);
    }

    public boolean checkGameMode(Player player) {
        return checkGameMode(player, player.getGameMode());
    }

    public boolean checkGameMode(Player player, GameMode gameMode) {
        return !this.plugin.getFPConfig().isCheckGameMode()
                || player.hasPermission("flyperms.allow.gamemode." + gameMode.name().toLowerCase());
    }

    public List<String> checkAllGameModes(Player player) {
        List<String> gameModesAllowed = new ArrayList<>();
        for (GameMode gameMode : GameMode.values()) {
            if (gameMode != GameMode.SPECTATOR && checkGameMode(player, gameMode)) {
                gameModesAllowed.add(gameMode.name().toLowerCase());
            }
        }
        return gameModesAllowed;
    }

    public boolean checkWorld(Player player) {
        return checkWorld(player, player.getWorld());
    }

    public boolean checkWorld(Player player, World world) {
        return !this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow.world." + world.getName());
    }

    public List<String> checkAllWorlds(Player player) {
        List<String> worldsAllowed = new ArrayList<>();
        for (World world : plugin.getServer().getWorlds()) {
            if (!this.plugin.isIgnoreWorld(world) && checkWorld(player, world)) {
                worldsAllowed.add(world.getName());
            }
        }
        return worldsAllowed;
    }

    public boolean checkAllow(Player player) {
        return this.plugin.getFPConfig().isCheckGameMode()
                || this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow");
    }

}
