package dev.benergy10.flyperms.api;

public interface FPSpeedGroup {
    /**
     * Check if a given speed is in allowed range of this speed group.
     *
     * @param speed Fly speed.
     * @return True if this group allow for the given fly speed.
     */
    boolean isInRange(double speed);

    /**
     * Name of the speed group.
     *
     * @return Name of the speed group.
     */
    String getName();

    /**
     * Minimum fly speed allowed for this speed group.
     *
     * @return Minimum fly speed for the speed group.
     */
    double getLowerLimit();

    /**
     * Maximum fly speed allowed for this speed group.
     *
     * @return Maximum fly speed for the speed group.
     */
    double getUpperLimit();
}
