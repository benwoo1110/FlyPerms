package com.benergy.flyperms.utils;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {

    private final FlyPerms plugin;
    private final Logger debugLogger;
    private Level logLevel = Level.INFO;

    public LoggerUtil(FlyPerms plugin) {
        this.plugin = plugin;
        debugLogger = Logger.getLogger(this.plugin.getName() + "-debug");
    }

    public void log(Level level, String msg) {
        if (level.intValue() < logLevel.intValue()) {
            return;
        }
        if (this.plugin.getFPConfig().isDebugMode() && level.intValue() < Level.INFO.intValue()) {
            debugLogger.info(msg);
            return;
        }
        this.plugin.getLogger().info(msg);
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public void startUpText() {
        String[] startupText = {
                "§2    ___§3  __",
                "§2   /__§3  /__)   §aFlyPerms - v" + this.plugin.getDescription().getVersion(),
                "§2  /  §3  /       §bbenwoo1110",
                ""
        };
        Arrays.stream(startupText).forEach(Bukkit.getServer().getConsoleSender()::sendMessage);
    }

}
