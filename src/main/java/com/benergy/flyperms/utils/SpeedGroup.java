package com.benergy.flyperms.utils;

import com.benergy.flyperms.api.FPSpeedGroup;

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

    public boolean isInRange(double speed) {
        return lowerLimit <= speed && upperLimit >= speed;
    }

    public String getName() {
        return name;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

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
