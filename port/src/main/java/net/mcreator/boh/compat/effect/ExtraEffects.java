package net.mcreator.boh.compat.effect;

import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

/** 1.20 effects that 1.7.10 lacks, implemented with their 1.20 behaviour. */
public final class ExtraEffects {

    private ExtraEffects() {}

    /** Floats upward 0.05 * (amplifier+1) blocks/tick like 1.20 levitation. */
    public static final class Levitation extends BohMobEffect {

        public Levitation() {
            super(MobEffectCategory.HARMFUL, 0xCEFFFF);
            setPotionName("effect.levitation");
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (e instanceof EntityPlayer && ((EntityPlayer) e).capabilities.isFlying) return;
            e.motionY += (0.05 * (amp + 1) - e.motionY) * 0.2;
            e.fallDistance = 0;
        }

        @Override
        public boolean isDurationEffectTick(int d, int a) {
            return true;
        }
    }

    /** Caps falling speed and cancels fall damage like 1.20 slow falling. */
    public static final class SlowFalling extends BohMobEffect {

        public SlowFalling() {
            super(MobEffectCategory.BENEFICIAL, 0xFEFFE2);
            setPotionName("effect.slowFalling");
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (e.motionY < -0.07) e.motionY = -0.07;
            e.fallDistance = 0;
        }

        @Override
        public boolean isDurationEffectTick(int d, int a) {
            return true;
        }
    }

    /** Outlined through walls on the client (see the glowing render hook). */
    public static final class Glowing extends BohMobEffect {

        public Glowing() {
            super(MobEffectCategory.NEUTRAL, 0x94A061);
            setPotionName("effect.glowing");
        }
    }

    /** Pulsing darkness: rendered by the client fog hook, otherwise like a weak blindness. */
    public static final class Darkness extends BohMobEffect {

        public Darkness() {
            super(MobEffectCategory.HARMFUL, 0x292721);
            setPotionName("effect.darkness");
        }
    }

    public static final class Luck extends BohMobEffect {

        public Luck() {
            super(MobEffectCategory.BENEFICIAL, 0x339900);
            setPotionName("effect.luck");
        }
    }

    public static final class BadOmen extends BohMobEffect {

        public BadOmen() {
            super(MobEffectCategory.NEUTRAL, 0x0B6138);
            setPotionName("effect.badOmen");
        }
    }

    /** Faster swimming like dolphin's grace. */
    public static final class DolphinsGrace extends BohMobEffect {

        public DolphinsGrace() {
            super(MobEffectCategory.BENEFICIAL, 0x88A3BE);
            setPotionName("effect.dolphinsGrace");
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

    /** Water breathing + night vision + haste underwater, like conduit power. */
    public static final class ConduitPower extends BohMobEffect {

        public ConduitPower() {
            super(MobEffectCategory.BENEFICIAL, 0x1DC2D1);
            setPotionName("effect.conduitPower");
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (e.isInWater()) e.setAir(300);
        }

        @Override
        public boolean isDurationEffectTick(int d, int a) {
            return true;
        }
    }

    public static Potion levitation, slowFalling, glowing, darkness, luck, badOmen, dolphinsGrace, conduitPower;

    /** Creates the extra effects once (called during preInit). */
    public static synchronized void init() {
        if (levitation != null) return;
        levitation = new Levitation();
        slowFalling = new SlowFalling();
        glowing = new Glowing();
        darkness = new Darkness();
        luck = new Luck();
        badOmen = new BadOmen();
        dolphinsGrace = new DolphinsGrace();
        conduitPower = new ConduitPower();
    }
}
