package net.mcreator.boh.compat.mc.advancements;

import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

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
        return new ResourceLocation(this.id);
    }

    public Advancement getParent() {
        return this.parent;
    }
}
