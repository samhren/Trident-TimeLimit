package net.sxmaa.timelimiter;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.7.10]")
public class TimeLimiter {

    @Mod.Instance("timelimiter")
    public static TimeLimiter instance;
    private static Logger LOG = LogManager.getLogger(Tags.MODID);

    @SidedProxy(clientSide = Tags.GROUPNAME + ".ClientProxy", serverSide = Tags.GROUPNAME + ".CommonProxy")
    public static CommonProxy proxy;
    private Timer timer;
    static HashMap<EntityPlayer, Date> Playerlist;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes."
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this."
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        proxy.serverAboutToStart(event);
    }

    @Mod.EventHandler
    // register server commands in this event handler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        proxy.serverStarted(event);
        this.setupTimerDelay(5);
        info("hello from server started event");
        System.out.println("eyo from server started");
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        proxy.serverStopping(event);
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        proxy.serverStopped(event);
    }

    public MinecraftServer getServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    public static String getSideString() {
        return FMLCommonHandler.instance().getSide().toString().toLowerCase();
    }

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


    public static void debug(String message) {
        LOG.debug(message);
    }

    public static void info(String message) {
        LOG.info(message);
    }

    public static void warn(String message) {
        LOG.warn(message);
    }

    public static void error(String message) {
        LOG.error(message);
    }
}
