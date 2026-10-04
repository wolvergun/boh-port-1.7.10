package net.mcreator.boh.init;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.entity.AnglerEntity;
import net.mcreator.boh.entity.AoOniEntity;
import net.mcreator.boh.entity.BaldiEntity;
import net.mcreator.boh.entity.BenDrownedEntity;
import net.mcreator.boh.entity.BigDaddyEntity;
import net.mcreator.boh.entity.BloodwaveEntity;
import net.mcreator.boh.entity.BoiledOneEntity;
import net.mcreator.boh.entity.BookSimonEntity;
import net.mcreator.boh.entity.BruceEntity;
import net.mcreator.boh.entity.CartoonCatEntity;
import net.mcreator.boh.entity.CelebiEntity;
import net.mcreator.boh.entity.ChestbursterEntity;
import net.mcreator.boh.entity.ChuckyEntity;
import net.mcreator.boh.entity.ChuckyGrabEntity;
import net.mcreator.boh.entity.DeathHorseEntity;
import net.mcreator.boh.entity.DecoyDogEntity;
import net.mcreator.boh.entity.DeerEntity;
import net.mcreator.boh.entity.DeerMimicEntity;
import net.mcreator.boh.entity.DemogorgonEntity;
import net.mcreator.boh.entity.DemonEntity;
import net.mcreator.boh.entity.EntityWellEntity;
import net.mcreator.boh.entity.ExeMonitorEntity;
import net.mcreator.boh.entity.EyelessJackEntity;
import net.mcreator.boh.entity.FacehuggerEntity;
import net.mcreator.boh.entity.FamineHorseEntity;
import net.mcreator.boh.entity.FigureEntity;
import net.mcreator.boh.entity.FlatwoodsMonsterEntity;
import net.mcreator.boh.entity.FlowersEntity;
import net.mcreator.boh.entity.FreddyKruegerEntity;
import net.mcreator.boh.entity.FresnoNightcrawlerEntity;
import net.mcreator.boh.entity.FuwattiEntity;
import net.mcreator.boh.entity.GasterEntity;
import net.mcreator.boh.entity.GhostEntity;
import net.mcreator.boh.entity.GhostfaceDecoyEntity;
import net.mcreator.boh.entity.GhostfaceEntity;
import net.mcreator.boh.entity.GojiEntity;
import net.mcreator.boh.entity.GoldHostileEntity;
import net.mcreator.boh.entity.GoldLostEntity;
import net.mcreator.boh.entity.GraftonMonsterEntity;
import net.mcreator.boh.entity.GrannyEntity;
import net.mcreator.boh.entity.GrayAlienEntity;
import net.mcreator.boh.entity.HypnoEntity;
import net.mcreator.boh.entity.InkDemonEntity;
import net.mcreator.boh.entity.JackalopeEntity;
import net.mcreator.boh.entity.JamesSunderlandEntity;
import net.mcreator.boh.entity.JaneTheKillerEntity;
import net.mcreator.boh.entity.JasonMaskEntity;
import net.mcreator.boh.entity.JasonVoorheesEntity;
import net.mcreator.boh.entity.JeffTheKillerEntity;
import net.mcreator.boh.entity.KirieHimuroEntity;
import net.mcreator.boh.entity.KrampusEntity;
import net.mcreator.boh.entity.KrasueEntity;
import net.mcreator.boh.entity.LaughingJackEntity;
import net.mcreator.boh.entity.LeatherfaceEntity;
import net.mcreator.boh.entity.LifeformEntity;
import net.mcreator.boh.entity.LightHeadEntity;
import net.mcreator.boh.entity.LittleSisterEntity;
import net.mcreator.boh.entity.MXEntity;
import net.mcreator.boh.entity.MartianDroneEntity;
import net.mcreator.boh.entity.MichaelDaviesEntity;
import net.mcreator.boh.entity.MichaelMyersEntity;
import net.mcreator.boh.entity.MothlingEntity;
import net.mcreator.boh.entity.MothmanEntity;
import net.mcreator.boh.entity.NPC000Entity;
import net.mcreator.boh.entity.NecoArcEntity;
import net.mcreator.boh.entity.NemesisEntity;
import net.mcreator.boh.entity.NewbornEntity;
import net.mcreator.boh.entity.NothingThereEntity;
import net.mcreator.boh.entity.PatrickBatemanEntity;
import net.mcreator.boh.entity.PestilenceHorseEntity;
import net.mcreator.boh.entity.PhantomBBEntity;
import net.mcreator.boh.entity.PhantomChicaEntity;
import net.mcreator.boh.entity.PhantomFoxyEntity;
import net.mcreator.boh.entity.PhantomFreddyEntity;
import net.mcreator.boh.entity.PhantomMangleEntity;
import net.mcreator.boh.entity.PhantomPuppetEntity;
import net.mcreator.boh.entity.PredatorEntity;
import net.mcreator.boh.entity.PredatorSightEntity;
import net.mcreator.boh.entity.PumpkinPlayerEntity;
import net.mcreator.boh.entity.PyramidHeadEntity;
import net.mcreator.boh.entity.REDEntity;
import net.mcreator.boh.entity.RaatmaEntity;
import net.mcreator.boh.entity.RabbidEntity;
import net.mcreator.boh.entity.RakeEntity;
import net.mcreator.boh.entity.RatEntity;
import net.mcreator.boh.entity.RatazanaEntity;
import net.mcreator.boh.entity.RexyEntity;
import net.mcreator.boh.entity.RollingGiantEntity;
import net.mcreator.boh.entity.RussianSleepExperimentEntity;
import net.mcreator.boh.entity.SadakoEntity;
import net.mcreator.boh.entity.SaucerEntity;
import net.mcreator.boh.entity.SawRunnerEntity;
import net.mcreator.boh.entity.ScissormanEntity;
import net.mcreator.boh.entity.SeedEaterEntity;
import net.mcreator.boh.entity.SimonhenrikssonEntity;
import net.mcreator.boh.entity.SirenHeadEntity;
import net.mcreator.boh.entity.SixEntity;
import net.mcreator.boh.entity.SlenderManEntity;
import net.mcreator.boh.entity.SmileDogEntity;
import net.mcreator.boh.entity.SonicBoomEntity;
import net.mcreator.boh.entity.SonicExeEntity;
import net.mcreator.boh.entity.SotirisEntity;
import net.mcreator.boh.entity.SouichiEntity;
import net.mcreator.boh.entity.Specimen9BossEntity;
import net.mcreator.boh.entity.SpringtrapEntity;
import net.mcreator.boh.entity.SquidwardDoomedEntity;
import net.mcreator.boh.entity.SquidwardEntity;
import net.mcreator.boh.entity.StiltwalkerEntity;
import net.mcreator.boh.entity.Subject3Entity;
import net.mcreator.boh.entity.SuicideMouseEntity;
import net.mcreator.boh.entity.SwampMonsterEntity;
import net.mcreator.boh.entity.TailsDollEntity;
import net.mcreator.boh.entity.TailsEntity;
import net.mcreator.boh.entity.TakenHandsEntity;
import net.mcreator.boh.entity.TakenPillarEntity;
import net.mcreator.boh.entity.TamedRatEntity;
import net.mcreator.boh.entity.TaperecorderbaldiEntity;
import net.mcreator.boh.entity.TheThingDogEntity;
import net.mcreator.boh.entity.TheThingVillagerEntity;
import net.mcreator.boh.entity.TinkyTankEntity;
import net.mcreator.boh.entity.TinkyWinkyEntity;
import net.mcreator.boh.entity.TormentPyramidEntity;
import net.mcreator.boh.entity.TrimmingEntity;
import net.mcreator.boh.entity.UnicornEntity;
import net.mcreator.boh.entity.Unown10Entity;
import net.mcreator.boh.entity.Unown11Entity;
import net.mcreator.boh.entity.Unown12Entity;
import net.mcreator.boh.entity.Unown13Entity;
import net.mcreator.boh.entity.Unown14Entity;
import net.mcreator.boh.entity.Unown15Entity;
import net.mcreator.boh.entity.Unown1Entity;
import net.mcreator.boh.entity.Unown2Entity;
import net.mcreator.boh.entity.Unown3Entity;
import net.mcreator.boh.entity.Unown4Entity;
import net.mcreator.boh.entity.Unown5Entity;
import net.mcreator.boh.entity.Unown6Entity;
import net.mcreator.boh.entity.Unown7Entity;
import net.mcreator.boh.entity.Unown8Entity;
import net.mcreator.boh.entity.Unown9Entity;
import net.mcreator.boh.entity.VampireBatEntity;
import net.mcreator.boh.entity.VampireEntity;
import net.mcreator.boh.entity.VitaMimicEntity;
import net.mcreator.boh.entity.WarHorseEntity;
import net.mcreator.boh.entity.WendigoEntity;
import net.mcreator.boh.entity.WerewolfEntity;
import net.mcreator.boh.entity.WhitefaceEntity;
import net.mcreator.boh.entity.WhitefaceFriendlyEntity;
import net.mcreator.boh.entity.WillowispEntity;
import net.mcreator.boh.entity.XenomorphEntity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class EntityAnimationFactory {
    @SubscribeEvent
    public void onEntityTick(LivingUpdateEvent event) {
        if (event != null && M.getEntity(event) != null) {
            if (M.getEntity(event) instanceof BenDrownedEntity syncable) {
                String animation = syncable.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncable.setAnimation("undefined");
                    syncable.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof JeffTheKillerEntity syncablex) {
                String animation = syncablex.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablex.setAnimation("undefined");
                    syncablex.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SirenHeadEntity syncablexx) {
                String animation = syncablexx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexx.setAnimation("undefined");
                    syncablexx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PyramidHeadEntity syncablexxx) {
                String animation = syncablexxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxx.setAnimation("undefined");
                    syncablexxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SawRunnerEntity syncablexxxx) {
                String animation = syncablexxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxx.setAnimation("undefined");
                    syncablexxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof XenomorphEntity syncablexxxxx) {
                String animation = syncablexxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxx.setAnimation("undefined");
                    syncablexxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof FacehuggerEntity syncablexxxxxx) {
                String animation = syncablexxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxx.setAnimation("undefined");
                    syncablexxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof ChestbursterEntity syncablexxxxxxx) {
                String animation = syncablexxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxx.setAnimation("undefined");
                    syncablexxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof LifeformEntity syncablexxxxxxxx) {
                String animation = syncablexxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SlenderManEntity syncablexxxxxxxxx) {
                String animation = syncablexxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof DemogorgonEntity syncablexxxxxxxxxx) {
                String animation = syncablexxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof GoldLostEntity syncablexxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof GoldHostileEntity syncablexxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown1Entity syncablexxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown2Entity syncablexxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown3Entity syncablexxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown4Entity syncablexxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown5Entity syncablexxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown6Entity syncablexxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown7Entity syncablexxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown8Entity syncablexxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown9Entity syncablexxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown10Entity syncablexxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown11Entity syncablexxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown12Entity syncablexxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown13Entity syncablexxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown14Entity syncablexxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Unown15Entity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof RakeEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof NecoArcEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof WhitefaceEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof MichaelDaviesEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SmileDogEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof WhitefaceFriendlyEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof DecoyDogEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SpringtrapEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof AoOniEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof RexyEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SonicExeEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof ExeMonitorEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SixEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof MothmanEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof RaatmaEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof RatEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SeedEaterEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof GasterEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof JamesSunderlandEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof GrayAlienEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SaucerEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SotirisEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof RatazanaEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SouichiEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SimonhenrikssonEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof BookSimonEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof MichaelMyersEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof EyelessJackEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof WendigoEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof NemesisEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof JasonVoorheesEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof JasonMaskEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof BigDaddyEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof LightHeadEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof LittleSisterEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof DeerEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof DeerMimicEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof KrampusEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof GrannyEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof StiltwalkerEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof REDEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SadakoEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof EntityWellEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Specimen9BossEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TakenPillarEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TakenHandsEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TormentPyramidEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof RollingGiantEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof FresnoNightcrawlerEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof MartianDroneEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof ChuckyEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof ChuckyGrabEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof GhostfaceEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof GhostfaceDecoyEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof FreddyKruegerEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof BoiledOneEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof RabbidEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof VampireEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof VampireBatEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof WerewolfEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof GhostEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof DemonEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SwampMonsterEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof FuwattiEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof CelebiEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TamedRatEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PhantomFreddyEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PhantomFoxyEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PhantomBBEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PhantomPuppetEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PhantomMangleEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PhantomChicaEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TailsEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof GojiEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof InkDemonEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PredatorEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PredatorSightEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof AnglerEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SonicBoomEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation("undefined");
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TinkyWinkyEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof NewbornEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof CartoonCatEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof BaldiEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof FigureEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TaperecorderbaldiEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof WillowispEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof WarHorseEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof FamineHorseEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof DeathHorseEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PestilenceHorseEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof UnicornEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof JackalopeEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof FlatwoodsMonsterEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof VitaMimicEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TrimmingEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof Subject3Entity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PatrickBatemanEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof LeatherfaceEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof MXEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof ScissormanEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof HypnoEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TinkyTankEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof RussianSleepExperimentEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof GraftonMonsterEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof PumpkinPlayerEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof KrasueEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof KirieHimuroEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof LaughingJackEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof JaneTheKillerEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TailsDollEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SuicideMouseEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SquidwardEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof SquidwardDoomedEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TheThingVillagerEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof TheThingDogEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof BloodwaveEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof BruceEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof MothlingEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof NothingThereEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof FlowersEntity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }

            if (M.getEntity(event) instanceof NPC000Entity syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                )
             {
                String animation = syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setAnimation(
                        "undefined"
                    );
                    syncablexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.animationprocedure = animation;
                }
            }
        }
    }
}
