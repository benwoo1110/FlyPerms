package dev.benergy10.flyperms.api;

import dev.benergy10.flyperms.Constants.MessageKey;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public interface MessageProvider {
    /**
     * Load up the message file.
     */
    void load();

    /**
     * Parse the message with appropriate placeholder replacements.
     *
     * @param messageKey    Target message identifier.
     * @param replacements  Objects to replace placeholder in message.
     * @return The parsed message.
     */
    @NotNull String parseMessage(@NotNull MessageKey messageKey, @Nullable Object...replacements);

    /**
     * Get the raw message set.
     *
     * @param messageKey    Target message identifier.
     * @return The raw message.
     */
    @Nullable String getMessage(@NotNull MessageKey messageKey);

    /**
     * Send a message to a {@link CommandSender}.
     *
     * @param sender        Person to send the message to.
     * @param messageKey    Target message identifier.
     * @param replacements  Objects to replace placeholder in message.
     */
    void send(@NotNull CommandSender sender, @NotNull MessageKey messageKey, @Nullable Object...replacements);
}
