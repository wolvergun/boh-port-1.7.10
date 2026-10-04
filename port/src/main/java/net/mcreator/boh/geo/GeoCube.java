package net.mcreator.boh.geo;

/** A baked cube: up to six quads plus its own pivot/rotation, already in render space (X mirrored). */
public final class GeoCube {

    final GeoQuad[] quads;
    final float pivotX, pivotY, pivotZ;
    final float rotX, rotY, rotZ;
    final float sizeX, sizeY, sizeZ;
    final boolean mirror;

    GeoCube(GeoQuad[] quads, float pivotX, float pivotY, float pivotZ, float rotX, float rotY, float rotZ,
        float sizeX, float sizeY, float sizeZ, boolean mirror) {
        this.quads = quads;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.pivotZ = pivotZ;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.mirror = mirror;
    }
}
