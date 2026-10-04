package net.mcreator.boh.compat.effect;

import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public final class ExtraEffects {
    public static Potion levitation;
    public static Potion slowFalling;
    public static Potion glowing;
    public static Potion darkness;
    public static Potion luck;
    public static Potion badOmen;
    public static Potion dolphinsGrace;
    public static Potion conduitPower;

    private ExtraEffects() {
    }

    public static synchronized void init() {
        if (levitation == null) {
            levitation = new ExtraEffects.Levitation();
            slowFalling = new ExtraEffects.SlowFalling();
            glowing = new ExtraEffects.Glowing();
            darkness = new ExtraEffects.Darkness();
            luck = new ExtraEffects.Luck();
            badOmen = new ExtraEffects.BadOmen();
            dolphinsGrace = new ExtraEffects.DolphinsGrace();
            conduitPower = new ExtraEffects.ConduitPower();
        }
    }

    public static final class BadOmen extends BohMobEffect {
        public BadOmen() {
            super(MobEffectCategory.NEUTRAL, 745784);
            this.setPotionName("effect.badOmen");
        }
    }

    public static final class ConduitPower extends BohMobEffect {
        public ConduitPower() {
            super(MobEffectCategory.BENEFICIAL, 1950417);
            this.setPotionName("effect.conduitPower");
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (e.isInWater()) {
                e.setAir(300);
            }
        }

        @Override
        public boolean isDurationEffectTick(int d, int a) {
            return true;
        }
    }

    public static final class Darkness extends BohMobEffect {
        public Darkness() {
            super(MobEffectCategory.HARMFUL, 2696993);
            this.setPotionName("effect.darkness");
        }
    }

    public static final class DolphinsGrace extends BohMobEffect {
        public DolphinsGrace() {
            super(MobEffectCategory.BENEFICIAL, 8954814);
            this.setPotionName("effect.dolphinsGrace");
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (e.isInWater()) {
                e.motionX *= 1.06;
                e.motionZ *= 1.06;
            }
        }

        @Override
        public boolean isDurationEffectTick(int d, int a) {
            return true;
        }
    }

    public static final class Glowing extends BohMobEffect {
        public Glowing() {
            super(MobEffectCategory.NEUTRAL, 9740385);
            this.setPotionName("effect.glowing");
        }
    }

    public static final class Levitation extends BohMobEffect {
        public Levitation() {
            super(MobEffectCategory.HARMFUL, 13565951);
            this.setPotionName("effect.levitation");
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (!(e instanceof EntityPlayer) || !((EntityPlayer)e).capabilities.isFlying) {
                e.motionY = e.motionY + (0.05 * (amp + 1) - e.motionY) * 0.2;
                e.fallDistance = 0.0F;
            }
        }

        @Override
        public boolean isDurationEffectTick(int d, int a) {
            return true;
        }
    }

    public static final class Luck extends BohMobEffect {
        public Luck() {
            super(MobEffectCategory.BENEFICIAL, 3381504);
            this.setPotionName("effect.luck");
        }
    }

    public static final class SlowFalling extends BohMobEffect {
        public SlowFalling() {
            super(MobEffectCategory.BENEFICIAL, 16711650);
            this.setPotionName("effect.slowFalling");
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (e.motionY < -0.07) {
                e.motionY = -0.07;
            }

            e.fallDistance = 0.0F;
        }

        @Override
        public boolean isDurationEffectTick(int d, int a) {
            return true;
        }
    }
}
