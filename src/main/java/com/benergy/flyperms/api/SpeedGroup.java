package com.benergy.flyperms.api;

public interface SpeedGroup {
    boolean isInRange(double speed);

    String getName();

    String permission();

    double getLowerLimit();

    double getUpperLimit();
}
