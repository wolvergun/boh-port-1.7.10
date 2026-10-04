package net.mcreator.boh.compat.forge.network;

public enum NetworkDirection {
    PLAY_TO_SERVER,
    PLAY_TO_CLIENT;

    public NetworkDirection.LogicalSide getReceptionSide() {
        return this == PLAY_TO_SERVER ? NetworkDirection.LogicalSide.SERVER : NetworkDirection.LogicalSide.CLIENT;
    }

    public static enum LogicalSide {
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
