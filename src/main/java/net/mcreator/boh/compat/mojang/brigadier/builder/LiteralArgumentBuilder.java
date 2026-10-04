package net.mcreator.boh.compat.mojang.brigadier.builder;

import java.util.function.Predicate;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;

public class LiteralArgumentBuilder {
    public final String name;
    public Predicate<CommandSourceStack> requirement = s -> true;
    public LiteralArgumentBuilder.Executor executor;

    private LiteralArgumentBuilder(String name) {
        this.name = name;
    }

    public static LiteralArgumentBuilder literal(String name) {
        return new LiteralArgumentBuilder(name);
    }

    public LiteralArgumentBuilder requires(Predicate<CommandSourceStack> r) {
        this.requirement = r;
        return this;
    }

    public LiteralArgumentBuilder executes(LiteralArgumentBuilder.Executor e) {
        this.executor = e;
        return this;
    }

    public LiteralArgumentBuilder then(Object child) {
        return this;
    }

    public static final class CommandArguments {
        private final CommandSourceStack source;

        public CommandArguments(CommandSourceStack source) {
            this.source = source;
        }

        public CommandSourceStack getSource() {
            return this.source;
        }
    }

    @FunctionalInterface
    public interface Executor {
        int run(LiteralArgumentBuilder.CommandArguments var1) throws Exception;
    }
}
