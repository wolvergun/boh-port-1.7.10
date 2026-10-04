package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.procedures.VampirismOnEffectActiveTickProcedure;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.M;

public class VampirismMobEffect extends BohMobEffect {

    public VampirismMobEffect() {
        super(MobEffectCategory.NEUTRAL, -9895165);
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public void applyEffectTick(EntityLivingBase entity, int amplifier) {
        VampirismOnEffectActiveTickProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
