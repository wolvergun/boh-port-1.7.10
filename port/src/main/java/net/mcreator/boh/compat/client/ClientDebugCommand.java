package net.mcreator.boh.compat.client;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;

/** /bohclient - client-side view of the player's mod effects and overlay flags (what the HUD overlays read). */
public class ClientDebugCommand extends CommandBase {

    public static void register() {
        ClientCommandHandler.instance.registerCommand(new ClientDebugCommand());
    }

    @Override
    public String getCommandName() {
        return "bohclient";
    }

    @Override
    public String getCommandUsage(ICommandSender s) {
        return "/bohclient [static|apparison <n>]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender s) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender s, String[] args) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;
        if (args.length == 2 && (args[0].equals("static") || args[0].equals("apparison"))) {
            mc.thePlayer.getEntityData().setDouble("exe_" + args[0], Double.parseDouble(args[1]));
            s.addChatMessage(new ChatComponentText("[client] set exe_" + args[0] + "=" + args[1] + " (stays until the effect re-rolls it; 0 to clear)"));
            return;
        }
        s.addChatMessage(new ChatComponentText("[client] effects:"));
        for (Object o : mc.thePlayer.getActivePotionEffects()) {
            PotionEffect e = (PotionEffect) o;
            Potion p = Potion.potionTypes[e.getPotionID()];
            s.addChatMessage(new ChatComponentText("  " + (p == null ? "?" : p.getName()) + " id=" + e.getPotionID() + " t=" + e.getDuration()));
        }
        s.addChatMessage(new ChatComponentText("[client] exe_static=" + mc.thePlayer.getEntityData().getDouble("exe_static") + " exe_apparison="
            + mc.thePlayer.getEntityData().getDouble("exe_apparison") + " overlayEvents=" + ClientEventBridge.overlayEvents
            + " framesWithFlag=" + ClientEventBridge.flagFrames + " clientEffectTicks=" + net.mcreator.boh.compat.effect.BohMobEffect.clientTicks));
    }
}
