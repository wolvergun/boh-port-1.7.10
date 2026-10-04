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

/** 1.20 ModelPart: baked cubes + child parts, rendered through PoseStack/VertexConsumer. */
public final class ModelPart {

    public float x, y, z;
    public float xRot, yRot, zRot;
    public float xScale = 1, yScale = 1, zScale = 1;
    public boolean visible = true;
    public boolean skipDraw;
    private final List<Cube> cubes;
    private final Map<String, ModelPart> children;
    private PartPose initialPose = PartPose.ZERO;

    public ModelPart(List<Cube> cubes, Map<String, ModelPart> children) {
        this.cubes = cubes;
        this.children = children;
    }

    public PartPose storePose() {
        return PartPose.offsetAndRotation(x, y, z, xRot, yRot, zRot);
    }

    public PartPose getInitialPose() {
        return initialPose;
    }

    public void setInitialPose(PartPose p) {
        initialPose = p;
    }

    public void resetPose() {
        loadPose(initialPose);
    }

    public void loadPose(PartPose p) {
        x = p.x;
        y = p.y;
        z = p.z;
        xRot = p.xRot;
        yRot = p.yRot;
        zRot = p.zRot;
        xScale = yScale = zScale = 1;
    }

    public void copyFrom(ModelPart o) {
        xScale = o.xScale;
        yScale = o.yScale;
        zScale = o.zScale;
        xRot = o.xRot;
        yRot = o.yRot;
        zRot = o.zRot;
        x = o.x;
        y = o.y;
        z = o.z;
    }

    public boolean hasChild(String name) {
        return children.containsKey(name);
    }

    public ModelPart getChild(String name) {
        ModelPart p = children.get(name);
        if (p == null) throw new NoSuchElementException("Can't find part " + name);
        return p;
    }

    public void setPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRotation(float x, float y, float z) {
        xRot = x;
        yRot = y;
        zRot = z;
    }

    public List<ModelPart> getAllParts() {
        List<ModelPart> l = new ArrayList<>();
        l.add(this);
        for (ModelPart c : children.values()) l.addAll(c.getAllParts());
        return l;
    }

    public boolean isEmpty() {
        return cubes.isEmpty();
    }

    public void render(PoseStack pose, VertexConsumer vc, int light, int overlay) {
        render(pose, vc, light, overlay, 1, 1, 1, 1);
    }

    public void render(PoseStack pose, VertexConsumer vc, int light, int overlay, float r, float g, float b, float a) {
        if (!visible || cubes.isEmpty() && children.isEmpty()) return;
        pose.pushPose();
        translateAndRotate(pose);
        if (!skipDraw) compile(pose.last(), vc, light, overlay, r, g, b, a);
        for (ModelPart c : children.values()) c.render(pose, vc, light, overlay, r, g, b, a);
        pose.popPose();
    }

    public void translateAndRotate(PoseStack pose) {
        pose.translate(x / 16f, y / 16f, z / 16f);
        if (xRot != 0 || yRot != 0 || zRot != 0) {
            // ZYX order, as Quaternionf.rotationZYX
            pose.mulPose(new Quaternionf().rotationZ(zRot).mul(new Quaternionf().rotationY(yRot)).mul(new Quaternionf().rotationX(xRot)));
        }
        if (xScale != 1 || yScale != 1 || zScale != 1) pose.scale(xScale, yScale, zScale);
    }

    private void compile(PoseStack.Pose p, VertexConsumer vc, int light, int overlay, float r, float g, float b, float a) {
        Matrix4f m = p.pose();
        Matrix3f n = p.normal();
        for (Cube cube : cubes) {
            for (Polygon poly : cube.polygons) {
                float nx = n.m00 * poly.nx + n.m01 * poly.ny + n.m02 * poly.nz;
                float ny = n.m10 * poly.nx + n.m11 * poly.ny + n.m12 * poly.nz;
                float nz = n.m20 * poly.nx + n.m21 * poly.ny + n.m22 * poly.nz;
                for (Vertex v : poly.vertices) {
                    float vx = v.x / 16f, vy = v.y / 16f, vz = v.z / 16f;
                    vc.vertex(m.transformX(vx, vy, vz), m.transformY(vx, vy, vz), m.transformZ(vx, vy, vz), r, g, b, a, v.u, v.v,
                        overlay, light, nx, ny, nz);
                }
            }
        }
    }

