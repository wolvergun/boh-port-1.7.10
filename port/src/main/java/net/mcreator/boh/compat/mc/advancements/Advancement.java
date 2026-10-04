package net.mcreator.boh.compat.mc.advancements;

import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

/** A 1.20 advancement backed by a 1.7.10 achievement. */
public final class Advancement {

    public final String id;
    public final Achievement achievement;
    public final Advancement parent;

    public Advancement(String id, Achievement achievement, Advancement parent) {
        this.id = id;
        this.achievement = achievement;
        this.parent = parent;
    }

    public ResourceLocation getId() {
        return new ResourceLocation(id);
    }

    public Advancement getParent() {
        return parent;
    }
}
