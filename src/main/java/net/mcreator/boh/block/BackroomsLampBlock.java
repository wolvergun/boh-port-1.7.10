package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.procedures.BackroomsCeilingTileUpdateTickProcedure;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldServer;

public class BackroomsLampBlock extends BohBlock {
    public BackroomsLampBlock() {
        super(
            Properties.of()
                .ignitedByLava()
                .instrument(NoteBlockInstrument.BASS)
                .sound(SoundType.WOOD)
                .strength(5.0F, 20.0F)
                .lightLevel(s -> 6)
                .friction(0.5F)
                .jumpFactor(0.9F)
                .randomTicks()
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
    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        BackroomsCeilingTileUpdateTickProcedure.execute(world, x, y, z);
    }
}
