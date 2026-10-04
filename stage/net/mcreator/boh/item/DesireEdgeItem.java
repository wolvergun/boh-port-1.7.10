package net.mcreator.boh.item;

import net.mcreator.boh.procedures.DesireEdgeLivingEntityIsHitWithToolProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.compat.M;

public class DesireEdgeItem extends SwordItem {

    public DesireEdgeItem() {
        super(new Tier() {

            public int getUses() {
                return 2003;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 1.2F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -3.1F, new Properties());
    }

    public boolean hurtEnemy(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        DesireEdgeLivingEntityIsHitWithToolProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity);
        return retval;
    }
}
