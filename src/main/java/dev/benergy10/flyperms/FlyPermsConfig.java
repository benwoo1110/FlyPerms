package dev.benergy10.flyperms;

import dev.benergy10.flyperms.api.FPConfig;
import dev.benergy10.flyperms.utils.Logging;
import dev.benergy10.flyperms.utils.SpeedGroup;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * {@inheritDoc}
 */
public class FlyPermsConfig implements FPConfig {

    private final FlyPerms plugin;
    private final FileConfiguration config;

    private boolean checkGameMode;
    private boolean checkWorld;
    private boolean allowCreative;
    private int checkInterval;
    private int coolDown;
    private boolean autoFlyOnAirTeleport;
    private boolean resetSpeedOnWorldChange;
    private boolean resetSpeedOnGameModeChange;
    private double resetSpeedValue;
    private Map<String, SpeedGroup> speedGroups;
    private List<String> disabledWorlds;
    private boolean hookPapi;
    private boolean debugMode;

    public FlyPermsConfig(FlyPerms plugin) {
        this.plugin = plugin;
        this.config = this.plugin.getConfig();
        this.speedGroups = new HashMap<>();
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
            loadConfigOptions();
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

    private void loadConfigOptions() {
        this.checkWorld = config.getBoolean("check-for-world", true);
        this.checkGameMode = config.getBoolean("check-for-gamemode", false);
        this.allowCreative = config.getBoolean("always-allow-in-creative", true);
        this.checkInterval = config.getInt("check-interval", 1000);
        this.coolDown = config.getInt("cooldown", 5000);
        this.autoFlyOnAirTeleport = config.getBoolean("fly-on-air-teleport", true);
        loadSpeedGroups(config.getMapList("speed-group"));
        this.resetSpeedOnWorldChange = config.getBoolean("reset-speed-world", false);
        this.resetSpeedOnGameModeChange = config.getBoolean("reset-speed-gamemode", false);
        this.resetSpeedValue = config.getDouble("speed-reset-value", 1.0);
        this.disabledWorlds = config.getStringList("ignore-in-worlds");
        this.hookPapi = config.getBoolean("enable-papi-hook", true);
        this.debugMode = config.getBoolean("show-debug-info", false);
    }

    private void loadSpeedGroups(List<Map<?, ?>> speedGroupConfig) {
        this.speedGroups = new HashMap<>();

        if (speedGroupConfig == null) {
            return;
        }

        for (Map<?, ?> group : speedGroupConfig) {
            for (Object rawGroupName : group.keySet()) {
                List<Double> speedValue = (List<Double>) group.get(rawGroupName);
                if (!validateSpeedValue(speedValue)) {
                    Logging.log(Level.WARNING, "Invalid speed group " + rawGroupName + ". Please check for config!");
                    continue;
                }
                String groupName = String.valueOf(rawGroupName);
                if (speedValue.size() == 2) {
                    speedGroups.put(groupName, new SpeedGroup(groupName, speedValue.get(0), speedValue.get(1)));
                }
                else if (speedValue.size() == 1) {
                    speedGroups.put(groupName, new SpeedGroup(groupName, speedValue.get(0)));
                }
            }
        }
    }

    private boolean validateSpeedValue(List<Double> speedValue) {
        return speedValue != null && ((speedValue.size() == 2 && speedValue.get(0) <= speedValue.get(1)) || speedValue.size() == 1);
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

    public boolean isAutoFlyOnAirTeleport() {
        return autoFlyOnAirTeleport;
    }

    public Collection<SpeedGroup> getSpeedGroups() {
        return speedGroups.values();
    }

    public boolean hasSpeedGroup(String groupName) {
        return speedGroups.containsKey(groupName);
    }

    public SpeedGroup getSpeedGroupOf(String groupName) {
        return speedGroups.get(groupName);
    }

    public Collection<String> getSpeedGroupNames() {
        return speedGroups.keySet();
    }

    public boolean isResetSpeedOnWorldChange() {
        return resetSpeedOnWorldChange;
    }

    public boolean isResetSpeedOnGameModeChange() {
        return resetSpeedOnGameModeChange;
    }

    public double getResetSpeedValue() {
        return resetSpeedValue;
    }

    public Collection<String> getIgnoreWorlds() {
        return Collections.unmodifiableList(disabledWorlds);
    }

    public boolean isIgnoreWorld(World world) {
        return isIgnoreWorld(world.getName());
    }

    public boolean isIgnoreWorld(String worldName) {
        return disabledWorlds.contains(worldName);
    }

    public boolean haveIgnoreWorld() {
        return disabledWorlds.size() > 0;
    }

    public boolean isHookPapi() {
        return hookPapi;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    @Override
    public String toString() {
        return "FlyPermsConfig{" +
                "checkGameMode=" + checkGameMode +
                ", checkWorld=" + checkWorld +
                ", allowCreative=" + allowCreative +
                ", checkInterval=" + checkInterval +
                ", coolDown=" + coolDown +
                ", autoFlyOnAirTeleport=" + autoFlyOnAirTeleport +
                ", resetSpeedOnWorldChange=" + resetSpeedOnWorldChange +
                ", resetSpeedOnGameModeChange=" + resetSpeedOnGameModeChange +
                ", resetSpeedValue=" + resetSpeedValue +
                ", speedGroups=" + speedGroups +
                ", disabledWorlds=" + disabledWorlds +
                ", debugMode=" + debugMode +
                '}';
    }
}
