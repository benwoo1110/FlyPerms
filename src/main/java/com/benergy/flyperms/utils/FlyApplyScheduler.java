package com.benergy.flyperms.utils;

import com.benergy.flyperms.FlyPerms;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Level;

public class FlyApplyScheduler {

    private final FlyPerms plugin;

    private BukkitTask flyCheckTask;

    public FlyApplyScheduler(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public void start() {
        this.flyCheckTask = Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                flyCheckRunnable(),
                0L,
                Formatter.millisecondsToTicks(this.plugin.getFPConfig().getCheckInterval())
        );
        Logging.log(Level.INFO, "Started fly check task...");
    }

    public void stop() {
        if (this.flyCheckTask == null) {
            Logging.log(Level.WARNING, "Unable to stop fly check task. Fly check task has not started!");
            return;
        }

        this.flyCheckTask.cancel();
        this.flyCheckTask = null;
        Logging.log(Level.INFO, "Stopped fly check task...");
    }

    public boolean isRunning() {
        return this.flyCheckTask != null && !this.flyCheckTask.isCancelled();
    }

    private Runnable flyCheckRunnable() {
        return () -> Bukkit.getOnlinePlayers().forEach(p -> this.plugin.getFlyManager().applyFlyState(p.getPlayer()));
    }
}
