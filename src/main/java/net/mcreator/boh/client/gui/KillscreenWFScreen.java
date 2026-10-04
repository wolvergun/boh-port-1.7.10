package net.mcreator.boh.client.gui;

import java.util.HashMap;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.components.Button;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.AbstractContainerScreen;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.boh.network.KillscreenWFButtonMessage;
import net.mcreator.boh.world.inventory.KillscreenWFMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class KillscreenWFScreen extends AbstractContainerScreen<KillscreenWFMenu> {
    private static final HashMap<String, Object> guistate = KillscreenWFMenu.guistate;
    private final World world;
    private final int x;
    private final int y;
    private final int z;
    private final EntityPlayer entity;
    Button button_empty;
    Button button_empty1;

    public KillscreenWFScreen(KillscreenWFMenu container, InventoryPlayer inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        M.renderBackground(this, guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        M.renderTooltip(this, guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            M.closeContainer(M.player(this.minecraft));
            return true;
        } else {
            return super.keyPressed(key, b, c);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        M.drawString(guiGraphics, this.font, Component.translatable("gui.boh.killscreen_wf.label_would_you_like_to_kill_white_fac"), -3, 61, -1, false);
        M.drawString(guiGraphics, this.font, Component.translatable("gui.boh.killscreen_wf.label_this_is_permanent"), 42, 106, -3407872, false);
    }

    @Override
    public void init() {
        super.init();
        this.button_empty = M.bounds(Button.builder(Component.translatable("gui.boh.killscreen_wf.button_empty"), e -> {
            M.sendToServer(BohMod.PACKET_HANDLER, new KillscreenWFButtonMessage(0, this.x, this.y, this.z));
            KillscreenWFButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z);
        }), this.leftPos + 42, this.topPos + 79, 25, 20).build();
        guistate.put("button:button_empty", this.button_empty);
        M.addRenderableWidget(this, this.button_empty);
        this.button_empty1 = M.bounds(Button.builder(Component.translatable("gui.boh.killscreen_wf.button_empty1"), e -> {
            M.sendToServer(BohMod.PACKET_HANDLER, new KillscreenWFButtonMessage(1, this.x, this.y, this.z));
            KillscreenWFButtonMessage.handleButtonAction(this.entity, 1, this.x, this.y, this.z);
        }), this.leftPos + 108, this.topPos + 81, 25, 20).build();
        guistate.put("button:button_empty1", this.button_empty1);
        M.addRenderableWidget(this, this.button_empty1);
    }
}
