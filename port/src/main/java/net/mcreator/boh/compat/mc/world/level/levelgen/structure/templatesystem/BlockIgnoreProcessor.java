package net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem;

import java.util.HashSet;
import java.util.Set;

/** 1.20 BlockIgnoreProcessor. */
public class BlockIgnoreProcessor extends StructureProcessor {

    public static final BlockIgnoreProcessor STRUCTURE_BLOCK = new BlockIgnoreProcessor("minecraft:structure_block");
    public static final BlockIgnoreProcessor AIR = new BlockIgnoreProcessor("minecraft:air");
    public static final BlockIgnoreProcessor STRUCTURE_AND_AIR = new BlockIgnoreProcessor("minecraft:structure_block", "minecraft:air");

    private final Set<String> ids = new HashSet<>();

    public BlockIgnoreProcessor(String... ids) {
        for (String s : ids) this.ids.add(s);
    }

    public BlockIgnoreProcessor(java.util.List<?> blocks) {
        for (Object o : blocks) {
            net.minecraft.block.Block b = o instanceof net.minecraft.block.Block ? (net.minecraft.block.Block) o : null;
            String n = b == null ? null : net.minecraft.block.Block.blockRegistry.getNameForObject(b);
            if (n != null) ids.add(n.contains(":") ? n : "minecraft:" + n);
        }
    }

    @Override
    public boolean ignores(String blockId) {
        return ids.contains(blockId);
    }
}
