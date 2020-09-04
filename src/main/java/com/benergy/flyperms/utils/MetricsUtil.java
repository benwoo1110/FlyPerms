package com.benergy.flyperms.utils;

import com.benergy.flyperms.FlyPerms;
import org.bstats.bukkit.Metrics;

public class MetricsUtil {

    private static final int PLUGIN_ID = 8745;

    public static void configureMetrics(FlyPerms plugin) {
        MetricsUtil configurator = new MetricsUtil(plugin);
        configurator.initMetrics();
    }

    private final FlyPerms plugin;
    private final Metrics metrics;

    private MetricsUtil(FlyPerms plugin) {
        this.plugin = plugin;
        this.metrics = new Metrics(plugin, PLUGIN_ID);
    }

    private void initMetrics() {
        // TODO: Custom metrics
    }
}
