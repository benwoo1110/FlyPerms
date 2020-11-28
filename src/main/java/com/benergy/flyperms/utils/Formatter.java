package com.benergy.flyperms.utils;

import org.bukkit.ChatColor;

import java.util.Collection;

public final class Formatter {

    public static String parseBoolean(Boolean bool) {
        if (bool == null) {
            return ChatColor.GRAY + ChatColor.ITALIC.toString() + "Invalid";
        }
        return bool ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No";
    }

    public static String parseList(Collection<String> list, ChatColor color) {
        if (list.size() == 0) {
            return ChatColor.GRAY + ChatColor.ITALIC.toString() + "None";
        }
        return color + String.join(", ", list);
    }

    public static String header(String title) {
        return ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "====[ " + title + " ]====";
    }

    public static long millisecondsToTicks(int ms) {
        return (long) ms / 50;
    }

    public static float millisecondsToSeconds(int ms) {
        return (float) ms / 1000;
    }
}
