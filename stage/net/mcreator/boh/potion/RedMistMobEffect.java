package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.effect.BohMobEffect;

public class RedMistMobEffect extends BohMobEffect {

    public RedMistMobEffect() {
        super(MobEffectCategory.HARMFUL, -12369085);
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
