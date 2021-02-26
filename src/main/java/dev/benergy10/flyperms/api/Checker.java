package dev.benergy10.flyperms.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Modular interface so permission checking can be done in a per object type basis.
 *
 * @param <T> Target object to check permissions for.
 */
public interface Checker<T> {
    /**
     * Should checking take place.
     *
     * @return True if checking should take place, else return false.
     */
    boolean isEnabled();

    /**
     * Get all the allowed object/action that the player have permission for.
     *
     * @param player A bukkit {@link Player} entity.
     * @return A List of object that player has permission. Returns null if not {@link #isEnabled()}.
     */
    @NotNull List<T> getAllowed(@NotNull Player player);

    /**
     * String representation of the target checker object {@link T} that a player is allowed.
     *
     * @param player A bukkit {@link Player} entity.
     * @return String representation for a List of object that player has permission. Returns null if not {@link #isEnabled()}.
     */
    @NotNull List<String> getAllowedNames(@NotNull Player player);

    /**
     * Check if player has permissions to do the relevant actions.
     *
     * @param player A bukkit {@link Player} entity.
     * @param check  String representation of the object, usually it's name.
     * @return True if player has permission, else false. Returns null if not {@link #isEnabled()}.
     */
    @Nullable Boolean hasPerm(@NotNull Player player, String check);

    /**
     * Check if player has permissions to do the relevant actions.
     *
     * @param player A bukkit {@link Player} entity.
     * @param check  Target object to check for permissions.
     * @return True if player has permission, else false. Returns null if not {@link #isEnabled()}.
     */
     @Nullable Boolean hasPerm(@NotNull Player player, T check);
}
