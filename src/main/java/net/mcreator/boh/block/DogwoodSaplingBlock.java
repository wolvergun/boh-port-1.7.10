package net.mcreator.boh.block;

import net.mcreator.boh.block.grower.DogwoodSaplingTreeGrower;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.SaplingBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.OffsetType;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.material.MapColor;
import net.mcreator.boh.compat.mc.world.level.material.PushReaction;
import net.minecraft.world.IBlockAccess;

public class DogwoodSaplingBlock extends SaplingBlock {
    public DogwoodSaplingBlock() {
        super(
            new DogwoodSaplingTreeGrower(),
            Properties.of()
                .mapColor(MapColor.PLANT)
                .randomTicks()
                .sound(SoundType.GRASS)
                .instabreak()
                .noCollission()
                .offsetType(OffsetType.NONE)
                .pushReaction(PushReaction.DESTROY)
        );
    }

    @Override
    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 60;
    }
}
