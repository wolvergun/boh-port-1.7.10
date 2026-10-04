package net.mcreator.boh.compat.forge.common;

import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityList;

public final class DungeonHooks {
    private DungeonHooks() {
    }

    public static float addDungeonMob(EntityType<?> type, int rarity) {
        if (type != null && type.getEntityClass() != null) {
            Object name = EntityList.classToStringMapping.get(type.getEntityClass());
            return name == null ? 0.0F : net.minecraftforge.common.DungeonHooks.addDungeonMob((String)name, rarity);
        } else {
            return 0.0F;
        }
    }
}
