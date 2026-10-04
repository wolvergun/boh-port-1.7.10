package net.mcreator.boh.compat.mc.world.level.block;

import java.util.function.Supplier;

import net.mcreator.boh.compat.block.CompatBlock;
import net.mcreator.boh.compat.block.CompatBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** 1.20 StairBlock on 1.7.10 BlockStairs (planks as the model block, own textures). */
public class StairBlock extends BlockStairs implements CompatBlock {

    protected ResourceLocation registryName;
    private IIcon side, top, bottom;

    public StairBlock(Supplier<BlockState> base, Properties props) {
        super(Blocks.planks, 0);
        CompatBlocks.apply(this, props);
        setLightOpacity(0);
        useNeighborBrightness = true;
    }

    public StairBlock(BlockState base, Properties props) {
        this(() -> base, props);
    }

    @Override
    public void onRegistered(ResourceLocation id) {
        registryName = id;
        CompatBlocks.registered(this, id);
    }

    public float getExplosionResistance() {
        return blockResistance / 3.0F;
    }

    @Override
    public float getExplosionResistance(Entity e) {
        return getExplosionResistance();
    }

    public boolean isRandomlyTicking(BlockState state) {
        return false;
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
        side = CompatBlocks.register(this, reg, "side");
        top = CompatBlocks.icon(this, "top");
        bottom = CompatBlocks.icon(this, "bottom");
        if (side == null) side = Blocks.planks.getIcon(2, 0);
        if (top == null) top = side;
        if (bottom == null) bottom = side;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int s, int meta) {
        return s == 0 ? bottom : s == 1 ? top : side;
    }
}
