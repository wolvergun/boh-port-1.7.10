package net.mcreator.boh.init;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.event.entity.EntityAttributeCreationEvent;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.forge.registries.RegistryObject;
import net.mcreator.boh.compat.mc.world.entity.Builder;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.MobCategory;
import net.mcreator.boh.entity.AnglerEntity;
import net.mcreator.boh.entity.AoOniEntity;
import net.mcreator.boh.entity.BaldiEntity;
import net.mcreator.boh.entity.BenDrownedEntity;
import net.mcreator.boh.entity.BigDaddyEntity;
import net.mcreator.boh.entity.BloodSpillEntity;
import net.mcreator.boh.entity.BloodwaveEntity;
import net.mcreator.boh.entity.BoiledOneEntity;
import net.mcreator.boh.entity.BookSimonEntity;
import net.mcreator.boh.entity.BruceEntity;
import net.mcreator.boh.entity.CartoonCatEntity;
import net.mcreator.boh.entity.CelebiEntity;
import net.mcreator.boh.entity.ChestbursterEntity;
import net.mcreator.boh.entity.ChuckyEntity;
import net.mcreator.boh.entity.ChuckyGrabEntity;
import net.mcreator.boh.entity.CrucifixProjectileEntity;
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
import net.mcreator.boh.entity.HerobrineEntity;
import net.mcreator.boh.entity.HolyWaterEntity;
import net.mcreator.boh.entity.HypnoEntity;
import net.mcreator.boh.entity.HypnoShotProjectileEntity;
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
import net.mcreator.boh.entity.MothmanbastProjectileEntity;
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
import net.mcreator.boh.entity.RayGunProjectileProjectileEntity;
import net.mcreator.boh.entity.RendProjectileEntity;
import net.mcreator.boh.entity.RexyEntity;
import net.mcreator.boh.entity.RollingGiantEntity;
import net.mcreator.boh.entity.RussianSleepExperimentEntity;
import net.mcreator.boh.entity.SadakoEntity;
import net.mcreator.boh.entity.SaucerEntity;
import net.mcreator.boh.entity.SawRunnerEntity;
import net.mcreator.boh.entity.ScissormanEntity;
import net.mcreator.boh.entity.SeedEaterEntity;
import net.mcreator.boh.entity.ShotgunProjectileEntity;
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
import net.mcreator.boh.entity.TarBallEntity;
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
import net.mcreator.boh.entity.WFPistolProjectileEntity;
import net.mcreator.boh.entity.WarHorseEntity;
import net.mcreator.boh.entity.WendigoEntity;
import net.mcreator.boh.entity.WerewolfDummyEntity;
import net.mcreator.boh.entity.WerewolfEntity;
import net.mcreator.boh.entity.WhitefaceEntity;
import net.mcreator.boh.entity.WhitefaceFriendlyEntity;
import net.mcreator.boh.entity.WillowispEntity;
import net.mcreator.boh.entity.XenomorphEntity;
import net.minecraft.entity.Entity;

