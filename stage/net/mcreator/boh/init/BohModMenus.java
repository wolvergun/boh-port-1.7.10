package net.mcreator.boh.init;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.world.inventory.ComputerGUIMenu;
import net.mcreator.boh.world.inventory.ForgivenesScreenMenu;
import net.mcreator.boh.world.inventory.KillscreenWFMenu;
import net.mcreator.boh.world.inventory.WitnessUIMenu;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.mcreator.boh.compat.forge.common.extensions.IForgeMenuType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mcreator.boh.compat.forge.network.PacketDistributor;
import net.mcreator.boh.compat.forge.network.Context;
import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.forge.registries.RegistryObject;
import net.mcreator.boh.compat.M;

public class BohModMenus {

    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "boh");

    public static final RegistryObject<MenuType<KillscreenWFMenu>> KILLSCREEN_WF = REGISTRY.register("killscreen_wf", () -> IForgeMenuType.create(KillscreenWFMenu::new));

    public static final RegistryObject<MenuType<ForgivenesScreenMenu>> FORGIVENES_SCREEN = REGISTRY.register("forgivenes_screen", () -> IForgeMenuType.create(ForgivenesScreenMenu::new));

    public static final RegistryObject<MenuType<WitnessUIMenu>> WITNESS_UI = REGISTRY.register("witness_ui", () -> IForgeMenuType.create(WitnessUIMenu::new));

    public static final RegistryObject<MenuType<ComputerGUIMenu>> COMPUTER_GUI = REGISTRY.register("computer_gui", () -> IForgeMenuType.create(ComputerGUIMenu::new));

    public static void setText(String boxname, String value, @Nullable EntityPlayerMP player) {
        if (player != null) {
            BohMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new BohModMenus.GuiSyncMessage(boxname, value));
        } else {
            BohMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new BohModMenus.GuiSyncMessage(boxname, value));
        }
    }

    @SubscribeEvent
    public void init(FMLCommonSetupEvent event) {
        BohMod.addNetworkMessage(BohModMenus.GuiSyncMessage.class, BohModMenus.GuiSyncMessage::buffer, BohModMenus.GuiSyncMessage::new, BohModMenus.GuiSyncMessage::handleData);
    }

    public static class GuiSyncMessage {

        private final String textboxid;

        private final String data;

        public GuiSyncMessage(FriendlyByteBuf buffer) {
            this.textboxid = M.getString(M.readComponent(buffer));
            this.data = M.getString(M.readComponent(buffer));
        }

        public GuiSyncMessage(String textboxid, String data) {
            this.textboxid = textboxid;
            this.data = data;
        }

        public static void buffer(BohModMenus.GuiSyncMessage message, FriendlyByteBuf buffer) {
            M.writeComponent(buffer, Component.literal(message.textboxid));
            M.writeComponent(buffer, Component.literal(message.data));
        }

        public static void handleData(BohModMenus.GuiSyncMessage message, Supplier<Context> contextSupplier) {
            Context context = contextSupplier.get();
            M.enqueueWork(context, () -> {
                if (!M.isServer(M.getReceptionSide(M.getDirection(context)))) {
                    BohModScreens.handleTextBoxMessage(message);
                }
            });
            M.setPacketHandled(context, true);
        }

        String editbox() {
            return this.textboxid;
        }

        String value() {
            return this.data;
        }
    }
}
