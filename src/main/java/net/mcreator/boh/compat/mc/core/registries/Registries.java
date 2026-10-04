package net.mcreator.boh.compat.mc.core.registries;

import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;

public final class Registries {
    public static final ResourceKey<?> BLOCK = key("block");
    public static final ResourceKey<?> BLOCKS = BLOCK;
    public static final ResourceKey<?> ITEM = key("item");
    public static final ResourceKey<?> ITEMS = ITEM;
    public static final ResourceKey<?> SOUND_EVENT = key("sound_event");
    public static final ResourceKey<?> SOUND_EVENTS = SOUND_EVENT;
    public static final ResourceKey<?> ENTITY_TYPE = key("entity_type");
    public static final ResourceKey<?> ENTITY_TYPES = ENTITY_TYPE;
    public static final ResourceKey<?> MOB_EFFECT = key("mob_effect");
    public static final ResourceKey<?> MOB_EFFECTS = MOB_EFFECT;
    public static final ResourceKey<?> POTION = key("potion");
    public static final ResourceKey<?> POTIONS = POTION;
    public static final ResourceKey<?> ENCHANTMENT = key("enchantment");
    public static final ResourceKey<?> ENCHANTMENTS = ENCHANTMENT;
    public static final ResourceKey<?> PARTICLE_TYPE = key("particle_type");
    public static final ResourceKey<?> PARTICLE_TYPES = PARTICLE_TYPE;
    public static final ResourceKey<?> MENU = key("menu");
    public static final ResourceKey<?> MENU_TYPES = MENU;
    public static final ResourceKey<?> BLOCK_ENTITY_TYPE = key("block_entity_type");
    public static final ResourceKey<?> BLOCK_ENTITY_TYPES = BLOCK_ENTITY_TYPE;
    public static final ResourceKey<?> PAINTING_VARIANT = key("painting_variant");
    public static final ResourceKey<?> PAINTING_VARIANTS = PAINTING_VARIANT;
    public static final ResourceKey<?> VILLAGER_PROFESSION = key("villager_profession");
    public static final ResourceKey<?> VILLAGER_PROFESSIONS = VILLAGER_PROFESSION;
    public static final ResourceKey<?> POINT_OF_INTEREST_TYPE = key("point_of_interest_type");
    public static final ResourceKey<?> POI_TYPES = POINT_OF_INTEREST_TYPE;
    public static final ResourceKey<?> CREATIVE_MODE_TAB = key("creative_mode_tab");
    public static final ResourceKey<?> FEATURE = key("worldgen/feature");
    public static final ResourceKey<?> CONFIGURED_FEATURE = key("worldgen/configured_feature");
    public static final ResourceKey<?> FEATURES = FEATURE;
    public static final ResourceKey<?> BIOME = key("worldgen/biome");
    public static final ResourceKey<?> BIOMES = BIOME;
    public static final ResourceKey<?> DAMAGE_TYPE = key("damage_type");
    public static final ResourceKey<?> DIMENSION = key("dimension");
    public static final ResourceKey<?> DIMENSION_TYPE = key("dimension_type");
    public static final ResourceKey<?> LEVEL_STEM = key("dimension");
    public static final ResourceKey<?> STRUCTURE = key("worldgen/structure");

    private Registries() {
    }

    private static ResourceKey<?> key(String name) {
        return ResourceKey.createRegistryKey(new ResourceLocation("minecraft", name));
    }
}
