package net.mcreator.boh.compat.world;

import net.mcreator.boh.compat.registry.LegacyIds;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/** 1.20 item NBT ({id:"ns:name", Count:b, tag:{}}) to 1.7.10 stacks. */
public final class ItemNbt {

    private ItemNbt() {}

    public static ItemStack toStack(NBTTagCompound it) {
        String id = it.getString("id");
        int count = it.hasKey("Count") ? it.getByte("Count") : it.hasKey("count") ? it.getInteger("count") : 1;
        Object o = Item.itemRegistry.getObject(id);
        ItemStack s = null;
        if (o instanceof Item) s = new ItemStack((Item) o, count);
        else {
            Block b = Block.getBlockFromName(id);
            if (b != null && b != Blocks.air) s = new ItemStack(b, count);
            else {
                LegacyIds.Target t = LegacyIds.target(id);
                Item i = t == null ? null : t.item();
                if (i != null) s = new ItemStack(i, count, t.meta);
            }
        }
        if (s != null && it.hasKey("tag")) {
            NBTTagCompound tag = (NBTTagCompound) it.getCompoundTag("tag").copy();
            if (tag.hasKey("Damage")) {
                s.setItemDamage(tag.getInteger("Damage"));
                tag.removeTag("Damage");
            }
            if (!tag.hasNoTags()) s.setTagCompound(tag);
        }
        return s;
    }

    /** Rewrites a list of 1.20 item compounds in place to 1.7.10 compounds (numeric id). */
    public static void convertItemList(NBTTagCompound parent, String key) {
        if (!parent.hasKey(key)) return;
        NBTTagList in = parent.getTagList(key, 10), out = new NBTTagList();
        for (int i = 0; i < in.tagCount(); i++) {
            NBTTagCompound it = in.getCompoundTagAt(i);
            ItemStack s = toStack(it);
            if (s == null) continue;
            NBTTagCompound c = new NBTTagCompound();
            c.setByte("Slot", it.getByte("Slot"));
            s.writeToNBT(c);
            out.appendTag(c);
        }
        parent.setTag(key, out);
    }

    /** Text of a 1.20 JSON text component ('{"text":"x"}' or '"x"'). */
    public static String plainText(String json) {
        if (json == null || json.isEmpty()) return "";
        try {
            com.google.gson.JsonElement e = new com.google.gson.JsonParser().parse(json);
            if (e.isJsonPrimitive()) return e.getAsString();
            if (e.isJsonObject()) {
                StringBuilder sb = new StringBuilder();
                com.google.gson.JsonObject o = e.getAsJsonObject();
                if (o.has("text")) sb.append(o.get("text").getAsString());
                if (o.has("extra")) for (com.google.gson.JsonElement x : o.getAsJsonArray("extra"))
                    sb.append(x.isJsonPrimitive() ? x.getAsString() : x.getAsJsonObject().has("text") ? x.getAsJsonObject().get("text").getAsString() : "");
                return sb.toString();
            }
        } catch (Exception ignored) {}
        return json;
    }
}