    /** A baked box. */
    public static final class Cube {

        final Polygon[] polygons;
        public final float minX, minY, minZ, maxX, maxY, maxZ;

        public Cube(int texU, int texV, float x, float y, float z, float w, float h, float d, float gx, float gy, float gz,
            boolean mirror, float texW, float texH) {
            minX = x;
            minY = y;
            minZ = z;
            maxX = x + w;
            maxY = y + h;
            maxZ = z + d;
            float f = x + w, f1 = y + h, f2 = z + d;
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
            Vertex v0 = new Vertex(x, y, z, 0, 0), v1 = new Vertex(f, y, z, 0, 8), v2 = new Vertex(f, f1, z, 8, 8),
                v3 = new Vertex(x, f1, z, 8, 0), v4 = new Vertex(x, y, f2, 0, 0), v5 = new Vertex(f, y, f2, 0, 8),
                v6 = new Vertex(f, f1, f2, 8, 8), v7 = new Vertex(x, f1, f2, 8, 0);
            float u0 = texU, u1 = texU + d, u2 = texU + d + w, u3 = texU + d + w + w, u4 = texU + d + w + d,
                u5 = texU + d + w + d + w;
            float t0 = texV, t1 = texV + d, t2 = texV + d + h;
            polygons = new Polygon[] { new Polygon(new Vertex[] { v5, v4, v0, v1 }, u1, t0, u2, t1, texW, texH, mirror, 0, -1, 0),
                new Polygon(new Vertex[] { v2, v3, v7, v6 }, u2, t1, u3, t0, texW, texH, mirror, 0, 1, 0),
                new Polygon(new Vertex[] { v0, v4, v7, v3 }, u0, t1, u1, t2, texW, texH, mirror, -1, 0, 0),
                new Polygon(new Vertex[] { v1, v0, v3, v2 }, u1, t1, u2, t2, texW, texH, mirror, 0, 0, -1),
                new Polygon(new Vertex[] { v5, v1, v2, v6 }, u2, t1, u4, t2, texW, texH, mirror, 1, 0, 0),
                new Polygon(new Vertex[] { v4, v5, v6, v7 }, u4, t1, u5, t2, texW, texH, mirror, 0, 0, 1) };
        }
    }

    static final class Polygon {

        final Vertex[] vertices;
        final float nx, ny, nz;

        Polygon(Vertex[] vs, float u1, float v1, float u2, float v2, float texW, float texH, boolean mirror, float nx, float ny,
            float nz) {
            vs[0] = vs[0].remap(u2 / texW, v1 / texH);
            vs[1] = vs[1].remap(u1 / texW, v1 / texH);
            vs[2] = vs[2].remap(u1 / texW, v2 / texH);
            vs[3] = vs[3].remap(u2 / texW, v2 / texH);
            if (mirror) {
                for (int i = 0; i < vs.length / 2; i++) {
                    Vertex t = vs[i];
                    vs[i] = vs[vs.length - 1 - i];
                    vs[vs.length - 1 - i] = t;
                }
                nx = -nx;
            }
            vertices = vs;
            this.nx = nx;
            this.ny = ny;
            this.nz = nz;
        }
    }

    static final class Vertex {

        final float x, y, z, u, v;

        Vertex(float x, float y, float z, float u, float v) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.u = u;
            this.v = v;
        }

        Vertex remap(float u, float v) {
            return new Vertex(x, y, z, u, v);
        }
    }

    static List<Cube> noCubes() {
        return Collections.emptyList();
    }
}
