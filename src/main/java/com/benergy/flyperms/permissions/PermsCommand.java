package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.utils.SpeedRange;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

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

    public boolean canChangeSpeedTo(Player player, double speed) {
        for (SpeedRange speedRange : this.plugin.getFPConfig().getSpeedGroups()) {
            if (player.hasPermission("flyperms.speed." + speedRange.getName()) && speedRange.isInRange(speed)) {
                return true;
            }
        }
        return false;
    }

    public boolean inAnySpeedGroup(CommandSender sender) {
        for (SpeedRange speedRange : this.plugin.getFPConfig().getSpeedGroups()) {
            if (sender.hasPermission("flyperms.speed." + speedRange.getName())) {
                return true;
            }
        }
        return false;
    }
}
