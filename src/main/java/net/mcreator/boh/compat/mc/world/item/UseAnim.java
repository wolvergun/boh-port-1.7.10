package net.mcreator.boh.compat.mc.world.item;

import net.minecraft.item.EnumAction;

public enum UseAnim {
    NONE(EnumAction.none),
    EAT(EnumAction.eat),
    DRINK(EnumAction.drink),
    BLOCK(EnumAction.block),
    BOW(EnumAction.bow),
    SPEAR(EnumAction.bow),
    CROSSBOW(EnumAction.bow),
    SPYGLASS(EnumAction.none),
    TOOT_HORN(EnumAction.none),
    BRUSH(EnumAction.none);

    private final EnumAction legacy;

    private UseAnim(EnumAction legacy) {
        this.legacy = legacy;
    }

    public EnumAction toVanilla() {
        return this.legacy;
    }
}
