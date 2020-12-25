package dev.benergy10.flyperms.commands;

import co.aikar.commands.BaseCommand;
import dev.benergy10.flyperms.FlyPerms;

public abstract class FlyPermsCommand extends BaseCommand {
    
    protected final FlyPerms plugin;

    protected FlyPermsCommand(FlyPerms plugin) {
        this.plugin = plugin;
    }
}
