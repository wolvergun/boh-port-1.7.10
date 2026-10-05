package net.mcreator.boh.compat.debug;

import java.util.List;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.world.Dimensions;
import net.mcreator.boh.compat.world.gen.BohWorldProvider;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Client check run by CI with -Dboh.clienttest=true: opens a creative world, moves the player to Level 0, spawns a
 * Lifeform from its egg path (spread within 20 blocks) and one without the spread 4 blocks in front of the player,
 * then logs where the server and the client each see them and takes screenshots. Quits the game when done.
 */
@SideOnly(Side.CLIENT)
public final class ClientTest {

    private int clientTicks, serverTicks, stage;
    private volatile int eggId = -1, nearId = -1;
    private boolean launched, clientMap;

    private ClientTest() {}

    public static void installIfEnabled() {
        if (System.getProperty("boh.clienttest") == null) return;
        FMLCommonHandler.instance().bus().register(new ClientTest());
        log("enabled");
    }

    private static void log(String s, Object... args) {
        BohMod.LOGGER.info("[BOH-CLIENTTEST] " + String.format(s, args));
    }

    private static int level0() {
        for (BohWorldProvider.Spec spec : BohWorldProvider.SPECS.values()) if (spec.floor != null) return spec.id;
        return 0;
    }

    private static String block(World w, int x, int y, int z) {
        String n = Block.blockRegistry.getNameForObject(w.getBlock(x, y, z));
        return n == null ? "?" : n.replace("minecraft:", "").replace("boh:", "");
    }

    private static String where(World w, Entity e) {
        if (e == null) return "none";
        int x = MathHelper.floor_double(e.posX), y = MathHelper.floor_double(e.posY), z = MathHelper.floor_double(e.posZ);
        return String.format("%.2f %.2f %.2f bb.minY %.2f (feet %s, below %s, ground %s, hp %.0f, dead %s)", e.posX, e.posY, e.posZ,
            e.boundingBox.minY, block(w, x, y, z), block(w, x, y - 1, z), e.onGround,
            e instanceof EntityLiving ? ((EntityLiving) e).getHealth() : 0f, e.isDead);
    }

