package net.mcreator.boh.geo;

final class AnimationPoint {

    final Keyframe keyFrame;
    final double currentTick;
    final double transitionLength;
    final double animationStartValue;
    final double animationEndValue;

    AnimationPoint(Keyframe keyFrame, double currentTick, double transitionLength, double start, double end) {
        this.keyFrame = keyFrame;
        this.currentTick = currentTick;
        this.transitionLength = transitionLength;
        this.animationStartValue = start;
        this.animationEndValue = end;
    }
}
