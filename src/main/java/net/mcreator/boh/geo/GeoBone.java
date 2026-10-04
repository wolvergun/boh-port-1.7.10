package net.mcreator.boh.geo;

import java.util.ArrayList;
import java.util.List;

public class GeoBone implements CoreGeoBone {
    private final GeoBone parent;
    private final String name;
    private final List<GeoBone> children = new ArrayList<>();
    private final List<GeoCube> cubes = new ArrayList<>();
    private final Boolean mirror;
    private final Double inflate;
    private final Boolean dontRender;
    private final Boolean reset;
    private BoneSnapshot initialSnapshot;
    private boolean hidden;
    private boolean childrenHidden;
    private float scaleX = 1.0F;
    private float scaleY = 1.0F;
    private float scaleZ = 1.0F;
    private float positionX;
    private float positionY;
    private float positionZ;
    private float pivotX;
    private float pivotY;
    private float pivotZ;
    private float rotX;
    private float rotY;
    private float rotZ;
    private boolean positionChanged;
    private boolean rotationChanged;
    private boolean scaleChanged;

    public GeoBone(GeoBone parent, String name, Boolean mirror, Double inflate, Boolean dontRender, Boolean reset) {
        this.parent = parent;
        this.name = name;
        this.mirror = mirror;
        this.inflate = inflate;
        this.dontRender = dontRender;
        this.reset = reset;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public GeoBone getParent() {
        return this.parent;
    }

    @Override
    public float getRotX() {
        return this.rotX;
    }

    @Override
    public float getRotY() {
        return this.rotY;
    }

    @Override
    public float getRotZ() {
        return this.rotZ;
    }

    @Override
    public float getPosX() {
        return this.positionX;
    }

    @Override
    public float getPosY() {
        return this.positionY;
    }

    @Override
    public float getPosZ() {
        return this.positionZ;
    }

    @Override
    public float getScaleX() {
        return this.scaleX;
    }

    @Override
    public float getScaleY() {
        return this.scaleY;
    }

    @Override
    public float getScaleZ() {
        return this.scaleZ;
    }

    @Override
    public void setRotX(float v) {
        this.rotX = v;
        this.markRotationAsChanged();
    }

    @Override
    public void setRotY(float v) {
        this.rotY = v;
        this.markRotationAsChanged();
    }

    @Override
    public void setRotZ(float v) {
        this.rotZ = v;
        this.markRotationAsChanged();
    }

    @Override
    public void setPosX(float v) {
        this.positionX = v;
        this.markPositionAsChanged();
    }

    @Override
    public void setPosY(float v) {
        this.positionY = v;
        this.markPositionAsChanged();
    }

    @Override
    public void setPosZ(float v) {
        this.positionZ = v;
        this.markPositionAsChanged();
    }

    @Override
    public void setScaleX(float v) {
        this.scaleX = v;
        this.markScaleAsChanged();
    }

    @Override
    public void setScaleY(float v) {
        this.scaleY = v;
        this.markScaleAsChanged();
    }

    @Override
    public void setScaleZ(float v) {
        this.scaleZ = v;
        this.markScaleAsChanged();
    }

    @Override
    public boolean isHidden() {
        return this.hidden;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
        this.setChildrenHidden(hidden);
    }

    @Override
    public void setChildrenHidden(boolean hide) {
        this.childrenHidden = hide;
    }

    @Override
    public void setPivotX(float v) {
        this.pivotX = v;
    }

    @Override
    public void setPivotY(float v) {
        this.pivotY = v;
    }

    @Override
    public void setPivotZ(float v) {
        this.pivotZ = v;
    }

    @Override
    public float getPivotX() {
        return this.pivotX;
    }

    @Override
    public float getPivotY() {
        return this.pivotY;
    }

    @Override
    public float getPivotZ() {
        return this.pivotZ;
    }

    @Override
    public boolean isHidingChildren() {
        return this.childrenHidden;
    }

    @Override
    public void markScaleAsChanged() {
        this.scaleChanged = true;
    }

    @Override
    public void markRotationAsChanged() {
        this.rotationChanged = true;
    }

    @Override
    public void markPositionAsChanged() {
        this.positionChanged = true;
    }

    @Override
    public boolean hasScaleChanged() {
        return this.scaleChanged;
    }

    @Override
    public boolean hasRotationChanged() {
        return this.rotationChanged;
    }

    @Override
    public boolean hasPositionChanged() {
        return this.positionChanged;
    }

    @Override
    public void resetStateChanges() {
        this.scaleChanged = false;
        this.rotationChanged = false;
        this.positionChanged = false;
    }

    @Override
    public BoneSnapshot getInitialSnapshot() {
        return this.initialSnapshot;
    }

    @Override
    public void saveInitialSnapshot() {
        if (this.initialSnapshot == null) {
            this.initialSnapshot = new BoneSnapshot(this);
        }
    }

    @Override
    public List<GeoBone> getChildBones() {
        return this.children;
    }

    public List<GeoCube> getCubes() {
        return this.cubes;
    }

    public Boolean getMirror() {
        return this.mirror;
    }

    public Double getInflate() {
        return this.inflate;
    }

    public Boolean shouldNeverRender() {
        return this.dontRender;
    }

    public Boolean getReset() {
        return this.reset;
    }
}
