package net.mcreator.boh.compat.mc.client.renderer.entity.player;

import net.mcreator.boh.compat.mc.client.model.PlayerModel;

public class PlayerRenderer {
    public static final PlayerRenderer INSTANCE = new PlayerRenderer();
    private final PlayerModel<Object> model = new PlayerModel<>(null, false);

    public PlayerModel getModel() {
        return this.model;
    }
}
