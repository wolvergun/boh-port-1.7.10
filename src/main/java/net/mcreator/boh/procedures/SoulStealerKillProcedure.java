package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.particles.ParticleTypes;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.Mth;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class SoulStealerKillProcedure {
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(
                event,
                M.level(M.getEntity(event)),
                M.getX(M.getEntity(event)),
                M.getY(M.getEntity(event)),
                M.getZ(M.getEntity(event)),
                M.getEntity(event),
                M.getEntity(M.getSource(event))
            );
        }
    }

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, final World world, final double x, final double y, final double z, Entity entity, Entity sourceentity) {
        if (entity != null
            && sourceentity != null
            && M.getItem(sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY) == BohModItems.SOUL_STEALER.get()
            && Math.random() < 0.5) {
            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker.cast_spell")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            if (world instanceof WorldServer _level) {
                M.sendParticles(_level, ParticleTypes.SOUL, x, y + M.getBbHeight(entity) / 2.0F, z, 10, 0.2, 0.2, 0.2, 0.01);
            }

            if (Math.random() < 0.25) {
                (new Object() {
                    void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                        if (world instanceof WorldServer _level) {
                            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.EXOTIC_SOUL.get()));
                            M.setPickUpDelay(entityToSpawn, 10);
                            M.addFreshEntity(_level, entityToSpawn);
                        }

                        BohMod.queueServerWork(ticks, () -> {
                            if (timedlooptotal > timedloopiterator + 1) {
                                this.timedLoop(timedloopiterator + 1, timedlooptotal, ticks);
                            }
                        });
                    }
                }).timedLoop(0, (int)Mth.nextDouble(RandomSource.create(), 1.0, 3.0), 1);
            } else if (Math.random() < 0.5) {
                (new Object() {
                    void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                        if (world instanceof WorldServer _level) {
                            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.DEMONIC_SOUL.get()));
                            M.setPickUpDelay(entityToSpawn, 10);
                            M.addFreshEntity(_level, entityToSpawn);
                        }

                        BohMod.queueServerWork(ticks, () -> {
                            if (timedlooptotal > timedloopiterator + 1) {
                                this.timedLoop(timedloopiterator + 1, timedlooptotal, ticks);
                            }
                        });
                    }
                }).timedLoop(0, (int)Mth.nextDouble(RandomSource.create(), 1.0, 3.0), 1);
            } else if (Math.random() < 0.75) {
                (new Object() {
                    void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                        if (world instanceof WorldServer _level) {
                            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.KILLERS_SOUL.get()));
                            M.setPickUpDelay(entityToSpawn, 10);
                            M.addFreshEntity(_level, entityToSpawn);
                        }

                        BohMod.queueServerWork(ticks, () -> {
                            if (timedlooptotal > timedloopiterator + 1) {
                                this.timedLoop(timedloopiterator + 1, timedlooptotal, ticks);
                            }
                        });
                    }
                }).timedLoop(0, (int)Mth.nextDouble(RandomSource.create(), 1.0, 3.0), 1);
            } else {
                (new Object() {
                    void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
                        if (world instanceof WorldServer _level) {
                            EntityItem entityToSpawn = M.new_EntityItem(_level, x, y, z, M.new_ItemStack(BohModItems.MONSTROUS_SOUL.get()));
                            M.setPickUpDelay(entityToSpawn, 10);
                            M.addFreshEntity(_level, entityToSpawn);
                        }

                        BohMod.queueServerWork(ticks, () -> {
                            if (timedlooptotal > timedloopiterator + 1) {
                                this.timedLoop(timedloopiterator + 1, timedlooptotal, ticks);
                            }
                        });
                    }
                }).timedLoop(0, (int)Mth.nextDouble(RandomSource.create(), 1.0, 3.0), 1);
            }
        }
    }
}
