package net.mcreator.boh.compat.mc.client.renderer.entity;

import net.mcreator.boh.compat.mc.client.renderer.entity.player.PlayerRenderer;

/** 1.20 EntityRenderDispatcher (player renderer lookups). */
public final class EntityRenderDispatcher {

    public static final EntityRenderDispatcher INSTANCE = new EntityRenderDispatcher();

    public Object getRenderer(Object entity) {
        return PlayerRenderer.INSTANCE;
    }
}
