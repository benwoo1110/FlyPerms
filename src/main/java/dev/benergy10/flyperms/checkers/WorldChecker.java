package dev.benergy10.flyperms.checkers;

import dev.benergy10.flyperms.Constants.Permissions;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.PlayerChecker;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class WorldChecker implements PlayerChecker<World> {

    private final FlyPerms plugin;

    public WorldChecker(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return this.plugin.getFPConfig().isCheckWorld();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<World> getAllowed(@NotNull Player player) {
        if (!isEnabled()) {
            return null;
        }

        return this.plugin.getServer().getWorlds()
                .stream()
                .filter(world -> !this.plugin.getFPConfig().isIgnoreWorld(world) && hasPerm(player, world))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> getAllowedNames(@NotNull Player player) {
        if (!isEnabled()) {
            return null;
        }

        return this.plugin.getServer().getWorlds()
                .stream()
                .filter(world -> !this.plugin.getFPConfig().isIgnoreWorld(world) && hasPerm(player, world))
                .map(World::getName)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean hasPerm(@NotNull Player player) {
        return hasPerm(player, player.getWorld());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean hasPerm(@NotNull Player player, String worldName) {
        World targetWorld = this.plugin.getServer().getWorld(worldName);
        if (targetWorld == null) {
            return null;
        }

        return hasPerm(player, targetWorld);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean hasPerm(@NotNull Player player, World world) {
        return isEnabled()
                ? player.hasPermission(Permissions.ALLOW_WORLD + world.getName())
                : null;
    }
}
