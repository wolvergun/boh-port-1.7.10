package net.mcreator.boh.compat.forge.items.wrapper;

import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.forge.items.IItemHandlerModifiable;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.WorldlyContainer;

/** Forge SidedInvWrapper (all faces share the container's slots here). */
public class SidedInvWrapper extends InvWrapper {

    public SidedInvWrapper(WorldlyContainer inv, Direction side) {
        super(inv);
    }

    @SuppressWarnings("unchecked")
    public static LazyOptional<IItemHandlerModifiable>[] create(WorldlyContainer inv, Direction... sides) {
        LazyOptional<IItemHandlerModifiable>[] out = new LazyOptional[sides.length];
        for (int i = 0; i < sides.length; i++) {
            Direction d = sides[i];
            out[i] = LazyOptional.of(() -> new SidedInvWrapper(inv, d));
        }
        return out;
    }
}
