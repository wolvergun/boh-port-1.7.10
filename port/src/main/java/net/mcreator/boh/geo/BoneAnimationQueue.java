package net.mcreator.boh.geo;

import java.util.LinkedList;

final class BoneAnimationQueue {

    final CoreGeoBone bone;
    final LinkedList<AnimationPoint> rotX = new LinkedList<>(), rotY = new LinkedList<>(), rotZ = new LinkedList<>();
    final LinkedList<AnimationPoint> posX = new LinkedList<>(), posY = new LinkedList<>(), posZ = new LinkedList<>();
    final LinkedList<AnimationPoint> scaleX = new LinkedList<>(), scaleY = new LinkedList<>(),
        scaleZ = new LinkedList<>();

    BoneAnimationQueue(CoreGeoBone bone) {
        this.bone = bone;
    }

    void addNextPosition(double tick, double length, BoneSnapshot start, AnimationPoint x, AnimationPoint y,
        AnimationPoint z) {
        posX.add(new AnimationPoint(null, tick, length, start.getOffsetX(), x.animationStartValue));
        posY.add(new AnimationPoint(null, tick, length, start.getOffsetY(), y.animationStartValue));
        posZ.add(new AnimationPoint(null, tick, length, start.getOffsetZ(), z.animationStartValue));
    }

    void addNextScale(double tick, double length, BoneSnapshot start, AnimationPoint x, AnimationPoint y,
        AnimationPoint z) {
        scaleX.add(new AnimationPoint(null, tick, length, start.getScaleX(), x.animationStartValue));
        scaleY.add(new AnimationPoint(null, tick, length, start.getScaleY(), y.animationStartValue));
        scaleZ.add(new AnimationPoint(null, tick, length, start.getScaleZ(), z.animationStartValue));
    }

    void addNextRotation(double tick, double length, BoneSnapshot start, BoneSnapshot initial, AnimationPoint x,
        AnimationPoint y, AnimationPoint z) {
        rotX.add(new AnimationPoint(null, tick, length, start.getRotX() - initial.getRotX(), x.animationStartValue));
        rotY.add(new AnimationPoint(null, tick, length, start.getRotY() - initial.getRotY(), y.animationStartValue));
        rotZ.add(new AnimationPoint(null, tick, length, start.getRotZ() - initial.getRotZ(), z.animationStartValue));
    }

    void addRotations(AnimationPoint x, AnimationPoint y, AnimationPoint z) {
        rotX.add(x);
        rotY.add(y);
        rotZ.add(z);
    }

    void addPositions(AnimationPoint x, AnimationPoint y, AnimationPoint z) {
        posX.add(x);
        posY.add(y);
        posZ.add(z);
    }

    void addScales(AnimationPoint x, AnimationPoint y, AnimationPoint z) {
        scaleX.add(x);
        scaleY.add(y);
        scaleZ.add(z);
    }
}
