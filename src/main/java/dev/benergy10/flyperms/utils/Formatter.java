package dev.benergy10.flyperms.utils;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Helper class used for various conversions and text formatting.
 */
public final class Formatter {

    private static final String NULL_VALUE = ChatColor.GRAY + ChatColor.ITALIC.toString() + "Invalid";
    private static final char COLOUR_CHAR = '&';

    public static @NotNull String parseBoolean(@Nullable Boolean bool) {
        if (bool == null) {
            return NULL_VALUE;
        }
        return bool ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No";
    }

    public static @NotNull String parseList(@Nullable Collection<String> list) {
        return parseList(list, null);
    }

    public static @NotNull String parseList(@Nullable Collection<String> list,
                                            @Nullable ChatColor color) {

        if (list == null) {
            return NULL_VALUE;
        }

        if (list.size() == 0) {
            return ChatColor.GRAY + ChatColor.ITALIC.toString() + "None";
        }

        return (color == null)
                ? String.join(", ", list)
                : color + String.join(", ", list);
    }

    public static @Nullable String colourise(@Nullable String text) {
        return (Strings.isNullOrEmpty(text))
                ? text
                : ChatColor.translateAlternateColorCodes(COLOUR_CHAR, text);
    }

    public static long millisecondsToTicks(int ms) {
        return (long) ms / 50;
    }

    public static float millisecondsToSeconds(int ms) {
        return (float) ms / 1000;
    }
}
