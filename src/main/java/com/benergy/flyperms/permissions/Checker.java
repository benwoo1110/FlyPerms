package com.benergy.flyperms.permissions;

import com.benergy.flyperms.FlyPerms;

public abstract class Checker {
    protected final FlyPerms plugin;

    protected Checker(FlyPerms plugin) {
        this.plugin = plugin;
    }
}
