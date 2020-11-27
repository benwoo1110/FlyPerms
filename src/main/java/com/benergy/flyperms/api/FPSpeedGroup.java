package com.benergy.flyperms.api;

public interface FPSpeedGroup {
    boolean isInRange(double speed);

    String getName();

    double getLowerLimit();

    double getUpperLimit();
}
