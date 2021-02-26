package dev.benergy10.flyperms.commands;

import co.aikar.commands.CommandHelp;
import co.aikar.commands.HelpEntry;
import co.aikar.commands.annotation.*;
import dev.benergy10.flyperms.Constants.Commands;
import dev.benergy10.flyperms.Constants.Permissions;
import dev.benergy10.flyperms.FlyPerms;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandAlias(Commands.BASE)
public class UsageCommand extends FlyPermsCommand {

    public UsageCommand(@NotNull FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.USAGE)
    @CommandPermission(Permissions.USAGE)
    @HelpCommand
    @Syntax("[search]")
    @Description("Shows command usage.")
    public void doHelp(@NotNull CommandHelp help) {
        List<HelpEntry> entries = help.getHelpEntries();
        if (entries.size() == 1) {
            this.plugin.getCommandManager().getHelpFormatter().showDetailedHelp(help, entries.get(0));
            return;
        }

        help.showHelp();
    }
}
