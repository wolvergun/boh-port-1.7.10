package net.mcreator.boh.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.DoorBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockSetType;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.IBlockAccess;

public class SinistreeDoorBlock extends DoorBlock {
    public SinistreeDoorBlock() {
        super(
            Properties.of()
                .ignitedByLava()
                .instrument(NoteBlockInstrument.BASS)
                .sound(SoundType.WOOD)
                .strength(3.0F, 2.7663238F)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false)
                .dynamicShape(),
            BlockSetType.OAK
        );
    }

    @Override
    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 8;
    }
}
