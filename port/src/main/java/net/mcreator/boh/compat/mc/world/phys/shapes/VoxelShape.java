package net.mcreator.boh.compat.mc.world.phys.shapes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.mcreator.boh.compat.mc.world.phys.AABB;

/** 1.20 VoxelShape as a list of boxes in block-local 0..1 coordinates. */
public class VoxelShape {

    final List<AABB> boxes;

    VoxelShape(List<AABB> boxes) {
        this.boxes = boxes;
    }

    public List<AABB> toAabbs() {
        return Collections.unmodifiableList(boxes);
    }

    public boolean isEmpty() {
        return boxes.isEmpty();
    }

    public AABB bounds() {
        if (boxes.isEmpty()) return new AABB(0, 0, 0, 0, 0, 0);
        AABB b = boxes.get(0);
        for (int i = 1; i < boxes.size(); i++) b = b.minmax(boxes.get(i));
        return b;
    }

    public VoxelShape move(double x, double y, double z) {
        List<AABB> out = new ArrayList<>(boxes.size());
        for (AABB b : boxes) out.add(b.move(x, y, z));
        return new VoxelShape(out);
    }

    public VoxelShape optimize() {
        return this;
    }

    public double max(net.mcreator.boh.compat.mc.core.Axis axis) {
        AABB b = bounds();
        return axis == net.mcreator.boh.compat.mc.core.Axis.X ? b.maxX : axis == net.mcreator.boh.compat.mc.core.Axis.Y ? b.maxY : b.maxZ;
    }

    public double min(net.mcreator.boh.compat.mc.core.Axis axis) {
        AABB b = bounds();
        return axis == net.mcreator.boh.compat.mc.core.Axis.X ? b.minX : axis == net.mcreator.boh.compat.mc.core.Axis.Y ? b.minY : b.minZ;
    }

    public boolean isFullBlock() {
        AABB b = bounds();
        return boxes.size() == 1 && b.minX <= 0 && b.minY <= 0 && b.minZ <= 0 && b.maxX >= 1 && b.maxY >= 1 && b.maxZ >= 1;
    }
}
