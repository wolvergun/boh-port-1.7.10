package net.mcreator.boh.compat.mc.commands;

import java.util.ArrayList;
import java.util.List;

import net.mcreator.boh.compat.command.Interpreter;
import net.mcreator.boh.compat.mojang.brigadier.builder.LiteralArgumentBuilder;

/** 1.20 Commands: runs 1.20-syntax command strings and builds literal commands. */
public class Commands {

    public static final Commands INSTANCE = new Commands();
    public static final List<LiteralArgumentBuilder> REGISTERED = new ArrayList<>();

    public int performPrefixedCommand(CommandSourceStack source, String command) {
        return Interpreter.run(command, source.toContext());
    }

    public int performCommand(Object parsed, String command) {
        return 0;
    }

    public static LiteralArgumentBuilder literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    public Object getDispatcher() {
        return this;
    }

    public void register(LiteralArgumentBuilder builder) {
        REGISTERED.add(builder);
    }
}
