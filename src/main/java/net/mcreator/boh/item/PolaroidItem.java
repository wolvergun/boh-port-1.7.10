package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.procedures.PolaroidRightclickedProcedure;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolaroidItem extends BohItem {
    public PolaroidItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        PolaroidRightclickedProcedure.execute(world, M.getX(entity), M.getY(entity), M.getZ(entity), entity);
        return ar;
    }
}
