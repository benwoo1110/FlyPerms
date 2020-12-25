package dev.benergy10.flyperms.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.Constants.Commands;
import dev.benergy10.flyperms.Constants.Permissions;
import dev.benergy10.flyperms.utils.CheckManager;
import dev.benergy10.flyperms.utils.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias(Commands.BASE)
public class SeeAllowedCommand extends FlyPermsCommand {

    private final CheckManager checker;

    public SeeAllowedCommand(FlyPerms plugin) {
        super(plugin);
        checker = this.plugin.getCheckManager();
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
        if (this.checker.getWorldChecker().isEnabled()) {
            sender.sendMessage(ChatColor.GREEN + "Only fly in worlds: " + Formatter.parseList(this.checker.getWorldChecker().getAllowedNames(player), ChatColor.WHITE));
        }
        if (this.checker.getGameModeChecker().isEnabled()) {
            sender.sendMessage(ChatColor.GREEN + "Only fly in gamemodes: " + Formatter.parseList(this.checker.getGameModeChecker().getAllowedNames(player), ChatColor.WHITE));
        }
        sender.sendMessage(ChatColor.AQUA + "Currently can fly: " + this.checker.calculateFlyState(player).toString());
        sender.sendMessage(ChatColor.AQUA + "In speed groups: " + Formatter.parseList(this.checker.getSpeedChecker().getAllowedNames(player), ChatColor.WHITE));
    }
}
