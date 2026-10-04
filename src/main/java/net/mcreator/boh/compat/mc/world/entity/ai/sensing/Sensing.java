package net.mcreator.boh.compat.mc.world.entity.ai.sensing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class Sensing {
    private final EntityLiving mob;

    public Sensing(EntityLiving mob) {
        this.mob = mob;
    }

    public boolean hasLineOfSight(Entity e) {
        return this.mob.getEntitySenses().canSee(e);
    }
}
