package net.mcreator.boh.geo;

import java.util.List;

public final class BakedGeoModel {
    private final List<GeoBone> topLevelBones;
    final float textureWidth;
    final float textureHeight;

    BakedGeoModel(List<GeoBone> topLevelBones, float textureWidth, float textureHeight) {
        this.topLevelBones = topLevelBones;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public List<GeoBone> topLevelBones() {
        return this.topLevelBones;
    }

    public List<GeoBone> getBones() {
        return this.topLevelBones;
    }

    public GeoBone getBone(String name) {
        for (GeoBone bone : this.topLevelBones) {
            GeoBone found = search(bone, name);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    private static GeoBone search(GeoBone bone, String name) {
        if (bone.getName().equals(name)) {
            return bone;
        } else {
            for (GeoBone child : bone.getChildBones()) {
                GeoBone found = search(child, name);
                if (found != null) {
                    return found;
                }
            }

            return null;
        }
    }
}
