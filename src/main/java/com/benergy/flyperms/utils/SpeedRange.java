package com.benergy.flyperms.utils;

public class SpeedRange {
    private String name;
    private double lowerLimit;
    private double upperLimit;

    public SpeedRange(String name, double lowerLimit, double upperLimit) {
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
        return "SpeedRange{" +
                "name='" + name + '\'' +
                ", lowerLimit=" + lowerLimit +
                ", upperLimit=" + upperLimit +
                '}';
    }
}