package net.mcreator.boh.geo;

/** Four vertices (x, y, z, u, v interleaved) and a face normal. */
public final class GeoQuad {

    /** 4 vertices x 5 floats: x, y, z, u, v. */
    final float[] data;
    final float nx, ny, nz;

    GeoQuad(float[] data, float nx, float ny, float nz) {
        this.data = data;
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
    }
}
