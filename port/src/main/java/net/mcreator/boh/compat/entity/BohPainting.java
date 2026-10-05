package net.mcreator.boh.compat.entity;

import io.netty.buffer.ByteBuf;

import net.mcreator.boh.compat.mc.world.entity.decoration.PaintingVariant;
import net.mcreator.boh.compat.registry.Registration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

/**
 * A painting showing one of the mod's 1.20 painting variants. 1.7.10 paintings are a fixed enum, so the mod's own
 * paintings are this entity; placing a vanilla painting can produce one (see compat/world/Paintings).
 */
public class BohPainting extends EntityHanging implements IEntityAdditionalSpawnData {

    private PaintingVariant variant;

    public BohPainting(net.minecraft.world.World world) {
        super(world);
    }

    public BohPainting(net.minecraft.world.World world, int x, int y, int z, int direction, PaintingVariant variant) {
        super(world, x, y, z, direction);
        this.variant = variant;
        setDirection(direction);
    }

    public PaintingVariant getVariant() {
        return variant;
    }

    static PaintingVariant byName(String name) {
        for (PaintingVariant v : Registration.PAINTINGS) if (v.getId() != null && v.getId().toString().equals(name)) return v;
        return null;
    }

    @Override
    public int getWidthPixels() {
        return variant == null ? 16 : variant.getWidth();
    }

    @Override
    public int getHeightPixels() {
        return variant == null ? 16 : variant.getHeight();
    }

    @Override
    public void onBroken(Entity breaker) {
        if (breaker instanceof EntityPlayer && ((EntityPlayer) breaker).capabilities.isCreativeMode) return;
        entityDropItem(new ItemStack(Items.painting), 0.0F);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        if (variant != null) tag.setString("BohMotive", variant.getId().toString());
        super.writeEntityToNBT(tag);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        variant = byName(tag.getString("BohMotive"));
        super.readEntityFromNBT(tag);
    }

    @Override
    public void writeSpawnData(ByteBuf buf) {
        buf.writeInt(field_146063_b);
        buf.writeInt(field_146064_c);
        buf.writeInt(field_146062_d);
        buf.writeByte(hangingDirection);
        ByteBufUtils.writeUTF8String(buf, variant == null ? "" : variant.getId().toString());
    }

    @Override
    public void readSpawnData(ByteBuf buf) {
        field_146063_b = buf.readInt();
        field_146064_c = buf.readInt();
        field_146062_d = buf.readInt();
        int dir = buf.readByte();
        variant = byName(ByteBufUtils.readUTF8String(buf));
        setDirection(dir);
    }
}
