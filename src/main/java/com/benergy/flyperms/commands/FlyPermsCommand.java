package com.benergy.flyperms.commands;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.utils.FormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class FlyPermsCommand implements CommandExecutor {

    FlyPerms plugin;

    private final List<String> versionPlugins = Arrays.asList(
            "FlyPerms", // This plugin
            "PlaceholderAPI", // API (TBA)
            "WorldGuard", // API (TBA)
            "LuckPerms", // permissions (recommended)
            "UltraPermissions", // permissions (not recommended)
            "PowerRanks", // permissions (not recommended)
            "PermissionsEx", // permissions (unsupported)
            "GroupManager", // permissions (unsupported)
            "bPermissions" // permissions (unsupported)
    );

    private final List<String> unsupportedPlugins = Arrays.asList(
            "PermissionsEx", // permissions (unsupported)
            "GroupManager", // permissions (unsupported)
            "bPermissions" // permissions (unsupported)
    );

    public FlyPermsCommand(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Nothing to the command
        if (args.length == 0) {
            sender.sendMessage(ChatColor.AQUA + "Running FlyPerms " + ChatColor.GREEN +
                    "v" + this.plugin.getDescription().getVersion());
            return true;
        }

        // Run given arguments
        switch (args[0]) {
            case "seeallowed":
                seeallowed(sender, args);
                break;
            case "info":
                info(sender);
                break;
            case "reload":
                reload(sender);
                break;
        }

        return true;
    }

    private void reload(CommandSender sender) {
        if (!sender.getName().equalsIgnoreCase("CONSOLE") && !sender.hasPermission("flyperms.reload")) {
            noPerms(sender);
            return;
        }

        // reload the config
        if (!this.plugin.reload()) {
            sender.sendMessage(ChatColor.RED + "Error reloading FlyPerms, see console for more details.");
            return;
        }
        sender.sendMessage(ChatColor.GREEN + "Successfully reloaded FlyPerms!");
    }

    private void seeallowed(CommandSender sender, String[] args) {
        if (!sender.getName().equalsIgnoreCase("CONSOLE") && !sender.hasPermission("flyperms.seeallowed")) {
            noPerms(sender);
            return;
        }

        // Get the player we want to see
        String senderName = sender.getName();
        switch (args.length) {
            case 1:
                break;
            case 2:
                if (!sender.getName().equalsIgnoreCase("CONSOLE") && !sender.hasPermission("flyperms.seeallowed.others")) {
                    noPerms(sender);
                    return;
                }
                senderName = args[1];
                break;
            default:
                return;
        }

        // Ensure we are not checking for console
        if (senderName.equalsIgnoreCase("CONSOLE")) {
            sender.sendMessage("You must enter a player to see from the console!");
            return;
        }

        // Ensure player exist
        Player player = Bukkit.getPlayer(senderName);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player '"+ senderName +"'");
            return;
        }

        // Show the info
        sender.sendMessage(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "====[ "+ senderName + " Flight Info ]====");
        sender.sendMessage(ChatColor.AQUA + "Current world: " + ChatColor.WHITE + player.getWorld().getName());
        sender.sendMessage(ChatColor.AQUA + "Current gamemode: " + ChatColor.WHITE + player.getGameMode().name().toLowerCase());
        if (this.plugin.getFPConfig().isCheckWorld()) {
            sender.sendMessage(ChatColor.GREEN + "Only fly in worlds: " + FormatUtil.parseList(this.plugin.getFPFly().checkAllWorlds(player), ChatColor.WHITE));
        }
        if (this.plugin.getFPConfig().isCheckGameMode()) {
            sender.sendMessage(ChatColor.GREEN + "Only fly in gamemodes: " + FormatUtil.parseList(this.plugin.getFPFly().checkAllGameModes(player), ChatColor.WHITE));
        }
        sender.sendMessage(ChatColor.AQUA + "Currently can fly: " + this.plugin.getFPFly().canFly(player).toString());
    }

    private void info(CommandSender sender) {
        if (!sender.getName().equalsIgnoreCase("CONSOLE") && !sender.hasPermission("flyperms.info")) {
            noPerms(sender);
            return;
        }

        // Show the info
        sender.sendMessage(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "====[ FlyPerms Info ]====");
        showPlugins(sender);
        sender.sendMessage(ChatColor.AQUA + "Check for worlds: " + FormatUtil.parseBoolean(this.plugin.getFPConfig().isCheckWorld()));
        sender.sendMessage(ChatColor.AQUA + "Check for gamemode: " + FormatUtil.parseBoolean(this.plugin.getFPConfig().isCheckGameMode()));
        sender.sendMessage(ChatColor.AQUA + "Always allow in creative: " + FormatUtil.parseBoolean(this.plugin.getFPConfig().isAllowCreative()));
        if (this.plugin.getFPConfig().haveDisabledWorld()) {
            sender.sendMessage(ChatColor.AQUA + "Disabled in worlds: " + FormatUtil.parseList(this.plugin.getFPConfig().getDisabledWorlds(), ChatColor.RED));
        }
    }

    private void showPlugins(CommandSender sender) {
        for (Plugin versionPlugin : this.plugin.getServer().getPluginManager().getPlugins()) {
            if (!this.versionPlugins.contains(versionPlugin.getName())) {
                continue;
            }
            if (this.unsupportedPlugins.contains(versionPlugin.getName())) {
                sender.sendMessage(ChatColor.AQUA + versionPlugin.getName() + " version: " + ChatColor.RED + versionPlugin.getDescription().getVersion() + " (unsupported)");
            } else {
                sender.sendMessage(ChatColor.AQUA + versionPlugin.getName() + " version: " + ChatColor.GREEN + versionPlugin.getDescription().getVersion());
            }
        }
    }

    private void noPerms(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
    }
}
