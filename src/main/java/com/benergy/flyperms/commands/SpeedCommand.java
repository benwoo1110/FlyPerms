package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.benergy.flyperms.FlyPerms;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("flyperms|fp|fperms|flypermissions")
public class SpeedCommand extends FlyPermsCommand {

    public SpeedCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand("speed")
    @CommandPermission("flyperms.speed")
    @Syntax("<speed>")
    public void onSpeed(Player player, int speed) {
        if (!this.plugin.getFPCommand().canChangeSpeedTo(player, speed)) {
            player.sendMessage(ChatColor.RED + "You are not allowed to set fly to this speed!");
            return;
        }
        player.setFlySpeed((float) speed / 10);
        player.sendMessage("Successfully set flying speed to " + speed);
    }
}
