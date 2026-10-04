package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.procedures.HiltOfABlacksmithToolInInventoryTickProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HiltOfABlacksmithItem extends SwordItem {
    public HiltOfABlacksmithItem() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 666;
            }

            @Override
            public float getSpeed() {
                return 7.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return 6.0F;
            }

            @Override
            public int getLevel() {
                return 3;
            }

            @Override
            public int getEnchantmentValue() {
                return 2;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(M.new_ItemStack(Blocks.OBSIDIAN));
            }
        }, 3, -3.0F, new Properties());
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        HiltOfABlacksmithToolInInventoryTickProcedure.execute(entity);
    }
}
