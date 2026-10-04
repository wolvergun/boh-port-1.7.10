package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DeathPlayerWithHuggerProcedure {
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.getEntity(event));
        }
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity != null
            && M.getItem(entity instanceof EntityLivingBase _entGetArmor ? M.getItemBySlot(_entGetArmor, EquipmentSlot.HEAD) : M.EMPTY)
                == BohModItems.FACEHUGGER_FACE.get()
            && entity instanceof EntityPlayer _player) {
            ItemStack _stktoremove = M.new_ItemStack(BohModItems.FACEHUGGER_FACE.get());
            M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 999, M.getCraftSlots(M.inventoryMenu(_player)));
        }
    }
}
