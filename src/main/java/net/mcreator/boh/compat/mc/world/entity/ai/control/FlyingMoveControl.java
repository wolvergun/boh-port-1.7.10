package net.mcreator.boh.compat.mc.world.entity.ai.control;

import java.util.Objects;
import net.mcreator.boh.compat.entity.BohMob;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.MathHelper;

public class FlyingMoveControl extends MoveControl {
    private final int maxTurn;
    private final boolean hoversInPlace;

    public FlyingMoveControl(EntityLiving mob, int maxTurn, boolean hoversInPlace) {
        super(mob);
        this.maxTurn = maxTurn;
        this.hoversInPlace = hoversInPlace;
        mob.moveHelper = new FlyingMoveControl.Helper();
    }

    @Override
    public void setWantedPosition(double x, double y, double z, double speed) {
        this.wantedX = x;
        this.wantedY = y;
        this.wantedZ = z;
        this.speedModifier = speed;
        this.operation = Operation.MOVE_TO;
    }

    @Override
    public boolean hasWanted() {
        return this.operation == Operation.MOVE_TO;
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            this.operation = Operation.WAIT;
            BohMob.setNoGravity(this.mob, true);
            double dx = this.wantedX - this.mob.posX;
            double dy = this.wantedY - this.mob.posY;
            double dz = this.wantedZ - this.mob.posZ;
            double distSqr = dx * dx + dy * dy + dz * dz;
            if (distSqr < 2.5E-7) {
                this.mob.moveForward = 0.0F;
                return;
            }

            float yaw = (float)(Math.atan2(dz, dx) * 180.0 / Math.PI) - 90.0F;
            this.mob.rotationYaw = rotlerp(this.mob.rotationYaw, yaw, 90.0F);
            double speed = (
                    this.mob.onGround
                        ? this.attr(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), 0.25)
                        : this.attr(Attributes.FLYING_SPEED.getAttributeUnlocalizedName(), 0.4)
                )
                * this.speedModifier;
            double dist = Math.sqrt(distSqr);
            double horizontal = Math.sqrt(dx * dx + dz * dz);
            if (Math.abs(dy) > 1.0E-5 || horizontal > 1.0E-5) {
                float pitch = (float)(-(Math.atan2(dy, horizontal) * 180.0 / Math.PI));
                this.mob.rotationPitch = rotlerp(this.mob.rotationPitch, pitch, this.maxTurn);
            }

            this.mob.motionX = this.mob.motionX + (dx / dist * speed - this.mob.motionX) * 0.25;
            this.mob.motionY = this.mob.motionY + (dy / dist * speed - this.mob.motionY) * 0.25;
            this.mob.motionZ = this.mob.motionZ + (dz / dist * speed - this.mob.motionZ) * 0.25;
            this.mob.setAIMoveSpeed((float)speed);
        } else {
            if (!this.hoversInPlace) {
                BohMob.setNoGravity(this.mob, false);
            }

            this.mob.moveForward = 0.0F;
        }
    }

    private double attr(String name, double fallback) {
        IAttributeInstance inst = this.mob.getAttributeMap().getAttributeInstanceByName(name);
        return inst == null ? fallback : inst.getAttributeValue();
    }

    private static float rotlerp(float from, float to, float max) {
        float d = MathHelper.wrapAngleTo180_float(to - from);
        if (d > max) {
            d = max;
        }

        if (d < -max) {
            d = -max;
        }

        return from + d;
    }

    private final class Helper extends EntityMoveHelper {
        Helper() {
            Objects.requireNonNull(FlyingMoveControl.this);
            super(FlyingMoveControl.this.mob);
        }

        public void setMoveTo(double x, double y, double z, double speed) {
            FlyingMoveControl.this.setWantedPosition(x, y, z, speed);
        }

        public boolean isUpdating() {
            return FlyingMoveControl.this.operation == Operation.MOVE_TO;
        }

        public double getSpeed() {
            return FlyingMoveControl.this.speedModifier;
        }

        public void onUpdateMoveHelper() {
            FlyingMoveControl.this.tick();
        }
    }
}
