package net.mcreator.boh.compat.world;

import java.util.HashMap;
import java.util.Map;

import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/** Dimension id <-> 1.20 dimension key, plus cross-dimension player transfer. */
public final class Dimensions {

    private static final Map<Integer, ResourceKey<World>> BY_ID = new HashMap<>();
    private static final Map<ResourceKey<World>, Integer> BY_KEY = new HashMap<>();

    public static final ResourceKey<World> OVERWORLD = register(new ResourceLocation("minecraft", "overworld"), 0);
    public static final ResourceKey<World> NETHER = register(new ResourceLocation("minecraft", "the_nether"), -1);
    public static final ResourceKey<World> END = register(new ResourceLocation("minecraft", "the_end"), 1);

    private Dimensions() {}

    public static ResourceKey<World> register(ResourceLocation name, int id) {
        ResourceKey<World> key = ResourceKey.create(Registries.DIMENSION, name);
        BY_ID.put(id, key);
        BY_KEY.put(key, id);
        return key;
    }

    /** ResourceKey.create(Registries.DIMENSION, name) with the World type the mod compares against. */
    public static ResourceKey<World> dimensionKey(ResourceLocation name) {
        return ResourceKey.create(Registries.DIMENSION, name);
    }

    public static ResourceKey<World> key(int id) {
        ResourceKey<World> k = BY_ID.get(id);
        if (k == null) k = register(new ResourceLocation("minecraft", "dim" + id), id);
        return k;
    }

    public static int id(ResourceKey<?> key) {
        Integer i = BY_KEY.get(key);
        return i == null ? 0 : i;
    }

    /** Moves a player to another dimension and places them exactly at the given position (no portal search). */
    public static void transferPlayer(EntityPlayerMP player, int dim, double x, double y, double z, float yaw, float pitch) {
        MinecraftServer server = MinecraftServer.getServer();
        WorldServer target = server.worldServerForDimension(dim);
        if (target == null) return;
        double[] spot = safeArrival(target, x, y, z);
        if (spot != null) {
            x = spot[0];
            y = spot[1];
            z = spot[2];
        }
        final double fx = x, fy = y, fz = z;
        if (player.dimension != dim) {
            server.getConfigurationManager().transferPlayerToDimension(player, dim, new Teleporter(target) {

                @Override
                public void placeInPortal(net.minecraft.entity.Entity e, double px, double py, double pz, float rot) {
                    e.setLocationAndAngles(fx, fy, fz, yaw, pitch);
                    e.motionX = e.motionY = e.motionZ = 0;
                }

                @Override
                public boolean placeInExistingPortal(net.minecraft.entity.Entity e, double px, double py, double pz, float rot) {
                    placeInPortal(e, px, py, pz, rot);
                    return true;
                }

                @Override
                public boolean makePortal(net.minecraft.entity.Entity e) {
                    return true;
                }

                @Override
                public void removeStalePortalLocations(long time) {}
            });
        }
        player.playerNetServerHandler.setPlayerLocation(x, y, z, yaw, pitch);
    }

    /**
     * Into a mod dimension over nothing (void dimensions with rooms on a grid): the spot inside the nearest room,
     * else null to keep the requested position. Dimensions with a floor everywhere (Level 0) never need it.
     */
    private static double[] safeArrival(WorldServer target, double x, double y, double z) {
        if (!(target.provider instanceof net.mcreator.boh.compat.world.gen.BohWorldProvider)) return null;
        int bx = net.minecraft.util.MathHelper.floor_double(x), bz = net.minecraft.util.MathHelper.floor_double(z);
        target.getChunkFromBlockCoords(bx, bz);
        for (int by = Math.min(net.minecraft.util.MathHelper.floor_double(y), target.getActualHeight() - 1); by >= 0; by--)
            if (target.getBlock(bx, by, bz).getMaterial().blocksMovement()) return null;
        net.mcreator.boh.compat.world.gen.BohWorldProvider p = (net.mcreator.boh.compat.world.gen.BohWorldProvider) target.provider;
        return net.mcreator.boh.compat.world.gen.StructureSets.findArrival(target, p.spec().biome.key, x, z);
    }
}
