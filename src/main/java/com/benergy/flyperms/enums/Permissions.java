package com.benergy.flyperms.enums;

public final class Permissions {
    // main
    public static final String ROOT = "flyperms.";
    public static final String OTHERS = ".others";

    // fly
    public static final String ALLOW_BASE = ROOT + "allow";
    public static final String ALLOW_WORLD = ALLOW_BASE + ".world.";
    public static final String ALLOW_GAMEMODE = ALLOW_BASE + ".gamemode.";

    // speed
    public static final String SPEED_GROUP = ROOT + ".speedgroup.";

    // commands
    public static final String USAGE = ROOT + "help";
    public static final String INFO = ROOT + "info";
    public static final String LIST_GROUPS = ROOT + "listgroups";
    public static final String RELOAD = ROOT + "reload";
    public static final String SEE_ALLOWED = ROOT + "seeallowed";
    public static final String SEE_ALLOWED_OTHERS = SEE_ALLOWED + OTHERS;
    public static final String CHANGE_SPEED = ROOT + "speed";
    public static final String CHANGE_SPEED_OTHERS = CHANGE_SPEED + OTHERS;
}
