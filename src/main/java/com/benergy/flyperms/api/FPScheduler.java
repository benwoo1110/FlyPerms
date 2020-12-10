package com.benergy.flyperms.api;

/**
 * A repeat scheduler used to periodically check and apply fly ability for online players.
 */
public interface FPScheduler {
    /**
     * Starts the fly check runnable.
     */
    void start();

    /**
     * Stops the fly check runnable.
     */
    void stop();

    /**
     *
     * @return True if fly check runnable is active.
     */
    boolean isRunning();
}
