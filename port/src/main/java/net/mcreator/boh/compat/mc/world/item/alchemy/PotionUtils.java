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
        // a meta-0 potion is always named "Water Bottle"; 8192/16384 let ItemPotion name it after its first effect
        if (stack.getItem() == net.minecraft.init.Items.potionitem && (stack.getItemDamage() & 16383) == 0)
            stack.setItemDamage((stack.getItemDamage() & 16384) != 0 ? 16384 : 8192);
        if (potion.getId() != null) tag.setString("Potion", potion.getId().toString());
        NBTTagList list = new NBTTagList();
        for (PotionEffect e : potion.getEffects()) list.appendTag(e.writeCustomPotionEffectToNBT(new NBTTagCompound()));
        tag.setTag("CustomPotionEffects", list);
        return stack;
    }

    public static BrewPotion getPotion(ItemStack stack) {
        if (stack == null) return Potions.WATER;
        if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Potion")) return legacyPotion(stack);
        BrewPotion p = ForgeRegistries.POTIONS.getValue(new net.minecraft.util.ResourceLocation(stack.getTagCompound().getString("Potion")));
        return p == null ? Potions.WATER : p;
    }

    /** 1.7.10 vanilla potions keep their brew in the metadata: 0 water, 16 awkward, 32 thick, effectless others mundane. */
    private static BrewPotion legacyPotion(ItemStack stack) {
        if (stack.getItem() != net.minecraft.init.Items.potionitem) return Potions.WATER;
        int base = stack.getItemDamage() & 16383;
        if (base == 0) return Potions.WATER;
        if (base == 16) return Potions.AWKWARD;
        if (base == 32) return Potions.THICK;
        java.util.List<?> effects = net.minecraft.init.Items.potionitem.getEffects(stack);
        return effects == null || effects.isEmpty() ? Potions.MUNDANE : Potions.VANILLA_EFFECT;
    }
}
