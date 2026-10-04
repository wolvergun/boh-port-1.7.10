package net.mcreator.boh.compat.block;

import net.mcreator.boh.compat.client.ItemModels;
import net.mcreator.boh.compat.mc.tags.BlockTags;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

/** Shared setup for blocks that extend 1.7.10 vanilla classes instead of BohBlock. */
public final class CompatBlocks {

    private CompatBlocks() {}

    public static void apply(Block b, Properties p) {
        if (p == null) return;
        b.setHardness(p.instabreak ? 0 : p.hardness);
        b.setResistance(p.resistance / 3.0F);
        b.setStepSound(p.sound.toVanilla());
        int light = p.lightLevel.applyAsInt(BlockState.of(b));
        if (light > 0) b.setLightLevel(light / 15.0F);
        b.slipperiness = p.friction;
    }

    /** Harvest tool/level from the 1.20 mineable/needs_*_tool tags. */
    public static void harvest(Block b, ResourceLocation id) {
        for (String tool : new String[] { "pickaxe", "axe", "shovel", "hoe" }) {
            if (BlockTags.create(new ResourceLocation("minecraft", "mineable/" + tool)).contains(id)) {
                int level = BlockTags.create(new ResourceLocation("minecraft", "needs_diamond_tool")).contains(id) ? 3
                    : BlockTags.create(new ResourceLocation("minecraft", "needs_iron_tool")).contains(id) ? 2
                        : BlockTags.create(new ResourceLocation("minecraft", "needs_stone_tool")).contains(id) ? 1 : 0;
                b.setHarvestLevel(tool, level);
            }
        }
    }

    public static void registered(Block b, ResourceLocation id) {
        BlockModels.configure(b, id);
        harvest(b, id);
    }

    /** Flat item icon for blocks whose item model is item/generated (doors, saplings, flowers), else null. */
    public static String itemIconName(ResourceLocation id) {
        if (id == null) return null;
        String parent = ItemModels.parentOf(id);
        if (parent == null) return null;
        String p = parent.replace("minecraft:", "");
        return p.startsWith("item/") ? ItemModels.iconFor(id) : null;
    }

    /** Registers the block's model textures; returns the icon for a texture key of its first model ("side", "texture"...). */
    public static IIcon register(Block b, IIconRegister reg, String key) {
        BlockModels.registerIcons(b, reg);
        return icon(b, key);
    }

    public static IIcon icon(Block b, String key) {
        String loc = BlockModels.textureOf(b, key);
        IIcon i = loc == null ? null : BlockModels.iconFor(b, loc);
        if (i == null) {
            loc = BlockModels.textureOf(b, "particle");
            i = loc == null ? null : BlockModels.iconFor(b, loc);
        }
        return i;
    }
}
