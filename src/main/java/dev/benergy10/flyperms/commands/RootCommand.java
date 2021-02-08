package dev.benergy10.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import dev.benergy10.flyperms.Constants.MessageKey;
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
        this.messenger.send(sender, MessageKey.BASE_HELP,
                this.plugin.getName(),
                this.plugin.getDescription().getVersion(),
                this.plugin.getDescription().getAuthors()
        );
        this.messenger.send(sender, MessageKey.BASE_HELP, "/fp help");
    }
}
