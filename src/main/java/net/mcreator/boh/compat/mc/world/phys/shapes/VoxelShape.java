package net.mcreator.boh.compat.mc.world.phys.shapes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.mcreator.boh.compat.mc.core.Axis;
import net.mcreator.boh.compat.mc.world.phys.AABB;

public class VoxelShape {
    final List<AABB> boxes;

    VoxelShape(List<AABB> boxes) {
        this.boxes = boxes;
    }

    public List<AABB> toAabbs() {
        return Collections.unmodifiableList(this.boxes);
    }

    public boolean isEmpty() {
        return this.boxes.isEmpty();
    }

    public AABB bounds() {
        if (this.boxes.isEmpty()) {
            return new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        } else {
            AABB b = this.boxes.get(0);

            for (int i = 1; i < this.boxes.size(); i++) {
                b = b.minmax(this.boxes.get(i));
            }

            return b;
        }
    }

    public VoxelShape move(double x, double y, double z) {
        List<AABB> out = new ArrayList<>(this.boxes.size());

        for (AABB b : this.boxes) {
            out.add(b.move(x, y, z));
        }

        return new VoxelShape(out);
    }

    public VoxelShape optimize() {
        return this;
    }

    public double max(Axis axis) {
        AABB b = this.bounds();
        return axis == Axis.X ? b.maxX : (axis == Axis.Y ? b.maxY : b.maxZ);
    }

    public double min(Axis axis) {
        AABB b = this.bounds();
        return axis == Axis.X ? b.minX : (axis == Axis.Y ? b.minY : b.minZ);
    }

    public boolean isFullBlock() {
        AABB b = this.bounds();
        return this.boxes.size() == 1 && b.minX <= 0.0 && b.minY <= 0.0 && b.minZ <= 0.0 && b.maxX >= 1.0 && b.maxY >= 1.0 && b.maxZ >= 1.0;
    }
}
