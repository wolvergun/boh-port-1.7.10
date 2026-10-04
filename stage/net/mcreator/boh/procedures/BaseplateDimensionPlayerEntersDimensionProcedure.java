package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.mcreator.boh.compat.M;

public class BaseplateDimensionPlayerEntersDimensionProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (world instanceof WorldServer _serverworld) {
                StructureTemplate template = M.getOrCreate(M.getStructureManager(_serverworld), new ResourceLocation("boh", "baseplate"));
                if (template != null) {
                    M.placeInWorld(template, _serverworld, new BlockPos(-3, 64, -6), new BlockPos(-3, 64, -6), M.setIgnoreEntities(M.setMirror(M.setRotation(new StructurePlaceSettings(), Rotation.NONE), Mirror.NONE), false), M.random(_serverworld),3);
                }
            }
            Entity _ent = entity;
            M.teleportTo(_ent, 0.5, 65.0, 0.5);
            if (_ent instanceof EntityPlayerMP _serverPlayer) {
                M.teleport(M.connection(_serverPlayer), 0.5, 65.0, 0.5, M.getYRot(_ent), M.getXRot(_ent));
            }
            Entity _ent_r5 = entity;
            M.setYRot(_ent_r5, -90.0F);
            M.setXRot(_ent_r5, 0.0F);
            M.setYBodyRot(_ent_r5, M.getYRot(_ent_r5));
            M.setYHeadRot(_ent_r5, M.getYRot(_ent_r5));
            M.set_yRotO(_ent_r5, M.getYRot(_ent_r5));
            M.set_xRotO(_ent_r5, M.getXRot(_ent_r5));
            if (_ent_r5 instanceof EntityLivingBase _entity) {
                M.set_yBodyRotO(_entity, M.getYRot(_entity));
                M.set_yHeadRotO(_entity, M.getYRot(_entity));
            }
        }
    }
}
