package net.mcreator.boh.geo;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/** Applies controller output to the bones of the active model, easing untouched bones back to rest. */
public class AnimationProcessor<T extends GeoAnimatable> {

    private final Map<String, CoreGeoBone> bones = new HashMap<>();
    private final GeoModel<T> model;
    public boolean reloadAnimations = false;

    public AnimationProcessor(GeoModel<T> model) {
        this.model = model;
    }

    public Queue<QueuedAnimation> buildAnimationQueue(T animatable, RawAnimation rawAnimation) {
        LinkedList<QueuedAnimation> animations = new LinkedList<>();
        for (RawAnimation.Stage stage : rawAnimation.getAnimationStages()) {
            Animation animation = stage.animationName == Animation.WAIT
                ? Animation.generateWaitAnimation(stage.additionalTicks)
                : model.getAnimation(animatable, stage.animationName);
            if (animation == null) return null;
            animations.add(new QueuedAnimation(animation, stage.loopType));
        }
        return animations;
    }

    public void tickAnimation(T animatable, GeoModel<T> model, AnimatableManager<T> manager, double animTime,
        AnimationState<T> state, boolean crashWhenCantFindBone) {
        Map<String, BoneSnapshot> boneSnapshots = updateBoneSnapshots(manager.getBoneSnapshotCollection());

        for (AnimationController<T> controller : manager.getAnimationControllers().values()) {
            if (reloadAnimations) {
                controller.forceAnimationReset();
                controller.getBoneAnimationQueues().clear();
            }
            controller.isJustStarting = manager.isFirstTick();
            state.withController(controller);
            controller.process(model, state, bones, boneSnapshots, animTime, crashWhenCantFindBone);

            for (BoneAnimationQueue q : controller.getBoneAnimationQueues().values()) {
                CoreGeoBone bone = q.bone;
                BoneSnapshot snapshot = boneSnapshots.get(bone.getName());
                BoneSnapshot initial = bone.getInitialSnapshot();
                AnimationPoint rx = q.rotX.poll(), ry = q.rotY.poll(), rz = q.rotZ.poll();
                AnimationPoint px = q.posX.poll(), py = q.posY.poll(), pz = q.posZ.poll();
                AnimationPoint sx = q.scaleX.poll(), sy = q.scaleY.poll(), sz = q.scaleZ.poll();
                EasingType easing = controller.overrideEasingTypeFunction.apply(animatable);

                if (rx != null && ry != null && rz != null) {
                    bone.setRotX((float) EasingType.lerpWithOverride(rx, easing) + initial.getRotX());
                    bone.setRotY((float) EasingType.lerpWithOverride(ry, easing) + initial.getRotY());
                    bone.setRotZ((float) EasingType.lerpWithOverride(rz, easing) + initial.getRotZ());
                    snapshot.updateRotation(bone.getRotX(), bone.getRotY(), bone.getRotZ());
                    snapshot.startRotAnim();
                    bone.markRotationAsChanged();
                }
                if (px != null && py != null && pz != null) {
                    bone.setPosX((float) EasingType.lerpWithOverride(px, easing));
                    bone.setPosY((float) EasingType.lerpWithOverride(py, easing));
                    bone.setPosZ((float) EasingType.lerpWithOverride(pz, easing));
                    snapshot.updateOffset(bone.getPosX(), bone.getPosY(), bone.getPosZ());
                    snapshot.startPosAnim();
                    bone.markPositionAsChanged();
                }
                if (sx != null && sy != null && sz != null) {
                    bone.setScaleX((float) EasingType.lerpWithOverride(sx, easing));
                    bone.setScaleY((float) EasingType.lerpWithOverride(sy, easing));
                    bone.setScaleZ((float) EasingType.lerpWithOverride(sz, easing));
                    snapshot.updateScale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
                    snapshot.startScaleAnim();
                    bone.markScaleAsChanged();
                }
            }
        }

        reloadAnimations = false;
        double resetTickLength = animatable.getBoneResetTime();

        for (CoreGeoBone bone : bones.values()) {
            BoneSnapshot initial = bone.getInitialSnapshot();
            BoneSnapshot save = boneSnapshots.get(bone.getName());

            if (!bone.hasRotationChanged()) {
                if (save.isRotAnimInProgress()) save.stopRotAnim(animTime);
                double pct = resetTickLength == 0 ? 1
                    : Math.min((animTime - save.getLastResetRotationTick()) / resetTickLength, 1);
                float ix = initial.getRotX(), iy = initial.getRotY(), iz = initial.getRotZ();
                float lx = save.getRotX(), ly = save.getRotY(), lz = save.getRotZ();
                if (pct == 0) {
                    if (lx != ix && isSuspectedCompletedRotation(lx)) {
                        lx = ix;
                        pct = 1;
                    }
                    if (ly != iy && isSuspectedCompletedRotation(ly)) {
                        ly = iy;
                        pct = 1;
                    }
                    if (lz != iz && isSuspectedCompletedRotation(lz)) {
                        lz = iz;
                        pct = 1;
                    }
                }
                bone.setRotX((float) EasingType.lerp(lx, ix, pct));
                bone.setRotY((float) EasingType.lerp(ly, iy, pct));
                bone.setRotZ((float) EasingType.lerp(lz, iz, pct));
                if (pct >= 1) save.updateRotation(bone.getRotX(), bone.getRotY(), bone.getRotZ());
            }

            if (!bone.hasPositionChanged()) {
                if (save.isPosAnimInProgress()) save.stopPosAnim(animTime);
                double pct = resetTickLength == 0 ? 1
                    : Math.min((animTime - save.getLastResetPositionTick()) / resetTickLength, 1);
                bone.setPosX((float) EasingType.lerp(save.getOffsetX(), initial.getOffsetX(), pct));
                bone.setPosY((float) EasingType.lerp(save.getOffsetY(), initial.getOffsetY(), pct));
                bone.setPosZ((float) EasingType.lerp(save.getOffsetZ(), initial.getOffsetZ(), pct));
                if (pct >= 1) save.updateOffset(bone.getPosX(), bone.getPosY(), bone.getPosZ());
            }

            if (!bone.hasScaleChanged()) {
                if (save.isScaleAnimInProgress()) save.stopScaleAnim(animTime);
                double pct = resetTickLength == 0 ? 1
                    : Math.min((animTime - save.getLastResetScaleTick()) / resetTickLength, 1);
                bone.setScaleX((float) EasingType.lerp(save.getScaleX(), initial.getScaleX(), pct));
                bone.setScaleY((float) EasingType.lerp(save.getScaleY(), initial.getScaleY(), pct));
                bone.setScaleZ((float) EasingType.lerp(save.getScaleZ(), initial.getScaleZ(), pct));
                if (pct >= 1) save.updateScale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
            }
        }

        for (CoreGeoBone bone : bones.values()) bone.resetStateChanges();
        manager.finishFirstTick();
    }

