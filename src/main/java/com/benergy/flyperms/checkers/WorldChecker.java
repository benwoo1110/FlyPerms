package com.benergy.flyperms.checkers;

import com.benergy.flyperms.Constants.Permissions;
import com.benergy.flyperms.FlyPerms;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class WorldChecker implements PlayerChecker<World> {

    private final FlyPerms plugin;

    public WorldChecker(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() {
        return this.plugin.getFPConfig().isCheckWorld();
    }

    @Override
    public List<World> getAllowed(Player player) {
        if (!isEnabled()) {
            return null;
        }

        return this.plugin.getServer().getWorlds()
                .stream()
                .filter(world -> !this.plugin.getFPConfig().isIgnoreWorld(world) && hasPerm(player, world))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllowedNames(Player player) {
        if (!isEnabled()) {
            return null;
        }

        return this.plugin.getServer().getWorlds()
                .stream()
                .filter(world -> !this.plugin.getFPConfig().isIgnoreWorld(world) && hasPerm(player, world))
                .map(World::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean hasPerm(Player player) {
        return hasPerm(player, player.getWorld());
    }

    @Override
    public Boolean hasPerm(Player player, String worldName) {
        World targetWorld = this.plugin.getServer().getWorld(worldName);
        if (targetWorld == null) {
            return null;
        }

        return hasPerm(player, targetWorld);
    }

    @Override
    public Boolean hasPerm(Player player, World world) {
        return isEnabled()
                ? player.hasPermission(Permissions.ALLOW_WORLD + world.getName())
                : null;
    }
}
