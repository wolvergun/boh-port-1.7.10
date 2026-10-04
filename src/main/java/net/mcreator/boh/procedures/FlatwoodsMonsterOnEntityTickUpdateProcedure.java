package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.FlatwoodsMonsterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class FlatwoodsMonsterOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.01 && entity instanceof EntityLiving _mob && M.isAggressive(_mob)) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                            SoundSource.HOSTILE,
                            2.0F,
                            0.5F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                            SoundSource.HOSTILE,
                            2.0F,
                            0.5F,
                            false
                        );
                    }
                }

                if (entity instanceof FlatwoodsMonsterEntity) {
                    ((FlatwoodsMonsterEntity)entity).setAnimation("buff");
                }

                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(5.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (!(entityiterator instanceof FlatwoodsMonsterEntity) && !(entityiterator instanceof EntityPlayer)) {
                        if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.GLOWING, 60, 1, false, false));
                        }

                        if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.DAMAGE_BOOST, 60, 1, false, false));
                        }

                        if (entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                            M.addEffect(_entity, M.new_PotionEffect(MobEffects.REGENERATION, 60, 1, false, false));
                        }
                    }
                }
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
                    "/effect give @a[distance=2..13] kurolib:radiation 30 0 true"
                );
            }

            if (!M.getBoolean(M.getPersistentData(entity), "lines_michael")
                && (entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer) {
                M.putBoolean(M.getPersistentData(entity), "lines_michael", true);
            }

            if (!M.getBoolean(M.getPersistentData(entity), "throlgular") && M.getBoolean(M.getPersistentData(entity), "lines_michael")) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.ambient")),
                            SoundSource.HOSTILE,
                            2.0F,
                            2.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.ambient")),
                            SoundSource.HOSTILE,
                            2.0F,
                            2.0F,
                            false
                        );
                    }
                }

                M.putBoolean(M.getPersistentData(entity), "throlgular", true);
            }

            if (!((entity instanceof EntityLiving _mobEnt ? M.getTarget(_mobEnt) : null) instanceof EntityPlayer)) {
                M.putBoolean(M.getPersistentData(entity), "lines_michael", false);
                M.putBoolean(M.getPersistentData(entity), "throlgular", false);
            }
        }
    }
}
