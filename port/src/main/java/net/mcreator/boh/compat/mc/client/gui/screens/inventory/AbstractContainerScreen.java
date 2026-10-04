package net.mcreator.boh.compat.mc.client.gui.screens.inventory;

import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.components.Button;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.input.Keyboard;

/** 1.20 AbstractContainerScreen on 1.7.10 GuiContainer. */
public abstract class AbstractContainerScreen<T extends AbstractContainerMenu> extends GuiContainer {

    protected final T menu;
    protected final InventoryPlayer playerInventory;
    protected final Component title;
    protected int imageWidth = 176, imageHeight = 166;
    protected int leftPos, topPos;
    protected int titleLabelX = 8, titleLabelY = 6, inventoryLabelX = 8, inventoryLabelY;
    protected FontRenderer font;
    protected Minecraft minecraft;
    private final GuiGraphics graphics = new GuiGraphics();

    public AbstractContainerScreen(T menu, InventoryPlayer inv, Component title) {
        super(menu);
        this.menu = menu;
        this.playerInventory = inv;
        this.title = title;
        inventoryLabelY = imageHeight - 94;
    }

    public T getMenu() {
        return menu;
    }

    public Component getTitle() {
        return title;
    }

    // ------------------------------------------------------------------ 1.20 API

    protected void init() {}

    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        super.drawScreen(mouseX, mouseY, partialTick);
    }

    protected abstract void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY);

    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        g.drawString(font, title, titleLabelX, titleLabelY, 0x404040, false);
    }

    public void renderBackground(GuiGraphics g) {
        drawDefaultBackground();
    }

    public void renderTooltip(GuiGraphics g, int mouseX, int mouseY) {}

    public boolean keyPressed(int key, int scanCode, int modifiers) {
        int lwjgl = key == 256 ? Keyboard.KEY_ESCAPE : scanCode;
        super.keyTyped('\0', lwjgl);
        return true;
    }

    public <W extends GuiButton> W addRenderableWidget(W w) {
        w.id = buttonList.size();
        buttonList.add(w);
        return w;
    }

    public <W> W addWidget(W w) {
        if (w instanceof GuiButton) addRenderableWidget((GuiButton) w);
        return w;
    }

    public void onClose() {
        if (mc.thePlayer != null) mc.thePlayer.closeScreen();
    }

    public void containerTick() {}

    public boolean isPauseScreen() {
        return false;
    }

    // ------------------------------------------------------------------ 1.7.10 bridge

    @Override
    public void initGui() {
        xSize = imageWidth;
        ySize = imageHeight;
        super.initGui();
        buttonList.clear();
        leftPos = guiLeft;
        topPos = guiTop;
        font = fontRendererObj;
        minecraft = mc;
        init();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
        renderBg(graphics, partialTick, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        renderLabels(graphics, mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char c, int key) {
        int glfw = key == Keyboard.KEY_ESCAPE ? 256 : key;
        keyPressed(glfw, key, 0);
    }

    @Override
    protected void actionPerformed(GuiButton b) {
        if (b instanceof Button) ((Button) b).onPress();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        containerTick();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return isPauseScreen();
    }
}
