package dev.benergy10.flyperms.commands;

import dev.benergy10.flyperms.configuration.ConfigOptions;
import dev.benergy10.flyperms.constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.constants.Commands;
import dev.benergy10.flyperms.constants.Permissions;
import dev.benergy10.flyperms.utils.Formatter;
import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandPermission;
import dev.benergy10.minecrafttools.acf.annotation.Description;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@CommandAlias(Commands.BASE)
public class InfoCommand extends FlyPermsCommand {

    private final List<String> versionPlugins = Arrays.asList(
            "FlyPerms",         // This plugin
            "PlaceholderAPI",   // API
            // "WorldGuard",    // API (TBA)
            "LuckPerms",        // permissions (recommended)
            "UltraPermissions", // permissions (not recommended)
            "PowerRanks"        // permissions (not recommended)
    );

    private final List<String> unsupportedPlugins = Arrays.asList(
            "PermissionsEx",    // permissions (unsupported)
            "GroupManager",     // permissions (unsupported)
            "bPermissions"      // permissions (unsupported)
    );

    public InfoCommand(@NotNull FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.INFO)
    @CommandPermission(Permissions.INFO)
    @Description("Displays basic information of the plugin.")
    public void onInfo(CommandSender sender) {
        this.messenger.send(sender, MessageKey.INFO_HEADER);
        for (Plugin versionPlugin : this.plugin.getServer().getPluginManager().getPlugins()) {
            if (this.versionPlugins.contains(versionPlugin.getName())) {
                this.messenger.send(sender, MessageKey.INFO_PLUGIN_SUPPORTED, versionPlugin.getName(), versionPlugin.getDescription().getVersion());
                continue;
            }
            if (this.unsupportedPlugins.contains(versionPlugin.getName())) {
                this.messenger.send(sender, MessageKey.INFO_PLUGIN_UNSUPPORTED, versionPlugin.getName(), versionPlugin.getDescription().getVersion());
            }
        }
        this.messenger.send(sender, MessageKey.INFO_CHECK_WORLD, Formatter.parseBoolean(this.plugin.getFPConfig().getValue(ConfigOptions.CHECK_WORLD)));
        this.messenger.send(sender, MessageKey.INFO_CHECK_GAMEMODE, Formatter.parseBoolean(this.plugin.getFPConfig().getValue(ConfigOptions.CHECK_GAMEMODE)));
        this.messenger.send(sender, MessageKey.INFO_ALLOW_CREATIVE, Formatter.parseBoolean(this.plugin.getFPConfig().getValue(ConfigOptions.ALLOW_IN_CREATIVE)));
        this.messenger.send(sender, MessageKey.INFO_DISABLED_WORLDS, Formatter.parseList(this.plugin.getFPConfig().getValue(ConfigOptions.IGNORE_WORLDS), ChatColor.RED));
    }
}
