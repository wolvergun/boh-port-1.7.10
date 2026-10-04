package net.mcreator.boh.geo;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

public class AnimationController<T extends GeoAnimatable> {
    protected final T animatable;
    protected final String name;
    protected final AnimationController.AnimationStateHandler<T> stateHandler;
    protected final Map<String, BoneAnimationQueue> boneAnimationQueues = new HashMap<>();
    protected final Map<String, BoneSnapshot> boneSnapshots = new HashMap<>();
    protected Queue<AnimationProcessor.QueuedAnimation> animationQueue = new LinkedList<>();
    protected boolean isJustStarting = false;
    protected boolean needsAnimationReload = false;
    protected boolean shouldResetTick = false;
    private boolean justStopped = true;
    protected boolean justStartedTransition = false;
    protected final Map<String, RawAnimation> triggerableAnimations = new HashMap<>(0);
    protected RawAnimation triggeredAnimation = null;
    protected boolean handlingTriggeredAnimations = false;
    protected double transitionLength;
    protected RawAnimation currentRawAnimation;
    protected AnimationProcessor.QueuedAnimation currentAnimation;
    protected AnimationController.State animationState = AnimationController.State.STOPPED;
    protected double tickOffset;
    protected double lastPollTime = -1.0;
    protected Function<T, Double> animationSpeedModifier = a -> 1.0;
    protected Function<T, EasingType> overrideEasingTypeFunction = a -> null;
    protected GeoModel<T> lastModel;

    public AnimationController(T animatable, AnimationController.AnimationStateHandler<T> handler) {
        this(animatable, "base_controller", 0, handler);
    }

    public AnimationController(T animatable, String name, AnimationController.AnimationStateHandler<T> handler) {
        this(animatable, name, 0, handler);
    }

    public AnimationController(T animatable, int transitionTickTime, AnimationController.AnimationStateHandler<T> handler) {
        this(animatable, "base_controller", transitionTickTime, handler);
    }

    public AnimationController(T animatable, String name, int transitionTickTime, AnimationController.AnimationStateHandler<T> handler) {
        this.animatable = animatable;
        this.name = name;
        this.transitionLength = transitionTickTime;
        this.stateHandler = handler;
    }

    public AnimationController<T> setAnimationSpeedHandler(Function<T, Double> speedModFunction) {
        this.animationSpeedModifier = speedModFunction;
        return this;
    }

    public AnimationController<T> setAnimationSpeed(double speed) {
        return this.setAnimationSpeedHandler(a -> speed);
    }

    public AnimationController<T> setOverrideEasingType(EasingType easing) {
        this.overrideEasingTypeFunction = a -> easing;
        return this;
    }

    public AnimationController<T> triggerableAnim(String name, RawAnimation animation) {
        this.triggerableAnimations.put(name, animation);
        return this;
    }

    public AnimationController<T> receiveTriggeredAnimations() {
        this.handlingTriggeredAnimations = true;
        return this;
    }

    public AnimationController<T> setSoundKeyframeHandler(Object handler) {
        return this;
    }

    public AnimationController<T> setParticleKeyframeHandler(Object handler) {
        return this;
    }

