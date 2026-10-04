package net.mcreator.boh.init;

import net.mcreator.boh.item.BigDaddyDrillItem;
import net.mcreator.boh.item.BoomStickItem;
import net.mcreator.boh.item.ChainsawItem;
import net.mcreator.boh.item.CrucifixitemItem;
import net.mcreator.boh.item.FreddyClawItem;
import net.mcreator.boh.item.GiantScissorItem;
import net.mcreator.boh.item.GunWithOneBulletItem;
import net.mcreator.boh.item.HemotorrentItem;
import net.mcreator.boh.item.MacheteItem;
import net.mcreator.boh.item.MassacreAxeItem;
import net.mcreator.boh.item.MimicryItem;
import net.mcreator.boh.item.PaintedSwordItem;
import net.mcreator.boh.item.RayGunItem;
import net.mcreator.boh.item.SchizosledgeItem;
import net.mcreator.boh.item.SimonsBookItem;
import net.mcreator.boh.item.SirenphoneItem;
import net.mcreator.boh.item.StopSignItem;
import net.mcreator.boh.item.TheGreatKnifeItem;
import net.mcreator.boh.item.WfPistolItem;
import net.mcreator.boh.item.ZacksScytheItem;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.geo.GeoItem;
import net.mcreator.boh.compat.M;

public class ItemAnimationFactory {

    @SubscribeEvent
    public void animatedItems(PlayerTickEvent event) {
        String animation = "";
        ItemStack mainhandItem = M.copy(M.getMainHandItem(M.player(event)));
        ItemStack offhandItem = M.copy(M.getOffhandItem(M.player(event)));
        if (event.phase == Phase.START && (M.getItem(mainhandItem) instanceof GeoItem || M.getItem(offhandItem) instanceof GeoItem)) {
            if (M.getItem(mainhandItem) instanceof TheGreatKnifeItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((TheGreatKnifeItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof TheGreatKnifeItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((TheGreatKnifeItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof RayGunItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((RayGunItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof RayGunItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((RayGunItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof SchizosledgeItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SchizosledgeItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof SchizosledgeItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SchizosledgeItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof SimonsBookItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SimonsBookItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof SimonsBookItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SimonsBookItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof MassacreAxeItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MassacreAxeItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof MassacreAxeItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MassacreAxeItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof CrucifixitemItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((CrucifixitemItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof CrucifixitemItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((CrucifixitemItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof SirenphoneItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SirenphoneItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof SirenphoneItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SirenphoneItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof WfPistolItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((WfPistolItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof WfPistolItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((WfPistolItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof GunWithOneBulletItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((GunWithOneBulletItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof GunWithOneBulletItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((GunWithOneBulletItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof BigDaddyDrillItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((BigDaddyDrillItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof BigDaddyDrillItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((BigDaddyDrillItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof StopSignItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((StopSignItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof StopSignItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((StopSignItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof ChainsawItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((ChainsawItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof ChainsawItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((ChainsawItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof FreddyClawItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((FreddyClawItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof FreddyClawItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((FreddyClawItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof PaintedSwordItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((PaintedSwordItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof PaintedSwordItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((PaintedSwordItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof MacheteItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MacheteItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof MacheteItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MacheteItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof GiantScissorItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((GiantScissorItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof GiantScissorItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((GiantScissorItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof ZacksScytheItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((ZacksScytheItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof ZacksScytheItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((ZacksScytheItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof BoomStickItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((BoomStickItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof BoomStickItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((BoomStickItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof HemotorrentItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((HemotorrentItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof HemotorrentItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((HemotorrentItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(mainhandItem) instanceof MimicryItem animatable) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MimicryItem) M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
            if (M.getItem(offhandItem) instanceof MimicryItem animatable) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MimicryItem) M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
        }
    }
}
