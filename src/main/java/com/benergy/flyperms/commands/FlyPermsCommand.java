package com.benergy.flyperms.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import com.benergy.flyperms.FlyPerms;

@CommandAlias("flyperms|fp|fperms|flypermissions")
public class FlyPermsCommand extends BaseCommand {

    protected final FlyPerms plugin;

    public FlyPermsCommand(FlyPerms plugin) {
        this.plugin = plugin;
    }
}
