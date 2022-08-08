package net.sxmaa.timelimiter;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import java.time.Instant;
import java.util.Date;


public class EventListener{

    static boolean playerOnline;
    private Date now;

    EventListener() {
        System.out.println("hello from event listener ------");
    }

    @SubscribeEvent
    public void login(final PlayerEvent.PlayerLoggedInEvent event) {
        now = Date.from(Instant.now());

        System.out.println("--player joined--");
        CommonProxy.plist.add(event.player);
        CommonProxy.addPlayer(event.player, now);

        //System.out.println("current players:"+ CommonProxy.plist);
        EventListener.playerOnline = true;
    }


    @SubscribeEvent
    public void logout(final PlayerEvent.PlayerLoggedOutEvent event) {
        System.out.println("--player left--");
        CommonProxy.plist.remove(event.player);
        CommonProxy.removePlayer(event.player);

        //System.out.println("current players:"+ CommonProxy.plist);
        EventListener.playerOnline = false;

    }

    static {
        TimeLimiter.info("EventListener Running");
        EventListener.playerOnline = false;
    }

}
