package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class PermsRegister {

    FlyPerms plugin;

    public PermsRegister(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public void registerPerms() {
        if (this.plugin.getFPConfig().isCheckGameMode()) {
            registerGameModePerms();
        }
        if (this.plugin.getFPConfig().isCheckWorld()) {
            registerWorldPerms();
        }
    }

    private void registerGameModePerms() {
        for (GameMode gameMode : GameMode.values()) {
            if (!gameMode.equals(GameMode.SPECTATOR)) {
                addGameModePerms(gameMode);
            }
        }
    }

    private void addGameModePerms(GameMode gameMode) {
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
            if (!this.plugin.isIgnoreWorld(world)) {
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
