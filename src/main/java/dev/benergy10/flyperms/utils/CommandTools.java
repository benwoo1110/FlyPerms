package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.commands.InfoCommand;
import dev.benergy10.flyperms.commands.ListGroupsCommand;
import dev.benergy10.flyperms.commands.ReloadCommand;
import dev.benergy10.flyperms.commands.RootCommand;
import dev.benergy10.flyperms.commands.SeeAllowedCommand;
import dev.benergy10.flyperms.commands.SpeedCommand;
import dev.benergy10.flyperms.commands.UsageCommand;
import dev.benergy10.minecrafttools.commands.CommandManager;
import org.jetbrains.annotations.NotNull;

public class CommandTools {

    public static void setUp(FlyPerms plugin) {
        new CommandTools(plugin);
    }

    private final FlyPerms plugin;

    public CommandTools(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
        CommandManager cm = plugin.getCommandManager();

        cm.enableUnstableAPI("help");
        cm.setDefaultHelpPerPage(6);

        cm.registerCommand(new RootCommand(this.plugin));
        cm.registerCommand(new InfoCommand(this.plugin));
        cm.registerCommand(new ReloadCommand(this.plugin));
        cm.registerCommand(new SeeAllowedCommand(this.plugin));
        cm.registerCommand(new SpeedCommand(this.plugin));
        cm.registerCommand(new ListGroupsCommand(this.plugin));
        cm.registerCommand(new UsageCommand(this.plugin));
    }


}
