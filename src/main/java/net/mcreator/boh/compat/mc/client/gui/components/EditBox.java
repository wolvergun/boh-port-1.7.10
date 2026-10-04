package net.mcreator.boh.compat.mc.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class EditBox extends GuiTextField {
    public EditBox(Object font, int x, int y, int w, int h, Object message) {
        super(Minecraft.getMinecraft().fontRenderer, x, y, w, h);
    }

    public void setValue(String s) {
        this.setText(s);
    }

    public String getValue() {
        return this.getText();
    }

    public void setSuggestion(String s) {
    }
}
