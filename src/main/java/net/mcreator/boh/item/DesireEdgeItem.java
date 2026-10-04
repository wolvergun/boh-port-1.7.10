package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.procedures.DesireEdgeLivingEntityIsHitWithToolProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class DesireEdgeItem extends SwordItem {
    public DesireEdgeItem() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 2003;
            }

            @Override
            public float getSpeed() {
                return 4.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return 1.2F;
            }

            @Override
            public int getLevel() {
                return 1;
            }

            @Override
            public int getEnchantmentValue() {
                return 2;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -3.1F, new Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        DesireEdgeLivingEntityIsHitWithToolProcedure.execute(M.level(entity), M.getX(entity), M.getY(entity), M.getZ(entity), entity);
        return retval;
    }
}
