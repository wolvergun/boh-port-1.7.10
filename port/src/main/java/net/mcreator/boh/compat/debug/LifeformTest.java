package net.mcreator.boh.compat.debug;

import net.mcreator.boh.BohMod;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * A Lifeform spawned in Level 0 the way its egg spawns it (finalizeSpawn spreads it within 20 blocks), then followed
 * for 10 s: where it lands and whether it stays on the room floor.
 */
public final class LifeformTest {

    private final WorldServer w;
    private EntityLiving mob;
    /** A world without players stops updating entities: count a fake player in it, 40 blocks away, during the test. */
    private net.minecraftforge.common.util.FakePlayer watcher;
    private int tick;
    private final StringBuilder track = new StringBuilder();

    private LifeformTest(WorldServer w) {
        this.w = w;
    }

    private static void log(String s, Object... args) {
        BohMod.LOGGER.info("[BOH-SELFTEST] " + String.format(s, args));
    }

    /** x, z: the middle of an area of generated Level 0 chunks. */
    static void start(WorldServer w, int x, int z) {
        LifeformTest t = new LifeformTest(w);
        int y = 1;
        while (y < 20 && !(w.getBlock(x, y - 1, z).getMaterial().blocksMovement() && w.isAirBlock(x, y, z) && w.isAirBlock(x, y + 1, z))) y++;
        t.mob = (EntityLiving) EntityList.createEntityByName("boh.lifeform", w);
        if (t.mob == null) {
            log("lifeform: no entity");
            return;
        }
        t.forceChunks(x >> 4, z >> 4);
        t.mob.setLocationAndAngles(x + 0.5, y, z + 0.5, 0, 0);
        t.mob.onSpawnWithEgg(null);
        w.spawnEntityInWorld(t.mob);
        t.track.append(String.format(" spawned at %d %d %d;", x, y, z));
        t.watcher = net.minecraftforge.common.util.FakePlayerFactory.get(w,
            new com.mojang.authlib.GameProfile(java.util.UUID.fromString("b0b0b0b0-0000-4000-8000-000000000002"), "[BohSelfTest2]"));
        t.watcher.setPosition(x + 40.5, y, z + 0.5);
        w.playerEntities.add(t.watcher);
        FMLCommonHandler.instance().bus().register(t);
    }

    private final java.util.List<net.minecraftforge.common.ForgeChunkManager.Ticket> tickets = new java.util.ArrayList<>();

    /**
     * Forge unloads a dimension as soon as no chunk in it is forced and no real player is in it; keep the 7x7 chunks
     * around the spawn (spread within 20 blocks, plus the 2 chunks entities need around them to update) loaded.
     */
    private void forceChunks(int cx, int cz) {
        net.minecraftforge.common.ForgeChunkManager.setForcedChunkLoadingCallback(BohMod.instance,
            (net.minecraftforge.common.ForgeChunkManager.LoadingCallback) (ts, world) -> {
                for (net.minecraftforge.common.ForgeChunkManager.Ticket ticket : ts) net.minecraftforge.common.ForgeChunkManager.releaseTicket(ticket);
            });
        net.minecraftforge.common.ForgeChunkManager.Ticket ticket = null;
        for (int dx = -3; dx <= 3; dx++) for (int dz = -3; dz <= 3; dz++) {
            if (ticket == null || ticket.getChunkList().size() >= ticket.getChunkListDepth()) {
                ticket = net.minecraftforge.common.ForgeChunkManager.requestTicket(BohMod.instance, w, net.minecraftforge.common.ForgeChunkManager.Type.NORMAL);
                if (ticket == null) {
                    log("lifeform: no chunk ticket");
                    return;
                }
                tickets.add(ticket);
            }
            net.minecraftforge.common.ForgeChunkManager.forceChunk(ticket, new net.minecraft.world.ChunkCoordIntPair(cx + dx, cz + dz));
        }
    }

    private String at() {
        int x = MathHelper.floor_double(mob.posX), y = MathHelper.floor_double(mob.posY), z = MathHelper.floor_double(mob.posZ);
        return String.format("%.1f %.2f %.1f (feet %s, below %s, ground %s, dead %s)", mob.posX, mob.posY, mob.posZ, name(x, y, z), name(x, y - 1, z),
            mob.onGround, mob.isDead);
    }

    private String name(int x, int y, int z) {
        String n = Block.blockRegistry.getNameForObject(w.getBlock(x, y, z));
        return n == null ? "?" : n.replace("minecraft:", "").replace("boh:", "");
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        tick++;
        if (tick % 20 == 0 || tick == 3) track.append(String.format(" t%d: %s;", tick, at()));
        if (tick >= 200 || mob.isDead) {
            FMLCommonHandler.instance().bus().unregister(this);
            w.playerEntities.remove(watcher);
            for (net.minecraftforge.common.ForgeChunkManager.Ticket ticket : tickets) net.minecraftforge.common.ForgeChunkManager.releaseTicket(ticket);
            log("lifeform in Level 0:%s", track);
            mob.setDead();
        }
    }
}
