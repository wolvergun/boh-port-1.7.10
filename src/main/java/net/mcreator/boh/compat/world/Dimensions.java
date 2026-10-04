package net.mcreator.boh.compat.world;

import java.util.HashMap;
import java.util.Map;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public final class Dimensions {
    private static final Map<Integer, ResourceKey<World>> BY_ID = new HashMap<>();
    private static final Map<ResourceKey<World>, Integer> BY_KEY = new HashMap<>();
    public static final ResourceKey<World> OVERWORLD = register(new ResourceLocation("minecraft", "overworld"), 0);
    public static final ResourceKey<World> NETHER = register(new ResourceLocation("minecraft", "the_nether"), -1);
    public static final ResourceKey<World> END = register(new ResourceLocation("minecraft", "the_end"), 1);

    private Dimensions() {
    }

    public static ResourceKey<World> register(ResourceLocation name, int id) {
        ResourceKey<World> key = ResourceKey.create(Registries.DIMENSION, name);
        BY_ID.put(id, key);
        BY_KEY.put(key, id);
        return key;
    }

    public static ResourceKey<World> dimensionKey(ResourceLocation name) {
        return ResourceKey.create(Registries.DIMENSION, name);
    }

    public static ResourceKey<World> key(int id) {
        ResourceKey<World> k = BY_ID.get(id);
        if (k == null) {
            k = register(new ResourceLocation("minecraft", "dim" + id), id);
        }

        return k;
    }

    public static int id(ResourceKey<?> key) {
        Integer i = BY_KEY.get(key);
        return i == null ? 0 : i;
    }

    public static void transferPlayer(EntityPlayerMP player, int dim, final double x, final double y, final double z, final float yaw, final float pitch) {
        MinecraftServer server = MinecraftServer.getServer();
        WorldServer target = server.worldServerForDimension(dim);
        if (target != null) {
            if (player.dimension != dim) {
                server.getConfigurationManager().transferPlayerToDimension(player, dim, new Teleporter(target) {
                    public void placeInPortal(Entity e, double px, double py, double pz, float rot) {
                        e.setLocationAndAngles(x, y, z, yaw, pitch);
                        e.motionX = e.motionY = e.motionZ = 0.0;
                    }

                    public boolean placeInExistingPortal(Entity e, double px, double py, double pz, float rot) {
                        this.placeInPortal(e, px, py, pz, rot);
                        return true;
                    }

                    public boolean makePortal(Entity e) {
                        return true;
                    }

                    public void removeStalePortalLocations(long time) {
                    }
                });
            }

            player.playerNetServerHandler.setPlayerLocation(x, y, z, yaw, pitch);
        }
    }
}
