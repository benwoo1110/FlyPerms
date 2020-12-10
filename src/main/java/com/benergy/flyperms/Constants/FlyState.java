package com.benergy.flyperms.Constants;

import org.bukkit.ChatColor;

/**
 * Represent fly ability state a player can be in.
 */
public enum FlyState {
    YES(ChatColor.GREEN + "Yes"),
    NO(ChatColor.RED + "No"),
    IGNORED(ChatColor.GRAY + "Ignored"),
    CREATIVE_BYPASS(ChatColor.GRAY + "Creative bypass"),
    SPECTATOR(ChatColor.GRAY + "Spectator mode");

    private final String state;

    FlyState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
