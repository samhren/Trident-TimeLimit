package net.sxmaa.timelimiter;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.time.Instant;
import java.util.*;

import org.apache.commons.lang3.time.DateUtils;

import com.ibm.icu.util.DateRule;


public class CommonProxy {

    private static final HashMap<EntityPlayer, Date> playerlist = new HashMap<>();
    public ModConfig modConfig;
    public PlayerTimeWallet playerTimeWallet;
    private Timer timer;
    private long now;
    private long Udelay;

    public static void addPlayer(EntityPlayer player, Date time) {

        playerlist.put(player, time);
        TimeLimiter.proxy.playerTimeWallet.update(player.getUniqueID().toString(),0);
        System.out.println("Players: "+playerlist);
    }

    public static void removePlayer(EntityPlayer player) {

        int currentTimeSeconds = (int) (
            Math.ceil(
                System.currentTimeMillis() -
                playerlist.get(player).getTime()
            )
            / 1000
        );

        int playerUpdateValue = (int)(
            currentTimeSeconds % 
            TimeLimiter.proxy.modConfig.get_playerTimeLimitUpdateInterval()
        );

        System.out.println(playerUpdateValue);
        TimeLimiter.proxy.playerTimeWallet.update(player.getUniqueID().toString(), playerUpdateValue);
        playerlist.remove(player);
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
        Udelay = modConfig.get_playerTimeLimitUpdateInterval()* 1000;
        this.setupTimerDelay(Udelay);

        this.playerTimeWallet.overrideLastUpdate(Date.from(Instant.now()).toString());
    }

    public void serverStopping(FMLServerStoppingEvent event) {}

    public void serverStopped(FMLServerStoppedEvent event) {}

    void setupTimer(final long time) {
        if (this.timer != null) {
            this.timer.cancel();
        }
        this.timer = new Timer();
        if (Udelay > 0L) {
            this.timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    now = System.currentTimeMillis();
                    playerlist.forEach((entityPlayer, date) -> {
                        int timeToLogin = (int)(now - date.getTime()) / 1000;
                        int timeUpdate = timeToLogin < (int)modConfig.get_playerTimeLimitUpdateInterval() ? timeToLogin : (int)modConfig.get_playerTimeLimitUpdateInterval();
                        playerTimeWallet.update(entityPlayer.getUniqueID().toString(), timeUpdate);

                        if(playerTimeWallet.getTime(entityPlayer.getUniqueID().toString()) <= 0) {
                            ((EntityPlayerMP)entityPlayer).playerNetServerHandler.kickPlayerFromServer("Your free trial of life has expired.");
                        }
                        
                        Date currentUpdate = Date.from(Instant.now());
                        playerTimeWallet.overrideLastUpdate(currentUpdate.toString());
                    });
                }
            }, new Date(time), Udelay);
        }
        else {
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    TimeLimiter.error("idk what just happened, but its related to the timer");
                }
            }, new Date(time));
        }
    }

    void setupTimerDelay(final long delay) {
        this.setupTimer(System.currentTimeMillis() + delay);
    }

}
