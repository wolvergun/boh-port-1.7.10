package net.mcreator.boh.compat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.mcreator.boh.compat.client.particle.ParticleEngine;
import net.mcreator.boh.compat.entity.ItemCooldowns;
import net.mcreator.boh.compat.mc.client.Camera;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.geo.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public final class MClientImpl {
    public static final Map<UUID, Object[]> BOSS_BARS = new LinkedHashMap<>();
    private static final RenderItem ITEM_RENDER = new RenderItem();

    private MClientImpl() {
    }

    public static float starBrightness(World w, float partial) {
        return w.isRemote ? w.getStarBrightness(partial) : 0.0F;
    }

    public static void spawnModParticle(ParticleType<?> t, double x, double y, double z, double dx, double dy, double dz) {
        ParticleEngine.spawn(t, x, y, z, dx, dy, dz);
    }

    public static void spawnParticleBurst(ParticleType<?> t, double x, double y, double z, int count, double dx, double dy, double dz, double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld != null) {
            Random r = mc.theWorld.rand;
            if (count == 0) {
                ParticleEngine.spawn(t, x, y, z, dx * speed, dy * speed, dz * speed);
            } else {
                for (int i = 0; i < count; i++) {
                    ParticleEngine.spawn(
                        t,
                        x + r.nextGaussian() * dx,
                        y + r.nextGaussian() * dy,
                        z + r.nextGaussian() * dz,
                        r.nextGaussian() * speed,
                        r.nextGaussian() * speed,
                        r.nextGaussian() * speed
                    );
                }
            }
        }
    }

    public static void setActionBar(String text) {
        Minecraft.getMinecraft().ingameGUI.func_110326_a(text, false);
    }

    public static void stopSound(String legacyName) {
        SoundHandler h = Minecraft.getMinecraft().getSoundHandler();
        if (legacyName != null && !legacyName.isEmpty()) {
            try {
                Object mgr = field(h, "sndManager", "field_147694_f");
                Map<String, ISound> playing = (Map<String, ISound>)field(mgr, "playingSounds", "field_148629_h");
                String path = legacyName.contains(":") ? legacyName.substring(legacyName.indexOf(58) + 1) : legacyName;

                for (ISound s : new ArrayList<>(playing.values())) {
                    if (s.getPositionedSoundLocation().getResourcePath().equals(path)) {
                        h.stopSound(s);
                    }
                }
            } catch (Exception var7) {
            }
        } else {
            h.stopSounds();
        }
    }

    private static Object field(Object o, String mcp, String srg) throws Exception {
        for (String n : new String[]{mcp, srg}) {
            try {
                Field f = o.getClass().getDeclaredField(n);
                f.setAccessible(true);
                return f.get(o);
            } catch (NoSuchFieldException var8) {
            }
        }

        throw new NoSuchFieldException(mcp);
    }

    public static void closeScreen() {
        Minecraft.getMinecraft().displayGuiScreen(null);
    }

    public static void bossBar(UUID id, String name, float progress, int color, int notches, boolean show) {
        if (show) {
            BOSS_BARS.put(id, new Object[]{name, progress, color, notches});
        } else {
            BOSS_BARS.remove(id);
        }
    }

    public static boolean altDown() {
        return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
    }

    public static void applyCooldown(Item item, int ticks) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null && item != null) {
            ItemCooldowns.of(mc.thePlayer).addCooldown(item, ticks);
        }
    }

    public static Vec3 skyColor(World w, float pt) {
        Entity v = Minecraft.getMinecraft().renderViewEntity;
        if (v == null) {
            return new Vec3(0.0, 0.0, 0.0);
        } else {
            net.minecraft.util.Vec3 c = w.getSkyColor(v, pt);
            return new Vec3(c.xCoord, c.yCoord, c.zCoord);
        }
    }

    public static GuiScreen screen() {
        return Minecraft.getMinecraft().currentScreen;
    }

    public static WorldClient world() {
        return Minecraft.getMinecraft().theWorld;
    }

    public static AbstractClientPlayer player() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static GameType gameModeOf(UUID id) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) {
            return null;
        } else if (mc.thePlayer.getUniqueID().equals(id) && mc.playerController != null) {
            return mc.playerController.isInCreativeMode()
                ? GameType.CREATIVE
                : (!mc.thePlayer.capabilities.allowEdit ? GameType.ADVENTURE : GameType.SURVIVAL);
        } else {
            if (mc.theWorld != null) {
                for (Object o : mc.theWorld.playerEntities) {
                    EntityPlayer p = (EntityPlayer)o;
                    if (p.getUniqueID().equals(id)) {
                        return p.capabilities.isCreativeMode ? GameType.CREATIVE : GameType.SURVIVAL;
                    }
                }
            }

            return null;
        }
    }

    public static Camera camera() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityLivingBase v = mc.renderViewEntity;
        if (v == null) {
            return new Camera(null, new Vec3(0.0, 0.0, 0.0));
        } else {
            float pt = RenderUtils.partialTick;
            double x = v.prevPosX + (v.posX - v.prevPosX) * pt;
            double y = v.prevPosY + (v.posY - v.prevPosY) * pt;
            double z = v.prevPosZ + (v.posZ - v.prevPosZ) * pt;
            if (v != mc.thePlayer) {
                y += v.getEyeHeight();
            }

            return new Camera(v, new Vec3(x, y, z));
        }
    }

    public static int renderDistance() {
        return Minecraft.getMinecraft().gameSettings.renderDistanceChunks;
    }

    public static void displayItemActivation(ItemStack stack) {
        if (stack != null && stack.getItem() != null) {
            Minecraft.getMinecraft().ingameGUI.func_110326_a(stack.getDisplayName(), false);
        }
    }

    public static void lightmap(boolean on) {
        if (on) {
            Minecraft.getMinecraft().entityRenderer.enableLightmap(0.0);
        } else {
            Minecraft.getMinecraft().entityRenderer.disableLightmap(0.0);
        }
    }

    public static ResourceLocation skin(EntityPlayer p) {
        return p instanceof AbstractClientPlayer ? ((AbstractClientPlayer)p).getLocationSkin() : AbstractClientPlayer.locationStevePng;
    }

    private static ScaledResolution res() {
        Minecraft mc = Minecraft.getMinecraft();
        return new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    }

    public static int guiWidth() {
        return res().getScaledWidth();
    }

    public static int guiHeight() {
        return res().getScaledHeight();
    }

    public static void blit(ResourceLocation tex, int x, int y, float u, float v, int w, int h, int uw, int vh, int texW, int texH) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
        Tessellator t = Tessellator.instance;
        float fu = 1.0F / texW;
        float fv = 1.0F / texH;
        t.startDrawingQuads();
        t.addVertexWithUV(x, y + h, 0.0, u * fu, (v + vh) * fv);
        t.addVertexWithUV(x + w, y + h, 0.0, (u + uw) * fu, (v + vh) * fv);
        t.addVertexWithUV(x + w, y, 0.0, (u + uw) * fu, v * fv);
        t.addVertexWithUV(x, y, 0.0, u * fu, v * fv);
        t.draw();
    }

    public static int drawString(String text, int x, int y, int color, boolean shadow) {
        FontRenderer f = Minecraft.getMinecraft().fontRenderer;
        return shadow ? f.drawStringWithShadow(text, x, y, color) : f.drawString(text, x, y, color);
    }

    public static int stringWidth(String text) {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }

    public static void fill(int x1, int y1, int x2, int y2, int color) {
        Gui.drawRect(x1, y1, x2, y2, color);
    }

    public static void renderItem(ItemStack stack, int x, int y) {
        if (stack != null && stack.getItem() != null) {
            Minecraft mc = Minecraft.getMinecraft();
            RenderHelper.enableGUIStandardItemLighting();
            ITEM_RENDER.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, x, y);
            RenderHelper.disableStandardItemLighting();
        }
    }
}
