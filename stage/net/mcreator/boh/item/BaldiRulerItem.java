package net.mcreator.boh.item;

import net.mcreator.boh.procedures.BaldiRulerRightclickedProcedure;
import net.mcreator.boh.procedures.BaldiRulerToolInHandTickProcedure;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class BaldiRulerItem extends SwordItem {

    public BaldiRulerItem() {
        super(new Tier() {

            public int getUses() {
                return 2200;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 0.0F;
            }

            public int getLevel() {
                return 0;
            }

            public int getEnchantmentValue() {
                return 15;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -3.0F, new Properties());
    }

    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        BaldiRulerRightclickedProcedure.execute(world, M.getX(entity), M.getY(entity), M.getZ(entity), entity, (ItemStack) M.getObject(ar));
        return ar;
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            BaldiRulerToolInHandTickProcedure.execute(entity, itemstack);
        }
    }
}
