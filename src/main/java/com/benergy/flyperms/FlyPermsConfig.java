package com.benergy.flyperms;

import com.benergy.flyperms.utils.FPLogger;
import com.benergy.flyperms.utils.SpeedRange;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class FlyPermsConfig {

    private final FlyPerms plugin;

    private boolean checkGameMode;
    private boolean checkWorld;
    private boolean allowCreative;
    private int checkInterval;
    private int coolDown;
    private List<String> disabledWorlds;
    private boolean debugMode;
    private final List<SpeedRange> speedGroups = new ArrayList<SpeedRange>();

    public FlyPermsConfig(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public boolean reloadConfigValues() {
        try {
            this.plugin.reloadConfig();
        } catch (Exception e) {
            e.printStackTrace();
            FPLogger.log(Level.SEVERE, "Error reloading config! Ensure your yaml format is correct with a tool like http://www.yamllint.com/");
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
            this.checkInterval = config.getInt("check-interval", 1000);
            this.coolDown = config.getInt("cooldown", 5000);
            this.disabledWorlds = config.getStringList("ignore-in-worlds");
            this.debugMode = config.getBoolean("show-debug-info", false);
            loadSpeedGroups(config.getMapList("speed-group"));
        }
        catch (Exception e) {
            e.printStackTrace();
            FPLogger.log(Level.SEVERE, "Error loading config! Ensure your yaml format is correct with a tool like http://www.yamllint.com/");
            FPLogger.log(Level.SEVERE,"If you get this error after updating FlyPerms, there is most likely a config change. Please delete the config.yml and restart.");
            return false;
        }

        setLogLevel();
        FPLogger.log(Level.FINE, this.toString());
        FPLogger.log(Level.INFO, "Loaded config.yml");
        return true;
    }

    private void loadSpeedGroups(List<Map<?, ?>> speedGroupConfig) {
        if (speedGroupConfig == null) {
            return;
        }

        this.speedGroups.clear();

        for (Map<?, ?> group : speedGroupConfig) {
            for (Object groupName : group.keySet()) {
                List<Double> speedValue = (List<Double>) group.get(groupName);
                if (speedValue == null || speedValue.size() != 2) {
                    FPLogger.log(Level.WARNING, "Invalid speed group " + groupName + ". Please check for config!");
                    continue;
                }
                speedGroups.add(new SpeedRange(String.valueOf(groupName), speedValue.get(0), speedValue.get(1)));
            }
        }
    }

    private void setLogLevel() {
        if (debugMode) {
            FPLogger.setLogLevel(Level.FINEST);
            FPLogger.log(Level.FINE, "Debug logging enabled.");
        }
        else {
            FPLogger.log(Level.FINE, "Debug logging disabled.");
            FPLogger.setLogLevel(Level.INFO);
        }
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

    public int getCheckInterval() {
        return checkInterval;
    }

    public int getCoolDown() {
        return coolDown;
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

    public List<SpeedRange> getSpeedGroups() {
        return speedGroups;
    }

    @Override
    public String toString() {
        return "FlyPermsConfig{" +
                "plugin=" + plugin +
                ", checkGameMode=" + checkGameMode +
                ", checkWorld=" + checkWorld +
                ", allowCreative=" + allowCreative +
                ", checkInterval=" + checkInterval +
                ", coolDown=" + coolDown +
                ", disabledWorlds=" + disabledWorlds +
                ", debugMode=" + debugMode +
                ", speedGroups=" + speedGroups +
                '}';
    }
}
