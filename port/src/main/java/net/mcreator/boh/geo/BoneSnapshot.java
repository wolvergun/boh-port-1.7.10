package net.mcreator.boh.geo;

public class BoneSnapshot {

    private final CoreGeoBone bone;
    private float scaleX, scaleY, scaleZ;
    private float offsetPosX, offsetPosY, offsetPosZ;
    private float rotX, rotY, rotZ;
    private double lastResetRotationTick, lastResetPositionTick, lastResetScaleTick;
    private boolean rotAnimInProgress = true, posAnimInProgress = true, scaleAnimInProgress = true;

    public BoneSnapshot(CoreGeoBone bone) {
        this.bone = bone;
        rotX = bone.getRotX();
        rotY = bone.getRotY();
        rotZ = bone.getRotZ();
        offsetPosX = bone.getPosX();
        offsetPosY = bone.getPosY();
        offsetPosZ = bone.getPosZ();
        scaleX = bone.getScaleX();
        scaleY = bone.getScaleY();
        scaleZ = bone.getScaleZ();
    }

    public static BoneSnapshot copy(BoneSnapshot s) {
        BoneSnapshot n = new BoneSnapshot(s.bone);
        n.scaleX = s.scaleX;
        n.scaleY = s.scaleY;
        n.scaleZ = s.scaleZ;
        n.offsetPosX = s.offsetPosX;
        n.offsetPosY = s.offsetPosY;
        n.offsetPosZ = s.offsetPosZ;
        n.rotX = s.rotX;
        n.rotY = s.rotY;
        n.rotZ = s.rotZ;
        return n;
    }

    public CoreGeoBone getBone() {
        return bone;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public float getOffsetX() {
        return offsetPosX;
    }

    public float getOffsetY() {
        return offsetPosY;
    }

    public float getOffsetZ() {
        return offsetPosZ;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public double getLastResetRotationTick() {
        return lastResetRotationTick;
    }

    public double getLastResetPositionTick() {
        return lastResetPositionTick;
    }

    public double getLastResetScaleTick() {
        return lastResetScaleTick;
    }

    public boolean isRotAnimInProgress() {
        return rotAnimInProgress;
    }

    public boolean isPosAnimInProgress() {
        return posAnimInProgress;
    }

    public boolean isScaleAnimInProgress() {
        return scaleAnimInProgress;
    }

    public void updateScale(float x, float y, float z) {
        scaleX = x;
        scaleY = y;
        scaleZ = z;
    }

    public void updateOffset(float x, float y, float z) {
        offsetPosX = x;
        offsetPosY = y;
        offsetPosZ = z;
    }

    public void updateRotation(float x, float y, float z) {
        rotX = x;
        rotY = y;
        rotZ = z;
    }

    public void startPosAnim() {
        posAnimInProgress = true;
    }

    public void stopPosAnim(double tick) {
        posAnimInProgress = false;
        lastResetPositionTick = tick;
    }

    public void startRotAnim() {
        rotAnimInProgress = true;
    }

    public void stopRotAnim(double tick) {
        rotAnimInProgress = false;
        lastResetRotationTick = tick;
    }

    public void startScaleAnim() {
        scaleAnimInProgress = true;
    }

    public void stopScaleAnim(double tick) {
        scaleAnimInProgress = false;
        lastResetScaleTick = tick;
    }
}
