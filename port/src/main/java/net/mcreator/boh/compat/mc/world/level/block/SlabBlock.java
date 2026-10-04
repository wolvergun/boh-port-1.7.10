package net.mcreator.boh.compat.mc.world.level.block;

import java.util.Random;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.EnumProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.SlabType;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** 1.20 SlabBlock: one block with type=bottom|top|double. */
public class SlabBlock extends BohBlock {

    public static final EnumProperty<SlabType> TYPE = EnumProperty.create("type", SlabType.class);

    public SlabBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(TYPE, SlabType.BOTTOM));
        useNeighborBrightness = true;
        setLightOpacity(0);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(new Property[] { TYPE });
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext ctx) {
        SlabType t = state.getValue(TYPE);
        return t == SlabType.DOUBLE ? Shapes.block() : t == SlabType.TOP ? box(0, 8, 0, 16, 16, 16) : box(0, 0, 0, 16, 8, 16);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction face = ctx.getClickedFace();
        double hy = ctx.getClickLocation().y - ctx.getClickedPos().getY();
        boolean top = face != Direction.UP && (face == Direction.DOWN || hy > 0.5);
        return defaultBlockState().setValue(TYPE, top ? SlabType.TOP : SlabType.BOTTOM);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random r) {
        return BlockState.of(this, meta).getValue(TYPE) == SlabType.DOUBLE ? 2 : 1;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        return true;
    }

    /** Places a second slab into a single slab (called by BohBlockItem before normal placement). */
    public boolean tryMerge(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hy) {
        if (tryMergeAt(stack, player, w, x, y, z, side, hy, true)) return true;
        int[] o = { 0, 0, 0 };
        switch (side) {
            case 0: o[1] = -1; break;
            case 1: o[1] = 1; break;
            case 2: o[2] = -1; break;
            case 3: o[2] = 1; break;
            case 4: o[0] = -1; break;
            default: o[0] = 1;
        }
        return tryMergeAt(stack, player, w, x + o[0], y + o[1], z + o[2], side, hy, false);
    }

    private boolean tryMergeAt(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hy, boolean clicked) {
        if (w.getBlock(x, y, z) != this) return false;
        SlabType t = BlockState.of(this, w.getBlockMetadata(x, y, z)).getValue(TYPE);
        if (t == SlabType.DOUBLE) return false;
        if (clicked && !(t == SlabType.BOTTOM && side == 1 || t == SlabType.TOP && side == 0)) return false;
        if (!clicked && side < 2) return false;
        if (!player.canPlayerEdit(x, y, z, side, stack)) return false;
        if (!w.checkNoEntityCollision(net.minecraft.util.AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1))) return false;
        w.setBlockMetadataWithNotify(x, y, z, BlockState.of(this, w.getBlockMetadata(x, y, z)).setValue(TYPE, SlabType.DOUBLE).meta(), 3);
        w.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, stepSound.func_150496_b(), (stepSound.getVolume() + 1.0F) / 2.0F, stepSound.getPitch() * 0.8F);
        if (!player.capabilities.isCreativeMode) stack.stackSize--;
        return true;
    }
}
