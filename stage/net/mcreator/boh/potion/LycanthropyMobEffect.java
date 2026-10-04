package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.procedures.LycanthropyOnEffectActiveTickProcedure;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.M;

public class LycanthropyMobEffect extends BohMobEffect {

    public LycanthropyMobEffect() {
        super(MobEffectCategory.NEUTRAL, -9285832);
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        LycanthropyOnEffectActiveTickProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
