package com.benergy.flyperms.api;

import org.bukkit.entity.Player;

/**
 * Checker for object where the {@link Player} has the {@link T} attribute itself.
 *
 * @param <T> {@inheritDoc}
 */
public interface PlayerChecker<T> extends Checker<T> {
    /**
     *
     * @param player A bukkit {@link Player} entity.
     * @return True if player has permission, else false. Returns null if not {@link #isEnabled()}.
     */
    Boolean hasPerm(Player player);
}
