package net.mcreator.boh.item;

import java.util.List;
import net.mcreator.boh.procedures.TeleportToBackroomsProcedure;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.TooltipFlag;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.minecraft.world.World;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.M;

public class BrokenCameraItem extends BohItem {

    public BrokenCameraItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    public int getUseDuration(ItemStack itemstack) {
        return 20;
    }

    public void appendHoverText(ItemStack itemstack, World level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);
        list.add(Component.translatable("item.boh.broken_camera.description_0"));
    }

    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        M.startUsingItem(entity, hand);
        TeleportToBackroomsProcedure.execute(entity, (ItemStack) M.getObject(ar));
        return ar;
    }
}
