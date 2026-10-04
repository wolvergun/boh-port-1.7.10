package net.mcreator.boh.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.minecraft.world.IBlockAccess;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.forge.common.util.ForgeSoundType;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.block.BohBlock;

public class StudBaseplateBlock extends BohBlock {

    public StudBaseplateBlock() {
        super(Properties.of().sound(new ForgeSoundType(1.0F, 1.0F, () -> (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.dripstone_block.break")), () -> (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:stud_footstep")), () -> (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.dripstone_block.place")), () -> (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.dripstone_block.hit")), () -> (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.dripstone_block.fall")))).strength(-1.0F, 10.0F));
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }
}
