package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.enums.Commands;
import com.benergy.flyperms.enums.Permissions;
import org.bukkit.ChatColor;
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
        if (!this.plugin.getSpeedChecker().canChangeSpeedTo(player, speed)) {
            player.sendMessage(ChatColor.RED + "You are not allowed to set fly to this speed!");
            return;
        }
        player.setFlySpeed((float) speed / 10);
        player.sendMessage("Successfully set flying speed to " + speed);
    }
}
