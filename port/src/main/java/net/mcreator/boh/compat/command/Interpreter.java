package net.mcreator.boh.compat.command;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.mcreator.boh.compat.mc.tags.BlockTags;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.net.CompatNetwork;
import net.mcreator.boh.compat.registry.LegacyIds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * Interpreter for the 1.20 command syntax the mod and its datapack functions issue (MCreator procedures build
 * command strings; 1.7.10's command system knows none of these forms).
 */
public final class Interpreter {

    private static final Map<String, List<String>> FUNCTIONS = new HashMap<>();
    private static int depth;

    private Interpreter() {}

    public static int run(String command, CommandContext ctx) {
        if (command == null) return 0;
        String c = command.trim();
        if (c.startsWith("/")) c = c.substring(1);
        if (c.isEmpty() || ctx.world == null || ctx.world.isRemote) return 0;
        if (depth > 512) return 0;
        depth++;
        try {
            return dispatch(tokenize(c), 0, ctx);
        } catch (RuntimeException e) {
            BohMod.LOGGER.debug("Command failed: {} ({})", command, e.toString());
            return 0;
        } finally {
            depth--;
        }
    }

    // ------------------------------------------------------------------ tokenizer

    static List<String> tokenize(String s) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        int depthBraces = 0;
        boolean quote = false;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '"' && (i == 0 || s.charAt(i - 1) != '\\')) quote = !quote;
            if (!quote) {
                if (ch == '{' || ch == '[') depthBraces++;
                if (ch == '}' || ch == ']') depthBraces--;
            }
            if (ch == ' ' && depthBraces == 0 && !quote) {
                if (cur.length() > 0) out.add(cur.toString());
                cur.setLength(0);
            } else cur.append(ch);
        }
        if (cur.length() > 0) out.add(cur.toString());
        return out;
    }

    private static String rest(List<String> t, int from) {
        return from >= t.size() ? "" : String.join(" ", t.subList(from, t.size()));
    }

    // ------------------------------------------------------------------ coordinates

    /** Parses three coordinate tokens (absolute, ~relative or ^local) starting at {@code i}. */
    static Vec3 pos(List<String> t, int i, CommandContext ctx) {
        String a = t.get(i), b = t.get(i + 1), c = t.get(i + 2);
        if (a.startsWith("^")) {
            double left = num(a.substring(1)), up = num(b.substring(1)), fwd = num(c.substring(1));
            Vec3 base = ctx.anchorPos();
            float yaw = ctx.yaw, pitch = ctx.pitch;
            double rad = Math.PI / 180.0;
            double f = Math.cos((yaw + 90.0) * rad), f1 = Math.sin((yaw + 90.0) * rad);
            double f2 = Math.cos(-pitch * rad), f3 = Math.sin(-pitch * rad);
            double f4 = Math.cos((-pitch + 90.0) * rad), f5 = Math.sin((-pitch + 90.0) * rad);
            Vec3 forward = new Vec3(f * f2, f3, f1 * f2), upv = new Vec3(f * f4, f5, f1 * f4);
            Vec3 leftv = forward.cross(upv).scale(-1.0);
            return new Vec3(base.x + forward.x * fwd + upv.x * up + leftv.x * left, base.y + forward.y * fwd + upv.y * up + leftv.y * left,
                base.z + forward.z * fwd + upv.z * up + leftv.z * left);
        }
        return new Vec3(coord(a, ctx.pos.x), coord(b, ctx.pos.y), coord(c, ctx.pos.z));
    }

    private static double coord(String s, double base) {
        if (s.startsWith("~")) return base + num(s.substring(1));
        return Double.parseDouble(s);
    }

    private static double num(String s) {
        return s.isEmpty() ? 0 : Double.parseDouble(s);
    }

    private static boolean isCoord(String s) {
        return s.startsWith("~") || s.startsWith("^") || s.matches("-?[0-9.]+");
    }

    private static float rot(String s, float base) {
        if (s.startsWith("~")) return base + (float) num(s.substring(1));
        return Float.parseFloat(s);
    }

    // ------------------------------------------------------------------ dispatch

    private static int dispatch(List<String> t, int i, CommandContext ctx) {
        if (i >= t.size()) return 0;
        String cmd = t.get(i);
        if (cmd.contains(":") && !cmd.startsWith("minecraft:")) return 0;
        switch (cmd.replace("minecraft:", "")) {
            case "execute":
                return execute(t, i + 1, ctx);
            case "particle":
                return particle(t, i + 1, ctx);
            case "playsound":
                return playsound(t, i + 1, ctx);
            case "stopsound":
                return stopsound(t, i + 1, ctx);
            case "effect":
                return effect(t, i + 1, ctx);
            case "team":
                return team(t, i + 1, ctx);
            case "scoreboard":
                return scoreboard(t, i + 1, ctx);
            case "spreadplayers":
                return spreadplayers(t, i + 1, ctx);
            case "fill":
                return fill(t, i + 1, ctx);
            case "setblock":
                return setblock(t, i + 1, ctx);
            case "tp":
            case "teleport":
                return tp(t, i + 1, ctx);
            case "tellraw":
                return tellraw(t, i + 1, ctx);
            case "kill":
                return kill(t, i + 1, ctx);
            case "tag":
                return tag(t, i + 1, ctx);
            case "summon":
                return summon(t, i + 1, ctx);
            case "attribute":
                return attribute(t, i + 1, ctx);
            case "damage":
                return damage(t, i + 1, ctx);
            case "advancement":
                return advancement(t, i + 1, ctx);
            case "say":
                return say(t, i + 1, ctx);
            case "function":
                return function(t.get(i + 1), ctx);
            case "time":
                if (t.get(i + 1).equals("set")) ctx.world.setWorldTime(parseTime(t.get(i + 2)));
                return 1;
            case "weather":
                ctx.world.getWorldInfo().setRaining(!t.get(i + 1).equals("clear"));
                ctx.world.getWorldInfo().setThundering(t.get(i + 1).equals("thunder"));
                return 1;
            case "gamemode":
                for (Entity e : Selectors.resolve(i + 2 < t.size() ? t.get(i + 2) : "@s", ctx))
                    if (e instanceof EntityPlayerMP) ((EntityPlayerMP) e).setGameType(gameType(t.get(i + 1)));
                return 1;
            default:
                return 0;
        }
    }

    private static long parseTime(String s) {
        switch (s) {
            case "day":
                return 1000;
            case "night":
                return 13000;
            case "noon":
                return 6000;
            case "midnight":
                return 18000;
            default:
                return Long.parseLong(s);
        }
    }

    // ------------------------------------------------------------------ execute

    private static int execute(List<String> t, int i, CommandContext ctx) {
        if (i >= t.size()) return 0;
        String sub = t.get(i);
        switch (sub) {
            case "run":
                return dispatch(t, i + 1, ctx);
            case "as": {
                int n = 0;
                for (Entity e : Selectors.resolve(t.get(i + 1), ctx)) n += execute(t, i + 2, ctx.as(e));
                return n;
            }
            case "at": {
                int n = 0;
                for (Entity e : Selectors.resolve(t.get(i + 1), ctx)) n += execute(t, i + 2, ctx.at(e));
                return n;
            }
            case "positioned":
                if (t.get(i + 1).equals("as")) {
                    int n = 0;
                    for (Entity e : Selectors.resolve(t.get(i + 2), ctx))
                        n += execute(t, i + 3, ctx.positioned(new Vec3(e.posX, M.getY(e), e.posZ)));
                    return n;
                }
                return execute(t, i + 4, ctx.positioned(pos(t, i + 1, ctx)));
            case "rotated":
                if (t.get(i + 1).equals("as")) {
                    int n = 0;
                    for (Entity e : Selectors.resolve(t.get(i + 2), ctx)) n += execute(t, i + 3, ctx.rotated(e.rotationYaw, e.rotationPitch));
                    return n;
                }
                return execute(t, i + 3, ctx.rotated(rot(t.get(i + 1), ctx.yaw), rot(t.get(i + 2), ctx.pitch)));
            case "facing":
                if (t.get(i + 1).equals("entity")) {
                    int n = 0;
                    boolean eyes = t.get(i + 3).equals("eyes");
                    for (Entity e : Selectors.resolve(t.get(i + 2), ctx)) {
                        Vec3 target = new Vec3(e.posX, M.getY(e) + (eyes ? M.getEyeHeight(e) : 0), e.posZ);
                        n += execute(t, i + 4, face(ctx, target));
                    }
                    return n;
                }
                return execute(t, i + 4, face(ctx, pos(t, i + 1, ctx)));
            case "anchored":
                return execute(t, i + 2, ctx.anchored(t.get(i + 1).equals("eyes")));
            case "in":
                return execute(t, i + 2, ctx);
            case "if":
            case "unless": {
                boolean want = sub.equals("if");
                String kind = t.get(i + 1);
                if (kind.equals("entity")) {
                    boolean found = !Selectors.resolve(t.get(i + 2), ctx).isEmpty();
                    return found == want ? execute(t, i + 3, ctx) : 0;
                }
                if (kind.equals("block")) {
                    Vec3 p = pos(t, i + 2, ctx);
                    boolean match = blockMatches(ctx.world, p, t.get(i + 5));
                    return match == want ? execute(t, i + 6, ctx) : 0;
                }
                return 0;
            }
            default:
                return 0;
        }
    }

    private static CommandContext face(CommandContext ctx, Vec3 target) {
        Vec3 from = ctx.anchorPos();
        double dx = target.x - from.x, dy = target.y - from.y, dz = target.z - from.z;
        double h = Math.sqrt(dx * dx + dz * dz);
        float pitch = (float) -(Math.atan2(dy, h) * 180.0 / Math.PI);
        float yaw = (float) (Math.atan2(dz, dx) * 180.0 / Math.PI) - 90.0F;
        return ctx.rotated(yaw, pitch);
    }

    private static boolean blockMatches(World w, Vec3 p, String spec) {
        int x = (int) Math.floor(p.x), y = (int) Math.floor(p.y), z = (int) Math.floor(p.z);
        Block b = w.getBlock(x, y, z);
        if (spec.startsWith("#")) {
            ResourceLocation tag = new ResourceLocation(spec.substring(1));
            ResourceLocation id = LegacyIds.blockKey(b);
            if (BlockTags.create(tag).contains(id)) return true;
            // vanilla blocks show up under their 1.7.10 names; treat see-through ones as "transparent"
            return tag.getResourcePath().equals("transparent") && !b.isOpaqueCube();
        }
        LegacyIds.Target target = LegacyIds.target(spec.contains("[") ? spec.substring(0, spec.indexOf('[')) : spec);
        return target != null && target.block() == b;
    }

    // ------------------------------------------------------------------ commands

    private static int particle(List<String> t, int i, CommandContext ctx) {
        if (!(ctx.world instanceof WorldServer)) return 0;
        String type = t.get(i).replace("minecraft:", "");
        int j = i + 1;
        String legacy = null;
        ParticleType<?> mod = null;
        if (type.equals("block") || type.equals("block_marker") || type.equals("falling_dust")) {
            LegacyIds.Target b = LegacyIds.target(t.get(j));
            Block blk = b == null ? null : b.block();
            legacy = "blockcrack_" + Block.getIdFromBlock(blk == null ? net.minecraft.init.Blocks.redstone_block : blk) + "_" + (b == null ? 0 : b.meta);
            j++;
        } else if (type.equals("dust")) {
            legacy = "reddust";
            j += 4;
        } else if (type.contains(":")) {
            mod = ForgeRegistries.PARTICLE_TYPES.getValue(new ResourceLocation(type));
        } else {
            legacy = ParticleNames.legacy(type);
        }
        Vec3 p = j + 2 < t.size() && isCoord(t.get(j)) ? pos(t, j, ctx) : ctx.pos;
        j += 3;
        double dx = 0, dy = 0, dz = 0, speed = 0;
        int count = 1;
        if (t.size() >= j + 4) {
            dx = Double.parseDouble(t.get(j));
            dy = Double.parseDouble(t.get(j + 1));
            dz = Double.parseDouble(t.get(j + 2));
            speed = Double.parseDouble(t.get(j + 3));
            count = j + 4 < t.size() ? Integer.parseInt(t.get(j + 4)) : 1;
        }
        if (mod != null) CompatNetwork.sendParticles((WorldServer) ctx.world, mod, p.x, p.y, p.z, count, dx, dy, dz, speed);
        else if (legacy != null) ((WorldServer) ctx.world).func_147487_a(legacy, p.x, p.y, p.z, Math.max(count, 1), dx, dy, dz, speed);
        return 1;
    }

    private static int playsound(List<String> t, int i, CommandContext ctx) {
        net.mcreator.boh.compat.mc.sounds.SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(t.get(i)));
        if (sound == null || sound.legacyName().isEmpty()) return 0;
        List<Entity> targets = i + 2 < t.size() ? Selectors.resolve(t.get(i + 2), ctx) : Collections.singletonList(ctx.executor);
        Vec3 p = t.size() > i + 5 ? pos(t, i + 3, ctx) : ctx.pos;
        float vol = t.size() > i + 6 ? Float.parseFloat(t.get(i + 6)) : 1.0F;
        float pitch = t.size() > i + 7 ? Float.parseFloat(t.get(i + 7)) : 1.0F;
        int n = 0;
        for (Entity e : targets) {
            if (!(e instanceof EntityPlayerMP)) continue;
            EntityPlayerMP pl = (EntityPlayerMP) e;
            double sx = p.x, sy = p.y, sz = p.z;
            double d = pl.getDistanceSq(sx, sy, sz);
            double range = vol > 1 ? 16 * vol : 16;
            if (d > range * range) {
                Vec3 dir = new Vec3(sx - pl.posX, sy - pl.posY, sz - pl.posZ).normalize();
                sx = pl.posX + dir.x * 2;
                sy = pl.posY + dir.y * 2;
                sz = pl.posZ + dir.z * 2;
            }
            pl.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(sound.legacyName(), sx, sy, sz, vol, pitch));
            n++;
        }
        return n;
    }

    private static int stopsound(List<String> t, int i, CommandContext ctx) {
        String sound = i + 2 < t.size() ? t.get(i + 2) : "";
        String legacy = sound.isEmpty() ? "" : ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(sound)).legacyName();
        int n = 0;
        for (Entity e : Selectors.resolve(t.get(i), ctx)) {
            if (e instanceof EntityPlayerMP) {
                CompatNetwork.sendStopSound((EntityPlayerMP) e, legacy);
                n++;
            }
        }
        return n;
    }

    public static Potion effectById(String id) {
        ResourceLocation rl = new ResourceLocation(id.contains(":") ? id : "minecraft:" + id);
        return ForgeRegistries.MOB_EFFECTS.getValue(rl);
    }

    private static int effect(List<String> t, int i, CommandContext ctx) {
        String mode = t.get(i);
        List<Entity> targets = Selectors.resolve(t.get(i + 1), ctx);
        if (mode.equals("clear")) {
            Potion p = i + 2 < t.size() ? effectById(t.get(i + 2)) : null;
            for (Entity e : targets) {
                if (!(e instanceof EntityLivingBase)) continue;
                if (p == null) ((EntityLivingBase) e).clearActivePotions();
                else ((EntityLivingBase) e).removePotionEffect(p.id);
            }
            return targets.size();
        }
        Potion p = effectById(t.get(i + 2));
        if (p == null) return 0;
        int seconds = i + 3 < t.size() ? (t.get(i + 3).equals("infinite") ? 1000000 : Integer.parseInt(t.get(i + 3))) : 30;
        if (seconds < 1) return 0; // 1.20 rejects 0 seconds, so such commands never applied in the original either
        int amp = i + 4 < t.size() ? Integer.parseInt(t.get(i + 4)) : 0;
        boolean hide = i + 5 < t.size() && t.get(i + 5).equals("true");
        for (Entity e : targets)
            if (e instanceof EntityLivingBase) ((EntityLivingBase) e).addPotionEffect(new PotionEffect(p.id, p.isInstant() ? 1 : seconds * 20, amp, hide));
        return targets.size();
    }

    private static int team(List<String> t, int i, CommandContext ctx) {
        Scoreboard sb = ctx.world.getScoreboard();
        switch (t.get(i)) {
            case "add":
                if (sb.getTeam(t.get(i + 1)) == null) sb.createTeam(t.get(i + 1));
                return 1;
            case "remove": {
                ScorePlayerTeam team = sb.getTeam(t.get(i + 1));
                if (team != null) sb.removeTeam(team);
                return 1;
            }
            case "join": {
                ScorePlayerTeam team = sb.getTeam(t.get(i + 1));
                if (team == null) return 0;
                List<Entity> es = i + 2 < t.size() ? Selectors.resolve(t.get(i + 2), ctx) : Collections.singletonList(ctx.executor);
                for (Entity e : es) sb.func_151392_a(M.getScoreboardName(e), team.getRegisteredName());
                return es.size();
            }
            case "leave": {
                List<Entity> es = Selectors.resolve(t.get(i + 1), ctx);
                for (Entity e : es) sb.removePlayerFromTeams(M.getScoreboardName(e));
                return es.size();
            }
            case "modify": {
                ScorePlayerTeam team = sb.getTeam(t.get(i + 1));
                if (team != null && t.get(i + 2).equals("friendlyFire")) team.setAllowFriendlyFire(Boolean.parseBoolean(t.get(i + 3)));
                return 1;
            }
            default:
                return 0;
        }
    }

    private static int scoreboard(List<String> t, int i, CommandContext ctx) {
        Scoreboard sb = ctx.world.getScoreboard();
        if (t.get(i).equals("objectives") && t.get(i + 1).equals("add")) {
            if (sb.getObjective(t.get(i + 2)) == null) sb.addScoreObjective(t.get(i + 2), net.minecraft.scoreboard.IScoreObjectiveCriteria.field_96641_b);
            return 1;
        }
        if (t.get(i).equals("players")) {
            String op = t.get(i + 1);
            ScoreObjective obj = sb.getObjective(t.get(i + 3));
            if (obj == null) obj = sb.addScoreObjective(t.get(i + 3), net.minecraft.scoreboard.IScoreObjectiveCriteria.field_96641_b);
            int v = i + 4 < t.size() ? Integer.parseInt(t.get(i + 4)) : 0;
            for (Entity e : Selectors.resolve(t.get(i + 2), ctx)) {
                net.minecraft.scoreboard.Score s = sb.func_96529_a(M.getScoreboardName(e), obj);
                if (op.equals("set")) s.setScorePoints(v);
                else if (op.equals("add")) s.increseScore(v);
                else if (op.equals("remove")) s.decreaseScore(v);
                else if (op.equals("reset")) sb.func_96510_d(M.getScoreboardName(e)).remove(obj);
            }
            return 1;
        }
        return 0;
    }

    private static int spreadplayers(List<String> t, int i, CommandContext ctx) {
        double cx = coord(t.get(i), ctx.pos.x), cz = coord(t.get(i + 1), ctx.pos.z);
        double maxRange = Double.parseDouble(t.get(i + 3));
        int j = i + 4;
        int maxHeight = 256;
        if (t.get(j).equals("under")) {
            maxHeight = Integer.parseInt(t.get(j + 1));
            j += 2;
        }
        j++; // respectTeams
        List<Entity> targets = Selectors.resolve(t.get(j), ctx);
        Random r = ctx.world.rand;
        for (Entity e : targets) {
            for (int attempt = 0; attempt < 20; attempt++) {
                int x = (int) Math.floor(cx + (r.nextDouble() * 2 - 1) * maxRange), z = (int) Math.floor(cz + (r.nextDouble() * 2 - 1) * maxRange);
                int y = Math.min(maxHeight, ctx.world.getActualHeight() - 1);
                if (ctx.world.provider instanceof net.mcreator.boh.compat.world.gen.BohWorldProvider
                    && ((net.mcreator.boh.compat.world.gen.BohWorldProvider) ctx.world.provider).spec().floor != null) {
                    // floored mod dimensions (Level 0) are a maze under one roof: 1.20 would drop the mob on the roof,
                    // which looks like it sinks into the ceiling; use the walkable floor level instead
                    int f = 1;
                    while (f < y && !(ctx.world.getBlock(x, f - 1, z).getMaterial().blocksMovement() && ctx.world.isAirBlock(x, f, z)
                        && ctx.world.isAirBlock(x, f + 1, z))) f++;
                    if (f >= y) continue;
                    M.teleportTo(e, x + 0.5, f, z + 0.5);
                    break;
                }
                while (y > 1 && ctx.world.isAirBlock(x, y, z)) y--;
                while (y > 1 && !ctx.world.isAirBlock(x, y, z) && maxHeight < 256) {
                    // under <maxHeight>: find a free spot below the ceiling
                    if (ctx.world.isAirBlock(x, y + 1, z) && ctx.world.isAirBlock(x, y + 2, z)) break;
                    y--;
                }
                if (y <= 1 || ctx.world.getBlock(x, y, z).getMaterial().isLiquid()) continue;
                M.teleportTo(e, x + 0.5, y + 1, z + 0.5);
                break;
            }
        }
        return targets.size();
    }

    private static int fill(List<String> t, int i, CommandContext ctx) {
        Vec3 a = pos(t, i, ctx), b = pos(t, i + 3, ctx);
        LegacyIds.Target with = LegacyIds.target(t.get(i + 6));
        if (with == null) return 0;
        String mode = i + 7 < t.size() ? t.get(i + 7) : "replace";
        String filter = mode.equals("replace") && i + 8 < t.size() ? t.get(i + 8) : null;
        int x0 = (int) Math.floor(Math.min(a.x, b.x)), x1 = (int) Math.floor(Math.max(a.x, b.x));
        int y0 = (int) Math.floor(Math.min(a.y, b.y)), y1 = (int) Math.floor(Math.max(a.y, b.y));
        int z0 = (int) Math.floor(Math.min(a.z, b.z)), z1 = (int) Math.floor(Math.max(a.z, b.z));
        int n = 0;
        for (int x = x0; x <= x1; x++) for (int y = y0; y <= y1; y++) for (int z = z0; z <= z1; z++) {
            if (filter != null && !blockMatches(ctx.world, new Vec3(x, y, z), filter)) continue;
            if (mode.equals("keep") && !ctx.world.isAirBlock(x, y, z)) continue;
            if (mode.equals("destroy")) ctx.world.func_147480_a(x, y, z, true);
            ctx.world.setBlock(x, y, z, with.block(), with.meta, 3);
            n++;
        }
        return n;
    }

    private static int setblock(List<String> t, int i, CommandContext ctx) {
        Vec3 p = pos(t, i, ctx);
        LegacyIds.Target with = LegacyIds.target(t.get(i + 3));
        if (with == null) return 0;
        ctx.world.setBlock((int) Math.floor(p.x), (int) Math.floor(p.y), (int) Math.floor(p.z), with.block(), with.meta, 3);
        return 1;
    }

    private static int tp(List<String> t, int i, CommandContext ctx) {
        List<Entity> targets;
        int j;
        if (t.size() - i == 1 || t.size() - i == 3 && isCoord(t.get(i))) {
            targets = Collections.singletonList(ctx.executor);
            j = i;
        } else {
            targets = Selectors.resolve(t.get(i), ctx);
            j = i + 1;
        }
        if (j >= t.size()) return 0;
        if (!isCoord(t.get(j))) {
            List<Entity> dest = Selectors.resolve(t.get(j), ctx);
            if (dest.isEmpty()) return 0;
            Entity d = dest.get(0);
            for (Entity e : targets) {
                if (e == null) continue;
                e.rotationYaw = d.rotationYaw;
                e.rotationPitch = d.rotationPitch;
                M.teleportTo(e, d.posX, M.getY(d), d.posZ);
            }
            return targets.size();
        }
        int n = 0;
        for (Entity e : targets) {
            if (e == null) continue;
            CommandContext ectx = ctx;
            Vec3 p = pos(t, j, ectx);
            if (t.size() > j + 4) {
                e.rotationYaw = rot(t.get(j + 3), e.rotationYaw);
                e.rotationPitch = rot(t.get(j + 4), e.rotationPitch);
            }
            M.teleportTo(e, p.x, p.y, p.z);
            n++;
        }
        return n;
    }

    private static int tellraw(List<String> t, int i, CommandContext ctx) {
        IChatComponent msg;
        try {
            msg = IChatComponent.Serializer.func_150699_a(rest(t, i + 1));
        } catch (RuntimeException e) {
            msg = new ChatComponentText(rest(t, i + 1));
        }
        int n = 0;
        for (Entity e : Selectors.resolve(t.get(i), ctx)) {
            if (e instanceof EntityPlayer) {
                ((EntityPlayer) e).addChatComponentMessage(msg);
                n++;
            }
        }
        return n;
    }

    private static int kill(List<String> t, int i, CommandContext ctx) {
        List<Entity> targets = i < t.size() ? Selectors.resolve(t.get(i), ctx) : Collections.singletonList(ctx.executor);
        for (Entity e : targets) M.kill(e);
        return targets.size();
    }

    private static int tag(List<String> t, int i, CommandContext ctx) {
        List<Entity> targets = Selectors.resolve(t.get(i), ctx);
        boolean add = t.get(i + 1).equals("add");
        for (Entity e : targets) Selectors.setTag(e, t.get(i + 2), add);
        return targets.size();
    }

    private static int summon(List<String> t, int i, CommandContext ctx) {
        String id = t.get(i).contains(":") ? t.get(i) : "minecraft:" + t.get(i);
        Vec3 p = t.size() > i + 3 && isCoord(t.get(i + 1)) ? pos(t, i + 1, ctx) : ctx.pos;
        String nbt = i + 4 < t.size() ? t.get(i + 4) : "";
        Entity e = Summons.create(id, ctx.world, p, nbt);
        if (e == null) return 0;
        M.addFreshEntity(ctx.world, e);
        return 1;
    }

    private static int attribute(List<String> t, int i, CommandContext ctx) {
        String attr = t.get(i + 1);
        if (!t.get(i + 2).equals("base") || !t.get(i + 3).equals("set")) return 0;
        double v = Double.parseDouble(t.get(i + 4));
        int n = 0;
        for (Entity e : Selectors.resolve(t.get(i), ctx)) {
            if (!(e instanceof EntityLivingBase)) continue;
            IAttributeInstance inst = ((EntityLivingBase) e).getAttributeMap().getAttributeInstanceByName(Summons.attributeName(attr));
            if (inst != null) {
                inst.setBaseValue(v);
                n++;
            }
        }
        return n;
    }

    private static int damage(List<String> t, int i, CommandContext ctx) {
        float amount = Float.parseFloat(t.get(i + 1));
        String type = i + 2 < t.size() ? t.get(i + 2) : "minecraft:generic";
        Entity by = null;
        if (i + 4 < t.size() && t.get(i + 3).equals("by")) {
            List<Entity> l = Selectors.resolve(t.get(i + 4), ctx);
            if (!l.isEmpty()) by = l.get(0);
        }
        DamageSource src = net.mcreator.boh.compat.mc.world.damagesource.DamageSources.create(
            net.mcreator.boh.compat.mc.resources.ResourceKey.create(net.mcreator.boh.compat.mc.core.registries.Registries.DAMAGE_TYPE,
                new ResourceLocation(type)),
            by);
        int n = 0;
        for (Entity e : Selectors.resolve(t.get(i), ctx)) if (e.attackEntityFrom(src, amount)) n++;
        return n;
    }

    private static int advancement(List<String> t, int i, CommandContext ctx) {
        if (!t.get(i).equals("grant") || !t.get(i + 2).equals("only")) return 0;
        ResourceLocation adv = new ResourceLocation(t.get(i + 3));
        int n = 0;
        for (Entity e : Selectors.resolve(t.get(i + 1), ctx)) {
            if (e instanceof EntityPlayerMP) {
                net.mcreator.boh.compat.advancement.Advancements.grant((EntityPlayerMP) e, adv.toString());
                n++;
            }
        }
        return n;
    }

    private static int say(List<String> t, int i, CommandContext ctx) {
        String name = ctx.executor == null ? "Server" : ctx.executor.getCommandSenderName();
        IChatComponent msg = new ChatComponentText("[" + name + "] " + rest(t, i));
        if (ctx.server != null) ctx.server.getConfigurationManager().sendChatMsg(msg);
        return 1;
    }

    private static synchronized int function(String id, CommandContext ctx) {
        List<String> lines = FUNCTIONS.get(id);
        if (lines == null) {
            lines = new ArrayList<>();
            ResourceLocation rl = new ResourceLocation(id);
            try (InputStream in = Interpreter.class.getResourceAsStream("/data/" + rl.getResourceDomain() + "/functions/" + rl.getResourcePath() + ".mcfunction")) {
                if (in != null) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                    String l;
                    while ((l = r.readLine()) != null) {
                        l = l.trim();
                        if (!l.isEmpty() && !l.startsWith("#")) lines.add(l);
                    }
                }
            } catch (Exception ignored) {}
            FUNCTIONS.put(id, lines);
        }
        int n = 0;
        for (String l : lines) n += run(l, ctx);
        return n;
    }

    /** WorldSettings.GameType.getByName is client-only. */
    private static net.minecraft.world.WorldSettings.GameType gameType(String name) {
        for (net.minecraft.world.WorldSettings.GameType g : net.minecraft.world.WorldSettings.GameType.values())
            if (g.getName().equals(name) || String.valueOf(g.getID()).equals(name)) return g;
        return net.minecraft.world.WorldSettings.GameType.SURVIVAL;
    }
}
