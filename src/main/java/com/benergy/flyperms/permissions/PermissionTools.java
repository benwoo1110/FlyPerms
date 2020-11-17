package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.utils.SpeedGroup;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

public class PermissionTools {

    private final FlyPerms plugin;
    private final PluginManager pm;

    public PermissionTools(FlyPerms plugin) {
        this.plugin = plugin;
        this.pm = plugin.getServer().getPluginManager();
    }

    public void registerPerms() {
        if (this.plugin.getFPConfig().isCheckGameMode()) {
            registerGameModePerms();
        }
        if (this.plugin.getFPConfig().isCheckWorld()) {
            registerWorldPerms();
        }
        registerSpeedGroupPerms();
    }

    private void registerSpeedGroupPerms() {
        for (SpeedGroup speedRange : this.plugin.getFPConfig().getSpeedGroups()) {
            this.pm.addPermission(
                    new Permission(
                            "flyperms.speed." + speedRange.getName(),
                            "Give you access to speed range for group " + speedRange.getName() + "defined in config.",
                            PermissionDefault.FALSE
                    )
            );
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
        this.pm.addPermission(
                new Permission(
                        "flyperms.allow.gamemode." + gameMode.toString().toLowerCase(),
                        "Allow you to fly in gamemode " + gameMode.toString().toLowerCase() + "!",
                        PermissionDefault.FALSE
                )
        );
    }

    private void registerWorldPerms() {
        for (World world : plugin.getServer().getWorlds()) {
            if (!this.plugin.isIgnoreWorld(world)) {
                addWorldPerm(world);
            }
        }
    }

    public void addWorldPerm(World world) {
        this.pm.addPermission(
                new Permission(
                        "flyperms.allow.world." + world.getName(),
                        "Allow you to fly in world named " + world.getName() + "!",
                        PermissionDefault.FALSE
                )
        );
    }

    public void removeWorldPerm(World world) {
        this.pm.removePermission(
                new Permission(
                        "flyperms.allow.world." + world.getName(),
                        "Allow you to fly in world named " + world.getName() + "!",
                        PermissionDefault.FALSE
                )
        );
    }

}
