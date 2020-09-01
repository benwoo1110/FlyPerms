package com.benergy.flyperms;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class FlyPermsConfig {

    private final boolean checkGameMode;
    private final boolean checkWorld;
    private final List<String> disabledWorlds;
    private final boolean debugMode;

    public FlyPermsConfig(FileConfiguration config) {
        this.checkGameMode = config.getBoolean("check-for-gamemode");
        this.checkWorld = config.getBoolean("check-for-world");
        this.disabledWorlds = config.getStringList("ignore-in-worlds");
        this.debugMode = config.getBoolean("show-debug-info");
    }

    public boolean isCheckGameMode() {
        return checkGameMode;
    }

    public boolean isCheckWorld() {
        return checkWorld;
    }

    public List<String> getDisabledWorlds() {
        return disabledWorlds;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}
