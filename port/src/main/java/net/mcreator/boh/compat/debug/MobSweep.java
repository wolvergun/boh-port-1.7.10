package net.mcreator.boh.compat.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.mcreator.boh.BohMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * Every mod mob in its own walled pen with a pig it is told to attack: which ones land a hit, how soon, whether they
 * move, fall out of the pen or die, over 15 s. Finds mobs whose attack or movement is broken without testing each by
 * hand.
 */
public final class MobSweep {

    private static final int Y = 235, SPACING = 16, R = 5, TICKS = 300;

    private static final class Case {
        final String id;
        EntityLiving mob, pig;
        double startX, startZ, maxMove, minDy;
        int firstHit = -1, hits;
        String error;

        Case(String id) {
            this.id = id;
        }
    }

    private final WorldServer w;
    private final List<Case> cases = new ArrayList<>();
    private final List<ForgeChunkManager.Ticket> tickets = new ArrayList<>();
    private int tick;

    private MobSweep(WorldServer w) {
        this.w = w;
    }

    private static void log(String s, Object... args) {
        BohMod.LOGGER.info("[BOH-SELFTEST] " + String.format(s, args));
    }

    static void start(WorldServer w, int x0, int z0) {
        MobSweep t = new MobSweep(w);
        List<String> ids = new ArrayList<>();
        for (Object k : EntityList.stringToClassMapping.keySet()) {
            String id = (String) k;
            Class<?> c = (Class<?>) EntityList.stringToClassMapping.get(id);
            if (id.startsWith("boh.") && EntityCreature.class.isAssignableFrom(c)) ids.add(id);
        }
        java.util.Collections.sort(ids);
        int cols = (int) Math.ceil(Math.sqrt(ids.size()));
        t.force(x0, z0, cols);
        for (int i = 0; i < ids.size(); i++) {
            Case c = new Case(ids.get(i));
            t.cases.add(c);
            int x = x0 + (i % cols) * SPACING, z = z0 + (i / cols) * SPACING;
            try {
                t.pen(x, z);
                Entity m = EntityList.createEntityByName(c.id, w);
                Entity p = EntityList.createEntityByName("Pig", w);
                if (!(m instanceof EntityLiving)) {
                    c.error = "not created";
                    continue;
                }
                m.setLocationAndAngles(x - 2.5, Y + 1, z + 0.5, 0, 0);
                p.setLocationAndAngles(x + 3.5, Y + 1, z + 0.5, 0, 0);
                w.spawnEntityInWorld(m);
                w.spawnEntityInWorld(p);
                c.mob = (EntityLiving) m;
                c.pig = (EntityLiving) p;
                c.startX = m.posX;
                c.startZ = m.posZ;
            } catch (Throwable e) {
                c.error = e.toString();
            }
        }
        FMLCommonHandler.instance().bus().register(t);
        MinecraftForge.EVENT_BUS.register(t);
        log("mob sweep: %d mobs", ids.size());
    }

    /** Keep the whole grid loaded (spawn chunks only reach about 128 blocks). */
    private void force(int x0, int z0, int cols) {
        ForgeChunkManager.setForcedChunkLoadingCallback(BohMod.instance, (ForgeChunkManager.LoadingCallback) (ts, world) -> {
            for (ForgeChunkManager.Ticket ticket : ts) ForgeChunkManager.releaseTicket(ticket);
        });
        int span = cols * SPACING + 2 * SPACING;
        ForgeChunkManager.Ticket ticket = null;
        for (int cx = (x0 - SPACING) >> 4; cx <= (x0 + span) >> 4; cx++) for (int cz = (z0 - SPACING) >> 4; cz <= (z0 + span) >> 4; cz++) {
            if (ticket == null || ticket.getChunkList().size() >= ticket.getChunkListDepth()) {
                ticket = ForgeChunkManager.requestTicket(BohMod.instance, w, ForgeChunkManager.Type.NORMAL);
                if (ticket == null) return;
                tickets.add(ticket);
            }
            ForgeChunkManager.forceChunk(ticket, new ChunkCoordIntPair(cx, cz));
        }
    }

    private void pen(int x, int z) {
        for (int dx = -R - 1; dx <= R + 1; dx++) for (int dz = -R - 1; dz <= R + 1; dz++) {
            boolean wall = Math.abs(dx) == R + 1 || Math.abs(dz) == R + 1;
            w.setBlock(x + dx, Y, z + dz, Blocks.stone, 0, 2);
            for (int y = Y + 1; y < Y + 6; y++) w.setBlock(x + dx, y, z + dz, wall ? Blocks.glass : Blocks.air, 0, 2);
        }
    }

    @SubscribeEvent
    public void onAttack(LivingAttackEvent e) {
        Entity src = e.source.getEntity();
        if (src == null) return;
        for (Case c : cases) if (c.mob == src) {
            c.hits++;
            if (c.firstHit < 0) c.firstHit = tick;
            return;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        tick++;
        if (tick == 20 && !cases.isEmpty() && cases.get(0).mob != null) {
            EntityLiving m = cases.get(0).mob;
            log("mob sweep check: %d tickets, %d forced chunks, first mob ticksExisted %d, in world list %s, chunk loaded %s, dead %s",
                tickets.size(), w.getPersistentChunks().size(), m.ticksExisted, w.loadedEntityList.contains(m),
                w.getChunkProvider().chunkExists((int) Math.floor(m.posX) >> 4, (int) Math.floor(m.posZ) >> 4), m.isDead);
        }
        for (Case c : cases) {
            if (c.mob == null) continue;
            if (c.pig != null && !c.pig.isDead && c.mob.getAttackTarget() == null) c.mob.setAttackTarget(c.pig);
            double dx = c.mob.posX - c.startX, dz = c.mob.posZ - c.startZ;
            c.maxMove = Math.max(c.maxMove, Math.sqrt(dx * dx + dz * dz));
            c.minDy = Math.min(c.minDy, c.mob.posY - (Y + 1));
        }
        if (tick < TICKS) return;
        FMLCommonHandler.instance().bus().unregister(this);
        MinecraftForge.EVENT_BUS.unregister(this);
        Map<String, List<String>> groups = new TreeMap<>();
        for (Case c : cases) {
            String g;
            if (c.error != null) g = "error";
            else if (c.mob.isDead) g = c.firstHit >= 0 ? "hit, then died" : "died without hitting";
            else if (c.minDy < -1.5) g = "fell below the pen floor";
            else if (c.firstHit >= 0) g = "hit";
            else if (c.maxMove < 0.5) g = "no hit, never moved";
            else g = "no hit, moved";
            String d = c.id.replace("boh.", "") + (c.error != null ? "(" + c.error + ")"
                : c.firstHit >= 0 ? String.format("(t%d x%d)", c.firstHit, c.hits) : String.format("(moved %.1f)", c.maxMove));
            groups.computeIfAbsent(g, k -> new ArrayList<>()).add(d);
        }
        for (Map.Entry<String, List<String>> g : groups.entrySet())
            log("mob sweep %s (%d): %s", g.getKey(), g.getValue().size(), String.join(" ", g.getValue()));
        for (Case c : cases) {
            if (c.mob != null) c.mob.setDead();
            if (c.pig != null) c.pig.setDead();
        }
        for (ForgeChunkManager.Ticket ticket : tickets) ForgeChunkManager.releaseTicket(ticket);
    }
}
