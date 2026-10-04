package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.EntityCreature;

public class WaterAvoidingRandomStrollGoal extends RandomStrollGoal {
    public WaterAvoidingRandomStrollGoal(EntityCreature mob, double speed) {
        super(mob, speed);
    }

    public WaterAvoidingRandomStrollGoal(EntityCreature mob, double speed, float probability) {
        super(mob, speed);
    }

    @Override
    protected Vec3 getPosition() {
        if (this.mob.isInWater()) {
            return super.getPosition();
        } else {
            for (int i = 0; i < 10; i++) {
                Vec3 v = super.getPosition();
                if (v == null) {
                    return null;
                }

                int x = (int)Math.floor(v.x);
                int y = (int)Math.floor(v.y);
                int z = (int)Math.floor(v.z);
                if (!this.mob.worldObj.getBlock(x, y - 1, z).getMaterial().isLiquid()
                    && !this.mob.worldObj.getBlock(x, y, z).getMaterial().isLiquid()) {
                    return v;
                }
            }

            return null;
        }
    }
}
