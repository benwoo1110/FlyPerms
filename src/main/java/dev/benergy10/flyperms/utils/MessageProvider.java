package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.Constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class MessageProvider {

    private static final char COLOUR_CHAR = '&';
    private static final String MESSAGE_FILENAME = "messages.yml";
    private static final String INVALID_MESSAGE = "&c!!INVALID!!";

    private final FlyPerms plugin;
    private Map<String, String> defaultMessagesMap;
    private Map<String, String> customMessagesMap;

    public MessageProvider(FlyPerms plugin) {
        this.plugin = plugin;
        loadDefault();
    }

    private void loadDefault() {
        Logging.debug("Loading default locale...");
        this.defaultMessagesMap = new HashMap<>(MessageKey.values().length);

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
        // this.messagesMap = new HashMap<>(Messages.values().length);
    }

    private void populateMessagesMap(Map<String, String> messagesMap, YamlConfiguration messageYaml) {
        for (MessageKey messageKey : MessageKey.values()) {
            messagesMap.put(
                    messageKey.name(),
                    colourise(messageYaml.getString(messageKey.name(), INVALID_MESSAGE))
            );
        }
        Logging.debug(String.valueOf(messagesMap));
    }

    private String colourise(String message) {
        return ChatColor.translateAlternateColorCodes(COLOUR_CHAR, message);
    }

    public String getMessage(MessageKey messageKey) {
        return this.defaultMessagesMap.getOrDefault(messageKey.name(), INVALID_MESSAGE);
    }

    public void send(CommandSender sender, MessageKey messageKey) {
        sender.sendMessage(getMessage(messageKey));
    }
}
