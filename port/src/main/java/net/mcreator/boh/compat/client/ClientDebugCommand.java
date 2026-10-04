package net.mcreator.boh.compat.client;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;

/**
 * /bohclient - client-side view of the player's mod effects and overlay flags (what the HUD overlays read);
 * /bohclient mobs [radius] - the mobs the client knows about, to compare with the server's /bohdebug inspect.
 */
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
        return "/bohclient [static|apparison <n> | mobs [radius]]";
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
        if (args.length >= 1 && args[0].equals("mobs")) {
            double r = args.length >= 2 ? Double.parseDouble(args[1]) : 32;
            java.util.List<EntityLivingBase> list = new java.util.ArrayList<>();
            for (Object o : mc.theWorld.loadedEntityList) {
                if (o instanceof EntityLivingBase && o != mc.thePlayer && mc.thePlayer.getDistanceToEntity((Entity) o) <= r) list.add((EntityLivingBase) o);
            }
            list.sort(java.util.Comparator.comparingDouble(e -> mc.thePlayer.getDistanceToEntity(e)));
            s.addChatMessage(new ChatComponentText("[client] " + list.size() + " mobs within " + r + " blocks:"));
            for (EntityLivingBase e : list.subList(0, Math.min(8, list.size()))) {
                s.addChatMessage(new ChatComponentText(String.format("  %s #%d at %.1f %.1f %.1f (%.1f) hp=%.1f deathTime=%d dead=%s", e.getClass().getSimpleName(),
                    e.getEntityId(), e.posX, e.posY, e.posZ, mc.thePlayer.getDistanceToEntity(e), e.getHealth(), e.deathTime, e.isDead)));
                // ticks: does the client still update it; serverPos: last position packet; lerp: interpolation steps left
                s.addChatMessage(new ChatComponentText(String.format("    ticks=%d serverPos=%.1f %.1f %.1f lerp=%s", e.ticksExisted, e.serverPosX / 32.0,
                    e.serverPosY / 32.0, e.serverPosZ / 32.0, lerpSteps(e))));
            }
            return;
        }
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

    private static String lerpSteps(EntityLivingBase e) {
        try {
            java.lang.reflect.Field f;
            try {
                f = EntityLivingBase.class.getDeclaredField("newPosRotationIncrements");
            } catch (NoSuchFieldException ex) {
                f = EntityLivingBase.class.getDeclaredField("field_70716_bi");
            }
            f.setAccessible(true);
            return String.valueOf(f.getInt(e));
        } catch (ReflectiveOperationException ex) {
            return "?";
        }
    }
}
