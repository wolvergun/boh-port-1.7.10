package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.procedures.BaldiRulerRightclickedProcedure;
import net.mcreator.boh.procedures.BaldiRulerToolInHandTickProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BaldiRulerItem extends SwordItem {
    public BaldiRulerItem() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 2200;
            }

            @Override
            public float getSpeed() {
                return 4.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return 0.0F;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return 15;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -3.0F, new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        BaldiRulerRightclickedProcedure.execute(world, M.getX(entity), M.getY(entity), M.getZ(entity), entity, M.getObject(ar));
        return ar;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            BaldiRulerToolInHandTickProcedure.execute(entity, itemstack);
        }
    }
}
