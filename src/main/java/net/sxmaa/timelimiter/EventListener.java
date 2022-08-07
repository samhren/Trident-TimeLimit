package net.sxmaa.timelimiter;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;


public class EventListener{

    static boolean playerOnline;

    EventListener() {
        System.out.println("hello from event listener ------");
    }

    @SubscribeEvent
    public void test(final PlayerEvent.ItemPickupEvent event) {
        System.out.println("TEST SUCCESSFULL");
    }

    @SubscribeEvent
    public void login(final PlayerEvent.PlayerLoggedInEvent event) {
        System.out.println("--player joined--");
        CommonProxy.plist.add(event.player);
        //TimeLimiter.Playerlist.put(event.player, new java.util.Date(System.currentTimeMillis()));

        System.out.println("current players:"+ CommonProxy.plist);
        EventListener.playerOnline = true;
    }


    @SubscribeEvent
    public void logout(final PlayerEvent.PlayerLoggedOutEvent event) {
        System.out.println("--player left--");
        CommonProxy.plist.remove(event.player);

        //assert TimeLimiter.Playerlist != null;
        System.out.println("current players:"+ CommonProxy.plist);
    }

    static {
        TimeLimiter.info("EventListener Running");
        EventListener.playerOnline = false;
    }

}
