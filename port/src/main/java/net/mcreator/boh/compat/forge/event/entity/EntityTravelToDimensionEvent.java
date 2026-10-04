package net.mcreator.boh.compat.forge.event.entity;

import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;

/** 1.20 EntityTravelToDimensionEvent; posted by the bridge when a player has changed dimension. */
public class EntityTravelToDimensionEvent extends EntityEvent {

    public EntityTravelToDimensionEvent() {
        this(null, 0);
    }

    private final int dimension;

    public EntityTravelToDimensionEvent(Entity entity, int dimension) {
        super(entity);
        this.dimension = dimension;
    }

    public ResourceKey<World> getDimension() {
        return Dimensions.key(dimension);
    }
}
