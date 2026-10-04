package net.mcreator.boh.init;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.geo.GeoItem;
import net.mcreator.boh.item.DeerMaskItem;
import net.mcreator.boh.item.ExeBootsItem;
import net.mcreator.boh.item.GojiHeadItem;
import net.mcreator.boh.item.WhisperingThornsItem;

public class ArmorAnimationFactory {
    @SubscribeEvent
    public void animatedArmors(PlayerTickEvent event) {
        String animation = "";
        if (event.phase == Phase.END) {
            if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.HEAD)) != M.getItem(M.EMPTY)
                && M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.HEAD)) instanceof GeoItem
                && !M.getString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.HEAD)), "geckoAnim").equals("")) {
                animation = M.getString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.HEAD)), "geckoAnim");
                M.putString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.HEAD)), "geckoAnim", "");
                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.HEAD)) instanceof DeerMaskItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.HEAD)) instanceof GojiHeadItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.HEAD)) instanceof ExeBootsItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.HEAD)) instanceof WhisperingThornsItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }
            }

            if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.CHEST)) != M.getItem(M.EMPTY)
                && M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.CHEST)) instanceof GeoItem
                && !M.getString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.CHEST)), "geckoAnim").equals("")) {
                animation = M.getString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.CHEST)), "geckoAnim");
                M.putString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.CHEST)), "geckoAnim", "");
                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.CHEST)) instanceof DeerMaskItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.CHEST)) instanceof GojiHeadItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.CHEST)) instanceof ExeBootsItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.CHEST)) instanceof WhisperingThornsItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }
            }

            if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.LEGS)) != M.getItem(M.EMPTY)
                && M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.LEGS)) instanceof GeoItem
                && !M.getString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.LEGS)), "geckoAnim").equals("")) {
                animation = M.getString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.LEGS)), "geckoAnim");
                M.putString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.LEGS)), "geckoAnim", "");
                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.LEGS)) instanceof DeerMaskItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.LEGS)) instanceof GojiHeadItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.LEGS)) instanceof ExeBootsItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.LEGS)) instanceof WhisperingThornsItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }
            }

            if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.FEET)) != M.getItem(M.EMPTY)
                && M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.FEET)) instanceof GeoItem
                && !M.getString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.FEET)), "geckoAnim").equals("")) {
                animation = M.getString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.FEET)), "geckoAnim");
                M.putString(M.getOrCreateTag(M.getItemBySlot(M.player(event), EquipmentSlot.FEET)), "geckoAnim", "");
                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.FEET)) instanceof DeerMaskItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.FEET)) instanceof GojiHeadItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.FEET)) instanceof ExeBootsItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }

                if (M.getItem(M.getItemBySlot(M.player(event), EquipmentSlot.FEET)) instanceof WhisperingThornsItem animatable
                    && M.isClientSide(M.level(M.player(event)))) {
                    animatable.animationprocedure = animation;
                }
            }
        }
    }
}
