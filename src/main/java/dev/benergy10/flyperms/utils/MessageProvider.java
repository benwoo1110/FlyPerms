package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.Constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class MessageProvider {

    private static final String MESSAGE_FILENAME = "messages.yml";
    private static final String INVALID_MESSAGE = "!!INVALID!!";

    private final FlyPerms plugin;
    private Map<String, String> defaultMessagesMap;
    private Map<String, String> messagesMap;

    public MessageProvider(FlyPerms plugin) {
        this.plugin = plugin;
        loadDefault();
    }

    private void loadDefault() {
        this.defaultMessagesMap = new HashMap<>(MessageKey.values().length);

        InputStream messageFileStream = this.plugin.getResource(MESSAGE_FILENAME);
        if (messageFileStream == null) {
            Logging.severe("Unable to load default messages!");
            return;
        }

        Reader messageFileReader = new InputStreamReader(messageFileStream);
        YamlConfiguration messageYaml = YamlConfiguration.loadConfiguration(messageFileReader);

        Logging.debug("Loading default locale...");

        for (MessageKey messageKey : MessageKey.values()) {
            this.defaultMessagesMap.put(messageKey.name(), messageYaml.getString(messageKey.name(), INVALID_MESSAGE));
            Logging.debug(String.valueOf(messageKey));
            Logging.debug(String.valueOf(this.defaultMessagesMap));
        }
    }

    public void load() {
        // this.messagesMap = new HashMap<>(Messages.values().length);
    }

    public String getMessage(MessageKey messageKey) {
        return this.defaultMessagesMap.getOrDefault(messageKey.name(), INVALID_MESSAGE);
    }

    public void send(CommandSender sender, MessageKey messageKey) {
        sender.sendMessage(getMessage(messageKey));
    }
}
