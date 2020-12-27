package dev.benergy10.flyperms.utils;

import co.aikar.commands.PaperCommandManager;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.commands.InfoCommand;
import dev.benergy10.flyperms.commands.ListGroupsCommand;
import dev.benergy10.flyperms.commands.ReloadCommand;
import dev.benergy10.flyperms.commands.RootCommand;
import dev.benergy10.flyperms.commands.SeeAllowedCommand;
import dev.benergy10.flyperms.commands.SpeedCommand;
import dev.benergy10.flyperms.commands.UsageCommand;

public class FPCommandManager extends PaperCommandManager {

    private final FlyPerms plugin;

    public FPCommandManager(FlyPerms plugin) {
        super(plugin);
        this.plugin = plugin;

        enableUnstableAPI("help");

        registerCommand(new RootCommand(this.plugin));
        registerCommand(new InfoCommand(this.plugin));
        registerCommand(new ReloadCommand(this.plugin));
        registerCommand(new SeeAllowedCommand(this.plugin));
        registerCommand(new SpeedCommand(this.plugin));
        registerCommand(new ListGroupsCommand(this.plugin));
        registerCommand(new UsageCommand(this.plugin));
    }
}
