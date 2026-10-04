package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.entity.JeffTheKillerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class JeffthekillerAttackProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.level(M.getEntity(event)), M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(World world, Entity entity, Entity sourceentity) {
        execute(null, world, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null && sourceentity instanceof JeffTheKillerEntity) {
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

            BohMod.queueServerWork(
                10,
                () -> {
                    if (!(entity instanceof EntityLivingBase _livEnt3 && M.isBlocking(_livEnt3))) {
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

                        M.hurt(
                            entity,
                            M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.GENERIC)),
                            6.0F
                        );
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
                                "effect give @s kurolib:bleeding 1 10"
                            );
                        }
                    }
                }
            );
        }
    }
}
