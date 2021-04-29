package dev.benergy10.flyperms.checkers;

import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.PlayerChecker;
import dev.benergy10.flyperms.constants.Permissions;
import org.bukkit.entity.Player;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.region.IWrappedRegion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class WorldGuardChecker implements PlayerChecker<IWrappedRegion> {

    private final FlyPerms plugin;
    private final WorldGuardWrapper worldGuard;

    public WorldGuardChecker(FlyPerms plugin) {
        this.plugin = plugin;
        this.worldGuard = WorldGuardWrapper.getInstance();
    }

    @Override
    public boolean isEnabled() {
        return this.worldGuard != null;
    }

    @Override
    public @NotNull List<IWrappedRegion> getAllowed(@NotNull Player player) {
        return null;
    }

    @Override
    public @NotNull List<String> getAllowedNames(@NotNull Player player) {
        return null;
    }

    @Override
    public @Nullable Boolean hasPerm(@NotNull Player player, String check) {
        Optional<IWrappedRegion> region = this.worldGuard.getRegion(player.getWorld(), check);
        return region.isPresent() && player.hasPermission(Permissions.ALLOW_REGION + region.get().getId());
    }

    @Override
    public @Nullable Boolean hasPerm(@NotNull Player player, IWrappedRegion check) {
        return null;
    }

    @Override
    public @Nullable Boolean hasPerm(@NotNull Player player) {
        return null;
    }
}
