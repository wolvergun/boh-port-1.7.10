package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.procedures.MassacreAxeLivingEntityIsHitWithToolProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class TheSlasherItem extends SwordItem {
    public TheSlasherItem() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 250;
            }

            @Override
            public float getSpeed() {
                return 4.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return 2.5F;
            }

            @Override
            public int getLevel() {
                return 1;
            }

            @Override
            public int getEnchantmentValue() {
                return 15;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(M.new_ItemStack(BohModItems.KILLERS_SOUL.get()));
            }
        }, 3, -3.1F, new Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        MassacreAxeLivingEntityIsHitWithToolProcedure.execute(entity);
        return retval;
    }
}
