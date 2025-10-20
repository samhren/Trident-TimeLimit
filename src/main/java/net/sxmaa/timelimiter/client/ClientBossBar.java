package net.sxmaa.timelimiter.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.MathHelper;

public class ClientBossBar {

    private static final long DISPLAY_REFRESH_INTERVAL = 1000L;

    private static int syncedRemainingTime;
    private static int maxTime;
    private static long lastSyncTimestamp;
    private static long lastDisplayUpdate;
    private static boolean hasSyncedData;

    public static void updateBossBar(int remainingTimeMs, int maxTimeMs) {
        syncedRemainingTime = Math.max(remainingTimeMs, 0);
        maxTime = Math.max(maxTimeMs, 0);
        lastSyncTimestamp = System.currentTimeMillis();
        lastDisplayUpdate = 0L;
        hasSyncedData = true;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (Minecraft.getMinecraft().thePlayer == null) {
            return;
        }

        if (!hasSyncedData) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        int currentRemaining = Math.max(0, syncedRemainingTime - (int) (currentTime - lastSyncTimestamp));

        if (currentTime - lastDisplayUpdate > DISPLAY_REFRESH_INTERVAL) {
            float progress = maxTime <= 0 ? 1.0F : MathHelper.clamp_float((float) currentRemaining / (float) maxTime, 0.0F, 1.0F);
            String formattedTime = maxTime <= 0 ? "Time Remaining: Unlimited" : formatTime(currentRemaining);

            BossStatus.bossName = formattedTime;
            BossStatus.healthScale = progress;
            BossStatus.statusBarTime = 100;
            BossStatus.hasColorModifier = false;

            lastDisplayUpdate = currentTime;
        } else {
            BossStatus.statusBarTime = 100;
        }
    }

    private static String formatTime(int milliseconds) {
        int totalSeconds = milliseconds / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        if (hours > 0) {
            return String.format("Time Remaining: %d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("Time Remaining: %02d:%02d", minutes, seconds);
    }
}
