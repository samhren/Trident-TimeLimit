package net.sxmaa.timelimiter;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;


public class EventListener{

    static boolean playerOnline;

    EventListener() {
        System.out.println("hello from event listener ------");
    }


    @SubscribeEvent
    public static void login(final PlayerEvent.PlayerLoggedInEvent event) {
        TimeLimiter.Playerlist.put(event.player, new java.util.Date(System.currentTimeMillis()));

        //assert TimeLimiter.Playerlist != null;
        System.out.println("current players:"+ TimeLimiter.Playerlist);
        EventListener.playerOnline = true;
    }


    @SubscribeEvent
    public static void logout(final PlayerEvent.PlayerLoggedOutEvent event) {
        if (TimeLimiter.Playerlist != null) {
            TimeLimiter.Playerlist.remove(event.player);
        }

        //assert TimeLimiter.Playerlist != null;
        System.out.println("current players:"+ TimeLimiter.Playerlist);
    }

    static {
        TimeLimiter.info("EventListener Running");
        EventListener.playerOnline = false;
    }

}
