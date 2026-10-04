package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.FlowersEntity;
import net.mcreator.boh.init.BohModMobEffects;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class FlowersOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            Vec3 _center = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                if (entityiterator instanceof EntityPlayer && !(entityiterator instanceof EntityLivingBase _livEnt1 && M.hasEffect(_livEnt1, (Potion) BohModMobEffects.THE_WHISLE.get())) && entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                    M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.THE_WHISLE.get(), 1000, 0, false, false));
                }
            }
            if (M.tickCount(entity) % 300 == 0) {
                if (!M.isClientSide(world) && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:flowers_whistle_damage")), SoundSource.HOSTILE, 100.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:flowers_whistle_damage")), SoundSource.HOSTILE, 100.0F, 1.0F, false);
                    }
                }
                if (entity instanceof FlowersEntity _datEntSetL) {
                    M.set(M.getEntityData(_datEntSetL), FlowersEntity.DATA_logic_whistle, true);
                }
                Vec3 _center_r25 = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center_r25, _center_r25).inflate(50.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center_r25))).toList()) {
                    if (entityiterator instanceof EntityPlayer && !(entityiterator instanceof EntityLivingBase _livEnt9 && M.hasEffect(_livEnt9, (Potion) BohModMobEffects.COVER_YOUR_EARS.get())) && entityiterator instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect((Potion) BohModMobEffects.COVER_YOUR_EARS.get(), 180, 0, false, false));
                    }
                }
            }
            if (entity instanceof FlowersEntity _datEntL12 && (Boolean) M.getEntityData(_datEntL12).get(FlowersEntity.DATA_logic_whistle) && entity instanceof FlowersEntity _datEntSetI) {
                M.set(M.getEntityData(_datEntSetI), FlowersEntity.DATA_logic_cooldown_whisle, (entity instanceof FlowersEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(FlowersEntity.DATA_logic_cooldown_whisle) : 0) + 1);
            }
            if ((entity instanceof FlowersEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(FlowersEntity.DATA_logic_cooldown_whisle) : 0) == 156) {
                if (entity instanceof FlowersEntity _datEntSetI) {
                    M.set(M.getEntityData(_datEntSetI), FlowersEntity.DATA_logic_cooldown_whisle, 0);
                }
                if (entity instanceof FlowersEntity _datEntSetL) {
                    M.set(M.getEntityData(_datEntSetL), FlowersEntity.DATA_logic_whistle, false);
                }
                Vec3 _centerx = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_centerx, _centerx).inflate(50.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof EntityPlayer && entityiterator instanceof EntityLivingBase _livEnt19 && M.hasEffect(_livEnt19, (Potion) BohModMobEffects.COVER_YOUR_EARS.get()) && (!(M.getXRot(entityiterator) > 75.0F) || !M.isShiftKeyDown(entityiterator))) {
                        Entity _ent = entityiterator;
                        if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/damage @s 15 boh:whistle_damage");
                        }
                        Entity _ent_r26 = entityiterator;
                        if (!M.isClientSide(M.level(_ent_r26)) && M.getServer(_ent_r26) != null) {
                            M.performPrefixedCommand(M.getCommands(M.getServer(_ent_r26)), new CommandSourceStack(CommandSource.NULL, M.position(_ent_r26), M.getRotationVector(_ent_r26), M.level(_ent_r26) instanceof WorldServer ? (WorldServer) M.level(_ent_r26) : null, 4, M.getString(M.getName(_ent_r26)), M.getDisplayName(_ent_r26), M.getServer(M.level(_ent_r26)), _ent_r26), "/fill -3 78 -6 44 65 41 air destroy");
                        }
                    }
                }
            }
        }
    }
}
