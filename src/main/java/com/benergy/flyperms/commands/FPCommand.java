package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import com.benergy.flyperms.FlyPerms;
import org.bukkit.command.CommandSender;

public class FPCommand extends FlyPermsCommand {

    public FPCommand(FlyPerms plugin) {
        super(plugin);
    }

    @CommandAlias("fp|flyperms|fperms|flypermissions")
    @Description("Base command for FlyPerms.")
    public void doBaseFPCommand(CommandSender sender) {
        sender.sendMessage("§aFlyPerms v" + this.plugin.getDescription().getVersion() + "§8 |§b Enabled by - benwoo1110");
        sender.sendMessage("§3See §b/fp help §3for available commands.");
    }
}
