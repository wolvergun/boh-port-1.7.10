package net.mcreator.boh.geo;

import java.util.List;

public final class BakedGeoModel {

    private final List<GeoBone> topLevelBones;
    final float textureWidth, textureHeight;

    BakedGeoModel(List<GeoBone> topLevelBones, float textureWidth, float textureHeight) {
        this.topLevelBones = topLevelBones;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public List<GeoBone> topLevelBones() {
        return topLevelBones;
    }

    public List<GeoBone> getBones() {
        return topLevelBones;
    }

    public GeoBone getBone(String name) {
        for (GeoBone bone : topLevelBones) {
            GeoBone found = search(bone, name);
            if (found != null) return found;
        }
        return null;
    }

    private static GeoBone search(GeoBone bone, String name) {
        if (bone.getName().equals(name)) return bone;
        for (GeoBone child : bone.getChildBones()) {
            GeoBone found = search(child, name);
            if (found != null) return found;
        }
        return null;
    }
}
