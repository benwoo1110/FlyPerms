package dev.benergy10.flyperms.commands;

import co.aikar.commands.annotation.*;
import dev.benergy10.flyperms.Constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.Constants.Commands;
import dev.benergy10.flyperms.Constants.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias(Commands.BASE)
public class SpeedCommand extends FlyPermsCommand {

    public SpeedCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.SPEED)
    @CommandPermission(Permissions.CHANGE_SPEED)
    @Syntax("<speed>")
    @Description("Changes fly speed, from -10 to 10.")
    public void onSpeed(Player player, String speed) {
        changeSpeed(player, player, speed, "your");
    }

    @Subcommand(Commands.SPEED)
    @CommandPermission(Permissions.CHANGE_SPEED_OTHERS)
    @CommandCompletion(" @players")
    @Syntax("<speed> [player]")
    @Description("Changes fly speed of another player, from -10 to 10.")
    public void onSpeedOther(CommandSender sender, String speed, String playerName) {
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            this.messenger.send(sender, MessageKey.ERROR_UNKNOWN_PLAYER, playerName);
            return;
        }
        changeSpeed(sender, targetPlayer, speed, playerName);
    }

    private void changeSpeed(CommandSender sender, Player targetPlayer, String speed, String name) {
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
