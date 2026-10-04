package net.mcreator.boh.compat;

import net.mcreator.boh.compat.client.particle.ParticleEngine;
import net.mcreator.boh.compat.entity.ItemCooldowns;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * Client-only implementations behind the M helpers. Only referenced from method bodies, so a dedicated server
 * never loads it.
 */
public final class MClientImpl {

    private MClientImpl() {}

    public static float starBrightness(World w, float partial) {
        return w.isRemote ? w.getStarBrightness(partial) : 0;
    }

    public static void spawnModParticle(ParticleType<?> t, double x, double y, double z, double dx, double dy, double dz) {
        ParticleEngine.spawn(t, x, y, z, dx, dy, dz);
    }

    public static void spawnParticleBurst(ParticleType<?> t, double x, double y, double z, int count, double dx, double dy, double dz,
        double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null) return;
        java.util.Random r = mc.theWorld.rand;
        if (count == 0) {
            ParticleEngine.spawn(t, x, y, z, dx * speed, dy * speed, dz * speed);
            return;
        }
        for (int i = 0; i < count; i++) {
            ParticleEngine.spawn(t, x + r.nextGaussian() * dx, y + r.nextGaussian() * dy, z + r.nextGaussian() * dz,
                r.nextGaussian() * speed, r.nextGaussian() * speed, r.nextGaussian() * speed);
        }
    }

    public static void setActionBar(String text) {
        Minecraft.getMinecraft().ingameGUI.func_110326_a(text, false);
    }

    /** Stops playing sounds whose 1.7.10 name matches (any domain), or all sounds for an empty name. */
    @SuppressWarnings("unchecked")
    public static void stopSound(String legacyName) {
        net.minecraft.client.audio.SoundHandler h = Minecraft.getMinecraft().getSoundHandler();
        if (legacyName == null || legacyName.isEmpty()) {
            h.stopSounds();
            return;
        }
        try {
            Object mgr = field(h, "sndManager", "field_147694_f");
            java.util.Map<String, net.minecraft.client.audio.ISound> playing = (java.util.Map<String, net.minecraft.client.audio.ISound>) field(mgr, "playingSounds", "field_148629_h");
            String path = legacyName.contains(":") ? legacyName.substring(legacyName.indexOf(':') + 1) : legacyName;
            for (net.minecraft.client.audio.ISound s : new java.util.ArrayList<>(playing.values())) {
                if (s.getPositionedSoundLocation().getResourcePath().equals(path)) h.stopSound(s);
            }
        } catch (Exception ignored) {}
    }

    private static Object field(Object o, String mcp, String srg) throws Exception {
        for (String n : new String[] { mcp, srg }) {
            try {
                java.lang.reflect.Field f = o.getClass().getDeclaredField(n);
                f.setAccessible(true);
                return f.get(o);
            } catch (NoSuchFieldException ignored) {}
        }
        throw new NoSuchFieldException(mcp);
    }

    public static void closeScreen() {
        Minecraft.getMinecraft().displayGuiScreen(null);
    }

    /** Boss bars announced by the server: id -> {name, progress, color, notches}. */
    public static final java.util.Map<java.util.UUID, Object[]> BOSS_BARS = new java.util.LinkedHashMap<>();

    public static void bossBar(java.util.UUID id, String name, float progress, int color, int notches, boolean show) {
        if (show) BOSS_BARS.put(id, new Object[] { name, progress, color, notches });
        else BOSS_BARS.remove(id);
    }

    public static boolean altDown() {
        return org.lwjgl.input.Keyboard.isKeyDown(56) || org.lwjgl.input.Keyboard.isKeyDown(184);
    }

    public static void applyCooldown(Item item, int ticks) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null && item != null) ItemCooldowns.of(mc.thePlayer).addCooldown(item, ticks);
    }

    // ------------------------------------------------------------------ player / camera / options

    public static net.mcreator.boh.compat.mc.world.phys.Vec3 skyColor(World w, float pt) {
        net.minecraft.entity.Entity v = Minecraft.getMinecraft().renderViewEntity;
        if (v == null) return new net.mcreator.boh.compat.mc.world.phys.Vec3(0, 0, 0);
        net.minecraft.util.Vec3 c = w.getSkyColor(v, pt);
        return new net.mcreator.boh.compat.mc.world.phys.Vec3(c.xCoord, c.yCoord, c.zCoord);
    }

    public static net.minecraft.client.gui.GuiScreen screen() {
        return Minecraft.getMinecraft().currentScreen;
    }

    public static net.minecraft.client.multiplayer.WorldClient world() {
        return Minecraft.getMinecraft().theWorld;
    }

    public static net.minecraft.client.entity.AbstractClientPlayer player() {
        return Minecraft.getMinecraft().thePlayer;
    }

    /** Game mode of a player as the client knows it (own player exact, others from the tab list when available). */
    public static net.mcreator.boh.compat.mc.world.level.GameType gameModeOf(java.util.UUID id) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return null;
        if (mc.thePlayer.getUniqueID().equals(id) && mc.playerController != null)
            return mc.playerController.isInCreativeMode() ? net.mcreator.boh.compat.mc.world.level.GameType.CREATIVE
                : !mc.thePlayer.capabilities.allowEdit ? net.mcreator.boh.compat.mc.world.level.GameType.ADVENTURE
                    : net.mcreator.boh.compat.mc.world.level.GameType.SURVIVAL;
        if (mc.theWorld != null) {
            for (Object o : mc.theWorld.playerEntities) {
                net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer) o;
                if (p.getUniqueID().equals(id)) return p.capabilities.isCreativeMode
                    ? net.mcreator.boh.compat.mc.world.level.GameType.CREATIVE
                    : net.mcreator.boh.compat.mc.world.level.GameType.SURVIVAL;
            }
        }
        return null;
    }

    public static net.mcreator.boh.compat.mc.client.Camera camera() {
        Minecraft mc = Minecraft.getMinecraft();
        net.minecraft.entity.EntityLivingBase v = mc.renderViewEntity;
        if (v == null) return new net.mcreator.boh.compat.mc.client.Camera(null, new net.mcreator.boh.compat.mc.world.phys.Vec3(0, 0, 0));
        float pt = net.mcreator.boh.geo.RenderUtils.partialTick;
        double x = v.prevPosX + (v.posX - v.prevPosX) * pt;
        double y = v.prevPosY + (v.posY - v.prevPosY) * pt;
        double z = v.prevPosZ + (v.posZ - v.prevPosZ) * pt;
        // 1.7.10 client player posY is already at eye level
        if (!(v == mc.thePlayer)) y += v.getEyeHeight();
        return new net.mcreator.boh.compat.mc.client.Camera(v, new net.mcreator.boh.compat.mc.world.phys.Vec3(x, y, z));
    }

    public static int renderDistance() {
        return Minecraft.getMinecraft().gameSettings.renderDistanceChunks;
    }

    /** 1.7.10 has no totem animation; show the item as a short title-style overlay instead. */
    public static void displayItemActivation(net.minecraft.item.ItemStack stack) {
        if (stack != null && stack.getItem() != null) Minecraft.getMinecraft().ingameGUI.func_110326_a(stack.getDisplayName(), false);
    }

    public static void lightmap(boolean on) {
        if (on) Minecraft.getMinecraft().entityRenderer.enableLightmap(0);
        else Minecraft.getMinecraft().entityRenderer.disableLightmap(0);
    }

    public static net.minecraft.util.ResourceLocation skin(net.minecraft.entity.player.EntityPlayer p) {
        return p instanceof net.minecraft.client.entity.AbstractClientPlayer ? ((net.minecraft.client.entity.AbstractClientPlayer) p).getLocationSkin()
            : net.minecraft.client.entity.AbstractClientPlayer.locationStevePng;
    }

    // ------------------------------------------------------------------ 2D drawing (GuiGraphics)

    private static net.minecraft.client.gui.ScaledResolution res() {
        Minecraft mc = Minecraft.getMinecraft();
        return new net.minecraft.client.gui.ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    }

    public static int guiWidth() {
        return res().getScaledWidth();
    }

    public static int guiHeight() {
        return res().getScaledHeight();
    }

    public static void blit(net.minecraft.util.ResourceLocation tex, int x, int y, float u, float v, int w, int h, int uw, int vh,
        int texW, int texH) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
        net.minecraft.client.renderer.Tessellator t = net.minecraft.client.renderer.Tessellator.instance;
        float fu = 1f / texW, fv = 1f / texH;
        t.startDrawingQuads();
        t.addVertexWithUV(x, y + h, 0, u * fu, (v + vh) * fv);
        t.addVertexWithUV(x + w, y + h, 0, (u + uw) * fu, (v + vh) * fv);
        t.addVertexWithUV(x + w, y, 0, (u + uw) * fu, v * fv);
        t.addVertexWithUV(x, y, 0, u * fu, v * fv);
        t.draw();
    }

    public static int drawString(String text, int x, int y, int color, boolean shadow) {
        net.minecraft.client.gui.FontRenderer f = Minecraft.getMinecraft().fontRenderer;
        return shadow ? f.drawStringWithShadow(text, x, y, color) : f.drawString(text, x, y, color);
    }

    public static int stringWidth(String text) {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }

    public static void fill(int x1, int y1, int x2, int y2, int color) {
        net.minecraft.client.gui.Gui.drawRect(x1, y1, x2, y2, color);
    }

    private static final net.minecraft.client.renderer.entity.RenderItem ITEM_RENDER = new net.minecraft.client.renderer.entity.RenderItem();

    public static void renderItem(net.minecraft.item.ItemStack stack, int x, int y) {
        if (stack == null || stack.getItem() == null) return;
        Minecraft mc = Minecraft.getMinecraft();
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        ITEM_RENDER.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, x, y);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    }
}
