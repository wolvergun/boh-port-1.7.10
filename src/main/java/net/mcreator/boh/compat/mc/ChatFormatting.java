package net.mcreator.boh.compat.mc;

import net.minecraft.util.EnumChatFormatting;

public enum ChatFormatting {
    BLACK,
    DARK_BLUE,
    DARK_GREEN,
    DARK_AQUA,
    DARK_RED,
    DARK_PURPLE,
    GOLD,
    GRAY,
    DARK_GRAY,
    BLUE,
    GREEN,
    AQUA,
    RED,
    LIGHT_PURPLE,
    YELLOW,
    WHITE,
    OBFUSCATED,
    BOLD,
    STRIKETHROUGH,
    UNDERLINE,
    ITALIC,
    RESET;

    public EnumChatFormatting toVanilla() {
        return EnumChatFormatting.valueOf(this.name());
    }

    @Override
    public String toString() {
        return this.toVanilla().toString();
    }

    public static String stripFormatting(String s) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(s);
    }

    public static ChatFormatting getByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception var2) {
            return null;
        }
    }

    public boolean isColor() {
        return this.ordinal() < 16;
    }

    public String getName() {
        return this.name().toLowerCase();
    }
}
