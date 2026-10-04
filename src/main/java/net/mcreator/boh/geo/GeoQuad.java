package net.mcreator.boh.geo;

public final class GeoQuad {
    final float[] data;
    final float nx;
    final float ny;
    final float nz;

    GeoQuad(float[] data, float nx, float ny, float nz) {
        this.data = data;
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
    }
}
