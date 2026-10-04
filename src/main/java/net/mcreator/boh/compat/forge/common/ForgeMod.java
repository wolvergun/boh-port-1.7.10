package net.mcreator.boh.compat.forge.common;

import java.util.function.Supplier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public final class ForgeMod {
    private static final IAttribute SWIM = new RangedAttribute("forge.swimSpeed", 1.0, 0.0, 1024.0);
    private static final IAttribute GRAVITY = new RangedAttribute("forge.entity_gravity", 0.08, -8.0, 8.0);
    private static final IAttribute REACH = new RangedAttribute("forge.block_reach", 4.5, 0.0, 1024.0);
    public static final Supplier<IAttribute> SWIM_SPEED = () -> SWIM;
    public static final Supplier<IAttribute> ENTITY_GRAVITY = () -> GRAVITY;
    public static final Supplier<IAttribute> BLOCK_REACH = () -> REACH;

    private ForgeMod() {
    }
}
