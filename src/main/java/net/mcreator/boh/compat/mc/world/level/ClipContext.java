package net.mcreator.boh.compat.mc.world.level;

import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;

public class ClipContext {
    public final Vec3 from;
    public final Vec3 to;
    public final ClipBlock block;
    public final ClipFluid fluid;

    public ClipContext(Vec3 from, Vec3 to, ClipBlock block, ClipFluid fluid, Entity entity) {
        this.from = from;
        this.to = to;
        this.block = block;
        this.fluid = fluid;
    }

    public Vec3 getFrom() {
        return this.from;
    }

    public Vec3 getTo() {
        return this.to;
    }
}
