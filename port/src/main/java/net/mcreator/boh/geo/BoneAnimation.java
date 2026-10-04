package net.mcreator.boh.geo;

public final class BoneAnimation {

    final String boneName;
    final KeyframeStack rotation;
    final KeyframeStack position;
    final KeyframeStack scale;

    public BoneAnimation(String boneName, KeyframeStack rotation, KeyframeStack position, KeyframeStack scale) {
        this.boneName = boneName;
        this.rotation = rotation;
        this.position = position;
        this.scale = scale;
    }

    public String boneName() {
        return boneName;
    }
}
