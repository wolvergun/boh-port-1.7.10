package net.mcreator.boh.compat.debug;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.mcreator.boh.BohMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * Mob fights and brewing, in walled pens high above the overworld spawn: who targets whom, how far apart attacker and
 * target are at each hit, and how often a chaser lands a hit; then a full brew of each mod recipe in a brewing stand.
 */
public final class CombatTest {

    private static final int Y = 220;

    private final WorldServer w;
    private final int bx, bz;
    private final Map<String, EntityLiving> mobs = new LinkedHashMap<>();
    private final Map<String, Integer> hits = new LinkedHashMap<>();
    private final List<String> hitLog = new ArrayList<>();
    private final StringBuilder track = new StringBuilder();
    private int tick;

    private CombatTest(WorldServer w, int x, int z) {
        this.w = w;
        this.bx = x;
        this.bz = z;
    }

    private static void log(String s, Object... args) {
        BohMod.LOGGER.info("[BOH-SELFTEST] " + String.format(s, args));
    }

    static void start(WorldServer w, int x, int z) {
        CombatTest t = new CombatTest(w, x, z);
        try {
            t.brewing(x, z - 30);
            t.pen(x, z, 6);
            t.add("leatherface", "boh.leatherface", x - 3, z);
            t.add("saw_runner", "boh.saw_runner", x + 3, z);
            t.pen(x + 30, z, 9);
            t.add("lifeform", "boh.lifeform", x + 23, z);
            t.add("pig", "Pig", x + 37, z);
            t.pen(x + 60, z, 9);
            t.add("pyramid_head", "boh.pyramid_head", x + 53, z);
            t.add("cow", "Cow", x + 67, z);
        } catch (Throwable e) {
            BohMod.LOGGER.error("[BOH-SELFTEST] combat setup failed", e);
            return;
        }
        FMLCommonHandler.instance().bus().register(t);
        MinecraftForge.EVENT_BUS.register(t);
    }

    /** Stone floor, glass walls 4 high, air inside, radius r around (x, z). */
    private void pen(int x, int z, int r) {
        for (int dx = -r - 1; dx <= r + 1; dx++) for (int dz = -r - 1; dz <= r + 1; dz++) {
            boolean wall = Math.abs(dx) == r + 1 || Math.abs(dz) == r + 1;
            w.setBlock(x + dx, Y, z + dz, Blocks.stone, 0, 2);
            for (int y = Y + 1; y < Y + 5; y++) w.setBlock(x + dx, y, z + dz, wall ? Blocks.glass : Blocks.air, 0, 2);
        }
    }

    private void add(String key, String id, int x, int z) {
        Entity e = EntityList.createEntityByName(id, w);
        if (!(e instanceof EntityLiving)) {
            log("combat: no entity %s", id);
            return;
        }
        e.setLocationAndAngles(x + 0.5, Y + 1, z + 0.5, 0, 0);
        w.spawnEntityInWorld(e);
        mobs.put(key, (EntityLiving) e);
    }

    private String name(Entity e) {
        if (e == null) return "-";
        for (Map.Entry<String, EntityLiving> m : mobs.entrySet()) if (m.getValue() == e) return m.getKey();
        String s = EntityList.getEntityString(e);
        return s == null ? e.getClass().getSimpleName() : s;
    }

    @SubscribeEvent
    public void onAttack(LivingAttackEvent e) {
        Entity src = e.source.getEntity();
        if (src == null || !mobs.containsValue(src)) return;
        EntityLivingBase t = e.entityLiving;
        double dx = t.posX - src.posX, dz = t.posZ - src.posZ;
        double centre = Math.sqrt(dx * dx + dz * dz), gap = centre - src.width / 2 - t.width / 2;
        hits.merge(name(src), 1, Integer::sum);
        if (hitLog.size() < 40)
            hitLog.add(String.format("t%d %s->%s centre %.2f gap %.2f dy %.2f", tick, name(src), name(t), centre, gap, t.posY - src.posY));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        tick++;
        if (tick % 20 == 0) {
            track.append(String.format("%n  t%d:", tick));
            for (Map.Entry<String, EntityLiving> m : mobs.entrySet()) {
                EntityLiving l = m.getValue();
                track.append(String.format(" %s(hp %.0f, target %s, y %.2f, path %s)", m.getKey(), l.getHealth(), name(l.getAttackTarget()),
                    l.posY - Y - 1, l.getNavigator().noPath() ? "none" : "yes"));
            }
        }
        if (tick >= 300) {
            FMLCommonHandler.instance().bus().unregister(this);
            MinecraftForge.EVENT_BUS.unregister(this);
            log("combat track:%s", track);
            log("combat hits %s:%n  %s", hits, String.join("\n  ", hitLog));
            for (EntityLiving l : mobs.values()) l.setDead();
        }
    }

    /** One brewing stand per mod ingredient, three water bottles, run for a full brew (400 ticks). */
    private void brewing(int x, int z) {
        String[] ingredients = { "boh:vampire_blood", "boh:werewolf_teeth" };
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < ingredients.length; i++) {
            Item ing = (Item) Item.itemRegistry.getObject(ingredients[i]);
            if (ing == null) {
                out.append(" ").append(ingredients[i]).append(": no item;");
                continue;
            }
            ItemStack is = new ItemStack(ing);
            w.setBlock(x + i * 2, Y, z, Blocks.stone, 0, 2);
            w.setBlock(x + i * 2, Y + 1, z, Blocks.brewing_stand, 0, 2);
            TileEntityBrewingStand stand = (TileEntityBrewingStand) w.getTileEntity(x + i * 2, Y + 1, z);
            stand.setInventorySlotContents(0, new ItemStack(Items.potionitem, 1, 0));
            stand.setInventorySlotContents(1, new ItemStack(Items.potionitem, 1, 16384));
            stand.setInventorySlotContents(3, is.copy());
            out.append(String.format(" %s: ingredient %s effect '%s', slot ok %s;", ingredients[i], ing.isPotionIngredient(is), ing.getPotionEffect(is),
                stand.isItemValidForSlot(3, is)));
            for (int t = 0; t < 420; t++) stand.updateEntity();
            for (int s = 0; s < 4; s++) {
                ItemStack r = stand.getStackInSlot(s);
                out.append(String.format(" slot%d=%s", s, r == null ? "-" : Item.itemRegistry.getNameForObject(r.getItem()) + ":" + r.getItemDamage()
                    + (r.hasTagCompound() ? r.getTagCompound().toString() : "")));
            }
            out.append(";");
        }
        log("brewing:%s", out);
    }
}
