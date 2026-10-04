package net.mcreator.boh.compat.forge.network;

/** Forge NetworkDirection. */
public enum NetworkDirection {

    PLAY_TO_SERVER,
    PLAY_TO_CLIENT;

    public LogicalSide getReceptionSide() {
        return this == PLAY_TO_SERVER ? LogicalSide.SERVER : LogicalSide.CLIENT;
    }

    public enum LogicalSide {
        CLIENT,
        SERVER;

        public boolean isServer() {
            return this == SERVER;
        }

        public boolean isClient() {
            return this == CLIENT;
        }
    }
}
