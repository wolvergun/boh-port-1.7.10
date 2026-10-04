package net.mcreator.boh.compat.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.registry.LegacyIds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;

public final class VanillaStates {
    private static final String[] HORIZONTAL = new String[]{"south", "west", "north", "east"};

    private VanillaStates() {
    }

    public static VanillaStates.Legacy convert(String name, Map<String, String> props) {
        if (props == null) {
            props = new HashMap<>();
        }

        if (!name.equals("minecraft:structure_void") && !name.equals("minecraft:jigsaw") && !name.equals("minecraft:structure_block")) {
            Block direct = name.startsWith("minecraft:") ? null : Block.getBlockFromName(name);
            if (direct instanceof BohBlock) {
                BlockState s = ((BohBlock)direct).defaultBlockState();

                for (Entry<String, String> e : props.entrySet()) {
                    Property p = s.definition().getProperty(e.getKey());
                    if (p != null) {
                        Object v = p.getValue(e.getValue()).orElse(null);
                        if (v != null) {
                            s = s.setValue(p, (Comparable)v);
                        }
                    }
                }

                return new VanillaStates.Legacy(direct, s.meta(), s);
            } else if (direct != null && direct != Blocks.air) {
                return new VanillaStates.Legacy(direct, legacyMeta(direct, 0, name, props), null);
            } else {
                String path = name.startsWith("minecraft:") ? name.substring(10) : name;
                if (path.equals("air") || path.equals("cave_air") || path.equals("void_air")) {
                    return new VanillaStates.Legacy(Blocks.air, 0, null);
                } else if (path.equals("water")) {
                    return new VanillaStates.Legacy(Blocks.water, 0, null);
                } else if (path.equals("lava")) {
                    return new VanillaStates.Legacy(Blocks.lava, 0, null);
                } else {
                    LegacyIds.Target t = LegacyIds.target(name);
                    if (t == null) {
                        return null;
                    } else {
                        Block b = t.block();
                        if (b != null && b != Blocks.air) {
                            if (path.endsWith("_slab") && "double".equals(props.get("type"))) {
                                if (b == Blocks.wooden_slab) {
                                    return new VanillaStates.Legacy(Blocks.double_wooden_slab, t.meta & 7, null);
                                }

                                if (b == Blocks.stone_slab) {
                                    return new VanillaStates.Legacy(Blocks.double_stone_slab, t.meta & 7, null);
                                }
                            }

                            return new VanillaStates.Legacy(b, legacyMeta(b, t.meta, path, props), null);
                        } else {
                            return null;
                        }
                    }
                }
            }
        } else {
            return null;
        }
    }

    private static int horiz(String facing, int south, int west, int north, int east) {
        if (facing == null) {
            return north;
        } else {
            switch (facing) {
                case "south":
                    return south;
                case "west":
                    return west;
                case "east":
                    return east;
                default:
                    return north;
            }
        }
    }

