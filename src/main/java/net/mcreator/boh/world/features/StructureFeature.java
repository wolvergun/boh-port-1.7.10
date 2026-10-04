package net.mcreator.boh.world.features;

import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.mc.core.registries.Registries;

public class StructureFeature {
    public static final DeferredRegister<Object> REGISTRY = DeferredRegister.create(Registries.FEATURE, "boh");

    private StructureFeature() {
    }
}
