package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.command.CommandSender;

public class PermsCommand {

    private final FlyPerms plugin;

    public PermsCommand(FlyPerms plugin) {
        this.plugin = plugin;
    }


    public boolean hasAnyPerms(CommandSender sender) {
        return sender.getName().equalsIgnoreCase("CONSOLE")
                || sender.hasPermission("flyperms.seeallowed")
                || sender.hasPermission("flyperms.info")
                || sender.hasPermission("flyperms.reload");
    }

}
