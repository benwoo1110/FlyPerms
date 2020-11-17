package com.benergy.flyperms;

import co.aikar.commands.PaperCommandManager;
import com.benergy.flyperms.api.FPPlugin;
import com.benergy.flyperms.commands.RootCommand;
import com.benergy.flyperms.commands.InfoCommand;
import com.benergy.flyperms.commands.ListGroupsCommand;
import com.benergy.flyperms.commands.ReloadCommand;
import com.benergy.flyperms.commands.SeeAllowedCommand;
import com.benergy.flyperms.commands.SpeedCommand;
import com.benergy.flyperms.commands.UsageCommand;
import com.benergy.flyperms.permissions.SpeedChecker;
import com.benergy.flyperms.utils.FlyCheckScheduler;
import com.benergy.flyperms.listeners.FPPlayerListener;
import com.benergy.flyperms.listeners.FPWorldListener;
import com.benergy.flyperms.permissions.FlyChecker;
import com.benergy.flyperms.permissions.PermissionTools;
import com.benergy.flyperms.utils.Logging;
import com.benergy.flyperms.utils.BstatsMetrics;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class FlyPerms extends JavaPlugin implements FPPlugin {
    // Config
    private final FlyPermsConfig config = new FlyPermsConfig(this);

    // Permissions
    private final PermissionTools permissionTools = new PermissionTools(this);
    private final FlyChecker flyChecker = new FlyChecker(this);
    private final SpeedChecker speedChecker = new SpeedChecker(this);

    // Handlers
    private final FlyCheckScheduler flyCheckScheduler = new FlyCheckScheduler(this);

    @Override
    public void onEnable() {
        Logging.setup(this);
        Logging.showStartUpText();

        // Get config
        this.saveDefaultConfig();
        this.config.loadConfigValues();

        // Init bstats
        BstatsMetrics.configureMetrics(this);

        // Register events
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new FPPlayerListener(this), this);
        pm.registerEvents(new FPWorldListener(this), this);

        // Register permission nodes
        this.permissionTools.registerPerms();

        // Register commands
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.enableUnstableAPI("help");
        commandManager.registerCommand(new RootCommand(this));
        commandManager.registerCommand(new InfoCommand(this));
        commandManager.registerCommand(new ReloadCommand(this));
        commandManager.registerCommand(new SeeAllowedCommand(this));
        commandManager.registerCommand(new SpeedCommand(this));
        commandManager.registerCommand(new ListGroupsCommand(this));
        commandManager.registerCommand(new UsageCommand(this));

        flyCheckScheduler.startFlyChecker();

        Logging.log(Level.INFO, "Started!");
    }

    public boolean reload() {
        flyCheckScheduler.stopFlyChecker();
        if (!this.config.reloadConfigValues()) {
            return false;
        }
        flyCheckScheduler.startFlyChecker();
        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        flyCheckScheduler.stopFlyChecker();
        Logging.log(Level.INFO, "Stopped. Happy flying!");
    }

    public boolean isIgnoreWorld(World world) {
        return (config.getDisabledWorlds().contains(world.getName()));
    }

    public FlyPermsConfig getFPConfig() {
        return config;
    }

    public PermissionTools getPermissionTools() {
        return permissionTools;
    }

    public FlyChecker getFlyChecker() {
        return flyChecker;
    }

    public SpeedChecker getSpeedChecker() {
        return speedChecker;
    }

    public FlyCheckScheduler getFlyCheckScheduler() {
        return flyCheckScheduler;
    }
}
