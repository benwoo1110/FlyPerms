package dev.benergy10.flyperms.commands;

import dev.benergy10.flyperms.Constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.Constants.Commands;
import dev.benergy10.flyperms.utils.Formatter;
import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.Description;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RootCommand extends FlyPermsCommand {

    public RootCommand(@NotNull FlyPerms plugin) {
        super(plugin);
    }

    @CommandAlias(Commands.BASE)
    @Description("Base command for FlyPerms.")
    public void doBaseFPCommand(CommandSender sender) {
        this.messenger.send(sender, MessageKey.BASE_VERSION,
                this.plugin.getName(),
                this.plugin.getDescription().getVersion(),
                Formatter.parseList(this.plugin.getDescription().getAuthors())
        );
        this.messenger.send(sender, MessageKey.BASE_HELP, "/fp help");
    }
}
