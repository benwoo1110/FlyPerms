package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.api.FPSpeedGroup;

/**
 * {@inheritDoc}
 */
public class SpeedGroup implements FPSpeedGroup {

    private final String name;
    private final double lowerLimit;
    private final double upperLimit;

    public SpeedGroup(String name, double limit) {
        this(name, limit, limit);
    }

    public SpeedGroup(String name, double lowerLimit, double upperLimit) {
        this.name = name.toLowerCase();
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isInRange(double speed) {
        return lowerLimit <= speed && upperLimit >= speed;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    public double getLowerLimit() {
        return lowerLimit;
    }

    /**
     * {@inheritDoc}
     */
    public double getUpperLimit() {
        return upperLimit;
    }

    @Override
    public String toString() {
        return "SpeedGroup{" +
                "name='" + name + '\'' +
                ", lowerLimit=" + lowerLimit +
                ", upperLimit=" + upperLimit +
                '}';
    }
}
