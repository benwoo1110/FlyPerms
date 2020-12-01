package com.benergy.flyperms.checkers;

import org.bukkit.entity.Player;

import java.util.List;

public interface Checker<T> {
    boolean isEnabled();

    List<T> getAllowed(Player player);

    List<String> getAllowedNames(Player player);


    Boolean hasPerm(Player player, String check);

    Boolean hasPerm(Player player, T check);
}
