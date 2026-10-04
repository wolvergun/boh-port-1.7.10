package net.mcreator.boh.compat.client;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * CPU-side matrix stack mirroring the 1.20 PoseStack. Vertices are transformed in software and handed
 * to the 1.7.10 Tessellator, so whatever GL matrix is current when the stack is created acts as its origin.
 */
public class PoseStack {

    public static final class Pose {

        private final Matrix4f pose;
        private final Matrix3f normal;

        Pose(Matrix4f pose, Matrix3f normal) {
            this.pose = pose;
            this.normal = normal;
        }

        public Matrix4f pose() {
            return pose;
        }

        public Matrix3f normal() {
            return normal;
        }
    }

    private final Deque<Pose> stack = new ArrayDeque<>();

    public PoseStack() {
        stack.addLast(new Pose(new Matrix4f(), new Matrix3f()));
    }

    public void pushPose() {
        Pose p = stack.getLast();
        stack.addLast(new Pose(new Matrix4f(p.pose), new Matrix3f(p.normal)));
    }

    public void popPose() {
        if (stack.size() > 1) stack.removeLast();
    }

    public Pose last() {
        return stack.getLast();
    }

    public boolean clear() {
        return stack.size() == 1;
    }

    public void translate(double x, double y, double z) {
        stack.getLast().pose.translate((float) x, (float) y, (float) z);
    }

    public void translate(float x, float y, float z) {
        stack.getLast().pose.translate(x, y, z);
    }

    public void scale(float x, float y, float z) {
        Pose p = stack.getLast();
        p.pose.scale(x, y, z);
        if (x == y && y == z) {
            if (x < 0) p.normal.scale(-1, -1, -1);
            return;
        }
        p.normal.scale(x, y, z);
    }

    public void mulPose(Quaternionf q) {
        Pose p = stack.getLast();
        p.pose.rotate(q);
        p.normal.rotate(q);
    }

    public void mulPoseMatrix(Matrix4f m) {
        stack.getLast().pose.mul(m);
    }

    public void setIdentity() {
        Pose p = stack.getLast();
        p.pose.identity();
        p.normal.identity();
    }
}
