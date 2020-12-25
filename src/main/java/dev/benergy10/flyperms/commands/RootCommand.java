package dev.benergy10.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.Constants.Commands;
import org.bukkit.command.CommandSender;

public class RootCommand extends FlyPermsCommand {

    public RootCommand(FlyPerms plugin) {
        super(plugin);
    }

    @CommandAlias(Commands.BASE)
    @Description("Base command for FlyPerms.")
    public void doBaseFPCommand(CommandSender sender) {
        sender.sendMessage("§aFlyPerms v" + this.plugin.getDescription().getVersion() + "§8 |§b Enabled by - benwoo1110");
        sender.sendMessage("§3See §b/fp help §3for available commands.");
    }
}
