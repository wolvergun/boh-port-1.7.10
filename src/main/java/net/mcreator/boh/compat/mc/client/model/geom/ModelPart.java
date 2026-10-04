package net.mcreator.boh.compat.mc.client.model.geom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import net.mcreator.boh.compat.client.Matrix3f;
import net.mcreator.boh.compat.client.Matrix4f;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.Quaternionf;
import net.mcreator.boh.compat.client.VertexConsumer;

public final class ModelPart {
    public float x;
    public float y;
    public float z;
    public float xRot;
    public float yRot;
    public float zRot;
    public float xScale = 1.0F;
    public float yScale = 1.0F;
    public float zScale = 1.0F;
    public boolean visible = true;
    public boolean skipDraw;
    private final List<ModelPart.Cube> cubes;
    private final Map<String, ModelPart> children;
    private PartPose initialPose = PartPose.ZERO;

    public ModelPart(List<ModelPart.Cube> cubes, Map<String, ModelPart> children) {
        this.cubes = cubes;
        this.children = children;
    }

    public PartPose storePose() {
        return PartPose.offsetAndRotation(this.x, this.y, this.z, this.xRot, this.yRot, this.zRot);
    }

    public PartPose getInitialPose() {
        return this.initialPose;
    }

    public void setInitialPose(PartPose p) {
        this.initialPose = p;
    }

    public void resetPose() {
        this.loadPose(this.initialPose);
    }

    public void loadPose(PartPose p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
        this.xRot = p.xRot;
        this.yRot = p.yRot;
        this.zRot = p.zRot;
        this.xScale = this.yScale = this.zScale = 1.0F;
    }

    public void copyFrom(ModelPart o) {
        this.xScale = o.xScale;
        this.yScale = o.yScale;
        this.zScale = o.zScale;
        this.xRot = o.xRot;
        this.yRot = o.yRot;
        this.zRot = o.zRot;
        this.x = o.x;
        this.y = o.y;
        this.z = o.z;
    }

    public boolean hasChild(String name) {
        return this.children.containsKey(name);
    }

    public ModelPart getChild(String name) {
        ModelPart p = this.children.get(name);
        if (p == null) {
            throw new NoSuchElementException("Can't find part " + name);
        } else {
            return p;
        }
    }

    public void setPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRotation(float x, float y, float z) {
        this.xRot = x;
        this.yRot = y;
        this.zRot = z;
    }

    public List<ModelPart> getAllParts() {
        List<ModelPart> l = new ArrayList<>();
        l.add(this);

        for (ModelPart c : this.children.values()) {
            l.addAll(c.getAllParts());
        }

        return l;
    }

    public boolean isEmpty() {
        return this.cubes.isEmpty();
    }

