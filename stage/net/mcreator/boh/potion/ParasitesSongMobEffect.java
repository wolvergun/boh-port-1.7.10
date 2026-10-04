package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.effect.BohMobEffect;

public class ParasitesSongMobEffect extends BohMobEffect {

    public ParasitesSongMobEffect() {
        super(MobEffectCategory.HARMFUL, -65536);
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
