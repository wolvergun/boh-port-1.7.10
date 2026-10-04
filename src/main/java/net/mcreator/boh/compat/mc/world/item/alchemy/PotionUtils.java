package net.mcreator.boh.compat.mc.world.item.alchemy;

import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public final class PotionUtils {
    private PotionUtils() {
    }

    public static ItemStack setPotion(ItemStack stack, BrewPotion potion) {
        if (stack == null) {
            return null;
        } else {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null) {
                stack.setTagCompound(tag = new NBTTagCompound());
            }

            if (potion == Potions.WATER) {
                tag.removeTag("Potion");
                tag.removeTag("CustomPotionEffects");
                return stack;
            } else {
                if (potion.getId() != null) {
                    tag.setString("Potion", potion.getId().toString());
                }

                NBTTagList list = new NBTTagList();

                for (PotionEffect e : potion.getEffects()) {
                    list.appendTag(e.writeCustomPotionEffectToNBT(new NBTTagCompound()));
                }

                tag.setTag("CustomPotionEffects", list);
                return stack;
            }
        }
    }

    public static BrewPotion getPotion(ItemStack stack) {
        if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("Potion")) {
            BrewPotion p = ForgeRegistries.POTIONS.getValue(new ResourceLocation(stack.getTagCompound().getString("Potion")));
            return p == null ? Potions.WATER : p;
        } else {
            return Potions.WATER;
        }
    }
}
