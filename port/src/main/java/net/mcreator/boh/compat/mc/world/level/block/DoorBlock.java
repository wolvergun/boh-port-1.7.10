package net.mcreator.boh.compat.mc.world.level.block;

import java.util.Random;

import net.mcreator.boh.compat.block.CompatBlock;
import net.mcreator.boh.compat.block.CompatBlocks;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockSetType;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** 1.20 DoorBlock on 1.7.10 BlockDoor (placed by DoorItem as two halves). */
public class DoorBlock extends BlockDoor implements CompatBlock {

    protected final BlockSetType type;
    protected ResourceLocation registryName;
    private IIcon[] upper, lower;

    public DoorBlock(Properties props, BlockSetType type) {
        super(type.wooden ? Material.wood : Material.iron);
        this.type = type;
        CompatBlocks.apply(this, props);
        disableStats();
    }

    @Override
    public void onRegistered(ResourceLocation id) {
        registryName = id;
        CompatBlocks.registered(this, id);
    }

    public BlockSetType type() {
        return type;
    }

    /** 1.20 use(): wooden doors toggle; subclasses may override and call super. */
    public InteractionResult use(BlockState state, World world, BlockPos pos, EntityPlayer player, InteractionHand hand, BlockHitResult hit) {
        if (!type.wooden) return InteractionResult.PASS;
        func_150014_a(world, pos.getX(), pos.getY(), pos.getZ(), !func_150015_f(world, pos.getX(), pos.getY(), pos.getZ()));
        return InteractionResult.sidedSuccess(world.isRemote);
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float hx, float hy, float hz) {
        BlockHitResult hit = new BlockHitResult(new Vec3(x + hx, y + hy, z + hz), Direction.from3DDataValue(side), new BlockPos(x, y, z), false);
        InteractionResult r = use(BlockState.of(this, w.getBlockMetadata(x, y, z)), w, new BlockPos(x, y, z), p, InteractionHand.MAIN_HAND, hit);
        return r != null && r.consumesAction();
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

    public int getLightBlock(BlockState state, IBlockAccess world, BlockPos pos) {
        return 0;
    }

    @Override
    public Item getItemDropped(int meta, Random r, int fortune) {
        return (meta & 8) != 0 ? null : Item.getItemFromBlock(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World w, int x, int y, int z) {
        return Item.getItemFromBlock(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return CompatBlocks.itemIconName(registryName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        net.mcreator.boh.compat.block.BlockModels.registerIcons(this, reg);
        IIcon top = CompatBlocks.icon(this, "top"), bottom = CompatBlocks.icon(this, "bottom");
        if (top == null) top = reg.registerIcon("door_wood_upper");
        if (bottom == null) bottom = reg.registerIcon("door_wood_lower");
        upper = new IIcon[] { top, new IconFlipped(top, true, false) };
        lower = new IIcon[] { bottom, new IconFlipped(bottom, true, false) };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return lower[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess w, int x, int y, int z, int side) {
        if (side == 1 || side == 0) return lower[0];
        int i1 = func_150012_g(w, x, y, z);
        int j1 = i1 & 3;
        boolean open = (i1 & 4) != 0;
        boolean flip = false;
        boolean top = (i1 & 8) != 0;
        if (open) {
            if (j1 == 0 && side == 2 || j1 == 1 && side == 5 || j1 == 2 && side == 3 || j1 == 3 && side == 4) flip = true;
        } else {
            if (j1 == 0 && side == 5 || j1 == 1 && side == 3 || j1 == 2 && side == 4 || j1 == 3 && side == 2) flip = true;
            if ((i1 & 16) != 0) flip = !flip;
        }
        return top ? upper[flip ? 1 : 0] : lower[flip ? 1 : 0];
    }
}
