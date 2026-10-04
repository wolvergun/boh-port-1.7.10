package net.mcreator.boh.compat.client;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;

/** Renderer factories collected from the 1.20 registration events; bound to 1.7.10 registries by MClientImpl. */
public final class ClientRegistry {

    public static final Map<EntityType<?>, Function<Context, ?>> ENTITY = new LinkedHashMap<>();
    public static final Map<BlockEntityType<?>, Function<Context, ?>> BLOCK_ENTITY = new LinkedHashMap<>();

    private ClientRegistry() {}

    public static void entityRenderer(EntityType<?> type, Function<Context, ?> f) {
        ENTITY.put(type, f);
    }

    public static void blockEntityRenderer(BlockEntityType<?> type, Function<Context, ?> f) {
        BLOCK_ENTITY.put(type, f);
    }
}
