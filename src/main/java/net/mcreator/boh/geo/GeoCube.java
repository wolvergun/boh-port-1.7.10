package net.mcreator.boh.geo;

public final class GeoCube {
    final GeoQuad[] quads;
    final float pivotX;
    final float pivotY;
    final float pivotZ;
    final float rotX;
    final float rotY;
    final float rotZ;
    final float sizeX;
    final float sizeY;
    final float sizeZ;
    final boolean mirror;

    GeoCube(
        GeoQuad[] quads, float pivotX, float pivotY, float pivotZ, float rotX, float rotY, float rotZ, float sizeX, float sizeY, float sizeZ, boolean mirror
    ) {
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
