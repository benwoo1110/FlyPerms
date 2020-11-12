package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.utils.FormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("flyperms|fp|fperms|flypermissions")
public class SeeAllowedCommand extends FlyPermsCommand {

    public SeeAllowedCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand("seeallowed")
    @CommandPermission("flyperms.seeallowed")
    public void onSeeAllowedSelf(Player player) {
        showAllowedInfo(player, player);
    }

    @Subcommand("seeallowed")
    @CommandPermission("flyperms.seeallowed.others")
    @CommandCompletion("@players")
    @Syntax("[player]")
    public void onSeeAllowedOthers(CommandSender sender, String playerName) {
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player '"+ playerName +"'");
            return;
        }
        showAllowedInfo(sender, targetPlayer);
    }

    private void showAllowedInfo(CommandSender sender, Player player) {
        FormatUtil.header(player, player.getName() + " Flight Info");
        sender.sendMessage(ChatColor.AQUA + "Current world: " + ChatColor.WHITE + player.getWorld().getName());
        sender.sendMessage(ChatColor.AQUA + "Current gamemode: " + ChatColor.WHITE + player.getGameMode().name().toLowerCase());
        if (this.plugin.getFPConfig().isCheckWorld()) {
            sender.sendMessage(ChatColor.GREEN + "Only fly in worlds: " + FormatUtil.parseList(this.plugin.getFPFly().checkAllWorlds(player), ChatColor.WHITE));
        }
        if (this.plugin.getFPConfig().isCheckGameMode()) {
            sender.sendMessage(ChatColor.GREEN + "Only fly in gamemodes: " + FormatUtil.parseList(this.plugin.getFPFly().checkAllGameModes(player), ChatColor.WHITE));
        }
        sender.sendMessage(ChatColor.AQUA + "Currently can fly: " + this.plugin.getFPFly().canFly(player).toString());
    }
}