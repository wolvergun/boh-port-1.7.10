package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class GasterDimensionPlayerEntersDimensionProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (world instanceof ServerLevel _serverworld) {
            StructureTemplate template = _serverworld.getStructureManager().getOrCreate(new ResourceLocation("boh", "gaster"));
            if (template != null) {
               template.placeInWorld(
                  _serverworld,
                  new BlockPos(-3, 64, -6),
                  new BlockPos(-3, 64, -6),
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
      }
   }
}
