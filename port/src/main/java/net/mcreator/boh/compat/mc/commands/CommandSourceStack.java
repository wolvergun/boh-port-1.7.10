package net.mcreator.boh.compat.mc.commands;

import net.mcreator.boh.compat.command.CommandContext;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/** 1.20 CommandSourceStack: everything the interpreter needs to run a command for a source. */
public class CommandSourceStack {

    private final Vec3 position;
    private final Vec2 rotation;
    private final World level;
    private final int permission;
    private final String name;
    private final Component displayName;
    private final MinecraftServer server;
    private final Entity entity;
    private boolean silent;

    public CommandSourceStack(CommandSource source, Vec3 position, Vec2 rotation, World level, int permission, String name,
        Component displayName, MinecraftServer server, Entity entity) {
        this.position = position;
        this.rotation = rotation;
        this.level = level;
        this.permission = permission;
        this.name = name;
        this.displayName = displayName;
        this.server = server;
        this.entity = entity;
    }

    public CommandSourceStack withSuppressedOutput() {
        silent = true;
        return this;
    }

    public CommandContext toContext() {
        World w = level != null ? level : entity != null ? entity.worldObj : null;
        return new CommandContext(w, entity, position, rotation == null ? 0 : rotation.y, rotation == null ? 0 : rotation.x, false);
    }

    public Entity getEntity() {
        return entity;
    }

    public Vec3 getPosition() {
        return position;
    }

    public Vec2 getRotation() {
        return rotation;
    }

    public World getLevel() {
        return level;
    }

    public World getUnsidedLevel() {
        return level;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public String getTextName() {
        return name;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public boolean hasPermission(int level) {
        return permission >= level;
    }

    public void sendSuccess(java.util.function.Supplier<Component> msg, boolean broadcast) {
        if (!silent && entity instanceof net.minecraft.entity.player.EntityPlayer)
            ((net.minecraft.entity.player.EntityPlayer) entity).addChatComponentMessage(msg.get().toVanilla());
    }

    public void sendFailure(Component msg) {
        if (entity instanceof net.minecraft.entity.player.EntityPlayer)
            ((net.minecraft.entity.player.EntityPlayer) entity).addChatComponentMessage(msg.toVanilla());
    }
}
