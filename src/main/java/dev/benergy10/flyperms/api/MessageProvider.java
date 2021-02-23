package dev.benergy10.flyperms.api;

import dev.benergy10.flyperms.Constants.MessageKey;
import org.bukkit.command.CommandSender;

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
    String parseMessage(MessageKey messageKey, Object...replacements);

    /**
     * Get the raw message set.
     *
     * @param messageKey    Target message identifier.
     * @return The raw message.
     */
    String getMessage(MessageKey messageKey);

    /**
     * Send a message to a {@link CommandSender}.
     *
     * @param sender        Person to send the message to.
     * @param messageKey    Target message identifier.
     * @param replacements  Objects to replace placeholder in message.
     */
    void send(CommandSender sender, MessageKey messageKey, Object...replacements);
}
