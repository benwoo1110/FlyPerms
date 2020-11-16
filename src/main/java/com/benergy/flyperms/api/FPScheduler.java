package com.benergy.flyperms.api;

public interface FPScheduler {
    void startFlyChecker();

    void stopFlyChecker();

    boolean isFlyCheckRunning();
}
