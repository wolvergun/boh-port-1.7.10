package net.mcreator.boh.compat.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public final class Selectors {
    private Selectors() {
    }

    public static List<Entity> resolve(String sel, CommandContext ctx) {
        List<Entity> out = new ArrayList<>();
        if (sel != null && !sel.isEmpty()) {
            if (sel.charAt(0) != '@') {
                for (Object o : ctx.world.playerEntities) {
                    if (((EntityPlayer)o).getCommandSenderName().equals(sel)) {
                        out.add((Entity)o);
                    }
                }

                return out;
            } else {
                char kind = sel.length() > 1 ? sel.charAt(1) : 101;
                Map<String, List<String>> args = parseArgs(sel);
                List<Entity> candidates;
                if (kind == 's') {
                    candidates = ctx.executor == null ? new ArrayList<>() : new ArrayList<>(Collections.singletonList(ctx.executor));
                } else if (kind == 'e') {
                    candidates = new ArrayList<>();

                    for (World w : worldsFor(ctx, args)) {
                        candidates.addAll(w.loadedEntityList);
                    }
                } else {
                    candidates = new ArrayList<>();

                    for (World w : worldsFor(ctx, args)) {
                        candidates.addAll(w.playerEntities);
                    }
                }

                Vec3 origin = ctx.pos;
                if (args.containsKey("x")) {
                    origin = new Vec3(Double.parseDouble(first(args, "x")), origin.y, origin.z);
                }

                if (args.containsKey("y")) {
                    origin = new Vec3(origin.x, Double.parseDouble(first(args, "y")), origin.z);
                }

                if (args.containsKey("z")) {
                    origin = new Vec3(origin.x, origin.y, Double.parseDouble(first(args, "z")));
                }

                for (Entity e : candidates) {
                    if (e.isEntityAlive() && matches(e, args, ctx, origin)) {
                        out.add(e);
                    }
                }

                String sort = first(args, "sort");
                if (sort == null) {
                    sort = kind == 'p' ? "nearest" : (kind == 'r' ? "random" : "arbitrary");
                }

                Vec3 ox = origin;
                Comparator<Entity> byDist = Comparator.comparingDouble(ex -> ex.getDistanceSq(o.x, o.y, o.z));
                if (sort.equals("nearest")) {
                    out.sort(byDist);
                } else if (sort.equals("furthest")) {
                    out.sort(byDist.reversed());
                } else if (sort.equals("random")) {
                    Collections.shuffle(out);
                }

                int limit = args.containsKey("limit") ? Integer.parseInt(first(args, "limit")) : (kind != 'p' && kind != 'r' ? Integer.MAX_VALUE : 1);
                if (out.size() > limit) {
                    out = new ArrayList<>(out.subList(0, limit));
                }

                return out;
            }
        } else {
            return out;
        }
    }

    private static List<World> worldsFor(CommandContext ctx, Map<String, List<String>> args) {
        List<World> l = new ArrayList<>();
        l.add(ctx.world);
        return l;
    }

    static String first(Map<String, List<String>> args, String k) {
        List<String> v = args.get(k);
        return v != null && !v.isEmpty() ? v.get(0) : null;
    }

    static Map<String, List<String>> parseArgs(String sel) {
        Map<String, List<String>> args = new HashMap<>();
        int b = sel.indexOf(91);
        if (b < 0) {
            return args;
        } else {
            String inner = sel.substring(b + 1, sel.lastIndexOf(93));
            int depth = 0;
            StringBuilder cur = new StringBuilder();
            List<String> parts = new ArrayList<>();

            for (char c : inner.toCharArray()) {
                if (c == '{' || c == '[') {
                    depth++;
                }

                if (c == '}' || c == ']') {
                    depth--;
                }

                if (c == ',' && depth == 0) {
                    parts.add(cur.toString());
                    cur.setLength(0);
                } else {
                    cur.append(c);
                }
            }

            if (cur.length() > 0) {
                parts.add(cur.toString());
            }

            for (String p : parts) {
                int eq = p.indexOf(61);
                if (eq >= 0) {
                    args.computeIfAbsent(p.substring(0, eq).trim(), k -> new ArrayList<>()).add(p.substring(eq + 1).trim());
                }
            }

            return args;
        }
    }

    private static boolean matches(Entity e, Map<String, List<String>> args, CommandContext ctx, Vec3 origin) {
        for (Entry<String, List<String>> a : args.entrySet()) {
            for (String v : a.getValue()) {
                boolean neg = v.startsWith("!");
                String val = neg ? v.substring(1) : v;
                String var11 = a.getKey();

                if (switch (var11) {
                        case "type" -> typeMatches(e, val);
                        case "distance" -> {
                            double d = Math.sqrt(e.getDistanceSq(origin.x, origin.y, origin.z));
                            yield inRange(d, val);
                        }
                        case "tag" -> val.isEmpty() ? tags(e).isEmpty() : tags(e).contains(val);
                        case "team" -> {
                            ScorePlayerTeam t = e.worldObj.getScoreboard().getPlayersTeam(M.getScoreboardName(e));
                            yield val.isEmpty() ? t == null : t != null && t.getRegisteredName().equals(val);
                        }
                        case "gamemode" -> e instanceof EntityPlayerMP && ((EntityPlayerMP)e).theItemInWorldManager.getGameType().getName().equals(val)
                            || e instanceof EntityPlayer
                                && !(e instanceof EntityPlayerMP)
                                && ((EntityPlayer)e).capabilities.isCreativeMode == val.equals("creative");
                        case "name" -> e.getCommandSenderName().equals(val);
                        case "dx", "dy", "dz" -> inVolume(e, args, origin);
                        case "level" -> e instanceof EntityPlayer && inRange(((EntityPlayer)e).experienceLevel, val);
                        default -> true;
                    }
                    == neg) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean inVolume(Entity e, Map<String, List<String>> args, Vec3 o) {
        double dx = args.containsKey("dx") ? Double.parseDouble(first(args, "dx")) : 0.0;
        double dy = args.containsKey("dy") ? Double.parseDouble(first(args, "dy")) : 0.0;
        double dz = args.containsKey("dz") ? Double.parseDouble(first(args, "dz")) : 0.0;
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
            Math.min(o.x, o.x + dx),
            Math.min(o.y, o.y + dy),
            Math.min(o.z, o.z + dz),
            Math.max(o.x, o.x + dx) + 1.0,
            Math.max(o.y, o.y + dy) + 1.0,
            Math.max(o.z, o.z + dz) + 1.0
        );
        return e.boundingBox.intersectsWith(box);
    }

    public static boolean inRange(double d, String range) {
        int dots = range.indexOf("..");
        if (dots < 0) {
            return Math.abs(d - Double.parseDouble(range)) < 1.0E-6;
        } else {
            String lo = range.substring(0, dots);
            String hi = range.substring(dots + 2);
            return !lo.isEmpty() && d < Double.parseDouble(lo) ? false : hi.isEmpty() || d <= Double.parseDouble(hi);
        }
    }

    static boolean typeMatches(Entity e, String type) {
        if (type.startsWith("#")) {
            TagKey<Object> tag = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(type.substring(1)));
            return tag.contains(EntityType.of(e).getId());
        } else {
            String full = type.contains(":") ? type : "minecraft:" + type;
            if (full.equals("minecraft:player")) {
                return e instanceof EntityPlayer;
            } else {
                ResourceLocation id = EntityType.of(e).getId();
                if (id != null && id.toString().equalsIgnoreCase(full)) {
                    return true;
                } else {
                    String legacy = EntityList.getEntityString(e);
                    return legacy != null && (legacy.equalsIgnoreCase(full.replace(':', '.')) || legacy.equalsIgnoreCase(full.substring(full.indexOf(58) + 1)));
                }
            }
        }
    }

    public static List<String> tags(Entity e) {
        List<String> l = new ArrayList<>();
        NBTTagList list = e.getEntityData().getTagList("boh_tags", 8);

        for (int i = 0; i < list.tagCount(); i++) {
            l.add(list.getStringTagAt(i));
        }

        return l;
    }

    public static void setTag(Entity e, String tag, boolean add) {
        List<String> cur = tags(e);
        if (add != cur.contains(tag)) {
            if (add) {
                cur.add(tag);
            } else {
                cur.remove(tag);
            }

            NBTTagList list = new NBTTagList();

            for (String s : cur) {
                list.appendTag(new NBTTagString(s));
            }

            e.getEntityData().setTag("boh_tags", list);
        }
    }
}
