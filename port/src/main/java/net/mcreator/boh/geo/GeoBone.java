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

    private float scaleX = 1, scaleY = 1, scaleZ = 1;
    private float positionX, positionY, positionZ;
    private float pivotX, pivotY, pivotZ;
    private float rotX, rotY, rotZ;

    private boolean positionChanged, rotationChanged, scaleChanged;

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
        return name;
    }

    @Override
    public GeoBone getParent() {
        return parent;
    }

    @Override
    public float getRotX() {
        return rotX;
    }

    @Override
    public float getRotY() {
        return rotY;
    }

    @Override
    public float getRotZ() {
        return rotZ;
    }

    @Override
    public float getPosX() {
        return positionX;
    }

    @Override
    public float getPosY() {
        return positionY;
    }

    @Override
    public float getPosZ() {
        return positionZ;
    }

    @Override
    public float getScaleX() {
        return scaleX;
    }

    @Override
    public float getScaleY() {
        return scaleY;
    }

    @Override
    public float getScaleZ() {
        return scaleZ;
    }

    @Override
    public void setRotX(float v) {
        rotX = v;
        markRotationAsChanged();
    }

    @Override
    public void setRotY(float v) {
        rotY = v;
        markRotationAsChanged();
    }

    @Override
    public void setRotZ(float v) {
        rotZ = v;
        markRotationAsChanged();
    }

    @Override
    public void setPosX(float v) {
        positionX = v;
        markPositionAsChanged();
    }

    @Override
    public void setPosY(float v) {
        positionY = v;
        markPositionAsChanged();
    }

    @Override
    public void setPosZ(float v) {
        positionZ = v;
        markPositionAsChanged();
    }

    @Override
    public void setScaleX(float v) {
        scaleX = v;
        markScaleAsChanged();
    }

    @Override
    public void setScaleY(float v) {
        scaleY = v;
        markScaleAsChanged();
    }

    @Override
    public void setScaleZ(float v) {
        scaleZ = v;
        markScaleAsChanged();
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
        setChildrenHidden(hidden);
    }

    @Override
    public void setChildrenHidden(boolean hide) {
        childrenHidden = hide;
    }

    @Override
    public void setPivotX(float v) {
        pivotX = v;
    }

    @Override
    public void setPivotY(float v) {
        pivotY = v;
    }

    @Override
    public void setPivotZ(float v) {
        pivotZ = v;
    }

    @Override
    public float getPivotX() {
        return pivotX;
    }

    @Override
    public float getPivotY() {
        return pivotY;
    }

    @Override
    public float getPivotZ() {
        return pivotZ;
    }

    @Override
    public boolean isHidingChildren() {
        return childrenHidden;
    }

    @Override
    public void markScaleAsChanged() {
        scaleChanged = true;
    }

    @Override
    public void markRotationAsChanged() {
        rotationChanged = true;
    }

    @Override
    public void markPositionAsChanged() {
        positionChanged = true;
    }

    @Override
    public boolean hasScaleChanged() {
        return scaleChanged;
    }

    @Override
    public boolean hasRotationChanged() {
        return rotationChanged;
    }

    @Override
    public boolean hasPositionChanged() {
        return positionChanged;
    }

    @Override
    public void resetStateChanges() {
        scaleChanged = false;
        rotationChanged = false;
        positionChanged = false;
    }

    @Override
    public BoneSnapshot getInitialSnapshot() {
        return initialSnapshot;
    }

    @Override
    public void saveInitialSnapshot() {
        if (initialSnapshot == null) initialSnapshot = new BoneSnapshot(this);
    }

    @Override
    public List<GeoBone> getChildBones() {
        return children;
    }

    public List<GeoCube> getCubes() {
        return cubes;
    }

    public Boolean getMirror() {
        return mirror;
    }

    public Double getInflate() {
        return inflate;
    }

    public Boolean shouldNeverRender() {
        return dontRender;
    }

    public Boolean getReset() {
        return reset;
    }
}
