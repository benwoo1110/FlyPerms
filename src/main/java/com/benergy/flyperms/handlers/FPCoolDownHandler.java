package com.benergy.flyperms.handlers;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.permissions.FlyState;
import com.benergy.flyperms.utils.FPLogger;
import com.benergy.flyperms.utils.FormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class FPCoolDownHandler {

    private final FlyPerms plugin;
    private final Set<UUID> playersToStopFly;

    public FPCoolDownHandler(FlyPerms plugin) {
        this.plugin = plugin;
        playersToStopFly = new HashSet<>();
    }

    public void stopFly(Player player) {
        if (this.playersToStopFly.contains(player.getUniqueId())) {
            return;
        }
        this.playersToStopFly.add(player.getUniqueId());

        int coolDown = this.plugin.getFPConfig().getCoolDown();
        player.sendMessage("You have lost your ability to fly. Dropping in " + FormatUtil.millisecondsToSeconds(coolDown) + "sec...");
        Bukkit.getScheduler().runTaskLater(
                this.plugin,
                () -> {
                    stopFlyRunnable(player);
                    this.playersToStopFly.remove(player.getUniqueId());
                },
                FormatUtil.millisecondsToTicks(coolDown));
    }

    private void stopFlyRunnable(Player player) {
        FPLogger.log(Level.FINE, "Running scheduled stop fly for " + player.getName());
        if (!player.isOnline()) {
            return;
        }
        if (!player.isFlying()) {
            return;
        }
        if (!this.plugin.getFPFly().canFly(player).equals(FlyState.NO)) {
            return;
        }
        player.sendMessage("Dropping now!");
        player.setFlying(false);
        player.setAllowFlight(false);
    }

}
