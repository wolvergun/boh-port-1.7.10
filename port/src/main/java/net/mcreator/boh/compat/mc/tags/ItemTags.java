package net.mcreator.boh.compat.mc.tags;

import net.minecraft.util.ResourceLocation;

public final class ItemTags {

    public static final TagKey<Object> MUSIC_DISCS = create(new ResourceLocation("minecraft", "music_discs"));
    public static final TagKey<Object> WOOL = create(new ResourceLocation("minecraft", "wool"));

    private ItemTags() {}

    public static <T> TagKey<T> create(ResourceLocation location) {
        return TagKey.of("items", location);
    }
}
