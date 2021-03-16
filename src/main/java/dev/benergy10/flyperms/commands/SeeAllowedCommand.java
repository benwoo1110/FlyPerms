package dev.benergy10.flyperms.commands;

import dev.benergy10.flyperms.constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.constants.Commands;
import dev.benergy10.flyperms.constants.Permissions;
import dev.benergy10.flyperms.managers.CheckManager;
import dev.benergy10.flyperms.utils.Formatter;
import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandCompletion;
import dev.benergy10.minecrafttools.acf.annotation.CommandPermission;
import dev.benergy10.minecrafttools.acf.annotation.Description;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.minecrafttools.acf.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias(Commands.BASE)
public class SeeAllowedCommand extends FlyPermsCommand {

    private final CheckManager checker;

    public SeeAllowedCommand(@NotNull FlyPerms plugin) {
        super(plugin);
        checker = this.plugin.getCheckManager();
    }

    @Subcommand(Commands.SEE_ALLOWED)
    @CommandPermission(Permissions.SEE_ALLOWED)
    @Description("Displays ability to fly.")
    public void onSeeAllowedSelf(@NotNull Player player) {
        showAllowedInfo(player, player);
    }

    @Subcommand(Commands.SEE_ALLOWED)
    @CommandPermission(Permissions.SEE_ALLOWED_OTHERS)
    @CommandCompletion("@players")
    @Syntax("[player]")
    @Description("Displays ability to fly for another player.")
    public void onSeeAllowedOthers(@NotNull CommandSender sender,
                                   @NotNull String playerName) {

        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            this.messenger.send(sender, MessageKey.ERROR_UNKNOWN_PLAYER, playerName);
            return;
        }
        showAllowedInfo(sender, targetPlayer);
    }

    private void showAllowedInfo(CommandSender sender, Player player) {
        this.messenger.send(sender, MessageKey.SEEALLOWED_HEADER, player.getName());
        this.messenger.send(sender, MessageKey.SEEALLOWED_CURRENT_WORLD, player.getWorld().getName());
        this.messenger.send(sender, MessageKey.SEEALLOWED_CURRENT_GAMEMODE, player.getGameMode().name().toLowerCase());
        this.messenger.send(sender, MessageKey.SEEALLOWED_WORLDS, Formatter.parseList(this.checker.getWorldChecker().getAllowedNames(player), ChatColor.WHITE));
        this.messenger.send(sender, MessageKey.SEEALLOWED_GAMEMODES, Formatter.parseList(this.checker.getGameModeChecker().getAllowedNames(player), ChatColor.WHITE));
        this.messenger.send(sender, MessageKey.SEEALLOWED_FLY_STATE, this.checker.calculateFlyState(player).toString());
        this.messenger.send(sender, MessageKey.SEEALLOWED_SPEED_GROUPS, Formatter.parseList(this.checker.getSpeedChecker().getAllowedNames(player), ChatColor.WHITE));
    }
}
