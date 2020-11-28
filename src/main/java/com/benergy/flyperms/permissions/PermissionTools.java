package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.Constants.Permissions;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PermissionTools {

    private final FlyPerms plugin;
    private final PluginManager pm;
    private final Map<String, Permission> cachedPerms;

    public PermissionTools(FlyPerms plugin) {
        this.plugin = plugin;
        this.pm = plugin.getServer().getPluginManager();
        cachedPerms = new HashMap<>(50);
    }

    public void registerPerms() {
        registerGameModePerms();
        registerWorldPerms();
        registerSpeedGroupPerms();
    }

    public void removeAllPerms() {
        cachedPerms.values().forEach(this.pm::removePermission);
        cachedPerms.clear();
    }

    public void addWorldPerm(World world) {
        addPerm(new Permission(Permissions.ALLOW_WORLD + world.getName(), PermissionDefault.FALSE));
    }

    public void removeWorldPerm(World world) {
        removePerm(Permissions.ALLOW_WORLD + world.getName());
    }

    private void registerSpeedGroupPerms() {
        this.plugin.getFPConfig()
                .getSpeedGroups()
                .forEach(group -> addPerm(new Permission(Permissions.SPEED_GROUP + group.getName(), PermissionDefault.FALSE)));
    }

    private void registerGameModePerms() {
        if (!this.plugin.getFPConfig().isCheckGameMode()) {
            return;
        }
        Arrays.stream(GameMode.values())
                .filter(gm -> !gm.equals(GameMode.SPECTATOR))
                .forEach(gm -> addPerm(new Permission(Permissions.ALLOW_GAMEMODE + gm.name().toLowerCase(), PermissionDefault.FALSE)));
    }

    private void registerWorldPerms() {
        if (!this.plugin.getFPConfig().isCheckWorld()) {
            return;
        }
        this.plugin.getServer()
                .getWorlds()
                .stream()
                .filter(world -> !this.plugin.isIgnoreWorld(world))
                .forEach(this::addWorldPerm);
    }

    private void addPerm(Permission perm) {
        this.pm.addPermission(perm);
        cachedPerms.put(perm.getName(), perm);
    }

    private void removePerm(String perm) {
        this.pm.removePermission(cachedPerms.get(perm));
        cachedPerms.remove(perm);
    }
}
