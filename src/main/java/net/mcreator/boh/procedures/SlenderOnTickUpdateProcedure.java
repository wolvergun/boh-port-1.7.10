package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SlenderManEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SlenderOnTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            double player_count = 0.0;
            M.putDouble(M.getPersistentData(entity), "ambinceslender", M.getDouble(M.getPersistentData(entity), "ambinceslender") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "ambinceslender") == 150.0) {
                if (Math.random() < 0.55) {
                    if (world instanceof World) {
                        if (!M.isClientSide(world)) {
                            M.playSound(
                                world,
                                null,
                                BlockPos.containing(x, y, z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:slender_ambient")),
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
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:slender_ambient")),
                                SoundSource.HOSTILE,
                                1.0F,
                                1.0F,
                                false
                            );
                        }
                    }

                    if (Math.random() < 0.3) {
                        Vec3 _center = new Vec3(x, y, z);

                        for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true)
                            .stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                            .toList()) {
                            if (entityiterator instanceof EntityPlayer && !M.isClientSide(M.level(entityiterator)) && M.getServer(entityiterator) != null) {
                                M.performPrefixedCommand(
                                    M.getCommands(M.getServer(entityiterator)),
                                    new CommandSourceStack(
                                        CommandSource.NULL,
                                        M.position(entityiterator),
                                        M.getRotationVector(entityiterator),
                                        M.level(entityiterator) instanceof WorldServer ? (WorldServer)M.level(entityiterator) : null,
                                        4,
                                        M.getString(M.getName(entityiterator)),
                                        M.getDisplayName(entityiterator),
                                        M.getServer(M.level(entityiterator)),
                                        entityiterator
                                    ),
                                    "execute as @s spreadplayers ~ ~ 10 10 false @e[type=boh:slender_man,limit=1,sort=nearest]"
                                );
                            }
                        }

                        if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                        }

                        if (entity instanceof SlenderManEntity) {
                            ((SlenderManEntity)entity).setAnimation("teleport_out");
                        }

                        BohMod.queueServerWork(5, () -> {
                            if (entity instanceof SlenderManEntity) {
                                ((SlenderManEntity)entity).setAnimation("teleport_in");
                            }
                        });
                    }
                }

                M.putDouble(M.getPersistentData(entity), "ambinceslender", 0.0);
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiteratorx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(500.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                .toList()) {
                if (entityiteratorx instanceof EntityLivingBase _livEnt12
                    && M.hasEffect(_livEnt12, BohModMobEffects.ENGAGED.get())
                    && entityiteratorx instanceof EntityPlayer) {
                    player_count++;
                }
            }

            Vec3 _center_r57 = new Vec3(x, y, z);

            for (Entity entityiteratorxx : M.getEntitiesOfClass(world, Entity.class, new AABB(_center_r57, _center_r57).inflate(500.0), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center_r57)))
                .toList()) {
                if ((
                            entityiteratorxx instanceof EntityLivingBase _livEnt && M.hasEffect(_livEnt, BohModMobEffects.ENGAGED.get())
                                ? M.getAmplifier(M.getEffect(_livEnt, BohModMobEffects.ENGAGED.get()))
                                : 0
                        )
                        < 3
                    && entityiteratorxx instanceof EntityPlayer
                    && entity instanceof EntityLivingBase _entity
                    && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 20, 254, false, false));
                }
            }

            if (player_count == 0.0 && !M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }
        }
    }
}
