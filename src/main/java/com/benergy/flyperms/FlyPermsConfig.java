package com.benergy.flyperms;

import com.benergy.flyperms.utils.FormatUtil;
import com.google.common.collect.Maps;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class FlyPermsConfig {

    private final FlyPerms plugin;

    private boolean checkGameMode;
    private boolean checkWorld;
    private boolean allowCreative;
    private int coolDown;
    private List<String> disabledWorlds;
    private boolean debugMode;
    private Map<String, List<Double>> speedGroup = new Hashtable<>();

    public FlyPermsConfig(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public boolean reloadConfigValues() {
        try {
            this.plugin.reloadConfig();
        } catch (Exception e) {
            e.printStackTrace();
            this.plugin.getFPLogger().log(Level.SEVERE, "Error reloading config! Ensure your yaml format is correct with a tool like http://www.yamllint.com/");
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
            this.coolDown = config.getInt("cooldown", 5000);
            this.disabledWorlds = config.getStringList("ignore-in-worlds");
            this.debugMode = config.getBoolean("show-debug-info", false);
            this.speedGroup.clear();
            List<Map<?, ?>> configSpeedGroup = config.getMapList("speed-group");
            for (Map<?, ?> group : configSpeedGroup) {
                for (Object groupName : group.keySet()) {
                    List<Double> speedValue = (List<Double>) group.get(groupName);
                    if (speedValue == null || speedValue.size() != 2) {
                        this.plugin.getFPLogger().log(Level.WARNING, "Invalid speed group " + groupName + ". Please check for config!");
                        continue;
                    }
                    speedGroup.put(String.valueOf(groupName), speedValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.plugin.getFPLogger().log(Level.SEVERE, "Error loading config! Ensure your yaml format is correct with a tool like http://www.yamllint.com/");
            this.plugin.getFPLogger().log(Level.SEVERE,"If you get this error after updating FlyPerms, there is most likely a config change. Please delete the config.yml and restart.");
            return false;
        }
        this.plugin.setLogLevel();
        this.plugin.getFPLogger().log(Level.FINE, this.toString());
        this.plugin.getFPLogger().log(Level.INFO, "Loaded config.yml");
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

    public Map<String, List<Double>> getSpeedGroup() {
        return speedGroup;
    }

    @Override
    public String toString() {
        return "FlyPermsConfig{" +
                "plugin=" + plugin +
                ", checkGameMode=" + checkGameMode +
                ", checkWorld=" + checkWorld +
                ", allowCreative=" + allowCreative +
                ", coolDown=" + coolDown +
                ", disabledWorlds=" + disabledWorlds +
                ", debugMode=" + debugMode +
                ", speedGroup=" + speedGroup +
                '}';
    }
}
