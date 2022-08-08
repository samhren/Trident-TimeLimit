package net.sxmaa.timelimiter;

import java.io.File;
import net.minecraftforge.common.config.*;

public class ModConfig extends Configuration
{
    // config elements
    public static boolean doPlayerTimeLimit = true;
    public static int playerTimeLimitResetInterval = 86400;
    public static int playerTimeLimit = 7200;

    public ModConfig(String configDir){
        super(
            new File(configDir + Tags.MODID + ".cfg")
        );

        this.load();
        loadConfiguration();
        this.save();
    }

    private void loadConfiguration() {

        doPlayerTimeLimit = this.getBoolean(
            "do player time limit",
            Configuration.CATEGORY_GENERAL,
            true,
            "Limit the time every player can spend on the server");

        playerTimeLimitResetInterval = this.getInt(
            "time limit reset interval",
            Configuration.CATEGORY_GENERAL,
            playerTimeLimitResetInterval,
            0,
            604800 /* 1 week*/,
            "Time to pass between each reset of a players time wallet to the value of \"time limit\"");

        playerTimeLimit = this.getInt(
            "player time limit",
            Configuration.CATEGORY_GENERAL,
            playerTimeLimit /*2 hours */,
            0,
            playerTimeLimitResetInterval,
            "Time each player can spend on the server within one interval.");

    }
}

