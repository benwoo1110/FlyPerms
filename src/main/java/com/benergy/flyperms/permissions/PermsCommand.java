package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PermsCommand {

    private final FlyPerms plugin;

    public PermsCommand(FlyPerms plugin) {
        this.plugin = plugin;
    }


    public boolean hasAnyPerms(CommandSender sender) {
        return isConsole(sender)
                || sender.hasPermission("flyperms.seeallowed")
                || sender.hasPermission("flyperms.info")
                || sender.hasPermission("flyperms.reload")
                || sender.hasPermission("flyperms.help");
    }

    public boolean canExecute(CommandSender sender, String perm) {
        return isConsole(sender) || (sender instanceof Player && sender.hasPermission(perm));
    }

    public boolean isConsole(CommandSender sender) {
        return sender instanceof ConsoleCommandSender;
    }

}
