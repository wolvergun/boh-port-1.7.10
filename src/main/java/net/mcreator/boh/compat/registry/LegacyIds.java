package net.mcreator.boh.compat.registry;

import cpw.mods.fml.common.Loader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.core.Axis;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public final class LegacyIds {
    private static Map<String, LegacyIds.Target> table;
    private static final Map<String, String> ENTITY_NAMES = new HashMap<>();
    private static final Map<String, String> ENTITY_NAMES_REVERSE = new HashMap<>();
    private static final Map<String, Potion> EFFECTS = new HashMap<>();

    private LegacyIds() {
    }

    private static synchronized Map<String, LegacyIds.Target> table() {
        if (table != null) {
            return table;
        } else {
            table = new HashMap<>();
            boolean efr = Loader.isModLoaded("etfuturum");

            String line;
            try (
                InputStream in = LegacyIds.class.getResourceAsStream("/assets/boh/compat/legacy_ids.txt");
                BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            ) {
                while ((line = r.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty() && !line.startsWith("#")) {
                        String[] kv = line.split("=", 2);
                        String modern = kv[0].trim();
                        String[] targets = kv[1].split("\\|");
                        String pick = targets[0].trim();
                        if (efr && targets.length > 1) {
                            pick = targets[1].trim();
                        }

                        table.put(modern, parse(pick));
                        if (targets.length > 1 && !efr) {
                        }
                    }
                }
            } catch (Exception var12) {
                BohMod.LOGGER.error("Could not read legacy id table", var12);
            }

            return table;
        }
    }

    private static LegacyIds.Target parse(String s) {
        int at = s.indexOf(64);
        return at < 0 ? new LegacyIds.Target(s, 0) : new LegacyIds.Target(s.substring(0, at), Integer.parseInt(s.substring(at + 1)));
    }

    public static LegacyIds.Target target(String modern) {
        String path = modern.startsWith("minecraft:") ? modern.substring(10) : modern;
        LegacyIds.Target t = table().get(path);
        if (t != null) {
            return t;
        } else if (modern.contains(":") && !modern.startsWith("minecraft:")) {
            return new LegacyIds.Target(modern, 0);
        } else {
            return Block.getBlockFromName(path) == null && Item.itemRegistry.getObject(path) == null ? null : new LegacyIds.Target(path, 0);
        }
    }

    public static Block blockOf(String modern) {
        LegacyIds.Target t = target(modern);
        return t == null ? Blocks.air : t.block();
    }

    public static Item itemOf(String modern) {
        if (modern.equals("air")) {
            return null;
        } else {
            LegacyIds.Target t = target(modern);
            return t == null ? null : t.item();
        }
    }

    public static Block block(ResourceLocation id) {
        if (id == null) {
            return null;
        } else {
            LegacyIds.Target t = target(id.getResourceDomain() + ":" + id.getResourcePath());
            return t == null ? null : t.block();
        }
    }

    public static ResourceLocation blockKey(Block block) {
        String n = Block.blockRegistry.getNameForObject(block);
        return n == null ? null : new ResourceLocation(n);
    }

    public static Item item(ResourceLocation id) {
        if (id == null) {
            return null;
        } else {
            LegacyIds.Target t = target(id.getResourceDomain() + ":" + id.getResourcePath());
            return t == null ? null : t.item();
        }
    }

    public static ResourceLocation itemKey(Item item) {
        String n = Item.itemRegistry.getNameForObject(item);
        return n == null ? null : new ResourceLocation(n);
    }

    public static Potion effect(ResourceLocation id) {
        if (EFFECTS.isEmpty()) {
            String[][] m = new String[][]{
                {"speed", "moveSpeed"},
                {"slowness", "moveSlowdown"},
                {"haste", "digSpeed"},
                {"mining_fatigue", "digSlowDown"},
                {"strength", "damageBoost"},
                {"instant_health", "heal"},
                {"instant_damage", "harm"},
                {"jump_boost", "jump"},
                {"nausea", "confusion"},
                {"regeneration", "regeneration"},
                {"resistance", "resistance"},
                {"fire_resistance", "fireResistance"},
                {"water_breathing", "waterBreathing"},
                {"invisibility", "invisibility"},
                {"blindness", "blindness"},
                {"night_vision", "nightVision"},
                {"hunger", "hunger"},
                {"weakness", "weakness"},
                {"poison", "poison"},
                {"wither", "wither"},
                {"health_boost", "field_76434_w"},
                {"absorption", "field_76444_x"},
                {"saturation", "field_76443_y"}
            };

            for (String[] p : m) {
                for (Potion pot : Potion.potionTypes) {
                    if (pot != null && pot.getName().equals("potion." + p[1])) {
                        EFFECTS.put(p[0], pot);
                    }
                }
            }

            EFFECTS.put("health_boost", Potion.field_76434_w);
            EFFECTS.put("absorption", Potion.field_76444_x);
            EFFECTS.put("saturation", Potion.field_76443_y);
        }

        return id != null && "minecraft".equals(id.getResourceDomain()) ? EFFECTS.get(id.getResourcePath()) : null;
    }

    public static ResourceLocation effectKey(Potion p) {
        if (p == null) {
            return null;
        } else {
            effect(new ResourceLocation("minecraft", "speed"));

            for (Entry<String, Potion> e : EFFECTS.entrySet()) {
                if (e.getValue() == p) {
                    return new ResourceLocation("minecraft", e.getKey());
                }
            }

            return new ResourceLocation("minecraft", p.getName().replace("potion.", ""));
        }
    }

    public static String entityName(String modernPath) {
        String n = ENTITY_NAMES.get(modernPath);
        return n != null ? n : modernPath;
    }

    public static String modernEntityName(String legacyName) {
        String n = ENTITY_NAMES_REVERSE.get(legacyName);
        return n != null ? n : legacyName.toLowerCase();
    }

    public static Object legacyPropertyValue(Block block, int meta, Property<?> p) {
        if (block instanceof BlockLog && p.getName().equals("axis")) {
            int a = meta >> 2 & 3;
            return a == 1 ? Axis.X : (a == 2 ? Axis.Z : Axis.Y);
        } else {
            return p.getName().equals("facing") && p.getValueClass() == Direction.class ? Direction.from3DDataValue(meta & 7) : null;
        }
    }

    public static Integer legacyPropertyMeta(Block block, int meta, Property<?> p, Object value) {
        if (block instanceof BlockLog && p.getName().equals("axis")) {
            int a = value == Axis.X ? 1 : (value == Axis.Z ? 2 : 0);
            return meta & 3 | a << 2;
        } else {
            return null;
        }
    }

    static {
        String[][] e = new String[][]{
            {"zombie", "Zombie"},
            {"skeleton", "Skeleton"},
            {"creeper", "Creeper"},
            {"spider", "Spider"},
            {"cave_spider", "CaveSpider"},
            {"enderman", "Enderman"},
            {"slime", "Slime"},
            {"ghast", "Ghast"},
            {"blaze", "Blaze"},
            {"witch", "Witch"},
            {"wither", "WitherBoss"},
            {"ender_dragon", "EnderDragon"},
            {"pig", "Pig"},
            {"cow", "Cow"},
            {"sheep", "Sheep"},
            {"chicken", "Chicken"},
            {"wolf", "Wolf"},
            {"horse", "EntityHorse"},
            {"villager", "Villager"},
            {"iron_golem", "VillagerGolem"},
            {"snow_golem", "SnowMan"},
            {"bat", "Bat"},
            {"squid", "Squid"},
            {"ocelot", "Ozelot"},
            {"cat", "Ozelot"},
            {"mooshroom", "MushroomCow"},
            {"silverfish", "Silverfish"},
            {"magma_cube", "LavaSlime"},
            {"zombified_piglin", "PigZombie"},
            {"giant", "Giant"},
            {"item", "Item"},
            {"experience_orb", "XPOrb"},
            {"arrow", "Arrow"},
            {"snowball", "Snowball"},
            {"fireball", "Fireball"},
            {"small_fireball", "SmallFireball"},
            {"tnt", "PrimedTnt"},
            {"falling_block", "FallingSand"},
            {"armor_stand", "ArmorStand"},
            {"boat", "Boat"},
            {"minecart", "MinecartRideable"},
            {"item_frame", "ItemFrame"},
            {"painting", "Painting"},
            {"wither_skull", "WitherSkull"},
            {"potion", "ThrownPotion"},
            {"ender_pearl", "ThrownEnderpearl"},
            {"lightning_bolt", "LightningBolt"}
        };

        for (String[] p : e) {
            ENTITY_NAMES.put(p[0], p[1]);
            ENTITY_NAMES_REVERSE.putIfAbsent(p[1], p[0]);
        }
    }

    public static final class Target {
        public final String name;
        public final int meta;

        Target(String name, int meta) {
            this.name = name;
            this.meta = meta;
        }

        public Block block() {
            Block b = Block.getBlockFromName(this.name);
            return b == null ? Blocks.air : b;
        }

        public Item item() {
            Object o = Item.itemRegistry.getObject(this.name);
            if (o instanceof Item) {
                return (Item)o;
            } else {
                Block b = Block.getBlockFromName(this.name);
                return b == null ? null : Item.getItemFromBlock(b);
            }
        }
    }
}
