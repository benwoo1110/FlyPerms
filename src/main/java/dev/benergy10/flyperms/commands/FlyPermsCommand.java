package dev.benergy10.flyperms.commands;

import co.aikar.commands.BaseCommand;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.MessageProvider;
import org.jetbrains.annotations.NotNull;

public abstract class FlyPermsCommand extends BaseCommand {
    
    protected final FlyPerms plugin;
    protected final MessageProvider messenger;

    protected FlyPermsCommand(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
        this.messenger = plugin.getMessageProvider();
    }
}
