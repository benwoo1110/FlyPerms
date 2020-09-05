package com.benergy.flyperms.permissions;

import org.bukkit.ChatColor;

public enum FlyState {
    YES,
    NO,
    IGNORED,
    CREATIVE_BYPASS,
    SPECTATOR;

    @Override
    public String toString() {
        switch (this) {
            case YES:
                return ChatColor.GREEN + "Yes";
            case NO:
                return ChatColor.RED + "No";
            case IGNORED:
                return ChatColor.GRAY + "Ignored";
            case CREATIVE_BYPASS:
                return ChatColor.GRAY + "Creative bypass";
            case SPECTATOR:
                return ChatColor.GRAY + "Spectator mode";
        }
        // Fallback
        return super.toString();
    }
}
