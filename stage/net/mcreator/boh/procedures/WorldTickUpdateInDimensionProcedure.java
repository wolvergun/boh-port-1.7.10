package net.mcreator.boh.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.FlowersEntity;
import net.mcreator.boh.entity.NPC000Entity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModItems;
import net.mcreator.boh.network.BohModVariables;
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
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.Entity;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class WorldTickUpdateInDimensionProcedure {

    @SubscribeEvent
    public void onWorldTick(WorldTickEvent event) {
        if (event.phase == Phase.END) {
            execute(event, M.level(event));
        }
    }

    public static void execute(World world) {
        execute(null, world);
    }

    private static void execute(@Nullable Event event, World world) {
        if ((world instanceof World _lvl ? M.dimension(_lvl) : (world instanceof World _wgl ? M.dimension(M.getLevel(_wgl)) : M.OVERWORLD)) == net.mcreator.boh.compat.world.Dimensions.dimensionKey( new ResourceLocation("boh:baseplate_dimension"))) {
            if (!BohModVariables.MapVariables.get(world).whistle_logic) {
                if (M.isEmpty(M.getEntitiesOfClass(world, FlowersEntity.class, AABB.ofSize(new Vec3(20.0, 65.0, 17.0), 100.0, 100.0, 100.0), e -> true)) && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(20.0, 65.0, 17.0), 100.0, 100.0, 100.0), e -> true))) {
                    BohModVariables.MapVariables.get(world).whisle_occurance_timer++;
                    BohModVariables.MapVariables.get(world).syncData(world);
                }
                if (BohModVariables.MapVariables.get(world).whisle_occurance_timer == 1.0 && !M.isClientSide(world) && world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, new BlockPos(20, 65, 17), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:flowers_fight_ost")), SoundSource.AMBIENT, 100.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, 20.0, 65.0, 17.0, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:flowers_fight_ost")), SoundSource.AMBIENT, 100.0F, 1.0F, false);
                    }
                }
                if (BohModVariables.MapVariables.get(world).whisle_occurance_timer == 300.0) {
                    BohModVariables.MapVariables.get(world).whistle_logic = true;
                    BohModVariables.MapVariables.get(world).syncData(world);
                    BohModVariables.MapVariables.get(world).whisle_occurance_timer = 0.0;
                    BohModVariables.MapVariables.get(world).syncData(world);
                    if (world instanceof WorldServer _level) {
                        Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.FLOWERS.get()), _level, new BlockPos(20, 65, 17), MobSpawnType.MOB_SUMMONED);
                        if (entityToSpawn != null) {
                        }
                    }
                    BohMod.queueServerWork(300, () -> {
                        if (world instanceof WorldServer _level) {
                            Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.NPC_000.get()), _level, new BlockPos(20, 65, 17), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                            }
                        }
                        if (world instanceof WorldServer _level) {
                            Entity entityToSpawn = M.spawn(((EntityType) BohModEntities.NPC_000.get()), _level, new BlockPos(20, 65, 17), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                            }
                        }
                    });
                }
            }
            if (BohModVariables.MapVariables.get(world).whistle_logic) {
                BohModVariables.MapVariables.get(world).whistle_global_timer++;
                BohModVariables.MapVariables.get(world).syncData(world);
            }
            if (BohModVariables.MapVariables.get(world).whistle_global_timer == 2500.0) {
                BohModVariables.MapVariables.get(world).whistle_global_timer = 0.0;
                BohModVariables.MapVariables.get(world).syncData(world);
                BohModVariables.MapVariables.get(world).whistle_logic = false;
                BohModVariables.MapVariables.get(world).syncData(world);
                if (!M.isEmpty(M.getEntitiesOfClass(world, FlowersEntity.class, AABB.ofSize(new Vec3(20.0, 65.0, 17.0), 100.0, 100.0, 100.0), e -> true)) && !M.isEmpty(M.getEntitiesOfClass(world, EntityPlayer.class, AABB.ofSize(new Vec3(20.0, 65.0, 17.0), 100.0, 100.0, 100.0), e -> true))) {
                    Vec3 _center = new Vec3(20.0, 65.0, 17.0);
                    for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                        if ((entityiterator instanceof FlowersEntity || entityiterator instanceof NPC000Entity) && !M.isClientSide(M.level(entityiterator))) {
                            M.discard(entityiterator);
                        }
                    }
                }
                Vec3 _center = new Vec3(20.0, 65.0, 17.0);
                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(50.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center))).toList()) {
                    if (entityiterator instanceof EntityPlayer) {
                        if (entityiterator instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player))) {
                            ResourceKey<World> destinationType = M.OVERWORLD;
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
                        if (entityiterator instanceof EntityPlayer _player) {
                            ItemStack _setstack = M.copy(M.new_ItemStack(BohModItems.WHISPERING_THORNS_HELMET.get()));
                            M.setCount(_setstack, 1);
                            ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
                        }
                        Entity _ent = entityiterator;
                        M.teleportTo(_ent, entityiterator instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getX(M.getRespawnPosition(_player)) : M.getXSpawn(M.getLevelData(M.level(_player)))) : 0.0, entityiterator instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getY(M.getRespawnPosition(_player)) : M.getYSpawn(M.getLevelData(M.level(_player)))) : 0.0, entityiterator instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getZ(M.getRespawnPosition(_player)) : M.getZSpawn(M.getLevelData(M.level(_player)))) : 0.0);
                        if (_ent instanceof EntityPlayerMP _serverPlayer) {
                            M.teleport(M.connection(_serverPlayer), entityiterator instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getX(M.getRespawnPosition(_player)) : M.getXSpawn(M.getLevelData(M.level(_player)))) : 0.0, entityiterator instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getY(M.getRespawnPosition(_player)) : M.getYSpawn(M.getLevelData(M.level(_player)))) : 0.0, entityiterator instanceof EntityPlayerMP _player && !M.isClientSide(M.level(_player)) ? (M.getRespawnDimension(_player).equals(M.dimension(M.level(_player))) && M.getRespawnPosition(_player) != null ? M.getZ(M.getRespawnPosition(_player)) : M.getZSpawn(M.getLevelData(M.level(_player)))) : 0.0, M.getYRot(_ent), M.getXRot(_ent));
                        }
                    }
                }
            }
        }
    }
}
