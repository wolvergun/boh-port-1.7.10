package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class GasterOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.005 && world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_laugh")), SoundSource.PLAYERS, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_laugh")), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                }
            }
            Vec3 _center = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(10.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("\ud83d\udc4e︎✌︎☼︎\ud83d\ude10︎\ud83d\udcea︎ \ud83d\udc4e︎✌︎☼︎\ud83d\ude10︎\ud83d\udcea︎ ✡︎☜︎❄︎ \ud83d\udc4e︎✌︎☼︎\ud83d\ude10︎☜︎☼︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("\ud83d\udc4d︎\ud83d\udd46︎\ud83d\udd46︎\ud83d\udd46︎\ud83d\udd46︎\ud83d\udd46︎\ud83d\udd46︎\ud83d\udc4c︎☜︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("✋︎ \ud83d\udca7︎☜︎☠︎\ud83d\udca7︎☜︎ ☠︎⚐︎ \ud83d\udc4e︎☜︎❄︎☜︎☼︎\ud83d\udca3︎✋︎☠︎✌︎❄︎✋︎⚐︎☠︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("\ud83d\udd48︎☟︎☜︎☼︎☜︎ ✌︎\ud83d\udca3︎ ✋︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("❄︎☟︎✋︎\ud83d\udca7︎ ✋︎\ud83d\udca7︎ ☹︎✋︎\ud83d\ude10︎☜︎ ❄︎☟︎✌︎❄︎ ❄︎✋︎\ud83d\udca3︎☜︎ ✋︎ \ud83d\udd48︎✌︎\ud83d\udca7︎ ✋︎☠︎ \ud83d\udc4c︎✋︎☠︎\ud83d\udc4e︎✋︎☠︎☝︎ ⚐︎☞︎ ✋︎\ud83d\udca7︎✌︎✌︎\ud83d\udc4d︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("✋︎ ✌︎\ud83d\udca3︎ ✌︎ ☝︎✌︎☝︎ \ud83d\udc4d︎☟︎✌︎☼︎✌︎\ud83d\udc4d︎❄︎☜︎☼︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("☞︎\ud83d\udd46︎\ud83d\udc4d︎\ud83d\ude10︎ ⚐︎☞︎☞︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("\ud83d\udc4d︎☼︎☜︎☜︎\ud83c\udff1︎☜︎☼︎ ✌︎\ud83d\udd48︎\ud83d\udd48︎ \ud83d\udca3︎✌︎☠︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("\ud83d\udd48︎☟︎☜︎☼︎☜︎ ✋︎\ud83d\udca7︎ \ud83d\udca7︎✌︎☠︎\ud83d\udca7︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("✋︎ \ud83d\udca3︎✋︎\ud83d\udca7︎\ud83d\udca7︎ \ud83d\udca3︎✡︎ \ud83d\udd48︎✋︎☞︎☜︎ ❄︎✌︎✋︎☹︎\ud83d\udca7︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("☝︎\ud83d\udd46︎\ud83d\udca7︎\ud83d\udcea︎ ❄︎✌︎\ud83d\udc4d︎\ud83d\udcc1︎\ud83d\udc4e︎✋︎☹︎☜︎\ud83d\udcea︎ \ud83d\udca7︎✋︎☼︎\ud83c\udff1︎✌︎☠︎\ud83d\udc4d︎✌︎\ud83d\ude10︎☜︎\ud83d\udca7︎"), true);
                    }
                } else if (Math.random() < 0.1) {
                    if (entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                        M.displayClientMessage(_player, Component.literal("\ud83d\uddcf︎\ud83d\uddb2︎ \ud83d\udc4c︎\ud83d\udd46︎☼︎✋︎☜︎\ud83d\udc4e︎ \ud83d\udcc1︎ ☞︎⚐︎\ud83d\udd46︎☠︎\ud83d\udc4e︎"), true);
                    }
                } else if (Math.random() < 0.1 && entityiterator instanceof EntityPlayer _player && !M.isClientSide(M.level(_player))) {
                    M.displayClientMessage(_player, Component.literal("☠︎⚐︎✋︎\ud83d\udca7︎☜︎✡︎ \ud83d\udc4c︎✋︎❄︎\ud83d\udc4d︎☟︎"), true);
                }
            }
            M.putDouble(M.getPersistentData(entity), "ambience", M.getDouble(M.getPersistentData(entity), "ambience") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "ambience") >= 1580.0) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_ambience")), SoundSource.AMBIENT, 0.5F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_ambience")), SoundSource.AMBIENT, 0.5F, 1.0F, false);
                    }
                }
                M.putDouble(M.getPersistentData(entity), "ambience", 0.0);
            }
        }
    }
}
