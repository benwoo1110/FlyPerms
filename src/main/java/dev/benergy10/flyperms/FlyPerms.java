package dev.benergy10.flyperms;

import dev.benergy10.flyperms.api.FPPlugin;
import dev.benergy10.flyperms.dependencies.BstatsMetrics;
import dev.benergy10.flyperms.dependencies.FlyStateContextCalculator;
import dev.benergy10.flyperms.dependencies.PapiExpansion;
import dev.benergy10.flyperms.listeners.PlayerListener;
import dev.benergy10.flyperms.listeners.WorldListener;
import dev.benergy10.flyperms.managers.CheckManager;
import dev.benergy10.flyperms.managers.CommandManager;
import dev.benergy10.flyperms.managers.FlightManager;
import dev.benergy10.flyperms.utils.*;
import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * {@inheritDoc}
 */
public final class FlyPerms extends JavaPlugin implements FPPlugin {

    private FlyPermsConfig config;
    private PermissionTools permissionTools;
    private CheckManager checkManager;
    private FlightManager flightManager;
    private FlyApplyScheduler flyApplyScheduler;
    private CommandManager commandManager;
    private SimpleMessageProvider messageProvider;

    @Override
    public void onEnable() {
        Logging.setup(this);
        Logging.showStartUpText(this);
        Logging.info("Starting...");

        // Init messaging
        Logging.info("Setting up messaging...");
        this.messageProvider = new SimpleMessageProvider(this);
        this.messageProvider.load();

        // Get config
        Logging.info("Setting up config...");
        this.config = new FlyPermsConfig(this);
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
        this.permissionTools = new PermissionTools(this);
        this.permissionTools.registerPerms();

        // Register dependencies
        Logging.debug("Registering dependencies...");
        if (this.config.isHookPapi() && pm.getPlugin("PlaceholderAPI") != null) {
            new PapiExpansion(this).register();
        }
        else {
            Logging.debug("FlyPerms placeholderAPI expansion is not registered!");
        }
//        LuckPerms luckPerms = getServer().getServicesManager().load(LuckPerms.class);
//        if (this.config.isHookPapi() && luckPerms != null) {
//            luckPerms.getContextManager().registerCalculator(new FlyStateContextCalculator(this));
//        }
//        else {
//            Logging.debug("FlyPerms LuckPerms context is not registered!");
//        }

        // Init remaining classes
        this.checkManager = new CheckManager(this);
        this.flightManager = new FlightManager(this);
        this.commandManager = new CommandManager(this);

        // Start scheduler for fly checking
        this.flyApplyScheduler = new FlyApplyScheduler(this);
        this.flyApplyScheduler.start();

        Logging.info("Started!");
    }

    /**
     * {@inheritDoc}
     */
    public boolean reload() {
        this.flyApplyScheduler.stop();
        this.permissionTools.removeAllPerms();

        if (!this.config.reloadConfigValues()) {
            return false;
        }
        this.messageProvider.load();

        this.permissionTools.registerPerms();
        this.flyApplyScheduler.start();

        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.flyApplyScheduler.stop();
        this.permissionTools.removeAllPerms();
        Logging.info("Stopped. Happy flying!");
    }

    public PermissionTools getPermissionTools() {
        return this.permissionTools;
    }

    /**
     * {@inheritDoc}
     */
    public CommandManager getCommandManager() {
        return commandManager;
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
        return this.flyApplyScheduler;
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

    public SimpleMessageProvider getMessageProvider() {
        return messageProvider;
    }
}
