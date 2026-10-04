package net.mcreator.boh.block;

import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.procedures.BackroomsCeilingFillerUpdateTickProcedure;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.mc.world.phys.HitResult;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.M;

public class BackroomsCeilingFillerBlock extends BohBlock {

    public BackroomsCeilingFillerBlock() {
        super(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.WOOD).strength(10.0F, 40.0F).noCollission());
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return M.new_ItemStack(BohModBlocks.BACKROOMS_CEILING_TILE.get());
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
        BackroomsCeilingFillerUpdateTickProcedure.execute(world, x, y, z);
        M.scheduleTick(world, pos, this, 1);
    }
}
