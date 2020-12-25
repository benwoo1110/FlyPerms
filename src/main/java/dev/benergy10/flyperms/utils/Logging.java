package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.FlyPerms;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * Handles console and debug logging for the plugin.
 */
public class Logging {

    private static boolean doneSetup;
    private static String[] startupText;
    private static java.util.logging.Logger logger;
    private static java.util.logging.Logger debugLogger;
    private static Level logLevel = Level.ALL;

    public static void setup(FlyPerms plugin) {
        logger = java.util.logging.Logger.getLogger(plugin.getName());
        debugLogger = java.util.logging.Logger.getLogger(plugin.getName() + "-debug");
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

    public static void log(Level level, String msg) {
        checkSetup();

        if (level.intValue() < logLevel.intValue()) {
            return;
        }
        if (level.intValue() < Level.INFO.intValue()) {
            debugLogger.info(msg);
            return;
        }

        logger.log(level, msg);
    }

    public static Level getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(Level level) {
        logLevel = level;
    }

    public static void showStartUpText() {
        Arrays.stream(startupText).forEach(Bukkit.getServer().getConsoleSender()::sendMessage);
    }
}
