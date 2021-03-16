package dev.benergy10.flyperms.utils;

import com.google.common.base.Strings;
import dev.benergy10.flyperms.Constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.MessageProvider;
import dev.benergy10.minecrafttools.utils.Logging;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class SimpleMessageProvider implements MessageProvider {

    private static final String MESSAGE_FILENAME = "messages.yml";
    private static final String INVALID_MESSAGE = ChatColor.RED + "!!INVALID!!";

    private final FlyPerms plugin;
    private final Map<String, String> defaultMessagesMap;
    private final Map<String, String> customMessagesMap;

    public SimpleMessageProvider(@NotNull FlyPerms plugin) {
        this.plugin = plugin;
        this.defaultMessagesMap = new HashMap<>(MessageKey.values().length);
        this.customMessagesMap = new HashMap<>(MessageKey.values().length);

        loadDefault();
    }

    /**
     *
     */
    private void loadDefault() {
        InputStream messageFileStream = this.plugin.getResource(MESSAGE_FILENAME);
        if (messageFileStream == null) {
            Logging.severe("Unable to load default messages!");
            return;
        }

        Reader messageFileReader = new InputStreamReader(messageFileStream);
        YamlConfiguration messageYaml = YamlConfiguration.loadConfiguration(messageFileReader);
        populateMessagesMap(this.defaultMessagesMap, messageYaml);
    }

    /**
     * {@inheritDoc}
     */
    public void load() {
        File messageFile = new File(this.plugin.getDataFolder(), MESSAGE_FILENAME);
        if (!messageFile.exists()) {
            this.plugin.saveResource(MESSAGE_FILENAME, false);
        }

        YamlConfiguration messageYaml = YamlConfiguration.loadConfiguration(messageFile);
        populateMessagesMap(this.customMessagesMap, messageYaml);
    }

    /**
     *
     * @param messagesMap
     * @param messageYaml
     */
    private void populateMessagesMap(@NotNull Map<String, String> messagesMap,
                                     @NotNull YamlConfiguration messageYaml) {

        messagesMap.clear();
        for (MessageKey messageKey : MessageKey.values()) {
            messagesMap.put(
                    messageKey.name(),
                    Formatter.colourise(messageYaml.getString(messageKey.name()))
            );
        }
        Logging.debug(String.valueOf(messagesMap));
    }

    /**
     * {@inheritDoc}
     */
    public @NotNull String parseMessage(@NotNull MessageKey messageKey,
                                        @Nullable Object...replacements) {

        String message = getMessage(messageKey);
        if (message == null) {
            Logging.warning("No message for key: %s", messageKey);
            return INVALID_MESSAGE;
        }
        if (message.length() == 0 || replacements == null || replacements.length == 0) {
            return message;
        }

        return doReplacements(message, replacements);
    }

    /**
     *
     * @param message
     * @param replacements
     * @return
     */
    private @NotNull String doReplacements(@NotNull String message,
                                           @NotNull Object[] replacements) {
        int index = 1;
        for (Object replacement : replacements) {
            message = message.replace("%" + index++, String.valueOf(replacement));
        }
        return message;
    }

    /**
     * {@inheritDoc}
     */
    public @Nullable String getMessage(@NotNull MessageKey messageKey) {
        String message = this.customMessagesMap.get(messageKey.name());
        return (message == null)
                ? this.defaultMessagesMap.get(messageKey.name())
                : message;
    }

    /**
     * {@inheritDoc}
     */
    public void send(@NotNull CommandSender sender,
                     @NotNull MessageKey messageKey,
                     @Nullable Object...replacements) {

        String parsedMessage = parseMessage(messageKey, replacements);
        if (Strings.isNullOrEmpty(parsedMessage)) {
            return;
        }
        sender.sendMessage(parsedMessage);
    }
}
