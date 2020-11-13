package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.benergy.flyperms.FlyPerms;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandAlias("fp|flyperms|fperms|flypermissions")
public class ReloadCommand extends FlyPermsCommand {

    public ReloadCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand("reload")
    @CommandPermission("flyperms.reload")
    @Description("Reloads configuration.")
    public void onReload(CommandSender sender) {
        if (!this.plugin.reload()) {
            sender.sendMessage(ChatColor.RED + "Error reloading FlyPerms, see console for more details.");
            return;
        }
        sender.sendMessage(ChatColor.GREEN + "Successfully reloaded FlyPerms!");
    }
}
