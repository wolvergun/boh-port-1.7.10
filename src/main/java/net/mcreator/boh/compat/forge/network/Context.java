package net.mcreator.boh.compat.forge.network;

import net.minecraft.entity.player.EntityPlayerMP;

public final class Context {
    private final EntityPlayerMP sender;
    private final NetworkDirection direction;
    private boolean handled;

    public Context(EntityPlayerMP sender, NetworkDirection direction) {
        this.sender = sender;
        this.direction = direction;
    }

    public EntityPlayerMP getSender() {
        return this.sender;
    }

    public NetworkDirection getDirection() {
        return this.direction;
    }

    public void enqueueWork(Runnable r) {
        r.run();
    }

    public void setPacketHandled(boolean b) {
        this.handled = b;
    }

    public boolean getPacketHandled() {
        return this.handled;
    }
}
