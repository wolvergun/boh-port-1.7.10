package net.mcreator.boh.compat.forge.common.capabilities;

import net.minecraft.nbt.NBTBase;

public interface ICapabilitySerializable<T extends NBTBase> extends ICapabilityProvider {
    T serializeNBT();

    void deserializeNBT(T var1);
}
