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
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.mcreator.boh.compat.forge.client.event.RegisterParticleProvidersEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;

public class BohModParticles {

    @SubscribeEvent
    public void registerParticles(RegisterParticleProvidersEvent event) {
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.MOTHMANSONICATTACKPARTICLE.get(), MothmansonicattackparticleParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.GRAY_PARTICLE.get(), GrayParticleParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.PAINTED_SWORD_SWEEP.get(), PaintedSwordSweepParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.CONFETTI.get(), ConfettiParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.BLOOD_FALL.get(), BloodFallParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.ANGLER_PARTICLE.get(), AnglerParticleParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.RIFT_TEAR_PARTICLE.get(), RiftTearParticleParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.BITE_PARTICLE.get(), BiteParticleParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.GOLD_HIT.get(), GoldHitParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.REND_SWEEP.get(), RendSweepParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.HYPNO_SHOT.get(), HypnoShotParticle::provider);
        M.registerSpriteSet(event, (ParticleType) BohModParticleTypes.MIMICRY_SWEEP.get(), MimicrySweepParticle::provider);
    }
}
