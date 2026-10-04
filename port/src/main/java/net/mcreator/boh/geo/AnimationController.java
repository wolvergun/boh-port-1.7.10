package net.mcreator.boh.geo;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

/** GeckoLib 4 animation controller state machine (transition, run, loop/hold/stop). */
public class AnimationController<T extends GeoAnimatable> {

    protected final T animatable;
    protected final String name;
    protected final AnimationStateHandler<T> stateHandler;
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
    protected State animationState = State.STOPPED;
    protected double tickOffset;
    protected double lastPollTime = -1;
    protected Function<T, Double> animationSpeedModifier = a -> 1d;
    protected Function<T, EasingType> overrideEasingTypeFunction = a -> null;
    protected GeoModel<T> lastModel;

    public AnimationController(T animatable, AnimationStateHandler<T> handler) {
        this(animatable, "base_controller", 0, handler);
    }

    public AnimationController(T animatable, String name, AnimationStateHandler<T> handler) {
        this(animatable, name, 0, handler);
    }

    public AnimationController(T animatable, int transitionTickTime, AnimationStateHandler<T> handler) {
        this(animatable, "base_controller", transitionTickTime, handler);
    }

    public AnimationController(T animatable, String name, int transitionTickTime, AnimationStateHandler<T> handler) {
        this.animatable = animatable;
        this.name = name;
        this.transitionLength = transitionTickTime;
        this.stateHandler = handler;
    }

    public AnimationController<T> setAnimationSpeedHandler(Function<T, Double> speedModFunction) {
        animationSpeedModifier = speedModFunction;
        return this;
    }

    public AnimationController<T> setAnimationSpeed(double speed) {
        return setAnimationSpeedHandler(a -> speed);
    }

    public AnimationController<T> setOverrideEasingType(EasingType easing) {
        overrideEasingTypeFunction = a -> easing;
        return this;
    }

    public AnimationController<T> triggerableAnim(String name, RawAnimation animation) {
        triggerableAnimations.put(name, animation);
        return this;
    }

    public AnimationController<T> receiveTriggeredAnimations() {
        handlingTriggeredAnimations = true;
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
        return name;
    }

    public AnimationProcessor.QueuedAnimation getCurrentAnimation() {
        return currentAnimation;
    }

    public State getAnimationState() {
        return animationState;
    }

    public AnimationStateHandler<T> getStateHandler() {
        return stateHandler;
    }

    Map<String, BoneAnimationQueue> getBoneAnimationQueues() {
        return boneAnimationQueues;
    }

    public double getAnimationSpeed() {
        return animationSpeedModifier.apply(animatable);
    }

    public void forceAnimationReset() {
        needsAnimationReload = true;
    }

    public void stop() {
        animationState = State.STOPPED;
    }

    public void setTransitionLength(int ticks) {
        transitionLength = ticks;
    }

    public AnimationController<T> transitionLength(int ticks) {
        transitionLength = ticks;
        return this;
    }

    public boolean hasAnimationFinished() {
        return currentRawAnimation != null && animationState == State.STOPPED;
    }

    public RawAnimation getCurrentRawAnimation() {
        return currentRawAnimation;
    }

    public boolean isPlayingTriggeredAnimation() {
        return triggeredAnimation != null && !hasAnimationFinished();
    }

    public void setAnimation(RawAnimation rawAnimation) {
        if (rawAnimation == null || rawAnimation.getAnimationStages().isEmpty()) {
            stop();
            return;
        }
        if (needsAnimationReload || !rawAnimation.equals(currentRawAnimation)) {
            if (lastModel != null) {
                Queue<AnimationProcessor.QueuedAnimation> animations = lastModel.getAnimationProcessor()
                    .buildAnimationQueue(animatable, rawAnimation);
                if (animations != null) {
                    animationQueue = animations;
                    currentRawAnimation = rawAnimation;
                    shouldResetTick = true;
                    animationState = State.TRANSITIONING;
                    justStartedTransition = true;
                    needsAnimationReload = false;
                    return;
                }
            }
            stop();
        }
    }

    public boolean tryTriggerAnimation(String animName) {
        RawAnimation anim = triggerableAnimations.get(animName);
        if (anim == null) return false;
        triggeredAnimation = anim;
        if (animationState == State.STOPPED) {
            animationState = State.TRANSITIONING;
            shouldResetTick = true;
            justStartedTransition = true;
        }
        return true;
    }

    protected PlayState handleAnimationState(AnimationState<T> state) {
        if (triggeredAnimation != null) {
            if (currentRawAnimation != triggeredAnimation) currentAnimation = null;
            setAnimation(triggeredAnimation);
            if (!hasAnimationFinished()
                && (!handlingTriggeredAnimations || stateHandler.handle(state) == PlayState.CONTINUE))
                return PlayState.CONTINUE;
            triggeredAnimation = null;
            needsAnimationReload = true;
        }
        return stateHandler.handle(state);
    }

