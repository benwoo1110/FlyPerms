package com.benergy.flyperms.utils;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.enums.FlyState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class FlyManager {

    private final FlyPerms plugin;
    private final Set<UUID> playersToStopFly;

    public FlyManager(FlyPerms plugin) {
        this.plugin = plugin;
        this. playersToStopFly = new HashSet<>();
    }

    public FlyState applyFlyState(Player player) {
        FlyState state = this.plugin.getFlyChecker().calculateFlyState(player);
        modifyFLyAbility(player, state);
        return state;
    }

    public boolean applyFlySpeed(Player player, double speed) {
        if (!this.plugin.getSpeedChecker().canChangeSpeedTo(player, speed)) {
            return false;
        }

        player.setFlySpeed((float) speed / 10);
        return true;
    }

    private void modifyFLyAbility(Player player, FlyState state) {
        switch (state) {
            case SPECTATOR:
                player.setAllowFlight(true);
            case CREATIVE_BYPASS:
                if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    Logging.log(Level.FINE, "Allowing flight for " + player.getName() + " due to creative bypass.");
                }
            case YES:
                if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    Logging.log(Level.FINE, "Allowing flight for " + player.getName());
                }
            case NO:
                if (player.getAllowFlight()) {
                    stopFly(player);
                    Logging.log(Level.FINE,"Disallowing flight for " + player.getName());
                }
        }
    }

    private void stopFly(Player player) {
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
            Logging.log(Level.FINE, "Running scheduled stop fly for " + player.getName());
            if (!player.isOnline()
                    || !player.isFlying()
                    || !this.plugin.getFlyChecker().calculateFlyState(player).equals(FlyState.NO)) {
                return;
            }

            player.sendMessage("Dropping now!");
            player.setFlying(false);
            player.setAllowFlight(false);

            this.playersToStopFly.remove(player.getUniqueId());
        };
    }
}
