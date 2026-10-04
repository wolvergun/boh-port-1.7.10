package net.mcreator.boh.compat.forge.event.entity;

import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;

public class EntityTravelToDimensionEvent extends EntityEvent {
    private final int dimension;

    public EntityTravelToDimensionEvent() {
        this(null, 0);
    }

    public EntityTravelToDimensionEvent(Entity entity, int dimension) {
        super(entity);
        this.dimension = dimension;
    }

    public ResourceKey<World> getDimension() {
        return Dimensions.key(this.dimension);
    }
}
