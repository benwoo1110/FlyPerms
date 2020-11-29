package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.*;
import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.Constants.Commands;
import com.benergy.flyperms.Constants.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            sender.sendMessage(ChatColor.RED + "Unknown player '"+ playerName +"'");
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
            sender.sendMessage(ChatColor.RED + "Error: '" + speed + "' is not a number.");
            return;
        }

        sender.sendMessage(this.plugin.getSpeedManager().applyFlySpeed(targetPlayer, parsedSpeed)
                ? "Successfully set " + name + " flying speed to " + speed
                : ChatColor.RED + "You are not allowed to set fly to this speed!");
    }
}
