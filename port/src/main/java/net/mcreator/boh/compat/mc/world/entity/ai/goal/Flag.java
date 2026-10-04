package net.mcreator.boh.compat.mc.world.entity.ai.goal;

/** 1.20 Goal.Flag, mapped to 1.7.10 mutex bits. */
public enum Flag {

    MOVE(1),
    LOOK(2),
    JUMP(4),
    TARGET(8);

    final int bit;

    Flag(int bit) {
        this.bit = bit;
    }
}
