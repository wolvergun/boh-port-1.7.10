package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class OverlayFacehuggerAppearProcedure {
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.player(event));
        }
    }

    public static boolean execute(Entity entity) {
        return execute(null, entity);
    }

    private static boolean execute(@Nullable Event event, Entity entity) {
        return entity == null
            ? false
            : M.getItem(entity instanceof EntityLivingBase _entGetArmor ? M.getItemBySlot(_entGetArmor, EquipmentSlot.HEAD) : M.EMPTY)
                == BohModItems.FACEHUGGER_FACE.get();
    }
}
