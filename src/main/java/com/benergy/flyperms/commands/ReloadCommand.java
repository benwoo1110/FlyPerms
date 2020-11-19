package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.enums.Commands;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandAlias(Commands.BASE)
public class ReloadCommand extends FlyPermsCommand {

    public ReloadCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.RELOAD)
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
