package net.mcreator.boh.block;

import net.mcreator.boh.procedures.EscapeNightmareBlockEntityCollidesInTheBlockProcedure;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.block.BohBlock;

public class EscapeNightmareBlockBlock extends BohBlock {

    public EscapeNightmareBlockBlock() {
        super(Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.METAL).strength(1.0F, 10.0F).noCollission());
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }

    public void entityInside(BlockState blockstate, World world, BlockPos pos, Entity entity) {
        super.entityInside(blockstate, world, pos, entity);
        EscapeNightmareBlockEntityCollidesInTheBlockProcedure.execute(world, entity);
    }
}
