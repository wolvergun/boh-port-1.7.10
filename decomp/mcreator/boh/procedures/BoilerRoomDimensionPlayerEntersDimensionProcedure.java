package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.FreddyKruegerEntity;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class BoilerRoomDimensionPlayerEntersDimensionProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 900, 1, true, true));
         }

         if (world instanceof ServerLevel _serverworld) {
            StructureTemplate template = _serverworld.getStructureManager().getOrCreate(new ResourceLocation("boh", "boiler_room"));
            if (template != null) {
               template.placeInWorld(
                  _serverworld,
                  new BlockPos(-18, 64, 0),
                  new BlockPos(-18, 64, 0),
                  new StructurePlaceSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false),
                  _serverworld.random,
                  3
               );
            }
         }

         Entity _ent = entity;
         _ent.teleportTo(0.5, 65.0, 0.5);
         if (_ent instanceof ServerPlayer _serverPlayer) {
            _serverPlayer.connection.teleport(0.5, 65.0, 0.5, _ent.getYRot(), _ent.getXRot());
         }

         _ent = entity;
         _ent.setYRot(-90.0F);
         _ent.setXRot(0.0F);
         _ent.setYBodyRot(_ent.getYRot());
         _ent.setYHeadRot(_ent.getYRot());
         _ent.yRotO = _ent.getYRot();
         _ent.xRotO = _ent.getXRot();
         if (_ent instanceof LivingEntity _entity) {
            _entity.yBodyRotO = _entity.getYRot();
            _entity.yHeadRotO = _entity.getYRot();
         }

         if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  new BlockPos(0, 65, 2),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:freddy_lullaby")),
                  SoundSource.AMBIENT,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  0.0,
                  65.0,
                  2.0,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:freddy_lullaby")),
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
               if (world.getEntitiesOfClass(FreddyKruegerEntity.class, AABB.ofSize(new Vec3(x, y, z), 100.0, 100.0, 100.0), e -> true).isEmpty()
                  && world instanceof ServerLevel _levelx) {
                  Entity entityToSpawn = ((EntityType)BohModEntities.FREDDY_KRUEGER.get())
                     .spawn(_levelx, new BlockPos(0, 65, 2), MobSpawnType.MOB_SUMMONED);
                  if (entityToSpawn != null) {
                  }
               }
            }
         );
         BohMod.queueServerWork(
            2,
            () -> {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(500.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator instanceof FreddyKruegerEntity && !entityiterator.level().isClientSide()) {
                     entityiterator.discard();
                  }
               }
            }
         );
      }
   }
}
