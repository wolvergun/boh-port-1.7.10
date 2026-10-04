package net.mcreator.boh.potion;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.minecraft.item.ItemStack;

public class SafeAndSoundMobEffect extends BohMobEffect {
    public SafeAndSoundMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -5278257);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> cures = new ArrayList<>();
        cures.add(M.new_ItemStack(Items.MILK_BUCKET));
        return cures;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
