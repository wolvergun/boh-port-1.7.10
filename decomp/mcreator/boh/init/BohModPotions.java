package net.mcreator.boh.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BohModPotions {
   public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, "boh");
   public static final RegistryObject<Potion> LYCANTHROPY_POTIONS = REGISTRY.register(
      "lycanthropy_potions",
      () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)BohModMobEffects.LYCANTHROPY.get(), 6000, 0, false, true)})
   );
   public static final RegistryObject<Potion> VAMPIRISM_POTIONS = REGISTRY.register(
      "vampirism_potions", () -> new Potion(new MobEffectInstance[]{new MobEffectInstance((MobEffect)BohModMobEffects.VAMPIRISM.get(), 6000, 0, false, true)})
   );
}
