package net.mcreator.boh.compat.mc.world.entity;

public enum HumanoidArm {

    LEFT,
    RIGHT;

    public HumanoidArm getOpposite() {
        return this == LEFT ? RIGHT : LEFT;
    }
}
