package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.block.CompatBlock;
import net.mcreator.boh.compat.block.CompatBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockSetType;
import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** 1.20 ButtonBlock on 1.7.10 BlockButton. */
public class ButtonBlock extends BlockButton implements CompatBlock {

    private final int ticks;

    public ButtonBlock(Properties props, BlockSetType type, int ticks, boolean arrowsCanPress) {
        super(arrowsCanPress);
        this.ticks = ticks;
        CompatBlocks.apply(this, props);
    }

    @Override
    public void onRegistered(ResourceLocation id) {
        CompatBlocks.registered(this, id);
    }

    @Override
    public int tickRate(World w) {
        return ticks;
    }

    public int getLightBlock(BlockState state, IBlockAccess world, BlockPos pos) {
        return 0;
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 0;
    }

    @Override
    public int getFlammability(IBlockAccess w, int x, int y, int z, net.minecraftforge.common.util.ForgeDirection face) {
        return getFlammability(BlockState.of(this, w.getBlockMetadata(x, y, z)), w, new BlockPos(x, y, z), Direction.from3DDataValue(face.ordinal()));
    }

    @Override
    public boolean isFlammable(IBlockAccess w, int x, int y, int z, net.minecraftforge.common.util.ForgeDirection face) {
        return getFlammability(w, x, y, z, face) > 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = CompatBlocks.register(this, reg, "texture");
        if (blockIcon == null) blockIcon = reg.registerIcon("planks_oak");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return blockIcon;
    }
}
