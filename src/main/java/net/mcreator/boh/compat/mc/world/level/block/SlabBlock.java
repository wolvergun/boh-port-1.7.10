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
import net.mcreator.boh.compat.mc.world.level.block.state.properties.SlabType;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SlabBlock extends BohBlock {
    public static final EnumProperty<SlabType> TYPE = EnumProperty.create("type", SlabType.class);

    public SlabBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM));
        this.useNeighborBrightness = true;
        this.setLightOpacity(0);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext ctx) {
        SlabType t = state.getValue(TYPE);
        return t == SlabType.DOUBLE ? Shapes.block() : (t == SlabType.TOP ? box(0.0, 8.0, 0.0, 16.0, 16.0, 16.0) : box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction face = ctx.getClickedFace();
        double hy = ctx.getClickLocation().y - ctx.getClickedPos().getY();
        boolean top = face != Direction.UP && (face == Direction.DOWN || hy > 0.5);
        return this.defaultBlockState().setValue(TYPE, top ? SlabType.TOP : SlabType.BOTTOM);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    public int quantityDropped(int meta, int fortune, Random r) {
        return BlockState.of(this, meta).getValue(TYPE) == SlabType.DOUBLE ? 2 : 1;
    }

    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        return true;
    }

    public boolean tryMerge(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hy) {
        if (this.tryMergeAt(stack, player, w, x, y, z, side, hy, true)) {
            return true;
        } else {
            int[] o = new int[]{0, 0, 0};
            switch (side) {
                case 0:
                    o[1] = -1;
                    break;
                case 1:
                    o[1] = 1;
                    break;
                case 2:
                    o[2] = -1;
                    break;
                case 3:
                    o[2] = 1;
                    break;
                case 4:
                    o[0] = -1;
                    break;
                default:
                    o[0] = 1;
            }

            return this.tryMergeAt(stack, player, w, x + o[0], y + o[1], z + o[2], side, hy, false);
        }
    }

    private boolean tryMergeAt(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hy, boolean clicked) {
        if (w.getBlock(x, y, z) != this) {
            return false;
        } else {
            SlabType t = BlockState.of(this, w.getBlockMetadata(x, y, z)).getValue(TYPE);
            if (t == SlabType.DOUBLE) {
                return false;
            } else if (!clicked || t == SlabType.BOTTOM && side == 1 || t == SlabType.TOP && side == 0) {
                if (!clicked && side < 2) {
                    return false;
                } else if (!player.canPlayerEdit(x, y, z, side, stack)) {
                    return false;
                } else if (!w.checkNoEntityCollision(AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1))) {
                    return false;
                } else {
                    w.setBlockMetadataWithNotify(x, y, z, BlockState.of(this, w.getBlockMetadata(x, y, z)).setValue(TYPE, SlabType.DOUBLE).meta(), 3);
                    w.playSoundEffect(
                        x + 0.5,
                        y + 0.5,
                        z + 0.5,
                        this.stepSound.func_150496_b(),
                        (this.stepSound.getVolume() + 1.0F) / 2.0F,
                        this.stepSound.getPitch() * 0.8F
                    );
                    if (!player.capabilities.isCreativeMode) {
                        stack.stackSize--;
                    }

                    return true;
                }
            } else {
                return false;
            }
        }
    }
}
