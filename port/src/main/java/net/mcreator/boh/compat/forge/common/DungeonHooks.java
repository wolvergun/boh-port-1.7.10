package net.mcreator.boh.compat.forge.common;

import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityList;

/** Forge DungeonHooks over 1.7.10's name-based dungeon mob table. */
public final class DungeonHooks {

    private DungeonHooks() {}

    public static float addDungeonMob(EntityType<?> type, int rarity) {
        if (type == null || type.getEntityClass() == null) return 0;
        Object name = EntityList.classToStringMapping.get(type.getEntityClass());
        return name == null ? 0 : net.minecraftforge.common.DungeonHooks.addDungeonMob((String) name, rarity);
    }
}
