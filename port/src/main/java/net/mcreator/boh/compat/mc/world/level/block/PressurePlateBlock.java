package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.block.CompatBlock;
import net.mcreator.boh.compat.block.CompatBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockSetType;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** 1.20 PressurePlateBlock on 1.7.10 BlockPressurePlate. */
public class PressurePlateBlock extends BlockPressurePlate implements CompatBlock {

    public PressurePlateBlock(net.mcreator.boh.compat.mc.world.level.block.Sensitivity sensitivity, Properties props, BlockSetType type) {
        super("planks_oak", type.wooden ? Material.wood : Material.rock,
            sensitivity == net.mcreator.boh.compat.mc.world.level.block.Sensitivity.MOBS ? BlockPressurePlate.Sensitivity.mobs : BlockPressurePlate.Sensitivity.everything);
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
