package com.benergy.flyperms.api;

public interface FPScheduler {
    void start();

    void stop();

    boolean isRunning();
}
