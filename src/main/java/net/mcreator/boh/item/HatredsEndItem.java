package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.procedures.HatredsEndToolInHandTickProcedure;
import net.mcreator.boh.procedures.KillerKnifeLivingEntityIsHitWithToolProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HatredsEndItem extends SwordItem {
    public HatredsEndItem() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 999;
            }

            @Override
            public float getSpeed() {
                return 6.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return 4.0F;
            }

            @Override
            public int getLevel() {
                return 1;
            }

            @Override
            public int getEnchantmentValue() {
                return 0;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(M.new_ItemStack(BohModItems.KILLERS_SOUL.get()));
            }
        }, 3, -1.5F, new Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        KillerKnifeLivingEntityIsHitWithToolProcedure.execute(M.level(entity), entity, sourceentity);
        return retval;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            HatredsEndToolInHandTickProcedure.execute(entity);
        }
    }
}
