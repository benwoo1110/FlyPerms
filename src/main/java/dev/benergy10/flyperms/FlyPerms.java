package dev.benergy10.flyperms;

import dev.benergy10.flyperms.api.FPPlugin;
import dev.benergy10.flyperms.api.MessageProvider;
import dev.benergy10.flyperms.configuration.ConfigOptions;
import dev.benergy10.flyperms.dependencies.BstatsMetrics;
import dev.benergy10.flyperms.dependencies.PapiExpansion;
import dev.benergy10.flyperms.listeners.PlayerListener;
import dev.benergy10.flyperms.listeners.WorldListener;
import dev.benergy10.flyperms.managers.CheckManager;
import dev.benergy10.flyperms.managers.FlightManager;
import dev.benergy10.flyperms.utils.CommandTools;
import dev.benergy10.flyperms.utils.FlyApplyScheduler;
import dev.benergy10.flyperms.utils.PermissionTools;
import dev.benergy10.flyperms.utils.SimpleMessageProvider;
import dev.benergy10.minecrafttools.MinecraftPlugin;
import dev.benergy10.minecrafttools.configs.CommentedYamlFile;
import dev.benergy10.minecrafttools.configs.YamlFile;
import dev.benergy10.minecrafttools.utils.Logging;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * {@inheritDoc}
 */
public final class FlyPerms extends MinecraftPlugin implements FPPlugin {

    private YamlFile config;
    private PermissionTools permissionTools;
    private CheckManager checkManager;
    private FlightManager flightManager;
    private FlyApplyScheduler flyApplyScheduler;
    private SimpleMessageProvider messageProvider;

    @Override
    public void enable() {
        Logging.showStartUpText(
                "§2    ___§3  __",
                "§2   /__§3  /__)   §aFlyPerms - v" + this.getDescription().getVersion(),
                "§2  /  §3  /       §bEnabled by - benwoo1110",
                ""
        );
        Logging.info("Starting...");

        // Get config
        Logging.info("Setting up config...");
        this.config = new CommentedYamlFile(this.getConfigFile(), ConfigOptions.getOptions(), ConfigOptions.header());

        // Init messaging
        Logging.info("Setting up messaging...");
        this.messageProvider = new SimpleMessageProvider(this);
        this.messageProvider.load();

        // Register events
        Logging.info("Registering events...");
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new WorldListener(this), this);

        // Register permission nodes
        Logging.info("Registering permissions...");
        this.permissionTools = new PermissionTools(this);
        this.permissionTools.registerPerms();

        // Register dependencies
        Logging.info("Registering dependencies...");
        if (this.config.getValue(ConfigOptions.PAPI_HOOK) && pm.getPlugin("PlaceholderAPI") != null) {
            new PapiExpansion(this).register();
        }
        else {
            Logging.info("FlyPerms placeholderAPI expansion is not registered!");
        }

        // Init remaining classes
        this.checkManager = new CheckManager(this);
        this.flightManager = new FlightManager(this);
        this.flyApplyScheduler = new FlyApplyScheduler(this);
        this.flyApplyScheduler.start();

        CommandTools.setUp(this);
        BstatsMetrics.configureMetrics(this);

        Logging.info("Started!");
    }

    /**
     * {@inheritDoc}
     */
    public boolean reload() {
        this.flyApplyScheduler.stop();
        this.permissionTools.removeAllPerms();

        if (!this.config.reload()) {
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
     * @return
     */
    public @NotNull YamlFile getFPConfig() {
        return this.config;
    }

    /**
     * {@inheritDoc}
     */
    public @NotNull FlyApplyScheduler getFlyApplyScheduler() {
        return this.flyApplyScheduler;
    }

    /**
     * {@inheritDoc}
     */
    public @NotNull FlightManager getFlightManager() {
        return this.flightManager;
    }

    /**
     * {@inheritDoc}
     */
    public @NotNull CheckManager getCheckManager() {
        return this.checkManager;
    }

    /**
     * {@inheritDoc}
     */
    public @NotNull MessageProvider getMessageProvider() {
        return messageProvider;
    }
}
