package net.mcreator.boh.compat.forge;

import net.mcreator.boh.compat.forge.event.entity.EntityTravelToDimensionEvent;
import net.mcreator.boh.compat.forge.event.entity.living.LivingChangeTargetEvent;
import net.mcreator.boh.compat.forge.event.entity.living.ShieldBlockEvent;
import net.mcreator.boh.compat.forge.event.entity.player.LeftClickBlock;
import net.mcreator.boh.compat.forge.event.entity.player.RightClickBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

/** Posts the 1.20-only events the mod listens to, derived from their 1.7.10 counterparts. */
public final class EventBridge {

    private static boolean retargeting;

    public static void install() {
        EventBridge b = new EventBridge();
        MinecraftForge.EVENT_BUS.register(b);
        FMLCommonHandler.instance().bus().register(b);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onSetTarget(LivingSetAttackTargetEvent e) {
        if (retargeting || e.target == null || !(e.entityLiving instanceof EntityLiving)) return;
        LivingChangeTargetEvent ev = new LivingChangeTargetEvent(e.entityLiving, e.target);
        MinecraftForge.EVENT_BUS.post(ev);
        if (ev.isCanceled() || ev.getNewTarget() != e.target) {
            retargeting = true;
            try {
                ((EntityLiving) e.entityLiving).setAttackTarget(ev.isCanceled() ? null : ev.getNewTarget());
            } finally {
                retargeting = false;
            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent e) {
        if (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            RightClickBlock ev = new RightClickBlock(e.entityPlayer, new BlockPos(e.x, e.y, e.z), Direction.from3DDataValue(e.face));
            if (MinecraftForge.EVENT_BUS.post(ev)) e.setCanceled(true);
        } else if (e.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            LeftClickBlock ev = new LeftClickBlock(e.entityPlayer, new BlockPos(e.x, e.y, e.z), Direction.from3DDataValue(e.face));
            if (MinecraftForge.EVENT_BUS.post(ev)) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onAttack(LivingAttackEvent e) {
        if (e.entityLiving instanceof EntityPlayer && ((EntityPlayer) e.entityLiving).isBlocking() && !e.source.isUnblockable()) {
            ShieldBlockEvent ev = new ShieldBlockEvent(e.entityLiving, e.source, e.ammount);
            MinecraftForge.EVENT_BUS.post(ev);
        }
    }

    @SubscribeEvent
    public void onChangedDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
        MinecraftForge.EVENT_BUS.post(new EntityTravelToDimensionEvent(e.player, e.toDim));
    }
}
