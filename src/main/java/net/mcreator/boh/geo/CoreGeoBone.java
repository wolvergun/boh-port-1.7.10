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

    void setRotX(float var1);

    void setRotY(float var1);

    void setRotZ(float var1);

    default void updateRotation(float x, float y, float z) {
        this.setRotX(x);
        this.setRotY(y);
        this.setRotZ(z);
    }

    void setPosX(float var1);

    void setPosY(float var1);

    void setPosZ(float var1);

    default void updatePosition(float x, float y, float z) {
        this.setPosX(x);
        this.setPosY(y);
        this.setPosZ(z);
    }

    void setScaleX(float var1);

    void setScaleY(float var1);

    void setScaleZ(float var1);

    default void updateScale(float x, float y, float z) {
        this.setScaleX(x);
        this.setScaleY(y);
        this.setScaleZ(z);
    }

    void setPivotX(float var1);

    void setPivotY(float var1);

    void setPivotZ(float var1);

    default void updatePivot(float x, float y, float z) {
        this.setPivotX(x);
        this.setPivotY(y);
        this.setPivotZ(z);
    }

    float getPivotX();

    float getPivotY();

    float getPivotZ();

    boolean isHidden();

    void setHidden(boolean var1);

    boolean isHidingChildren();

    void setChildrenHidden(boolean var1);

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
