package net.mcreator.boh.compat.mc.world.level;

import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;

/** 1.20 ClipContext (ray trace request). */
public class ClipContext {

    public final Vec3 from, to;
    public final ClipBlock block;
    public final ClipFluid fluid;

    public ClipContext(Vec3 from, Vec3 to, ClipBlock block, ClipFluid fluid, Entity entity) {
        this.from = from;
        this.to = to;
        this.block = block;
        this.fluid = fluid;
    }

    public Vec3 getFrom() {
        return from;
    }

    public Vec3 getTo() {
        return to;
    }
}
