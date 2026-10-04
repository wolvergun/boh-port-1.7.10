package net.mcreator.boh.init;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.geo.GeoItem;
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
                        ((TheGreatKnifeItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof TheGreatKnifeItem animatablex) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((TheGreatKnifeItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof RayGunItem animatablexx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((RayGunItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof RayGunItem animatablexxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((RayGunItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof SchizosledgeItem animatablexxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SchizosledgeItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof SchizosledgeItem animatablexxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SchizosledgeItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof SimonsBookItem animatablexxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SimonsBookItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof SimonsBookItem animatablexxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SimonsBookItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof MassacreAxeItem animatablexxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MassacreAxeItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof MassacreAxeItem animatablexxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MassacreAxeItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof CrucifixitemItem animatablexxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((CrucifixitemItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof CrucifixitemItem animatablexxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((CrucifixitemItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof SirenphoneItem animatablexxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SirenphoneItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof SirenphoneItem animatablexxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((SirenphoneItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof WfPistolItem animatablexxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((WfPistolItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof WfPistolItem animatablexxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((WfPistolItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof GunWithOneBulletItem animatablexxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((GunWithOneBulletItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof GunWithOneBulletItem animatablexxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((GunWithOneBulletItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof BigDaddyDrillItem animatablexxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((BigDaddyDrillItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof BigDaddyDrillItem animatablexxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((BigDaddyDrillItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof StopSignItem animatablexxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((StopSignItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof StopSignItem animatablexxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((StopSignItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof ChainsawItem animatablexxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((ChainsawItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof ChainsawItem animatablexxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((ChainsawItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof FreddyClawItem animatablexxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((FreddyClawItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof FreddyClawItem animatablexxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((FreddyClawItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof PaintedSwordItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((PaintedSwordItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof PaintedSwordItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((PaintedSwordItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof MacheteItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MacheteItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof MacheteItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MacheteItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof GiantScissorItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((GiantScissorItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof GiantScissorItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((GiantScissorItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof ZacksScytheItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((ZacksScytheItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof ZacksScytheItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((ZacksScytheItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof BoomStickItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((BoomStickItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof BoomStickItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((BoomStickItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof HemotorrentItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((HemotorrentItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof HemotorrentItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((HemotorrentItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(mainhandItem) instanceof MimicryItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(mainhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getMainHandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MimicryItem)M.getItem(M.getMainHandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }

            if (M.getItem(offhandItem) instanceof MimicryItem animatablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                animation = M.getString(M.getOrCreateTag(offhandItem), "geckoAnim");
                if (!M.isEmpty(animation)) {
                    M.putString(M.getOrCreateTag(M.getOffhandItem(M.player(event))), "geckoAnim", "");
                    if (M.isClientSide(M.level(M.player(event)))) {
                        ((MimicryItem)M.getItem(M.getOffhandItem(M.player(event)))).animationprocedure = animation;
                    }
                }
            }
        }
    }
}
