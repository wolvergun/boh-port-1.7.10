package net.mcreator.boh.item;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.procedures.MassacreAxeLivingEntityIsHitWithToolProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.compat.M;

public class TheSlasherItem extends SwordItem {

    public TheSlasherItem() {
        super(new Tier() {

            public int getUses() {
                return 250;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 2.5F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 15;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[] { M.new_ItemStack(BohModItems.KILLERS_SOUL.get()) });
            }
        }, 3, -3.1F, new Properties());
    }

    public boolean hurtEnemy(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        MassacreAxeLivingEntityIsHitWithToolProcedure.execute(entity);
        return retval;
    }
}
