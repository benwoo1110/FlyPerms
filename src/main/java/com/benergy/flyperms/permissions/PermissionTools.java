package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.enums.Permissions;
import com.benergy.flyperms.utils.SpeedGroup;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class PermissionTools {

    private final FlyPerms plugin;
    private final PluginManager pm;

    private final ArrayList<Permission> cachedSpeedGroup;

    public PermissionTools(FlyPerms plugin) {
        this.plugin = plugin;
        this.pm = plugin.getServer().getPluginManager();
        cachedSpeedGroup = new ArrayList<>(10);
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

    public void registerSpeedGroupPerms() {
        for (SpeedGroup group : this.plugin.getFPConfig().getSpeedGroups()) {
            Permission newGroupPerm = new Permission(Permissions.SPEED_GROUP + group.getName(), PermissionDefault.FALSE);
            this.pm.addPermission(newGroupPerm);
            cachedSpeedGroup.add(newGroupPerm);
        }
    }

    public void removeSpeedGroupPerms() {
        cachedSpeedGroup.forEach(this.pm::removePermission);
        cachedSpeedGroup.clear();
    }

    private void registerGameModePerms() {
        for (GameMode gameMode : GameMode.values()) {
            if (gameMode.equals(GameMode.SPECTATOR)) {
                continue;
            }
            this.pm.addPermission(new Permission(Permissions.ALLOW_GAMEMODE + gameMode.name().toLowerCase(), PermissionDefault.FALSE));
        }
    }

    private void registerWorldPerms() {
        for (World world : plugin.getServer().getWorlds()) {
            if (this.plugin.isIgnoreWorld(world)) {
                continue;
            }
            addWorldPerm(world);
        }
    }

    public void addWorldPerm(World world) {
        this.pm.addPermission(new Permission(Permissions.ALLOW_WORLD + world.getName(), PermissionDefault.FALSE));
    }

    public void removeWorldPerm(World world) {
        this.pm.removePermission(new Permission(Permissions.ALLOW_WORLD + world.getName(), PermissionDefault.FALSE));
    }

}
