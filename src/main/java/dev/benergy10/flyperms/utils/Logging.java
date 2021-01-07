package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.FlyPerms;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.logging.Logger;

/**
 * Handles console and debug logging for the plugin.
 */
public final class Logging {

    private static Logger logger;
    private static Logger debugLogger;
    private static boolean doDebug = false;

    public static void setup(FlyPerms plugin) {
        logger = Logger.getLogger(plugin.getName());
        debugLogger = Logger.getLogger(plugin.getName() + "-debug");
    }

    public static void info(String msg, Object... args) {
        logger.info(format(msg, args));
    }

    public static void warning(String msg, Object... args) {
        logger.warning(format(msg, args));
    }

    public static void severe(String msg, Object... args) {
        logger.severe(format(msg, args));
    }

    public static void debug(String msg, Object... args) {
        if (doDebug) {
            debugLogger.info(format(msg, args));
        }
    }

    private static String format(String msg, Object[] args) {
        try {
            return String.format(msg, args);
        }
        catch (IllegalFormatException e) {
            logger.warning("Illegal format in the following message:");
            return msg;
        }
    }

    public static void doDebugLog(boolean state) {
        doDebug = state;
    }

    public static boolean isDoDebug() {
        return doDebug;
    }

    public static void showStartUpText(FlyPerms plugin) {
        String[] startupText = new String[]{
                "§2    ___§3  __",
                "§2   /__§3  /__)   §aFlyPerms - v" + plugin.getDescription().getVersion(),
                "§2  /  §3  /       §bEnabled by - benwoo1110",
                ""
        };
        Arrays.stream(startupText).forEach(Bukkit.getServer().getConsoleSender()::sendMessage);
    }
}
