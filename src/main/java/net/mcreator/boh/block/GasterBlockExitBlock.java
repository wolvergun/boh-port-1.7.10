package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.level.block.DoorBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockSetType;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.procedures.GasterBlockExitOnBlockRightClickedProcedure;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GasterBlockExitBlock extends DoorBlock {
    public GasterBlockExitBlock() {
        super(
            Properties.of()
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.GRAVEL)
                .strength(99.0F)
                .friction(0.5F)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false)
                .dynamicShape(),
            BlockSetType.STONE
        );
    }

    @Override
    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 0;
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
        GasterBlockExitOnBlockRightClickedProcedure.execute(world, x, y, z, entity);
        return InteractionResult.SUCCESS;
    }
}
