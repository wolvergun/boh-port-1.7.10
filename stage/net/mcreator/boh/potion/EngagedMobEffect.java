package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.procedures.EngagedEffectExpiresProcedure;
import net.mcreator.boh.procedures.EngagedEffectStartedappliedProcedure;
import net.mcreator.boh.procedures.EngagedOnEffectActiveTickProcedure;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.M;

public class EngagedMobEffect extends BohMobEffect {

    public EngagedMobEffect() {
        super(MobEffectCategory.HARMFUL, -16777216);
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public void addAttributeModifiers(EntityLivingBase entity, BaseAttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        EngagedEffectStartedappliedProcedure.execute(entity);
    }

    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        EngagedOnEffectActiveTickProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity);
    }

    public void removeAttributeModifiers(EntityLivingBase entity, BaseAttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        EngagedEffectExpiresProcedure.execute(entity);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