    public void process(GeoModel<T> model, AnimationState<T> state, Map<String, CoreGeoBone> bones,
        Map<String, BoneSnapshot> snapshots, final double seekTime, boolean crashWhenCantFindBone) {
        double adjustedTick = adjustTick(seekTime);
        lastModel = model;

        if (animationState == State.TRANSITIONING && adjustedTick >= transitionLength) {
            shouldResetTick = true;
            animationState = State.RUNNING;
            adjustedTick = adjustTick(seekTime);
        }

        PlayState playState = handleAnimationState(state);

        if (playState == PlayState.STOP || (currentAnimation == null && animationQueue.isEmpty())) {
            animationState = State.STOPPED;
            justStopped = true;
            return;
        }

        createInitialQueues(bones.values());

        if (justStartedTransition && (shouldResetTick || justStopped)) {
            justStopped = false;
            adjustedTick = adjustTick(seekTime);
            if (currentAnimation == null) animationState = State.TRANSITIONING;
        } else if (currentAnimation == null) {
            shouldResetTick = true;
            animationState = State.TRANSITIONING;
            justStartedTransition = true;
            needsAnimationReload = false;
            adjustedTick = adjustTick(seekTime);
        } else if (animationState != State.TRANSITIONING) {
            animationState = State.RUNNING;
        }

        if (animationState == State.RUNNING) {
            processCurrentAnimation(adjustedTick, seekTime, crashWhenCantFindBone);
        } else if (animationState == State.TRANSITIONING) {
            if (lastPollTime != seekTime && (adjustedTick == 0 || isJustStarting)) {
                justStartedTransition = false;
                lastPollTime = seekTime;
                currentAnimation = animationQueue.poll();
                if (currentAnimation == null) return;
                saveSnapshotsForAnimation(currentAnimation, snapshots);
            }
            if (currentAnimation != null) {
                for (BoneAnimation boneAnimation : currentAnimation.animation.boneAnimations()) {
                    BoneAnimationQueue queue = boneAnimationQueues.get(boneAnimation.boneName);
                    BoneSnapshot boneSnapshot = boneSnapshots.get(boneAnimation.boneName);
                    CoreGeoBone bone = bones.get(boneAnimation.boneName);
                    if (boneSnapshot == null || bone == null) continue;
                    KeyframeStack rot = boneAnimation.rotation, pos = boneAnimation.position,
                        scale = boneAnimation.scale;
                    if (!rot.x.isEmpty()) queue.addNextRotation(adjustedTick, transitionLength, boneSnapshot,
                        bone.getInitialSnapshot(), pointAt(rot.x, 0), pointAt(rot.y, 0), pointAt(rot.z, 0));
                    if (!pos.x.isEmpty()) queue.addNextPosition(adjustedTick, transitionLength, boneSnapshot,
                        pointAt(pos.x, 0), pointAt(pos.y, 0), pointAt(pos.z, 0));
                    if (!scale.x.isEmpty()) queue.addNextScale(adjustedTick, transitionLength, boneSnapshot,
                        pointAt(scale.x, 0), pointAt(scale.y, 0), pointAt(scale.z, 0));
                }
            }
        }
    }

    private void processCurrentAnimation(double adjustedTick, double seekTime, boolean crashWhenCantFindBone) {
        if (adjustedTick >= currentAnimation.animation.length()) {
            if (currentAnimation.loopType.shouldPlayAgain(animatable, this, currentAnimation.animation)) {
                if (animationState != State.PAUSED) {
                    shouldResetTick = true;
                    adjustedTick = adjustTick(seekTime);
                }
            } else {
                AnimationProcessor.QueuedAnimation next = animationQueue.peek();
                if (next == null) {
                    animationState = State.STOPPED;
                    return;
                }
                animationState = State.TRANSITIONING;
                shouldResetTick = true;
                adjustedTick = adjustTick(seekTime);
                currentAnimation = animationQueue.poll();
            }
        }

        for (BoneAnimation boneAnimation : currentAnimation.animation.boneAnimations()) {
            BoneAnimationQueue queue = boneAnimationQueues.get(boneAnimation.boneName);
            if (queue == null) continue;
            KeyframeStack rot = boneAnimation.rotation, pos = boneAnimation.position, scale = boneAnimation.scale;
            if (!rot.x.isEmpty()) queue.addRotations(
                pointAt(rot.x, adjustedTick),
                pointAt(rot.y, adjustedTick),
                pointAt(rot.z, adjustedTick));
            if (!pos.x.isEmpty()) queue.addPositions(
                pointAt(pos.x, adjustedTick),
                pointAt(pos.y, adjustedTick),
                pointAt(pos.z, adjustedTick));
            if (!scale.x.isEmpty()) queue.addScales(
                pointAt(scale.x, adjustedTick),
                pointAt(scale.y, adjustedTick),
                pointAt(scale.z, adjustedTick));
        }

        if (transitionLength == 0 && shouldResetTick && animationState == State.TRANSITIONING)
            currentAnimation = animationQueue.poll();
    }

    private void createInitialQueues(Collection<CoreGeoBone> bones) {
        boneAnimationQueues.clear();
        for (CoreGeoBone bone : bones) boneAnimationQueues.put(bone.getName(), new BoneAnimationQueue(bone));
    }

    private void saveSnapshotsForAnimation(AnimationProcessor.QueuedAnimation animation,
        Map<String, BoneSnapshot> snapshots) {
        for (BoneSnapshot snapshot : snapshots.values()) {
            for (BoneAnimation boneAnimation : animation.animation.boneAnimations()) {
                if (boneAnimation.boneName.equals(snapshot.getBone().getName())) {
                    boneSnapshots.put(boneAnimation.boneName, BoneSnapshot.copy(snapshot));
                    break;
                }
            }
        }
    }

    protected double adjustTick(double tick) {
        if (!shouldResetTick) return animationSpeedModifier.apply(animatable) * Math.max(tick - tickOffset, 0);
        if (animationState != State.STOPPED) tickOffset = tick;
        shouldResetTick = false;
        return 0;
    }

    private static AnimationPoint pointAt(List<Keyframe> frames, double tick) {
        double total = 0;
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
        if (frame == null) frame = frames.get(frames.size() - 1);
        return new AnimationPoint(frame, location, frame.length, frame.startValue, frame.endValue);
    }

    @FunctionalInterface
    public interface AnimationStateHandler<A extends GeoAnimatable> {

        PlayState handle(AnimationState<A> state);
    }

    public enum State {
        RUNNING,
        TRANSITIONING,
        PAUSED,
        STOPPED
    }
}
