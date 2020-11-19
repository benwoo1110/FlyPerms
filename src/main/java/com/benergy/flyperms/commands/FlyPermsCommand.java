package com.benergy.flyperms.commands;

import co.aikar.commands.BaseCommand;
import com.benergy.flyperms.FlyPerms;

public abstract class FlyPermsCommand extends BaseCommand {

    protected final FlyPerms plugin;

    protected FlyPermsCommand(FlyPerms plugin) {
        this.plugin = plugin;
    }
}
