package net.mcreator.boh.block;

import net.mcreator.boh.procedures.GlowblockFigureBlockAddedProcedure;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.entity.EntityLiving;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.pathfinder.BlockPathTypes;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.M;

public class GlowblockFigureBlock extends BohBlock {

    public GlowblockFigureBlock() {
        super(Properties.of().sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.EMPTY).strength(1.0F, 10.0F).lightLevel(s -> 5).noCollission().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockAccess reader, BlockPos pos) {
        return true;
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 0;
    }

    public VoxelShape getVisualShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return box(0.0, 0.0, 0.0, 0.1, 0.1, 0.1);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return M.getItem(M.getItemInHand(context)) != M.asItem(this);
    }

    public BlockPathTypes getBlockPathType(BlockState state, IBlockAccess world, BlockPos pos, EntityLiving entity) {
        return BlockPathTypes.OPEN;
    }

    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        GlowblockFigureBlockAddedProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
    }
}
