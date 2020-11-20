package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.*;
import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.enums.Commands;
import com.benergy.flyperms.enums.Permissions;
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
    public void onSpeed(Player player, int speed) {
        changeSpeed(player, player, speed);
    }

    @Subcommand(Commands.SPEED)
    @CommandPermission(Permissions.CHANGE_SPEED_OTHERS)
    @CommandCompletion(" @players")
    @Syntax("<speed> [player]")
    @Description("Displays ability to fly for another player.")
    public void onSpeedOther(CommandSender sender, int speed, Player targetPlayer) {
        changeSpeed(sender, targetPlayer, speed);
    }

    private void changeSpeed(CommandSender sender, Player targetPlayer, int speed) {
        if (!this.plugin.getSpeedChecker().canChangeSpeedTo(targetPlayer, speed)) {
            sender.sendMessage(ChatColor.RED + "You are not allowed to set fly to this speed!");
            return;
        }
        targetPlayer.setFlySpeed((float) speed / 10);
        sender.sendMessage("Successfully set flying speed to " + speed);
    }
}
