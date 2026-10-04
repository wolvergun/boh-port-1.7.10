package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.LeavesBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.procedures.SinistreeLeavesBlockDestroyedByPlayerProcedure;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SinistreeLeavesBlock extends LeavesBlock {
    public SinistreeLeavesBlock() {
        super(Properties.of().ignitedByLava().sound(SoundType.GRASS).strength(0.2F).noOcclusion());
    }

    @Override
    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 1;
    }

    @Override
    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 30;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockstate, World world, BlockPos pos, EntityPlayer entity, boolean willHarvest, FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
        SinistreeLeavesBlockDestroyedByPlayerProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos), entity);
        return retval;
    }
}
