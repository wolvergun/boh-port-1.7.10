package net.mcreator.boh.compat.mc.client.renderer.entity.player;

import net.mcreator.boh.compat.mc.client.model.PlayerModel;

/** 1.20 PlayerRenderer: only its model's arms are used (first-person gun rendering). */
public class PlayerRenderer {

    public static final PlayerRenderer INSTANCE = new PlayerRenderer();
    private final PlayerModel<Object> model = new PlayerModel<>(null, false);

    @SuppressWarnings("rawtypes")
    public PlayerModel getModel() {
        return model;
    }
}
