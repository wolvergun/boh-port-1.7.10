package net.mcreator.boh.compat.forge.registries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import net.mcreator.boh.compat.mc.tags.TagKey;
import net.minecraft.util.ResourceLocation;

/** 1.20 ITagManager over an {@link IForgeRegistry}. */
public class ITagManager<T> {

    private final IForgeRegistry<T> registry;

    ITagManager(IForgeRegistry<T> registry) {
        this.registry = registry;
    }

    public ITag<T> getTag(TagKey<T> key) {
        List<T> values = new ArrayList<>();
        for (String id : key.members()) {
            T v = registry.getValue(new ResourceLocation(id));
            if (v != null) values.add(v);
        }
        return new ITag<>(values);
    }

    public TagKey<T> createTagKey(ResourceLocation location) {
        return TagKey.create(registry.getRegistryKey(), location);
    }

    public static final class ITag<T> implements Iterable<T> {

        private final List<T> values;

        ITag(List<T> values) {
            this.values = values;
        }

        public Optional<T> getRandomElement(Random random) {
            return values.isEmpty() ? Optional.empty() : Optional.of(values.get(random.nextInt(values.size())));
        }

        public boolean contains(T value) {
            return values.contains(value);
        }

        public boolean isEmpty() {
            return values.isEmpty();
        }

        public int size() {
            return values.size();
        }

        public Stream<T> stream() {
            return values.stream();
        }

        @Override
        public java.util.Iterator<T> iterator() {
            return values.iterator();
        }
    }
}
