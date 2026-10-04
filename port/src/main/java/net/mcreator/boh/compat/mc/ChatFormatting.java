package net.mcreator.boh.compat.mc;

import net.minecraft.util.EnumChatFormatting;

/** 1.20 ChatFormatting mapped onto 1.7.10 {@link EnumChatFormatting}. */
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
        return EnumChatFormatting.valueOf(name());
    }

    @Override
    public String toString() {
        return toVanilla().toString();
    }

    public static String stripFormatting(String s) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(s);
    }

    public static ChatFormatting getByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isColor() {
        return ordinal() < 16;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
