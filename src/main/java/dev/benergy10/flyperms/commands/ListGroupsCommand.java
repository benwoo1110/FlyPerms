package dev.benergy10.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.Constants.Commands;
import dev.benergy10.flyperms.Constants.Permissions;
import dev.benergy10.flyperms.utils.Formatter;
import org.bukkit.command.CommandSender;

@CommandAlias(Commands.BASE)
public class ListGroupsCommand extends FlyPermsCommand {

    public ListGroupsCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.LIST_GROUPS)
    @CommandPermission(Permissions.LIST_GROUPS)
    @Description("Show all the speed groups available.")
    public void onListGroups(CommandSender sender) {
        sender.sendMessage(Formatter.header("Speed Groups"));
        this.plugin.getFPConfig()
                .getSpeedGroups()
                .forEach(group -> sender.sendMessage(group.getName() + ": " + group.getLowerLimit() + " to " + group.getUpperLimit()));
    }
}
