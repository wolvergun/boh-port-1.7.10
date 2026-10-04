package net.mcreator.boh.compat.mc.client.model.geom.builders;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.boh.compat.mc.client.model.geom.ModelPart;

public final class CubeListBuilder {
    final List<CubeListBuilder.Def> cubes = new ArrayList<>();
    private int u;
    private int v;
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
        return this.mirror(true);
    }

    public CubeListBuilder mirror(boolean m) {
        this.mirror = m;
        return this;
    }

    public CubeListBuilder addBox(float x, float y, float z, float w, float h, float d) {
        return this.addBox(x, y, z, w, h, d, CubeDeformation.NONE);
    }

    public CubeListBuilder addBox(float x, float y, float z, float w, float h, float d, boolean mirror) {
        this.cubes.add(new CubeListBuilder.Def(this.u, this.v, x, y, z, w, h, d, CubeDeformation.NONE, mirror));
        return this;
    }

    public CubeListBuilder addBox(float x, float y, float z, float w, float h, float d, CubeDeformation grow) {
        this.cubes.add(new CubeListBuilder.Def(this.u, this.v, x, y, z, w, h, d, grow, this.mirror));
        return this;
    }

    public CubeListBuilder addBox(String name, float x, float y, float z, int w, int h, int d, CubeDeformation grow, int u, int v) {
        this.cubes.add(new CubeListBuilder.Def(u, v, x, y, z, w, h, d, grow, this.mirror));
        return this;
    }

    public CubeListBuilder addBox(String name, float x, float y, float z, float w, float h, float d) {
        return this.addBox(x, y, z, w, h, d);
    }

    public CubeListBuilder addBox(String name, float x, float y, float z, float w, float h, float d, CubeDeformation grow) {
        return this.addBox(x, y, z, w, h, d, grow);
    }

    static final class Def {
        final int u;
        final int v;
        final float x;
        final float y;
        final float z;
        final float w;
        final float h;
        final float d;
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
            return new ModelPart.Cube(
                this.u, this.v, this.x, this.y, this.z, this.w, this.h, this.d, this.grow.growX, this.grow.growY, this.grow.growZ, this.mirror, texW, texH
            );
        }
    }
}
