package net.mcreator.boh.compat.client;

import net.mcreator.boh.compat.MClientImpl;
import net.mcreator.boh.compat.forge.client.DimensionSpecialEffectsManager;
import net.mcreator.boh.compat.forge.client.event.ComputeFogColor;
import net.mcreator.boh.compat.forge.client.event.Pre;
import net.mcreator.boh.compat.forge.client.event.RenderFog;
import net.mcreator.boh.compat.forge.client.event.Window;
import net.mcreator.boh.compat.forge.event.entity.player.LeftClickEmpty;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.mcreator.boh.compat.mc.client.renderer.FogMode;
import net.mcreator.boh.compat.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import org.lwjgl.opengl.GL11;

/** Client-side 1.20 events: GUI overlay, fog, fog colour, left click on air, custom dimension skies. */
public final class ClientEventBridge {

    public static int overlayEvents;
    public static int flagFrames;
    private boolean attackWasDown;
    private final GuiGraphics graphics = new GuiGraphics();

    public static void install() {
        ClientEventBridge b = new ClientEventBridge();
        MinecraftForge.EVENT_BUS.register(b);
        FMLCommonHandler.instance().bus().register(b);
    }

    @SubscribeEvent
    public void onOverlay(RenderGameOverlayEvent.Pre e) {
        if (e.type != RenderGameOverlayEvent.ElementType.ALL) return;
        ScaledResolution r = e.resolution;
        overlayEvents++;
        if (r != null && net.minecraft.client.Minecraft.getMinecraft().thePlayer != null) {
            net.minecraft.nbt.NBTTagCompound d = net.minecraft.client.Minecraft.getMinecraft().thePlayer.getEntityData();
            if (d.getDouble("exe_static") != 0 || d.getDouble("exe_apparison") != 0) flagFrames++;
        }
        // Forge 1.7.10 fires Pre(ALL) before GuiIngameForge sets up the 2D GUI projection; 1.20 overlays expect it
        net.minecraft.client.Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        try {
            MinecraftForge.EVENT_BUS.post(new Pre(new Window(r.getScaledWidth(), r.getScaledHeight()), graphics, e.partialTicks));
        } finally {
            GL11.glPopAttrib();
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
    }

    /** Screen overlays of the KuroLIB stand-in effects (sleep darkness, flashbang whiteout). */
    @SubscribeEvent
    public void onOverlayPost(RenderGameOverlayEvent.Post e) {
        if (e.type != RenderGameOverlayEvent.ElementType.ALL) return;
        net.minecraft.client.entity.EntityPlayerSP p = net.minecraft.client.Minecraft.getMinecraft().thePlayer;
        if (p == null) return;
        float alpha = 0;
        int rgb = 0;
        net.minecraft.potion.PotionEffect s = net.mcreator.boh.compat.effect.KuroEffects.sleep == null ? null : p.getActivePotionEffect(net.mcreator.boh.compat.effect.KuroEffects.sleep);
        net.minecraft.potion.PotionEffect f = net.mcreator.boh.compat.effect.KuroEffects.flashbanged == null ? null : p.getActivePotionEffect(net.mcreator.boh.compat.effect.KuroEffects.flashbanged);
        if (f != null) {
            alpha = Math.min(1F, f.getDuration() / 100F);
            rgb = 0xFFFFFF;
        } else if (s != null) {
            alpha = Math.min(0.92F, 0.6F + 0.3F * (float) Math.sin(p.ticksExisted * 0.05));
        }
        if (alpha <= 0) return;
        int a = (int) (alpha * 255) << 24;
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT);
        net.minecraft.client.gui.Gui.drawRect(0, 0, e.resolution.getScaledWidth(), e.resolution.getScaledHeight(), a | rgb);
        GL11.glPopAttrib();
    }

    @SubscribeEvent
    public void onFog(EntityViewRenderEvent.RenderFogEvent e) {
        RenderFog ev = new RenderFog(e.fogMode < 0 ? FogMode.FOG_SKY : FogMode.FOG_TERRAIN, MClientImpl.camera(), e.renderPartialTicks,
            e.farPlaneDistance * 0.75F, e.farPlaneDistance);
        if (MinecraftForge.EVENT_BUS.post(ev)) {
            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
            GL11.glFogf(GL11.GL_FOG_START, ev.getNearPlaneDistance());
            GL11.glFogf(GL11.GL_FOG_END, ev.getFarPlaneDistance());
        }
    }

    @SubscribeEvent
    public void onFogColor(EntityViewRenderEvent.FogColors e) {
        ComputeFogColor ev = new ComputeFogColor(MClientImpl.camera(), e.renderPartialTicks, e.red, e.green, e.blue);
        MinecraftForge.EVENT_BUS.post(ev);
        e.red = ev.getRed();
        e.green = ev.getGreen();
        e.blue = ev.getBlue();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getMinecraft();
        boolean down = mc.gameSettings.keyBindAttack.getIsKeyPressed();
        if (down && !attackWasDown && mc.thePlayer != null && mc.currentScreen == null
            && (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.MISS))
            MinecraftForge.EVENT_BUS.post(new LeftClickEmpty(mc.thePlayer));
        attackWasDown = down;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load e) {
        if (!(e.world instanceof WorldClient)) return;
        DimensionSpecialEffects fx = DimensionSpecialEffectsManager.getForType(Dimensions.key(e.world.provider.dimensionId).location());
        if (fx != DimensionSpecialEffects.OVERWORLD) e.world.provider.setSkyRenderer(new SkyHook(fx));
    }

    /** Renders the vanilla sky, then the effects' custom sky pass. */
    static final class SkyHook extends IRenderHandler {

        private final DimensionSpecialEffects fx;
        private boolean inside;

        SkyHook(DimensionSpecialEffects fx) {
            this.fx = fx;
        }

        @Override
        public void render(float partialTicks, WorldClient world, Minecraft mc) {
            if (inside) return;
            inside = true;
            try {
                world.provider.setSkyRenderer(null);
                mc.renderGlobal.renderSky(partialTicks);
            } finally {
                world.provider.setSkyRenderer(this);
                inside = false;
            }
            GL11.glPushMatrix();
            GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            try {
                fx.renderSky(world, (int) world.getTotalWorldTime(), partialTicks, new PoseStack(), MClientImpl.camera(), new Matrix4f(), false,
                    () -> {});
            } catch (Throwable t) {
                // a broken sky predicate must not crash rendering
            } finally {
                GL11.glPopAttrib();
                GL11.glPopMatrix();
                RenderSystem.setShaderColor(1, 1, 1, 1);
            }
        }
    }
}
