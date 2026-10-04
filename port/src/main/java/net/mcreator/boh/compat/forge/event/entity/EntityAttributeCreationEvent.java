package net.mcreator.boh.compat.forge.event.entity;

import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.AttributeSupplier;
import cpw.mods.fml.common.eventhandler.Event;

/** 1.20 EntityAttributeCreationEvent. */
public class EntityAttributeCreationEvent extends Event {

    public void put(EntityType<?> type, AttributeSupplier supplier) {
        if (type != null && type.getEntityClass() != null) AttributeSupplier.register(type.getEntityClass(), supplier);
    }
}