    static int legacyMeta(Block b, int base, String path, Map<String, String> p) {
        String facing = p.get("facing");
        if (path.endsWith("_stairs")) {
            int d = horiz(facing, 2, 1, 3, 0);
            return d | ("top".equals(p.get("half")) ? 4 : 0);
        } else if (b instanceof BlockSlab) {
            return base & 7 | ("top".equals(p.get("type")) ? 8 : 0);
        } else if (b instanceof BlockLog && p.containsKey("axis")) {
            String a = p.get("axis");
            return base & 3 | ("x".equals(a) ? 4 : ("z".equals(a) ? 8 : 0));
        } else if (b == Blocks.quartz_block && p.containsKey("axis")) {
            String a = p.get("axis");
            return "x".equals(a) ? 3 : ("z".equals(a) ? 4 : 2);
        } else if (b instanceof BlockRotatedPillar && p.containsKey("axis")) {
            String a = p.get("axis");
            return base & 3 | ("x".equals(a) ? 4 : ("z".equals(a) ? 8 : 0));
        } else if (path.endsWith("_trapdoor")) {
            int d = horiz(facing, 1, 2, 0, 3);
            return d | ("true".equals(p.get("open")) ? 4 : 0) | ("top".equals(p.get("half")) ? 8 : 0);
        } else if (path.endsWith("_door")) {
            return "upper".equals(p.get("half"))
                ? 8 | ("right".equals(p.get("hinge")) ? 1 : 0)
                : horiz(facing, 1, 2, 3, 0) | ("true".equals(p.get("open")) ? 4 : 0);
        } else if (path.endsWith("_fence_gate")) {
            return horiz(facing, 0, 1, 2, 3) | ("true".equals(p.get("open")) ? 4 : 0);
        } else if (path.endsWith("_bed")) {
            return horiz(facing, 0, 1, 2, 3) | ("head".equals(p.get("part")) ? 8 : 0);
        } else if (path.endsWith("_leaves")) {
            return base & 3 | ("true".equals(p.get("persistent")) ? 4 : 0);
        } else if (path.endsWith("wall_torch") || path.equals("redstone_wall_torch")) {
            return horiz(facing, 3, 2, 4, 1);
        } else if (path.equals("torch") || path.equals("redstone_torch") || path.equals("soul_torch")) {
            return 5;
        } else if (path.endsWith("_button")) {
            String face = p.get("face");
            if ("floor".equals(face)) {
                return 5;
            } else {
                return "ceiling".equals(face) ? 0 : horiz(facing, 3, 2, 4, 1);
            }
        } else if (!path.equals("ladder")
            && !path.endsWith("wall_sign")
            && !path.endsWith("wall_banner")
            && !path.equals("chest")
            && !path.equals("trapped_chest")
            && !path.equals("ender_chest")
            && !path.equals("furnace")
            && !path.equals("blast_furnace")
            && !path.equals("smoker")
            && !path.equals("dispenser")
            && !path.equals("dropper")
            && !path.equals("barrel")) {
            if (path.equals("vine")) {
                int m = 0;
                if ("true".equals(p.get("south"))) {
                    m |= 1;
                }

                if ("true".equals(p.get("west"))) {
                    m |= 2;
                }

                if ("true".equals(p.get("north"))) {
                    m |= 4;
                }

                if ("true".equals(p.get("east"))) {
                    m |= 8;
                }

                return m;
            } else if (path.equals("rail") && p.containsKey("shape")) {
                String[] shapes = new String[]{
                    "north_south",
                    "east_west",
                    "ascending_east",
                    "ascending_west",
                    "ascending_north",
                    "ascending_south",
                    "south_east",
                    "south_west",
                    "north_west",
                    "north_east"
                };

                for (int i = 0; i < shapes.length; i++) {
                    if (shapes[i].equals(p.get("shape"))) {
                        return i;
                    }
                }

                return 0;
            } else if (path.equals("redstone_wire") && p.containsKey("power")) {
                return Integer.parseInt(p.get("power"));
            } else if (path.equals("tall_grass")
                || path.equals("large_fern")
                || path.equals("sunflower")
                || path.equals("lilac")
                || path.equals("rose_bush")
                || path.equals("peony")) {
                return "upper".equals(p.get("half")) ? 8 : base;
            } else if (path.equals("tripwire_hook")) {
                return horiz(facing, 0, 1, 2, 3) | ("true".equals(p.get("attached")) ? 4 : 0);
            } else if (!path.equals("skeleton_skull") && (!path.endsWith("_head") || path.endsWith("wall_head"))) {
                if (path.equals("water_cauldron") && p.containsKey("level")) {
                    return Integer.parseInt(p.get("level"));
                } else {
                    return path.equals("redstone_lamp") ? base : base;
                }
            } else {
                return 1;
            }
        } else if ("up".equals(facing)) {
            return 1;
        } else {
            return "down".equals(facing) ? 0 : horiz(facing, 3, 4, 2, 5);
        }
    }

    public static Map<String, String> rotate(Map<String, String> props, int quarterTurns, boolean mirrorX) {
        if (props != null && (quarterTurns != 0 || mirrorX)) {
            Map<String, String> out = new HashMap<>(props);
            String f = props.get("facing");
            if (f != null) {
                out.put("facing", rotateDir(f, quarterTurns, mirrorX));
            }

            String a = props.get("axis");
            if (a != null && (quarterTurns & 1) == 1 && !a.equals("y")) {
                out.put("axis", a.equals("x") ? "z" : "x");
            }

            if (props.containsKey("north")) {
                for (String d : HORIZONTAL) {
                    out.put(rotateDir(d, quarterTurns, mirrorX), props.getOrDefault(d, "false"));
                }
            }

            if (mirrorX && props.containsKey("hinge")) {
                out.put("hinge", "left".equals(props.get("hinge")) ? "right" : "left");
            }

            return out;
        } else {
            return props;
        }
    }

    private static String rotateDir(String d, int turns, boolean mirrorX) {
        int i = -1;

        for (int k = 0; k < 4; k++) {
            if (HORIZONTAL[k].equals(d)) {
                i = k;
            }
        }

        if (i < 0) {
            return d;
        } else {
            if (mirrorX && (i == 1 || i == 3)) {
                i = i == 1 ? 3 : 1;
            }

            return HORIZONTAL[i + turns & 3];
        }
    }

    public static final class Legacy {
        public final Block block;
        public final int meta;
        public final BlockState modState;

        Legacy(Block block, int meta, BlockState modState) {
            this.block = block;
            this.meta = meta;
            this.modState = modState;
        }
    }
}