    public void render(PoseStack pose, VertexConsumer vc, int light, int overlay) {
        this.render(pose, vc, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void render(PoseStack pose, VertexConsumer vc, int light, int overlay, float r, float g, float b, float a) {
        if (this.visible && (!this.cubes.isEmpty() || !this.children.isEmpty())) {
            pose.pushPose();
            this.translateAndRotate(pose);
            if (!this.skipDraw) {
                this.compile(pose.last(), vc, light, overlay, r, g, b, a);
            }

            for (ModelPart c : this.children.values()) {
                c.render(pose, vc, light, overlay, r, g, b, a);
            }

            pose.popPose();
        }
    }

    public void translateAndRotate(PoseStack pose) {
        pose.translate(this.x / 16.0F, this.y / 16.0F, this.z / 16.0F);
        if (this.xRot != 0.0F || this.yRot != 0.0F || this.zRot != 0.0F) {
            pose.mulPose(new Quaternionf().rotationZ(this.zRot).mul(new Quaternionf().rotationY(this.yRot)).mul(new Quaternionf().rotationX(this.xRot)));
        }

        if (this.xScale != 1.0F || this.yScale != 1.0F || this.zScale != 1.0F) {
            pose.scale(this.xScale, this.yScale, this.zScale);
        }
    }

    private void compile(PoseStack.Pose p, VertexConsumer vc, int light, int overlay, float r, float g, float b, float a) {
        Matrix4f m = p.pose();
        Matrix3f n = p.normal();

        for (ModelPart.Cube cube : this.cubes) {
            for (ModelPart.Polygon poly : cube.polygons) {
                float nx = n.m00 * poly.nx + n.m01 * poly.ny + n.m02 * poly.nz;
                float ny = n.m10 * poly.nx + n.m11 * poly.ny + n.m12 * poly.nz;
                float nz = n.m20 * poly.nx + n.m21 * poly.ny + n.m22 * poly.nz;

                for (ModelPart.Vertex v : poly.vertices) {
                    float vx = v.x / 16.0F;
                    float vy = v.y / 16.0F;
                    float vz = v.z / 16.0F;
                    vc.vertex(m.transformX(vx, vy, vz), m.transformY(vx, vy, vz), m.transformZ(vx, vy, vz), r, g, b, a, v.u, v.v, overlay, light, nx, ny, nz);
                }
            }
        }
    }

    static List<ModelPart.Cube> noCubes() {
        return Collections.emptyList();
    }

    public static final class Cube {
        final ModelPart.Polygon[] polygons;
        public final float minX;
        public final float minY;
        public final float minZ;
        public final float maxX;
        public final float maxY;
        public final float maxZ;

        public Cube(
            int texU, int texV, float x, float y, float z, float w, float h, float d, float gx, float gy, float gz, boolean mirror, float texW, float texH
        ) {
            this.minX = x;
            this.minY = y;
            this.minZ = z;
            this.maxX = x + w;
            this.maxY = y + h;
            this.maxZ = z + d;
            float f = x + w;
            float f1 = y + h;
            float f2 = z + d;
            x -= gx;
            y -= gy;
            z -= gz;
            f += gx;
            f1 += gy;
            f2 += gz;
            if (mirror) {
                float t = f;
                f = x;
                x = t;
            }

            ModelPart.Vertex v0 = new ModelPart.Vertex(x, y, z, 0.0F, 0.0F);
            ModelPart.Vertex v1 = new ModelPart.Vertex(f, y, z, 0.0F, 8.0F);
            ModelPart.Vertex v2 = new ModelPart.Vertex(f, f1, z, 8.0F, 8.0F);
            ModelPart.Vertex v3 = new ModelPart.Vertex(x, f1, z, 8.0F, 0.0F);
            ModelPart.Vertex v4 = new ModelPart.Vertex(x, y, f2, 0.0F, 0.0F);
            ModelPart.Vertex v5 = new ModelPart.Vertex(f, y, f2, 0.0F, 8.0F);
            ModelPart.Vertex v6 = new ModelPart.Vertex(f, f1, f2, 8.0F, 8.0F);
            ModelPart.Vertex v7 = new ModelPart.Vertex(x, f1, f2, 8.0F, 0.0F);
            float u0 = texU;
            float u1 = texU + d;
            float u2 = texU + d + w;
            float u3 = texU + d + w + w;
            float u4 = texU + d + w + d;
            float u5 = texU + d + w + d + w;
            float t0 = texV;
            float t1 = texV + d;
            float t2 = texV + d + h;
            this.polygons = new ModelPart.Polygon[]{
                new ModelPart.Polygon(new ModelPart.Vertex[]{v5, v4, v0, v1}, u1, t0, u2, t1, texW, texH, mirror, 0.0F, -1.0F, 0.0F),
                new ModelPart.Polygon(new ModelPart.Vertex[]{v2, v3, v7, v6}, u2, t1, u3, t0, texW, texH, mirror, 0.0F, 1.0F, 0.0F),
                new ModelPart.Polygon(new ModelPart.Vertex[]{v0, v4, v7, v3}, u0, t1, u1, t2, texW, texH, mirror, -1.0F, 0.0F, 0.0F),
                new ModelPart.Polygon(new ModelPart.Vertex[]{v1, v0, v3, v2}, u1, t1, u2, t2, texW, texH, mirror, 0.0F, 0.0F, -1.0F),
                new ModelPart.Polygon(new ModelPart.Vertex[]{v5, v1, v2, v6}, u2, t1, u4, t2, texW, texH, mirror, 1.0F, 0.0F, 0.0F),
                new ModelPart.Polygon(new ModelPart.Vertex[]{v4, v5, v6, v7}, u4, t1, u5, t2, texW, texH, mirror, 0.0F, 0.0F, 1.0F)
            };
        }
    }

    static final class Polygon {
        final ModelPart.Vertex[] vertices;
        final float nx;
        final float ny;
        final float nz;

        Polygon(ModelPart.Vertex[] vs, float u1, float v1, float u2, float v2, float texW, float texH, boolean mirror, float nx, float ny, float nz) {
            vs[0] = vs[0].remap(u2 / texW, v1 / texH);
            vs[1] = vs[1].remap(u1 / texW, v1 / texH);
            vs[2] = vs[2].remap(u1 / texW, v2 / texH);
            vs[3] = vs[3].remap(u2 / texW, v2 / texH);
            if (mirror) {
                for (int i = 0; i < vs.length / 2; i++) {
                    ModelPart.Vertex t = vs[i];
                    vs[i] = vs[vs.length - 1 - i];
                    vs[vs.length - 1 - i] = t;
                }

                nx = -nx;
            }

            this.vertices = vs;
            this.nx = nx;
            this.ny = ny;
            this.nz = nz;
        }
    }

    static final class Vertex {
        final float x;
        final float y;
        final float z;
        final float u;
        final float v;

        Vertex(float x, float y, float z, float u, float v) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.u = u;
            this.v = v;
        }

        ModelPart.Vertex remap(float u, float v) {
            return new ModelPart.Vertex(this.x, this.y, this.z, u, v);
        }
    }
}
