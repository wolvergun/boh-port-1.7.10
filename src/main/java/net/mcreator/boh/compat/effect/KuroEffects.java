package net.mcreator.boh.compat.effect;

import java.util.UUID;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public final class KuroEffects {
    public static Potion bleeding;
    public static Potion radiation;
    public static Potion aggression;
    public static Potion paranoia;
    public static Potion sleep;
    public static Potion flashbanged;
    private static final DamageSource BLEED = new DamageSource("boh.bleeding").setDamageBypassesArmor();
    private static final DamageSource RADIATION = new DamageSource("boh.radiation").setDamageBypassesArmor();

    private KuroEffects() {
    }

    public static void init() {
        bleeding = reg("bleeding", new KuroEffects.Bleeding());
        radiation = reg("radiation", new KuroEffects.Radiation());
        aggression = reg("aggression", new KuroEffects.Aggression());
        paranoia = reg("paranoia", new KuroEffects.Paranoia());
        sleep = reg("sleep", new KuroEffects.Sleep());
        flashbanged = reg("flashbanged", new KuroEffects.Flashbanged());
    }

    private static Potion reg(String name, BohMobEffect p) {
        ForgeRegistries.MOB_EFFECTS.register(new ResourceLocation("kurolib", name), p);
        p.setPotionName("effect.kurolib." + name);
        p.setIconTexture(null);
        return p;
    }

    public static final class Aggression extends BohMobEffect {
        private static final UUID ID = UUID.fromString("5b6f0e57-0b8b-4b5f-9f1c-2f0d7a1a6b11");

        Aggression() {
            super(MobEffectCategory.BENEFICIAL, 11538448);
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amp) {
            return true;
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            IAttributeInstance a = e.getEntityAttribute(SharedMonsterAttributes.attackDamage);
            if (a != null) {
                AttributeModifier cur = a.getModifier(ID);
                if (cur == null || cur.getAmount() != 2.0 * (amp + 1)) {
                    if (cur != null) {
                        a.removeModifier(cur);
                    }

                    a.applyModifier(new AttributeModifier(ID, "kurolib aggression", 2.0 * (amp + 1), 0).setSaved(false));
                }
            }
        }

        @Override
        public void removeAttributesModifiersFromEntity(EntityLivingBase e, BaseAttributeMap map, int amp) {
            IAttributeInstance a = e.getEntityAttribute(SharedMonsterAttributes.attackDamage);
            if (a != null && a.getModifier(ID) != null) {
                a.removeModifier(a.getModifier(ID));
            }

            super.removeAttributesModifiersFromEntity(e, map, amp);
        }
    }

    public static final class Bleeding extends BohMobEffect {
        Bleeding() {
            super(MobEffectCategory.HARMFUL, 9044739);
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amp) {
            int interval = Math.max(5, 30 - amp * 3);
            return duration % interval == 0;
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (!e.worldObj.isRemote) {
                e.attackEntityFrom(KuroEffects.BLEED, 1.0F);
            } else {
                for (int i = 0; i < 4; i++) {
                    e.worldObj
                        .spawnParticle(
                            "reddust",
                            e.posX + (e.getRNG().nextDouble() - 0.5) * e.width,
                            e.boundingBox.minY + e.getRNG().nextDouble() * e.height,
                            e.posZ + (e.getRNG().nextDouble() - 0.5) * e.width,
                            0.6,
                            0.0,
                            0.0
                        );
                }
            }
        }
    }

    public static final class Flashbanged extends BohMobEffect {
        Flashbanged() {
            super(MobEffectCategory.HARMFUL, 16777215);
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amp) {
            return duration % 20 == 0;
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (!e.worldObj.isRemote) {
                e.addPotionEffect(new PotionEffect(Potion.confusion.id, 40, 0, true));
            }
        }
    }

    public static final class Paranoia extends BohMobEffect {
        private static final String[] SOUNDS = new String[]{
            "ambient.cave.cave", "mob.zombie.say", "mob.endermen.stare", "random.door_open", "step.gravel", "mob.ghast.moan"
        };

        Paranoia() {
            super(MobEffectCategory.HARMFUL, 3811914);
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amp) {
            return true;
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (e.worldObj.isRemote && e.getRNG().nextInt(Math.max(40, 200 - amp * 40)) == 0) {
                double a = e.getRNG().nextDouble() * Math.PI * 2.0;
                double r = 4.0 + e.getRNG().nextDouble() * 6.0;
                e.worldObj
                    .playSound(
                        e.posX + Math.cos(a) * r,
                        e.posY,
                        e.posZ + Math.sin(a) * r,
                        SOUNDS[e.getRNG().nextInt(SOUNDS.length)],
                        0.8F,
                        0.8F + e.getRNG().nextFloat() * 0.3F,
                        false
                    );
            }
        }
    }

    public static final class Radiation extends BohMobEffect {
        Radiation() {
            super(MobEffectCategory.HARMFUL, 6029099);
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amp) {
            return duration % Math.max(10, 40 - amp * 8) == 0;
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (!e.worldObj.isRemote) {
                e.attackEntityFrom(KuroEffects.RADIATION, 1.0F);
                if (e instanceof EntityPlayer) {
                    ((EntityPlayer)e).addExhaustion(0.5F * (amp + 1));
                }
            }
        }
    }

    public static final class Sleep extends BohMobEffect {
        Sleep() {
            super(MobEffectCategory.HARMFUL, 1776442);
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amp) {
            return duration % 20 == 0;
        }

        @Override
        public void applyEffectTick(EntityLivingBase e, int amp) {
            if (!e.worldObj.isRemote) {
                e.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 30, 3, true));
                e.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 30, 2, true));
            }
        }
    }
}
