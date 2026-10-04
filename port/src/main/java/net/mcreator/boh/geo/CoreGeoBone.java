package net.mcreator.boh.geo;

import java.util.List;

public interface CoreGeoBone {

    String getName();

    CoreGeoBone getParent();

    float getRotX();

    float getRotY();

    float getRotZ();

    float getPosX();

    float getPosY();

    float getPosZ();

    float getScaleX();

    float getScaleY();

    float getScaleZ();

    void setRotX(float v);

    void setRotY(float v);

    void setRotZ(float v);

    default void updateRotation(float x, float y, float z) {
        setRotX(x);
        setRotY(y);
        setRotZ(z);
    }

    void setPosX(float v);

    void setPosY(float v);

    void setPosZ(float v);

    default void updatePosition(float x, float y, float z) {
        setPosX(x);
        setPosY(y);
        setPosZ(z);
    }

    void setScaleX(float v);

    void setScaleY(float v);

    void setScaleZ(float v);

    default void updateScale(float x, float y, float z) {
        setScaleX(x);
        setScaleY(y);
        setScaleZ(z);
    }

    void setPivotX(float v);

    void setPivotY(float v);

    void setPivotZ(float v);

    default void updatePivot(float x, float y, float z) {
        setPivotX(x);
        setPivotY(y);
        setPivotZ(z);
    }

    float getPivotX();

    float getPivotY();

    float getPivotZ();

    boolean isHidden();

    void setHidden(boolean hidden);

    boolean isHidingChildren();

    void setChildrenHidden(boolean hide);

    void markScaleAsChanged();

    void markRotationAsChanged();

    void markPositionAsChanged();

    boolean hasScaleChanged();

    boolean hasRotationChanged();

    boolean hasPositionChanged();

    void resetStateChanges();

    BoneSnapshot getInitialSnapshot();

    void saveInitialSnapshot();

    List<? extends CoreGeoBone> getChildBones();
}
