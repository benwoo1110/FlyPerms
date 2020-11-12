package com.benergy.flyperms.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Formatter {

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

    public static void header(CommandSender sender, String title) {
        sender.sendMessage(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "====[ " + title + " ]====");
    }

    public static void commandUsage(CommandSender sender, String command, String description) {
        sender.sendMessage(ChatColor.GREEN + command + ChatColor.GRAY + " -> " + ChatColor.AQUA + description);
    }

    public static long millisecondsToTicks(int ms) {
        return (long) ms / 50;
    }

    public static float millisecondsToSeconds(int ms) {
        return (float) ms / 1000;
    }

}
