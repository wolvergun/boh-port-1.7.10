package net.mcreator.boh.item;

import java.util.List;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.TooltipFlag;
import net.mcreator.boh.procedures.TeleportToBoilerProcedure;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SingedDreamCatcherItem extends BohItem {
    public SingedDreamCatcherItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 20;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, World level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);
        list.add(Component.translatable("item.boh.singed_dream_catcher.description_0"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(World world, EntityPlayer entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        M.startUsingItem(entity, hand);
        TeleportToBoilerProcedure.execute(entity, M.getObject(ar));
        return ar;
    }
}
