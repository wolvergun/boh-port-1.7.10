package net.mcreator.boh.compat.forge.registries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.minecraft.util.ResourceLocation;

public class ITagManager<T> {
    private final IForgeRegistry<T> registry;

    ITagManager(IForgeRegistry<T> registry) {
        this.registry = registry;
    }

    public ITagManager.ITag<T> getTag(TagKey<T> key) {
        List<T> values = new ArrayList<>();

        for (String id : key.members()) {
            T v = this.registry.getValue(new ResourceLocation(id));
            if (v != null) {
                values.add(v);
            }
        }

        return new ITagManager.ITag<>(values);
    }

    public TagKey<T> createTagKey(ResourceLocation location) {
        return TagKey.create(this.registry.getRegistryKey(), location);
    }

    public static final class ITag<T> implements Iterable<T> {
        private final List<T> values;

        ITag(List<T> values) {
            this.values = values;
        }

        public Optional<T> getRandomElement(Random random) {
            return this.values.isEmpty() ? Optional.empty() : Optional.of(this.values.get(random.nextInt(this.values.size())));
        }

        public boolean contains(T value) {
            return this.values.contains(value);
        }

        public boolean isEmpty() {
            return this.values.isEmpty();
        }

        public int size() {
            return this.values.size();
        }

        public Stream<T> stream() {
            return this.values.stream();
        }

        @Override
        public Iterator<T> iterator() {
            return this.values.iterator();
        }
    }
}
