package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.context.UseOnContext;
import net.mcreator.boh.procedures.WhitefaceheartItemInInventoryTickProcedure;
import net.mcreator.boh.procedures.WhitefaceheartRightclickedOnBlockProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WhitefaceheartItem extends BohItem {
    public WhitefaceheartItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        WhitefaceheartRightclickedOnBlockProcedure.execute(world, M.getX(entity), M.getY(entity), M.getZ(entity), entity);
        return ar;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        WhitefaceheartRightclickedOnBlockProcedure.execute(
            M.getLevel(context), M.getX(M.getClickedPos(context)), M.getY(M.getClickedPos(context)), M.getZ(M.getClickedPos(context)), M.getPlayer(context)
        );
        return InteractionResult.SUCCESS;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        WhitefaceheartItemInInventoryTickProcedure.execute(entity);
    }
}
