package net.mcreator.boh.compat.forge.network;

import net.minecraft.entity.player.EntityPlayerMP;

/** Forge NetworkEvent.Context. */
public final class Context {

    private final EntityPlayerMP sender;
    private final NetworkDirection direction;
    private boolean handled;

    public Context(EntityPlayerMP sender, NetworkDirection direction) {
        this.sender = sender;
        this.direction = direction;
    }

    public EntityPlayerMP getSender() {
        return sender;
    }

    public NetworkDirection getDirection() {
        return direction;
    }

    /** 1.7.10 handlers already run on the game thread. */
    public void enqueueWork(Runnable r) {
        r.run();
    }

    public void setPacketHandled(boolean b) {
        handled = b;
    }

    public boolean getPacketHandled() {
        return handled;
    }
}
