package com.benergy.flyperms.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.utils.FormatUtil;
import org.bukkit.command.CommandSender;

@CommandAlias("flyperms|fp|fperms|flypermissions")
public class UsageCommand extends FlyPermsCommand {

    public UsageCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand("help")
    @CommandPermission("flyperms.help")
    public void doHelp(CommandSender sender) {
        FormatUtil.header(sender, "FlyPerms Usage");
        FormatUtil.commandUsage(sender, "/fp info", "Displays basic information of the plugin");
        FormatUtil.commandUsage(sender, "/fp seeallowed [player]", "Displays player's ability to fly");
        FormatUtil.commandUsage(sender, "/fp reload", "Reloads the plugin config and fly access check");
        FormatUtil.commandUsage(sender, "/fp help", "It's this command ;)");
        FormatUtil.commandUsage(sender, "/fp speed <speed>", "Changes fly speed, from -10 to 10.");
    }
}
