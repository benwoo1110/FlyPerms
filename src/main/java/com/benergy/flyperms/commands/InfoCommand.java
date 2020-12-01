package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.Constants.Commands;
import com.benergy.flyperms.Constants.Permissions;
import com.benergy.flyperms.utils.Formatter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

@CommandAlias(Commands.BASE)
public class InfoCommand extends FlyPermsCommand {

    private final List<String> versionPlugins = Arrays.asList(
            "FlyPerms", // This plugin
            "PlaceholderAPI", // API
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

    public InfoCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.INFO)
    @CommandPermission(Permissions.INFO)
    @Description("Displays basic information of the plugin.")
    public void onInfo(CommandSender sender) {
        // Show the info
        sender.sendMessage(Formatter.header("FlyPerms Info"));

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

        sender.sendMessage(ChatColor.AQUA + "Check for worlds: " + Formatter.parseBoolean(this.plugin.getFPConfig().isCheckWorld()));
        sender.sendMessage(ChatColor.AQUA + "Check for gamemode: " + Formatter.parseBoolean(this.plugin.getFPConfig().isCheckGameMode()));
        sender.sendMessage(ChatColor.AQUA + "Always allow in creative: " + Formatter.parseBoolean(this.plugin.getFPConfig().isAllowCreative()));
        if (this.plugin.getFPConfig().haveIgnoreWorld()) {
            sender.sendMessage(ChatColor.AQUA + "Disabled in worlds: " + Formatter.parseList(this.plugin.getFPConfig().getIgnoreWorlds(), ChatColor.RED));
        }
    }
}
