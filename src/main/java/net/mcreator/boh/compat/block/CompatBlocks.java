package net.mcreator.boh.compat.block;

import net.mcreator.boh.compat.client.ItemModels;
import net.mcreator.boh.compat.mc.tags.BlockTags;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public final class CompatBlocks {
    private CompatBlocks() {
    }

    public static void apply(Block b, Properties p) {
        if (p != null) {
            b.setHardness(p.instabreak ? 0.0F : p.hardness);
            b.setResistance(p.resistance / 3.0F);
            b.setStepSound(p.sound.toVanilla());
            int light = p.lightLevel.applyAsInt(BlockState.of(b));
            if (light > 0) {
                b.setLightLevel(light / 15.0F);
            }

            b.slipperiness = p.friction;
        }
    }

    public static void harvest(Block b, ResourceLocation id) {
        for (String tool : new String[]{"pickaxe", "axe", "shovel", "hoe"}) {
            if (BlockTags.create(new ResourceLocation("minecraft", "mineable/" + tool)).contains(id)) {
                int level = BlockTags.create(new ResourceLocation("minecraft", "needs_diamond_tool")).contains(id)
                    ? 3
                    : (
                        BlockTags.create(new ResourceLocation("minecraft", "needs_iron_tool")).contains(id)
                            ? 2
                            : (BlockTags.create(new ResourceLocation("minecraft", "needs_stone_tool")).contains(id) ? 1 : 0)
                    );
                b.setHarvestLevel(tool, level);
            }
        }
    }

    public static void registered(Block b, ResourceLocation id) {
        BlockModels.configure(b, id);
        harvest(b, id);
    }

    public static String itemIconName(ResourceLocation id) {
        if (id == null) {
            return null;
        } else {
            String parent = ItemModels.parentOf(id);
            if (parent == null) {
                return null;
            } else {
                String p = parent.replace("minecraft:", "");
                return p.startsWith("item/") ? ItemModels.iconFor(id) : null;
            }
        }
    }

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
