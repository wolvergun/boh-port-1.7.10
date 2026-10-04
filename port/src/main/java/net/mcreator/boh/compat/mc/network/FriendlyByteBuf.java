package net.mcreator.boh.compat.mc.network;

import io.netty.buffer.ByteBuf;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;

/** 1.20 FriendlyByteBuf: a netty buffer with the modern read/write helpers. */
public class FriendlyByteBuf {

    private final ByteBuf buf;

    public FriendlyByteBuf(ByteBuf buf) {
        this.buf = buf;
    }

    public ByteBuf unwrap() {
        return buf;
    }

    public int readInt() {
        return buf.readInt();
    }

    public FriendlyByteBuf writeInt(int v) {
        buf.writeInt(v);
        return this;
    }

    public byte readByte() {
        return buf.readByte();
    }

    public FriendlyByteBuf writeByte(int v) {
        buf.writeByte(v);
        return this;
    }

    public boolean readBoolean() {
        return buf.readBoolean();
    }

    public FriendlyByteBuf writeBoolean(boolean v) {
        buf.writeBoolean(v);
        return this;
    }

    public double readDouble() {
        return buf.readDouble();
    }

    public FriendlyByteBuf writeDouble(double v) {
        buf.writeDouble(v);
        return this;
    }

    public float readFloat() {
        return buf.readFloat();
    }

    public FriendlyByteBuf writeFloat(float v) {
        buf.writeFloat(v);
        return this;
    }

    public long readLong() {
        return buf.readLong();
    }

    public FriendlyByteBuf writeLong(long v) {
        buf.writeLong(v);
        return this;
    }

    public int readVarInt() {
        return ByteBufUtils.readVarInt(buf, 5);
    }

    public FriendlyByteBuf writeVarInt(int v) {
        ByteBufUtils.writeVarInt(buf, v, 5);
        return this;
    }

    public String readUtf() {
        return ByteBufUtils.readUTF8String(buf);
    }

    public String readUtf(int max) {
        return readUtf();
    }

    public FriendlyByteBuf writeUtf(String s) {
        ByteBufUtils.writeUTF8String(buf, s == null ? "" : s);
        return this;
    }

    public BlockPos readBlockPos() {
        return BlockPos.of(buf.readLong());
    }

    public FriendlyByteBuf writeBlockPos(BlockPos p) {
        buf.writeLong(p.asLong());
        return this;
    }

    public Component readComponent() {
        return Component.literal(readUtf());
    }

    public FriendlyByteBuf writeComponent(Component c) {
        return writeUtf(c == null ? "" : c.getString());
    }

    public NBTTagCompound readNbt() {
        return ByteBufUtils.readTag(buf);
    }

    public FriendlyByteBuf writeNbt(NBTTagCompound tag) {
        ByteBufUtils.writeTag(buf, tag);
        return this;
    }

    public ItemStack readItem() {
        return ByteBufUtils.readItemStack(buf);
    }

    public FriendlyByteBuf writeItem(ItemStack s) {
        ByteBufUtils.writeItemStack(buf, s);
        return this;
    }

    public int readableBytes() {
        return buf.readableBytes();
    }

    public <T extends Enum<T>> T readEnum(Class<T> cls) {
        return cls.getEnumConstants()[readVarInt()];
    }

    public FriendlyByteBuf writeEnum(Enum<?> e) {
        return writeVarInt(e.ordinal());
    }
}
