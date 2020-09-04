package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.List;

public class FPPermissions {

    FlyPerms plugin;

    public FPPermissions(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public boolean hasCommandPerms(CommandSender sender) {
        if (!sender.getName().equalsIgnoreCase("CONSOLE")) {
            return true;
        }
        return sender.hasPermission("flyperms.seeallowed")
                || sender.hasPermission("flyperms.info");
    }

    public Boolean canFly(Player player) {
        if (this.plugin.ignoreWorld(player.getWorld())) {
            return null;
        }
        boolean fly = checkAllow(player)
                && checkGameMode(player)
                && checkWorld(player);

        player.setAllowFlight(fly);
        if (!fly && !player.isFlying()) {
            player.setFlying(false);
        }

        return fly;
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
            if (!this.plugin.ignoreWorld(world) && checkWorld(player, world)) {
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

    public void registerPerms() {
        if (this.plugin.getFPConfig().isCheckGameMode()) {
            registerGameModePerms();
        }
        if (this.plugin.getFPConfig().isCheckWorld()) {
            registerWorldPerms();
        }
    }

    public void registerGameModePerms() {
        for (GameMode gameMode : GameMode.values()) {
            if (!gameMode.equals(GameMode.SPECTATOR)) {
                addGameModePerms(gameMode);
            }
        }
    }

    public void addGameModePerms(GameMode gameMode) {
        this.plugin.getServer().getPluginManager().addPermission(
            new Permission(
                "flyperms.allow.gamemode." + gameMode.toString().toLowerCase(),
                "Allow you to fly in gamemode " + gameMode.toString().toLowerCase() + "!",
                PermissionDefault.FALSE
            )
        );
    }

    public void registerWorldPerms() {
        for (World world : plugin.getServer().getWorlds()) {
            if (!this.plugin.ignoreWorld(world)) {
                addWorldPerm(world);
            }
        }
    }

    public void addWorldPerm(World world) {
        this.plugin.getServer().getPluginManager().addPermission(
            new Permission(
                "flyperms.allow.world." + world.getName(),
                "Allow you to fly in world named " + world.getName() + "!",
                PermissionDefault.FALSE
            )
        );
    }

    public void removeWorldPerm(World world) {
        this.plugin.getServer().getPluginManager().removePermission(
            new Permission(
                "flyperms.allow.world." + world.getName(),
                "Allow you to fly in world named " + world.getName() + "!",
                PermissionDefault.FALSE
            )
        );
    }
}