    public AnimationController<T> setCustomInstructionKeyframeHandler(Object handler) {
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AnimationProcessor.QueuedAnimation getCurrentAnimation() {
        return this.currentAnimation;
    }

    public AnimationController.State getAnimationState() {
        return this.animationState;
    }

    public AnimationController.AnimationStateHandler<T> getStateHandler() {
        return this.stateHandler;
    }

    Map<String, BoneAnimationQueue> getBoneAnimationQueues() {
        return this.boneAnimationQueues;
    }

    public double getAnimationSpeed() {
        return this.animationSpeedModifier.apply(this.animatable);
    }

    public void forceAnimationReset() {
        this.needsAnimationReload = true;
    }

    public void stop() {
        this.animationState = AnimationController.State.STOPPED;
    }

    public void setTransitionLength(int ticks) {
        this.transitionLength = ticks;
    }

    public AnimationController<T> transitionLength(int ticks) {
        this.transitionLength = ticks;
        return this;
    }

    public boolean hasAnimationFinished() {
        return this.currentRawAnimation != null && this.animationState == AnimationController.State.STOPPED;
    }

    public RawAnimation getCurrentRawAnimation() {
        return this.currentRawAnimation;
    }

    public boolean isPlayingTriggeredAnimation() {
        return this.triggeredAnimation != null && !this.hasAnimationFinished();
    }

    public void setAnimation(RawAnimation rawAnimation) {
        if (rawAnimation != null && !rawAnimation.getAnimationStages().isEmpty()) {
            if (this.needsAnimationReload || !rawAnimation.equals(this.currentRawAnimation)) {
                if (this.lastModel != null) {
                    Queue<AnimationProcessor.QueuedAnimation> animations = this.lastModel
                        .getAnimationProcessor()
                        .buildAnimationQueue(this.animatable, rawAnimation);
                    if (animations != null) {
                        this.animationQueue = animations;
                        this.currentRawAnimation = rawAnimation;
                        this.shouldResetTick = true;
                        this.animationState = AnimationController.State.TRANSITIONING;
                        this.justStartedTransition = true;
                        this.needsAnimationReload = false;
                        return;
                    }
                }

                this.stop();
            }
        } else {
            this.stop();
        }
    }

    public boolean tryTriggerAnimation(String animName) {
        RawAnimation anim = this.triggerableAnimations.get(animName);
        if (anim == null) {
            return false;
        } else {
            this.triggeredAnimation = anim;
            if (this.animationState == AnimationController.State.STOPPED) {
                this.animationState = AnimationController.State.TRANSITIONING;
                this.shouldResetTick = true;
                this.justStartedTransition = true;
            }

            return true;
        }
    }

    protected PlayState handleAnimationState(AnimationState<T> state) {
        if (this.triggeredAnimation != null) {
            if (this.currentRawAnimation != this.triggeredAnimation) {
                this.currentAnimation = null;
            }

            this.setAnimation(this.triggeredAnimation);
            if (!this.hasAnimationFinished() && (!this.handlingTriggeredAnimations || this.stateHandler.handle(state) == PlayState.CONTINUE)) {
                return PlayState.CONTINUE;
            }

            this.triggeredAnimation = null;
            this.needsAnimationReload = true;
        }

        return this.stateHandler.handle(state);
    }

    public void process(
        GeoModel<T> model,
        AnimationState<T> state,
        Map<String, CoreGeoBone> bones,
        Map<String, BoneSnapshot> snapshots,
        double seekTime,
        boolean crashWhenCantFindBone
    ) {
        double adjustedTick = this.adjustTick(seekTime);
        this.lastModel = model;
        if (this.animationState == AnimationController.State.TRANSITIONING && adjustedTick >= this.transitionLength) {
            this.shouldResetTick = true;
            this.animationState = AnimationController.State.RUNNING;
            adjustedTick = this.adjustTick(seekTime);
        }

        PlayState playState = this.handleAnimationState(state);
        if (playState != PlayState.STOP && (this.currentAnimation != null || !this.animationQueue.isEmpty())) {
            this.createInitialQueues(bones.values());
            if (this.justStartedTransition && (this.shouldResetTick || this.justStopped)) {
                this.justStopped = false;
                adjustedTick = this.adjustTick(seekTime);
                if (this.currentAnimation == null) {
                    this.animationState = AnimationController.State.TRANSITIONING;
                }
            } else if (this.currentAnimation == null) {
                this.shouldResetTick = true;
                this.animationState = AnimationController.State.TRANSITIONING;
                this.justStartedTransition = true;
                this.needsAnimationReload = false;
                adjustedTick = this.adjustTick(seekTime);
            } else if (this.animationState != AnimationController.State.TRANSITIONING) {
                this.animationState = AnimationController.State.RUNNING;
            }

            if (this.animationState == AnimationController.State.RUNNING) {
                this.processCurrentAnimation(adjustedTick, seekTime, crashWhenCantFindBone);
            } else if (this.animationState == AnimationController.State.TRANSITIONING) {
                if (this.lastPollTime != seekTime && (adjustedTick == 0.0 || this.isJustStarting)) {
                    this.justStartedTransition = false;
                    this.lastPollTime = seekTime;
                    this.currentAnimation = this.animationQueue.poll();
                    if (this.currentAnimation == null) {
                        return;
                    }

                    this.saveSnapshotsForAnimation(this.currentAnimation, snapshots);
                }

                if (this.currentAnimation != null) {
                    for (BoneAnimation boneAnimation : this.currentAnimation.animation.boneAnimations()) {
                        BoneAnimationQueue queue = this.boneAnimationQueues.get(boneAnimation.boneName);
                        BoneSnapshot boneSnapshot = this.boneSnapshots.get(boneAnimation.boneName);
                        CoreGeoBone bone = bones.get(boneAnimation.boneName);
                        if (boneSnapshot != null && bone != null) {
                            KeyframeStack rot = boneAnimation.rotation;
                            KeyframeStack pos = boneAnimation.position;
                            KeyframeStack scale = boneAnimation.scale;
                            if (!rot.x.isEmpty()) {
                                queue.addNextRotation(
                                    adjustedTick,
                                    this.transitionLength,
                                    boneSnapshot,
                                    bone.getInitialSnapshot(),
                                    pointAt(rot.x, 0.0),
                                    pointAt(rot.y, 0.0),
                                    pointAt(rot.z, 0.0)
                                );
                            }

                            if (!pos.x.isEmpty()) {
                                queue.addNextPosition(
                                    adjustedTick, this.transitionLength, boneSnapshot, pointAt(pos.x, 0.0), pointAt(pos.y, 0.0), pointAt(pos.z, 0.0)
                                );
                            }

                            if (!scale.x.isEmpty()) {
                                queue.addNextScale(
                                    adjustedTick, this.transitionLength, boneSnapshot, pointAt(scale.x, 0.0), pointAt(scale.y, 0.0), pointAt(scale.z, 0.0)
                                );
                            }
                        }
                    }
                }
            }
        } else {
            this.animationState = AnimationController.State.STOPPED;
            this.justStopped = true;
        }
    }

    private void processCurrentAnimation(double adjustedTick, double seekTime, boolean crashWhenCantFindBone) {
        if (adjustedTick >= this.currentAnimation.animation.length()) {
            if (this.currentAnimation.loopType.shouldPlayAgain(this.animatable, this, this.currentAnimation.animation)) {
                if (this.animationState != AnimationController.State.PAUSED) {
                    this.shouldResetTick = true;
                    adjustedTick = this.adjustTick(seekTime);
                }
            } else {
                AnimationProcessor.QueuedAnimation next = this.animationQueue.peek();
                if (next == null) {
                    this.animationState = AnimationController.State.STOPPED;
                    return;
                }

                this.animationState = AnimationController.State.TRANSITIONING;
                this.shouldResetTick = true;
                adjustedTick = this.adjustTick(seekTime);
                this.currentAnimation = this.animationQueue.poll();
            }
        }

        for (BoneAnimation boneAnimation : this.currentAnimation.animation.boneAnimations()) {
            BoneAnimationQueue queue = this.boneAnimationQueues.get(boneAnimation.boneName);
            if (queue != null) {
                KeyframeStack rot = boneAnimation.rotation;
                KeyframeStack pos = boneAnimation.position;
                KeyframeStack scale = boneAnimation.scale;
                if (!rot.x.isEmpty()) {
                    queue.addRotations(pointAt(rot.x, adjustedTick), pointAt(rot.y, adjustedTick), pointAt(rot.z, adjustedTick));
                }

                if (!pos.x.isEmpty()) {
                    queue.addPositions(pointAt(pos.x, adjustedTick), pointAt(pos.y, adjustedTick), pointAt(pos.z, adjustedTick));
                }

                if (!scale.x.isEmpty()) {
                    queue.addScales(pointAt(scale.x, adjustedTick), pointAt(scale.y, adjustedTick), pointAt(scale.z, adjustedTick));
                }
            }
        }

        if (this.transitionLength == 0.0 && this.shouldResetTick && this.animationState == AnimationController.State.TRANSITIONING) {
            this.currentAnimation = this.animationQueue.poll();
        }
    }

    private void createInitialQueues(Collection<CoreGeoBone> bones) {
        this.boneAnimationQueues.clear();

        for (CoreGeoBone bone : bones) {
            this.boneAnimationQueues.put(bone.getName(), new BoneAnimationQueue(bone));
        }
    }

    private void saveSnapshotsForAnimation(AnimationProcessor.QueuedAnimation animation, Map<String, BoneSnapshot> snapshots) {
        for (BoneSnapshot snapshot : snapshots.values()) {
            for (BoneAnimation boneAnimation : animation.animation.boneAnimations()) {
                if (boneAnimation.boneName.equals(snapshot.getBone().getName())) {
                    this.boneSnapshots.put(boneAnimation.boneName, BoneSnapshot.copy(snapshot));
                    break;
                }
            }
        }
    }

    protected double adjustTick(double tick) {
        if (!this.shouldResetTick) {
            return this.animationSpeedModifier.apply(this.animatable) * Math.max(tick - this.tickOffset, 0.0);
        } else {
            if (this.animationState != AnimationController.State.STOPPED) {
                this.tickOffset = tick;
            }

            this.shouldResetTick = false;
            return 0.0;
        }
    }

    private static AnimationPoint pointAt(List<Keyframe> frames, double tick) {
        double total = 0.0;
        Keyframe frame = null;
        double location = tick;

        for (Keyframe f : frames) {
            total += f.length;
            if (total > tick) {
                frame = f;
                location = tick - (total - f.length);
                break;
            }
        }

        if (frame == null) {
            frame = frames.get(frames.size() - 1);
        }

        return new AnimationPoint(frame, location, frame.length, frame.startValue, frame.endValue);
    }

    @FunctionalInterface
    public interface AnimationStateHandler<A extends GeoAnimatable> {
        PlayState handle(AnimationState<A> var1);
    }

    public static enum State {
        RUNNING,
        TRANSITIONING,
        PAUSED,
        STOPPED;
    }
}
