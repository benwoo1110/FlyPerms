package com.benergy.flyperms;

import com.benergy.flyperms.api.FPConfig;
import com.benergy.flyperms.utils.Logging;
import com.benergy.flyperms.utils.SpeedGroup;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class FlyPermsConfig implements FPConfig {

    private final FlyPerms plugin;

    private boolean checkGameMode;
    private boolean checkWorld;
    private boolean allowCreative;
    private int checkInterval;
    private int coolDown;
    private List<String> disabledWorlds;
    private boolean debugMode;
    private final Map<String, SpeedGroup> speedGroups;

    public FlyPermsConfig(FlyPerms plugin) {
        this.plugin = plugin;
        speedGroups = new HashMap<>();
    }

    protected boolean reloadConfigValues() {
        try {
            this.plugin.reloadConfig();
        } catch (Exception e) {
            e.printStackTrace();
            Logging.log(Level.SEVERE, "Error reloading config! Ensure your yaml format is correct with a tool like http://www.yamllint.com/");
            return false;
        }
        return this.loadConfigValues();
    }

    protected boolean loadConfigValues() {
        try {
            FileConfiguration config = this.plugin.getConfig();
            this.checkWorld = config.getBoolean("check-for-world", true);
            this.checkGameMode = config.getBoolean("check-for-gamemode", false);
            this.allowCreative = config.getBoolean("always-allow-in-creative", true);
            this.checkInterval = config.getInt("check-interval", 1000);
            this.coolDown = config.getInt("cooldown", 5000);
            this.disabledWorlds = config.getStringList("ignore-in-worlds");
            this.debugMode = config.getBoolean("show-debug-info", false);
            loadSpeedGroups(config.getMapList("speed-group"));
        }
        catch (Exception e) {
            e.printStackTrace();
            Logging.log(Level.SEVERE, "Error loading config! Ensure your yaml format is correct with a tool like http://www.yamllint.com/");
            Logging.log(Level.SEVERE,"If you get this error after updating FlyPerms, there is most likely a config change. Please delete the config.yml and restart.");
            return false;
        }

        setLogLevel();
        Logging.log(Level.FINE, this.toString());
        Logging.log(Level.INFO, "Loaded config.yml");
        return true;
    }

    private void loadSpeedGroups(List<Map<?, ?>> speedGroupConfig) {
        if (speedGroupConfig == null) {
            return;
        }

        this.speedGroups.clear();

        for (Map<?, ?> group : speedGroupConfig) {
            for (Object rawGroupName : group.keySet()) {
                List<Double> speedValue = (List<Double>) group.get(rawGroupName);
                if (!validateSpeedValue(speedValue)) {
                    Logging.log(Level.WARNING, "Invalid speed group " + rawGroupName + ". Please check for config!");
                    continue;
                }
                String groupName = String.valueOf(rawGroupName);
                speedGroups.put(groupName, new SpeedGroup(groupName, speedValue.get(0), speedValue.get(1)));
            }
        }
    }

    private boolean validateSpeedValue(List<Double> speedValue) {
        return speedValue != null && speedValue.size() == 2 && speedValue.get(0) <= speedValue.get(1);
    }

    private void setLogLevel() {
        if (debugMode) {
            Logging.setLogLevel(Level.FINEST);
            Logging.log(Level.FINE, "Debug logging enabled.");
        }
        else {
            Logging.log(Level.FINE, "Debug logging disabled.");
            Logging.setLogLevel(Level.INFO);
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

    public Collection<String> getDisabledWorlds() {
        return Collections.unmodifiableList(disabledWorlds);
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public Map<String, SpeedGroup> getSpeedGroups() {
        return Collections.unmodifiableMap(speedGroups);
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
