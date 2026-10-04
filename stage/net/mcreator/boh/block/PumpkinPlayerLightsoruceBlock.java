package net.mcreator.boh.block;

import net.mcreator.boh.procedures.PumpkinPlayerLightsoruceOnTickUpdateProcedure;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.material.PushReaction;
import net.mcreator.boh.compat.mc.world.level.pathfinder.BlockPathTypes;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.M;

public class PumpkinPlayerLightsoruceBlock extends BohBlock {

    public PumpkinPlayerLightsoruceBlock() {
        super(Properties.of().air().sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.EMPTY).instabreak().lightLevel(s -> 15).noCollission().noOcclusion().pushReaction(PushReaction.IGNORE).isRedstoneConductor((bs, br, bp) -> false));
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }

    public VoxelShape getVisualShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return box(15.9, 15.9, 15.9, 16.0, 16.0, 16.0);
    }

    public BlockPathTypes getBlockPathType(BlockState state, IBlockAccess world, BlockPos pos, EntityLiving entity) {
        return BlockPathTypes.OPEN;
    }

    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        M.scheduleTick(world, pos, this, 1);
    }

    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        PumpkinPlayerLightsoruceOnTickUpdateProcedure.execute(world, x, y, z);
        M.scheduleTick(world, pos, this, 1);
    }
}
