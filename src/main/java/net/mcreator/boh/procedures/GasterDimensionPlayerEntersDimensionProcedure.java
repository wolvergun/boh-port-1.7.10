package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GasterDimensionPlayerEntersDimensionProcedure {
    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (world instanceof WorldServer _serverworld) {
                StructureTemplate template = M.getOrCreate(M.getStructureManager(_serverworld), new ResourceLocation("boh", "gaster"));
                if (template != null) {
                    M.placeInWorld(
                        template,
                        _serverworld,
                        new BlockPos(-3, 64, -6),
                        new BlockPos(-3, 64, -6),
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
        }
    }
}
