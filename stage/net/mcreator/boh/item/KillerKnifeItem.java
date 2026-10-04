package net.mcreator.boh.item;

import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.procedures.KillerKnifeLivingEntityIsHitWithToolProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.compat.M;

public class KillerKnifeItem extends SwordItem {

    public KillerKnifeItem() {
        super(new Tier() {

            public int getUses() {
                return 666;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return -1.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 0;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[] { M.new_ItemStack(BohModItems.KILLERS_SOUL.get()) });
            }
        }, 3, -1.5F, new Properties());
    }

    public boolean hurtEnemy(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        KillerKnifeLivingEntityIsHitWithToolProcedure.execute(M.level(entity), entity, sourceentity);
        return retval;
    }
}
