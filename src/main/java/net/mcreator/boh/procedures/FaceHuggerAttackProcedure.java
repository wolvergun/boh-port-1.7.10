package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.entity.FacehuggerEntity;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class FaceHuggerAttackProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
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

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null && sourceentity instanceof FacehuggerEntity) {
            M.setDeltaMovement(
                sourceentity,
                new Vec3(
                    M.getDeltaMovement(sourceentity).x() + M.getLookAngle(sourceentity).x,
                    M.getDeltaMovement(sourceentity).y() + M.getLookAngle(sourceentity).y + 0.3,
                    M.getDeltaMovement(sourceentity).z() + M.getLookAngle(sourceentity).z
                )
            );
            if (sourceentity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 60, 254, false, false));
            }

            if (sourceentity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                M.addEffect(_entity, M.new_PotionEffect(MobEffects.WEAKNESS, 60, 254, false, false));
            }

            if (!(entity instanceof EntityLivingBase _livEnt10 && M.isBlocking(_livEnt10))
                && M.getItem(entity instanceof EntityLivingBase _entGetArmor ? M.getItemBySlot(_entGetArmor, EquipmentSlot.HEAD) : M.EMPTY)
                    != BohModItems.FACEHUGGER_FACE.get()) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:facehugger_attack")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:facehugger_attack")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                if (world instanceof WorldServer _level) {
                    EntityItem entityToSpawn = M.new_EntityItem(
                        _level, x, y, z, entity instanceof EntityLivingBase _entGetArmorx ? M.getItemBySlot(_entGetArmorx, EquipmentSlot.HEAD) : M.EMPTY
                    );
                    M.setPickUpDelay(entityToSpawn, 10);
                    M.setUnlimitedLifetime(entityToSpawn);
                    M.addFreshEntity(_level, entityToSpawn);
                }

                if (M.getItem(entity instanceof EntityLivingBase _entGetArmorx ? M.getItemBySlot(_entGetArmorx, EquipmentSlot.HEAD) : M.EMPTY)
                    != BohModItems.FACEHUGGER_FACE.get()) {
                    if (entity instanceof EntityPlayer _player) {
                        M.set(M.armor(M.getInventory(_player)), 3, M.new_ItemStack(BohModItems.FACEHUGGER_FACE.get()));
                        M.setChanged(M.getInventory(_player));
                    } else if (entity instanceof EntityLivingBase _living) {
                        M.setItemSlot(_living, EquipmentSlot.HEAD, M.new_ItemStack(BohModItems.FACEHUGGER_FACE.get()));
                    }

                    if (!M.isClientSide(M.level(sourceentity))) {
                        M.discard(sourceentity);
                    }
                }
            }
        }
    }
}
