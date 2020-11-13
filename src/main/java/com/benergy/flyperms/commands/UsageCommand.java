package com.benergy.flyperms.commands;

import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.benergy.flyperms.FlyPerms;
import org.bukkit.command.CommandSender;

@CommandAlias("fp|flyperms|fperms|flypermissions")
public class UsageCommand extends FlyPermsCommand {

    public UsageCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand("help")
    @HelpCommand
    @CommandPermission("flyperms.help")
    @Syntax("[search]")
    @Description("Shows command usage.")
    public void doHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }
}
