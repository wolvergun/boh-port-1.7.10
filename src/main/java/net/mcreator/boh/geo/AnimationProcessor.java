package net.mcreator.boh.geo;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class AnimationProcessor<T extends GeoAnimatable> {
    private final Map<String, CoreGeoBone> bones = new HashMap<>();
    private final GeoModel<T> model;
    public boolean reloadAnimations = false;

    public AnimationProcessor(GeoModel<T> model) {
        this.model = model;
    }

    public Queue<AnimationProcessor.QueuedAnimation> buildAnimationQueue(T animatable, RawAnimation rawAnimation) {
        LinkedList<AnimationProcessor.QueuedAnimation> animations = new LinkedList<>();

        for (RawAnimation.Stage stage : rawAnimation.getAnimationStages()) {
            Animation animation = stage.animationName == "internal.wait"
                ? Animation.generateWaitAnimation(stage.additionalTicks)
                : this.model.getAnimation(animatable, stage.animationName);
            if (animation == null) {
                return null;
            }

            animations.add(new AnimationProcessor.QueuedAnimation(animation, stage.loopType));
        }

        return animations;
    }

    public void tickAnimation(
        T animatable, GeoModel<T> model, AnimatableManager<T> manager, double animTime, AnimationState<T> state, boolean crashWhenCantFindBone
    ) {
        Map<String, BoneSnapshot> boneSnapshots = this.updateBoneSnapshots(manager.getBoneSnapshotCollection());

        for (AnimationController<T> controller : manager.getAnimationControllers().values()) {
            if (this.reloadAnimations) {
                controller.forceAnimationReset();
                controller.getBoneAnimationQueues().clear();
            }

            controller.isJustStarting = manager.isFirstTick();
            state.withController(controller);
            controller.process(model, state, this.bones, boneSnapshots, animTime, crashWhenCantFindBone);

            for (BoneAnimationQueue q : controller.getBoneAnimationQueues().values()) {
                CoreGeoBone bone = q.bone;
                BoneSnapshot snapshot = boneSnapshots.get(bone.getName());
                BoneSnapshot initial = bone.getInitialSnapshot();
                AnimationPoint rx = q.rotX.poll();
                AnimationPoint ry = q.rotY.poll();
                AnimationPoint rz = q.rotZ.poll();
                AnimationPoint px = q.posX.poll();
                AnimationPoint py = q.posY.poll();
                AnimationPoint pz = q.posZ.poll();
                AnimationPoint sx = q.scaleX.poll();
                AnimationPoint sy = q.scaleY.poll();
                AnimationPoint sz = q.scaleZ.poll();
                EasingType easing = controller.overrideEasingTypeFunction.apply(animatable);
                if (rx != null && ry != null && rz != null) {
                    bone.setRotX((float)EasingType.lerpWithOverride(rx, easing) + initial.getRotX());
                    bone.setRotY((float)EasingType.lerpWithOverride(ry, easing) + initial.getRotY());
                    bone.setRotZ((float)EasingType.lerpWithOverride(rz, easing) + initial.getRotZ());
                    snapshot.updateRotation(bone.getRotX(), bone.getRotY(), bone.getRotZ());
                    snapshot.startRotAnim();
                    bone.markRotationAsChanged();
                }

                if (px != null && py != null && pz != null) {
                    bone.setPosX((float)EasingType.lerpWithOverride(px, easing));
                    bone.setPosY((float)EasingType.lerpWithOverride(py, easing));
                    bone.setPosZ((float)EasingType.lerpWithOverride(pz, easing));
                    snapshot.updateOffset(bone.getPosX(), bone.getPosY(), bone.getPosZ());
                    snapshot.startPosAnim();
                    bone.markPositionAsChanged();
                }

                if (sx != null && sy != null && sz != null) {
                    bone.setScaleX((float)EasingType.lerpWithOverride(sx, easing));
                    bone.setScaleY((float)EasingType.lerpWithOverride(sy, easing));
                    bone.setScaleZ((float)EasingType.lerpWithOverride(sz, easing));
                    snapshot.updateScale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
                    snapshot.startScaleAnim();
                    bone.markScaleAsChanged();
                }
            }
        }

        this.reloadAnimations = false;
        double resetTickLength = animatable.getBoneResetTime();

        for (CoreGeoBone bonex : this.bones.values()) {
            BoneSnapshot initialx = bonex.getInitialSnapshot();
            BoneSnapshot save = boneSnapshots.get(bonex.getName());
            if (!bonex.hasRotationChanged()) {
                if (save.isRotAnimInProgress()) {
                    save.stopRotAnim(animTime);
                }

                double pct = resetTickLength == 0.0 ? 1.0 : Math.min((animTime - save.getLastResetRotationTick()) / resetTickLength, 1.0);
                float ix = initialx.getRotX();
                float iy = initialx.getRotY();
                float iz = initialx.getRotZ();
                float lx = save.getRotX();
                float ly = save.getRotY();
                float lz = save.getRotZ();
                if (pct == 0.0) {
                    if (lx != ix && isSuspectedCompletedRotation(lx)) {
                        lx = ix;
                        pct = 1.0;
                    }

                    if (ly != iy && isSuspectedCompletedRotation(ly)) {
                        ly = iy;
                        pct = 1.0;
                    }

                    if (lz != iz && isSuspectedCompletedRotation(lz)) {
                        lz = iz;
                        pct = 1.0;
                    }
                }

                bonex.setRotX((float)EasingType.lerp(lx, ix, pct));
                bonex.setRotY((float)EasingType.lerp(ly, iy, pct));
                bonex.setRotZ((float)EasingType.lerp(lz, iz, pct));
                if (pct >= 1.0) {
                    save.updateRotation(bonex.getRotX(), bonex.getRotY(), bonex.getRotZ());
                }
            }

            if (!bonex.hasPositionChanged()) {
                if (save.isPosAnimInProgress()) {
                    save.stopPosAnim(animTime);
                }

                double pctx = resetTickLength == 0.0 ? 1.0 : Math.min((animTime - save.getLastResetPositionTick()) / resetTickLength, 1.0);
                bonex.setPosX((float)EasingType.lerp(save.getOffsetX(), initialx.getOffsetX(), pctx));
                bonex.setPosY((float)EasingType.lerp(save.getOffsetY(), initialx.getOffsetY(), pctx));
                bonex.setPosZ((float)EasingType.lerp(save.getOffsetZ(), initialx.getOffsetZ(), pctx));
                if (pctx >= 1.0) {
                    save.updateOffset(bonex.getPosX(), bonex.getPosY(), bonex.getPosZ());
                }
            }

            if (!bonex.hasScaleChanged()) {
                if (save.isScaleAnimInProgress()) {
                    save.stopScaleAnim(animTime);
                }

                double pctx = resetTickLength == 0.0 ? 1.0 : Math.min((animTime - save.getLastResetScaleTick()) / resetTickLength, 1.0);
                bonex.setScaleX((float)EasingType.lerp(save.getScaleX(), initialx.getScaleX(), pctx));
                bonex.setScaleY((float)EasingType.lerp(save.getScaleY(), initialx.getScaleY(), pctx));
                bonex.setScaleZ((float)EasingType.lerp(save.getScaleZ(), initialx.getScaleZ(), pctx));
                if (pctx >= 1.0) {
                    save.updateScale(bonex.getScaleX(), bonex.getScaleY(), bonex.getScaleZ());
                }
            }
        }

        for (CoreGeoBone bonex : this.bones.values()) {
            bonex.resetStateChanges();
        }

        manager.finishFirstTick();
    }

    private static boolean isSuspectedCompletedRotation(float lastRotation) {
        float rotations = Math.abs(lastRotation / (float) (Math.PI * 2));
        float partial = 1.0F - (rotations - (int)rotations);
        return partial == 1.0F || partial < 0.026 * rotations;
    }

    private Map<String, BoneSnapshot> updateBoneSnapshots(Map<String, BoneSnapshot> snapshots) {
        for (CoreGeoBone bone : this.bones.values()) {
            if (!snapshots.containsKey(bone.getName())) {
                snapshots.put(bone.getName(), BoneSnapshot.copy(bone.getInitialSnapshot()));
            }
        }

        return snapshots;
    }

    public CoreGeoBone getBone(String boneName) {
        return this.bones.get(boneName);
    }

    public void registerGeoBone(CoreGeoBone bone) {
        bone.saveInitialSnapshot();
        this.bones.put(bone.getName(), bone);

        for (CoreGeoBone child : bone.getChildBones()) {
            this.registerGeoBone(child);
        }
    }

    public void setActiveModel(BakedGeoModel model) {
        this.bones.clear();

        for (GeoBone bone : model.getBones()) {
            this.registerGeoBone(bone);
        }
    }

    public Collection<CoreGeoBone> getRegisteredBones() {
        return this.bones.values();
    }

    public static final class QueuedAnimation {
        final Animation animation;
        final Animation.LoopType loopType;

        QueuedAnimation(Animation animation, Animation.LoopType loopType) {
            this.animation = animation;
            this.loopType = loopType;
        }

        public Animation animation() {
            return this.animation;
        }

        public Animation.LoopType loopType() {
            return this.loopType;
        }
    }
}
