package net.mcreator.boh.block;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModBlockEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.minecraft.world.IBlockAccess;
import net.mcreator.boh.compat.mc.world.level.block.BaseEntityBlock;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.EntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.RenderShape;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.compat.M;

public class JarOWispBlock extends BaseEntityBlock implements EntityBlock {

    public static final IntegerProperty ANIMATION = IntegerProperty.create("animation", 0, 1);

    public JarOWispBlock() {
        super(Properties.of().sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.DECORATED_POT).strength(1.0F, 10.0F).lightLevel(s -> 15).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    public TileEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return M.create(((BlockEntityType) BohModBlockEntities.JAR_O_WISP.get()), blockPos, blockState);
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 7;
    }

    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return box(4.0, 0.0, 4.0, 12.0, 12.05, 12.0);
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(new Property[] { ANIMATION });
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return M.defaultBlockState(this);
    }

    public List<ItemStack> getDrops(BlockState state, Object builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        return !M.isEmpty(dropsOriginal) ? dropsOriginal : Collections.singletonList(M.new_ItemStack(this, 1));
    }
}
