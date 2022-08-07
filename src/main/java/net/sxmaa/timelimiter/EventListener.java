package net.sxmaa.timelimiter;


import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class EventListener extends Event {

    static boolean playerOnline;

    private EventListener() {
    }

    @SubscribeEvent
    public static void login(final PlayerEvent.PlayerLoggedInEvent event) {
        TimeLimiter.Playerlist.add(event.player)
    }

    static {
        EventListener.playerOnline = false;
    }

}
