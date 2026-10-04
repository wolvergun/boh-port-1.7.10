package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.item.BoneMealItem;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.M;

public class PumpkinPlayerOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z) {
        boolean YourCondition = false;
        if (Blocks.FARMLAND == M.getBlock(M.getBlockState(world, BlockPos.containing(x, 1.0 - y, z))) && Math.random() < 0.7 && world instanceof World _level) {
            BlockPos _bp = BlockPos.containing(x, y, z);
            if ((BoneMealItem.growCrop(M.new_ItemStack(Items.BONE_MEAL), _level, _bp) || BoneMealItem.growWaterPlant(M.new_ItemStack(Items.BONE_MEAL), _level, _bp, null)) && !M.isClientSide(_level)) {
                M.levelEvent(_level, 2005, _bp, 0);
            }
        }
        if (M.isEmptyBlock(world, BlockPos.containing(x, 1.0 + y, z))) {
            M.setBlock(world, BlockPos.containing(x, 1.0 + y, z), M.defaultBlockState(((Block) BohModBlocks.PUMPKIN_PLAYER_LIGHTSORUCE.get())), 3);
        }
    }
}
