package com.benergy.flyperms.api;

public interface FPSpeedGroup {
    boolean isInRange(double speed);

    String getName();

    String permission();

    double getLowerLimit();

    double getUpperLimit();
}
