package dev.benergy10.flyperms.commands;

import dev.benergy10.flyperms.constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.constants.Commands;
import dev.benergy10.flyperms.constants.Permissions;
import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandCompletion;
import dev.benergy10.minecrafttools.acf.annotation.CommandPermission;
import dev.benergy10.minecrafttools.acf.annotation.Description;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.minecrafttools.acf.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias(Commands.BASE)
public class SpeedCommand extends FlyPermsCommand {

    public SpeedCommand(@NotNull FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.SPEED)
    @CommandPermission(Permissions.CHANGE_SPEED)
    @Syntax("<speed>")
    @Description("Changes fly speed, from -10 to 10.")
    public void onSpeed(@NotNull Player player,
                        @NotNull String speed) {

        changeSpeed(player, player, speed, "your");
    }

    @Subcommand(Commands.SPEED)
    @CommandPermission(Permissions.CHANGE_SPEED_OTHERS)
    @CommandCompletion(" @players")
    @Syntax("<speed> [player]")
    @Description("Changes fly speed of another player, from -10 to 10.")
    public void onSpeedOther(@NotNull CommandSender sender,
                             @NotNull String speed,
                             @NotNull String playerName) {

        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            this.messenger.send(sender, MessageKey.ERROR_UNKNOWN_PLAYER, playerName);
            return;
        }
        changeSpeed(sender, targetPlayer, speed, playerName);
    }

    private void changeSpeed(@NotNull CommandSender sender,
                             @NotNull Player targetPlayer,
                             @NotNull String speed,
                             @NotNull String name) {

        double parsedSpeed;
        try {
            parsedSpeed = Double.parseDouble(speed);
        }
        catch (NumberFormatException ignored) {
            this.messenger.send(sender, MessageKey.ERROR_NOT_NUMBER, speed);
            return;
        }
        // TODO: Add check for max speed range.

        if (this.plugin.getFlightManager().applyFlySpeed(targetPlayer, parsedSpeed)) {
            this.messenger.send(sender, MessageKey.SPEED_SET_SUCCESSFUL, name, speed);
            return;
        }
        this.messenger.send(sender, MessageKey.SPEED_SET_DISALLOWED, name);
    }
}
