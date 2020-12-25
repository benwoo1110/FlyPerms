package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.FlyPerms;
import org.bstats.bukkit.Metrics;

public class BstatsMetrics {

    private final FlyPerms plugin;
    private final Metrics metrics;
    private static final int PLUGIN_ID = 8745;

    public static void configureMetrics(FlyPerms plugin) {
        BstatsMetrics configurator = new BstatsMetrics(plugin);
        configurator.initMetrics();
    }

    private BstatsMetrics(FlyPerms plugin) {
        this.plugin = plugin;
        this.metrics = new org.bstats.bukkit.Metrics(plugin, PLUGIN_ID);
    }

    private void initMetrics() {
        // TODO: Custom metrics
    }
}
