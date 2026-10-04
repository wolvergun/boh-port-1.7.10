package net.mcreator.boh.block;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.forge.common.util.ForgeSoundType;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

public class StudPartBlock extends BohBlock {
    public StudPartBlock() {
        super(
            Properties.of()
                .sound(
                    new ForgeSoundType(
                        1.0F,
                        1.0F,
                        () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.dripstone_block.break")),
                        () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:stud_footstep")),
                        () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.dripstone_block.place")),
                        () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.dripstone_block.hit")),
                        () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.dripstone_block.fall"))
                    )
                )
                .strength(2.0F, 10.0F)
                .requiresCorrectToolForDrops()
        );
    }

    @Override
    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }
}
