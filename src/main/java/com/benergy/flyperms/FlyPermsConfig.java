package com.benergy.flyperms;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class FlyPermsConfig {

    private FlyPerms plugin;

    private boolean checkGameMode;
    private boolean checkWorld;
    private boolean allowCreative;
    private List<String> disabledWorlds;
    private boolean debugMode;

    public FlyPermsConfig(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public boolean reloadConfigValues() {
        try {
            this.plugin.reloadConfig();
        } catch (Exception e) {
            e.printStackTrace();
            this.plugin.getLog().severe("Error reloading config! Ensure your yaml format is correct with a tool like http://www.yamllint.com/");
            return false;
        }
        return this.loadConfigValues();
    }

    public boolean loadConfigValues() {
        try {
            FileConfiguration config = this.plugin.getConfig();
            this.checkGameMode = config.getBoolean("check-for-gamemode", true);
            this.checkWorld = config.getBoolean("check-for-world", true);
            this.allowCreative = config.getBoolean("always-allow-in-creative", false);
            this.disabledWorlds = config.getStringList("ignore-in-worlds");
            this.debugMode = config.getBoolean("show-debug-info", false);
        } catch (Exception e) {
            e.printStackTrace();
            this.plugin.getLog().severe("Error loading config.yml!");
            this.plugin.getLog().severe("If you get this error after updating FlyPerms, there is most likely a config change. Please delete the config.yml and restart.");
            return false;
        }
        return true;
    }

    public boolean isCheckGameMode() {
        return checkGameMode;
    }

    public boolean isCheckWorld() {
        return checkWorld;
    }

    public boolean isAllowCreative() {
        return allowCreative;
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
