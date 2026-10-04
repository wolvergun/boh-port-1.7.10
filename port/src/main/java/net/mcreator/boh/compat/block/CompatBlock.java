package net.mcreator.boh.compat.block;

import net.minecraft.util.ResourceLocation;

/** A 1.7.10 block (vanilla-derived shim) that wants its 1.20 registry id after registration. */
public interface CompatBlock {

    void onRegistered(ResourceLocation id);
}
