package dev.benergy10.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.Constants.Commands;
import dev.benergy10.flyperms.Constants.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandAlias(Commands.BASE)
public class ReloadCommand extends FlyPermsCommand {

    public ReloadCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.RELOAD)
    @CommandPermission(Permissions.RELOAD)
    @Description("Reloads configuration.")
    public void onReload(CommandSender sender) {
        if (!this.plugin.reload()) {
            sender.sendMessage(ChatColor.RED + "Error reloading FlyPerms, see console for more details.");
            return;
        }
        sender.sendMessage(ChatColor.GREEN + "Successfully reloaded FlyPerms!");
    }
}
