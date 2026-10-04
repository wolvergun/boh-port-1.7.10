package net.mcreator.boh.compat.mc.network.chat;

import net.mcreator.boh.compat.mc.ChatFormatting;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

/** 1.20 text component backed by a 1.7.10 {@link IChatComponent}. */
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
        for (int i = 0; i < args.length; i++)
            converted[i] = args[i] instanceof Component ? ((Component) args[i]).handle : args[i];
        return new MutableComponent(new ChatComponentTranslation(key, converted));
    }

    public static MutableComponent empty() {
        return literal("");
    }

    public static MutableComponent of(IChatComponent c) {
        return new MutableComponent(c == null ? new ChatComponentText("") : c);
    }

    public static MutableComponent nullToEmpty(String s) {
        return literal(s == null ? "" : s);
    }

    public IChatComponent toVanilla() {
        return handle;
    }

    public String getString() {
        return handle.getUnformattedText();
    }

    public String getFormattedText() {
        // IChatComponent.getFormattedText is client-only
        return cpw.mods.fml.common.FMLCommonHandler.instance().getSide().isClient() ? net.mcreator.boh.compat.MClientImpl.formattedText(handle)
            : handle.getUnformattedText();
    }

    public MutableComponent copy() {
        return new MutableComponent(handle.createCopy());
    }

    public MutableComponent plainCopy() {
        return copy();
    }

    public MutableComponent withStyle(ChatFormatting... formats) {
        for (ChatFormatting f : formats) apply(f.toVanilla());
        return this instanceof MutableComponent ? (MutableComponent) this : copy();
    }

    public MutableComponent withStyle(ChatFormatting format) {
        apply(format.toVanilla());
        return this instanceof MutableComponent ? (MutableComponent) this : copy();
    }

    private void apply(EnumChatFormatting f) {
        if (f == null) return;
        switch (f) {
            case BOLD:
                handle.getChatStyle().setBold(true);
                break;
            case ITALIC:
                handle.getChatStyle().setItalic(true);
                break;
            case UNDERLINE:
                handle.getChatStyle().setUnderlined(true);
                break;
            case STRIKETHROUGH:
                handle.getChatStyle().setStrikethrough(true);
                break;
            case OBFUSCATED:
                handle.getChatStyle().setObfuscated(true);
                break;
            case RESET:
                break;
            default:
                handle.getChatStyle().setColor(f);
        }
    }

    public MutableComponent append(Component other) {
        handle.appendSibling(other.handle);
        return this instanceof MutableComponent ? (MutableComponent) this : copy();
    }

    public MutableComponent append(String text) {
        handle.appendText(text);
        return this instanceof MutableComponent ? (MutableComponent) this : copy();
    }

    @Override
    public String toString() {
        return getString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Component && ((Component) o).handle.equals(handle);
    }

    @Override
    public int hashCode() {
        return handle.hashCode();
    }
}
