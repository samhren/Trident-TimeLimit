package net.sxmaa.timelimiter;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.*;

public class ModConfig
{
    private static Configuration configuration;

    // config elements
    public static boolean doPlayerTimeLimit;
    public static int playerTimeLimitResetInterval;
    public static int playerTimeLimit;

    public static void init(String configDir) {
        
        if(configuration == null) {
            File path = new File(configDir + Tags.MODID + ".cfg");

            configuration = new Configuration(path);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        
        doPlayerTimeLimit = configuration.getBoolean("do player time limit", Configuration.CATEGORY_GENERAL, true, "Limit the time every player can spend on the server");
        playerTimeLimitResetInterval = configuration.getInt("time limit reset interval", Configuration.CATEGORY_GENERAL, 86400, 0, 604800 /* 1 week*/, "Time to pass between each reset of a players time wallet to the value of \"time limit\"");
        playerTimeLimit = configuration.getInt("player time limit", Configuration.CATEGORY_GENERAL, 7200 /*2 hours */, 0, playerTimeLimitResetInterval, "Time each player can spend on the server within one interval.");

        if(configuration.hasChanged()) {
            configuration.save();
        }
    }

    @SubscribeEvent
    public static void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {

        if(event.modID.equalsIgnoreCase(Tags.MODID)) {
            loadConfiguration();
        }
    }

    public static Configuration getConfiguration() { return configuration; }
}

