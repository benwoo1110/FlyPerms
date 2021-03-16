package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.constants.Permissions;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles permission register.
 */
public class PermissionTools {

    private final FlyPerms plugin;
    private final PluginManager pm;
    private final Map<String, Permission> cachedPerms;

    public PermissionTools(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
        this.pm = plugin.getServer().getPluginManager();
        cachedPerms = new HashMap<>(50);
    }

    /**
     * Register all non-command permissions used.
     */
    public void registerPerms() {
        registerGameModePerms();
        registerWorldPerms();
        registerSpeedGroupPerms();
    }

    /**
     * Unregister all non-command permissions used.
     */
    public void removeAllPerms() {
        cachedPerms.values().forEach(this.pm::removePermission);
        cachedPerms.clear();
    }

    /**
     * Register a single world permission.
     *
     * @param world A bukkit {@link World}.
     */
    public void addWorldPerm(@NotNull World world) {
        addPerm(new Permission(Permissions.ALLOW_WORLD + world.getName(), PermissionDefault.FALSE));
    }

    /**
     * Unregister a single world permission.
     *
     * @param world A bukkit {@link World}.
     */
    public void removeWorldPerm(@NotNull World world) {
        removePerm(Permissions.ALLOW_WORLD + world.getName());
    }

    /**
     * Register all permissions for {@link SpeedGroup} defined in config.
     */
    private void registerSpeedGroupPerms() {
        this.plugin.getFPConfig()
                .getSpeedGroups()
                .forEach(group -> addPerm(new Permission(Permissions.SPEED_GROUP + group.getName(), PermissionDefault.FALSE)));
    }

    /**
     * Register all permissions for {@link GameMode}.
     */
    private void registerGameModePerms() {
        if (!this.plugin.getFPConfig().isCheckGameMode()) {
            return;
        }
        Arrays.stream(GameMode.values())
                .filter(gm -> !gm.equals(GameMode.SPECTATOR))
                .forEach(gm -> addPerm(new Permission(Permissions.ALLOW_GAMEMODE + gm.name().toLowerCase(), PermissionDefault.FALSE)));
    }

    /**
     * Register all permissions for all loaded {@link World}.
     */
    private void registerWorldPerms() {
        if (!this.plugin.getFPConfig().isCheckWorld()) {
            return;
        }
        this.plugin.getServer()
                .getWorlds()
                .stream()
                .filter(world -> !this.plugin.getFPConfig().isIgnoreWorld(world))
                .forEach(this::addWorldPerm);
    }

    /**
     * Do permissions register and add to cache.
     *
     * @param perm The permission node to register.
     */
    private void addPerm(@NotNull Permission perm) {
        this.pm.addPermission(perm);
        cachedPerms.put(perm.getName(), perm);
    }

    /**
     * Do permissions unregister and remove from cache.
     *
     * @param perm The permission node to unregister.
     */
    private void removePerm(@NotNull String perm) {
        this.pm.removePermission(cachedPerms.get(perm));
        cachedPerms.remove(perm);
    }
}
