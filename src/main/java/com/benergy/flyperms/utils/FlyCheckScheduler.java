package com.benergy.flyperms.utils;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.permissions.FlyState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class FlyCheckScheduler {

    private final FlyPerms plugin;
    private final Set<UUID> playersToStopFly;
    private BukkitTask flyCheckTask;

    public FlyCheckScheduler(FlyPerms plugin) {
        this.plugin = plugin;
        this. playersToStopFly = new HashSet<>();
    }

    public void startFlyChecker() {
        this.flyCheckTask = Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                flyCheckRunnable(),
                0L,
                Formatter.millisecondsToTicks(this.plugin.getFPConfig().getCheckInterval())
        );
        FPLogger.log(Level.INFO, "Started fly check task...");
    }

    public void stopFlyChecker() {
        if (this.flyCheckTask == null) {
            FPLogger.log(Level.WARNING, "Unable to stop fly check task. Fly check task has not started!");
            return;
        }

        this.flyCheckTask.cancel();
        this.flyCheckTask = null;
        FPLogger.log(Level.INFO, "Stopped fly check task...");
    }

    public boolean isFlyCheckRunning() {
        return this.flyCheckTask != null && this.flyCheckTask.isCancelled();
    }

    private Runnable flyCheckRunnable() {
        return () -> Bukkit.getOnlinePlayers().forEach(p -> this.plugin.getFPFly().canFly(p));
    }

    public void stopFly(Player player) {
        if (this.playersToStopFly.contains(player.getUniqueId())) {
            return;
        }
        this.playersToStopFly.add(player.getUniqueId());

        int coolDown = this.plugin.getFPConfig().getCoolDown();
        player.sendMessage("You have lost your ability to fly. Dropping in " + Formatter.millisecondsToSeconds(coolDown) + "sec...");
        Bukkit.getScheduler().runTaskLater(
                this.plugin,
                stopFlyRunnable(player),
                Formatter.millisecondsToTicks(coolDown)
        );
    }

    private Runnable stopFlyRunnable(Player player) {
        return () -> {
            FPLogger.log(Level.FINE, "Running scheduled stop fly for " + player.getName());
            if (!player.isOnline()
                    || !player.isFlying()
                    || !this.plugin.getFPFly().canFly(player).equals(FlyState.NO)) {
                return;
            }

            player.sendMessage("Dropping now!");
            player.setFlying(false);
            player.setAllowFlight(false);

            this.playersToStopFly.remove(player.getUniqueId());
        };
    }

}
