package net.sxmaa.timelimiter;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import net.minecraft.entity.player.EntityPlayer;

import java.util.*;

public class CommonProxy {

    public static ArrayList<EntityPlayer> plist = new ArrayList<>();
    private static HashMap<EntityPlayer, Date> playerlist = new HashMap<>();
    public ModConfig modConfig;
    public PlayerTimeWallet playerTimeWallet;
    private Timer timer;

    public static void addPlayer(EntityPlayer player, Date time) {
        playerlist.put(player, time);
        System.out.println("Players: "+playerlist);
    }

    public static void removePlayer(EntityPlayer player) {
        playerlist.remove(player);
        System.out.println("Players: "+playerlist);
    }

    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event) {
        Config.syncronizeConfiguration(event.getSuggestedConfigurationFile());

        TimeLimiter.info(Config.greeting);
        TimeLimiter.info("I am " + Tags.MODNAME + " at version " + Tags.VERSION + " and group name " + Tags.GROUPNAME);

        this.modConfig = new ModConfig(
            event.getSuggestedConfigurationFile()
        );

        this.playerTimeWallet = new PlayerTimeWallet(
            event.getModConfigurationDirectory().toString()
        );
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes."
    public void init(FMLInitializationEvent event) {
        System.out.println("hello from common fml init");
        FMLCommonHandler.instance().bus().register(new EventListener());
    }

    // postInit "Handle interaction with other mods, complete your setup based on this."
    public void postInit(FMLPostInitializationEvent event) {}

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
    }

    // register server commands in this event handler
    public void serverStarting(FMLServerStartingEvent event) {}

    public void serverStarted(FMLServerStartedEvent event) {
        this.setupTimerDelay(5);
    }

    public void serverStopping(FMLServerStoppingEvent event) {}

    public void serverStopped(FMLServerStoppedEvent event) {}

    void setupTimer(final long time) {
        if (this.timer != null) {
            this.timer.cancel();
        }
        this.timer = new Timer();
        final long delay = 5;
        if (delay > 0L) {
            this.timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    //5 min check
                }
            }, new Date(time), delay);
        }
        else {
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //first start
                }
            }, new Date(time));
        }
    }

    void setupTimerDelay(final long delay) {
        this.setupTimer(System.currentTimeMillis() + delay);
    }

}
