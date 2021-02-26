package dev.benergy10.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import dev.benergy10.flyperms.Constants.Commands;
import dev.benergy10.flyperms.Constants.MessageKey;
import dev.benergy10.flyperms.Constants.Permissions;
import dev.benergy10.flyperms.FlyPerms;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@CommandAlias(Commands.BASE)
public class ReloadCommand extends FlyPermsCommand {

    public ReloadCommand(@NotNull FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.RELOAD)
    @CommandPermission(Permissions.RELOAD)
    @Description("Reloads configuration.")
    public void onReload(CommandSender sender) {
        if (!this.plugin.reload()) {
            this.messenger.send(sender, MessageKey.RELOAD_ERROR);
            return;
        }
        this.messenger.send(sender, MessageKey.RELOAD_SUCCESS);
    }
}
