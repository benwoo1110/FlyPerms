package dev.benergy10.flyperms.api;

import dev.benergy10.flyperms.managers.CheckManager;
import dev.benergy10.flyperms.utils.FlyApplyScheduler;
import dev.benergy10.flyperms.managers.FlightManager;
import dev.benergy10.minecrafttools.CommandManager;
import dev.benergy10.minecrafttools.configs.YamlFile;
import org.jetbrains.annotations.NotNull;

/**
 * FlyPerms plugin.
 */
public interface FPPlugin {
    /**
     * Reloads the plugin.
     *
     * @return True if reload is successful, false otherwise.
     */
    boolean reload();

    /**
     * Gets class used to manage commands.
     *
     * @return {@link CommandManager}.
     */
    @NotNull CommandManager getCommandManager();

    /**
     * Gets class where all to config options are stored.
     *
     * @return {@link FPConfig}.
     */
    @NotNull YamlFile getFPConfig();

    /**
     * Gets scheduler class that is used to
     *
     * @return {@link FPScheduler}.
     */
    @NotNull FlyApplyScheduler getFlyApplyScheduler();

    /**
     * Gets main class that is used to manager the applying of the various fly abilities.
     *
     * @return {@link FPFlightManager}.
     */
    @NotNull FlightManager getFlightManager();

    /**
     * Gets the check class that is responsible for calculating fly properties of player based on their permissions.
     *
     * @return {@link FPCheckManager}.
     */
    @NotNull CheckManager getCheckManager();

    /**
     * Gets message provider used for sending messages by the plugin.
     *
     * @return {@link MessageProvider}.
     */
    @NotNull MessageProvider getMessageProvider();
}
