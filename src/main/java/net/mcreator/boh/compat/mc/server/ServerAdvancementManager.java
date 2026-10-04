package net.mcreator.boh.compat.mc.server;

import net.mcreator.boh.compat.advancement.Advancements;
import net.mcreator.boh.compat.mc.advancements.Advancement;
import net.minecraft.util.ResourceLocation;

public final class ServerAdvancementManager {
    public static final ServerAdvancementManager INSTANCE = new ServerAdvancementManager();

    public Advancement getAdvancement(ResourceLocation id) {
        return Advancements.get(id.toString());
    }
}
