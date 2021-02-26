package dev.benergy10.flyperms.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checker for object where the {@link Player} has the {@link T} attribute itself.
 *
 * @param <T> {@inheritDoc}
 */
public interface PlayerChecker<T> extends Checker<T> {
    /**
     * Check if player has permissions to do the relevant actions.
     *
     * @param player A bukkit {@link Player} entity.
     * @return True if player has permission, else false. Returns null if not {@link #isEnabled()}.
     */
    @Nullable Boolean hasPerm(@NotNull Player player);
}
