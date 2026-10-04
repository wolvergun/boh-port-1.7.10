package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.block.CompatBlock;
import net.mcreator.boh.compat.block.CompatBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockSetType;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** 1.20 TrapDoorBlock on 1.7.10 BlockTrapDoor. */
public class TrapDoorBlock extends BlockTrapDoor implements CompatBlock {

    protected final BlockSetType type;
    protected ResourceLocation registryName;

    public TrapDoorBlock(Properties props, BlockSetType type) {
        super(type.wooden ? Material.wood : Material.iron);
        this.type = type;
        CompatBlocks.apply(this, props);
    }

    @Override
    public void onRegistered(ResourceLocation id) {
        registryName = id;
        CompatBlocks.registered(this, id);
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float hx, float hy, float hz) {
        if (!type.wooden) return false;
        int meta = w.getBlockMetadata(x, y, z);
        w.setBlockMetadataWithNotify(x, y, z, meta ^ 4, 2);
        w.playAuxSFXAtEntity(p, 1003, x, y, z, 0);
        return true;
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return type.wooden ? 20 : 0;
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
        if (blockIcon == null) blockIcon = reg.registerIcon("trapdoor");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return blockIcon;
    }
}
