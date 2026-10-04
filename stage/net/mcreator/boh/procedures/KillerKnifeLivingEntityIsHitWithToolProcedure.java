package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.M;

public class KillerKnifeLivingEntityIsHitWithToolProcedure {

    public static void execute(World world, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (!(entity instanceof EntityLivingBase _livEnt0 && M.isBlocking(_livEnt0))) {
                BohMod.queueServerWork(10, () -> {
                    M.hurt(entity, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)), 3.0F);
                    if (sourceentity instanceof EntityLivingBase _entity) {
                        M.swing(_entity, InteractionHand.MAIN_HAND, true);
                    }
                    Entity _ent = sourceentity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle minecraft:sweep_attack ~ ~1.2 ~");
                    }
                    Entity _ent_r45 = sourceentity;
                    if (!M.isClientSide(M.level(_ent_r45)) && M.getServer(_ent_r45) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r45)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r45), M.getRotationVector(_ent_r45), M.level(_ent_r45) instanceof WorldServer ? (WorldServer) M.level(_ent_r45) : null, 4, M.getString(M.getName(_ent_r45)), M.getDisplayName(_ent_r45), M.getServer(M.level(_ent_r45)), _ent_r45), "/playsound minecraft:entity.player.attack.strong hostile @a");
                    }
                    if (Math.random() < 0.6) {
                        _ent_r45 = entity;
                        if (!M.isClientSide(M.level(_ent_r45)) && M.getServer(_ent_r45) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r45)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r45), M.getRotationVector(_ent_r45), M.level(_ent_r45) instanceof WorldServer ? (WorldServer) M.level(_ent_r45) : null, 4, M.getString(M.getName(_ent_r45)), M.getDisplayName(_ent_r45), M.getServer(M.level(_ent_r45)), _ent_r45), "effect give @s kurolib:bleeding 0 30");
                        }
                    }
                });
            }
        }
    }
}
