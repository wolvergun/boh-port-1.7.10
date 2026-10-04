package net.mcreator.boh.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundGameEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundLevelEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.M;

public class GasterDoorOnBlockRightClickedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(2, () -> {
                M.putDouble(M.getPersistentData(entity), "xposp", M.getX(entity));
                M.putDouble(M.getPersistentData(entity), "yposp", M.getY(entity));
                M.putDouble(M.getPersistentData(entity), "zposp", M.getZ(entity));
                BohMod.queueServerWork(2, () -> {
                    if (entity instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))) {
                        ResourceKey<World> destinationType = net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:gaster_dimension"));
                        if (M.dimension(M.level(_player)) == destinationType) {
                            return;
                        }
                        WorldServer nextLevel = M.getLevel(M.server(_player), destinationType);
                        if (nextLevel != null) {
                            M.connection(_player).send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0.0F));
                            M.teleportTo(_player, nextLevel, M.getX(_player), M.getY(_player), M.getZ(_player), M.getYRot(_player), M.getXRot(_player));
                            M.connection(_player).send(new ClientboundPlayerAbilitiesPacket(M.getAbilities(_player)));
                            for (PotionEffect _effectinstance : M.getActiveEffects(_player)) {
                                M.connection(_player).send(new ClientboundUpdateMobEffectPacket(M.getId(_player), _effectinstance));
                            }
                            M.connection(_player).send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                        }
                    }
                });
            });
            BlockPos _bp = BlockPos.containing(x, y, z);
            BlockState _bs = M.defaultBlockState(Blocks.AIR);
            BlockState _bso = M.getBlockState(world, _bp);
            UnmodifiableIterator var11 = M.getValues(_bso).entrySet().iterator();
            while (var11.hasNext()) {
                Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var11.next();
                Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs)), M.getName(entry.getKey()));
                if (_property != null && _bs.getValue(_property) != null) {
                    try {
                        _bs = (BlockState) M.setValue(_bs, _property, entry.getValue());
                    } catch (Exception var16) {
                    }
                }
            }
            M.setBlock(world, _bp, _bs, 3);
            BlockPos _bp_r28 = BlockPos.containing(x, y + 1.0, z);
            BlockState _bs_r29 = M.defaultBlockState(Blocks.AIR);
            BlockState _bso_r30 = M.getBlockState(world, _bp_r28);
            var11 = M.getValues(_bso_r30).entrySet().iterator();
            while (var11.hasNext()) {
                Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>) var11.next();
                Property _property = M.getProperty(M.getStateDefinition(M.getBlock(_bs_r29)), M.getName(entry.getKey()));
                if (_property != null && _bs_r29.getValue(_property) != null) {
                    try {
                        _bs_r29 = (BlockState) M.setValue(_bs_r29, _property, entry.getValue());
                    } catch (Exception var15) {
                    }
                }
            }
            M.setBlock(world, _bp_r28, _bs_r29, 3);
        }
    }
}
