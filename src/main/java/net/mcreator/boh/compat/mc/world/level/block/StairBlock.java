package net.mcreator.boh.compat.mc.world.level.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class StairBlock extends BlockStairs implements CompatBlock {
    protected ResourceLocation registryName;
    private IIcon side;
    private IIcon top;
    private IIcon bottom;

    public StairBlock(Supplier<BlockState> base, Properties props) {
        super(net.minecraft.init.Blocks.planks, 0);
        CompatBlocks.apply(this, props);
        this.setLightOpacity(0);
        this.useNeighborBrightness = true;
    }

    public StairBlock(BlockState base, Properties props) {
        this(() -> base, props);
    }

    @Override
    public void onRegistered(ResourceLocation id) {
        this.registryName = id;
        CompatBlocks.registered(this, id);
    }

    public float getExplosionResistance() {
        return this.blockResistance / 3.0F;
    }

    public float getExplosionResistance(Entity e) {
        return this.getExplosionResistance();
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

    public int getFlammability(IBlockAccess w, int x, int y, int z, ForgeDirection face) {
        return this.getFlammability(BlockState.of(this, w.getBlockMetadata(x, y, z)), w, new BlockPos(x, y, z), Direction.from3DDataValue(face.ordinal()));
    }

    public boolean isFlammable(IBlockAccess w, int x, int y, int z, ForgeDirection face) {
        return this.getFlammability(w, x, y, z, face) > 0;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.side = CompatBlocks.register(this, reg, "side");
        this.top = CompatBlocks.icon(this, "top");
        this.bottom = CompatBlocks.icon(this, "bottom");
        if (this.side == null) {
            this.side = net.minecraft.init.Blocks.planks.getIcon(2, 0);
        }

        if (this.top == null) {
            this.top = this.side;
        }

        if (this.bottom == null) {
            this.bottom = this.side;
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int s, int meta) {
        return s == 0 ? this.bottom : (s == 1 ? this.top : this.side);
    }
}
