package net.mcreator.boh.compat.mc.client.model.geom.builders;

import java.util.ArrayList;
import java.util.List;

import net.mcreator.boh.compat.mc.client.model.geom.ModelPart;

/** 1.20 CubeListBuilder. */
public final class CubeListBuilder {

    static final class Def {

        final int u, v;
        final float x, y, z, w, h, d;
        final CubeDeformation grow;
        final boolean mirror;

        Def(int u, int v, float x, float y, float z, float w, float h, float d, CubeDeformation grow, boolean mirror) {
            this.u = u;
            this.v = v;
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
            this.h = h;
            this.d = d;
            this.grow = grow;
            this.mirror = mirror;
        }

        ModelPart.Cube bake(int texW, int texH) {
            return new ModelPart.Cube(u, v, x, y, z, w, h, d, grow.growX, grow.growY, grow.growZ, mirror, texW, texH);
        }
    }

    final List<Def> cubes = new ArrayList<>();
    private int u, v;
    private boolean mirror;

    public static CubeListBuilder create() {
        return new CubeListBuilder();
    }

    public CubeListBuilder texOffs(int u, int v) {
        this.u = u;
        this.v = v;
        return this;
    }

    public CubeListBuilder mirror() {
        return mirror(true);
    }

    public CubeListBuilder mirror(boolean m) {
        mirror = m;
        return this;
    }

    public CubeListBuilder addBox(float x, float y, float z, float w, float h, float d) {
        return addBox(x, y, z, w, h, d, CubeDeformation.NONE);
    }

    public CubeListBuilder addBox(float x, float y, float z, float w, float h, float d, boolean mirror) {
        cubes.add(new Def(u, v, x, y, z, w, h, d, CubeDeformation.NONE, mirror));
        return this;
    }

    public CubeListBuilder addBox(float x, float y, float z, float w, float h, float d, CubeDeformation grow) {
        cubes.add(new Def(u, v, x, y, z, w, h, d, grow, mirror));
        return this;
    }

    public CubeListBuilder addBox(String name, float x, float y, float z, int w, int h, int d, CubeDeformation grow, int u, int v) {
        cubes.add(new Def(u, v, x, y, z, w, h, d, grow, mirror));
        return this;
    }

    public CubeListBuilder addBox(String name, float x, float y, float z, float w, float h, float d) {
        return addBox(x, y, z, w, h, d);
    }

    public CubeListBuilder addBox(String name, float x, float y, float z, float w, float h, float d, CubeDeformation grow) {
        return addBox(x, y, z, w, h, d, grow);
    }
}
