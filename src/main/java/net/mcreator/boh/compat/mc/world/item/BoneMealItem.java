package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

public final class BoneMealItem {
    private BoneMealItem() {
    }

    public static boolean growCrop(ItemStack stack, World w, BlockPos pos) {
        if (stack != null && stack.getItem() != null) {
            ItemStack s = stack.copy();
            return w instanceof WorldServer
                ? ItemDye.applyBonemeal(s, w, pos.getX(), pos.getY(), pos.getZ(), FakePlayerFactory.getMinecraft((WorldServer)w))
                : ItemDye.func_150919_a(s, w, pos.getX(), pos.getY(), pos.getZ());
        } else {
            return false;
        }
    }

    public static boolean growWaterPlant(ItemStack stack, World w, BlockPos pos, Object face) {
        return false;
    }

    public static void addGrowthParticles(Object world, BlockPos pos, int count) {
    }
}
