package net.mcreator.boh.compat.mc.network.chat;

import net.mcreator.boh.compat.mc.ChatFormatting;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class Component {
    protected final IChatComponent handle;

    protected Component(IChatComponent handle) {
        this.handle = handle;
    }

    public static MutableComponent literal(String text) {
        return new MutableComponent(new ChatComponentText(text == null ? "" : text));
    }

    public static MutableComponent translatable(String key, Object... args) {
        Object[] converted = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            converted[i] = args[i] instanceof Component ? ((Component)args[i]).handle : args[i];
        }

        return new MutableComponent(new ChatComponentTranslation(key, converted));
    }

    public static MutableComponent empty() {
        return literal("");
    }

    public static MutableComponent of(IChatComponent c) {
        return new MutableComponent((IChatComponent)(c == null ? new ChatComponentText("") : c));
    }

    public static MutableComponent nullToEmpty(String s) {
        return literal(s == null ? "" : s);
    }

    public IChatComponent toVanilla() {
        return this.handle;
    }

    public String getString() {
        return this.handle.getUnformattedText();
    }

    public String getFormattedText() {
        return this.handle.getFormattedText();
    }

    public MutableComponent copy() {
        return new MutableComponent(this.handle.createCopy());
    }

    public MutableComponent plainCopy() {
        return this.copy();
    }

    public MutableComponent withStyle(ChatFormatting... formats) {
        for (ChatFormatting f : formats) {
            this.apply(f.toVanilla());
        }

        return this instanceof MutableComponent ? (MutableComponent)this : this.copy();
    }

    public MutableComponent withStyle(ChatFormatting format) {
        this.apply(format.toVanilla());
        return this instanceof MutableComponent ? (MutableComponent)this : this.copy();
    }

    private void apply(EnumChatFormatting f) {
        if (f != null) {
            switch (f) {
                case BOLD:
                    this.handle.getChatStyle().setBold(true);
                    break;
                case ITALIC:
                    this.handle.getChatStyle().setItalic(true);
                    break;
                case UNDERLINE:
                    this.handle.getChatStyle().setUnderlined(true);
                    break;
                case STRIKETHROUGH:
                    this.handle.getChatStyle().setStrikethrough(true);
                    break;
                case OBFUSCATED:
                    this.handle.getChatStyle().setObfuscated(true);
                case RESET:
                    break;
                default:
                    this.handle.getChatStyle().setColor(f);
            }
        }
    }

    public MutableComponent append(Component other) {
        this.handle.appendSibling(other.handle);
        return this instanceof MutableComponent ? (MutableComponent)this : this.copy();
    }

    public MutableComponent append(String text) {
        this.handle.appendText(text);
        return this instanceof MutableComponent ? (MutableComponent)this : this.copy();
    }

    @Override
    public String toString() {
        return this.getString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Component && ((Component)o).handle.equals(this.handle);
    }

    @Override
    public int hashCode() {
        return this.handle.hashCode();
    }
}
