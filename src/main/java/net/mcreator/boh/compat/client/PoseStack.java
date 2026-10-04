package net.mcreator.boh.compat.client;

import java.util.ArrayDeque;
import java.util.Deque;

public class PoseStack {
    private final Deque<PoseStack.Pose> stack = new ArrayDeque<>();

    public PoseStack() {
        this.stack.addLast(new PoseStack.Pose(new Matrix4f(), new Matrix3f()));
    }

    public void pushPose() {
        PoseStack.Pose p = this.stack.getLast();
        this.stack.addLast(new PoseStack.Pose(new Matrix4f(p.pose), new Matrix3f(p.normal)));
    }

    public void popPose() {
        if (this.stack.size() > 1) {
            this.stack.removeLast();
        }
    }

    public PoseStack.Pose last() {
        return this.stack.getLast();
    }

    public boolean clear() {
        return this.stack.size() == 1;
    }

    public void translate(double x, double y, double z) {
        this.stack.getLast().pose.translate((float)x, (float)y, (float)z);
    }

    public void translate(float x, float y, float z) {
        this.stack.getLast().pose.translate(x, y, z);
    }

    public void scale(float x, float y, float z) {
        PoseStack.Pose p = this.stack.getLast();
        p.pose.scale(x, y, z);
        if (x == y && y == z) {
            if (x < 0.0F) {
                p.normal.scale(-1.0F, -1.0F, -1.0F);
            }
        } else {
            p.normal.scale(x, y, z);
        }
    }

    public void mulPose(Quaternionf q) {
        PoseStack.Pose p = this.stack.getLast();
        p.pose.rotate(q);
        p.normal.rotate(q);
    }

    public void mulPoseMatrix(Matrix4f m) {
        this.stack.getLast().pose.mul(m);
    }

    public void setIdentity() {
        PoseStack.Pose p = this.stack.getLast();
        p.pose.identity();
        p.normal.identity();
    }

    public static final class Pose {
        private final Matrix4f pose;
        private final Matrix3f normal;

        Pose(Matrix4f pose, Matrix3f normal) {
            this.pose = pose;
            this.normal = normal;
        }

        public Matrix4f pose() {
            return this.pose;
        }

        public Matrix3f normal() {
            return this.normal;
        }
    }
}