    private static boolean isSuspectedCompletedRotation(float lastRotation) {
        float rotations = Math.abs(lastRotation / (360f * ((float) Math.PI / 180f)));
        float partial = 1 - (rotations - (int) rotations);
        return partial == 1 || partial < 0.026 * rotations;
    }

    private Map<String, BoneSnapshot> updateBoneSnapshots(Map<String, BoneSnapshot> snapshots) {
        for (CoreGeoBone bone : bones.values()) {
            if (!snapshots.containsKey(bone.getName()))
                snapshots.put(bone.getName(), BoneSnapshot.copy(bone.getInitialSnapshot()));
        }
        return snapshots;
    }

    public CoreGeoBone getBone(String boneName) {
        return bones.get(boneName);
    }

    public void registerGeoBone(CoreGeoBone bone) {
        bone.saveInitialSnapshot();
        bones.put(bone.getName(), bone);
        for (CoreGeoBone child : bone.getChildBones()) registerGeoBone(child);
    }

    public void setActiveModel(BakedGeoModel model) {
        bones.clear();
        for (GeoBone bone : model.getBones()) registerGeoBone(bone);
    }

    public Collection<CoreGeoBone> getRegisteredBones() {
        return bones.values();
    }

    public static final class QueuedAnimation {

        final Animation animation;
        final Animation.LoopType loopType;

        QueuedAnimation(Animation animation, Animation.LoopType loopType) {
            this.animation = animation;
            this.loopType = loopType;
        }

        public Animation animation() {
            return animation;
        }

        public Animation.LoopType loopType() {
            return loopType;
        }
    }
}
