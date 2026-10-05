package net.mcreator.boh.compat.entity;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * Smooth short moves for entities whose 1.20 procedures "teleport" them in small hops: the Saucer's random +-5 block
 * hops become a 1 s glide (eased), moved a bit every server tick so clients see ordinary smooth movement.
 */
public final class Glides {

    private static final int TICKS = 20;
    private static final Map<Entity, double[]> ACTIVE = new WeakHashMap<>();
    private static boolean installed;

    private Glides() {}

    /** Whether a teleport of this entity to (x, y, z) should glide instead. */
    public static boolean glides(Entity e, double x, double y, double z) {
        if (e.worldObj.isRemote || !"boh.saucer".equals(EntityList.getEntityString(e))) return false;
        double dx = x - e.posX, dy = y - e.posY, dz = z - e.posZ;
        return dx * dx + dy * dy + dz * dz < 16 * 16;
    }

    public static void start(Entity e, double x, double y, double z) {
        if (!installed) {
            installed = true;
            FMLCommonHandler.instance().bus().register(new Glides());
        }
        ACTIVE.put(e, new double[] { e.posX, e.posY, e.posZ, x, y, z, 0 });
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent ev) {
        if (ev.phase != TickEvent.Phase.END) return;
        for (Iterator<Map.Entry<Entity, double[]>> it = ACTIVE.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Entity, double[]> en = it.next();
            Entity e = en.getKey();
            double[] g = en.getValue();
            if (e == null || e.isDead) {
                it.remove();
                continue;
            }
            double t = ++g[6] / TICKS;
            double s = t * t * (3 - 2 * t);
            e.setPosition(g[0] + (g[3] - g[0]) * s, g[1] + (g[4] - g[1]) * s, g[2] + (g[5] - g[2]) * s);
            e.motionX = e.motionY = e.motionZ = 0;
            if (t >= 1) it.remove();
        }
    }
}
