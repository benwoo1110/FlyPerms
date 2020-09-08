package com.benergy.flyperms.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class FormatUtil {

    public static String parseBoolean(Boolean bool) {
        if (bool == null) {
            return ChatColor.GRAY + ChatColor.ITALIC.toString() + "Ignored";
        }
        return bool ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No";
    }

    public static String parseList(List<String> list, ChatColor color) {
        if (list.size() == 0) {
            return ChatColor.GRAY + ChatColor.ITALIC.toString() + "None";
        }
        return color + String.join(", ", list);
    }
}
