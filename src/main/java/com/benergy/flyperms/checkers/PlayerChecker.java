package com.benergy.flyperms.checkers;

import org.bukkit.entity.Player;

interface PlayerChecker<T> extends Checker<T> {
    Boolean hasPerm(Player player);
}
