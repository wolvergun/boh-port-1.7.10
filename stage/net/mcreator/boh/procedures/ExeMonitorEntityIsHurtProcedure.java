package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.entity.SonicExeEntity;
import net.mcreator.boh.entity.TailsEntity;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.particles.ParticleTypes;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class ExeMonitorEntityIsHurtProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (!M.isClientSide(M.level(entity))) {
                M.discard(entity);
            }
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:monitor_break")), SoundSource.AMBIENT, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:monitor_break")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                }
            }
            if (world instanceof WorldServer _level) {
                M.sendParticles(_level, ParticleTypes.EXPLOSION, M.getX(entity), M.getY(entity) + 0.5, M.getZ(entity), 1, 0.0, 0.0, 0.0, 0.0);
            }
            Vec3 _center = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(500.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                if (entityiterator instanceof SonicExeEntity && !M.isClientSide(M.level(entityiterator))) {
                    M.discard(entityiterator);
                }
            }
            Vec3 _center_r22 = new Vec3(x, y, z);
            for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center_r22, _center_r22).inflate(500.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center_r22))).toList()) {
                if (entityiterator instanceof TailsEntity && !M.isClientSide(M.level(entityiterator))) {
                    M.discard(entityiterator);
                }
            }
            if (!M.isEmpty(M.getEntitiesOfClass(world, SonicExeEntity.class, AABB.ofSize(new Vec3(x, y, z), 1000.0, 1000.0, 1000.0), e -> true)) && world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_despawn")), SoundSource.AMBIENT, 10000.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:exe_despawn")), SoundSource.AMBIENT, 10000.0F, 1.0F, false);
                }
            }
            if (world instanceof WorldServer _level) {
                EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.EXE_BOOTS_BOOTS.get()));
                M.setPickUpDelay(entityToSpawn, 10);
                M.addFreshEntity(_level, entityToSpawn);
            }
            if (world instanceof WorldServer _level) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "/effect clear @e boh:hide_and_seek");
            }
            if (world instanceof WorldServer _level) {
                M.performPrefixedCommand(M.getCommands(M.getServer(_level)), M.withSuppressedOutput(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), M.getServer(_level), null)), "/stopsound @a music boh:exe_chase");
            }
        }
    }
}
