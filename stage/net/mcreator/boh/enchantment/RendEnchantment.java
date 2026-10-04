package net.mcreator.boh.enchantment;

import java.util.List;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.minecraft.enchantment.Enchantment;
import net.mcreator.boh.compat.mc.world.item.enchantment.EnchantmentCategory;
import net.mcreator.boh.compat.mc.world.item.enchantment.Enchantments;
import net.mcreator.boh.compat.mc.world.item.enchantment.Rarity;
import net.mcreator.boh.compat.item.BohEnchantment;
import net.mcreator.boh.compat.M;

public class RendEnchantment extends BohEnchantment {

    private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("boh_rend", item -> Ingredient.of(new ItemStack[] { M.new_ItemStack(BohModItems.KILLER_KNIFE.get()), M.new_ItemStack(BohModItems.THE_GREAT_KNIFE.get()), M.new_ItemStack(BohModItems.SCHIZOSLEDGE.get()), M.new_ItemStack(BohModItems.THE_SLASHER.get()), M.new_ItemStack(BohModItems.MASSACRE_AXE.get()), M.new_ItemStack(BohModItems.DESIRE_EDGE.get()), M.new_ItemStack(BohModItems.TACTICAL_KNIFE.get()), M.new_ItemStack(BohModItems.PAINTED_SWORD.get()), M.new_ItemStack(BohModItems.MACHETE.get()), M.new_ItemStack(BohModItems.GIANT_SCISSOR.get()), M.new_ItemStack(BohModItems.SOUL_STEALER.get()), M.new_ItemStack(BohModItems.HILT_OF_A_BLACKSMITH.get()), M.new_ItemStack(BohModItems.ZACKS_SCYTHE.get()), M.new_ItemStack(Items.WOODEN_SWORD), M.new_ItemStack(Items.STONE_SWORD), M.new_ItemStack(Items.IRON_SWORD), M.new_ItemStack(Items.GOLDEN_SWORD), M.new_ItemStack(Items.DIAMOND_SWORD), M.new_ItemStack(Items.NETHERITE_SWORD) }).test(M.new_ItemStack(item)));

    public RendEnchantment() {
        super(Rarity.COMMON, ENCHANTMENT_CATEGORY, EquipmentSlot.values());
    }

    public int getMinCost(int level) {
        return 1 + level * 10;
    }

    public int getMaxCost(int level) {
        return 6 + level * 10;
    }

    protected boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && !M.contains(List.of(Enchantments.SHARPNESS, Enchantments.BANE_OF_ARTHROPODS, Enchantments.SWEEPING_EDGE), enchantment);
    }

    public boolean isTreasureOnly() {
        return true;
    }
}
