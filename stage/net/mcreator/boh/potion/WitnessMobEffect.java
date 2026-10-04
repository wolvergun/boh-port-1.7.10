package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.effect.BohMobEffect;

public class WitnessMobEffect extends BohMobEffect {

    public WitnessMobEffect() {
        super(MobEffectCategory.HARMFUL, -4587520);
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
