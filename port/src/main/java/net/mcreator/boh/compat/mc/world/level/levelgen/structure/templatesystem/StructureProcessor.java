package net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem;

/** 1.20 StructureProcessor (only block ignoring is supported). */
public abstract class StructureProcessor {

    /** true to drop a block with this 1.20 id */
    public abstract boolean ignores(String blockId);
}
