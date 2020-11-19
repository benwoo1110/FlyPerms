package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.enums.Commands;
import com.benergy.flyperms.enums.Permissions;
import com.benergy.flyperms.utils.Formatter;
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
        Formatter.header(sender, "Speed Groups");
        this.plugin.getFPConfig()
                .getSpeedGroups()
                .forEach(group -> sender.sendMessage(group.getName() + ": " + group.getLowerLimit() + " to " + group.getUpperLimit()));
    }
}
