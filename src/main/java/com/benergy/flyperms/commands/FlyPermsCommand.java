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
            // "PlaceholderAPI", // API (TBA)
            // "WorldGuard", // API (TBA)
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
            case "help":
                help(sender);
                break;
            default:
                unknownCommand(sender);
        }

        return true;
    }

    private void reload(CommandSender sender) {
        if (!plugin.getFPCommand().canExecute(sender, "flyperms.reload")) {
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
        if (!plugin.getFPCommand().canExecute(sender, "flyperms.seeallowed")) {
            noPerms(sender);
            return;
        }

        // Get the player we want to see
        String playerNameToCheck = sender.getName();
        switch (args.length) {
            case 1:
                break;
            case 2:
                if (!plugin.getFPCommand().canExecute(sender, "flyperms.seeallowed.others")) {
                    noPerms(sender);
                    return;
                }
                playerNameToCheck = args[1];
                break;
            default:
                return;
        }

        // Ensure we are not checking for console
        if (playerNameToCheck.equalsIgnoreCase("CONSOLE")) {
            sender.sendMessage("You must enter a player to see from the console!");
            return;
        }

        // Ensure player exist
        Player playerToCheck = Bukkit.getPlayer(playerNameToCheck);
        if (playerToCheck == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player '"+ playerNameToCheck +"'");
            return;
        }

        // Show the info
        FormatUtil.header(sender, playerNameToCheck + " Flight Info");
        sender.sendMessage(ChatColor.AQUA + "Current world: " + ChatColor.WHITE + playerToCheck.getWorld().getName());
        sender.sendMessage(ChatColor.AQUA + "Current gamemode: " + ChatColor.WHITE + playerToCheck.getGameMode().name().toLowerCase());
        if (this.plugin.getFPConfig().isCheckWorld()) {
            sender.sendMessage(ChatColor.GREEN + "Only fly in worlds: " + FormatUtil.parseList(this.plugin.getFPFly().checkAllWorlds(playerToCheck), ChatColor.WHITE));
        }
        if (this.plugin.getFPConfig().isCheckGameMode()) {
            sender.sendMessage(ChatColor.GREEN + "Only fly in gamemodes: " + FormatUtil.parseList(this.plugin.getFPFly().checkAllGameModes(playerToCheck), ChatColor.WHITE));
        }
        sender.sendMessage(ChatColor.AQUA + "Currently can fly: " + this.plugin.getFPFly().canFly(playerToCheck).toString());
    }

    private void info(CommandSender sender) {
        if (!plugin.getFPCommand().canExecute(sender, "flyperms.info")) {
            noPerms(sender);
            return;
        }

        // Show the info
        FormatUtil.header(sender, "FlyPerms Info");
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
                sender.sendMessage(ChatColor.DARK_AQUA + versionPlugin.getName() + " version: " + ChatColor.RED + versionPlugin.getDescription().getVersion() + " (unsupported)");
            } else {
                sender.sendMessage(ChatColor.AQUA + versionPlugin.getName() + " version: " + ChatColor.GREEN + versionPlugin.getDescription().getVersion());
            }
        }
    }

    private void help(CommandSender sender) {
        if (!plugin.getFPCommand().canExecute(sender, "flyperms.help")) {
            noPerms(sender);
            return;
        }

        // show command info
        FormatUtil.header(sender, "FlyPerms Usage");
        FormatUtil.commandUsage(sender, "/fp info", "Displays basic information of the plugin");
        FormatUtil.commandUsage(sender, "/fp seeallowed [player]", "Displays player's ability to fly");
        FormatUtil.commandUsage(sender, "/fp reload", "Reloads the plugin config and fly access check");
        FormatUtil.commandUsage(sender, "/fp help", "It's this command ;)");
    }

    private void noPerms(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
    }

    private void unknownCommand(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Unknown FlyPerms command. See " + ChatColor.AQUA + "/fp help" + ChatColor.RED + " for command usage.");
    }
}
