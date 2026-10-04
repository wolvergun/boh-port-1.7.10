package net.mcreator.boh.procedures;

import java.util.Map;
import java.util.function.Supplier;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class ForgiveYesProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            BohModVariables.MapVariables.get(world).Kill_WF = 0.0;
            BohModVariables.MapVariables.get(world).syncData(world);
            if (entity instanceof EntityPlayer _player && M.containerMenu(_player) instanceof Supplier _current && _current.get() instanceof Map _slots) {
                M.remove(((Slot) _slots.get(0)), 1);
                M.broadcastChanges(M.containerMenu(_player));
            }
        }
    }
}
