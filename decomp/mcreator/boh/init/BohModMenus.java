package net.mcreator.boh.init;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.world.inventory.ComputerGUIMenu;
import net.mcreator.boh.world.inventory.ForgivenesScreenMenu;
import net.mcreator.boh.world.inventory.KillscreenWFMenu;
import net.mcreator.boh.world.inventory.WitnessUIMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD)
public class BohModMenus {
   public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "boh");
   public static final RegistryObject<MenuType<KillscreenWFMenu>> KILLSCREEN_WF = REGISTRY.register(
      "killscreen_wf", () -> IForgeMenuType.create(KillscreenWFMenu::new)
   );
   public static final RegistryObject<MenuType<ForgivenesScreenMenu>> FORGIVENES_SCREEN = REGISTRY.register(
      "forgivenes_screen", () -> IForgeMenuType.create(ForgivenesScreenMenu::new)
   );
   public static final RegistryObject<MenuType<WitnessUIMenu>> WITNESS_UI = REGISTRY.register("witness_ui", () -> IForgeMenuType.create(WitnessUIMenu::new));
   public static final RegistryObject<MenuType<ComputerGUIMenu>> COMPUTER_GUI = REGISTRY.register(
      "computer_gui", () -> IForgeMenuType.create(ComputerGUIMenu::new)
   );

   public static void setText(String boxname, String value, @Nullable ServerPlayer player) {
      if (player != null) {
         BohMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new BohModMenus.GuiSyncMessage(boxname, value));
      } else {
         BohMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new BohModMenus.GuiSyncMessage(boxname, value));
      }
   }

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      BohMod.addNetworkMessage(
         BohModMenus.GuiSyncMessage.class, BohModMenus.GuiSyncMessage::buffer, BohModMenus.GuiSyncMessage::new, BohModMenus.GuiSyncMessage::handleData
      );
   }

   public static class GuiSyncMessage {
      private final String textboxid;
      private final String data;

      public GuiSyncMessage(FriendlyByteBuf buffer) {
         this.textboxid = buffer.readComponent().getString();
         this.data = buffer.readComponent().getString();
      }

      public GuiSyncMessage(String textboxid, String data) {
         this.textboxid = textboxid;
         this.data = data;
      }

      public static void buffer(BohModMenus.GuiSyncMessage message, FriendlyByteBuf buffer) {
         buffer.writeComponent(Component.literal(message.textboxid));
         buffer.writeComponent(Component.literal(message.data));
      }

      public static void handleData(BohModMenus.GuiSyncMessage message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            if (!context.getDirection().getReceptionSide().isServer()) {
               BohModScreens.handleTextBoxMessage(message);
            }
         });
         context.setPacketHandled(true);
      }

      String editbox() {
         return this.textboxid;
      }

      String value() {
         return this.data;
      }
   }
}
