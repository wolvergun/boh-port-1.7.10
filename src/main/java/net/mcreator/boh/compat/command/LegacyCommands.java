package net.mcreator.boh.compat.command;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import java.util.List;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.commands.Commands;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;

public final class LegacyCommands {
    private LegacyCommands() {
    }

    public static void registerAll(FMLServerStartingEvent event) {
        for (LiteralArgumentBuilder b : Commands.REGISTERED) {
            event.registerServerCommand(new LegacyCommands.Wrapped(b));
        }

        event.registerServerCommand(new DebugCommand());
    }

    static CommandSourceStack stackFor(ICommandSender s) {
        Entity e = s instanceof Entity ? (Entity)s : null;
        ChunkCoordinates c = s.getPlayerCoordinates();
        Vec3 pos = e != null
            ? new Vec3(e.posX, e.boundingBox.minY, e.posZ)
            : new Vec3(c.posX + 0.5, c.posY, c.posZ + 0.5);
        Vec2 rot = e != null ? new Vec2(e.rotationPitch, e.rotationYaw) : new Vec2(0.0F, 0.0F);
        int perm = s instanceof EntityPlayerMP
            ? (MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayerMP)s).getGameProfile()) ? 4 : 0)
            : 4;
        if (MinecraftServer.getServer() != null
            && MinecraftServer.getServer().isSinglePlayer()
            && s instanceof EntityPlayerMP
            && MinecraftServer.getServer().worldServers[0].getWorldInfo().areCommandsAllowed()) {
            perm = 4;
        }

        return new CommandSourceStack(
            CommandSource.NULL, pos, rot, s.getEntityWorld(), perm, s.getCommandSenderName(), Component.literal(s.getCommandSenderName()), MinecraftServer.getServer(), e
        );
    }

    static final class Wrapped extends CommandBase {
        private final LiteralArgumentBuilder builder;

        Wrapped(LiteralArgumentBuilder b) {
            this.builder = b;
        }

        public String getCommandName() {
            return this.builder.name;
        }

        public String getCommandUsage(ICommandSender s) {
            return "/" + this.builder.name;
        }

        public int getRequiredPermissionLevel() {
            return 0;
        }

        public boolean canCommandSenderUseCommand(ICommandSender s) {
            return this.builder.requirement.test(LegacyCommands.stackFor(s));
        }

        public void processCommand(ICommandSender s, String[] args) {
            if (this.builder.executor != null) {
                try {
                    this.builder.executor.run(new LiteralArgumentBuilder.CommandArguments(LegacyCommands.stackFor(s)));
                } catch (Exception var4) {
                    throw new CommandException(String.valueOf(var4.getMessage()), new Object[0]);
                }
            }
        }

        public List addTabCompletionOptions(ICommandSender s, String[] args) {
            return null;
        }
    }
}
