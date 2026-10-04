package net.mcreator.boh.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.world.IBlockAccess;
import net.mcreator.boh.compat.mc.world.level.block.FlowerBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.OffsetType;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.material.MapColor;
import net.mcreator.boh.compat.mc.world.level.material.PushReaction;

public class KindnessFlowerBlock extends FlowerBlock {

    public KindnessFlowerBlock() {
        super(() -> MobEffects.HEALTH_BOOST, 100, Properties.of().mapColor(MapColor.PLANT).sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.GRASS).instabreak().noCollission().offsetType(OffsetType.XZ).pushReaction(PushReaction.DESTROY));
    }

    public int getEffectDuration() {
        return 100;
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 100;
    }

    public int getFireSpreadSpeed(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 60;
    }
}
