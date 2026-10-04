package net.mcreator.boh.init;

import net.mcreator.boh.enchantment.RendEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.forge.registries.RegistryObject;

public class BohModEnchantments {

    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "boh");

    public static final RegistryObject<Enchantment> REND = REGISTRY.register("rend", () -> new RendEnchantment());
}
