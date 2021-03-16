package dev.benergy10.flyperms.commands;

import dev.benergy10.flyperms.constants.Commands;
import dev.benergy10.flyperms.constants.MessageKey;
import dev.benergy10.flyperms.constants.Permissions;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandPermission;
import dev.benergy10.minecrafttools.acf.annotation.Description;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
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
