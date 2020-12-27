package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.FPScheduler;
import org.bukkit.scheduler.BukkitTask;

/**
 * {@inheritDoc}
 */
public class FlyApplyScheduler implements FPScheduler {

    private final FlyPerms plugin;

    private BukkitTask flyCheckTask;

    public FlyApplyScheduler(FlyPerms plugin) {
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     */
    public void start() {
        this.flyCheckTask = this.plugin.getServer().getScheduler().runTaskTimer(
                this.plugin,
                flyCheckRunnable(),
                0L,
                Formatter.millisecondsToTicks(this.plugin.getFPConfig().getCheckInterval())
        );
        Logging.info("Started fly check task...");
    }

    /**
     * {@inheritDoc}
     */
    public void stop() {
        if (this.flyCheckTask == null) {
            Logging.warning("Unable to stop fly check task. Fly check task has not started!");
            return;
        }

        this.flyCheckTask.cancel();
        this.flyCheckTask = null;
        Logging.info("Stopped fly check task...");
    }

    /**
     * {@inheritDoc}
     */
    public boolean isRunning() {
        return this.flyCheckTask != null && !this.flyCheckTask.isCancelled();
    }

    private Runnable flyCheckRunnable() {
        return () -> this.plugin.getServer()
                .getOnlinePlayers()
                .forEach(p -> this.plugin.getFlightManager().applyFlyState(p.getPlayer()));
    }
}
