package dev.benergy10.flyperms.dependencies;

import dev.benergy10.flyperms.FlyPerms;
import org.bstats.bukkit.Metrics;
import org.jetbrains.annotations.NotNull;

/**
 * Upload plugin stats to https://bstats.org/
 */
public class BstatsMetrics {

    private static final int PLUGIN_ID = 8745;

    private final FlyPerms plugin;
    private final Metrics metrics;

    public static void configureMetrics(@NotNull FlyPerms plugin) {
        BstatsMetrics configurator = new BstatsMetrics(plugin);
        configurator.initMetrics();
    }

    private BstatsMetrics(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
        this.metrics = new org.bstats.bukkit.Metrics(plugin, PLUGIN_ID);
    }

    private void initMetrics() {
        // TODO: Custom metrics
    }
}
