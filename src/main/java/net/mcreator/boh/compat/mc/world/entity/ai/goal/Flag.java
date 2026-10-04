package net.mcreator.boh.compat.mc.world.entity.ai.goal;

public enum Flag {
    MOVE(1),
    LOOK(2),
    JUMP(4),
    TARGET(8);

    final int bit;

    private Flag(int bit) {
        this.bit = bit;
    }
}
