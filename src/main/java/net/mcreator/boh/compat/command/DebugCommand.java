package net.mcreator.boh.compat.command;

import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DebugCommand extends CommandBase {
    public String getCommandName() {
        return "bohdebug";
    }

    public String getCommandUsage(ICommandSender s) {
        return "/bohdebug [effect <boh effect name> [seconds] | dim <dimension>]";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public void processCommand(ICommandSender s, String[] args) {
        EntityPlayerMP p = getCommandSenderAsPlayer(s);
        if (args.length >= 2 && args[0].equals("dim")) {
            ResourceKey<World> key = Dimensions.dimensionKey(new ResourceLocation(args[1].contains(":") ? args[1] : "boh:" + args[1]));
            int id = Dimensions.id(key);
            if (id == 0 && !args[1].endsWith("overworld")) {
                s.addChatMessage(
                    new ChatComponentText(
                        "Unknown dimension " + args[1] + " (level_0, baseplate_dimension, boiler_room_dimension, gaster_dimension, overworld)"
                    )
                );
            } else {
                Dimensions.transferPlayer(p, id, p.posX, id == 0 ? 100.0 : p.posY, p.posZ, p.rotationYaw, p.rotationPitch);
                s.addChatMessage(new ChatComponentText("Sent to " + key.location() + " (dimension id " + id + ")"));
            }
        } else if (args.length >= 2 && args[0].equals("effect")) {
            Potion pot = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(args[1].contains(":") ? args[1] : "boh:" + args[1]));
            if (pot == null) {
                s.addChatMessage(new ChatComponentText("Unknown effect " + args[1]));
            } else {
                int secs = args.length >= 3 ? parseInt(s, args[2]) : 15;
                p.addPotionEffect(new PotionEffect(pot.id, secs * 20, 0));
                s.addChatMessage(new ChatComponentText("Applied " + pot.getName() + " (id " + pot.id + ") for " + secs + "s"));
            }
        } else {
            s.addChatMessage(new ChatComponentText("Active effects:"));

            for (Object o : p.getActivePotionEffects()) {
                PotionEffect e = (PotionEffect)o;
                Potion pot = Potion.potionTypes[e.getPotionID()];
                s.addChatMessage(new ChatComponentText("  " + (pot == null ? "?" : pot.getName()) + " id=" + e.getPotionID() + " t=" + e.getDuration()));
            }

            NBTTagCompound d = p.getEntityData();
            s.addChatMessage(
                new ChatComponentText(
                    "exe_static="
                        + d.getDouble("exe_static")
                        + " exe_apparison="
                        + d.getDouble("exe_apparison")
                        + " (server side; overlays use the client copy)"
                )
            );
        }
    }
}
