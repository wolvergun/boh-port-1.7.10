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
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import software.bernie.geckolib.animatable.GeoItem;

@EventBusSubscriber
public class ItemAnimationFactory {
   @SubscribeEvent
   public static void animatedItems(PlayerTickEvent event) {
      String animation = "";
      ItemStack mainhandItem = event.player.getMainHandItem().copy();
      ItemStack offhandItem = event.player.getOffhandItem().copy();
      if (event.phase == Phase.START && (mainhandItem.getItem() instanceof GeoItem || offhandItem.getItem() instanceof GeoItem)) {
         if (mainhandItem.getItem() instanceof TheGreatKnifeItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((TheGreatKnifeItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof TheGreatKnifeItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((TheGreatKnifeItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof RayGunItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((RayGunItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof RayGunItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((RayGunItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof SchizosledgeItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((SchizosledgeItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof SchizosledgeItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((SchizosledgeItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof SimonsBookItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((SimonsBookItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof SimonsBookItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((SimonsBookItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof MassacreAxeItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((MassacreAxeItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof MassacreAxeItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((MassacreAxeItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof CrucifixitemItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((CrucifixitemItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof CrucifixitemItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((CrucifixitemItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof SirenphoneItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((SirenphoneItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof SirenphoneItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((SirenphoneItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof WfPistolItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((WfPistolItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof WfPistolItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((WfPistolItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof GunWithOneBulletItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((GunWithOneBulletItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof GunWithOneBulletItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((GunWithOneBulletItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof BigDaddyDrillItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((BigDaddyDrillItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof BigDaddyDrillItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((BigDaddyDrillItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof StopSignItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((StopSignItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof StopSignItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((StopSignItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof ChainsawItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((ChainsawItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof ChainsawItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((ChainsawItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof FreddyClawItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((FreddyClawItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof FreddyClawItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((FreddyClawItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof PaintedSwordItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((PaintedSwordItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof PaintedSwordItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((PaintedSwordItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof MacheteItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((MacheteItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof MacheteItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((MacheteItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof GiantScissorItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((GiantScissorItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof GiantScissorItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((GiantScissorItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof ZacksScytheItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((ZacksScytheItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof ZacksScytheItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((ZacksScytheItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof BoomStickItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((BoomStickItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof BoomStickItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((BoomStickItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof HemotorrentItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((HemotorrentItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof HemotorrentItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((HemotorrentItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (mainhandItem.getItem() instanceof MimicryItem animatable) {
            animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((MimicryItem)event.player.getMainHandItem().getItem()).animationprocedure = animation;
               }
            }
         }

         if (offhandItem.getItem() instanceof MimicryItem animatable) {
            animation = offhandItem.getOrCreateTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
               event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
               if (event.player.level().isClientSide()) {
                  ((MimicryItem)event.player.getOffhandItem().getItem()).animationprocedure = animation;
               }
            }
         }
      }
   }
}
