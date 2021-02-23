package dev.benergy10.flyperms.utils;

import com.google.common.base.Strings;
import dev.benergy10.flyperms.Constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import dev.benergy10.flyperms.api.MessageProvider;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

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

    public SimpleMessageProvider(FlyPerms plugin) {
        this.plugin = plugin;
        this.defaultMessagesMap = new HashMap<>(MessageKey.values().length);
        this.customMessagesMap = new HashMap<>(MessageKey.values().length);

        loadDefault();
        load();
    }

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

    public void load() {
        File messageFile = new File(this.plugin.getDataFolder(), MESSAGE_FILENAME);
        if (!messageFile.exists()) {
            this.plugin.saveResource(MESSAGE_FILENAME, false);
        }

        YamlConfiguration messageYaml = YamlConfiguration.loadConfiguration(messageFile);
        populateMessagesMap(this.customMessagesMap, messageYaml);
    }

    private void populateMessagesMap(Map<String, String> messagesMap, YamlConfiguration messageYaml) {
        messagesMap.clear();
        for (MessageKey messageKey : MessageKey.values()) {
            messagesMap.put(
                    messageKey.name(),
                    Formatter.colourise(messageYaml.getString(messageKey.name()))
            );
        }
        Logging.debug(String.valueOf(messagesMap));
    }

    public String parseMessage(MessageKey messageKey, Object...replacements) {
        String message = getMessage(messageKey);
        if (message == null) {
            Logging.warning("No message for key: %s", messageKey);
            return INVALID_MESSAGE;
        }
        if (message.length() == 0|| replacements == null || replacements.length == 0) {
            return message;
        }

        return doReplacements(message, replacements);
    }

    private String doReplacements(String message, Object[] replacements) {
        int index = 1;
        for (Object replacement : replacements) {
            message = message.replace("%" + index++, String.valueOf(replacement));
        }
        return message;
    }

    public String getMessage(MessageKey messageKey) {
        String message = this.customMessagesMap.get(messageKey.name());
        return (message == null)
                ? this.defaultMessagesMap.get(messageKey.name())
                : message;
    }

    public void send(CommandSender sender, MessageKey messageKey, Object...replacements) {
        String parsedMessage = parseMessage(messageKey, replacements);
        if (Strings.isNullOrEmpty(parsedMessage)) {
            return;
        }
        sender.sendMessage(parsedMessage);
    }
}
