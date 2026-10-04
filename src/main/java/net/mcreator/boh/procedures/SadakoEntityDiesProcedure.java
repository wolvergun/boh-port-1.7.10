package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SadakoEntityDiesProcedure {
    public static void execute(World world, double x, double y, double z) {
        if (world instanceof WorldServer _level) {
            M.performPrefixedCommand(
                M.getCommands(M.getServer(_level)),
                M.withSuppressedOutput(
                    new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)
                ),
                "/effect clear @a boh:sadako_effect "
            );
        }
    }
}
