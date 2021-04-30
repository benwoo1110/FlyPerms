package dev.benergy10.flyperms.checkers;

import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.PlayerChecker;
import dev.benergy10.flyperms.constants.Permissions;
import dev.benergy10.flyperms.utils.ConfigOptions;
import org.bukkit.entity.Player;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.region.IWrappedRegion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WorldGuardChecker implements PlayerChecker<IWrappedRegion> {

    private final FlyPerms plugin;
    private WorldGuardWrapper worldGuard;

    public WorldGuardChecker(FlyPerms plugin) {
        this.plugin = plugin;
        try {
            this.worldGuard = WorldGuardWrapper.getInstance();
        }
        catch (NoClassDefFoundError e) {
            // Ignore
        }
    }

    @Override
    public boolean isEnabled() {
        return this.worldGuard != null && this.plugin.getFPConfig().getValue(ConfigOptions.CHECK_WORLDGUARD);
    }

    @Override
    public @NotNull List<IWrappedRegion> getAllowed(@NotNull Player player) {
        return Collections.emptyList();
    }

    @Override
    public @NotNull List<String> getAllowedNames(@NotNull Player player) {
        return Collections.emptyList();
    }

    @Override
    public @Nullable Boolean hasPerm(@NotNull Player player, String check) {
        Optional<IWrappedRegion> region = this.worldGuard.getRegion(player.getWorld(), check);
        return region.map(iWrappedRegion -> hasPerm(player, iWrappedRegion)).orElse(null);
    }

    @Override
    public @Nullable Boolean hasPerm(@NotNull Player player, IWrappedRegion check) {
        return player.hasPermission(Permissions.ALLOW_WORLDGUARD + check.getId());
    }

    @Override
    public @Nullable Boolean hasPerm(@NotNull Player player) {
        Set<IWrappedRegion> regions = this.worldGuard.getRegions(player.getLocation());
        return regions.stream().anyMatch(region -> hasPerm(player, region));
    }
}
