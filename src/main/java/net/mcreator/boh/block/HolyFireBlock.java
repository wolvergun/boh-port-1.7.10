package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.OffsetType;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.phys.HitResult;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.procedures.HolyFireBlockAddedProcedure;
import net.mcreator.boh.procedures.HolyFireBlockValidPlacementConditionProcedure;
import net.mcreator.boh.procedures.HolyFireEntityCollidesInTheBlockProcedure;
import net.mcreator.boh.procedures.HolyFireOnTickUpdateProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class HolyFireBlock extends BohBlock {
    public HolyFireBlock() {
        super(
            Properties.of()
                .sound(SoundType.METAL)
                .instabreak()
                .lightLevel(s -> 7)
                .noCollission()
                .friction(1.0F)
                .noOcclusion()
                .hasPostProcess((bs, br, bp) -> true)
                .emissiveRendering((bs, br, bp) -> true)
                .isRedstoneConductor((bs, br, bp) -> false)
                .dynamicShape()
                .offsetType(OffsetType.XZ)
        );
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
        Vec3 offset = M.getOffset(state, world, pos);
        return M.move(box(2.0, 0.0, 2.0, 14.0, 0.5, 14.0), offset.x, offset.y, offset.z);
    }

    public boolean canSurvive(BlockState blockstate, World worldIn, BlockPos pos) {
        if (worldIn instanceof World) {
            int x = M.getX(pos);
            int y = M.getY(pos);
            int z = M.getZ(pos);
            return HolyFireBlockValidPlacementConditionProcedure.execute(worldIn, x, y, z);
        } else {
            return super.canSurvive(blockstate, worldIn, pos);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, World world, BlockPos currentPos, BlockPos facingPos) {
        return !M.canSurvive(state, world, currentPos)
            ? M.defaultBlockState(Blocks.AIR)
            : super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return M.getItem(M.getItemInHand(context)) != M.asItem(this);
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return M.new_ItemStack(Blocks.AIR);
    }

    @Override
    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        M.scheduleTick(world, pos, this, 1);
        HolyFireBlockAddedProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
    }

    @Override
    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        HolyFireOnTickUpdateProcedure.execute(world, x, y, z);
        M.scheduleTick(world, pos, this, 1);
    }

    @Override
    public void entityInside(BlockState blockstate, World world, BlockPos pos, Entity entity) {
        super.entityInside(blockstate, world, pos, entity);
        HolyFireEntityCollidesInTheBlockProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos), entity);
    }
}
