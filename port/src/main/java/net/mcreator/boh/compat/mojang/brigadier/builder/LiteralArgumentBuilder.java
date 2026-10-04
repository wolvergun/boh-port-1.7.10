package net.mcreator.boh.compat.mojang.brigadier.builder;

import java.util.function.Predicate;

import net.mcreator.boh.compat.mc.commands.CommandSourceStack;

/** Minimal brigadier literal: name + permission predicate + executor (registered as a 1.7.10 command). */
public class LiteralArgumentBuilder {

    @FunctionalInterface
    public interface Executor {

        int run(CommandArguments args) throws Exception;
    }

    /** What 1.20 command lambdas call getSource() on. */
    public static final class CommandArguments {

        private final CommandSourceStack source;

        public CommandArguments(CommandSourceStack source) {
            this.source = source;
        }

        public CommandSourceStack getSource() {
            return source;
        }
    }

    public final String name;
    public Predicate<CommandSourceStack> requirement = s -> true;
    public Executor executor;

    private LiteralArgumentBuilder(String name) {
        this.name = name;
    }

    public static LiteralArgumentBuilder literal(String name) {
        return new LiteralArgumentBuilder(name);
    }

    public LiteralArgumentBuilder requires(Predicate<CommandSourceStack> r) {
        requirement = r;
        return this;
    }

    public LiteralArgumentBuilder executes(Executor e) {
        executor = e;
        return this;
    }

    public LiteralArgumentBuilder then(Object child) {
        return this;
    }
}
