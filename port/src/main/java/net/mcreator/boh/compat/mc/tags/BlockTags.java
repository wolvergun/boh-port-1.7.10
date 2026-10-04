package net.mcreator.boh.compat.mc.tags;

import net.minecraft.util.ResourceLocation;

public final class BlockTags {

    public static final TagKey<Object> LEAVES = create(new ResourceLocation("minecraft", "leaves"));
    public static final TagKey<Object> LOGS = create(new ResourceLocation("minecraft", "logs"));

    private BlockTags() {}

    public static <T> TagKey<T> create(ResourceLocation location) {
        return TagKey.of("blocks", location);
    }
}
