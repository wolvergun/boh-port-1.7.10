package net.mcreator.boh.compat.mc.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FriendlyByteBuf {
    private final ByteBuf buf;

    public FriendlyByteBuf(ByteBuf buf) {
        this.buf = buf;
    }

    public ByteBuf unwrap() {
        return this.buf;
    }

    public int readInt() {
        return this.buf.readInt();
    }

    public FriendlyByteBuf writeInt(int v) {
        this.buf.writeInt(v);
        return this;
    }

    public byte readByte() {
        return this.buf.readByte();
    }

    public FriendlyByteBuf writeByte(int v) {
        this.buf.writeByte(v);
        return this;
    }

    public boolean readBoolean() {
        return this.buf.readBoolean();
    }

    public FriendlyByteBuf writeBoolean(boolean v) {
        this.buf.writeBoolean(v);
        return this;
    }

    public double readDouble() {
        return this.buf.readDouble();
    }

    public FriendlyByteBuf writeDouble(double v) {
        this.buf.writeDouble(v);
        return this;
    }

    public float readFloat() {
        return this.buf.readFloat();
    }

    public FriendlyByteBuf writeFloat(float v) {
        this.buf.writeFloat(v);
        return this;
    }

    public long readLong() {
        return this.buf.readLong();
    }

    public FriendlyByteBuf writeLong(long v) {
        this.buf.writeLong(v);
        return this;
    }

    public int readVarInt() {
        return ByteBufUtils.readVarInt(this.buf, 5);
    }

    public FriendlyByteBuf writeVarInt(int v) {
        ByteBufUtils.writeVarInt(this.buf, v, 5);
        return this;
    }

    public String readUtf() {
        return ByteBufUtils.readUTF8String(this.buf);
    }

    public String readUtf(int max) {
        return this.readUtf();
    }

    public FriendlyByteBuf writeUtf(String s) {
        ByteBufUtils.writeUTF8String(this.buf, s == null ? "" : s);
        return this;
    }

    public BlockPos readBlockPos() {
        return BlockPos.of(this.buf.readLong());
    }

    public FriendlyByteBuf writeBlockPos(BlockPos p) {
        this.buf.writeLong(p.asLong());
        return this;
    }

    public Component readComponent() {
        return Component.literal(this.readUtf());
    }

    public FriendlyByteBuf writeComponent(Component c) {
        return this.writeUtf(c == null ? "" : c.getString());
    }

    public NBTTagCompound readNbt() {
        return ByteBufUtils.readTag(this.buf);
    }

    public FriendlyByteBuf writeNbt(NBTTagCompound tag) {
        ByteBufUtils.writeTag(this.buf, tag);
        return this;
    }

    public ItemStack readItem() {
        return ByteBufUtils.readItemStack(this.buf);
    }

    public FriendlyByteBuf writeItem(ItemStack s) {
        ByteBufUtils.writeItemStack(this.buf, s);
        return this;
    }

    public int readableBytes() {
        return this.buf.readableBytes();
    }

    public <T extends Enum<T>> T readEnum(Class<T> cls) {
        return cls.getEnumConstants()[this.readVarInt()];
    }

    public FriendlyByteBuf writeEnum(Enum<?> e) {
        return this.writeVarInt(e.ordinal());
    }
}
