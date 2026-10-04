package net.mcreator.boh.compat.mc.world.item;

public enum ItemDisplayContext {
    NONE,
    THIRD_PERSON_LEFT_HAND,
    THIRD_PERSON_RIGHT_HAND,
    FIRST_PERSON_LEFT_HAND,
    FIRST_PERSON_RIGHT_HAND,
    HEAD,
    GUI,
    GROUND,
    FIXED;

    public boolean firstPerson() {
        return this == FIRST_PERSON_LEFT_HAND || this == FIRST_PERSON_RIGHT_HAND;
    }
}
