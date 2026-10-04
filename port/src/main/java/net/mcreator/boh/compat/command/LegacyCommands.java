package net.mcreator.boh.compat.command;

import java.util.List;

import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.commands.Commands;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

/** Registers the mod's brigadier literal commands as 1.7.10 commands. */
public final class LegacyCommands {

    private LegacyCommands() {}

    public static void registerAll(FMLServerStartingEvent event) {
        for (LiteralArgumentBuilder b : Commands.REGISTERED) event.registerServerCommand(new Wrapped(b));
        event.registerServerCommand(new DebugCommand());
    }

    static CommandSourceStack stackFor(ICommandSender s) {
        Entity e = s instanceof Entity ? (Entity) s : null;
        ChunkCoordinates c = s.getPlayerCoordinates();
        Vec3 pos = e != null ? new Vec3(e.posX, e.boundingBox.minY, e.posZ) : new Vec3(c.posX + 0.5, c.posY, c.posZ + 0.5);
        Vec2 rot = e != null ? new Vec2(e.rotationPitch, e.rotationYaw) : new Vec2(0, 0);
        int perm = s instanceof net.minecraft.entity.player.EntityPlayerMP
            ? (MinecraftServer.getServer().getConfigurationManager().func_152596_g(((net.minecraft.entity.player.EntityPlayerMP) s).getGameProfile()) ? 4 : 0)
            : 4;
        if (MinecraftServer.getServer() != null && MinecraftServer.getServer().isSinglePlayer() && s instanceof net.minecraft.entity.player.EntityPlayerMP
            && MinecraftServer.getServer().worldServers[0].getWorldInfo().areCommandsAllowed()) perm = 4;
        return new CommandSourceStack(CommandSource.NULL, pos, rot, s.getEntityWorld(), perm, s.getCommandSenderName(),
            Component.literal(s.getCommandSenderName()), MinecraftServer.getServer(), e);
    }

    static final class Wrapped extends CommandBase {

        private final LiteralArgumentBuilder builder;

        Wrapped(LiteralArgumentBuilder b) {
            builder = b;
        }

        @Override
        public String getCommandName() {
            return builder.name;
        }

        @Override
        public String getCommandUsage(ICommandSender s) {
            return "/" + builder.name;
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 0;
        }

        @Override
        public boolean canCommandSenderUseCommand(ICommandSender s) {
            return builder.requirement.test(stackFor(s));
        }

        @Override
        public void processCommand(ICommandSender s, String[] args) {
            if (builder.executor == null) return;
            try {
                builder.executor.run(new LiteralArgumentBuilder.CommandArguments(stackFor(s)));
            } catch (Exception e) {
                throw new net.minecraft.command.CommandException(String.valueOf(e.getMessage()));
            }
        }

        @Override
        @SuppressWarnings("rawtypes")
        public List addTabCompletionOptions(ICommandSender s, String[] args) {
            return null;
        }
    }
}
