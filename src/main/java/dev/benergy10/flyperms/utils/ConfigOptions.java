package dev.benergy10.flyperms.utils;

import dev.benergy10.minecrafttools.configs.ConfigOption;
import dev.benergy10.minecrafttools.configs.ConfigOptionHandler;
import dev.benergy10.minecrafttools.utils.Logging;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigOptions {

    private static final List<ConfigOption<?>> options = new ArrayList<>();

    private static void register(ConfigOption<?> option) {
        options.add(option);
    }

    public static String[] header() {
        return new String[] {
                "+----------+",
                "| FlyPerms |",
                "+----------+",
                "Github repo: https://github.com/benwoo1110/FlyPerms",
                "Discord: https://discord.gg/Be59ehc",
                "Spigot: https://www.spigotmc.org/resources/flyperms-1-8-1-16.83432/",
                "Paypal: https://paypal.me/benergy10"
        };
    }

    public static Collection<ConfigOption<?>> getOptions() {
        return options;
    }

    public static final ConfigOption<Boolean> CHECK_WORLD = new ConfigOption.Builder<Boolean>()
            .path("check-for-world")
            .comment("")
            .comment("")
            .comment("+--------------+")
            .comment("| Fly Checking |")
            .comment("+--------------+")
            .comment("If enabled, players will need flyperms.allow.world.WORLDNAME permission node to fly.")
            .defaultValue(true)
            .register(ConfigOptions::register);

    public static final ConfigOption<Boolean> CHECK_GAMEMODE = new ConfigOption.Builder<Boolean>()
            .path("check-for-gamemode")
            .comment("")
            .comment("If enabled, players will need flyperms.allow.gamemode.GAMEMODE permission node to fly.")
            .defaultValue(false)
            .register(ConfigOptions::register);

    public static final ConfigOption<Boolean> ALLOW_IN_CREATIVE = new ConfigOption.Builder<Boolean>()
            .path("always-allow-in-creative")
            .comment("")
            .comment("If set if true, players will always be allowed to fly in creative mode regardless of permissions.")
            .comment("Basically restores the vanilla behaviour that players can fly in gamemode creative.")
            .defaultValue(true)
            .register(ConfigOptions::register);

    public static final ConfigOption<Integer> CHECK_INTERVAL = new ConfigOption.Builder<Integer>()
            .path("check-interval")
            .comment("")
            .comment("Amount of time (in milliseconds) between each check if player still has permission to fly.")
            .comment("NOTE: too large a number may cause significant delay in allow/disallowing flying on permission change.")
            .defaultValue(1000)
            .register(ConfigOptions::register);

    public static final ConfigOption<Integer> COOLDOWN = new ConfigOption.Builder<Integer>()
            .path("cooldown")
            .comment("")
            .comment("Amount of time (in milliseconds) before a player flight is disabled after their permission is removed.")
            .comment("This serves as a warning to players and can prevents sudden fall without warning.")
            .defaultValue(5000)
            .register(ConfigOptions::register);

    public static final ConfigOption<Boolean> FLY_ON_AIR_TELEPORT = new ConfigOption.Builder<Boolean>()
            .path("fly-on-air-teleport")
            .comment("")
            .comment("Auto-enable flying if teleported to a location in air. Only works if player is able to fly.")
            .defaultValue(true)
            .register(ConfigOptions::register);

    public static final ConfigOption<List<SpeedGroup>> SPEED_GROUPS = new ConfigOption.Builder<List<SpeedGroup>>()
            .path("speed-group")
            .comment("")
            .comment("")
            .comment("+-----------+")
            .comment("| Fly Speed |")
            .comment("+-----------+")
            .comment("Allow players to change their fly speed, from -10 to 10. Negative values indicate reverse directions.")
            .comment("Permission to give users the speed group is 'flyperms.speedgroup.<groupname>', which will give them access to the")
            .comment("range of speed as defined.")
            .comment("Command to change speed is '/fp speed <speed>'.")
            .defaultValue(Arrays.asList(
                    new SpeedGroup("default", 0, 2),
                    new SpeedGroup("special", 0, 5),
                    new SpeedGroup("admin", -10, 10)
            ))
            .handler(new ConfigOptionHandler<List<SpeedGroup>, Map<String, Object>>() {
                @Override
                public Map<String, Object> getData(YamlConfiguration config, String path) {
                    ConfigurationSection configurationSection = config.getConfigurationSection(path);
                    return configurationSection.getValues(false);
                }

                @Override
                public Object serialize(List<SpeedGroup> speedGroups) {
                    Map<String, List<Double>> speedData = new LinkedHashMap<>();
                    for (SpeedGroup group : speedGroups) {
                        List<Double> speedRange = new ArrayList<Double>() {{
                            add(group.getLowerLimit());
                            if (group.getLowerLimit() != group.getUpperLimit()) {
                                add(group.getUpperLimit());
                            }
                        }};
                        speedData.put(group.getName(), speedRange);
                    }
                    return speedData;
                }

                @Override
                public List<SpeedGroup> deserialize(Map<String, Object> data) {
                    List<SpeedGroup> speedGroups = new ArrayList<>();
                    for (Object rawGroupName : data.keySet()) {
                        List<Double> speedValue;
                        try {
                            speedValue = (List<Double>) data.get(rawGroupName);
                        }
                        catch (ClassCastException e) {
                            Logging.warning("Invalid speed group " + rawGroupName + ". Please check for config!");
                            continue;
                        }
                        if (!validateSpeedValue(speedValue)) {
                            Logging.warning("Invalid speed group " + rawGroupName + ". Please check for config!");
                            continue;
                        }
                        String groupName = String.valueOf(rawGroupName);
                        if (speedValue.size() == 2) {
                            speedGroups.add(new SpeedGroup(groupName, speedValue.get(0), speedValue.get(1)));
                        }
                        else if (speedValue.size() == 1) {
                            speedGroups.add(new SpeedGroup(groupName, speedValue.get(0)));
                        }
                    }
                    return speedGroups;
                }

                private boolean validateSpeedValue(List<Double> speedValue) {
                    return speedValue != null && ((speedValue.size() == 2 && speedValue.get(0) <= speedValue.get(1)) || speedValue.size() == 1);
                }
            })
            .register(ConfigOptions::register);

    public static final ConfigOption<Boolean> RESET_SPEED_WORLD = new ConfigOption.Builder<Boolean>()
            .path("reset-speed-world")
            .comment("")
            .comment("When true, speed will reset when player changes world, and can only be bypassed with")
            .comment("'flyperms.bypass.speed.world' permission node.")
            .defaultValue(false)
            .register(ConfigOptions::register);

    public static final ConfigOption<Boolean> RESET_SPEED_GAMEMODE = new ConfigOption.Builder<Boolean>()
            .path("reset-speed-gamemode")
            .comment("")
            .comment("When true, speed will reset when player changes gamemode, and can only be bypassed with")
            .comment("'flyperms.bypass.speed.gamemode' permission node.")
            .defaultValue(false)
            .register(ConfigOptions::register);

    public static final ConfigOption<Double> SPEED_RESET_VALUE = new ConfigOption.Builder<Double>()
            .path("cooldown")
            .comment("")
            .comment("When above reset option is enable, this will be the fly speed that players reset to. This option will bypass")
            .comment("the speed groups configuration.")
            .defaultValue(1.0)
            .register(ConfigOptions::register);

    public static final ConfigOption<List<String>> IGNORE_WORLDS = new ConfigOption.Builder<List<String>>()
            .path("ignore-in-worlds")
            .comment("")
            .comment("")
            .comment("+--------+")
            .comment("| Others |")
            .comment("+--------+")
            .comment("FlyPerms will not effect these worlds.")
            .comment("All perms and fly checks will not be done when players are in these worlds.")
            .defaultValue(new ArrayList<>())
            .register(ConfigOptions::register);

    public static final ConfigOption<Boolean> PAPI_HOOK = new ConfigOption.Builder<Boolean>()
            .path("enable-papi-hook")
            .comment("")
            .comment("There is already detection to only register FlyPerm's PlaceholderAPI extension when PlaceholderAPI is installed.")
            .comment("So there shouldn't be a need to worry. But in any case, if there is a need to disable FlyPerm's PlaceholderAPI")
            .comment("extension, you can set this to false.")
            .defaultValue(true)
            .register(ConfigOptions::register);

    public static final ConfigOption<Boolean> DO_DEBUG = new ConfigOption.Builder<Boolean>()
            .path("show-debug-info")
            .comment("")
            .comment("Used for development and troubleshooting purposes.")
            .defaultValue(false)
            .setConsumer(value -> {
                Logging.doDebugLog(value);
                Logging.debug("Debug mode enabled");
            })
            .register(ConfigOptions::register);
}
