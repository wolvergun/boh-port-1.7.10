package net.mcreator.boh.compat.mc.world.level.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.mcreator.boh.compat.block.CompatBlock;
import net.mcreator.boh.compat.block.CompatBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.WoodType;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class FenceGateBlock extends BlockFenceGate implements CompatBlock {
    public FenceGateBlock(Properties props, WoodType type) {
        CompatBlocks.apply(this, props);
    }

    @Override
    public void onRegistered(ResourceLocation id) {
        CompatBlocks.registered(this, id);
    }

    public int getLightBlock(BlockState state, IBlockAccess world, BlockPos pos) {
        return 0;
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 0;
    }

    public int getFlammability(IBlockAccess w, int x, int y, int z, ForgeDirection face) {
        return this.getFlammability(BlockState.of(this, w.getBlockMetadata(x, y, z)), w, new BlockPos(x, y, z), Direction.from3DDataValue(face.ordinal()));
    }

    public boolean isFlammable(IBlockAccess w, int x, int y, int z, ForgeDirection face) {
        return this.getFlammability(w, x, y, z, face) > 0;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = CompatBlocks.register(this, reg, "texture");
        if (this.blockIcon == null) {
            this.blockIcon = reg.registerIcon("planks_oak");
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }
}
