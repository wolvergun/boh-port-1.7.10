package net.mcreator.boh.compat.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.mcreator.boh.BohMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * The document chain, run by the self-test after the world checks: Soul Stealer kills drop souls, the Computer turns
 * black dye + Haunted Paper into a random document, and every document used inside a ring of 4 Rift Stabilizers
 * summons its entity (and one used without the ring summons nothing). High above the spawn, which stays loaded.
 */
final class RiftTest {

    private static final int Y = 200;
    private static final int SUMMON_WAIT = 80, COMPUTER_WAIT = 23 * 15 + 60;

    private final WorldServer w;
    private final FakePlayer player;
    private final int sx, sz;
    private final List<String> docs = new ArrayList<>();
    private final List<int[]> spots = new ArrayList<>();
    private int[] noRing, computer;
    private Entity shot;
    private double shotX, shotY, shotZ;
    private int tick;

    private RiftTest(WorldServer w) {
        this.w = w;
        this.player = FakePlayerFactory.get(w, new GameProfile(UUID.fromString("b0b0b0b0-0000-4000-8000-000000000001"), "[BohSelfTest]"));
        ChunkCoordinates s = w.getSpawnPoint();
        sx = s.posX;
        sz = s.posZ;
    }

    static void start(WorldServer w) {
        RiftTest t = new RiftTest(w);
        try {
            t.souls();
            t.setUp();
        } catch (Throwable e) {
            BohMod.LOGGER.error("[BOH-SELFTEST] rift setup failed", e);
        }
        FMLCommonHandler.instance().bus().register(t);
    }

    private static void log(String s, Object... args) {
        BohMod.LOGGER.info("[BOH-SELFTEST] " + String.format(s, args));
    }

    private void platform(int x, int z, int r) {
        for (int dx = -r; dx <= r; dx++) for (int dz = -r; dz <= r; dz++) {
            w.setBlock(x + dx, Y, z + dz, Blocks.stone, 0, 2);
            for (int y = Y + 1; y < Y + 6; y++) w.setBlock(x + dx, y, z + dz, Blocks.air, 0, 2);
        }
    }

    private void hold(ItemStack stack, int x, int z) {
        player.inventory.mainInventory[0] = stack;
        player.inventory.currentItem = 0;
        player.setPositionAndRotation(x + 0.5, Y + 1, z + 3.5, 180, 30);
    }

