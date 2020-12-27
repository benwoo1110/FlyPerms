package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.FlyPerms;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Handles console and debug logging for the plugin.
 */
public final class Logging {

    private static boolean doneSetup;
    private static String[] startupText;
    private static Logger logger;
    private static Logger debugLogger;
    private static boolean doDebug = false;

    public static void setup(FlyPerms plugin) {
        logger = Logger.getLogger(plugin.getName());
        debugLogger = Logger.getLogger(plugin.getName() + "-debug");
        startupText = new String[]{
                "§2    ___§3  __",
                "§2   /__§3  /__)   §aFlyPerms - v" + plugin.getDescription().getVersion(),
                "§2  /  §3  /       §bEnabled by - benwoo1110",
                ""
        };
        doneSetup = true;
    }

    private static void checkSetup() {
        if (!doneSetup) {
            throw new IllegalArgumentException("Logging has not been setup!");
        }
    }

    public static void info(String msg) {
        checkSetup();
        logger.info(msg);
    }

    public static void warning(String msg) {
        checkSetup();
        logger.warning(msg);
    }

    public static void severe(String msg) {
        checkSetup();
        logger.severe(msg);
    }

    public static void debug(String msg) {
        if (doDebug) {
            checkSetup();
            debugLogger.info(msg);
        }
    }

    public static void doDebugLog(boolean state) {
        doDebug = state;
    }

    public static boolean isDoDebug() {
        return doDebug;
    }

    public static void showStartUpText() {
        Arrays.stream(startupText).forEach(Bukkit.getServer().getConsoleSender()::sendMessage);
    }
}