public class BohModEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "boh");
    public static final RegistryObject<EntityType<BenDrownedEntity>> BEN_DROWNED = register(
        "ben_drowned",
        M.fireImmune(
                Builder.of(BenDrownedEntity::new, BenDrownedEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(1000)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(BenDrownedEntity::new)
            )
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<JeffTheKillerEntity>> JEFF_THE_KILLER = register(
        "jeff_the_killer",
        Builder.of(JeffTheKillerEntity::new, JeffTheKillerEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(300)
            .setUpdateInterval(3)
            .setCustomClientFactory(JeffTheKillerEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<SirenHeadEntity>> SIREN_HEAD = register(
        "siren_head",
        Builder.of(SirenHeadEntity::new, SirenHeadEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(100)
            .setUpdateInterval(3)
            .setCustomClientFactory(SirenHeadEntity::new)
            .sized(1.2F, 9.0F)
    );
    public static final RegistryObject<EntityType<PyramidHeadEntity>> PYRAMID_HEAD = register(
        "pyramid_head",
        Builder.of(PyramidHeadEntity::new, PyramidHeadEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(1000)
            .setUpdateInterval(3)
            .setCustomClientFactory(PyramidHeadEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<SawRunnerEntity>> SAW_RUNNER = register(
        "saw_runner",
        Builder.of(SawRunnerEntity::new, SawRunnerEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(300)
            .setUpdateInterval(3)
            .setCustomClientFactory(SawRunnerEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<XenomorphEntity>> XENOMORPH = register(
        "xenomorph",
        Builder.of(XenomorphEntity::new, XenomorphEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(1000)
            .setUpdateInterval(3)
            .setCustomClientFactory(XenomorphEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<FacehuggerEntity>> FACEHUGGER = register(
        "facehugger",
        Builder.of(FacehuggerEntity::new, FacehuggerEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(FacehuggerEntity::new)
            .sized(0.6F, 0.4F)
    );
    public static final RegistryObject<EntityType<ChestbursterEntity>> CHESTBURSTER = register(
        "chestburster",
        Builder.of(ChestbursterEntity::new, ChestbursterEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(ChestbursterEntity::new)
            .sized(0.6F, 0.8F)
    );
    public static final RegistryObject<EntityType<LifeformEntity>> LIFEFORM = register(
        "lifeform",
        Builder.of(LifeformEntity::new, LifeformEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(300)
            .setUpdateInterval(3)
            .setCustomClientFactory(LifeformEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<SlenderManEntity>> SLENDER_MAN = register(
        "slender_man",
        Builder.of(SlenderManEntity::new, SlenderManEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(90)
            .setUpdateInterval(3)
            .setCustomClientFactory(SlenderManEntity::new)
            .sized(0.6F, 4.0F)
    );
    public static final RegistryObject<EntityType<DemogorgonEntity>> DEMOGORGON = register(
        "demogorgon",
        Builder.of(DemogorgonEntity::new, DemogorgonEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(DemogorgonEntity::new)
            .sized(0.7F, 3.0F)
    );
    public static final RegistryObject<EntityType<GoldLostEntity>> GOLD_LOST = register(
        "gold_lost",
        Builder.of(GoldLostEntity::new, GoldLostEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(GoldLostEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<GoldHostileEntity>> GOLD_HOSTILE = register(
        "gold_hostile",
        Builder.of(GoldHostileEntity::new, GoldHostileEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(GoldHostileEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<Unown1Entity>> UNOWN_1 = register(
        "unown_1",
        Builder.of(Unown1Entity::new, Unown1Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown1Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown2Entity>> UNOWN_2 = register(
        "unown_2",
        Builder.of(Unown2Entity::new, Unown2Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown2Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown3Entity>> UNOWN_3 = register(
        "unown_3",
        Builder.of(Unown3Entity::new, Unown3Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown3Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown4Entity>> UNOWN_4 = register(
        "unown_4",
        Builder.of(Unown4Entity::new, Unown4Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown4Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown5Entity>> UNOWN_5 = register(
        "unown_5",
        Builder.of(Unown5Entity::new, Unown5Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown5Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown6Entity>> UNOWN_6 = register(
        "unown_6",
        Builder.of(Unown6Entity::new, Unown6Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown6Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown7Entity>> UNOWN_7 = register(
        "unown_7",
        Builder.of(Unown7Entity::new, Unown7Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown7Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown8Entity>> UNOWN_8 = register(
        "unown_8",
        Builder.of(Unown8Entity::new, Unown8Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown8Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown9Entity>> UNOWN_9 = register(
        "unown_9",
        Builder.of(Unown9Entity::new, Unown9Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown9Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown10Entity>> UNOWN_10 = register(
        "unown_10",
        Builder.of(Unown10Entity::new, Unown10Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown10Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown11Entity>> UNOWN_11 = register(
        "unown_11",
        Builder.of(Unown11Entity::new, Unown11Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown11Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown12Entity>> UNOWN_12 = register(
        "unown_12",
        Builder.of(Unown12Entity::new, Unown12Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown12Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown13Entity>> UNOWN_13 = register(
        "unown_13",
        Builder.of(Unown13Entity::new, Unown13Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown13Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown14Entity>> UNOWN_14 = register(
        "unown_14",
        Builder.of(Unown14Entity::new, Unown14Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown14Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<Unown15Entity>> UNOWN_15 = register(
        "unown_15",
        Builder.of(Unown15Entity::new, Unown15Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Unown15Entity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<RakeEntity>> RAKE = register(
        "rake",
        Builder.of(RakeEntity::new, RakeEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(RakeEntity::new)
            .sized(0.6F, 1.0F)
    );
    public static final RegistryObject<EntityType<NecoArcEntity>> NECO_ARC = register(
        "neco_arc",
        M.fireImmune(
                Builder.of(NecoArcEntity::new, NecoArcEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(NecoArcEntity::new)
            )
            .sized(0.6F, 1.4F)
    );
    public static final RegistryObject<EntityType<WhitefaceEntity>> WHITEFACE = register(
        "whiteface",
        Builder.of(WhitefaceEntity::new, WhitefaceEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(1000)
            .setUpdateInterval(3)
            .setCustomClientFactory(WhitefaceEntity::new)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<MichaelDaviesEntity>> MICHAEL_DAVIES = register(
        "michael_davies",
        Builder.of(MichaelDaviesEntity::new, MichaelDaviesEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(MichaelDaviesEntity::new)
            .sized(0.7F, 0.7F)
    );
    public static final RegistryObject<EntityType<SmileDogEntity>> SMILE_DOG = register(
        "smile_dog",
        Builder.of(SmileDogEntity::new, SmileDogEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SmileDogEntity::new)
            .sized(0.6F, 0.6F)
    );
    public static final RegistryObject<EntityType<WhitefaceFriendlyEntity>> WHITEFACE_FRIENDLY = register(
        "whiteface_friendly",
        Builder.of(WhitefaceFriendlyEntity::new, WhitefaceFriendlyEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(1000)
            .setUpdateInterval(3)
            .setCustomClientFactory(WhitefaceFriendlyEntity::new)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<DecoyDogEntity>> DECOY_DOG = register(
        "decoy_dog",
        Builder.of(DecoyDogEntity::new, DecoyDogEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(DecoyDogEntity::new)
            .sized(0.6F, 0.6F)
    );
    public static final RegistryObject<EntityType<SpringtrapEntity>> SPRINGTRAP = register(
        "springtrap",
        Builder.of(SpringtrapEntity::new, SpringtrapEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SpringtrapEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<AoOniEntity>> AO_ONI = register(
        "ao_oni",
        Builder.of(AoOniEntity::new, AoOniEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(AoOniEntity::new)
            .sized(0.6F, 3.0F)
    );
    public static final RegistryObject<EntityType<RexyEntity>> REXY = register(
        "rexy",
        Builder.of(RexyEntity::new, RexyEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(RexyEntity::new)
            .sized(2.0F, 2.8F)
    );
    public static final RegistryObject<EntityType<SonicExeEntity>> SONIC_EXE = register(
        "sonic_exe",
        Builder.of(SonicExeEntity::new, SonicExeEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(1000)
            .setUpdateInterval(3)
            .setCustomClientFactory(SonicExeEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<ExeMonitorEntity>> EXE_MONITOR = register(
        "exe_monitor",
        Builder.of(ExeMonitorEntity::new, ExeMonitorEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(ExeMonitorEntity::new)
            .sized(1.0F, 1.0F)
    );
    public static final RegistryObject<EntityType<HerobrineEntity>> HEROBRINE = register(
        "herobrine",
        M.fireImmune(
                Builder.of(HerobrineEntity::new, HerobrineEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(1000)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(HerobrineEntity::new)
            )
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<SixEntity>> SIX = register(
        "six",
        Builder.of(SixEntity::new, SixEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SixEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<MothmanEntity>> MOTHMAN = register(
        "mothman",
        Builder.of(MothmanEntity::new, MothmanEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(MothmanEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<RaatmaEntity>> RAATMA = register(
        "raatma",
        Builder.of(RaatmaEntity::new, RaatmaEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(RaatmaEntity::new)
            .sized(0.6F, 1.2F)
    );
    public static final RegistryObject<EntityType<RatEntity>> RAT = register(
        "rat",
        Builder.of(RatEntity::new, RatEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(RatEntity::new)
            .sized(0.6F, 0.6F)
    );
    public static final RegistryObject<EntityType<SeedEaterEntity>> SEED_EATER = register(
        "seed_eater",
        Builder.of(SeedEaterEntity::new, SeedEaterEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SeedEaterEntity::new)
            .sized(0.6F, 2.1F)
    );
    public static final RegistryObject<EntityType<GasterEntity>> GASTER = register(
        "gaster",
        Builder.of(GasterEntity::new, GasterEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(GasterEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<JamesSunderlandEntity>> JAMES_SUNDERLAND = register(
        "james_sunderland",
        Builder.of(JamesSunderlandEntity::new, JamesSunderlandEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(JamesSunderlandEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<GrayAlienEntity>> GRAY_ALIEN = register(
        "gray_alien",
        Builder.of(GrayAlienEntity::new, GrayAlienEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(GrayAlienEntity::new)
            .sized(0.6F, 1.5F)
    );
    public static final RegistryObject<EntityType<SaucerEntity>> SAUCER = register(
        "saucer",
        Builder.of(SaucerEntity::new, SaucerEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SaucerEntity::new)
            .sized(8.0F, 1.8F)
    );
    public static final RegistryObject<EntityType<SotirisEntity>> SOTIRIS = register(
        "sotiris",
        Builder.of(SotirisEntity::new, SotirisEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SotirisEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<RatazanaEntity>> RATAZANA = register(
        "ratazana",
        Builder.of(RatazanaEntity::new, RatazanaEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(RatazanaEntity::new)
            .sized(0.6F, 0.6F)
    );
    public static final RegistryObject<EntityType<SouichiEntity>> SOUICHI = register(
        "souichi",
        Builder.of(SouichiEntity::new, SouichiEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SouichiEntity::new)
            .sized(0.6F, 1.7F)
    );
    public static final RegistryObject<EntityType<SimonhenrikssonEntity>> SIMONHENRIKSSON = register(
        "simonhenriksson",
        Builder.of(SimonhenrikssonEntity::new, SimonhenrikssonEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SimonhenrikssonEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<BookSimonEntity>> BOOK_SIMON = register(
        "book_simon",
        Builder.of(BookSimonEntity::new, BookSimonEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(BookSimonEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<MichaelMyersEntity>> MICHAEL_MYERS = register(
        "michael_myers",
        Builder.of(MichaelMyersEntity::new, MichaelMyersEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(MichaelMyersEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<EyelessJackEntity>> EYELESS_JACK = register(
        "eyeless_jack",
        Builder.of(EyelessJackEntity::new, EyelessJackEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(EyelessJackEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<WendigoEntity>> WENDIGO = register(
        "wendigo",
        Builder.of(WendigoEntity::new, WendigoEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(WendigoEntity::new)
            .sized(0.8F, 2.0F)
    );
    public static final RegistryObject<EntityType<NemesisEntity>> NEMESIS = register(
        "nemesis",
        Builder.of(NemesisEntity::new, NemesisEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(NemesisEntity::new)
            .sized(1.0F, 2.6F)
    );
    public static final RegistryObject<EntityType<JasonVoorheesEntity>> JASON_VOORHEES = register(
        "jason_voorhees",
        Builder.of(JasonVoorheesEntity::new, JasonVoorheesEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(JasonVoorheesEntity::new)
            .sized(0.8F, 2.5F)
    );
    public static final RegistryObject<EntityType<JasonMaskEntity>> JASON_MASK = register(
        "jason_mask",
        Builder.of(JasonMaskEntity::new, JasonMaskEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(JasonMaskEntity::new)
            .sized(0.4F, 0.4F)
    );
    public static final RegistryObject<EntityType<BigDaddyEntity>> BIG_DADDY = register(
        "big_daddy",
        Builder.of(BigDaddyEntity::new, BigDaddyEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(BigDaddyEntity::new)
            .sized(1.0F, 2.0F)
    );
    public static final RegistryObject<EntityType<LightHeadEntity>> LIGHT_HEAD = register(
        "light_head",
        Builder.of(LightHeadEntity::new, LightHeadEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(100)
            .setUpdateInterval(3)
            .setCustomClientFactory(LightHeadEntity::new)
            .sized(1.2F, 9.0F)
    );
    public static final RegistryObject<EntityType<LittleSisterEntity>> LITTLE_SISTER = register(
        "little_sister",
        Builder.of(LittleSisterEntity::new, LittleSisterEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(LittleSisterEntity::new)
            .sized(0.4F, 1.4F)
    );
    public static final RegistryObject<EntityType<DeerEntity>> DEER = register(
        "deer",
        Builder.of(DeerEntity::new, DeerEntity.class, MobCategory.AMBIENT)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(DeerEntity::new)
            .sized(0.8F, 1.9F)
    );
    public static final RegistryObject<EntityType<DeerMimicEntity>> DEER_MIMIC = register(
        "deer_mimic",
        Builder.of(DeerMimicEntity::new, DeerMimicEntity.class, MobCategory.AMBIENT)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(DeerMimicEntity::new)
            .sized(0.8F, 1.9F)
    );
    public static final RegistryObject<EntityType<KrampusEntity>> KRAMPUS = register(
        "krampus",
        Builder.of(KrampusEntity::new, KrampusEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(KrampusEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<GrannyEntity>> GRANNY = register(
        "granny",
        Builder.of(GrannyEntity::new, GrannyEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(1000)
            .setUpdateInterval(3)
            .setCustomClientFactory(GrannyEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<StiltwalkerEntity>> STILTWALKER = register(
        "stiltwalker",
        Builder.of(StiltwalkerEntity::new, StiltwalkerEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(StiltwalkerEntity::new)
            .sized(0.9F, 4.0F)
    );
    public static final RegistryObject<EntityType<REDEntity>> RED = register(
        "red",
        Builder.of(REDEntity::new, REDEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(256)
            .setUpdateInterval(3)
            .setCustomClientFactory(REDEntity::new)
            .sized(12.0F, 25.0F)
    );
    public static final RegistryObject<EntityType<CrucifixProjectileEntity>> CRUCIFIX_PROJECTILE = register(
        "crucifix_projectile",
        Builder.<CrucifixProjectileEntity>of(CrucifixProjectileEntity::new, CrucifixProjectileEntity.class, MobCategory.MISC)
            .setCustomClientFactory(CrucifixProjectileEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<MothmanbastProjectileEntity>> MOTHMANBAST_PROJECTILE = register(
        "mothmanbast_projectile",
        Builder.<MothmanbastProjectileEntity>of(MothmanbastProjectileEntity::new, MothmanbastProjectileEntity.class, MobCategory.MISC)
            .setCustomClientFactory(MothmanbastProjectileEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<RayGunProjectileProjectileEntity>> RAY_GUN_PROJECTILE_PROJECTILE = register(
        "ray_gun_projectile_projectile",
        Builder.<RayGunProjectileProjectileEntity>of(RayGunProjectileProjectileEntity::new, RayGunProjectileProjectileEntity.class, MobCategory.MISC)
            .setCustomClientFactory(RayGunProjectileProjectileEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<SadakoEntity>> SADAKO = register(
        "sadako",
        M.fireImmune(
                Builder.of(SadakoEntity::new, SadakoEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(1000)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(SadakoEntity::new)
            )
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<EntityWellEntity>> ENTITY_WELL = register(
        "entity_well",
        M.fireImmune(
                Builder.of(EntityWellEntity::new, EntityWellEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(EntityWellEntity::new)
            )
            .sized(0.1F, 0.1F)
    );
    public static final RegistryObject<EntityType<Specimen9BossEntity>> SPECIMEN_9_BOSS = register(
        "specimen_9_boss",
        M.fireImmune(
                Builder.of(Specimen9BossEntity::new, Specimen9BossEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(Specimen9BossEntity::new)
            )
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<TakenPillarEntity>> TAKEN_PILLAR = register(
        "taken_pillar",
        M.fireImmune(
                Builder.of(TakenPillarEntity::new, TakenPillarEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(TakenPillarEntity::new)
            )
            .sized(1.0F, 20.0F)
    );
    public static final RegistryObject<EntityType<TakenHandsEntity>> TAKEN_HANDS = register(
        "taken_hands",
        Builder.of(TakenHandsEntity::new, TakenHandsEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(TakenHandsEntity::new)
            .sized(0.6F, 1.5F)
    );
    public static final RegistryObject<EntityType<BloodSpillEntity>> BLOOD_SPILL = register(
        "blood_spill",
        Builder.<BloodSpillEntity>of(BloodSpillEntity::new, BloodSpillEntity.class, MobCategory.MISC)
            .setCustomClientFactory(BloodSpillEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<TormentPyramidEntity>> TORMENT_PYRAMID = register(
        "torment_pyramid",
        M.fireImmune(
                Builder.of(TormentPyramidEntity::new, TormentPyramidEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(TormentPyramidEntity::new)
            )
            .sized(1.0F, 1.0F)
    );
    public static final RegistryObject<EntityType<WFPistolProjectileEntity>> WF_PISTOL_PROJECTILE = register(
        "wf_pistol_projectile",
        Builder.<WFPistolProjectileEntity>of(WFPistolProjectileEntity::new, WFPistolProjectileEntity.class, MobCategory.MISC)
            .setCustomClientFactory(WFPistolProjectileEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<RollingGiantEntity>> ROLLING_GIANT = register(
        "rolling_giant",
        Builder.of(RollingGiantEntity::new, RollingGiantEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(RollingGiantEntity::new)
            .sized(0.9F, 2.5F)
    );
    public static final RegistryObject<EntityType<FresnoNightcrawlerEntity>> FRESNO_NIGHTCRAWLER = register(
        "fresno_nightcrawler",
        Builder.of(FresnoNightcrawlerEntity::new, FresnoNightcrawlerEntity.class, MobCategory.AMBIENT)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(FresnoNightcrawlerEntity::new)
            .sized(0.6F, 1.6F)
    );
    public static final RegistryObject<EntityType<MartianDroneEntity>> MARTIAN_DRONE = register(
        "martian_drone",
        M.fireImmune(
                Builder.of(MartianDroneEntity::new, MartianDroneEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(MartianDroneEntity::new)
            )
            .sized(0.6F, 2.5F)
    );
    public static final RegistryObject<EntityType<ChuckyEntity>> CHUCKY = register(
        "chucky",
        Builder.of(ChuckyEntity::new, ChuckyEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(ChuckyEntity::new)
            .sized(0.4F, 1.1F)
    );
    public static final RegistryObject<EntityType<ChuckyGrabEntity>> CHUCKY_GRAB = register(
        "chucky_grab",
        Builder.of(ChuckyGrabEntity::new, ChuckyGrabEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(ChuckyGrabEntity::new)
            .sized(0.9F, 1.8F)
    );
    public static final RegistryObject<EntityType<GhostfaceEntity>> GHOSTFACE = register(
        "ghostface",
        Builder.of(GhostfaceEntity::new, GhostfaceEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(GhostfaceEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<GhostfaceDecoyEntity>> GHOSTFACE_DECOY = register(
        "ghostface_decoy",
        Builder.of(GhostfaceDecoyEntity::new, GhostfaceDecoyEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(GhostfaceDecoyEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<FreddyKruegerEntity>> FREDDY_KRUEGER = register(
        "freddy_krueger",
        Builder.of(FreddyKruegerEntity::new, FreddyKruegerEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(FreddyKruegerEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<BoiledOneEntity>> BOILED_ONE = register(
        "boiled_one",
        M.fireImmune(
                Builder.of(BoiledOneEntity::new, BoiledOneEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(BoiledOneEntity::new)
            )
            .sized(0.6F, 5.8F)
    );
    public static final RegistryObject<EntityType<RabbidEntity>> RABBID = register(
        "rabbid",
        Builder.of(RabbidEntity::new, RabbidEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(RabbidEntity::new)
            .sized(0.6F, 1.2F)
    );
    public static final RegistryObject<EntityType<VampireEntity>> VAMPIRE = register(
        "vampire",
        Builder.of(VampireEntity::new, VampireEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(VampireEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<VampireBatEntity>> VAMPIRE_BAT = register(
        "vampire_bat",
        Builder.of(VampireBatEntity::new, VampireBatEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(VampireBatEntity::new)
            .sized(1.0F, 1.0F)
    );
    public static final RegistryObject<EntityType<WerewolfEntity>> WEREWOLF = register(
        "werewolf",
        Builder.of(WerewolfEntity::new, WerewolfEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(WerewolfEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<GhostEntity>> GHOST = register(
        "ghost",
        Builder.of(GhostEntity::new, GhostEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(GhostEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<DemonEntity>> DEMON = register(
        "demon",
        Builder.of(DemonEntity::new, DemonEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(100)
            .setUpdateInterval(3)
            .setCustomClientFactory(DemonEntity::new)
            .sized(0.6F, 1.5F)
    );
    public static final RegistryObject<EntityType<SwampMonsterEntity>> SWAMP_MONSTER = register(
        "swamp_monster",
        Builder.of(SwampMonsterEntity::new, SwampMonsterEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SwampMonsterEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<FuwattiEntity>> FUWATTI = register(
        "fuwatti",
        Builder.of(FuwattiEntity::new, FuwattiEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(FuwattiEntity::new)
            .sized(0.6F, 1.5F)
    );
    public static final RegistryObject<EntityType<CelebiEntity>> CELEBI = register(
        "celebi",
        Builder.of(CelebiEntity::new, CelebiEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(CelebiEntity::new)
            .sized(0.4F, 0.8F)
    );
    public static final RegistryObject<EntityType<TamedRatEntity>> TAMED_RAT = register(
        "tamed_rat",
        Builder.of(TamedRatEntity::new, TamedRatEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(TamedRatEntity::new)
            .sized(0.6F, 0.6F)
    );
    public static final RegistryObject<EntityType<PhantomFreddyEntity>> PHANTOM_FREDDY = register(
        "phantom_freddy",
        M.fireImmune(
                Builder.of(PhantomFreddyEntity::new, PhantomFreddyEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PhantomFreddyEntity::new)
            )
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<PhantomFoxyEntity>> PHANTOM_FOXY = register(
        "phantom_foxy",
        M.fireImmune(
                Builder.of(PhantomFoxyEntity::new, PhantomFoxyEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PhantomFoxyEntity::new)
            )
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<PhantomBBEntity>> PHANTOM_BB = register(
        "phantom_bb",
        M.fireImmune(
                Builder.of(PhantomBBEntity::new, PhantomBBEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PhantomBBEntity::new)
            )
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<PhantomPuppetEntity>> PHANTOM_PUPPET = register(
        "phantom_puppet",
        M.fireImmune(
                Builder.of(PhantomPuppetEntity::new, PhantomPuppetEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PhantomPuppetEntity::new)
            )
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<PhantomMangleEntity>> PHANTOM_MANGLE = register(
        "phantom_mangle",
        M.fireImmune(
                Builder.of(PhantomMangleEntity::new, PhantomMangleEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PhantomMangleEntity::new)
            )
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<PhantomChicaEntity>> PHANTOM_CHICA = register(
        "phantom_chica",
        M.fireImmune(
                Builder.of(PhantomChicaEntity::new, PhantomChicaEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PhantomChicaEntity::new)
            )
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<TailsEntity>> TAILS = register(
        "tails",
        Builder.of(TailsEntity::new, TailsEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(1000)
            .setUpdateInterval(3)
            .setCustomClientFactory(TailsEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<GojiEntity>> GOJI = register(
        "goji",
        Builder.of(GojiEntity::new, GojiEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(GojiEntity::new)
            .sized(1.1F, 4.5F)
    );
    public static final RegistryObject<EntityType<InkDemonEntity>> INK_DEMON = register(
        "ink_demon",
        Builder.of(InkDemonEntity::new, InkDemonEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(InkDemonEntity::new)
            .sized(0.6F, 2.3F)
    );
    public static final RegistryObject<EntityType<PredatorEntity>> PREDATOR = register(
        "predator",
        Builder.of(PredatorEntity::new, PredatorEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(256)
            .setUpdateInterval(3)
            .setCustomClientFactory(PredatorEntity::new)
            .sized(0.7F, 2.0F)
    );
    public static final RegistryObject<EntityType<PredatorSightEntity>> PREDATOR_SIGHT = register(
        "predator_sight",
        M.fireImmune(
                Builder.of(PredatorSightEntity::new, PredatorSightEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PredatorSightEntity::new)
            )
            .sized(0.1F, 0.1F)
    );
    public static final RegistryObject<EntityType<AnglerEntity>> ANGLER = register(
        "angler",
        Builder.of(AnglerEntity::new, AnglerEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(AnglerEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<SonicBoomEntity>> SONIC_BOOM = register(
        "sonic_boom",
        M.fireImmune(
                Builder.of(SonicBoomEntity::new, SonicBoomEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(SonicBoomEntity::new)
            )
            .sized(1.0F, 1.0F)
    );
    public static final RegistryObject<EntityType<TinkyWinkyEntity>> TINKY_WINKY = register(
        "tinky_winky",
        Builder.of(TinkyWinkyEntity::new, TinkyWinkyEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(TinkyWinkyEntity::new)
            .sized(0.8F, 2.6F)
    );
    public static final RegistryObject<EntityType<NewbornEntity>> NEWBORN = register(
        "newborn",
        Builder.of(NewbornEntity::new, NewbornEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(NewbornEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<CartoonCatEntity>> CARTOON_CAT = register(
        "cartoon_cat",
        Builder.of(CartoonCatEntity::new, CartoonCatEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(CartoonCatEntity::new)
            .sized(0.9F, 3.0F)
    );
    public static final RegistryObject<EntityType<BaldiEntity>> BALDI = register(
        "baldi",
        Builder.of(BaldiEntity::new, BaldiEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(BaldiEntity::new)
            .sized(0.6F, 3.5F)
    );
    public static final RegistryObject<EntityType<HolyWaterEntity>> HOLY_WATER = register(
        "holy_water",
        Builder.<HolyWaterEntity>of(HolyWaterEntity::new, HolyWaterEntity.class, MobCategory.MISC)
            .setCustomClientFactory(HolyWaterEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<WerewolfDummyEntity>> WEREWOLF_DUMMY = register(
        "werewolf_dummy",
        Builder.of(WerewolfDummyEntity::new, WerewolfDummyEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(WerewolfDummyEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<FigureEntity>> FIGURE = register(
        "figure",
        Builder.of(FigureEntity::new, FigureEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(FigureEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<TaperecorderbaldiEntity>> TAPERECORDERBALDI = register(
        "taperecorderbaldi",
        Builder.of(TaperecorderbaldiEntity::new, TaperecorderbaldiEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(TaperecorderbaldiEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<WillowispEntity>> WILLOWISP = register(
        "willowisp",
        M.fireImmune(
                Builder.of(WillowispEntity::new, WillowispEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(WillowispEntity::new)
            )
            .sized(0.3F, 0.3F)
    );
    public static final RegistryObject<EntityType<WarHorseEntity>> WAR_HORSE = register(
        "war_horse",
        M.fireImmune(
                Builder.of(WarHorseEntity::new, WarHorseEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(WarHorseEntity::new)
            )
            .sized(1.3F, 1.6F)
    );
    public static final RegistryObject<EntityType<FamineHorseEntity>> FAMINE_HORSE = register(
        "famine_horse",
        M.fireImmune(
                Builder.of(FamineHorseEntity::new, FamineHorseEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(FamineHorseEntity::new)
            )
            .sized(1.3F, 1.6F)
    );
    public static final RegistryObject<EntityType<DeathHorseEntity>> DEATH_HORSE = register(
        "death_horse",
        M.fireImmune(
                Builder.of(DeathHorseEntity::new, DeathHorseEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(DeathHorseEntity::new)
            )
            .sized(1.3F, 1.6F)
    );
    public static final RegistryObject<EntityType<PestilenceHorseEntity>> PESTILENCE_HORSE = register(
        "pestilence_horse",
        M.fireImmune(
                Builder.of(PestilenceHorseEntity::new, PestilenceHorseEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PestilenceHorseEntity::new)
            )
            .sized(1.3F, 1.6F)
    );
    public static final RegistryObject<EntityType<UnicornEntity>> UNICORN = register(
        "unicorn",
        M.fireImmune(
                Builder.of(UnicornEntity::new, UnicornEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(UnicornEntity::new)
            )
            .sized(1.3F, 1.6F)
    );
    public static final RegistryObject<EntityType<JackalopeEntity>> JACKALOPE = register(
        "jackalope",
        Builder.of(JackalopeEntity::new, JackalopeEntity.class, MobCategory.AMBIENT)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(JackalopeEntity::new)
            .sized(0.6F, 0.8F)
    );
    public static final RegistryObject<EntityType<FlatwoodsMonsterEntity>> FLATWOODS_MONSTER = register(
        "flatwoods_monster",
        Builder.of(FlatwoodsMonsterEntity::new, FlatwoodsMonsterEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(FlatwoodsMonsterEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<RendProjectileEntity>> REND_PROJECTILE = register(
        "rend_projectile",
        Builder.<RendProjectileEntity>of(RendProjectileEntity::new, RendProjectileEntity.class, MobCategory.MISC)
            .setCustomClientFactory(RendProjectileEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.8F, 0.2F)
    );
    public static final RegistryObject<EntityType<VitaMimicEntity>> VITA_MIMIC = register(
        "vita_mimic",
        Builder.of(VitaMimicEntity::new, VitaMimicEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(VitaMimicEntity::new)
            .sized(0.6F, 2.8F)
    );
    public static final RegistryObject<EntityType<TrimmingEntity>> TRIMMING = register(
        "trimming",
        Builder.of(TrimmingEntity::new, TrimmingEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(TrimmingEntity::new)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<Subject3Entity>> SUBJECT_3 = register(
        "subject_3",
        Builder.of(Subject3Entity::new, Subject3Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(Subject3Entity::new)
            .sized(0.6F, 2.8F)
    );
    public static final RegistryObject<EntityType<PatrickBatemanEntity>> PATRICK_BATEMAN = register(
        "patrick_bateman",
        Builder.of(PatrickBatemanEntity::new, PatrickBatemanEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(PatrickBatemanEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<LeatherfaceEntity>> LEATHERFACE = register(
        "leatherface",
        Builder.of(LeatherfaceEntity::new, LeatherfaceEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(300)
            .setUpdateInterval(3)
            .setCustomClientFactory(LeatherfaceEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<MXEntity>> MX = register(
        "mx",
        Builder.of(MXEntity::new, MXEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(MXEntity::new)
            .sized(1.0F, 2.6F)
    );
    public static final RegistryObject<EntityType<ScissormanEntity>> SCISSORMAN = register(
        "scissorman",
        Builder.of(ScissormanEntity::new, ScissormanEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(ScissormanEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<HypnoEntity>> HYPNO = register(
        "hypno",
        Builder.of(HypnoEntity::new, HypnoEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(HypnoEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<TinkyTankEntity>> TINKY_TANK = register(
        "tinky_tank",
        Builder.of(TinkyTankEntity::new, TinkyTankEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(TinkyTankEntity::new)
            .sized(0.8F, 3.0F)
    );
    public static final RegistryObject<EntityType<RussianSleepExperimentEntity>> RUSSIAN_SLEEP_EXPERIMENT = register(
        "russian_sleep_experiment",
        Builder.of(RussianSleepExperimentEntity::new, RussianSleepExperimentEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(RussianSleepExperimentEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<GraftonMonsterEntity>> GRAFTON_MONSTER = register(
        "grafton_monster",
        Builder.of(GraftonMonsterEntity::new, GraftonMonsterEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(GraftonMonsterEntity::new)
            .sized(0.8F, 3.0F)
    );
    public static final RegistryObject<EntityType<PumpkinPlayerEntity>> PUMPKIN_PLAYER = register(
        "pumpkin_player",
        Builder.of(PumpkinPlayerEntity::new, PumpkinPlayerEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(PumpkinPlayerEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<KrasueEntity>> KRASUE = register(
        "krasue",
        Builder.of(KrasueEntity::new, KrasueEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(1000)
            .setUpdateInterval(3)
            .setCustomClientFactory(KrasueEntity::new)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<KirieHimuroEntity>> KIRIE_HIMURO = register(
        "kirie_himuro",
        Builder.of(KirieHimuroEntity::new, KirieHimuroEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(KirieHimuroEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<LaughingJackEntity>> LAUGHING_JACK = register(
        "laughing_jack",
        Builder.of(LaughingJackEntity::new, LaughingJackEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(LaughingJackEntity::new)
            .sized(0.6F, 2.0F)
    );
    public static final RegistryObject<EntityType<JaneTheKillerEntity>> JANE_THE_KILLER = register(
        "jane_the_killer",
        Builder.of(JaneTheKillerEntity::new, JaneTheKillerEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(300)
            .setUpdateInterval(3)
            .setCustomClientFactory(JaneTheKillerEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<TarBallEntity>> TAR_BALL = register(
        "tar_ball",
        Builder.<TarBallEntity>of(TarBallEntity::new, TarBallEntity.class, MobCategory.MISC)
            .setCustomClientFactory(TarBallEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<HypnoShotProjectileEntity>> HYPNO_SHOT_PROJECTILE = register(
        "hypno_shot_projectile",
        Builder.<HypnoShotProjectileEntity>of(HypnoShotProjectileEntity::new, HypnoShotProjectileEntity.class, MobCategory.MISC)
            .setCustomClientFactory(HypnoShotProjectileEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(1.0F, 1.0F)
    );
    public static final RegistryObject<EntityType<TailsDollEntity>> TAILS_DOLL = register(
        "tails_doll",
        Builder.of(TailsDollEntity::new, TailsDollEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(1000)
            .setUpdateInterval(3)
            .setCustomClientFactory(TailsDollEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<SuicideMouseEntity>> SUICIDE_MOUSE = register(
        "suicide_mouse",
        Builder.of(SuicideMouseEntity::new, SuicideMouseEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SuicideMouseEntity::new)
            .sized(0.6F, 1.0F)
    );
    public static final RegistryObject<EntityType<SquidwardEntity>> SQUIDWARD = register(
        "squidward",
        Builder.of(SquidwardEntity::new, SquidwardEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SquidwardEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<SquidwardDoomedEntity>> SQUIDWARD_DOOMED = register(
        "squidward_doomed",
        Builder.of(SquidwardDoomedEntity::new, SquidwardDoomedEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(SquidwardDoomedEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<ShotgunProjectileEntity>> SHOTGUN_PROJECTILE = register(
        "shotgun_projectile",
        Builder.<ShotgunProjectileEntity>of(ShotgunProjectileEntity::new, ShotgunProjectileEntity.class, MobCategory.MISC)
            .setCustomClientFactory(ShotgunProjectileEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<TheThingVillagerEntity>> THE_THING_VILLAGER = register(
        "the_thing_villager",
        Builder.of(TheThingVillagerEntity::new, TheThingVillagerEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(TheThingVillagerEntity::new)
            .sized(0.6F, 1.8F)
    );
    public static final RegistryObject<EntityType<TheThingDogEntity>> THE_THING_DOG = register(
        "the_thing_dog",
        Builder.of(TheThingDogEntity::new, TheThingDogEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(TheThingDogEntity::new)
            .sized(0.6F, 0.6F)
    );
    public static final RegistryObject<EntityType<BloodwaveEntity>> BLOODWAVE = register(
        "bloodwave",
        M.fireImmune(
                Builder.of(BloodwaveEntity::new, BloodwaveEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(BloodwaveEntity::new)
            )
            .sized(0.5F, 1.8F)
    );
    public static final RegistryObject<EntityType<BruceEntity>> BRUCE = register(
        "bruce",
        Builder.of(BruceEntity::new, BruceEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(BruceEntity::new)
            .sized(1.5F, 1.8F)
    );
    public static final RegistryObject<EntityType<MothlingEntity>> MOTHLING = register(
        "mothling",
        Builder.of(MothlingEntity::new, MothlingEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .setCustomClientFactory(MothlingEntity::new)
            .sized(0.5F, 0.5F)
    );
    public static final RegistryObject<EntityType<NothingThereEntity>> NOTHING_THERE = register(
        "nothing_there",
        Builder.of(NothingThereEntity::new, NothingThereEntity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(NothingThereEntity::new)
            .sized(1.0F, 2.6F)
    );
    public static final RegistryObject<EntityType<FlowersEntity>> FLOWERS = register(
        "flowers",
        M.fireImmune(
                Builder.of(FlowersEntity::new, FlowersEntity.class, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(FlowersEntity::new)
            )
            .sized(0.6F, 4.0F)
    );
    public static final RegistryObject<EntityType<NPC000Entity>> NPC_000 = register(
        "npc_000",
        Builder.of(NPC000Entity::new, NPC000Entity.class, MobCategory.MONSTER)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(128)
            .setUpdateInterval(3)
            .setCustomClientFactory(NPC000Entity::new)
            .sized(0.6F, 1.8F)
    );

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryname, () -> entityTypeBuilder.build(registryname));
    }

    @SubscribeEvent
    public void init(FMLCommonSetupEvent event) {
        M.enqueueWork(event, () -> {
            BenDrownedEntity.init();
            JeffTheKillerEntity.init();
            SirenHeadEntity.init();
            PyramidHeadEntity.init();
            SawRunnerEntity.init();
            XenomorphEntity.init();
            FacehuggerEntity.init();
            ChestbursterEntity.init();
            LifeformEntity.init();
            SlenderManEntity.init();
            DemogorgonEntity.init();
            GoldLostEntity.init();
            GoldHostileEntity.init();
            Unown1Entity.init();
            Unown2Entity.init();
            Unown3Entity.init();
            Unown4Entity.init();
            Unown5Entity.init();
            Unown6Entity.init();
            Unown7Entity.init();
            Unown8Entity.init();
            Unown9Entity.init();
            Unown10Entity.init();
            Unown11Entity.init();
            Unown12Entity.init();
            Unown13Entity.init();
            Unown14Entity.init();
            Unown15Entity.init();
            RakeEntity.init();
            NecoArcEntity.init();
            WhitefaceEntity.init();
            MichaelDaviesEntity.init();
            SmileDogEntity.init();
            WhitefaceFriendlyEntity.init();
            DecoyDogEntity.init();
            SpringtrapEntity.init();
            AoOniEntity.init();
            RexyEntity.init();
            SonicExeEntity.init();
            ExeMonitorEntity.init();
            HerobrineEntity.init();
            SixEntity.init();
            MothmanEntity.init();
            RaatmaEntity.init();
            RatEntity.init();
            SeedEaterEntity.init();
            GasterEntity.init();
            JamesSunderlandEntity.init();
            GrayAlienEntity.init();
            SaucerEntity.init();
            SotirisEntity.init();
            RatazanaEntity.init();
            SouichiEntity.init();
            SimonhenrikssonEntity.init();
            BookSimonEntity.init();
            MichaelMyersEntity.init();
            EyelessJackEntity.init();
            WendigoEntity.init();
            NemesisEntity.init();
            JasonVoorheesEntity.init();
            JasonMaskEntity.init();
            BigDaddyEntity.init();
            LightHeadEntity.init();
            LittleSisterEntity.init();
            DeerEntity.init();
            DeerMimicEntity.init();
            KrampusEntity.init();
            GrannyEntity.init();
            StiltwalkerEntity.init();
            REDEntity.init();
            SadakoEntity.init();
            EntityWellEntity.init();
            Specimen9BossEntity.init();
            TakenPillarEntity.init();
            TakenHandsEntity.init();
            TormentPyramidEntity.init();
            RollingGiantEntity.init();
            FresnoNightcrawlerEntity.init();
            MartianDroneEntity.init();
            ChuckyEntity.init();
            ChuckyGrabEntity.init();
            GhostfaceEntity.init();
            GhostfaceDecoyEntity.init();
            FreddyKruegerEntity.init();
            BoiledOneEntity.init();
            RabbidEntity.init();
            VampireEntity.init();
            VampireBatEntity.init();
            WerewolfEntity.init();
            GhostEntity.init();
            DemonEntity.init();
            SwampMonsterEntity.init();
            FuwattiEntity.init();
            CelebiEntity.init();
            TamedRatEntity.init();
            PhantomFreddyEntity.init();
            PhantomFoxyEntity.init();
            PhantomBBEntity.init();
            PhantomPuppetEntity.init();
            PhantomMangleEntity.init();
            PhantomChicaEntity.init();
            TailsEntity.init();
            GojiEntity.init();
            InkDemonEntity.init();
            PredatorEntity.init();
            PredatorSightEntity.init();
            AnglerEntity.init();
            SonicBoomEntity.init();
            TinkyWinkyEntity.init();
            NewbornEntity.init();
            CartoonCatEntity.init();
            BaldiEntity.init();
            WerewolfDummyEntity.init();
            FigureEntity.init();
            TaperecorderbaldiEntity.init();
            WillowispEntity.init();
            WarHorseEntity.init();
            FamineHorseEntity.init();
            DeathHorseEntity.init();
            PestilenceHorseEntity.init();
            UnicornEntity.init();
            JackalopeEntity.init();
            FlatwoodsMonsterEntity.init();
            VitaMimicEntity.init();
            TrimmingEntity.init();
            Subject3Entity.init();
            PatrickBatemanEntity.init();
            LeatherfaceEntity.init();
            MXEntity.init();
            ScissormanEntity.init();
            HypnoEntity.init();
            TinkyTankEntity.init();
            RussianSleepExperimentEntity.init();
            GraftonMonsterEntity.init();
            PumpkinPlayerEntity.init();
            KrasueEntity.init();
            KirieHimuroEntity.init();
            LaughingJackEntity.init();
            JaneTheKillerEntity.init();
            TailsDollEntity.init();
            SuicideMouseEntity.init();
            SquidwardEntity.init();
            SquidwardDoomedEntity.init();
            TheThingVillagerEntity.init();
            TheThingDogEntity.init();
            BloodwaveEntity.init();
            BruceEntity.init();
            MothlingEntity.init();
            NothingThereEntity.init();
            FlowersEntity.init();
            NPC000Entity.init();
        });
    }

    @SubscribeEvent
    public void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(BEN_DROWNED.get(), BenDrownedEntity.createAttributes().build());
        event.put(JEFF_THE_KILLER.get(), JeffTheKillerEntity.createAttributes().build());
        event.put(SIREN_HEAD.get(), SirenHeadEntity.createAttributes().build());
        event.put(PYRAMID_HEAD.get(), PyramidHeadEntity.createAttributes().build());
        event.put(SAW_RUNNER.get(), SawRunnerEntity.createAttributes().build());
        event.put(XENOMORPH.get(), XenomorphEntity.createAttributes().build());
        event.put(FACEHUGGER.get(), FacehuggerEntity.createAttributes().build());
        event.put(CHESTBURSTER.get(), ChestbursterEntity.createAttributes().build());
        event.put(LIFEFORM.get(), LifeformEntity.createAttributes().build());
        event.put(SLENDER_MAN.get(), SlenderManEntity.createAttributes().build());
        event.put(DEMOGORGON.get(), DemogorgonEntity.createAttributes().build());
        event.put(GOLD_LOST.get(), GoldLostEntity.createAttributes().build());
        event.put(GOLD_HOSTILE.get(), GoldHostileEntity.createAttributes().build());
        event.put(UNOWN_1.get(), Unown1Entity.createAttributes().build());
        event.put(UNOWN_2.get(), Unown2Entity.createAttributes().build());
        event.put(UNOWN_3.get(), Unown3Entity.createAttributes().build());
        event.put(UNOWN_4.get(), Unown4Entity.createAttributes().build());
        event.put(UNOWN_5.get(), Unown5Entity.createAttributes().build());
        event.put(UNOWN_6.get(), Unown6Entity.createAttributes().build());
        event.put(UNOWN_7.get(), Unown7Entity.createAttributes().build());
        event.put(UNOWN_8.get(), Unown8Entity.createAttributes().build());
        event.put(UNOWN_9.get(), Unown9Entity.createAttributes().build());
        event.put(UNOWN_10.get(), Unown10Entity.createAttributes().build());
        event.put(UNOWN_11.get(), Unown11Entity.createAttributes().build());
        event.put(UNOWN_12.get(), Unown12Entity.createAttributes().build());
        event.put(UNOWN_13.get(), Unown13Entity.createAttributes().build());
        event.put(UNOWN_14.get(), Unown14Entity.createAttributes().build());
        event.put(UNOWN_15.get(), Unown15Entity.createAttributes().build());
        event.put(RAKE.get(), RakeEntity.createAttributes().build());
        event.put(NECO_ARC.get(), NecoArcEntity.createAttributes().build());
        event.put(WHITEFACE.get(), WhitefaceEntity.createAttributes().build());
        event.put(MICHAEL_DAVIES.get(), MichaelDaviesEntity.createAttributes().build());
        event.put(SMILE_DOG.get(), SmileDogEntity.createAttributes().build());
        event.put(WHITEFACE_FRIENDLY.get(), WhitefaceFriendlyEntity.createAttributes().build());
        event.put(DECOY_DOG.get(), DecoyDogEntity.createAttributes().build());
        event.put(SPRINGTRAP.get(), SpringtrapEntity.createAttributes().build());
        event.put(AO_ONI.get(), AoOniEntity.createAttributes().build());
        event.put(REXY.get(), RexyEntity.createAttributes().build());
        event.put(SONIC_EXE.get(), SonicExeEntity.createAttributes().build());
        event.put(EXE_MONITOR.get(), ExeMonitorEntity.createAttributes().build());
        event.put(HEROBRINE.get(), HerobrineEntity.createAttributes().build());
        event.put(SIX.get(), SixEntity.createAttributes().build());
        event.put(MOTHMAN.get(), MothmanEntity.createAttributes().build());
        event.put(RAATMA.get(), RaatmaEntity.createAttributes().build());
        event.put(RAT.get(), RatEntity.createAttributes().build());
        event.put(SEED_EATER.get(), SeedEaterEntity.createAttributes().build());
        event.put(GASTER.get(), GasterEntity.createAttributes().build());
        event.put(JAMES_SUNDERLAND.get(), JamesSunderlandEntity.createAttributes().build());
        event.put(GRAY_ALIEN.get(), GrayAlienEntity.createAttributes().build());
        event.put(SAUCER.get(), SaucerEntity.createAttributes().build());
        event.put(SOTIRIS.get(), SotirisEntity.createAttributes().build());
        event.put(RATAZANA.get(), RatazanaEntity.createAttributes().build());
        event.put(SOUICHI.get(), SouichiEntity.createAttributes().build());
        event.put(SIMONHENRIKSSON.get(), SimonhenrikssonEntity.createAttributes().build());
        event.put(BOOK_SIMON.get(), BookSimonEntity.createAttributes().build());
        event.put(MICHAEL_MYERS.get(), MichaelMyersEntity.createAttributes().build());
        event.put(EYELESS_JACK.get(), EyelessJackEntity.createAttributes().build());
        event.put(WENDIGO.get(), WendigoEntity.createAttributes().build());
        event.put(NEMESIS.get(), NemesisEntity.createAttributes().build());
        event.put(JASON_VOORHEES.get(), JasonVoorheesEntity.createAttributes().build());
        event.put(JASON_MASK.get(), JasonMaskEntity.createAttributes().build());
        event.put(BIG_DADDY.get(), BigDaddyEntity.createAttributes().build());
        event.put(LIGHT_HEAD.get(), LightHeadEntity.createAttributes().build());
        event.put(LITTLE_SISTER.get(), LittleSisterEntity.createAttributes().build());
        event.put(DEER.get(), DeerEntity.createAttributes().build());
        event.put(DEER_MIMIC.get(), DeerMimicEntity.createAttributes().build());
        event.put(KRAMPUS.get(), KrampusEntity.createAttributes().build());
        event.put(GRANNY.get(), GrannyEntity.createAttributes().build());
        event.put(STILTWALKER.get(), StiltwalkerEntity.createAttributes().build());
        event.put(RED.get(), REDEntity.createAttributes().build());
        event.put(SADAKO.get(), SadakoEntity.createAttributes().build());
        event.put(ENTITY_WELL.get(), EntityWellEntity.createAttributes().build());
        event.put(SPECIMEN_9_BOSS.get(), Specimen9BossEntity.createAttributes().build());
        event.put(TAKEN_PILLAR.get(), TakenPillarEntity.createAttributes().build());
        event.put(TAKEN_HANDS.get(), TakenHandsEntity.createAttributes().build());
        event.put(TORMENT_PYRAMID.get(), TormentPyramidEntity.createAttributes().build());
        event.put(ROLLING_GIANT.get(), RollingGiantEntity.createAttributes().build());
        event.put(FRESNO_NIGHTCRAWLER.get(), FresnoNightcrawlerEntity.createAttributes().build());
        event.put(MARTIAN_DRONE.get(), MartianDroneEntity.createAttributes().build());
        event.put(CHUCKY.get(), ChuckyEntity.createAttributes().build());
        event.put(CHUCKY_GRAB.get(), ChuckyGrabEntity.createAttributes().build());
        event.put(GHOSTFACE.get(), GhostfaceEntity.createAttributes().build());
        event.put(GHOSTFACE_DECOY.get(), GhostfaceDecoyEntity.createAttributes().build());
        event.put(FREDDY_KRUEGER.get(), FreddyKruegerEntity.createAttributes().build());
        event.put(BOILED_ONE.get(), BoiledOneEntity.createAttributes().build());
        event.put(RABBID.get(), RabbidEntity.createAttributes().build());
        event.put(VAMPIRE.get(), VampireEntity.createAttributes().build());
        event.put(VAMPIRE_BAT.get(), VampireBatEntity.createAttributes().build());
        event.put(WEREWOLF.get(), WerewolfEntity.createAttributes().build());
        event.put(GHOST.get(), GhostEntity.createAttributes().build());
        event.put(DEMON.get(), DemonEntity.createAttributes().build());
        event.put(SWAMP_MONSTER.get(), SwampMonsterEntity.createAttributes().build());
        event.put(FUWATTI.get(), FuwattiEntity.createAttributes().build());
        event.put(CELEBI.get(), CelebiEntity.createAttributes().build());
        event.put(TAMED_RAT.get(), TamedRatEntity.createAttributes().build());
        event.put(PHANTOM_FREDDY.get(), PhantomFreddyEntity.createAttributes().build());
        event.put(PHANTOM_FOXY.get(), PhantomFoxyEntity.createAttributes().build());
        event.put(PHANTOM_BB.get(), PhantomBBEntity.createAttributes().build());
        event.put(PHANTOM_PUPPET.get(), PhantomPuppetEntity.createAttributes().build());
        event.put(PHANTOM_MANGLE.get(), PhantomMangleEntity.createAttributes().build());
        event.put(PHANTOM_CHICA.get(), PhantomChicaEntity.createAttributes().build());
        event.put(TAILS.get(), TailsEntity.createAttributes().build());
        event.put(GOJI.get(), GojiEntity.createAttributes().build());
        event.put(INK_DEMON.get(), InkDemonEntity.createAttributes().build());
        event.put(PREDATOR.get(), PredatorEntity.createAttributes().build());
        event.put(PREDATOR_SIGHT.get(), PredatorSightEntity.createAttributes().build());
        event.put(ANGLER.get(), AnglerEntity.createAttributes().build());
        event.put(SONIC_BOOM.get(), SonicBoomEntity.createAttributes().build());
        event.put(TINKY_WINKY.get(), TinkyWinkyEntity.createAttributes().build());
        event.put(NEWBORN.get(), NewbornEntity.createAttributes().build());
        event.put(CARTOON_CAT.get(), CartoonCatEntity.createAttributes().build());
        event.put(BALDI.get(), BaldiEntity.createAttributes().build());
        event.put(WEREWOLF_DUMMY.get(), WerewolfDummyEntity.createAttributes().build());
        event.put(FIGURE.get(), FigureEntity.createAttributes().build());
        event.put(TAPERECORDERBALDI.get(), TaperecorderbaldiEntity.createAttributes().build());
        event.put(WILLOWISP.get(), WillowispEntity.createAttributes().build());
        event.put(WAR_HORSE.get(), WarHorseEntity.createAttributes().build());
        event.put(FAMINE_HORSE.get(), FamineHorseEntity.createAttributes().build());
        event.put(DEATH_HORSE.get(), DeathHorseEntity.createAttributes().build());
        event.put(PESTILENCE_HORSE.get(), PestilenceHorseEntity.createAttributes().build());
        event.put(UNICORN.get(), UnicornEntity.createAttributes().build());
        event.put(JACKALOPE.get(), JackalopeEntity.createAttributes().build());
        event.put(FLATWOODS_MONSTER.get(), FlatwoodsMonsterEntity.createAttributes().build());
        event.put(VITA_MIMIC.get(), VitaMimicEntity.createAttributes().build());
        event.put(TRIMMING.get(), TrimmingEntity.createAttributes().build());
        event.put(SUBJECT_3.get(), Subject3Entity.createAttributes().build());
        event.put(PATRICK_BATEMAN.get(), PatrickBatemanEntity.createAttributes().build());
        event.put(LEATHERFACE.get(), LeatherfaceEntity.createAttributes().build());
        event.put(MX.get(), MXEntity.createAttributes().build());
        event.put(SCISSORMAN.get(), ScissormanEntity.createAttributes().build());
        event.put(HYPNO.get(), HypnoEntity.createAttributes().build());
        event.put(TINKY_TANK.get(), TinkyTankEntity.createAttributes().build());
        event.put(RUSSIAN_SLEEP_EXPERIMENT.get(), RussianSleepExperimentEntity.createAttributes().build());
        event.put(GRAFTON_MONSTER.get(), GraftonMonsterEntity.createAttributes().build());
        event.put(PUMPKIN_PLAYER.get(), PumpkinPlayerEntity.createAttributes().build());
        event.put(KRASUE.get(), KrasueEntity.createAttributes().build());
        event.put(KIRIE_HIMURO.get(), KirieHimuroEntity.createAttributes().build());
        event.put(LAUGHING_JACK.get(), LaughingJackEntity.createAttributes().build());
        event.put(JANE_THE_KILLER.get(), JaneTheKillerEntity.createAttributes().build());
        event.put(TAILS_DOLL.get(), TailsDollEntity.createAttributes().build());
        event.put(SUICIDE_MOUSE.get(), SuicideMouseEntity.createAttributes().build());
        event.put(SQUIDWARD.get(), SquidwardEntity.createAttributes().build());
        event.put(SQUIDWARD_DOOMED.get(), SquidwardDoomedEntity.createAttributes().build());
        event.put(THE_THING_VILLAGER.get(), TheThingVillagerEntity.createAttributes().build());
        event.put(THE_THING_DOG.get(), TheThingDogEntity.createAttributes().build());
        event.put(BLOODWAVE.get(), BloodwaveEntity.createAttributes().build());
        event.put(BRUCE.get(), BruceEntity.createAttributes().build());
        event.put(MOTHLING.get(), MothlingEntity.createAttributes().build());
        event.put(NOTHING_THERE.get(), NothingThereEntity.createAttributes().build());
        event.put(FLOWERS.get(), FlowersEntity.createAttributes().build());
        event.put(NPC_000.get(), NPC000Entity.createAttributes().build());
    }
}