    /** One mob of each soul tag killed 8 times with the Soul Stealer: about half should drop their soul. */
    private void souls() {
        Item stealer = (Item) Item.itemRegistry.getObject("boh:soul_stealer");
        String[][] tags = { { "killers", "boh.simonhenriksson" }, { "exotic", "boh.deer_mimic" }, { "monstrous", "boh.xenomorph" }, { "demons", "boh.demon" } };
        int x = sx - 40, z = sz - 40;
        platform(x, z, 4);
        StringBuilder out = new StringBuilder();
        for (String[] t : tags) {
            int kills = 0;
            for (int i = 0; i < 8; i++) {
                Entity e = EntityList.createEntityByName(t[1], w);
                if (!(e instanceof EntityLivingBase)) {
                    out.append(String.format(" %s: no entity %s;", t[0], t[1]));
                    break;
                }
                e.setPosition(x + 0.5, Y + 1, z + 0.5);
                w.spawnEntityInWorld(e);
                hold(stealer == null ? null : new ItemStack(stealer), x, z);
                e.attackEntityFrom(DamageSource.causePlayerDamage(player), 10000);
                if (((EntityLivingBase) e).getHealth() <= 0) kills++;
                e.setDead();
            }
            Map<String, Integer> drops = new TreeMap<>();
            for (Object o : w.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x - 4, Y - 1, z - 4, x + 5, Y + 6, z + 5))) {
                EntityItem it = (EntityItem) o;
                String n = Item.itemRegistry.getNameForObject(it.getEntityItem().getItem());
                if (n != null && n.endsWith("_soul")) drops.merge(n, it.getEntityItem().stackSize, Integer::sum);
                it.setDead();
            }
            out.append(String.format(" %s (%s): %d kills, souls %s;", t[0], t[1], kills, drops));
        }
        log("souls: Soul Stealer %s;%s", stealer == null ? "MISSING" : "ok", out);
    }

    private void setUp() {
        for (Object k : Item.itemRegistry.getKeys()) {
            String n = (String) k;
            if (n.startsWith("boh:document_") || n.equals("boh:pumpkin_guy_document")) docs.add(n);
        }
        java.util.Collections.sort(docs);
        Block stabilizer = Block.getBlockFromName("boh:rift_stabilizer");
        for (int i = 0; i < docs.size(); i++) {
            int x = sx + (i % 8) * 9 - 32, z = sz + (i / 8) * 9 - 32;
            platform(x, z, 2);
            w.setBlock(x + 1, Y + 1, z, stabilizer, 0, 2);
            w.setBlock(x - 1, Y + 1, z, stabilizer, 0, 2);
            w.setBlock(x, Y + 1, z + 1, stabilizer, 0, 2);
            w.setBlock(x, Y + 1, z - 1, stabilizer, 0, 2);
            spots.add(new int[] { x, z });
            use(docs.get(i), x, z);
        }
        // the same document without the stabilizer ring must do nothing
        noRing = new int[] { sx + 40, sz + 40 };
        platform(noRing[0], noRing[1], 2);
        if (!docs.isEmpty()) use(docs.get(0), noRing[0], noRing[1]);

        // Computer: black dye in slot 0, Haunted Paper in slot 1, a document appears in slot 2
        computer = new int[] { sx + 40, sz - 40 };
        platform(computer[0], computer[1], 1);
        Block pc = Block.getBlockFromName("boh:computer");
        w.setBlock(computer[0], Y + 1, computer[1], pc, 0, 3);
        TileEntity te = w.getTileEntity(computer[0], Y + 1, computer[1]);
        Item paper = (Item) Item.itemRegistry.getObject("boh:haunted_paper");
        if (te instanceof IInventory && paper != null) {
            ((IInventory) te).setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
            ((IInventory) te).setInventorySlotContents(1, new ItemStack(paper));
        } else log("computer: block %s, block entity %s, haunted paper %s", pc, te, paper);

        // Ray Gun: fire level, north, from 30 blocks up over the spawn and follow the projectile
        Item gun = (Item) Item.itemRegistry.getObject("boh:ray_gun");
        if (gun != null) {
            ItemStack g = new ItemStack(gun);
            hold(g, sx, sz + 60);
            player.setPositionAndRotation(sx + 0.5, Y + 30, sz + 60.5, 180, 0);
            int before = w.loadedEntityList.size();
            gun.onItemRightClick(g, w, player);
            for (int i = before; i < w.loadedEntityList.size(); i++) {
                Entity e = (Entity) w.loadedEntityList.get(i);
                if (EntityList.getEntityString(e) != null && EntityList.getEntityString(e).contains("ray_gun")) shot = e;
            }
            if (shot != null) {
                shotX = shot.posX;
                shotY = shot.posY;
                shotZ = shot.posZ;
            }
            log("ray gun: fired, projectile %s at %.1f %.1f %.1f, motion %.2f %.2f %.2f", shot == null ? null : EntityList.getEntityString(shot), shotX, shotY,
                shotZ, shot == null ? 0 : shot.motionX, shot == null ? 0 : shot.motionY, shot == null ? 0 : shot.motionZ);
        }
    }

    private void use(String doc, int x, int z) {
        Item item = (Item) Item.itemRegistry.getObject(doc);
        ItemStack stack = new ItemStack(item);
        hold(stack, x, z);
        try {
            item.onItemUse(stack, player, w, x, Y, z, 1, 0.5F, 1.0F, 0.5F);
        } catch (Throwable e) {
            BohMod.LOGGER.error("[BOH-SELFTEST] using " + doc + " failed", e);
        }
    }

    private String mobsAt(int x, int z) {
        List<String> names = new ArrayList<>();
        for (Object o : w.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(x - 3, Y - 2, z - 3, x + 4, Y + 8, z + 4))) {
            Entity e = (Entity) o;
            names.add(EntityList.getEntityString(e));
            e.setDead();
        }
        return String.join("+", names);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        tick++;
        try {
            if (shot != null && (tick == 10 || tick == 30))
                log("ray gun projectile after %d ticks: alive %s, moved %.1f blocks (dy %.1f), motion %.2f %.2f %.2f", tick, !shot.isDead,
                    Math.sqrt(sq(shot.posX - shotX) + sq(shot.posZ - shotZ)), shot.posY - shotY, shot.motionX, shot.motionY, shot.motionZ);
            if (tick == SUMMON_WAIT) summons();
            if (tick == COMPUTER_WAIT) computer();
        } catch (Throwable t) {
            BohMod.LOGGER.error("[BOH-SELFTEST] rift check failed", t);
        }
        if (tick == COMPUTER_WAIT) {
            FMLCommonHandler.instance().bus().unregister(this);
            log("done");
            MinecraftServer.getServer().initiateShutdown();
        }
    }

    private static double sq(double d) {
        return d * d;
    }

    private void summons() {
        Block stabilizer = Block.getBlockFromName("boh:rift_stabilizer");
        int ok = 0;
        StringBuilder all = new StringBuilder(), bad = new StringBuilder();
        for (int i = 0; i < docs.size(); i++) {
            int x = spots.get(i)[0], z = spots.get(i)[1];
            String mobs = mobsAt(x, z);
            int left = 0;
            if (w.getBlock(x + 1, Y + 1, z) == stabilizer) left++;
            if (w.getBlock(x - 1, Y + 1, z) == stabilizer) left++;
            if (w.getBlock(x, Y + 1, z + 1) == stabilizer) left++;
            if (w.getBlock(x, Y + 1, z - 1) == stabilizer) left++;
            String d = docs.get(i).replace("boh:", "");
            if (!mobs.isEmpty() && left == 0) ok++;
            else bad.append(String.format(" %s (mobs '%s', %d stabilizers left);", d, mobs, left));
            all.append(String.format(" %s=%s", d, mobs.isEmpty() ? "-" : mobs.replace("boh.", "")));
        }
        log("rift: %d/%d documents summoned and used up their stabilizers;%s", ok, docs.size(), bad.length() == 0 ? " all ok" : bad);
        log("rift summons:%s", all);
        log("rift without stabilizers: mobs '%s' (expected none), document still held: %s", mobsAt(noRing[0], noRing[1]),
            player.inventory.mainInventory[0] != null);
    }

    private void computer() {
        TileEntity te = w.getTileEntity(computer[0], Y + 1, computer[1]);
        if (!(te instanceof IInventory)) {
            log("computer: no block entity");
            return;
        }
        IInventory inv = (IInventory) te;
        ItemStack out = inv.getStackInSlot(2);
        log("computer after %d ticks: slot0 %s, slot1 %s, output %s, block meta %d", COMPUTER_WAIT, inv.getStackInSlot(0), inv.getStackInSlot(1),
            out == null ? null : Item.itemRegistry.getNameForObject(out.getItem()) + " x" + out.stackSize,
            w.getBlockMetadata(computer[0], Y + 1, computer[1]));
    }
}
