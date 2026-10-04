package net.mcreator.boh.compat.forge.client.event;

import java.util.LinkedHashMap;
import java.util.Map;

import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.eventhandler.Event;

/** 1.20 RegisterDimensionSpecialEffectsEvent. */
public class RegisterDimensionSpecialEffectsEvent extends Event {

    public final Map<ResourceLocation, DimensionSpecialEffects> effects = new LinkedHashMap<>();

    public void register(ResourceLocation id, DimensionSpecialEffects e) {
        effects.put(id, e);
    }
}