    /** The floor layer (y 0) and the layer above (y 1) in a 32x32 area: '#' both solid, '_' floor only, '.' no floor. */
    private static void floorMap(String who, World w, int cx, int cz) {
        StringBuilder b = new StringBuilder();
        for (int z = cz - 16; z < cz + 16; z++) {
            b.append(String.format("%n  z%4d ", z));
            for (int x = cx - 16; x < cx + 16; x++) {
                boolean f = w.getBlock(x, 0, z).getMaterial().blocksMovement(), a = w.getBlock(x, 1, z).getMaterial().blocksMovement();
                b.append(f && a ? '#' : f ? '_' : a ? '^' : '.');
            }
        }
        log("%s floor map around %d %d (x from %d):%s", who, cx, cz, cx - 16, b);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getMinecraft();
        mc.gameSettings.pauseOnLostFocus = false;
        if (!launched && mc.currentScreen instanceof GuiMainMenu) {
            launched = true;
            WorldSettings ws = new WorldSettings(1234L, WorldSettings.GameType.CREATIVE, false, false, WorldType.FLAT);
            ws.enableCommands();
            log("launching the test world");
            mc.launchIntegratedServer("boh-clienttest", "boh-clienttest", ws);
            return;
        }
        if (mc.theWorld == null || mc.thePlayer == null) return;
        clientTicks++;
        if (stage < 2) return;
        Entity egg = eggId < 0 ? null : mc.theWorld.getEntityByID(eggId), near = nearId < 0 ? null : mc.theWorld.getEntityByID(nearId);
        if (near != null) {
            // keep looking at the Lifeform in front
            double dx = near.posX - mc.thePlayer.posX, dz = near.posZ - mc.thePlayer.posZ, dy = near.posY + 0.9 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
            mc.thePlayer.rotationYaw = (float) (Math.atan2(dz, dx) * 180 / Math.PI) - 90;
            mc.thePlayer.rotationPitch = (float) -(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * 180 / Math.PI);
        }
        if (clientTicks % 5 == 0)
            log("client t%d: dim %d, player %.1f %.1f %.1f; egg lifeform %s; near lifeform %s", clientTicks, mc.theWorld.provider.dimensionId,
                mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, where(mc.theWorld, egg), where(mc.theWorld, near));
        if (stage >= 2 && clientTicks % 10 == 0 && clientTicks / 10 <= 30) {
            String msg = ScreenShotHelper.saveScreenshot(mc.mcDataDir, mc.displayWidth, mc.displayHeight, mc.getFramebuffer()).getUnformattedText();
            log("screenshot: %s", msg);
        }
        if (!clientMap) {
            clientMap = true;
            floorMap("client", mc.theWorld, 104, 88);
        }
        if (stage == 3) {
            log("done");
            mc.shutdown();
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null) return;
        @SuppressWarnings("unchecked")
        List<EntityPlayerMP> players = server.getConfigurationManager().playerEntityList;
        if (players.isEmpty()) return;
        EntityPlayerMP p = players.get(0);
        serverTicks++;
        int dim = level0();
        if (stage == 0 && serverTicks == 100) {
            log("moving the player to Level 0 (dim %d)", dim);
            p.capabilities.isFlying = false;
            Dimensions.transferPlayer(p, dim, 100.5, 2, 100.5, 0, 0);
            stage = 1;
            serverTicks = 0;
            return;
        }
        if (stage == 1 && serverTicks == 100) {
            WorldServer w = (WorldServer) p.worldObj;
            floorMap("server", w, 104, 88);
            log("server: player in dim %d at %.1f %.1f %.1f, feet %s, below %s", w.provider.dimensionId, p.posX, p.posY, p.posZ,
                block(w, (int) Math.floor(p.posX), (int) Math.floor(p.posY), (int) Math.floor(p.posZ)),
                block(w, (int) Math.floor(p.posX), (int) Math.floor(p.posY) - 1, (int) Math.floor(p.posZ)));
            EntityLiving egg = (EntityLiving) EntityList.createEntityByName("boh.lifeform", w);
            egg.setLocationAndAngles(p.posX, p.posY, p.posZ, 0, 0);
            egg.onSpawnWithEgg(null);
            w.spawnEntityInWorld(egg);
            eggId = egg.getEntityId();
            // the one to look at: same floor as the player, a few blocks along whichever way is open
            double[][] dirs = { { 4, 0 }, { -4, 0 }, { 0, 4 }, { 0, -4 }, { 2, 0 }, { -2, 0 }, { 0, 2 }, { 0, -2 } };
            double nx = p.posX, nz = p.posZ;
            for (double[] d : dirs) {
                int bx = MathHelper.floor_double(p.posX + d[0]), by = MathHelper.floor_double(p.posY), bz = MathHelper.floor_double(p.posZ + d[1]);
                if (w.isAirBlock(bx, by, bz) && w.isAirBlock(bx, by + 1, bz) && w.getBlock(bx, by - 1, bz).getMaterial().blocksMovement()) {
                    nx = p.posX + d[0];
                    nz = p.posZ + d[1];
                    break;
                }
            }
            EntityLiving near = (EntityLiving) EntityList.createEntityByName("boh.lifeform", w);
            near.setLocationAndAngles(nx, p.posY, nz, 0, 0);
            w.spawnEntityInWorld(near);
            nearId = near.getEntityId();
            // something for it to chase in view: a pig 2 blocks further along the same line
            Entity pig = EntityList.createEntityByName("Pig", w);
            pig.setLocationAndAngles(nx + (nx - p.posX) / 2, p.posY, nz + (nz - p.posZ) / 2, 0, 0);
            w.spawnEntityInWorld(pig);
            stage = 2;
            serverTicks = 0;
            return;
        }
        if (stage == 2) {
            WorldServer w = (WorldServer) p.worldObj;
            if (serverTicks % 5 == 0)
                log("server t%d: egg lifeform %s; near lifeform %s", serverTicks, where(w, w.getEntityByID(eggId)), where(w, w.getEntityByID(nearId)));
            if (serverTicks >= 360) stage = 3;
        }
    }
}
