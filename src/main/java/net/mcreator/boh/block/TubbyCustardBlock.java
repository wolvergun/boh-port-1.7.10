package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.procedures.TubbyCustardBlockAddedProcedure;
import net.mcreator.boh.procedures.TubbyCustardOnBlockRightClickedProcedure;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TubbyCustardBlock extends BohBlock {
    public TubbyCustardBlock() {
        super(Properties.of().sound(SoundType.METAL).strength(20.0F, 200.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockAccess reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return box(4.0, 0.0, 4.0, 12.0, 2.0, 12.0);
    }

    @Override
    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        TubbyCustardBlockAddedProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
    }

    @Override
    public InteractionResult use(BlockState blockstate, World world, BlockPos pos, EntityPlayer entity, InteractionHand hand, BlockHitResult hit) {
        super.use(blockstate, world, pos, entity, hand, hit);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        double hitX = M.getLocation(hit).x;
        double hitY = M.getLocation(hit).y;
        double hitZ = M.getLocation(hit).z;
        Direction direction = M.getDirection(hit);
        TubbyCustardOnBlockRightClickedProcedure.execute(world, x, y, z);
        return InteractionResult.SUCCESS;
    }
}
