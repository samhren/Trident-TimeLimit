package net.sxmaa.timelimiter.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.sxmaa.timelimiter.client.ClientBossBar;

public class BossBarUpdateMessage implements IMessage {

    private int remainingTimeMs;
    private int maxTimeMs;

    public BossBarUpdateMessage() {
    }

    public BossBarUpdateMessage(int remainingTimeMs, int maxTimeMs) {
        this.remainingTimeMs = remainingTimeMs;
        this.maxTimeMs = maxTimeMs;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.remainingTimeMs = buf.readInt();
        this.maxTimeMs = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.remainingTimeMs);
        buf.writeInt(this.maxTimeMs);
    }

    public int getRemainingTimeMs() {
        return remainingTimeMs;
    }

    public int getMaxTimeMs() {
        return maxTimeMs;
    }

    public static class Handler implements IMessageHandler<BossBarUpdateMessage, IMessage> {

        @Override
        public IMessage onMessage(final BossBarUpdateMessage message, MessageContext ctx) {
            Minecraft.getMinecraft().func_152344_a(new Runnable() {
                @Override
                public void run() {
                    ClientBossBar.updateBossBar(message.getRemainingTimeMs(), message.getMaxTimeMs());
                }
            });
            return null;
        }
    }
}
