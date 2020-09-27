package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class PermsCommand {

    private final FlyPerms plugin;

    public PermsCommand(FlyPerms plugin) {
        this.plugin = plugin;
    }


    public boolean hasAnyPerms(CommandSender sender) {
        return sender instanceof ConsoleCommandSender
                || sender.hasPermission("flyperms.seeallowed")
                || sender.hasPermission("flyperms.info")
                || sender.hasPermission("flyperms.reload")
                || sender.hasPermission("flyperms.help");
    }

}
