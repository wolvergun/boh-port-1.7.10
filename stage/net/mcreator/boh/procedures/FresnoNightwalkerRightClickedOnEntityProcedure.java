package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.FresnoNightcrawlerEntity;
import net.mcreator.boh.init.BohModItems;
import net.minecraft.client.Minecraft;
import net.mcreator.boh.compat.mc.commands.CommandSource;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.world.effect.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.Items;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class FresnoNightwalkerRightClickedOnEntityProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity != null && sourceentity != null) {
            if (!(sourceentity instanceof EntityPlayer _plrCldCheck1 && M.isOnCooldown(M.getCooldowns(_plrCldCheck1), M.getItem((sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)))) && M.getItem((sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)) == Items.APPLE) {
                if (sourceentity instanceof EntityPlayer _player) {
                    M.addCooldown(M.getCooldowns(_player), M.getItem((sourceentity instanceof EntityLivingBase _livEnt ? M.getMainHandItem(_livEnt) : M.EMPTY)), 15);
                }
                if (!(new Object() {

                    public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof EntityPlayerMP _serverPlayer) {
                            return M.getGameModeForPlayer(M.gameMode(_serverPlayer)) == GameType.CREATIVE;
                        } else {
                            return M.isClientSide(M.level(_ent)) && _ent instanceof EntityPlayer _player ? M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player))) != null && M.getGameMode(M.getPlayerInfo(M.getConnection(Minecraft.getMinecraft()), M.getId(M.getGameProfile(_player)))) == GameType.CREATIVE : false;
                        }
                    }
                }).checkGamemode(sourceentity) && sourceentity instanceof EntityPlayer _player) {
                    ItemStack _stktoremove = M.new_ItemStack(Items.APPLE);
                    M.clearOrCountMatchingItems(M.getInventory(_player), p -> M.getItem(_stktoremove) == M.getItem(p), 1, M.getCraftSlots(M.inventoryMenu(_player)));
                }
                Entity _ent = entity;
                if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                    M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle item apple ~ ~1.1 ~ .1 .1 .1 .05 5");
                }
                if (world instanceof World) {
                    M.playLocalSound(((World) world), x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                }
                if (Math.random() < 0.1) {
                    _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/execute at @e[limit=1,type=boh:fresno_nightcrawler,sort=nearest] run playsound boh:fresno_happy ambient @a");
                    }
                    _ent = entity;
                    if (!M.isClientSide(M.level(_ent)) && M.getServer(_ent) != null) {
                        M.performPrefixedCommand(M.getCommands(M.getServer(_ent)), new CommandSourceStack(CommandSource.NULL, M.position(_ent), M.getRotationVector(_ent), M.level(_ent) instanceof WorldServer ? (WorldServer) M.level(_ent) : null, 4, M.getString(M.getName(_ent)), M.getDisplayName(_ent), M.getServer(M.level(_ent)), _ent), "/particle boh:confetti ~ ~1 ~ 0 0 0 .1 20");
                    }
                    if (entity instanceof FresnoNightcrawlerEntity) {
                        ((FresnoNightcrawlerEntity) entity).setAnimation("excited");
                    }
                    if (world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x + 0.5, y + 2.0, z + 0.5, M.new_ItemStack(Items.PHANTOM_MEMBRANE));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                    if (Math.random() < 0.5 && world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x + 0.5, y + 2.0, z + 0.5, M.new_ItemStack(BohModItems.PARTY_POPPER.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                    if (Math.random() < 0.75 && world instanceof WorldServer _level) {
                        EntityItem entityToSpawn = M.new_EntityItem(_level, x + 0.5, y + 2.0, z + 0.5, M.new_ItemStack(BohModItems.ECTOPLASM.get()));
                        M.setPickUpDelay(entityToSpawn, 10);
                        M.addFreshEntity(_level, entityToSpawn);
                    }
                    if (entity instanceof EntityLivingBase _entity && !M.isClientSide(M.level(_entity))) {
                        M.addEffect(_entity, M.new_PotionEffect(MobEffects.MOVEMENT_SLOWDOWN, 40, 254, false, false));
                    }
                }
            }
        }
    }
}
