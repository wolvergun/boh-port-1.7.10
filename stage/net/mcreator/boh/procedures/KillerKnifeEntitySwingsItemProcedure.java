package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class KillerKnifeEntitySwingsItemProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(10, () -> {
                if (entity instanceof EntityLivingBase _entity) {
                    M.swing(_entity, InteractionHand.MAIN_HAND, true);
                }
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:sweep_attack ~ ~1.2 ~");
                }
                Entity _ent_r44 = entity;
                if (!M.isClientSide(M.level(_ent_r44)) && M.getServer(_ent_r44) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r44)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r44), M.getRotationVector(_ent_r44), M.level(_ent_r44) instanceof WorldServer ? (WorldServer) M.level(_ent_r44) : null, 4, M.getString(M.getName(_ent_r44)), M.getDisplayName(_ent_r44), M.getServer(M.level(_ent_r44)), _ent_r44), "/playsound minecraft:entity.player.attack.strong hostile @a");
                }
            });
        }
    }
}
