package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

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

    public boolean inSpeedGroupRange(Player player, double speed) {
        for (Map.Entry<String, List<Double>> group : this.plugin.getFPConfig().getSpeedGroup().entrySet()) {
            if (player.hasPermission("flyperms.speed." + group.getKey().toLowerCase())
                    && (group.getValue().get(0) <= speed && group.getValue().get(1) >= speed)) {
                return true;
            }
        }
        return false;
    }

    public boolean inAnySpeedGroup(CommandSender sender) {
        for (Map.Entry<String, List<Double>> group : this.plugin.getFPConfig().getSpeedGroup().entrySet()) {
            if (sender.hasPermission("flyperms.speed." + group.getKey().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
