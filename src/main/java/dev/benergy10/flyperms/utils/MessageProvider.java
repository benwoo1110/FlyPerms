package dev.benergy10.flyperms.utils;

import com.google.common.base.Strings;
import dev.benergy10.flyperms.Constants.MessageKey;
import dev.benergy10.flyperms.FlyPerms;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
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
        loadCustom();
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

    public void loadCustom() {
        this.customMessagesMap = new HashMap<>(MessageKey.values().length);

        File messageFile = new File(this.plugin.getDataFolder(), MESSAGE_FILENAME);
        if (!messageFile.exists()) {
            this.plugin.saveResource(MESSAGE_FILENAME, false);
        }

        YamlConfiguration messageYaml = YamlConfiguration.loadConfiguration(messageFile);
        populateMessagesMap(this.customMessagesMap, messageYaml);
    }

    private void populateMessagesMap(Map<String, String> messagesMap, YamlConfiguration messageYaml) {
        for (MessageKey messageKey : MessageKey.values()) {
            messagesMap.put(
                    messageKey.name(),
                    colourise(messageYaml.getString(messageKey.name()))
            );
        }
        Logging.debug(String.valueOf(messagesMap));
    }

    private String colourise(String message) {
        return (Strings.isNullOrEmpty(message))
                ? message
                : ChatColor.translateAlternateColorCodes(COLOUR_CHAR, message);
    }

    public String parseMessage(MessageKey messageKey, Object...replacements) {
        String message = getMessage(messageKey);

        if (replacements == null || replacements.length == 0) {
            return message;
        }

        int index = 1;
        for (Object replacement : replacements) {
            message = message.replace("%" + index++, String.valueOf(replacement));
        }
        return message;
    }

    private String getMessage(MessageKey messageKey) {
        String message = this.customMessagesMap.get(messageKey.name());
        return (message == null)
                ? this.defaultMessagesMap.getOrDefault(messageKey.name(), INVALID_MESSAGE)
                : message;
    }

    public void send(CommandSender sender, MessageKey messageKey, Object...replacements) {
        sender.sendMessage(parseMessage(messageKey, replacements));
    }
}
