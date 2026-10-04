package net.mcreator.boh.world.features;

import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.mc.core.registries.Registries;

/**
 * Hand-written override of the 1.20 structure feature: in 1.7.10 the mod's NBT structures are placed by
 * compat world generation (see compat.world), so this only keeps the registry BohMod references.
 */
public class StructureFeature {

    public static final DeferredRegister<Object> REGISTRY = DeferredRegister.create(Registries.FEATURE, "boh");

    private StructureFeature() {}
}
