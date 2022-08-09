package net.sxmaa.timelimiter;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import java.time.Instant;
import java.util.Date;


public class EventListener{

    static boolean playerOnline;

    EventListener() {
        System.out.println("hello from event listener ------");
    }

    @SubscribeEvent
    public void login(final PlayerEvent.PlayerLoggedInEvent event) {
        Date now = Date.from(Instant.now());
        System.out.println("--player joined--");
        CommonProxy.addPlayer(event.player, now);
        final int pt = TimeLimiter.proxy.playerTimeWallet.getTime(event.player.getUniqueID().toString());
        if(pt <= 0) {
            event.setCanceled(true);
        }
        EventListener.playerOnline = true;
    }


    @SubscribeEvent
    public void logout(final PlayerEvent.PlayerLoggedOutEvent event) {
        System.out.println("--player left--");
        CommonProxy.removePlayer(event.player);
        EventListener.playerOnline = false;
    }

    static {
        TimeLimiter.info("EventListener Running");
        EventListener.playerOnline = false;
    }

}
