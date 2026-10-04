package net.mcreator.boh.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.core.Axis;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.EnumProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.M;

public class SinistreeWoodBlock extends BohBlock {

    public static final EnumProperty<Axis> AXIS = BlockStateProperties.AXIS;

    public SinistreeWoodBlock() {
        super(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.WOOD).strength(2.0F));
        M.registerDefaultState(this, (BlockState) M.setValue(((BlockState) M.any(this.stateDefinition)), AXIS, Axis.Y));
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[] { AXIS });
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState) M.setValue(super.getStateForPlacement(context), AXIS, M.getClickedFace(context).getAxis());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) {
            if (state.getValue(AXIS) == Axis.X) {
                return (BlockState) M.setValue(state, AXIS, Axis.Z);
            }
            if (state.getValue(AXIS) == Axis.Z) {
                return (BlockState) M.setValue(state, AXIS, Axis.X);
            }
        }
        return state;
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 5;
    }
}
