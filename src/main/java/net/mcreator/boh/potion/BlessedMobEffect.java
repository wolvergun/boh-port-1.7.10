package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.mcreator.boh.procedures.BlessedEffectStartedappliedProcedure;
import net.mcreator.boh.procedures.BlessedOnEffectActiveTickProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.ItemStack;

public class BlessedMobEffect extends BohMobEffect {
    public BlessedMobEffect() {
        super(MobEffectCategory.HARMFUL, -15791349);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    @Override
    public void addAttributeModifiers(EntityLivingBase entity, BaseAttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        BlessedEffectStartedappliedProcedure.execute(entity);
    }

    @Override
    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        BlessedOnEffectActiveTickProcedure.execute(entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
