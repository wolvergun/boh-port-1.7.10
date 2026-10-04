package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.mcreator.boh.procedures.MadOnEffectActiveTickProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class MadMobEffect extends BohMobEffect {
    public MadMobEffect() {
        super(MobEffectCategory.HARMFUL, -11448497);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    @Override
    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        MadOnEffectActiveTickProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
