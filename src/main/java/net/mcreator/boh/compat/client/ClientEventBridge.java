package net.mcreator.boh.compat.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.mcreator.boh.compat.MClientImpl;
import net.mcreator.boh.compat.effect.KuroEffects;
import net.mcreator.boh.compat.forge.client.DimensionSpecialEffectsManager;
import net.mcreator.boh.compat.forge.client.event.ComputeFogColor;
import net.mcreator.boh.compat.forge.client.event.RenderFog;
import net.mcreator.boh.compat.forge.client.event.Window;
import net.mcreator.boh.compat.forge.event.entity.player.LeftClickEmpty;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.mcreator.boh.compat.mc.client.renderer.FogMode;
import net.mcreator.boh.compat.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.boh.compat.world.Dimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent.Load;
import org.lwjgl.opengl.GL11;

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
    public void onOverlay(Pre e) {
        if (e.type == ElementType.ALL) {
            ScaledResolution r = e.resolution;
            overlayEvents++;
            if (r != null && Minecraft.getMinecraft().thePlayer != null) {
                NBTTagCompound d = Minecraft.getMinecraft().thePlayer.getEntityData();
                if (d.getDouble("exe_static") != 0.0 || d.getDouble("exe_apparison") != 0.0) {
                    flagFrames++;
                }
            }

            Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
            GL11.glEnable(3553);
            GL11.glPushAttrib(24832);

            try {
                MinecraftForge.EVENT_BUS
                    .post(new net.mcreator.boh.compat.forge.client.event.Pre(new Window(r.getScaledWidth(), r.getScaledHeight()), this.graphics, e.partialTicks));
            } finally {
                GL11.glPopAttrib();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    @SubscribeEvent
    public void onOverlayPost(Post e) {
        if (e.type == ElementType.ALL) {
            EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
            if (p != null) {
                float alpha = 0.0F;
                int rgb = 0;
                PotionEffect s = KuroEffects.sleep == null ? null : p.getActivePotionEffect(KuroEffects.sleep);
                PotionEffect f = KuroEffects.flashbanged == null ? null : p.getActivePotionEffect(KuroEffects.flashbanged);
                if (f != null) {
                    alpha = Math.min(1.0F, f.getDuration() / 100.0F);
                    rgb = 16777215;
                } else if (s != null) {
                    alpha = Math.min(0.92F, 0.6F + 0.3F * (float)Math.sin(p.ticksExisted * 0.05));
                }

                if (!(alpha <= 0.0F)) {
                    int a = (int)(alpha * 255.0F) << 24;
                    GL11.glPushAttrib(24576);
                    Gui.drawRect(0, 0, e.resolution.getScaledWidth(), e.resolution.getScaledHeight(), a | rgb);
                    GL11.glPopAttrib();
                }
            }
        }
    }

    @SubscribeEvent
    public void onFog(RenderFogEvent e) {
        RenderFog ev = new RenderFog(
            e.fogMode < 0 ? FogMode.FOG_SKY : FogMode.FOG_TERRAIN, MClientImpl.camera(), e.renderPartialTicks, e.farPlaneDistance * 0.75F, e.farPlaneDistance
        );
        if (MinecraftForge.EVENT_BUS.post(ev)) {
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, ev.getNearPlaneDistance());
            GL11.glFogf(2916, ev.getFarPlaneDistance());
        }
    }

    @SubscribeEvent
    public void onFogColor(FogColors e) {
        ComputeFogColor ev = new ComputeFogColor(MClientImpl.camera(), e.renderPartialTicks, e.red, e.green, e.blue);
        MinecraftForge.EVENT_BUS.post(ev);
        e.red = ev.getRed();
        e.green = ev.getGreen();
        e.blue = ev.getBlue();
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent e) {
        if (e.phase == Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            boolean down = mc.gameSettings.keyBindAttack.getIsKeyPressed();
            if (down
                && !this.attackWasDown
                && mc.thePlayer != null
                && mc.currentScreen == null
                && (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit == MovingObjectType.MISS)) {
                MinecraftForge.EVENT_BUS.post(new LeftClickEmpty(mc.thePlayer));
            }

            this.attackWasDown = down;
        }
    }

    @SubscribeEvent
    public void onWorldLoad(Load e) {
        if (e.world instanceof WorldClient) {
            DimensionSpecialEffects fx = DimensionSpecialEffectsManager.getForType(Dimensions.key(e.world.provider.dimensionId).location());
            if (fx != DimensionSpecialEffects.OVERWORLD) {
                e.world.provider.setSkyRenderer(new ClientEventBridge.SkyHook(fx));
            }
        }
    }

    static final class SkyHook extends IRenderHandler {
        private final DimensionSpecialEffects fx;
        private boolean inside;

        SkyHook(DimensionSpecialEffects fx) {
            this.fx = fx;
        }

        public void render(float partialTicks, WorldClient world, Minecraft mc) {
            if (!this.inside) {
                this.inside = true;

                try {
                    world.provider.setSkyRenderer(null);
                    mc.renderGlobal.renderSky(partialTicks);
                } finally {
                    world.provider.setSkyRenderer(this);
                    this.inside = false;
                }

                GL11.glPushMatrix();
                GL11.glPushAttrib(24832);

                try {
                    this.fx.renderSky(world, (int)world.getTotalWorldTime(), partialTicks, new PoseStack(), MClientImpl.camera(), new Matrix4f(), false, () -> {});
                } catch (Throwable var12) {
                } finally {
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}
