package dev.benergy10.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import dev.benergy10.flyperms.Constants.Commands;
import dev.benergy10.flyperms.Constants.MessageKey;
import dev.benergy10.flyperms.Constants.Permissions;
import dev.benergy10.flyperms.FlyPerms;
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
        this.messenger.send(sender, MessageKey.LISTGROUP_HEADER);
        this.plugin.getFPConfig()
                .getSpeedGroups()
                .forEach(group -> this.messenger.send(sender, MessageKey.LISTGROUP_SPEED_INFO,
                        group.getName(),
                        group.getLowerLimit(),
                        group.getUpperLimit())
                );
    }
}
