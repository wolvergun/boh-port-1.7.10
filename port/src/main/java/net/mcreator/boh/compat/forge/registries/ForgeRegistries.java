package net.mcreator.boh.compat.forge.registries;

import java.util.HashMap;
import java.util.Map;

import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.ai.village.poi.PoiType;
import net.mcreator.boh.compat.mc.world.entity.decoration.PaintingVariant;
import net.mcreator.boh.compat.mc.world.entity.npc.VillagerProfession;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.mcreator.boh.compat.mc.world.item.CreativeModeTab;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;
import net.mcreator.boh.compat.mc.world.level.levelgen.feature.Feature;
import net.mcreator.boh.compat.registry.LegacyIds;
import net.mcreator.boh.compat.registry.Registration;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.BiomeGenBase;

/** 1.20 ForgeRegistries backed by the 1.7.10 registries. */
public final class ForgeRegistries {

    private static final Map<ResourceKey<?>, IForgeRegistry<?>> BY_KEY = new HashMap<>();

    public static final IForgeRegistry<Block> BLOCKS = reg(new IForgeRegistry<Block>(Registries.BLOCK)
        .onRegister(Registration::block)
        .withFallback(LegacyIds::block, LegacyIds::blockKey));
    public static final IForgeRegistry<Item> ITEMS = reg(new IForgeRegistry<Item>(Registries.ITEM)
        .onRegister(Registration::item)
        .withFallback(LegacyIds::item, LegacyIds::itemKey));
    public static final IForgeRegistry<SoundEvent> SOUND_EVENTS = reg(new IForgeRegistry<SoundEvent>(Registries.SOUND_EVENT)
        .withFallback(SoundEvent::new, SoundEvent::getLocation));
    public static final IForgeRegistry<EntityType<?>> ENTITY_TYPES = reg(new IForgeRegistry<EntityType<?>>(Registries.ENTITY_TYPE)
        .onRegister(Registration::entity)
        .withFallback(EntityType::vanilla, EntityType::key));
    public static final IForgeRegistry<Potion> MOB_EFFECTS = reg(new IForgeRegistry<Potion>(Registries.MOB_EFFECT)
        .onRegister(Registration::effect)
        .withFallback(LegacyIds::effect, LegacyIds::effectKey));
    public static final IForgeRegistry<net.mcreator.boh.compat.mc.world.item.alchemy.BrewPotion> POTIONS = reg(
        new IForgeRegistry<net.mcreator.boh.compat.mc.world.item.alchemy.BrewPotion>(Registries.POTION)
            .onRegister(Registration::potion));
    public static final IForgeRegistry<Enchantment> ENCHANTMENTS = reg(new IForgeRegistry<Enchantment>(Registries.ENCHANTMENT)
        .onRegister(Registration::enchantment));
    public static final IForgeRegistry<ParticleType<?>> PARTICLE_TYPES = reg(new IForgeRegistry<ParticleType<?>>(Registries.PARTICLE_TYPE)
        .onRegister(Registration::particle));
    public static final IForgeRegistry<MenuType<?>> MENU_TYPES = reg(new IForgeRegistry<MenuType<?>>(Registries.MENU)
        .onRegister(Registration::menu));
    public static final IForgeRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = reg(
        new IForgeRegistry<BlockEntityType<?>>(Registries.BLOCK_ENTITY_TYPE).onRegister(Registration::blockEntity));
    public static final IForgeRegistry<PaintingVariant> PAINTING_VARIANTS = reg(new IForgeRegistry<PaintingVariant>(Registries.PAINTING_VARIANT)
        .onRegister(Registration::painting));
    public static final IForgeRegistry<VillagerProfession> VILLAGER_PROFESSIONS = reg(
        new IForgeRegistry<VillagerProfession>(Registries.VILLAGER_PROFESSION).onRegister(Registration::profession));
    public static final IForgeRegistry<PoiType> POI_TYPES = reg(new IForgeRegistry<PoiType>(Registries.POINT_OF_INTEREST_TYPE));
    public static final IForgeRegistry<CreativeModeTab> CREATIVE_MODE_TABS = reg(new IForgeRegistry<CreativeModeTab>(Registries.CREATIVE_MODE_TAB)
        .onRegister(Registration::tab));
    public static final IForgeRegistry<Feature<?>> FEATURES = reg(new IForgeRegistry<Feature<?>>(Registries.FEATURE));
    public static final IForgeRegistry<BiomeGenBase> BIOMES = reg(new IForgeRegistry<BiomeGenBase>(Registries.BIOME));

    private ForgeRegistries() {}

    private static <T> IForgeRegistry<T> reg(IForgeRegistry<T> r) {
        BY_KEY.put(r.getRegistryKey(), r);
        return r;
    }

    public static IForgeRegistry<?> byKey(ResourceKey<?> key) {
        IForgeRegistry<?> r = BY_KEY.get(key);
        if (r == null) {
            // registries the port only needs as name maps (structures, features, ...)
            r = reg(new IForgeRegistry<Object>(key));
        }
        return r;
    }

    public static final class Keys {

        public static final ResourceKey<?> BLOCKS = Registries.BLOCK;
        public static final ResourceKey<?> ITEMS = Registries.ITEM;
        public static final ResourceKey<?> ENTITY_TYPES = Registries.ENTITY_TYPE;
        public static final ResourceKey<?> BIOMES = Registries.BIOME;
        public static final ResourceKey<?> FEATURES = Registries.FEATURE;

        private Keys() {}
    }
}
