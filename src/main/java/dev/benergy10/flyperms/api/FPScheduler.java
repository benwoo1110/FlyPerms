package dev.benergy10.flyperms.api;

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
     * Status of the runnable.
     *
     * @return True if fly check runnable is active.
     */
    boolean isRunning();
}
