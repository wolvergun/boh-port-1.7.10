package net.mcreator.boh.item;

import java.util.List;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.TooltipFlag;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.procedures.MassacreAxeLivingEntityIsHitWithToolProcedure;
import net.mcreator.boh.procedures.TacticalKnifeRightclickedProcedure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TacticalKnifeItem extends SwordItem {
    public TacticalKnifeItem() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 750;
            }

            @Override
            public float getSpeed() {
                return 4.0F;
            }

            @Override
            public float getAttackDamageBonus() {
                return 1.0F;
            }

            @Override
            public int getLevel() {
                return 1;
            }

            @Override
            public int getEnchantmentValue() {
                return 5;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -2.9F, new Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        MassacreAxeLivingEntityIsHitWithToolProcedure.execute(entity);
        return retval;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        TacticalKnifeRightclickedProcedure.execute(world, M.getX(entity), M.getY(entity), M.getZ(entity), entity, M.getObject(ar));
        return ar;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, World level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);
        list.add(Component.translatable("item.boh.tactical_knife.description_0"));
    }
}
