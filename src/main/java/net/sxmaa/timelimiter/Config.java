package net.sxmaa.timelimiter;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class Config {

    private static class Defaults {
        public static final String greeting = "Hello World";
    }

    private static class Categories {
        public static final String general = "general";
    }

    public static String greeting = Defaults.greeting;

    public static void syncronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        configuration.load();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}

