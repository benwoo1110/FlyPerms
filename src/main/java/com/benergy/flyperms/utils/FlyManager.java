package com.benergy.flyperms.utils;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.Constants.FlyState;
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
                if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    Logging.log(Level.FINE, player.getName() + " in spectator mode.");
                }
                break;
            case CREATIVE_BYPASS:
                if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    Logging.log(Level.FINE, "Allowed flight for " + player.getName() + " due to creative bypass.");
                }
                break;
            case YES:
                if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    Logging.log(Level.FINE, "Allowed flight for " + player.getName());
                }
                break;
            case NO:
                if (player.getAllowFlight()) {
                    if (player.isFlying()) {
                        stopFly(player);
                    }
                    else {
                        player.setAllowFlight(false);
                        Logging.log(Level.FINE,"Disallowed flight for " + player.getName());
                    }
                }
                break;
        }
    }

    private void stopFly(Player player) {
        if (this.playersToStopFly.contains(player.getUniqueId())) {
            return;
        }
        this.playersToStopFly.add(player.getUniqueId());

        int coolDown = this.plugin.getFPConfig().getCoolDown();
        player.sendMessage("You have lost your ability to fly. Dropping in " + Formatter.millisecondsToSeconds(coolDown) + "sec...");
        this.plugin.getServer().getScheduler().runTaskLater(
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
                Logging.log(Level.FINE, "Stop fly for " + player.getName() + " aborted");
                return;
            }

            player.sendMessage("Dropping now!");
            player.setFlying(false);
            player.setAllowFlight(false);

            this.playersToStopFly.remove(player.getUniqueId());
            Logging.log(Level.FINE,"Disallowed flight for " + player.getName() + " after cooldown.");
        };
    }
}
