package net.mcreator.boh.compat;

import java.util.Random;
import java.util.function.Consumer;
import net.mcreator.boh.compat.forge.registries.RegistryObject;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.mcreator.boh.compat.mc.world.food.Builder;
import net.mcreator.boh.compat.mc.world.food.FoodProperties;
import net.mcreator.boh.compat.registry.LegacyIds;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MItem extends MCore {
    public static final ItemStack EMPTY = new ItemStack((Item)null, 0, 0);

    protected MItem() {
    }

    public static ItemStack stack(ItemStack s) {
        return s == null ? EMPTY : s;
    }

    public static ItemStack legacy(ItemStack s) {
        return s != null && s != EMPTY && s.getItem() != null && s.stackSize > 0 ? s : null;
    }

    public static ItemStack new_ItemStack(Item item) {
        return item == null ? EMPTY : new ItemStack(item);
    }

    public static ItemStack new_ItemStack(Item item, int count) {
        return item == null ? EMPTY : new ItemStack(item, count);
    }

    public static ItemStack new_ItemStack(Block block) {
        Item i = block == null ? null : Item.getItemFromBlock(block);
        return i == null ? EMPTY : new ItemStack(i);
    }

    public static ItemStack new_ItemStack(Block block, int count) {
        Item i = block == null ? null : Item.getItemFromBlock(block);
        return i == null ? EMPTY : new ItemStack(i, count);
    }

    public static ItemStack new_ItemStack(Object itemLike) {
        return new_ItemStack(itemLike, 1);
    }

    public static ItemStack new_ItemStack(Object itemLike, int count) {
        if (itemLike instanceof Item) {
            return new_ItemStack((Item)itemLike, count);
        } else if (itemLike instanceof Block) {
            return new_ItemStack((Block)itemLike, count);
        } else if (itemLike instanceof RegistryObject) {
            return new_ItemStack(((RegistryObject)itemLike).get(), count);
        } else {
            return itemLike instanceof ItemStack ? ((ItemStack)itemLike).copy() : EMPTY;
        }
    }

    public static ItemStack new_ItemStack(Item item, int count, NBTTagCompound tag) {
        ItemStack s = new_ItemStack(item, count);
        if (s != EMPTY) {
            s.setTagCompound(tag);
        }

        return s;
    }

    public static Item asItem(Item item) {
        return item;
    }

    public static Item asItem(Block block) {
        return Item.getItemFromBlock(block);
    }

    public static Item getItem(ItemStack s) {
        return s == null ? null : s.getItem();
    }

    public static boolean isEmpty(ItemStack s) {
        return legacy(s) == null;
    }

    public static ItemStack copy(ItemStack s) {
        ItemStack l = legacy(s);
        return l == null ? EMPTY : l.copy();
    }

    public static int getCount(ItemStack s) {
        return legacy(s) == null ? 0 : s.stackSize;
    }

    public static void setCount(ItemStack s, int n) {
        if (s != null && s != EMPTY) {
            s.stackSize = n;
        }
    }

    public static void shrink(ItemStack s, int n) {
        if (s != null && s != EMPTY) {
            s.stackSize -= n;
        }
    }

    public static void grow(ItemStack s, int n) {
        if (s != null && s != EMPTY) {
            s.stackSize += n;
        }
    }

    public static ItemStack split(ItemStack s, int n) {
        return legacy(s) == null ? EMPTY : s.splitStack(Math.min(n, s.stackSize));
    }

    public static int getMaxStackSize(ItemStack s) {
        return legacy(s) == null ? 64 : s.getMaxStackSize();
    }

    public static boolean isStackable(ItemStack s) {
        return legacy(s) != null && s.isStackable();
    }

    public static boolean is(ItemStack s, Item item) {
        return s != null && s.getItem() == item;
    }

    public static boolean is(ItemStack s, TagKey<?> tag) {
        Item i = getItem(s);
        return i != null && tag.contains(LegacyIds.itemKey(i));
    }

    public static boolean is(Item a, Item b) {
        return a == b;
    }

    public static NBTTagCompound getOrCreateTag(ItemStack s) {
        if (s != null && s != EMPTY) {
            if (s.getTagCompound() == null) {
                s.setTagCompound(new NBTTagCompound());
            }

            return s.getTagCompound();
        } else {
            return new NBTTagCompound();
        }
    }

    public static NBTTagCompound getTag(ItemStack s) {
        return s == null ? null : s.getTagCompound();
    }

    public static boolean hasTag(ItemStack s) {
        return s != null && s.hasTagCompound();
    }

    public static void setTag(ItemStack s, NBTTagCompound tag) {
        if (s != null && s != EMPTY) {
            s.setTagCompound(tag);
        }
    }

    public static int getDamageValue(ItemStack s) {
        return legacy(s) == null ? 0 : s.getItemDamage();
    }

    public static void setDamageValue(ItemStack s, int dmg) {
        if (legacy(s) != null) {
            s.setItemDamage(dmg);
        }
    }

    public static int getMaxDamage(ItemStack s) {
        return legacy(s) == null ? 0 : s.getMaxDamage();
    }

    public static boolean isDamageableItem(ItemStack s) {
        return legacy(s) != null && s.isItemStackDamageable();
    }

    public static boolean isDamaged(ItemStack s) {
        return legacy(s) != null && s.isItemDamaged();
    }

    public static boolean hurt(ItemStack s, int amount, Random random, EntityPlayerMP player) {
        return legacy(s) != null && s.attemptDamageItem(amount, random);
    }

    public static boolean hurt(ItemStack s, int amount, Random random, Object player) {
        return legacy(s) != null && s.attemptDamageItem(amount, random);
    }

    public static <T extends EntityLivingBase> void hurtAndBreak(ItemStack s, int amount, T entity, Consumer<T> onBreak) {
        if (legacy(s) != null) {
            if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).capabilities.isCreativeMode) {
                s.damageItem(amount, entity);
                if (s.stackSize <= 0 && onBreak != null) {
                    onBreak.accept(entity);
                }
            }
        }
    }

    public static Component getDisplayName(ItemStack s) {
        return Component.literal(legacy(s) == null ? "Air" : s.getDisplayName());
    }

    public static Component getHoverName(ItemStack s) {
        return getDisplayName(s);
    }

    public static ItemStack setHoverName(ItemStack s, Component name) {
        if (legacy(s) != null) {
            s.setStackDisplayName(name.getString());
        }

        return s;
    }

    public static String getDescriptionId(ItemStack s) {
        return legacy(s) == null ? "block.minecraft.air" : s.getUnlocalizedName();
    }

    public static String getDescriptionId(Item i) {
        return i.getUnlocalizedName();
    }

    public static void enchant(ItemStack s, Enchantment e, int level) {
        if (legacy(s) != null) {
            s.addEnchantment(e, level);
        }
    }

    public static boolean isEnchanted(ItemStack s) {
        return legacy(s) != null && s.isItemEnchanted();
    }

    public static int getEnchantmentLevel(ItemStack s, Enchantment e) {
        return legacy(s) == null ? 0 : EnchantmentHelper.getEnchantmentLevel(e.effectId, s);
    }

    public static boolean isSameItemSameTags(ItemStack a, ItemStack b) {
        ItemStack la = legacy(a);
        ItemStack lb = legacy(b);
        return la != null && lb != null
            ? la.getItem() == lb.getItem() && la.getItemDamage() == lb.getItemDamage() && ItemStack.areItemStackTagsEqual(la, lb)
            : la == lb;
    }

    public static boolean isSameItem(ItemStack a, ItemStack b) {
        ItemStack la = legacy(a);
        ItemStack lb = legacy(b);
        return la != null && lb != null ? la.getItem() == lb.getItem() : la == lb;
    }

    public static boolean stacksMatch(ItemStack a, ItemStack b) {
        ItemStack la = legacy(a);
        ItemStack lb = legacy(b);
        return la != null && lb != null ? ItemStack.areItemStacksEqual(la, lb) : la == lb;
    }

    public static FoodProperties getFoodProperties(Item item) {
        if (item instanceof BohItem) {
            return ((BohItem)item).food();
        } else {
            return item instanceof ItemFood f ? new Builder().nutrition(f.func_150905_g(null)).saturationMod(f.func_150906_h(null)).build() : null;
        }
    }

    public static FoodProperties getFoodProperties(ItemStack s) {
        return getFoodProperties(getItem(s));
    }

    public static boolean isEdible(Item item) {
        return getFoodProperties(item) != null;
    }

    public static boolean isEdible(ItemStack s) {
        return isEdible(getItem(s));
    }

    public static int getNutrition(FoodProperties f) {
        return f == null ? 0 : f.getNutrition();
    }

    public static float getSaturationModifier(FoodProperties f) {
        return f == null ? 0.0F : f.getSaturationModifier();
    }
}
