package net.mcreator.boh.compat.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;

/** /bohdebug [effect <name> [seconds]] - shows the player's mod effects and overlay flags, or applies a mod effect. */
public class DebugCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "bohdebug";
    }

    @Override
    public String getCommandUsage(ICommandSender s) {
        return "/bohdebug [effect <boh effect name> [seconds] | dim <dimension>]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender s, String[] args) {
        EntityPlayerMP p = getCommandSenderAsPlayer(s);
        if (args.length >= 2 && args[0].equals("dim")) {
            net.mcreator.boh.compat.mc.resources.ResourceKey<net.minecraft.world.World> key = net.mcreator.boh.compat.world.Dimensions
                .dimensionKey(new net.minecraft.util.ResourceLocation(args[1].contains(":") ? args[1] : "boh:" + args[1]));
            int id = net.mcreator.boh.compat.world.Dimensions.id(key);
            if (id == 0 && !args[1].endsWith("overworld")) {
                s.addChatMessage(new ChatComponentText("Unknown dimension " + args[1] + " (level_0, baseplate_dimension, boiler_room_dimension, gaster_dimension, overworld)"));
                return;
            }
            net.mcreator.boh.compat.world.Dimensions.transferPlayer(p, id, p.posX, id == 0 ? 100 : p.posY, p.posZ, p.rotationYaw, p.rotationPitch);
            s.addChatMessage(new ChatComponentText("Sent to " + key.location() + " (dimension id " + id + ")"));
            return;
        }
        if (args.length >= 2 && args[0].equals("effect")) {
            Potion pot = net.mcreator.boh.compat.forge.registries.ForgeRegistries.MOB_EFFECTS
                .getValue(new net.minecraft.util.ResourceLocation(args[1].contains(":") ? args[1] : "boh:" + args[1]));
            if (pot == null) {
                s.addChatMessage(new ChatComponentText("Unknown effect " + args[1]));
                return;
            }
            int secs = args.length >= 3 ? parseInt(s, args[2]) : 15;
            p.addPotionEffect(new PotionEffect(pot.id, secs * 20, 0));
            s.addChatMessage(new ChatComponentText("Applied " + pot.getName() + " (id " + pot.id + ") for " + secs + "s"));
            return;
        }
        s.addChatMessage(new ChatComponentText("Active effects:"));
        for (Object o : p.getActivePotionEffects()) {
            PotionEffect e = (PotionEffect) o;
            Potion pot = Potion.potionTypes[e.getPotionID()];
            s.addChatMessage(new ChatComponentText("  " + (pot == null ? "?" : pot.getName()) + " id=" + e.getPotionID() + " t=" + e.getDuration()));
        }
        net.minecraft.nbt.NBTTagCompound d = p.getEntityData();
        s.addChatMessage(new ChatComponentText("exe_static=" + d.getDouble("exe_static") + " exe_apparison=" + d.getDouble("exe_apparison")
            + " (server side; overlays use the client copy)"));
    }
}
