package net.mcreator.boh.geo;

import net.minecraft.entity.Entity;

public final class DataTickets {
    public static final DataTicket<Double> TICK = new DataTicket<>("tick");
    public static final DataTicket<Entity> ENTITY = new DataTicket<>("entity");
    public static final DataTicket<EntityModelData> ENTITY_MODEL_DATA = new DataTicket<>("entity_model_data");
    public static final DataTicket<Object> ITEM_RENDER_PERSPECTIVE = new DataTicket<>("item_render_perspective");

    private DataTickets() {
    }
}
