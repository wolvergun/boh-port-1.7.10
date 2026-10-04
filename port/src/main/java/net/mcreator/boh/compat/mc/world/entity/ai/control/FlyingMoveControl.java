package net.mcreator.boh.compat.mc.world.entity.ai.control;

import net.mcreator.boh.compat.entity.BohMob;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.MathHelper;

/**
 * 1.20 FlyingMoveControl. Installs a flying {@link EntityMoveHelper} on the mob so that vanilla AI tasks and the
 * flying navigator both steer it through the air.
 */
public class FlyingMoveControl extends MoveControl {

    private final int maxTurn;
    private final boolean hoversInPlace;

    public FlyingMoveControl(EntityLiving mob, int maxTurn, boolean hoversInPlace) {
        super(mob);
        this.maxTurn = maxTurn;
        this.hoversInPlace = hoversInPlace;
        mob.moveHelper = new Helper();
    }

    @Override
    public void setWantedPosition(double x, double y, double z, double speed) {
        wantedX = x;
        wantedY = y;
        wantedZ = z;
        speedModifier = speed;
        operation = Operation.MOVE_TO;
    }

    @Override
    public boolean hasWanted() {
        return operation == Operation.MOVE_TO;
    }

    @Override
    public void tick() {
        if (operation == Operation.MOVE_TO) {
            operation = Operation.WAIT;
            BohMob.setNoGravity(mob, true);
            double dx = wantedX - mob.posX, dy = wantedY - mob.posY, dz = wantedZ - mob.posZ;
            double distSqr = dx * dx + dy * dy + dz * dz;
            if (distSqr < 2.5E-7) {
                mob.moveForward = 0;
                return;
            }
            float yaw = (float) (Math.atan2(dz, dx) * 180.0 / Math.PI) - 90.0F;
            mob.rotationYaw = rotlerp(mob.rotationYaw, yaw, 90.0F);
            double speed = (mob.onGround ? attr(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), 0.25)
                : attr(Attributes.FLYING_SPEED.getAttributeUnlocalizedName(), 0.4)) * speedModifier;
            double dist = Math.sqrt(distSqr);
            double horizontal = Math.sqrt(dx * dx + dz * dz);
            if (Math.abs(dy) > 1.0E-5 || horizontal > 1.0E-5) {
                float pitch = (float) (-(Math.atan2(dy, horizontal) * 180.0 / Math.PI));
                mob.rotationPitch = rotlerp(mob.rotationPitch, pitch, maxTurn);
            }
            mob.motionX += (dx / dist * speed - mob.motionX) * 0.25;
            mob.motionY += (dy / dist * speed - mob.motionY) * 0.25;
            mob.motionZ += (dz / dist * speed - mob.motionZ) * 0.25;
            mob.setAIMoveSpeed((float) speed);
        } else {
            if (!hoversInPlace) BohMob.setNoGravity(mob, false);
            mob.moveForward = 0;
        }
    }

    private double attr(String name, double fallback) {
        IAttributeInstance inst = mob.getAttributeMap().getAttributeInstanceByName(name);
        return inst == null ? fallback : inst.getAttributeValue();
    }

    private static float rotlerp(float from, float to, float max) {
        float d = MathHelper.wrapAngleTo180_float(to - from);
        if (d > max) d = max;
        if (d < -max) d = -max;
        return from + d;
    }

    /** Vanilla-facing helper: tasks call setMoveTo, the entity ticks onUpdateMoveHelper. */
    private final class Helper extends EntityMoveHelper {

        Helper() {
            super(FlyingMoveControl.this.mob);
        }

        @Override
        public void setMoveTo(double x, double y, double z, double speed) {
            FlyingMoveControl.this.setWantedPosition(x, y, z, speed);
        }

        @Override
        public boolean isUpdating() {
            return FlyingMoveControl.this.operation == Operation.MOVE_TO;
        }

        @Override
        public double getSpeed() {
            return FlyingMoveControl.this.speedModifier;
        }

        @Override
        public void onUpdateMoveHelper() {
            FlyingMoveControl.this.tick();
        }
    }
}
