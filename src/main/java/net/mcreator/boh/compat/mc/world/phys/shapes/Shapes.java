package net.mcreator.boh.compat.mc.world.phys.shapes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.mcreator.boh.compat.mc.world.phys.AABB;

public final class Shapes {
    private static final VoxelShape EMPTY = new VoxelShape(Collections.emptyList());
    private static final VoxelShape BLOCK = new VoxelShape(Collections.singletonList(new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)));

    private Shapes() {
    }

    public static VoxelShape empty() {
        return EMPTY;
    }

    public static VoxelShape block() {
        return BLOCK;
    }

    public static VoxelShape box(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new VoxelShape(Collections.singletonList(new AABB(x1, y1, z1, x2, y2, z2)));
    }

    public static VoxelShape create(AABB b) {
        return new VoxelShape(Collections.singletonList(b));
    }

    public static VoxelShape or(VoxelShape first, VoxelShape... others) {
        List<AABB> l = new ArrayList<>(first.boxes);

        for (VoxelShape s : others) {
            l.addAll(s.boxes);
        }

        return new VoxelShape(l);
    }

    public static VoxelShape or(VoxelShape a, VoxelShape b) {
        List<AABB> l = new ArrayList<>(a.boxes);
        l.addAll(b.boxes);
        return new VoxelShape(l);
    }

    public static VoxelShape join(VoxelShape a, VoxelShape b, Object op) {
        return or(a, b);
    }
}
