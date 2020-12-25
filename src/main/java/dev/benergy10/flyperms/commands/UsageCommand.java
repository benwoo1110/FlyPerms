package dev.benergy10.flyperms.commands;

import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.Constants.Commands;
import dev.benergy10.flyperms.Constants.Permissions;
import org.bukkit.command.CommandSender;

@CommandAlias(Commands.BASE)
public class UsageCommand extends FlyPermsCommand {

    public UsageCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.USAGE)
    @CommandPermission(Permissions.USAGE)
    @HelpCommand
    @Syntax("[search]")
    @Description("Shows command usage.")
    public void doHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }
}