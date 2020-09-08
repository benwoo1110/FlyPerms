package com.benergy.flyperms.utils;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.permissions.FlyState;
import sun.rmi.runtime.Log;

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

}
