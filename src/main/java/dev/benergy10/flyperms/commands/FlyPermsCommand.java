package dev.benergy10.flyperms.commands;

import co.aikar.commands.BaseCommand;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.MessageProvider;

public abstract class FlyPermsCommand extends BaseCommand {
    
    protected final FlyPerms plugin;
    protected final MessageProvider messenger;

    protected FlyPermsCommand(FlyPerms plugin) {
        this.plugin = plugin;
        this.messenger = plugin.getMessageProvider();
    }
}
