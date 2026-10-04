package net.mcreator.boh.compat.mc.world.entity;

import java.util.HashMap;
import java.util.Map;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.levelgen.Types;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public final class SpawnPlacements {
    private static final Map<EntityType<?>, SpawnPlacements.Data> DATA = new HashMap<>();

    private SpawnPlacements() {
    }

    public static <T extends Entity> void register(EntityType<T> type, Type placement, Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate) {
        DATA.put(type, new SpawnPlacements.Data(placement, heightmap, predicate));
    }

    public static SpawnPlacements.Data get(EntityType<?> type) {
        return DATA.get(type);
    }

    public static boolean checkSpawnRules(EntityType<?> type, World world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        SpawnPlacements.Data d = DATA.get(type);
        return d == null || d.predicate.test(type, world, reason, pos, random);
    }

    public static final class Data {
        public final Type placement;
        public final Types heightmap;
        public final SpawnPlacements.SpawnPredicate<?> predicate;

        Data(Type placement, Types heightmap, SpawnPlacements.SpawnPredicate<?> predicate) {
            this.placement = placement;
            this.heightmap = heightmap;
            this.predicate = predicate;
        }
    }

    @FunctionalInterface
    public interface SpawnPredicate<T extends Entity> {
        boolean test(EntityType<T> var1, World var2, MobSpawnType var3, BlockPos var4, RandomSource var5);
    }
}
