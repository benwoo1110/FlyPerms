package com.benergy.flyperms.Permissions;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class FPPermissions {

    FlyPerms plugin;

    public FPPermissions(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public boolean canFly(Player player) {
        // Check gamemode
        if (this.plugin.getFPConfig().isCheckGameMode()
                && !player.hasPermission("flyperms.allow.gamemode." + player.getGameMode().toString().toLowerCase())) {
            return false;
        }

        // Check world
        if (this.plugin.getFPConfig().isCheckWorld()
                && !player.hasPermission("flyperms.allow.world." + player.getWorld().getName())) {
            return false;
        }

        // Just simple allow or disabled
        if (!this.plugin.getFPConfig().isCheckGameMode()
                && !this.plugin.getFPConfig().isCheckWorld()
                && !player.hasPermission("flyperms.allow")) {
            return false;
        }
        return true;
    }

    public void registerWorldsPerms() {
        List<World> worlds = plugin.getServer().getWorlds();
        for (World world : worlds) {
            addWorldPerm(world);
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
