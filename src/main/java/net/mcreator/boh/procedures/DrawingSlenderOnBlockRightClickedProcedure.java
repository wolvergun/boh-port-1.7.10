package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.items.ItemHandlerHelper;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.advancements.Advancement;
import net.mcreator.boh.compat.mc.advancements.AdvancementProgress;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.SlenderManEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class DrawingSlenderOnBlockRightClickedProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:page_pickup")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:page_pickup")), SoundSource.NEUTRAL, 1.0F, 1.0F, false
                    );
                }
            }

            if (entity instanceof EntityPlayerMP _player) {
                Advancement _adv = M.getAdvancement(M.getAdvancements(M.server(_player)), new ResourceLocation("boh:grimm_start"));
                AdvancementProgress _ap = M.getOrStartProgress(M.getAdvancements(_player), _adv);
                if (!M.isDone(_ap)) {
                    for (String criteria : M.getRemainingCriteria(_ap)) {
                        M.award(M.getAdvancements(_player), _adv, criteria);
                    }
                }
            }

            M.destroyBlock(world, BlockPos.containing(x, y, z), false);
            if (entity instanceof EntityPlayer _playerx) {
                ItemStack _setstack = M.copy(M.new_ItemStack(Items.PAPER));
                M.setCount(_setstack, 1);
                ItemHandlerHelper.giveItemToPlayer(_playerx, _setstack);
            }

            if (M.isEmpty(M.getEntitiesOfClass(world, SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 600.0, 600.0, 600.0), e -> true))) {
                if (world instanceof WorldServer _level) {
                    Entity entityToSpawn = M.spawn(BohModEntities.SLENDER_MAN.get(), _level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        M.setDeltaMovement(entityToSpawn, 0.0, 0.0, 0.0);
                    }
                }

                BohMod.queueServerWork(
                    10,
                    () -> {
                        if (!M.isClientSide(M.level(entity)) && M.getServer(entity) != null) {
                            M.performPrefixedCommand(
                                M.getCommands(M.getServer(entity)),
                                new CommandSourceStack(
                                    CommandSource.NULL,
                                    M.position(entity),
                                    M.getRotationVector(entity),
                                    M.level(entity) instanceof WorldServer ? (WorldServer)M.level(entity) : null,
                                    4,
                                    M.getString(M.getName(entity)),
                                    M.getDisplayName(entity),
                                    M.getServer(M.level(entity)),
                                    entity
                                ),
                                "/execute at @p rotated ~ 1 run spreadplayers ~ ~ 20 30 false @e[type=boh:slender_man,limit=1]"
                            );
                        }
                    }
                );
            }

            if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(
                    _entity,
                    M.new_PotionEffect(
                        BohModMobEffects.ENGAGED.get(),
                        999999,
                        (
                                entity instanceof EntityLivingBase _livEnt && M.hasEffect(_livEnt, BohModMobEffects.ENGAGED.get())
                                    ? M.getAmplifier(M.getEffect(_livEnt, BohModMobEffects.ENGAGED.get()))
                                    : 0
                            )
                            + 1,
                        false,
                        false
                    )
                );
            }

            if ((
                    entity instanceof EntityLivingBase _livEnt && M.hasEffect(_livEnt, BohModMobEffects.ENGAGED.get())
                        ? M.getAmplifier(M.getEffect(_livEnt, BohModMobEffects.ENGAGED.get()))
                        : 0
                )
                > 7) {
                if (entity instanceof EntityLivingBase _entity) {
                    M.removeEffect(_entity, BohModMobEffects.ENGAGED.get());
                }

                Vec3 _center = new Vec3(x, y, z);

                for (Entity entityiterator : M.getEntitiesOfClass(world, Entity.class, new AABB(_center, _center).inflate(300.0), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> M.distanceToSqr(_entcnd, _center)))
                    .toList()) {
                    if (entityiterator instanceof SlenderManEntity) {
                        if (entity instanceof EntityPlayerMP _playerx) {
                            Advancement _adv = M.getAdvancement(M.getAdvancements(M.server(_playerx)), new ResourceLocation("boh:slender_gift"));
                            AdvancementProgress _ap = M.getOrStartProgress(M.getAdvancements(_playerx), _adv);
                            if (!M.isDone(_ap)) {
                                for (String criteria : M.getRemainingCriteria(_ap)) {
                                    M.award(M.getAdvancements(_playerx), _adv, criteria);
                                }
                            }
                        }

                        if (!M.isClientSide(M.level(entityiterator)) && M.getServer(entityiterator) != null) {
                            M.performPrefixedCommand(
                                M.getCommands(M.getServer(entityiterator)),
                                new CommandSourceStack(
                                    CommandSource.NULL,
                                    M.position(entityiterator),
                                    M.getRotationVector(entityiterator),
                                    M.level(entityiterator) instanceof WorldServer ? (WorldServer)M.level(entityiterator) : null,
                                    4,
                                    M.getString(M.getName(entityiterator)),
                                    M.getDisplayName(entityiterator),
                                    M.getServer(M.level(entityiterator)),
                                    entityiterator
                                ),
                                "/particle minecraft:squid_ink ~ ~ ~ 0.5 3 0.5 0 200"
                            );
                        }

                        if (!M.isClientSide(M.level(entityiterator)) && M.getServer(entityiterator) != null) {
                            M.performPrefixedCommand(
                                M.getCommands(M.getServer(entityiterator)),
                                new CommandSourceStack(
                                    CommandSource.NULL,
                                    M.position(entityiterator),
                                    M.getRotationVector(entityiterator),
                                    M.level(entityiterator) instanceof WorldServer ? (WorldServer)M.level(entityiterator) : null,
                                    4,
                                    M.getString(M.getName(entityiterator)),
                                    M.getDisplayName(entityiterator),
                                    M.getServer(M.level(entityiterator)),
                                    entityiterator
                                ),
                                "/advancement grant @a[distance=0..600] only boh:slender_gift "
                            );
                        }

                        if (!M.isClientSide(M.level(entityiterator)) && M.getServer(entityiterator) != null) {
                            M.performPrefixedCommand(
                                M.getCommands(M.getServer(entityiterator)),
                                new CommandSourceStack(
                                    CommandSource.NULL,
                                    M.position(entityiterator),
                                    M.getRotationVector(entityiterator),
                                    M.level(entityiterator) instanceof WorldServer ? (WorldServer)M.level(entityiterator) : null,
                                    4,
                                    M.getString(M.getName(entityiterator)),
                                    M.getDisplayName(entityiterator),
                                    M.getServer(M.level(entityiterator)),
                                    entityiterator
                                ),
                                "/playsound boh:slender_jumpscare hostile @a"
                            );
                        }

                        if (!M.isClientSide(M.level(entityiterator))) {
                            M.discard(entityiterator);
                        }
                    }
                }
            }
        }
    }
}
