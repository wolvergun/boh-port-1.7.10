package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class DamageWithGojiHeadProcedure {
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && M.getEntity(event) != null) {
            execute(event, M.level(M.getEntity(event)), M.getEntity(event), M.getEntity(M.getSource(event)));
        }
    }

    public static void execute(World world, Entity entity, Entity sourceentity) {
        execute(null, world, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, World world, Entity entity, Entity sourceentity) {
        if (entity != null
            && sourceentity != null
            && M.getItem(sourceentity instanceof EntityLivingBase _entGetArmorxxxx ? M.getItemBySlot(_entGetArmorxxxx, EquipmentSlot.HEAD) : M.EMPTY)
                == BohModItems.GOJI_HEAD_HELMET.get()
            && (
                M.getItem(entity instanceof EntityLivingBase _entGetArmorxxx ? M.getItemBySlot(_entGetArmorxxx, EquipmentSlot.HEAD) : M.EMPTY)
                        != M.getItem(M.EMPTY)
                    || M.getItem(entity instanceof EntityLivingBase _entGetArmorxx ? M.getItemBySlot(_entGetArmorxx, EquipmentSlot.CHEST) : M.EMPTY)
                        != M.getItem(M.EMPTY)
                    || M.getItem(entity instanceof EntityLivingBase _entGetArmorx ? M.getItemBySlot(_entGetArmorx, EquipmentSlot.LEGS) : M.EMPTY)
                        != M.getItem(M.EMPTY)
                    || M.getItem(entity instanceof EntityLivingBase _entGetArmor ? M.getItemBySlot(_entGetArmor, EquipmentSlot.FEET) : M.EMPTY)
                        != M.getItem(M.EMPTY)
            )
            && Math.random() < 0.2) {
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
                            "/particle minecraft:block redstone_block ~ ~1 ~ .5 1 .5 0 25"
                        );
                    }

                    M.hurt(
                        entity,
                        M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.PLAYER_ATTACK)),
                        5.0F
                    );
                }
            );
        }
    }
}
