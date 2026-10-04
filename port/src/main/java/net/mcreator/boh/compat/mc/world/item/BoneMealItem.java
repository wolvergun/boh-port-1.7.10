package net.mcreator.boh.compat.mc.world.item;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

/** 1.20 BoneMealItem static helpers. */
public final class BoneMealItem {

    private BoneMealItem() {}

    public static boolean growCrop(ItemStack stack, World w, BlockPos pos) {
        if (stack == null || stack.getItem() == null) return false;
        ItemStack s = stack.copy();
        if (w instanceof WorldServer)
            return ItemDye.applyBonemeal(s, w, pos.getX(), pos.getY(), pos.getZ(), FakePlayerFactory.getMinecraft((WorldServer) w));
        return ItemDye.func_150919_a(s, w, pos.getX(), pos.getY(), pos.getZ());
    }

    public static boolean growWaterPlant(ItemStack stack, World w, BlockPos pos, Object face) {
        return false;
    }

    public static void addGrowthParticles(Object world, BlockPos pos, int count) {}
}
