package dev.benergy10.flyperms;

import dev.benergy10.flyperms.api.FPPlugin;
import dev.benergy10.flyperms.dependencies.PapiExpansion;
import dev.benergy10.flyperms.listeners.PlayerListener;
import dev.benergy10.flyperms.listeners.WorldListener;
import dev.benergy10.flyperms.utils.BstatsMetrics;
import dev.benergy10.flyperms.utils.CheckManager;
import dev.benergy10.flyperms.utils.FPCommandManager;
import dev.benergy10.flyperms.utils.FlightManager;
import dev.benergy10.flyperms.utils.FlyApplyScheduler;
import dev.benergy10.flyperms.utils.Logging;
import dev.benergy10.flyperms.utils.PermissionTools;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * {@inheritDoc}
 */
public final class FlyPerms extends JavaPlugin implements FPPlugin {

    // Config
    private final FlyPermsConfig config = new FlyPermsConfig(this);

    // Handlers
    private final PermissionTools permissionTools = new PermissionTools(this);
    private final CheckManager checkManager = new CheckManager(this);
    private final FlightManager flightManager = new FlightManager(this);
    private final FlyApplyScheduler flyCheckScheduler = new FlyApplyScheduler(this);
    private FPCommandManager commandManager;

    @Override
    public void onEnable() {
        Logging.setup(this);
        Logging.showStartUpText(this);
        Logging.info("Starting...");

        // Get config
        Logging.info("Setting up config...");
        this.saveDefaultConfig();
        this.config.loadConfigValues();

        // Init bstats
        Logging.debug("Setting up bstats...");
        BstatsMetrics.configureMetrics(this);

        // Register events
        Logging.debug("Registering events...");
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new WorldListener(this), this);

        // Register permission nodes
        Logging.debug("Registering permissions...");
        this.permissionTools.registerPerms();

        // Register commands
        Logging.debug("Setting commands...");
        this.commandManager = new FPCommandManager(this);

        // Register dependencies
        Logging.debug("Registering dependencies...");
        if (this.config.isHookPapi() && pm.getPlugin("PlaceholderAPI") != null) {
            new PapiExpansion(this).register();
        }
        else {
            Logging.debug("FlyPerms placeholderAPI expansion is not registered!");
        }

        this.flyCheckScheduler.start();

        Logging.info("Started!");
    }

    /**
     * {@inheritDoc}
     */
    public boolean reload() {
        this.flyCheckScheduler.stop();
        this.permissionTools.removeAllPerms();

        if (!this.config.reloadConfigValues()) {
            return false;
        }

        this.permissionTools.registerPerms();
        this.flyCheckScheduler.start();

        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.flyCheckScheduler.stop();
        this.permissionTools.removeAllPerms();
        Logging.info("Stopped. Happy flying!");
    }

    /**
     * {@inheritDoc}
     */
    public PermissionTools getPermissionTools() {
        return this.permissionTools;
    }

    /**
     * {@inheritDoc}
     */
    public FlyPermsConfig getFPConfig() {
        return this.config;
    }

    /**
     * {@inheritDoc}
     */
    public FlyApplyScheduler getFlyApplyScheduler() {
        return this.flyCheckScheduler;
    }

    /**
     * {@inheritDoc}
     */
    public FlightManager getFlightManager() {
        return this.flightManager;
    }

    /**
     * {@inheritDoc}
     */
    public CheckManager getCheckManager() {
        return this.checkManager;
    }
}
