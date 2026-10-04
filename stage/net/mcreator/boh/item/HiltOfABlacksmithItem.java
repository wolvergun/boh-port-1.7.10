package net.mcreator.boh.item;

import net.mcreator.boh.procedures.HiltOfABlacksmithToolInInventoryTickProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.M;

public class HiltOfABlacksmithItem extends SwordItem {

    public HiltOfABlacksmithItem() {
        super(new Tier() {

            public int getUses() {
                return 666;
            }

            public float getSpeed() {
                return 7.0F;
            }

            public float getAttackDamageBonus() {
                return 6.0F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[] { M.new_ItemStack(Blocks.OBSIDIAN) });
            }
        }, 3, -3.0F, new Properties());
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        HiltOfABlacksmithToolInInventoryTickProcedure.execute(entity);
    }
}
