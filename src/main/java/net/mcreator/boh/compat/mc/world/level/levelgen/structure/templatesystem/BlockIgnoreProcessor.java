package net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;

public class BlockIgnoreProcessor extends StructureProcessor {
    public static final BlockIgnoreProcessor STRUCTURE_BLOCK = new BlockIgnoreProcessor("minecraft:structure_block");
    public static final BlockIgnoreProcessor AIR = new BlockIgnoreProcessor("minecraft:air");
    public static final BlockIgnoreProcessor STRUCTURE_AND_AIR = new BlockIgnoreProcessor("minecraft:structure_block", "minecraft:air");
    private final Set<String> ids = new HashSet<>();

    public BlockIgnoreProcessor(String... ids) {
        for (String s : ids) {
            this.ids.add(s);
        }
    }

    public BlockIgnoreProcessor(List<?> blocks) {
        for (Object o : blocks) {
            Block b = o instanceof Block ? (Block)o : null;
            String n = b == null ? null : Block.blockRegistry.getNameForObject(b);
            if (n != null) {
                this.ids.add(n.contains(":") ? n : "minecraft:" + n);
            }
        }
    }

    @Override
    public boolean ignores(String blockId) {
        return this.ids.contains(blockId);
    }
}
