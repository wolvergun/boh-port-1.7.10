package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.mcreator.boh.procedures.IntoTheFogEffectStartedappliedProcedure;
import net.mcreator.boh.procedures.IntoTheFogOnEffectActiveTickProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.ItemStack;

public class IntoTheFogMobEffect extends BohMobEffect {
    public IntoTheFogMobEffect() {
        super(MobEffectCategory.HARMFUL, -12369085);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    @Override
    public void addAttributeModifiers(EntityLivingBase entity, BaseAttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        IntoTheFogEffectStartedappliedProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity));
    }

    @Override
    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        IntoTheFogOnEffectActiveTickProcedure.execute(M.level(entity), entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
