package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.mcreator.boh.procedures.VampirismOnEffectActiveTickProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class VampirismMobEffect extends BohMobEffect {
    public VampirismMobEffect() {
        super(MobEffectCategory.NEUTRAL, -9895165);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    @Override
    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        VampirismOnEffectActiveTickProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
