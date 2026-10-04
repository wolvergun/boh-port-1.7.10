package net.mcreator.boh.init;

import net.mcreator.boh.enchantment.RendEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BohModEnchantments {
   public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "boh");
   public static final RegistryObject<Enchantment> REND = REGISTRY.register("rend", () -> new RendEnchantment());
}
