package net.mcreator.boh.compat.mc.world.item.alchemy;

import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;

/** 1.20 PotionUtils: brewing potions are stored as {"Potion": id} plus the effects 1.7.10 reads. */
public final class PotionUtils {

    private PotionUtils() {}

    public static ItemStack setPotion(ItemStack stack, BrewPotion potion) {
        if (stack == null) return null;
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) stack.setTagCompound(tag = new NBTTagCompound());
        if (potion == Potions.WATER) {
            tag.removeTag("Potion");
            tag.removeTag("CustomPotionEffects");
            return stack;
        }
        if (potion.getId() != null) tag.setString("Potion", potion.getId().toString());
        NBTTagList list = new NBTTagList();
        for (PotionEffect e : potion.getEffects()) list.appendTag(e.writeCustomPotionEffectToNBT(new NBTTagCompound()));
        tag.setTag("CustomPotionEffects", list);
        return stack;
    }

    public static BrewPotion getPotion(ItemStack stack) {
        if (stack == null || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("Potion")) return Potions.WATER;
        BrewPotion p = ForgeRegistries.POTIONS.getValue(new net.minecraft.util.ResourceLocation(stack.getTagCompound().getString("Potion")));
        return p == null ? Potions.WATER : p;
    }
}
