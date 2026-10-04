package net.mcreator.boh.procedures;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.forge.event.entity.player.LeftClickEmpty;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mcreator.boh.compat.forge.network.Context;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.registries.Registries;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.damagesource.DamageTypes;
import net.mcreator.boh.compat.mc.world.item.enchantment.EnchantmentHelper;
import net.mcreator.boh.entity.RendProjectileEntity;
import net.mcreator.boh.init.BohModEnchantments;
import net.mcreator.boh.init.BohModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RendEnchantUseProcedure {
    @SubscribeEvent
    public void onLeftClick(LeftClickEmpty event) {
        M.sendToServer(BohMod.PACKET_HANDLER, new RendEnchantUseProcedure.RendEnchantUseMessage());
        execute(M.getLevel(event), M.getX(M.getPos(event)), M.getY(M.getPos(event)), M.getZ(M.getPos(event)), M.getEntity(event));
    }

    public static void execute(World world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, World world, double x, double y, double z, Entity entity) {
        if (entity != null
            && EnchantmentHelper.getItemEnchantmentLevel(
                    BohModEnchantments.REND.get(), entity instanceof EntityLivingBase _livEntx ? M.getMainHandItem(_livEntx) : M.EMPTY
                )
                != 0
            && !(
                entity instanceof EntityPlayer _plrCldCheck3
                    && M.isOnCooldown(
                        M.getCooldowns(_plrCldCheck3), M.getItem(entity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)
                    )
            )) {
            if (entity instanceof EntityPlayer _player) {
                M.addCooldown(M.getCooldowns(_player), M.getItem(entity instanceof EntityLivingBase _livEntxx ? M.getMainHandItem(_livEntxx) : M.EMPTY), 40);
            }

            World projectileLevel = M.level(entity);
            if (!M.isClientSide(projectileLevel)) {
                Entity _entityToSpawn = (new Object() {
                    public Entity getArrow(World level, Entity shooter, float damage, int knockback, byte piercing) {
                        BohAbstractArrow entityToSpawn = new RendProjectileEntity(BohModEntities.REND_PROJECTILE.get(), level);
                        M.setOwner(entityToSpawn, shooter);
                        M.setBaseDamage(entityToSpawn, damage);
                        M.setKnockback(entityToSpawn, knockback);
                        M.setSilent(entityToSpawn, true);
                        M.setPierceLevel(entityToSpawn, piercing);
                        return entityToSpawn;
                    }
                }).getArrow(projectileLevel, entity, 0.0F, 0, (byte)10);
                M.setPos(_entityToSpawn, M.getX(entity), M.getEyeY(entity) - 0.1, M.getZ(entity));
                M.shoot(_entityToSpawn, M.getLookAngle(entity).x, M.getLookAngle(entity).y, M.getLookAngle(entity).z, 5.0F, 0.1F);
                M.addFreshEntity(projectileLevel, _entityToSpawn);
            }

            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rend_slash")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:rend_slash")), SoundSource.NEUTRAL, 1.0F, 1.0F, false
                    );
                }
            }

            M.hurt(entity, M.new_DamageSource(M.getHolderOrThrow(M.registryOrThrow(M.registryAccess(world), Registries.DAMAGE_TYPE), DamageTypes.MAGIC)), 2.5F);
            ItemStack _ist = entity instanceof EntityLivingBase _livEntxx ? M.getMainHandItem(_livEntxx) : M.EMPTY;
            if (M.hurt(_ist, 3, RandomSource.create(), null)) {
                M.shrink(_ist, 1);
                M.setDamageValue(_ist, 0);
            }
        }
    }

    public static class RendEnchantUseMessage {
        public RendEnchantUseMessage() {
        }

        public RendEnchantUseMessage(FriendlyByteBuf buffer) {
        }

        public static void buffer(RendEnchantUseProcedure.RendEnchantUseMessage message, FriendlyByteBuf buffer) {
        }

        public static void handler(RendEnchantUseProcedure.RendEnchantUseMessage message, Supplier<Context> contextSupplier) {
            Context context = contextSupplier.get();
            M.enqueueWork(
                context,
                () -> {
                    if (M.hasChunkAt(M.level(M.getSender(context)), M.blockPosition(M.getSender(context)))) {
                        RendEnchantUseProcedure.execute(
                            M.level(M.getSender(context)),
                            M.getX(M.getSender(context)),
                            M.getY(M.getSender(context)),
                            M.getZ(M.getSender(context)),
                            M.getSender(context)
                        );
                    }
                }
            );
            M.setPacketHandled(context, true);
        }

        @SubscribeEvent
        public void registerMessage(FMLCommonSetupEvent event) {
            BohMod.addNetworkMessage(
                RendEnchantUseProcedure.RendEnchantUseMessage.class,
                RendEnchantUseProcedure.RendEnchantUseMessage::buffer,
                RendEnchantUseProcedure.RendEnchantUseMessage::new,
                RendEnchantUseProcedure.RendEnchantUseMessage::handler
            );
        }
    }
}
