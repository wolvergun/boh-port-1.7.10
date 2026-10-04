package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class KillerKnifeLivingEntityIsHitWithToolProcedure {
    public static void execute(World world, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null && !(entity instanceof EntityLivingBase _livEnt0 && M.isBlocking(_livEnt0))) {
            BohMod.queueServerWork(
                10,
                () -> {
                    M.hurt(
                        entity,
                        M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)),
                        3.0F
                    );
                    if (sourceentity instanceof EntityLivingBase _entity) {
                        M.swing(_entity, InteractionHand.MAIN_HAND, true);
                    }

                    if (!M.isClientSide(M.level(sourceentity)) && M.getServer(sourceentity) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(sourceentity)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(sourceentity),
                                M.getRotationVector(sourceentity),
                                M.level(sourceentity) instanceof WorldServer ? (WorldServer)M.level(sourceentity) : null,
                                4,
                                M.getString(M.getName(sourceentity)),
                                M.getDisplayName(sourceentity),
                                M.getServer(M.level(sourceentity)),
                                sourceentity
                            ),
                            "/particle minecraft:sweep_attack ~ ~1.2 ~"
                        );
                    }

                    if (!M.isClientSide(M.level(sourceentity)) && M.getServer(sourceentity) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(sourceentity)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(sourceentity),
                                M.getRotationVector(sourceentity),
                                M.level(sourceentity) instanceof WorldServer ? (WorldServer)M.level(sourceentity) : null,
                                4,
                                M.getString(M.getName(sourceentity)),
                                M.getDisplayName(sourceentity),
                                M.getServer(M.level(sourceentity)),
                                sourceentity
                            ),
                            "/playsound minecraft:entity.player.attack.strong hostile @a"
                        );
                    }

                    if (Math.random() < 0.6 && !M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                        M.performPrefixedCommand(
                            M.getCommands(M.getServer(entity)),
                            new CommandSourceStack(
                                CommandSource.NULL,
                                M.position(entity),
                                M.getRotationVector(entity),
                                M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                4,
                                M.getString(M.getName(entity)),
                                M.getDisplayName(entity),
                                M.getServer(M.level(entity)),
                                entity
                            ),
                            "effect give @s kurolib:bleeding 0 30"
                        );
                    }
                }
            );
        }
    }
}
