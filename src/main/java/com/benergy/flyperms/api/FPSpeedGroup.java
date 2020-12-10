package com.benergy.flyperms.api;

public interface FPSpeedGroup {
    /**
     *
     * @param speed Fly speed.
     * @return True if this group allow for the given fly speed.
     */
    boolean isInRange(double speed);

    /**
     *
     * @return Name of the speed group.
     */
    String getName();

    /**
     *
     * @return Minimum fly speed for the speed group.
     */
    double getLowerLimit();

    /**
     *
     * @return Maximum fly speed for the speed group.
     */
    double getUpperLimit();
}
