package net.mcreator.boh.compat.registry;

import java.util.ArrayList;
import java.util.List;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.item.BohBlockItem;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.decoration.PaintingVariant;
import net.mcreator.boh.compat.mc.world.entity.npc.VillagerProfession;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.mcreator.boh.compat.mc.world.item.CreativeModeTab;
import net.mcreator.boh.compat.mc.world.item.alchemy.BrewPotion;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

/** Pushes objects registered through the 1.20-style DeferredRegisters into the 1.7.10 registries. */
public final class Registration {

    private static int nextEntityId = 0;
    private static int nextGuiId = 0;
    private static int nextProfessionId = 6600;
    public static final List<MenuType<?>> MENUS = new ArrayList<>();
    public static final List<PaintingVariant> PAINTINGS = new ArrayList<>();
    public static final List<ParticleType<?>> PARTICLES = new ArrayList<>();
    public static final List<EntityType<?>> ENTITIES = new ArrayList<>();
    public static final List<CreativeModeTab> TABS = new ArrayList<>();

    private Registration() {}

    public static Block block(ResourceLocation id, Block block) {
        block.setBlockName(id.getResourceDomain() + "." + id.getResourcePath());
        if (block instanceof net.mcreator.boh.compat.block.BohBlock)
            ((net.mcreator.boh.compat.block.BohBlock) block).onRegistered(id);
        else if (block instanceof net.mcreator.boh.compat.block.CompatBlock)
            ((net.mcreator.boh.compat.block.CompatBlock) block).onRegistered(id);
        GameRegistry.registerBlock(block, BohBlockItem.class, id.getResourcePath());
        return block;
    }

    /** Block items were already created together with their block; return that canonical item. */
    public static Item item(ResourceLocation id, Item item) {
        if (item instanceof ItemBlock) {
            Item existing = Item.getItemFromBlock(((ItemBlock) item).field_150939_a);
            if (existing != null) return existing;
        }
        item.setUnlocalizedName(id.getResourceDomain() + "." + id.getResourcePath());
        if (item instanceof net.mcreator.boh.compat.item.BohItem)
            ((net.mcreator.boh.compat.item.BohItem) item).onRegistered(id);
        else item.setTextureName(id.getResourceDomain() + ":" + id.getResourcePath());
        GameRegistry.registerItem(item, id.getResourcePath());
        return item;
    }

    public static EntityType<?> entity(ResourceLocation id, EntityType<?> type) {
        type.setRegistryName(id);
        ENTITIES.add(type);
        if (type.getEntityClass() != null) {
            EntityRegistry.registerModEntity(type.getEntityClass(), id.getResourcePath(), nextEntityId++, BohMod.instance,
                Math.max(64, Math.min(type.clientTrackingRange(), 256)), Math.max(1, type.updateInterval()), type.trackDeltas());
        }
        return type;
    }

    public static Potion effect(ResourceLocation id, Potion effect) {
        effect.setPotionName("effect." + id.getResourceDomain() + "." + id.getResourcePath());
        if (effect instanceof net.mcreator.boh.compat.effect.BohMobEffect)
            ((net.mcreator.boh.compat.effect.BohMobEffect) effect).setIconTexture(new ResourceLocation(id.getResourceDomain(), "textures/mob_effect/" + id.getResourcePath() + ".png"));
        return effect;
    }

    public static BrewPotion potion(ResourceLocation id, BrewPotion potion) {
        potion.setRegistryName(id);
        return potion;
    }

    public static Enchantment enchantment(ResourceLocation id, Enchantment e) {
        e.setName(id.getResourceDomain() + "." + id.getResourcePath());
        return e;
    }

    public static ParticleType<?> particle(ResourceLocation id, ParticleType<?> p) {
        p.setRegistryName(id);
        PARTICLES.add(p);
        return p;
    }

    public static MenuType<?> menu(ResourceLocation id, MenuType<?> m) {
        m.bind(id, nextGuiId++);
        MENUS.add(m);
        return m;
    }

    public static BlockEntityType<?> blockEntity(ResourceLocation id, BlockEntityType<?> t) {
        t.setRegistryName(id);
        GameRegistry.registerTileEntity(t.tileClass(), id.toString());
        return t;
    }

    public static PaintingVariant painting(ResourceLocation id, PaintingVariant p) {
        p.setRegistryName(id);
        PAINTINGS.add(p);
        return p;
    }

    public static VillagerProfession profession(ResourceLocation id, VillagerProfession p) {
        int legacy = nextProfessionId++;
        p.setLegacyId(legacy);
        VillagerRegistry.instance().registerVillagerId(legacy);
        return p;
    }

    public static CreativeModeTab tab(ResourceLocation id, CreativeModeTab tab) {
        tab.toVanilla(id.getResourceDomain() + "." + id.getResourcePath());
        TABS.add(tab);
        return tab;
    }
}
