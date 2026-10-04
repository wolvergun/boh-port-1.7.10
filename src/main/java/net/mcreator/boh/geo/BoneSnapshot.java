package net.mcreator.boh.geo;

public class BoneSnapshot {
    private final CoreGeoBone bone;
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private float offsetPosX;
    private float offsetPosY;
    private float offsetPosZ;
    private float rotX;
    private float rotY;
    private float rotZ;
    private double lastResetRotationTick;
    private double lastResetPositionTick;
    private double lastResetScaleTick;
    private boolean rotAnimInProgress = true;
    private boolean posAnimInProgress = true;
    private boolean scaleAnimInProgress = true;

    public BoneSnapshot(CoreGeoBone bone) {
        this.bone = bone;
        this.rotX = bone.getRotX();
        this.rotY = bone.getRotY();
        this.rotZ = bone.getRotZ();
        this.offsetPosX = bone.getPosX();
        this.offsetPosY = bone.getPosY();
        this.offsetPosZ = bone.getPosZ();
        this.scaleX = bone.getScaleX();
        this.scaleY = bone.getScaleY();
        this.scaleZ = bone.getScaleZ();
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
        return this.bone;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public float getScaleZ() {
        return this.scaleZ;
    }

    public float getOffsetX() {
        return this.offsetPosX;
    }

    public float getOffsetY() {
        return this.offsetPosY;
    }

    public float getOffsetZ() {
        return this.offsetPosZ;
    }

    public float getRotX() {
        return this.rotX;
    }

    public float getRotY() {
        return this.rotY;
    }

    public float getRotZ() {
        return this.rotZ;
    }

    public double getLastResetRotationTick() {
        return this.lastResetRotationTick;
    }

    public double getLastResetPositionTick() {
        return this.lastResetPositionTick;
    }

    public double getLastResetScaleTick() {
        return this.lastResetScaleTick;
    }

    public boolean isRotAnimInProgress() {
        return this.rotAnimInProgress;
    }

    public boolean isPosAnimInProgress() {
        return this.posAnimInProgress;
    }

    public boolean isScaleAnimInProgress() {
        return this.scaleAnimInProgress;
    }

    public void updateScale(float x, float y, float z) {
        this.scaleX = x;
        this.scaleY = y;
        this.scaleZ = z;
    }

    public void updateOffset(float x, float y, float z) {
        this.offsetPosX = x;
        this.offsetPosY = y;
        this.offsetPosZ = z;
    }

    public void updateRotation(float x, float y, float z) {
        this.rotX = x;
        this.rotY = y;
        this.rotZ = z;
    }

    public void startPosAnim() {
        this.posAnimInProgress = true;
    }

    public void stopPosAnim(double tick) {
        this.posAnimInProgress = false;
        this.lastResetPositionTick = tick;
    }

    public void startRotAnim() {
        this.rotAnimInProgress = true;
    }

    public void stopRotAnim(double tick) {
        this.rotAnimInProgress = false;
        this.lastResetRotationTick = tick;
    }

    public void startScaleAnim() {
        this.scaleAnimInProgress = true;
    }

    public void stopScaleAnim(double tick) {
        this.scaleAnimInProgress = false;
        this.lastResetScaleTick = tick;
    }
}
