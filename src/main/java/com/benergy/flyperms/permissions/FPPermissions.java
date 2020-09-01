package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

public class FPPermissions {

    FlyPerms plugin;

    public FPPermissions(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public boolean canFly(Player player) {
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
        return !this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow.gamemode." + player.getGameMode().toString().toLowerCase());
    }

    public boolean checkWorld(Player player) {
        return !this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow.world." + player.getWorld().getName());
    }

    public boolean checkAllow(Player player) {
        return this.plugin.getFPConfig().isCheckGameMode()
                || this.plugin.getFPConfig().isCheckWorld()
                || player.hasPermission("flyperms.allow");
    }

    public void registerPerms() {
        registerGameModePerms();
        registerWorldPerms();
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
        List<World> worlds = plugin.getServer().getWorlds();
        for (World world : worlds) {
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
