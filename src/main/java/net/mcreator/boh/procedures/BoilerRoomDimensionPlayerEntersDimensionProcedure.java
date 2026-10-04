package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.FreddyKruegerEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BoilerRoomDimensionPlayerEntersDimensionProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 900, 1, true, true));
            }

            if (world instanceof WorldServer _serverworld) {
                StructureTemplate template = M.getOrCreate(M.getStructureManager(_serverworld), new ResourceLocation("boh", "boiler_room"));
                if (template != null) {
                    M.placeInWorld(
                        template,
                        _serverworld,
                        new BlockPos(-18, 64, 0),
                        new BlockPos(-18, 64, 0),
                        M.setIgnoreEntities(M.setMirror(M.setRotation(new StructurePlaceSettings(), Rotation.NONE), Mirror.NONE), false),
                        M.random(_serverworld),
                        3
                    );
                }
            }

            M.teleportTo(entity, 0.5, 65.0, 0.5);
            if (entity instanceof EntityPlayerMP _serverPlayer) {
                M.teleport(M.connection(_serverPlayer), 0.5, 65.0, 0.5, M.getYRot(entity), M.getXRot(entity));
            }

            M.setYRot(entity, -90.0F);
            M.setXRot(entity, 0.0F);
            M.setYBodyRot(entity, M.getYRot(entity));
            M.setYHeadRot(entity, M.getYRot(entity));
            M.set_yRotO(entity, M.getYRot(entity));
            M.set_xRotO(entity, M.getXRot(entity));
            if (entity instanceof EntityLivingBase _entity) {
                M.set_yBodyRotO(_entity, M.getYRot(_entity));
                M.set_yHeadRotO(_entity, M.getYRot(_entity));
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        new BlockPos(0, 65, 2),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:freddy_lullaby")),
                        SoundSource.AMBIENT,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        0.0,
                        65.0,
                        2.0,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:freddy_lullaby")),
                        SoundSource.AMBIENT,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            BohMod.queueServerWork(
                500,
                () -> {
                    if (M.isEmpty(M.getEntitiesOfClass(world, FreddyKruegerEntity.class, AABB.ofSize(new Vec3(x, y, z), 100.0, 100.0, 100.0), e -> true))
                        && world instanceof WorldServer _levelx) {
                        Entity entityToSpawn = M.spawn(BohModEntities.FREDDY_KRUEGER.get(), _levelx, new BlockPos(0, 65, 2), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                    }
                }
            );
            BohMod.queueServerWork(
                2,
                () -> {
                    Vec3 _center = new Vec3(x, y, z);

                    for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(500.0), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                        .toList()) {
                        if (entityiterator instanceof FreddyKruegerEntity && !M.isClientSide(M.level(entityiterator))) {
                            M.discard(entityiterator);
                        }
                    }
                }
            );
        }
    }
}
