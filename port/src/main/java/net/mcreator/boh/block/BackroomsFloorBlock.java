package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.procedures.BackroomsFloorUpdateTickProcedure;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldServer;

/**
 * Hand-written override of the translated block (stage/ copy removed, see tools/retranslate.sh). The original is an
 * MCreator container block (empty inventory, no GUI, nothing reads it), so every floor block of Level 0 carried a
 * block entity: hundreds per chunk created, saved, synced and looked up by the chunk renderer, which made exploring
 * Level 0 stutter. Without the inventory the block behaves the same.
 */
public class BackroomsFloorBlock extends BohBlock {

    public BackroomsFloorBlock() {
        super(Properties.of().ignitedByLava().sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.WOOL).strength(10.0F, 40.0F));
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }

    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        BackroomsFloorUpdateTickProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
    }
}
