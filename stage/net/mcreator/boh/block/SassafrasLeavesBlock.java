package net.mcreator.boh.block;

import net.mcreator.boh.procedures.SassafrasLeavesBlockDestroyedByPlayerProcedure;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.LeavesBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.M;

public class SassafrasLeavesBlock extends LeavesBlock {

    public SassafrasLeavesBlock() {
        super(Properties.of().ignitedByLava().sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.GRASS).strength(0.2F).noOcclusion());
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 1;
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 30;
    }

    public boolean onDestroyedByPlayer(BlockState blockstate, World world, BlockPos pos, EntityPlayer entity, boolean willHarvest, FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
        SassafrasLeavesBlockDestroyedByPlayerProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos), entity);
        return retval;
    }
}
