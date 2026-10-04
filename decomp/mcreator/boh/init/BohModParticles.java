package net.mcreator.boh.init;

import net.mcreator.boh.client.particle.AnglerParticleParticle;
import net.mcreator.boh.client.particle.BiteParticleParticle;
import net.mcreator.boh.client.particle.BloodFallParticle;
import net.mcreator.boh.client.particle.ConfettiParticle;
import net.mcreator.boh.client.particle.GoldHitParticle;
import net.mcreator.boh.client.particle.GrayParticleParticle;
import net.mcreator.boh.client.particle.HypnoShotParticle;
import net.mcreator.boh.client.particle.MimicrySweepParticle;
import net.mcreator.boh.client.particle.MothmansonicattackparticleParticle;
import net.mcreator.boh.client.particle.PaintedSwordSweepParticle;
import net.mcreator.boh.client.particle.RendSweepParticle;
import net.mcreator.boh.client.particle.RiftTearParticleParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class BohModParticles {
   @SubscribeEvent
   public static void registerParticles(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)BohModParticleTypes.MOTHMANSONICATTACKPARTICLE.get(), MothmansonicattackparticleParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.GRAY_PARTICLE.get(), GrayParticleParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.PAINTED_SWORD_SWEEP.get(), PaintedSwordSweepParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.CONFETTI.get(), ConfettiParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.BLOOD_FALL.get(), BloodFallParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.ANGLER_PARTICLE.get(), AnglerParticleParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.RIFT_TEAR_PARTICLE.get(), RiftTearParticleParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.BITE_PARTICLE.get(), BiteParticleParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.GOLD_HIT.get(), GoldHitParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.REND_SWEEP.get(), RendSweepParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.HYPNO_SHOT.get(), HypnoShotParticle::provider);
      event.registerSpriteSet((ParticleType)BohModParticleTypes.MIMICRY_SWEEP.get(), MimicrySweepParticle::provider);
   }
}
