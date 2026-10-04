package net.mcreator.boh.client.gui;

import java.util.HashMap;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.components.ImageButton;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.AbstractContainerScreen;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.boh.world.inventory.WitnessUIMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class WitnessUIScreen extends AbstractContainerScreen<WitnessUIMenu> {
    private static final HashMap<String, Object> guistate = WitnessUIMenu.guistate;
    private final World world;
    private final int x;
    private final int y;
    private final int z;
    private final EntityPlayer entity;
    ImageButton imagebutton_smilejpeg;

    public WitnessUIScreen(WitnessUIMenu container, InventoryPlayer inventory, Component text) {
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
    }

    @Override
    public void init() {
        super.init();
        this.imagebutton_smilejpeg = new ImageButton(
            this.leftPos + -671,
            this.topPos + -349,
            1063,
            743,
            0,
            0,
            743,
            new ResourceLocation("boh:textures/screens/atlas/imagebutton_smilejpeg.png"),
            1063,
            1486,
            e -> {}
        );
        guistate.put("button:imagebutton_smilejpeg", this.imagebutton_smilejpeg);
        M.addRenderableWidget(this, this.imagebutton_smilejpeg);
    }
}
