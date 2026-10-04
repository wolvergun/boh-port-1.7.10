package net.mcreator.boh.item;

import net.mcreator.boh.procedures.CameraObscuraRightclickedProcedure;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.minecraft.world.World;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.M;

public class CameraObscuraItem extends BohItem {

    public CameraObscuraItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        CameraObscuraRightclickedProcedure.execute(world, M.getX(entity), M.getY(entity), M.getZ(entity), entity, (ItemStack) M.getObject(ar));
        return ar;
    }
}
