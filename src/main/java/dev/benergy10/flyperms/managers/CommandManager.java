package dev.benergy10.flyperms.managers;

import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.commands.InfoCommand;
import dev.benergy10.flyperms.commands.ListGroupsCommand;
import dev.benergy10.flyperms.commands.ReloadCommand;
import dev.benergy10.flyperms.commands.RootCommand;
import dev.benergy10.flyperms.commands.SeeAllowedCommand;
import dev.benergy10.flyperms.commands.SpeedCommand;
import dev.benergy10.flyperms.commands.UsageCommand;
import dev.benergy10.minecrafttools.acf.PaperCommandManager;
import org.jetbrains.annotations.NotNull;

public class CommandManager extends PaperCommandManager {

    private final FlyPerms plugin;

    public CommandManager(@NotNull FlyPerms plugin) {
        super(plugin);
        this.plugin = plugin;

        enableUnstableAPI("help");
        setDefaultHelpPerPage(6);

        registerCommand(new RootCommand(this.plugin));
        registerCommand(new InfoCommand(this.plugin));
        registerCommand(new ReloadCommand(this.plugin));
        registerCommand(new SeeAllowedCommand(this.plugin));
        registerCommand(new SpeedCommand(this.plugin));
        registerCommand(new ListGroupsCommand(this.plugin));
        registerCommand(new UsageCommand(this.plugin));
    }
}
