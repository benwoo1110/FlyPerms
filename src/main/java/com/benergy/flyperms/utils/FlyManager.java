package com.benergy.flyperms.utils;

import com.benergy.flyperms.FlyPerms;
import com.benergy.flyperms.enums.FlyState;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class FlyManager {

    private final FlyPerms plugin;

    public FlyManager(FlyPerms plugin) {
        this.plugin = plugin;
    }

    public FlyState applyFlyState(Player player) {
        FlyState state = this.plugin.getFlyChecker().calculateFlyState(player);
        modifyFLyAbility(player, state);
        return state;
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
                    player.setAllowFlight(false);
                    Logging.log(Level.FINE,"Disallowing flight for " + player.getName());
                }
        }
    }

    public boolean applyFlySpeed(Player player, double speed) {
        if (!this.plugin.getSpeedChecker().canChangeSpeedTo(player, speed)) {
            return false;
        }

        player.setFlySpeed((float) speed / 10);
        return true;
    }
}
