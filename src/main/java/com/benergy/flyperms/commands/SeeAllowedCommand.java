package com.benergy.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.enums.Commands;
import com.benergy.flyperms.enums.Permissions;
import com.benergy.flyperms.utils.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias(Commands.BASE)
public class SeeAllowedCommand extends FlyPermsCommand {

    public SeeAllowedCommand(FlyPerms plugin) {
        super(plugin);
    }

    @Subcommand(Commands.SEE_ALLOWED)
    @CommandPermission(Permissions.SEE_ALLOWED)
    @Description("Displays ability to fly.")
    public void onSeeAllowedSelf(Player player) {
        showAllowedInfo(player, player);
    }

    @Subcommand(Commands.SEE_ALLOWED)
    @CommandPermission(Permissions.SEE_ALLOWED_OTHERS)
    @CommandCompletion("@players")
    @Syntax("[player]")
    @Description("Displays ability to fly for another player.")
    public void onSeeAllowedOthers(CommandSender sender, String playerName) {
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Unknown player '"+ playerName +"'");
            return;
        }
        showAllowedInfo(sender, targetPlayer);
    }

    private void showAllowedInfo(CommandSender sender, Player player) {
        sender.sendMessage(Formatter.header(player.getName() + " Flight Info"));
        sender.sendMessage(ChatColor.AQUA + "Current world: " + ChatColor.WHITE + player.getWorld().getName());
        sender.sendMessage(ChatColor.AQUA + "Current gamemode: " + ChatColor.WHITE + player.getGameMode().name().toLowerCase());
        if (this.plugin.getFPConfig().isCheckWorld()) {
            sender.sendMessage(ChatColor.GREEN + "Only fly in worlds: " + Formatter.parseList(this.plugin.getFlyChecker().allowInWorlds(player), ChatColor.WHITE));
        }
        if (this.plugin.getFPConfig().isCheckGameMode()) {
            sender.sendMessage(ChatColor.GREEN + "Only fly in gamemodes: " + Formatter.parseList(this.plugin.getFlyChecker().allowInGameModes(player), ChatColor.WHITE));
        }
        sender.sendMessage(ChatColor.AQUA + "Currently can fly: " + this.plugin.getFlyChecker().canFly(player).toString());
        sender.sendMessage(ChatColor.AQUA + "In speed groups: " + Formatter.parseList(this.plugin.getSpeedChecker().inSpeedGroups(player), ChatColor.WHITE));
    }
}
