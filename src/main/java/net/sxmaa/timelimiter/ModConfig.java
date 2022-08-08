package net.sxmaa.timelimiter;

import java.io.File;
import net.minecraftforge.common.config.*;

public class ModConfig extends Configuration
{
    // config elements
    public static boolean doPlayerTimeLimit = true;
    public static int playerTimeLimitResetInterval = 86400;
    public static int playerTimeLimit = 7200;

    public ModConfig(File configFile){
        super(configFile);

        this.load();
        loadConfiguration();
        this.save();
    }

    private void loadConfiguration() {

        doPlayerTimeLimit = this.getBoolean(
            "do player time limit",
            super.CATEGORY_GENERAL,
            true,
            "Limit the time every player can spend on the server");

        playerTimeLimitResetInterval = this.getInt(
            "time limit reset interval",
            super.CATEGORY_GENERAL,
            86400, //1 day
            0,
            604800, //1 week
            "Time to pass between each reset of a players time wallet to the value of \"time limit\"");

        playerTimeLimit = this.getInt(
            "player time limit",
            super.CATEGORY_GENERAL,
            7200, //2 hours
            0,
            playerTimeLimitResetInterval,
            "Time each player can spend on the server within one interval.");

    }
}

