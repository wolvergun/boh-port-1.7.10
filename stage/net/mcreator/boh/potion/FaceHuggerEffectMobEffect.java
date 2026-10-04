package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.procedures.FaceHuggerEffectEffectExpiresProcedure;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.M;

public class FaceHuggerEffectMobEffect extends BohMobEffect {

    public FaceHuggerEffectMobEffect() {
        super(MobEffectCategory.HARMFUL, -8371389);
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public void removeAttributeModifiers(EntityLivingBase entity, BaseAttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        FaceHuggerEffectEffectExpiresProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
