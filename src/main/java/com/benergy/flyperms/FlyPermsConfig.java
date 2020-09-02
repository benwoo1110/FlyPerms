package com.benergy.flyperms;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class FlyPermsConfig {

    private FlyPerms plugin;

    private boolean checkGameMode = true;
    private boolean checkWorld = true;
    private List<String> disabledWorlds = new ArrayList<>();
    private boolean debugMode = false;

    public FlyPermsConfig(FlyPerms plugin, FileConfiguration config) {
        try {
            this.checkGameMode = config.getBoolean("check-for-gamemode");
            this.checkWorld = config.getBoolean("check-for-world");
            this.disabledWorlds = config.getStringList("ignore-in-worlds");
            this.debugMode = config.getBoolean("show-debug-info");
        } catch (Exception e) {
            e.printStackTrace();
            this.plugin.getLog().severe("Error loading config.yml!");
        }
    }

    public boolean isCheckGameMode() {
        return checkGameMode;
    }

    public boolean isCheckWorld() {
        return checkWorld;
    }

    public boolean haveDisabledWorld() {
        return disabledWorlds.size() > 0;
    }

    public List<String> getDisabledWorlds() {
        return disabledWorlds;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}
