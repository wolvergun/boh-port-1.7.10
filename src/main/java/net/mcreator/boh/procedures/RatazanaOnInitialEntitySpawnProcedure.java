package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.entity.RatazanaEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RatazanaOnInitialEntitySpawnProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof RatazanaEntity) {
                ((RatazanaEntity)entity).setAnimation("spawn");
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
            }

            if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                    "/execute as @e[type=boh:rat,limit=1,distance=0..2] run spreadplayers ~ ~ 3 3 false @e[type=boh:ratazana,limit=1,distance=0..2]"
                );
            }

            for (int index0 = 0; index0 < 10; index0++) {
                BohMod.queueServerWork(
                    2,
                    () -> {
                        if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
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
                                "/particle minecraft:block minecraft:dirt ~ ~ ~ .1 0 .1 0 20 "
                            );
                        }

                        if (world instanceof World) {
                            if (!M.isClientSide(world)) {
                                M.playSound(
                                    world,
                                    null,
                                    BlockPos.containing(x, y, z),
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.break")),
                                    SoundSource.HOSTILE,
                                    1.0F,
                                    1.0F
                                );
                            } else {
                                M.playLocalSound(
                                    world,
                                    x,
                                    y,
                                    z,
                                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.grass.break")),
                                    SoundSource.HOSTILE,
                                    1.0F,
                                    1.0F,
                                    false
                                );
                            }
                        }
                    }
                );
            }
        }
    }
}
