package net.mcreator.boh.init;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.mcreator.boh.compat.mc.world.item.alchemy.BrewPotion;
import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.forge.registries.RegistryObject;
import net.mcreator.boh.compat.M;

public class BohModPotions {

    public static final DeferredRegister<BrewPotion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, "boh");

    public static final RegistryObject<BrewPotion> LYCANTHROPY_POTIONS = REGISTRY.register("lycanthropy_potions", () -> new BrewPotion(new PotionEffect[] { M.new_PotionEffect((Potion) BohModMobEffects.LYCANTHROPY.get(), 6000, 0, false, true) }));

    public static final RegistryObject<BrewPotion> VAMPIRISM_POTIONS = REGISTRY.register("vampirism_potions", () -> new BrewPotion(new PotionEffect[] { M.new_PotionEffect((Potion) BohModMobEffects.VAMPIRISM.get(), 6000, 0, false, true) }));
}
