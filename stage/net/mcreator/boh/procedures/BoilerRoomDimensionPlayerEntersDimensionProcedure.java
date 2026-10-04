package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.FreddyKruegerEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.minecraft.entity.EntityLivingBase;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class BoilerRoomDimensionPlayerEntersDimensionProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 900, 1, true, true));
            }
            if (world instanceof WorldServer _serverworld) {
                StructureTemplate template = M.getOrCreate(M.getStructureManager(_serverworld), new ResourceLocation("boh", "boiler_room"));
                if (template != null) {
                    M.placeInWorld(template, _serverworld, new BlockPos(-18, 64, 0), new BlockPos(-18, 64, 0), M.setIgnoreEntities(M.setMirror(M.setRotation(new StructurePlaceSettings(), Rotation.NONE), Mirror.NONE), false), M.random(_serverworld),3);
                }
            }
            Entity _ent = entity;
            M.teleportTo(_ent, 0.5, 65.0, 0.5);
            if (_ent instanceof EntityPlayerMP _serverPlayer) {
                M.teleport(M.connection(_serverPlayer), 0.5, 65.0, 0.5, M.getYRot(_ent), M.getXRot(_ent));
            }
            Entity _ent_r11 = entity;
            M.setYRot(_ent_r11, -90.0F);
            M.setXRot(_ent_r11, 0.0F);
            M.setYBodyRot(_ent_r11, M.getYRot(_ent_r11));
            M.setYHeadRot(_ent_r11, M.getYRot(_ent_r11));
            M.set_yRotO(_ent_r11, M.getYRot(_ent_r11));
            M.set_xRotO(_ent_r11, M.getXRot(_ent_r11));
            if (_ent_r11 instanceof EntityLivingBase _entity) {
                M.set_yBodyRotO(_entity, M.getYRot(_entity));
                M.set_yHeadRotO(_entity, M.getYRot(_entity));
            }
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, new BlockPos(0, 65, 2), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:freddy_lullaby")), SoundSource.AMBIENT, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, 0.0, 65.0, 2.0, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:freddy_lullaby")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                }
            }
            BohMod.queueServerWork(500, () -> {
                if (M.isEmpty(M.getEntitiesOfClass(world, FreddyKruegerEntity.class, AABB.ofSize(new Vec3(x, y, z), 100.0, 100.0, 100.0), e -> true)) && world instanceof WorldServer _levelx) {
                    Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.FREDDY_KRUEGER.get()), _levelx, new BlockPos(0, 65, 2), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                    }
                }
            });
            BohMod.queueServerWork(2, () -> {
                Vec3 _center = new Vec3(x, y, z);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(500.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof FreddyKruegerEntity && !M.isClientSide(M.level(entityiterator))) {
                        M.discard(entityiterator);
                    }
                }
            });
        }
    }
}
