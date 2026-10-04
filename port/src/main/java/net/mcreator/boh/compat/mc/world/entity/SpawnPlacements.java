package net.mcreator.boh.compat.mc.world.entity;

import java.util.HashMap;
import java.util.Map;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.levelgen.Types;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/** Per-type natural spawn rules; the compat base entities consult them from getCanSpawnHere(). */
public final class SpawnPlacements {

    @FunctionalInterface
    public interface SpawnPredicate<T extends Entity> {

        boolean test(EntityType<T> type, World world, MobSpawnType reason, BlockPos pos, RandomSource random);
    }

    public static final class Data {

        public final Type placement;
        public final Types heightmap;
        public final SpawnPredicate<?> predicate;

        Data(Type placement, Types heightmap, SpawnPredicate<?> predicate) {
            this.placement = placement;
            this.heightmap = heightmap;
            this.predicate = predicate;
        }
    }

    private static final Map<EntityType<?>, Data> DATA = new HashMap<>();

    private SpawnPlacements() {}

    public static <T extends Entity> void register(EntityType<T> type, Type placement, Types heightmap,
        SpawnPredicate<T> predicate) {
        DATA.put(type, new Data(placement, heightmap, predicate));
    }

    public static Data get(EntityType<?> type) {
        return DATA.get(type);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean checkSpawnRules(EntityType<?> type, World world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        Data d = DATA.get(type);
        return d == null || ((SpawnPredicate) d.predicate).test(type, world, reason, pos, random);
    }
}
