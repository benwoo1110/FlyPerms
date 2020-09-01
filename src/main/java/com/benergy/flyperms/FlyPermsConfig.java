package com.benergy.flyperms;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventPriority;

import java.util.List;

public class FlyPermsConfig {

    private final EventPriority priority;
    private final boolean checkGameMode;
    private final boolean checkWorld;
    private final List<String> disabledWorlds;

    public FlyPermsConfig(FileConfiguration config) {
        this.priority = EventPriority.valueOf(config.getString("listener-priority"));
        this.checkGameMode = config.getBoolean("check-for-gamemode");
        this.checkWorld = config.getBoolean("check-for-world");
        this.disabledWorlds = config.getStringList("ignore-in-worlds");
    }

    public EventPriority getPriority() {
        return priority;
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
}
