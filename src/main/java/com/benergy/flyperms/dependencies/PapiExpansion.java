package com.benergy.flyperms.dependencies;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.utils.CheckManager;
import com.benergy.flyperms.utils.Formatter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * This class will be registered through the register-method in the
 * plugins onEnable-method.
 */
public class PapiExpansion extends PlaceholderExpansion {

    private final FlyPerms plugin;
    private final CheckManager checker;

    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     * @param plugin
     *        The instance of our plugin.
     */
    public PapiExpansion(FlyPerms plugin) {
        this.plugin = plugin;
        this.checker = this.plugin.getCheckManager();
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>The identifier has to be lowercase and can't contain _ or %
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier() {
        return "flyperms";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.entity.Player Player}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (player == null || identifier.length() == 0) {
            return "";
        }

        if (identifier.equals("status")) {
            return this.plugin.getCheckManager().calculateFlyState(player).toString();
        }

        if (identifier.equals("in_worlds")) {
            return Formatter.parseList(this.checker.getWorldChecker().getAllowedNames(player), ChatColor.WHITE);
        }

        if (identifier.equals("in_gamemodes")) {
            return Formatter.parseList(this.checker.getGameModeChecker().getAllowedNames(player), ChatColor.WHITE);
        }

        if (identifier.equals("in_speedgroups")) {
            return Formatter.parseList(this.checker.getSpeedChecker().getAllowedNames(player), ChatColor.WHITE);
        }

        if (identifier.startsWith("in_world_")) {
            String targetWorld = identifier.substring(9);
            return Formatter.parseBoolean(this.checker.getWorldChecker().hasPerm(player, targetWorld));
        }

        if (identifier.startsWith("in_gamemode_")) {
            String targetGameMode = identifier.substring(12);
            return Formatter.parseBoolean(this.checker.getGameModeChecker().hasPerm(player, targetGameMode));
        }

        if (identifier.startsWith("in_speedgroup_")) {
            String targetSpeedGroup = identifier.substring(14);
            return Formatter.parseBoolean(this.checker.getSpeedChecker().hasPerm(player, targetSpeedGroup));
        }

        if (identifier.equals("list_speedgroups")) {
            return Formatter.parseList(this.plugin.getFPConfig().getSpeedGroupNames(), ChatColor.WHITE);
        }

        return null;
    }
}
