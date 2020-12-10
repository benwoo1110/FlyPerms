package com.benergy.flyperms.api;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Modular interface so permission checking can be done in a per object type basis.
 *
 * @param <T> Target object to check permissions for.
 */
public interface Checker<T> {
    /**
     *
     * @return True if checking should take place, else return false.
     */
    boolean isEnabled();

    /**
     *
     * @param player A bukkit {@link Player} entity.
     * @return A List of object that player has permission. Returns null if not {@link #isEnabled()}.
     */
    List<T> getAllowed(Player player);

    /**
     *
     * @param player A bukkit {@link Player} entity.
     * @return String representation for a List of object that player has permission. Returns null if not {@link #isEnabled()}.
     */
    List<String> getAllowedNames(Player player);

    /**
     *
     * @param player A bukkit {@link Player} entity.
     * @param check  String representation of the object, usually it's name.
     * @return True if player has permission, else false. Returns null if not {@link #isEnabled()}.
     */
    Boolean hasPerm(Player player, String check);

    /**
     *
     * @param player A bukkit {@link Player} entity.
     * @param check  Target object to check for permissions.
     * @return True if player has permission, else false. Returns null if not {@link #isEnabled()}.
     */
    Boolean hasPerm(Player player, T check);
}
