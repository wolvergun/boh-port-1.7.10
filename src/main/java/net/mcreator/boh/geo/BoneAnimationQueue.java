package net.mcreator.boh.geo;

import java.util.LinkedList;

final class BoneAnimationQueue {
    final CoreGeoBone bone;
    final LinkedList<AnimationPoint> rotX = new LinkedList<>();
    final LinkedList<AnimationPoint> rotY = new LinkedList<>();
    final LinkedList<AnimationPoint> rotZ = new LinkedList<>();
    final LinkedList<AnimationPoint> posX = new LinkedList<>();
    final LinkedList<AnimationPoint> posY = new LinkedList<>();
    final LinkedList<AnimationPoint> posZ = new LinkedList<>();
    final LinkedList<AnimationPoint> scaleX = new LinkedList<>();
    final LinkedList<AnimationPoint> scaleY = new LinkedList<>();
    final LinkedList<AnimationPoint> scaleZ = new LinkedList<>();

    BoneAnimationQueue(CoreGeoBone bone) {
        this.bone = bone;
    }

    void addNextPosition(double tick, double length, BoneSnapshot start, AnimationPoint x, AnimationPoint y, AnimationPoint z) {
        this.posX.add(new AnimationPoint(null, tick, length, start.getOffsetX(), x.animationStartValue));
        this.posY.add(new AnimationPoint(null, tick, length, start.getOffsetY(), y.animationStartValue));
        this.posZ.add(new AnimationPoint(null, tick, length, start.getOffsetZ(), z.animationStartValue));
    }

    void addNextScale(double tick, double length, BoneSnapshot start, AnimationPoint x, AnimationPoint y, AnimationPoint z) {
        this.scaleX.add(new AnimationPoint(null, tick, length, start.getScaleX(), x.animationStartValue));
        this.scaleY.add(new AnimationPoint(null, tick, length, start.getScaleY(), y.animationStartValue));
        this.scaleZ.add(new AnimationPoint(null, tick, length, start.getScaleZ(), z.animationStartValue));
    }

    void addNextRotation(double tick, double length, BoneSnapshot start, BoneSnapshot initial, AnimationPoint x, AnimationPoint y, AnimationPoint z) {
        this.rotX.add(new AnimationPoint(null, tick, length, start.getRotX() - initial.getRotX(), x.animationStartValue));
        this.rotY.add(new AnimationPoint(null, tick, length, start.getRotY() - initial.getRotY(), y.animationStartValue));
        this.rotZ.add(new AnimationPoint(null, tick, length, start.getRotZ() - initial.getRotZ(), z.animationStartValue));
    }

    void addRotations(AnimationPoint x, AnimationPoint y, AnimationPoint z) {
        this.rotX.add(x);
        this.rotY.add(y);
        this.rotZ.add(z);
    }

    void addPositions(AnimationPoint x, AnimationPoint y, AnimationPoint z) {
        this.posX.add(x);
        this.posY.add(y);
        this.posZ.add(z);
    }

    void addScales(AnimationPoint x, AnimationPoint y, AnimationPoint z) {
        this.scaleX.add(x);
        this.scaleY.add(y);
        this.scaleZ.add(z);
    }
}
