package net.mcreator.boh.compat;

import com.google.common.collect.Multimap;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import net.mcreator.boh.compat.block.BlockModels;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.block.CompatBlock;
import net.mcreator.boh.compat.client.Axis;
import net.mcreator.boh.compat.client.BufferSource;
import net.mcreator.boh.compat.client.ClientDebugCommand;
import net.mcreator.boh.compat.client.ClientEventBridge;
import net.mcreator.boh.compat.client.ElementBlockRenderer;
import net.mcreator.boh.compat.client.ItemRendererBridge;
import net.mcreator.boh.compat.client.ItemTransforms;
import net.mcreator.boh.compat.client.LightTexture;
import net.mcreator.boh.compat.client.Matrix3f;
import net.mcreator.boh.compat.client.Matrix4f;
import net.mcreator.boh.compat.client.MultiBufferSource;
import net.mcreator.boh.compat.client.PoseStack;
import net.mcreator.boh.compat.client.Quaternionf;
import net.mcreator.boh.compat.client.RenderType;
import net.mcreator.boh.compat.client.TessellatorConsumer;
import net.mcreator.boh.compat.client.VertexConsumer;
import net.mcreator.boh.compat.client.particle.ParticleEngine;
import net.mcreator.boh.compat.command.CommandContext;
import net.mcreator.boh.compat.effect.BohMobEffect;
import net.mcreator.boh.compat.effect.ExtraEffects;
import net.mcreator.boh.compat.effect.KuroEffects;
import net.mcreator.boh.compat.entity.BohAbstractArrow;
import net.mcreator.boh.compat.entity.BohAnimal;
import net.mcreator.boh.compat.entity.BohAreaEffectCloud;
import net.mcreator.boh.compat.entity.BohLightningBolt;
import net.mcreator.boh.compat.entity.BohMob;
import net.mcreator.boh.compat.entity.BohMonster;
import net.mcreator.boh.compat.entity.BohPathfinderMob;
import net.mcreator.boh.compat.entity.BohSpider;
import net.mcreator.boh.compat.entity.BohTamableAnimal;
import net.mcreator.boh.compat.entity.ItemCooldowns;
import net.mcreator.boh.compat.forge.EventBridge;
import net.mcreator.boh.compat.forge.client.event.ComputeFogColor;
import net.mcreator.boh.compat.forge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.mcreator.boh.compat.forge.client.event.RegisterLayerDefinitions;
import net.mcreator.boh.compat.forge.client.event.RegisterParticleProvidersEvent;
import net.mcreator.boh.compat.forge.client.event.RegisterRenderers;
import net.mcreator.boh.compat.forge.client.event.RenderFog;
import net.mcreator.boh.compat.forge.client.event.Window;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientItemExtensions;
import net.mcreator.boh.compat.forge.client.extensions.common.IClientMobEffectExtensions;
import net.mcreator.boh.compat.forge.common.BasicItemListing;
import net.mcreator.boh.compat.forge.common.ForgeSpawnEggItem;
import net.mcreator.boh.compat.forge.common.brewing.IBrewingRecipe;
import net.mcreator.boh.compat.forge.common.capabilities.Capability;
import net.mcreator.boh.compat.forge.common.capabilities.EntityCapabilities;
import net.mcreator.boh.compat.forge.common.capabilities.ICapabilityProvider;
import net.mcreator.boh.compat.forge.common.capabilities.ICapabilitySerializable;
import net.mcreator.boh.compat.forge.common.capabilities.RegisterCapabilitiesEvent;
import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.forge.common.util.NonNullSupplier;
import net.mcreator.boh.compat.forge.event.AttachCapabilitiesEvent;
import net.mcreator.boh.compat.forge.event.BuildCreativeModeTabContentsEvent;
import net.mcreator.boh.compat.forge.event.RegisterCommandsEvent;
import net.mcreator.boh.compat.forge.event.entity.EntityAttributeCreationEvent;
import net.mcreator.boh.compat.forge.event.entity.living.LivingChangeTargetEvent;
import net.mcreator.boh.compat.forge.event.entity.living.ShieldBlockEvent;
import net.mcreator.boh.compat.forge.event.entity.player.LeftClickBlock;
import net.mcreator.boh.compat.forge.event.entity.player.LeftClickEmpty;
import net.mcreator.boh.compat.forge.event.entity.player.RightClickBlock;
import net.mcreator.boh.compat.forge.event.village.VillagerTradesEvent;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLClientSetupEvent;
import net.mcreator.boh.compat.forge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mcreator.boh.compat.forge.items.IItemHandler;
import net.mcreator.boh.compat.forge.items.IItemHandlerModifiable;
import net.mcreator.boh.compat.forge.items.ItemStackHandler;
import net.mcreator.boh.compat.forge.items.SlotItemHandler;
import net.mcreator.boh.compat.forge.items.wrapper.InvWrapper;
import net.mcreator.boh.compat.forge.network.NetworkDirection;
import net.mcreator.boh.compat.forge.network.PacketDistributor;
import net.mcreator.boh.compat.forge.network.simple.SimpleChannel;
import net.mcreator.boh.compat.forge.registries.DeferredRegister;
import net.mcreator.boh.compat.forge.registries.IForgeRegistry;
import net.mcreator.boh.compat.forge.registries.ITagManager;
import net.mcreator.boh.compat.forge.registries.RegisterEvent;
import net.mcreator.boh.compat.forge.registries.RegistryObject;
import net.mcreator.boh.compat.item.BohBlockItem;
import net.mcreator.boh.compat.item.BohEnchantment;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.ChatFormatting;
import net.mcreator.boh.compat.mc.advancements.Advancement;
import net.mcreator.boh.compat.mc.advancements.AdvancementProgress;
import net.mcreator.boh.compat.mc.advancements.PlayerAdvancements;
import net.mcreator.boh.compat.mc.client.BossOverlay;
import net.mcreator.boh.compat.mc.client.Camera;
import net.mcreator.boh.compat.mc.client.gui.GuiGraphics;
import net.mcreator.boh.compat.mc.client.gui.components.Button;
import net.mcreator.boh.compat.mc.client.gui.components.EditBox;
import net.mcreator.boh.compat.mc.client.gui.components.ImageButton;
import net.mcreator.boh.compat.mc.client.gui.screens.MenuScreens;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.AbstractContainerScreen;
import net.mcreator.boh.compat.mc.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.mcreator.boh.compat.mc.client.model.ArmPose;
import net.mcreator.boh.compat.mc.client.model.EntityModel;
import net.mcreator.boh.compat.mc.client.model.HumanoidModel;
import net.mcreator.boh.compat.mc.client.model.PlayerModel;
import net.mcreator.boh.compat.mc.client.model.geom.ModelLayerLocation;
import net.mcreator.boh.compat.mc.client.model.geom.ModelPart;
import net.mcreator.boh.compat.mc.client.model.geom.PartPose;
import net.mcreator.boh.compat.mc.client.model.geom.builders.CubeDeformation;
import net.mcreator.boh.compat.mc.client.model.geom.builders.CubeListBuilder;
import net.mcreator.boh.compat.mc.client.model.geom.builders.LayerDefinition;
import net.mcreator.boh.compat.mc.client.model.geom.builders.MeshDefinition;
import net.mcreator.boh.compat.mc.client.model.geom.builders.PartDefinition;
import net.mcreator.boh.compat.mc.client.particle.Particle;
import net.mcreator.boh.compat.mc.client.particle.ParticleProvider;
import net.mcreator.boh.compat.mc.client.particle.ParticleRenderType;
import net.mcreator.boh.compat.mc.client.particle.SpriteSet;
import net.mcreator.boh.compat.mc.client.renderer.BlockEntityWithoutLevelRenderer;
import net.mcreator.boh.compat.mc.client.renderer.DimensionSpecialEffects;
import net.mcreator.boh.compat.mc.client.renderer.FogMode;
import net.mcreator.boh.compat.mc.client.renderer.ShaderInstance;
import net.mcreator.boh.compat.mc.client.renderer.SkyType;
import net.mcreator.boh.compat.mc.client.renderer.entity.Context;
import net.mcreator.boh.compat.mc.client.renderer.entity.EntityRenderDispatcher;
import net.mcreator.boh.compat.mc.client.renderer.entity.EntityRenderer;
import net.mcreator.boh.compat.mc.client.renderer.entity.EntityRendererProvider;
import net.mcreator.boh.compat.mc.client.renderer.entity.HumanoidMobRenderer;
import net.mcreator.boh.compat.mc.client.renderer.entity.ThrownItemRenderer;
import net.mcreator.boh.compat.mc.client.renderer.entity.player.PlayerRenderer;
import net.mcreator.boh.compat.mc.commands.CommandSourceStack;
import net.mcreator.boh.compat.mc.commands.Commands;
import net.mcreator.boh.compat.mc.core.AxisDirection;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.core.Holder;
import net.mcreator.boh.compat.mc.core.NonNullList;
import net.mcreator.boh.compat.mc.core.Registry;
import net.mcreator.boh.compat.mc.core.Vec3i;
import net.mcreator.boh.compat.mc.core.particles.ParticleOptions;
import net.mcreator.boh.compat.mc.core.particles.ParticleType;
import net.mcreator.boh.compat.mc.core.particles.SimpleParticleType;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.mcreator.boh.compat.mc.network.chat.MutableComponent;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundGameEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundLevelEventPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.CompatPacket;
import net.mcreator.boh.compat.mc.network.protocol.game.PlayerConnection;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataAccessor;
import net.mcreator.boh.compat.mc.network.syncher.EntityDataSerializer;
import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.resources.ResourceKey;
import net.mcreator.boh.compat.mc.server.ServerAdvancementManager;
import net.mcreator.boh.compat.mc.server.level.ServerBossEvent;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.BossBarColor;
import net.mcreator.boh.compat.mc.world.BossBarOverlay;
import net.mcreator.boh.compat.mc.world.Difficulty;
import net.mcreator.boh.compat.mc.world.DifficultyInstance;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.InteractionResultHolder;
import net.mcreator.boh.compat.mc.world.MenuProvider;
import net.mcreator.boh.compat.mc.world.WorldlyContainer;
import net.mcreator.boh.compat.mc.world.effect.MobEffectCategory;
import net.mcreator.boh.compat.mc.world.entity.Builder;
import net.mcreator.boh.compat.mc.world.entity.EntityDimensions;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.EquipmentSlot;
import net.mcreator.boh.compat.mc.world.entity.HumanoidArm;
import net.mcreator.boh.compat.mc.world.entity.MobCategory;
import net.mcreator.boh.compat.mc.world.entity.MobSpawnType;
import net.mcreator.boh.compat.mc.world.entity.MobType;
import net.mcreator.boh.compat.mc.world.entity.Pose;
import net.mcreator.boh.compat.mc.world.entity.RemovalReason;
import net.mcreator.boh.compat.mc.world.entity.SpawnGroupData;
import net.mcreator.boh.compat.mc.world.entity.SpawnPlacements;
import net.mcreator.boh.compat.mc.world.entity.Type;
import net.mcreator.boh.compat.mc.world.entity.WalkAnimationState;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.AttributeInstance;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.AttributeSupplier;
import net.mcreator.boh.compat.mc.world.entity.ai.attributes.Operation;
import net.mcreator.boh.compat.mc.world.entity.ai.control.FlyingMoveControl;
import net.mcreator.boh.compat.mc.world.entity.ai.control.LookControl;
import net.mcreator.boh.compat.mc.world.entity.ai.control.MoveControl;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.Flag;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.Goal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.GoalSelector;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.MeleeAttackGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RandomStrollGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.RemoveBlockGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.WrappedGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.target.HurtByTargetGoal;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.FlyingNavigator;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.PathNavigation;
import net.mcreator.boh.compat.mc.world.entity.ai.sensing.Sensing;
import net.mcreator.boh.compat.mc.world.entity.ai.village.poi.PoiType;
import net.mcreator.boh.compat.mc.world.entity.decoration.PaintingVariant;
import net.mcreator.boh.compat.mc.world.entity.monster.RangedAttackMob;
import net.mcreator.boh.compat.mc.world.entity.npc.VillagerProfession;
import net.mcreator.boh.compat.mc.world.entity.projectile.ItemSupplier;
import net.mcreator.boh.compat.mc.world.food.FoodProperties;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.inventory.ChestMenu;
import net.mcreator.boh.compat.mc.world.inventory.ContainerLevelAccess;
import net.mcreator.boh.compat.mc.world.inventory.MenuType;
import net.mcreator.boh.compat.mc.world.item.ArmorItem;
import net.mcreator.boh.compat.mc.world.item.ArmorMaterial;
import net.mcreator.boh.compat.mc.world.item.CreativeModeTab;
import net.mcreator.boh.compat.mc.world.item.ItemDisplayContext;
import net.mcreator.boh.compat.mc.world.item.PickaxeItem;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.RecordItem;
import net.mcreator.boh.compat.mc.world.item.SwordItem;
import net.mcreator.boh.compat.mc.world.item.Tier;
import net.mcreator.boh.compat.mc.world.item.TooltipFlag;
import net.mcreator.boh.compat.mc.world.item.UseAnim;
import net.mcreator.boh.compat.mc.world.item.alchemy.BrewPotion;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.item.context.UseOnContext;
import net.mcreator.boh.compat.mc.world.item.crafting.Ingredient;
import net.mcreator.boh.compat.mc.world.item.enchantment.EnchantmentCategory;
import net.mcreator.boh.compat.mc.world.level.ClipBlock;
import net.mcreator.boh.compat.mc.world.level.ClipContext;
import net.mcreator.boh.compat.mc.world.level.ClipFluid;
import net.mcreator.boh.compat.mc.world.level.GameType;
import net.mcreator.boh.compat.mc.world.level.LightLayer;
import net.mcreator.boh.compat.mc.world.level.block.BaseEntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.ButtonBlock;
import net.mcreator.boh.compat.mc.world.level.block.DoorBlock;
import net.mcreator.boh.compat.mc.world.level.block.EntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.FallingBlock;
import net.mcreator.boh.compat.mc.world.level.block.FenceBlock;
import net.mcreator.boh.compat.mc.world.level.block.FenceGateBlock;
import net.mcreator.boh.compat.mc.world.level.block.FlowerBlock;
import net.mcreator.boh.compat.mc.world.level.block.LeavesBlock;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.PressurePlateBlock;
import net.mcreator.boh.compat.mc.world.level.block.RenderShape;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.SaplingBlock;
import net.mcreator.boh.compat.mc.world.level.block.SlabBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.StairBlock;
import net.mcreator.boh.compat.mc.world.level.block.TrapDoorBlock;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntitySupplier;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;
import net.mcreator.boh.compat.mc.world.level.block.entity.RandomizableContainerBlockEntity;
import net.mcreator.boh.compat.mc.world.level.block.grower.AbstractTreeGrower;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.ExtendedStateStore;
import net.mcreator.boh.compat.mc.world.level.block.state.OffsetType;
import net.mcreator.boh.compat.mc.world.level.block.state.StateDefinition;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockSetType;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BooleanProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.EnumProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.WoodType;
import net.mcreator.boh.compat.mc.world.level.levelgen.Types;
import net.mcreator.boh.compat.mc.world.level.levelgen.feature.Feature;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.mcreator.boh.compat.mc.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.mcreator.boh.compat.mc.world.level.material.Fluid;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.level.material.MapColor;
import net.mcreator.boh.compat.mc.world.level.material.PushReaction;
import net.mcreator.boh.compat.mc.world.level.pathfinder.Path;
import net.mcreator.boh.compat.mc.world.level.saveddata.DataHolder;
import net.mcreator.boh.compat.mc.world.level.saveddata.DimensionDataStorage;
import net.mcreator.boh.compat.mc.world.level.saveddata.SavedData;
import net.mcreator.boh.compat.mc.world.phys.AABB;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.EntityHitResult;
import net.mcreator.boh.compat.mc.world.phys.HitResult;
import net.mcreator.boh.compat.mc.world.phys.HitResultType;
import net.mcreator.boh.compat.mc.world.phys.Vec2;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.compat.mojang.blaze3d.platform.DestFactor;
import net.mcreator.boh.compat.mojang.blaze3d.platform.SourceFactor;
import net.mcreator.boh.compat.mojang.blaze3d.shaders.FogShape;
import net.mcreator.boh.compat.mojang.blaze3d.vertex.BufferBuilder;
import net.mcreator.boh.compat.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.mcreator.boh.compat.mojang.blaze3d.vertex.Mode;
import net.mcreator.boh.compat.mojang.blaze3d.vertex.RenderedBuffer;
import net.mcreator.boh.compat.mojang.blaze3d.vertex.Tesselator;
import net.mcreator.boh.compat.mojang.blaze3d.vertex.VertexBuffer;
import net.mcreator.boh.compat.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.mcreator.boh.compat.mojang.math.Vector3f;
import net.mcreator.boh.geo.AnimatableInstanceCache;
import net.mcreator.boh.geo.AnimatableManager;
import net.mcreator.boh.geo.Animation;
import net.mcreator.boh.geo.AnimationController;
import net.mcreator.boh.geo.AnimationProcessor;
import net.mcreator.boh.geo.AnimationState;
import net.mcreator.boh.geo.BakedGeoModel;
import net.mcreator.boh.geo.BoneAnimation;
import net.mcreator.boh.geo.BoneSnapshot;
import net.mcreator.boh.geo.CoreGeoBone;
import net.mcreator.boh.geo.DataTicket;
import net.mcreator.boh.geo.EasingType;
import net.mcreator.boh.geo.EntityModelData;
import net.mcreator.boh.geo.GeoAnimatable;
import net.mcreator.boh.geo.GeoArmorRenderer;
import net.mcreator.boh.geo.GeoBlockEntity;
import net.mcreator.boh.geo.GeoBlockRenderer;
import net.mcreator.boh.geo.GeoBone;
import net.mcreator.boh.geo.GeoCube;
import net.mcreator.boh.geo.GeoEntity;
import net.mcreator.boh.geo.GeoEntityRenderer;
import net.mcreator.boh.geo.GeoItem;
import net.mcreator.boh.geo.GeoItemRenderer;
import net.mcreator.boh.geo.GeoModel;
import net.mcreator.boh.geo.GeoRenderLayer;
import net.mcreator.boh.geo.GeoRenderer;
import net.mcreator.boh.geo.Keyframe;
import net.mcreator.boh.geo.KeyframeStack;
import net.mcreator.boh.geo.PlayState;
import net.mcreator.boh.geo.RawAnimation;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent.Load;

public class MGen {
    protected MGen() {
    }

    public static String texture(BlockModels.Model self, String a0) {
        return self.texture(a0);
    }

    public static boolean canBlockStay(BohBlock self, World a0, int a1, int a2, int a3) {
        return self.canBlockStay(a0, a1, a2, a3);
    }

    public static boolean canEntityDestroy(BohBlock self, IBlockAccess a0, int a1, int a2, int a3, Entity a4) {
        return self.canEntityDestroy(a0, a1, a2, a3, a4);
    }

    public static boolean canPlaceBlockAt(BohBlock self, World a0, int a1, int a2, int a3) {
        return self.canPlaceBlockAt(a0, a1, a2, a3);
    }

    public static boolean canRenderInPass(BohBlock self, int a0) {
        return self.canRenderInPass(a0);
    }

    public static boolean hasAnalogOutputSignal(BohBlock self, BlockState a0) {
        return self.hasAnalogOutputSignal(a0);
    }

    public static boolean hasComparatorInputOverride(BohBlock self) {
        return self.hasComparatorInputOverride();
    }

    public static boolean hasTileEntity(BohBlock self, int a0) {
        return self.hasTileEntity(a0);
    }

    public static boolean isNormalCube(BohBlock self, IBlockAccess a0, int a1, int a2, int a3) {
        return self.isNormalCube(a0, a1, a2, a3);
    }

    public static boolean isOpaqueCube(BohBlock self) {
        return self.isOpaqueCube();
    }

    public static boolean isRandomlyTicking(BohBlock self, BlockState a0) {
        return self.isRandomlyTicking(a0);
    }

    public static boolean isReplaceable(BohBlock self, IBlockAccess a0, int a1, int a2, int a3) {
        return self.isReplaceable(a0, a1, a2, a3);
    }

    public static boolean onBlockActivated(BohBlock self, World a0, int a1, int a2, int a3, EntityPlayer a4, int a5, float a6, float a7, float a8) {
        return self.onBlockActivated(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static boolean onBlockEventReceived(BohBlock self, World a0, int a1, int a2, int a3, int a4, int a5) {
        return self.onBlockEventReceived(a0, a1, a2, a3, a4, a5);
    }

    public static boolean onDestroyedByPlayer(BohBlock self, BlockState a0, World a1, BlockPos a2, EntityPlayer a3, boolean a4, FluidState a5) {
        return self.onDestroyedByPlayer(a0, a1, a2, a3, a4, a5);
    }

    public static boolean propagatesSkylightDown(BohBlock self, BlockState a0, IBlockAccess a1, BlockPos a2) {
        return self.propagatesSkylightDown(a0, a1, a2);
    }

    public static boolean removedByPlayer(BohBlock self, World a0, EntityPlayer a1, int a2, int a3, int a4, boolean a5) {
        return self.removedByPlayer(a0, a1, a2, a3, a4, a5);
    }

    public static boolean renderAsNormalBlock(BohBlock self) {
        return self.renderAsNormalBlock();
    }

    public static void registerDefaultState(BohBlock self, BlockState a0) {
        self.registerDefaultState(a0);
    }

    public static float getExplosionResistance(BohBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Explosion a3) {
        return self.getExplosionResistance(a0, a1, a2, a3);
    }

    public static float getExplosionResistance(BohBlock self, Entity a0, World a1, int a2, int a3, int a4, double a5, double a6, double a7) {
        return self.getExplosionResistance(a0, a1, a2, a3, a4, a5, a6, a7);
    }

    public static float getJumpFactor(BohBlock self) {
        return self.getJumpFactor();
    }

    public static float getSpeedFactor(BohBlock self) {
        return self.getSpeedFactor();
    }

    public static int getAnalogOutputSignal(BohBlock self, BlockState a0, World a1, BlockPos a2) {
        return self.getAnalogOutputSignal(a0, a1, a2);
    }

    public static int getComparatorInputOverride(BohBlock self, World a0, int a1, int a2, int a3, int a4) {
        return self.getComparatorInputOverride(a0, a1, a2, a3, a4);
    }

    public static int getFireSpreadSpeed(BohBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Direction a3) {
        return self.getFireSpreadSpeed(a0, a1, a2, a3);
    }

    public static int getFireSpreadSpeed(BohBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.getFireSpreadSpeed(a0, a1, a2, a3, a4);
    }

    public static int getFlammability(BohBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Direction a3) {
        return self.getFlammability(a0, a1, a2, a3);
    }

    public static int getFlammability(BohBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.getFlammability(a0, a1, a2, a3, a4);
    }

    public static int getLightBlock(BohBlock self, BlockState a0, IBlockAccess a1, BlockPos a2) {
        return self.getLightBlock(a0, a1, a2);
    }

    public static int getLightOpacity(BohBlock self, IBlockAccess a0, int a1, int a2, int a3) {
        return self.getLightOpacity(a0, a1, a2, a3);
    }

    public static int getLightValue(BohBlock self, IBlockAccess a0, int a1, int a2, int a3) {
        return self.getLightValue(a0, a1, a2, a3);
    }

    public static int getMobilityFlag(BohBlock self) {
        return self.getMobilityFlag();
    }

    public static int getRenderBlockPass(BohBlock self) {
        return self.getRenderBlockPass();
    }

    public static int getRenderType(BohBlock self) {
        return self.getRenderType();
    }

    public static int onBlockPlaced(BohBlock self, World a0, int a1, int a2, int a3, int a4, float a5, float a6, float a7, int a8) {
        return self.onBlockPlaced(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static Object getBlockPathType(BohBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Object a3) {
        return self.getBlockPathType(a0, a1, a2, a3);
    }

    public static Object getMenuProvider(BohBlock self, BlockState a0, World a1, BlockPos a2) {
        return self.getMenuProvider(a0, a1, a2);
    }

    public static ArrayList<ItemStack> getDrops(BohBlock self, World a0, int a1, int a2, int a3, int a4, int a5) {
        return self.getDrops(a0, a1, a2, a3, a4, a5);
    }

    public static List<ItemStack> getDrops(BohBlock self, BlockState a0, Object a1) {
        return self.getDrops(a0, a1);
    }

    public static InteractionResult use(BohBlock self, BlockState a0, World a1, BlockPos a2, EntityPlayer a3, InteractionHand a4, BlockHitResult a5) {
        return self.use(a0, a1, a2, a3, a4, a5);
    }

    public static RenderShape getRenderShape(BohBlock self, BlockState a0) {
        return self.getRenderShape(a0);
    }

    public static BlockState getStateForPlacement(BohBlock self, BlockPlaceContext a0) {
        return self.getStateForPlacement(a0);
    }

    public static BlockState updateShape(BohBlock self, BlockState a0, Direction a1, BlockState a2, World a3, BlockPos a4, BlockPos a5) {
        return self.updateShape(a0, a1, a2, a3, a4, a5);
    }

    public static VoxelShape getCollisionShape(BohBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, CollisionContext a3) {
        return self.getCollisionShape(a0, a1, a2, a3);
    }

    public static VoxelShape getShape(BohBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, CollisionContext a3) {
        return self.getShape(a0, a1, a2, a3);
    }

    public static VoxelShape getVisualShape(BohBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, CollisionContext a3) {
        return self.getVisualShape(a0, a1, a2, a3);
    }

    public static ItemStack getCloneItemStack(BohBlock self, IBlockAccess a0, BlockPos a1, BlockState a2) {
        return self.getCloneItemStack(a0, a1, a2);
    }

    public static ItemStack getPickBlock(BohBlock self, MovingObjectPosition a0, World a1, int a2, int a3, int a4, EntityPlayer a5) {
        return self.getPickBlock(a0, a1, a2, a3, a4, a5);
    }

    public static TileEntity createTileEntity(BohBlock self, World a0, int a1) {
        return self.createTileEntity(a0, a1);
    }

    public static AxisAlignedBB getCollisionBoundingBoxFromPool(BohBlock self, World a0, int a1, int a2, int a3) {
        return self.getCollisionBoundingBoxFromPool(a0, a1, a2, a3);
    }

    public static IIcon getIcon(BohBlock self, int a0, int a1) {
        return self.getIcon(a0, a1);
    }

    public static ResourceLocation registryName(BohBlock self) {
        return self.registryName();
    }

    public static void addCollisionBoxesToList(BohBlock self, World a0, int a1, int a2, int a3, AxisAlignedBB a4, List a5, Entity a6) {
        self.addCollisionBoxesToList(a0, a1, a2, a3, a4, a5, a6);
    }

    public static void animateTick(BohBlock self, BlockState a0, World a1, BlockPos a2, RandomSource a3) {
        self.animateTick(a0, a1, a2, a3);
    }

    public static void appendHoverText(BohBlock self, ItemStack a0, IBlockAccess a1, List<Component> a2, TooltipFlag a3) {
        self.appendHoverText(a0, a1, a2, a3);
    }

    public static void attack(BohBlock self, BlockState a0, World a1, BlockPos a2, EntityPlayer a3) {
        self.attack(a0, a1, a2, a3);
    }

    public static void breakBlock(BohBlock self, World a0, int a1, int a2, int a3, Block a4, int a5) {
        self.breakBlock(a0, a1, a2, a3, a4, a5);
    }

    public static void entityInside(BohBlock self, BlockState a0, World a1, BlockPos a2, Entity a3) {
        self.entityInside(a0, a1, a2, a3);
    }

    public static void neighborChanged(BohBlock self, BlockState a0, World a1, BlockPos a2, Block a3, BlockPos a4, boolean a5) {
        self.neighborChanged(a0, a1, a2, a3, a4, a5);
    }

    public static void onBlockAdded(BohBlock self, World a0, int a1, int a2, int a3) {
        self.onBlockAdded(a0, a1, a2, a3);
    }

    public static void onBlockClicked(BohBlock self, World a0, int a1, int a2, int a3, EntityPlayer a4) {
        self.onBlockClicked(a0, a1, a2, a3, a4);
    }

    public static void onBlockDestroyedByExplosion(BohBlock self, World a0, int a1, int a2, int a3, Explosion a4) {
        self.onBlockDestroyedByExplosion(a0, a1, a2, a3, a4);
    }

    public static void onBlockPlacedBy(BohBlock self, World a0, int a1, int a2, int a3, EntityLivingBase a4, ItemStack a5) {
        self.onBlockPlacedBy(a0, a1, a2, a3, a4, a5);
    }

    public static void onEntityCollidedWithBlock(BohBlock self, World a0, int a1, int a2, int a3, Entity a4) {
        self.onEntityCollidedWithBlock(a0, a1, a2, a3, a4);
    }

    public static void onEntityWalking(BohBlock self, World a0, int a1, int a2, int a3, Entity a4) {
        self.onEntityWalking(a0, a1, a2, a3, a4);
    }

    public static void onNeighborBlockChange(BohBlock self, World a0, int a1, int a2, int a3, Block a4) {
        self.onNeighborBlockChange(a0, a1, a2, a3, a4);
    }

    public static void onPlace(BohBlock self, BlockState a0, World a1, BlockPos a2, BlockState a3, boolean a4) {
        self.onPlace(a0, a1, a2, a3, a4);
    }

    public static void onRegistered(BohBlock self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static void onRemove(BohBlock self, BlockState a0, World a1, BlockPos a2, BlockState a3, boolean a4) {
        self.onRemove(a0, a1, a2, a3, a4);
    }

    public static void randomDisplayTick(BohBlock self, World a0, int a1, int a2, int a3, Random a4) {
        self.randomDisplayTick(a0, a1, a2, a3, a4);
    }

    public static void randomTick(BohBlock self, BlockState a0, WorldServer a1, BlockPos a2, RandomSource a3) {
        self.randomTick(a0, a1, a2, a3);
    }

    public static void registerBlockIcons(BohBlock self, IIconRegister a0) {
        self.registerBlockIcons(a0);
    }

    public static void setBlockBoundsBasedOnState(BohBlock self, IBlockAccess a0, int a1, int a2, int a3) {
        self.setBlockBoundsBasedOnState(a0, a1, a2, a3);
    }

    public static void setBlockBoundsForItemRender(BohBlock self) {
        self.setBlockBoundsForItemRender();
    }

    public static void setBlockIcon(BohBlock self, IIcon a0) {
        self.setBlockIcon(a0);
    }

    public static void setPlacedBy(BohBlock self, World a0, BlockPos a1, BlockState a2, EntityLivingBase a3, ItemStack a4) {
        self.setPlacedBy(a0, a1, a2, a3, a4);
    }

    public static void stepOn(BohBlock self, World a0, BlockPos a1, BlockState a2, Entity a3) {
        self.stepOn(a0, a1, a2, a3);
    }

    public static void tick(BohBlock self, BlockState a0, WorldServer a1, BlockPos a2, RandomSource a3) {
        self.tick(a0, a1, a2, a3);
    }

    public static void updateTick(BohBlock self, World a0, int a1, int a2, int a3, Random a4) {
        self.updateTick(a0, a1, a2, a3, a4);
    }

    public static void wasExploded(BohBlock self, World a0, BlockPos a1, Explosion a2) {
        self.wasExploded(a0, a1, a2);
    }

    public static void onRegistered(CompatBlock self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static Quaternionf rotation(Axis self, float a0) {
        return self.rotation(a0);
    }

    public static Quaternionf rotationDegrees(Axis self, float a0) {
        return self.rotationDegrees(a0);
    }

    public static VertexConsumer getBuffer(BufferSource self, RenderType a0) {
        return self.getBuffer(a0);
    }

    public static void endBatch(BufferSource self) {
        self.endBatch();
    }

    public static boolean canCommandSenderUseCommand(ClientDebugCommand self, ICommandSender a0) {
        return self.canCommandSenderUseCommand(a0);
    }

    public static int getRequiredPermissionLevel(ClientDebugCommand self) {
        return self.getRequiredPermissionLevel();
    }

    public static String getCommandName(ClientDebugCommand self) {
        return self.getCommandName();
    }

    public static String getCommandUsage(ClientDebugCommand self, ICommandSender a0) {
        return self.getCommandUsage(a0);
    }

    public static void processCommand(ClientDebugCommand self, ICommandSender a0, String[] a1) {
        self.processCommand(a0, a1);
    }

    public static void onClientTick(ClientEventBridge self, ClientTickEvent a0) {
        self.onClientTick(a0);
    }

    public static void onFog(ClientEventBridge self, RenderFogEvent a0) {
        self.onFog(a0);
    }

    public static void onFogColor(ClientEventBridge self, FogColors a0) {
        self.onFogColor(a0);
    }

    public static void onOverlay(ClientEventBridge self, Pre a0) {
        self.onOverlay(a0);
    }

    public static void onOverlayPost(ClientEventBridge self, Post a0) {
        self.onOverlayPost(a0);
    }

    public static void onWorldLoad(ClientEventBridge self, Load a0) {
        self.onWorldLoad(a0);
    }

    public static boolean renderWorldBlock(ElementBlockRenderer self, IBlockAccess a0, int a1, int a2, int a3, Block a4, int a5, RenderBlocks a6) {
        return self.renderWorldBlock(a0, a1, a2, a3, a4, a5, a6);
    }

    public static boolean shouldRender3DInInventory(ElementBlockRenderer self, int a0) {
        return self.shouldRender3DInInventory(a0);
    }

    public static int getRenderId(ElementBlockRenderer self) {
        return self.getRenderId();
    }

    public static void renderInventoryBlock(ElementBlockRenderer self, Block a0, int a1, int a2, RenderBlocks a3) {
        self.renderInventoryBlock(a0, a1, a2, a3);
    }

    public static boolean handleRenderType(ItemRendererBridge self, ItemStack a0, ItemRenderType a1) {
        return self.handleRenderType(a0, a1);
    }

    public static boolean shouldUseRenderHelper(ItemRendererBridge self, ItemRenderType a0, ItemStack a1, ItemRendererHelper a2) {
        return self.shouldUseRenderHelper(a0, a1, a2);
    }

    public static void renderItem(ItemRendererBridge self, ItemRenderType a0, ItemStack a1, Object... a2) {
        self.renderItem(a0, a1, a2);
    }

    public static void apply(ItemTransforms.Transform self, boolean a0, PoseStack a1) {
        self.apply(a0, a1);
    }

    public static float m00(Matrix3f self) {
        return self.m00;
    }

    public static void set_m00(Matrix3f self, float v) {
        self.m00 = v;
    }

    public static float m01(Matrix3f self) {
        return self.m01;
    }

    public static void set_m01(Matrix3f self, float v) {
        self.m01 = v;
    }

    public static float m02(Matrix3f self) {
        return self.m02;
    }

    public static void set_m02(Matrix3f self, float v) {
        self.m02 = v;
    }

    public static float m10(Matrix3f self) {
        return self.m10;
    }

    public static void set_m10(Matrix3f self, float v) {
        self.m10 = v;
    }

    public static float m11(Matrix3f self) {
        return self.m11;
    }

    public static void set_m11(Matrix3f self, float v) {
        self.m11 = v;
    }

    public static float m12(Matrix3f self) {
        return self.m12;
    }

    public static void set_m12(Matrix3f self, float v) {
        self.m12 = v;
    }

    public static float m20(Matrix3f self) {
        return self.m20;
    }

    public static void set_m20(Matrix3f self, float v) {
        self.m20 = v;
    }

    public static float m21(Matrix3f self) {
        return self.m21;
    }

    public static void set_m21(Matrix3f self, float v) {
        self.m21 = v;
    }

    public static float m22(Matrix3f self) {
        return self.m22;
    }

    public static void set_m22(Matrix3f self, float v) {
        self.m22 = v;
    }

    public static Matrix3f identity(Matrix3f self) {
        return self.identity();
    }

    public static Matrix3f mul(Matrix3f self, Matrix3f a0) {
        return self.mul(a0);
    }

    public static Matrix3f rotate(Matrix3f self, Quaternionf a0) {
        return self.rotate(a0);
    }

    public static Matrix3f scale(Matrix3f self, float a0, float a1, float a2) {
        return self.scale(a0, a1, a2);
    }

    public static Matrix3f set(Matrix3f self, Matrix3f a0) {
        return self.set(a0);
    }

    public static float m00(Matrix4f self) {
        return self.m00;
    }

    public static void set_m00(Matrix4f self, float v) {
        self.m00 = v;
    }

    public static float m01(Matrix4f self) {
        return self.m01;
    }

    public static void set_m01(Matrix4f self, float v) {
        self.m01 = v;
    }

    public static float m02(Matrix4f self) {
        return self.m02;
    }

    public static void set_m02(Matrix4f self, float v) {
        self.m02 = v;
    }

    public static float m03(Matrix4f self) {
        return self.m03;
    }

    public static void set_m03(Matrix4f self, float v) {
        self.m03 = v;
    }

    public static float m10(Matrix4f self) {
        return self.m10;
    }

    public static void set_m10(Matrix4f self, float v) {
        self.m10 = v;
    }

    public static float m11(Matrix4f self) {
        return self.m11;
    }

    public static void set_m11(Matrix4f self, float v) {
        self.m11 = v;
    }

    public static float m12(Matrix4f self) {
        return self.m12;
    }

    public static void set_m12(Matrix4f self, float v) {
        self.m12 = v;
    }

    public static float m13(Matrix4f self) {
        return self.m13;
    }

    public static void set_m13(Matrix4f self, float v) {
        self.m13 = v;
    }

    public static float m20(Matrix4f self) {
        return self.m20;
    }

    public static void set_m20(Matrix4f self, float v) {
        self.m20 = v;
    }

    public static float m21(Matrix4f self) {
        return self.m21;
    }

    public static void set_m21(Matrix4f self, float v) {
        self.m21 = v;
    }

    public static float m22(Matrix4f self) {
        return self.m22;
    }

    public static void set_m22(Matrix4f self, float v) {
        self.m22 = v;
    }

    public static float m23(Matrix4f self) {
        return self.m23;
    }

    public static void set_m23(Matrix4f self, float v) {
        self.m23 = v;
    }

    public static float m30(Matrix4f self) {
        return self.m30;
    }

    public static void set_m30(Matrix4f self, float v) {
        self.m30 = v;
    }

    public static float m31(Matrix4f self) {
        return self.m31;
    }

    public static void set_m31(Matrix4f self, float v) {
        self.m31 = v;
    }

    public static float m32(Matrix4f self) {
        return self.m32;
    }

    public static void set_m32(Matrix4f self, float v) {
        self.m32 = v;
    }

    public static float m33(Matrix4f self) {
        return self.m33;
    }

    public static void set_m33(Matrix4f self, float v) {
        self.m33 = v;
    }

    public static float transformX(Matrix4f self, float a0, float a1, float a2) {
        return self.transformX(a0, a1, a2);
    }

    public static float transformY(Matrix4f self, float a0, float a1, float a2) {
        return self.transformY(a0, a1, a2);
    }

    public static float transformZ(Matrix4f self, float a0, float a1, float a2) {
        return self.transformZ(a0, a1, a2);
    }

    public static Matrix4f identity(Matrix4f self) {
        return self.identity();
    }

    public static Matrix4f mul(Matrix4f self, Matrix4f a0) {
        return self.mul(a0);
    }

    public static Matrix4f rotate(Matrix4f self, Quaternionf a0) {
        return self.rotate(a0);
    }

    public static Matrix4f scale(Matrix4f self, float a0, float a1, float a2) {
        return self.scale(a0, a1, a2);
    }

    public static Matrix4f set(Matrix4f self, Matrix4f a0) {
        return self.set(a0);
    }

    public static Matrix4f translate(Matrix4f self, float a0, float a1, float a2) {
        return self.translate(a0, a1, a2);
    }

    public static VertexConsumer getBuffer(MultiBufferSource self, RenderType a0) {
        return self.getBuffer(a0);
    }

    public static Matrix3f normal(PoseStack.Pose self) {
        return self.normal();
    }

    public static Matrix4f pose(PoseStack.Pose self) {
        return self.pose();
    }

    public static boolean clear(PoseStack self) {
        return self.clear();
    }

    public static PoseStack.Pose last(PoseStack self) {
        return self.last();
    }

    public static void mulPose(PoseStack self, Quaternionf a0) {
        self.mulPose(a0);
    }

    public static void mulPoseMatrix(PoseStack self, Matrix4f a0) {
        self.mulPoseMatrix(a0);
    }

    public static void popPose(PoseStack self) {
        self.popPose();
    }

    public static void pushPose(PoseStack self) {
        self.pushPose();
    }

    public static void scale(PoseStack self, float a0, float a1, float a2) {
        self.scale(a0, a1, a2);
    }

    public static void setIdentity(PoseStack self) {
        self.setIdentity();
    }

    public static void translate(PoseStack self, double a0, double a1, double a2) {
        self.translate(a0, a1, a2);
    }

    public static void translate(PoseStack self, float a0, float a1, float a2) {
        self.translate(a0, a1, a2);
    }

    public static float w(Quaternionf self) {
        return self.w;
    }

    public static void set_w(Quaternionf self, float v) {
        self.w = v;
    }

    public static float x(Quaternionf self) {
        return self.x;
    }

    public static void set_x(Quaternionf self, float v) {
        self.x = v;
    }

    public static float y(Quaternionf self) {
        return self.y;
    }

    public static void set_y(Quaternionf self, float v) {
        self.y = v;
    }

    public static float z(Quaternionf self) {
        return self.z;
    }

    public static void set_z(Quaternionf self, float v) {
        self.z = v;
    }

    public static Matrix3f toMatrix3(Quaternionf self) {
        return self.toMatrix3();
    }

    public static Matrix4f toMatrix4(Quaternionf self) {
        return self.toMatrix4();
    }

    public static Quaternionf mul(Quaternionf self, Quaternionf a0) {
        return self.mul(a0);
    }

    public static Quaternionf rotateX(Quaternionf self, float a0) {
        return self.rotateX(a0);
    }

    public static Quaternionf rotateY(Quaternionf self, float a0) {
        return self.rotateY(a0);
    }

    public static Quaternionf rotateZ(Quaternionf self, float a0) {
        return self.rotateZ(a0);
    }

    public static Quaternionf rotationX(Quaternionf self, float a0) {
        return self.rotationX(a0);
    }

    public static Quaternionf rotationXYZ(Quaternionf self, float a0, float a1, float a2) {
        return self.rotationXYZ(a0, a1, a2);
    }

    public static Quaternionf rotationY(Quaternionf self, float a0) {
        return self.rotationY(a0);
    }

    public static Quaternionf rotationZ(Quaternionf self, float a0) {
        return self.rotationZ(a0);
    }

    public static boolean fullBright(RenderType self) {
        return self.fullBright();
    }

    public static RenderType.Mode mode(RenderType self) {
        return self.mode();
    }

    public static ResourceLocation texture(RenderType self) {
        return self.texture();
    }

    public static boolean isDrawing(TessellatorConsumer self) {
        return self.isDrawing();
    }

    public static VertexConsumer color(TessellatorConsumer self, float a0, float a1, float a2, float a3) {
        return self.color(a0, a1, a2, a3);
    }

    public static VertexConsumer normal(TessellatorConsumer self, float a0, float a1, float a2) {
        return self.normal(a0, a1, a2);
    }

    public static VertexConsumer overlayCoords(TessellatorConsumer self, int a0) {
        return self.overlayCoords(a0);
    }

    public static VertexConsumer uv(TessellatorConsumer self, float a0, float a1) {
        return self.uv(a0, a1);
    }

    public static VertexConsumer uv2(TessellatorConsumer self, int a0) {
        return self.uv2(a0);
    }

    public static VertexConsumer vertex(TessellatorConsumer self, double a0, double a1, double a2) {
        return self.vertex(a0, a1, a2);
    }

    public static void begin(TessellatorConsumer self, int a0, boolean a1) {
        self.begin(a0, a1);
    }

    public static void begin(TessellatorConsumer self, int a0, boolean a1, boolean a2) {
        self.begin(a0, a1, a2);
    }

    public static void end(TessellatorConsumer self) {
        self.end();
    }

    public static void endVertex(TessellatorConsumer self) {
        self.endVertex();
    }

    public static VertexConsumer color(VertexConsumer self, float a0, float a1, float a2, float a3) {
        return self.color(a0, a1, a2, a3);
    }

    public static VertexConsumer normal(VertexConsumer self, float a0, float a1, float a2) {
        return self.normal(a0, a1, a2);
    }

    public static VertexConsumer overlayCoords(VertexConsumer self, int a0) {
        return self.overlayCoords(a0);
    }

    public static VertexConsumer uv(VertexConsumer self, float a0, float a1) {
        return self.uv(a0, a1);
    }

    public static VertexConsumer uv2(VertexConsumer self, int a0) {
        return self.uv2(a0);
    }

    public static VertexConsumer vertex(VertexConsumer self, double a0, double a1, double a2) {
        return self.vertex(a0, a1, a2);
    }

    public static void endVertex(VertexConsumer self) {
        self.endVertex();
    }

    public static VertexConsumer color(VertexConsumer self, int a0) {
        return self.color(a0);
    }

    public static VertexConsumer color(VertexConsumer self, int a0, int a1, int a2, int a3) {
        return self.color(a0, a1, a2, a3);
    }

    public static VertexConsumer normal(VertexConsumer self, Matrix3f a0, float a1, float a2, float a3) {
        return self.normal(a0, a1, a2, a3);
    }

    public static VertexConsumer overlayCoords(VertexConsumer self, int a0, int a1) {
        return self.overlayCoords(a0, a1);
    }

    public static VertexConsumer uv2(VertexConsumer self, int a0, int a1) {
        return self.uv2(a0, a1);
    }

    public static VertexConsumer vertex(VertexConsumer self, Matrix4f a0, float a1, float a2, float a3) {
        return self.vertex(a0, a1, a2, a3);
    }

    public static void vertex(
        VertexConsumer self,
        float a0,
        float a1,
        float a2,
        float a3,
        float a4,
        float a5,
        float a6,
        float a7,
        float a8,
        int a9,
        int a10,
        float a11,
        float a12,
        float a13
    ) {
        self.vertex(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13);
    }

    public static IIcon get(ParticleEngine.Sprites self, int a0, int a1) {
        return self.get(a0, a1);
    }

    public static IIcon get(ParticleEngine.Sprites self, Random a0) {
        return self.get(a0);
    }

    public static void onStitch(ParticleEngine self, net.minecraftforge.client.event.TextureStitchEvent.Pre a0) {
        self.onStitch(a0);
    }

    public static boolean hasStatusIcon(BohMobEffect self) {
        return self.hasStatusIcon();
    }

    public static boolean isBeneficial(BohMobEffect self) {
        return self.isBeneficial();
    }

    public static boolean isDurationEffectTick(BohMobEffect self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static boolean isInstant(BohMobEffect self) {
        return self.isInstant();
    }

    public static boolean isInstantenous(BohMobEffect self) {
        return self.isInstantenous();
    }

    public static boolean isReady(BohMobEffect self, int a0, int a1) {
        return self.isReady(a0, a1);
    }

    public static boolean shouldRender(BohMobEffect self, PotionEffect a0) {
        return self.shouldRender(a0);
    }

    public static boolean shouldRenderInvText(BohMobEffect self, PotionEffect a0) {
        return self.shouldRenderInvText(a0);
    }

    public static List<ItemStack> getCurativeItems(BohMobEffect self) {
        return self.getCurativeItems();
    }

    public static BohMobEffect addAttributeModifier(BohMobEffect self, IAttribute a0, String a1, double a2, Operation a3) {
        return self.addAttributeModifier(a0, a1, a2, a3);
    }

    public static MobEffectCategory getCategory(BohMobEffect self) {
        return self.getCategory();
    }

    public static void addAttributeModifiers(BohMobEffect self, EntityLivingBase a0, BaseAttributeMap a1, int a2) {
        self.addAttributeModifiers(a0, a1, a2);
    }

    public static void affectEntity(BohMobEffect self, EntityLivingBase a0, EntityLivingBase a1, int a2, double a3) {
        self.affectEntity(a0, a1, a2, a3);
    }

    public static void applyAttributesModifiersToEntity(BohMobEffect self, EntityLivingBase a0, BaseAttributeMap a1, int a2) {
        self.applyAttributesModifiersToEntity(a0, a1, a2);
    }

    public static void applyEffectTick(BohMobEffect self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static void applyInstantenousEffect(BohMobEffect self, Entity a0, Entity a1, EntityLivingBase a2, int a3, double a4) {
        self.applyInstantenousEffect(a0, a1, a2, a3, a4);
    }

    public static void initializeClient(BohMobEffect self, Consumer<IClientMobEffectExtensions> a0) {
        self.initializeClient(a0);
    }

    public static void performEffect(BohMobEffect self, EntityLivingBase a0, int a1) {
        self.performEffect(a0, a1);
    }

    public static void removeAttributeModifiers(BohMobEffect self, EntityLivingBase a0, BaseAttributeMap a1, int a2) {
        self.removeAttributeModifiers(a0, a1, a2);
    }

    public static void removeAttributesModifiersFromEntity(BohMobEffect self, EntityLivingBase a0, BaseAttributeMap a1, int a2) {
        self.removeAttributesModifiersFromEntity(a0, a1, a2);
    }

    public static void renderInventoryEffect(BohMobEffect self, int a0, int a1, PotionEffect a2, Minecraft a3) {
        self.renderInventoryEffect(a0, a1, a2, a3);
    }

    public static void setIconTexture(BohMobEffect self, ResourceLocation a0) {
        self.setIconTexture(a0);
    }

    public static boolean isDurationEffectTick(ExtraEffects.ConduitPower self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static void applyEffectTick(ExtraEffects.ConduitPower self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static boolean isDurationEffectTick(ExtraEffects.DolphinsGrace self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static void applyEffectTick(ExtraEffects.DolphinsGrace self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static boolean isDurationEffectTick(ExtraEffects.Levitation self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static void applyEffectTick(ExtraEffects.Levitation self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static boolean isDurationEffectTick(ExtraEffects.SlowFalling self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static void applyEffectTick(ExtraEffects.SlowFalling self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static boolean isDurationEffectTick(KuroEffects.Aggression self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static void applyEffectTick(KuroEffects.Aggression self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static void removeAttributesModifiersFromEntity(KuroEffects.Aggression self, EntityLivingBase a0, BaseAttributeMap a1, int a2) {
        self.removeAttributesModifiersFromEntity(a0, a1, a2);
    }

    public static boolean isDurationEffectTick(KuroEffects.Bleeding self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static void applyEffectTick(KuroEffects.Bleeding self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static boolean isDurationEffectTick(KuroEffects.Flashbanged self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static void applyEffectTick(KuroEffects.Flashbanged self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static boolean isDurationEffectTick(KuroEffects.Paranoia self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static void applyEffectTick(KuroEffects.Paranoia self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static boolean isDurationEffectTick(KuroEffects.Radiation self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static void applyEffectTick(KuroEffects.Radiation self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static boolean isDurationEffectTick(KuroEffects.Sleep self, int a0, int a1) {
        return self.isDurationEffectTick(a0, a1);
    }

    public static void applyEffectTick(KuroEffects.Sleep self, EntityLivingBase a0, int a1) {
        self.applyEffectTick(a0, a1);
    }

    public static boolean canAttackWithItem(BohAbstractArrow self) {
        return self.canAttackWithItem();
    }

    public static boolean canHitEntity(BohAbstractArrow self, Entity a0) {
        return self.canHitEntity(a0);
    }

    public static boolean isCritArrow(BohAbstractArrow self) {
        return self.isCritArrow();
    }

    public static float getShadowSize(BohAbstractArrow self) {
        return self.getShadowSize();
    }

    public static void addAdditionalSaveData(BohAbstractArrow self, NBTTagCompound a0) {
        self.addAdditionalSaveData(a0);
    }

    public static void onCollideWithPlayer(BohAbstractArrow self, EntityPlayer a0) {
        self.onCollideWithPlayer(a0);
    }

    public static void onUpdate(BohAbstractArrow self) {
        self.onUpdate();
    }

    public static void playerTouch(BohAbstractArrow self, EntityPlayer a0) {
        self.playerTouch(a0);
    }

    public static void readAdditionalSaveData(BohAbstractArrow self, NBTTagCompound a0) {
        self.readAdditionalSaveData(a0);
    }

    public static void readSpawnData(BohAbstractArrow self, ByteBuf a0) {
        self.readSpawnData(a0);
    }

    public static void setSilentArrow(BohAbstractArrow self, boolean a0) {
        self.setSilentArrow(a0);
    }

    public static void setVelocity(BohAbstractArrow self, double a0, double a1, double a2) {
        self.setVelocity(a0, a1, a2);
    }

    public static void tick(BohAbstractArrow self) {
        self.tick();
    }

    public static void writeSpawnData(BohAbstractArrow self, ByteBuf a0) {
        self.writeSpawnData(a0);
    }

    public static boolean attackEntityAsMob(BohAnimal self, Entity a0) {
        return self.attackEntityAsMob(a0);
    }

    public static boolean attackEntityFrom(BohAnimal self, DamageSource a0, float a1) {
        return self.attackEntityFrom(a0, a1);
    }

    public static boolean canBePushed(BohAnimal self) {
        return self.canBePushed();
    }

    public static boolean canChangeDimensions(BohAnimal self) {
        return self.canChangeDimensions();
    }

    public static boolean canCollideWith(BohAnimal self, Entity a0) {
        return self.canCollideWith(a0);
    }

    public static boolean causeFallDamage(BohAnimal self, float a0, float a1, DamageSource a2) {
        return self.causeFallDamage(a0, a1, a2);
    }

    public static boolean checkSpawnObstruction(BohAnimal self, World a0) {
        return self.checkSpawnObstruction(a0);
    }

    public static boolean fireImmune(BohAnimal self) {
        return self.fireImmune();
    }

    public static boolean getCanSpawnHere(BohAnimal self) {
        return self.getCanSpawnHere();
    }

    public static boolean ignoreExplosion(BohAnimal self) {
        return self.ignoreExplosion();
    }

    public static boolean interact(BohAnimal self, EntityPlayer a0) {
        return self.interact(a0);
    }

    public static boolean isBreedingItem(BohAnimal self, ItemStack a0) {
        return self.isBreedingItem(a0);
    }

    public static boolean isCreatureType(BohAnimal self, EnumCreatureType a0, boolean a1) {
        return self.isCreatureType(a0, a1);
    }

    public static boolean isFood(BohAnimal self, ItemStack a0) {
        return self.isFood(a0);
    }

    public static boolean isPushable(BohAnimal self) {
        return self.isPushable();
    }

    public static boolean isPushedByFluid(BohAnimal self) {
        return self.isPushedByFluid();
    }

    public static boolean isPushedByWater(BohAnimal self) {
        return self.isPushedByWater();
    }

    public static boolean removeWhenFarAway(BohAnimal self, double a0) {
        return self.removeWhenFarAway(a0);
    }

    public static double getMountedYOffset(BohAnimal self) {
        return self.getMountedYOffset();
    }

    public static double getMyRidingOffset(BohAnimal self) {
        return self.getMyRidingOffset();
    }

    public static double getPassengersRidingOffset(BohAnimal self) {
        return self.getPassengersRidingOffset();
    }

    public static double getYOffset(BohAnimal self) {
        return self.getYOffset();
    }

    public static int getTotalArmorValue(BohAnimal self) {
        return self.getTotalArmorValue();
    }

    public static SynchedEntityData bohEntityData(BohAnimal self) {
        return self.bohEntityData();
    }

    public static SynchedEntityData getEntityDataModern(BohAnimal self) {
        return self.getEntityDataModern();
    }

    public static SoundEvent getAmbientSound(BohAnimal self) {
        return self.getAmbientSound();
    }

    public static SoundEvent getDeathSoundEvent(BohAnimal self) {
        return self.getDeathSoundEvent();
    }

    public static SoundEvent getHurtSound(BohAnimal self, DamageSource a0) {
        return self.getHurtSound(a0);
    }

    public static InteractionResult mobInteract(BohAnimal self, EntityPlayer a0, InteractionHand a1) {
        return self.mobInteract(a0, a1);
    }

    public static EntityDimensions getDimensions(BohAnimal self, Pose a0) {
        return self.getDimensions(a0);
    }

    public static EntityType<?> bohType(BohAnimal self) {
        return self.bohType();
    }

    public static MobType getMobType(BohAnimal self) {
        return self.getMobType();
    }

    public static SpawnGroupData finalizeSpawn(BohAnimal self, World a0, DifficultyInstance a1, MobSpawnType a2, SpawnGroupData a3, NBTTagCompound a4) {
        return self.finalizeSpawn(a0, a1, a2, a3, a4);
    }

    public static LookControl bohLookControl(BohAnimal self) {
        return self.bohLookControl();
    }

    public static MoveControl bohMoveControl(BohAnimal self) {
        return self.bohMoveControl();
    }

    public static GoalSelector bohGoals(BohAnimal self) {
        return self.bohGoals();
    }

    public static GoalSelector bohTargets(BohAnimal self) {
        return self.bohTargets();
    }

    public static PathNavigation bohNavigation(BohAnimal self) {
        return self.bohNavigation();
    }

    public static EntityAgeable createChild(BohAnimal self, EntityAgeable a0) {
        return self.createChild(a0);
    }

    public static EntityAgeable getBreedOffspring(BohAnimal self, World a0, EntityAgeable a1) {
        return self.getBreedOffspring(a0, a1);
    }

    public static EnumCreatureAttribute getCreatureAttribute(BohAnimal self) {
        return self.getCreatureAttribute();
    }

    public static IEntityLivingData onSpawnWithEgg(BohAnimal self, IEntityLivingData a0) {
        return self.onSpawnWithEgg(a0);
    }

    public static void addAdditionalSaveData(BohAnimal self, NBTTagCompound a0) {
        self.addAdditionalSaveData(a0);
    }

    public static void aiStep(BohAnimal self) {
        self.aiStep();
    }

    public static void awardKillScore(BohAnimal self, Entity a0, int a1, DamageSource a2) {
        self.awardKillScore(a0, a1, a2);
    }

    public static void baseTick(BohAnimal self) {
        self.baseTick();
    }

    public static void die(BohAnimal self, DamageSource a0) {
        self.die(a0);
    }

    public static void moveEntityWithHeading(BohAnimal self, float a0, float a1) {
        self.moveEntityWithHeading(a0, a1);
    }

    public static void onCollideWithPlayer(BohAnimal self, EntityPlayer a0) {
        self.onCollideWithPlayer(a0);
    }

    public static void onDeath(BohAnimal self, DamageSource a0) {
        self.onDeath(a0);
    }

    public static void onEntityUpdate(BohAnimal self) {
        self.onEntityUpdate();
    }

    public static void onKillEntity(BohAnimal self, EntityLivingBase a0) {
        self.onKillEntity(a0);
    }

    public static void onLivingUpdate(BohAnimal self) {
        self.onLivingUpdate();
    }

    public static void onStruckByLightning(BohAnimal self, EntityLightningBolt a0) {
        self.onStruckByLightning(a0);
    }

    public static void onUpdate(BohAnimal self) {
        self.onUpdate();
    }

    public static void playerTouch(BohAnimal self, EntityPlayer a0) {
        self.playerTouch(a0);
    }

    public static void readAdditionalSaveData(BohAnimal self, NBTTagCompound a0) {
        self.readAdditionalSaveData(a0);
    }

    public static void readEntityFromNBT(BohAnimal self, NBTTagCompound a0) {
        self.readEntityFromNBT(a0);
    }

    public static void setSizeCompat(BohAnimal self, float a0, float a1) {
        self.setSizeCompat(a0, a1);
    }

    public static void startSeenByPlayer(BohAnimal self, EntityPlayerMP a0) {
        self.startSeenByPlayer(a0);
    }

    public static void stopSeenByPlayer(BohAnimal self, EntityPlayerMP a0) {
        self.stopSeenByPlayer(a0);
    }

    public static void thunderHit(BohAnimal self, World a0, EntityLightningBolt a1) {
        self.thunderHit(a0, a1);
    }

    public static void tick(BohAnimal self) {
        self.tick();
    }

    public static void travel(BohAnimal self, Vec3 a0) {
        self.travel(a0);
    }

    public static void travelToDimension(BohAnimal self, int a0) {
        self.travelToDimension(a0);
    }

    public static void writeEntityToNBT(BohAnimal self, NBTTagCompound a0) {
        self.writeEntityToNBT(a0);
    }

    public static void onUpdate(BohAreaEffectCloud self) {
        self.onUpdate();
    }

    public static void readSpawnData(BohAreaEffectCloud self, ByteBuf a0) {
        self.readSpawnData(a0);
    }

    public static void writeSpawnData(BohAreaEffectCloud self, ByteBuf a0) {
        self.writeSpawnData(a0);
    }

    public static boolean isVisualOnly(BohLightningBolt self) {
        return self.isVisualOnly();
    }

    public static void onUpdate(BohLightningBolt self) {
        self.onUpdate();
    }

    public static SynchedEntityData bohEntityData(BohMob self) {
        return self.bohEntityData();
    }

    public static EntityType<?> bohType(BohMob self) {
        return self.bohType();
    }

    public static LookControl bohLookControl(BohMob self) {
        return self.bohLookControl();
    }

    public static MoveControl bohMoveControl(BohMob self) {
        return self.bohMoveControl();
    }

    public static GoalSelector bohGoals(BohMob self) {
        return self.bohGoals();
    }

    public static GoalSelector bohTargets(BohMob self) {
        return self.bohTargets();
    }

    public static PathNavigation bohNavigation(BohMob self) {
        return self.bohNavigation();
    }

    public static void setSizeCompat(BohMob self, float a0, float a1) {
        self.setSizeCompat(a0, a1);
    }

    public static boolean attackEntityAsMob(BohMonster self, Entity a0) {
        return self.attackEntityAsMob(a0);
    }

    public static boolean attackEntityFrom(BohMonster self, DamageSource a0, float a1) {
        return self.attackEntityFrom(a0, a1);
    }

    public static boolean canBePushed(BohMonster self) {
        return self.canBePushed();
    }

    public static boolean canChangeDimensions(BohMonster self) {
        return self.canChangeDimensions();
    }

    public static boolean canCollideWith(BohMonster self, Entity a0) {
        return self.canCollideWith(a0);
    }

    public static boolean causeFallDamage(BohMonster self, float a0, float a1, DamageSource a2) {
        return self.causeFallDamage(a0, a1, a2);
    }

    public static boolean checkSpawnObstruction(BohMonster self, World a0) {
        return self.checkSpawnObstruction(a0);
    }

    public static boolean fireImmune(BohMonster self) {
        return self.fireImmune();
    }

    public static boolean getCanSpawnHere(BohMonster self) {
        return self.getCanSpawnHere();
    }

    public static boolean ignoreExplosion(BohMonster self) {
        return self.ignoreExplosion();
    }

    public static boolean interact(BohMonster self, EntityPlayer a0) {
        return self.interact(a0);
    }

    public static boolean isCreatureType(BohMonster self, EnumCreatureType a0, boolean a1) {
        return self.isCreatureType(a0, a1);
    }

    public static boolean isPushable(BohMonster self) {
        return self.isPushable();
    }

    public static boolean isPushedByFluid(BohMonster self) {
        return self.isPushedByFluid();
    }

    public static boolean isPushedByWater(BohMonster self) {
        return self.isPushedByWater();
    }

    public static boolean removeWhenFarAway(BohMonster self, double a0) {
        return self.removeWhenFarAway(a0);
    }

    public static double getMountedYOffset(BohMonster self) {
        return self.getMountedYOffset();
    }

    public static double getMyRidingOffset(BohMonster self) {
        return self.getMyRidingOffset();
    }

    public static double getPassengersRidingOffset(BohMonster self) {
        return self.getPassengersRidingOffset();
    }

    public static double getYOffset(BohMonster self) {
        return self.getYOffset();
    }

    public static int getTotalArmorValue(BohMonster self) {
        return self.getTotalArmorValue();
    }

    public static SynchedEntityData bohEntityData(BohMonster self) {
        return self.bohEntityData();
    }

    public static SynchedEntityData getEntityDataModern(BohMonster self) {
        return self.getEntityDataModern();
    }

    public static SoundEvent getAmbientSound(BohMonster self) {
        return self.getAmbientSound();
    }

    public static SoundEvent getDeathSoundEvent(BohMonster self) {
        return self.getDeathSoundEvent();
    }

    public static SoundEvent getHurtSound(BohMonster self, DamageSource a0) {
        return self.getHurtSound(a0);
    }

    public static InteractionResult mobInteract(BohMonster self, EntityPlayer a0, InteractionHand a1) {
        return self.mobInteract(a0, a1);
    }

    public static EntityDimensions getDimensions(BohMonster self, Pose a0) {
        return self.getDimensions(a0);
    }

    public static EntityType<?> bohType(BohMonster self) {
        return self.bohType();
    }

    public static MobType getMobType(BohMonster self) {
        return self.getMobType();
    }

    public static SpawnGroupData finalizeSpawn(BohMonster self, World a0, DifficultyInstance a1, MobSpawnType a2, SpawnGroupData a3, NBTTagCompound a4) {
        return self.finalizeSpawn(a0, a1, a2, a3, a4);
    }

    public static LookControl bohLookControl(BohMonster self) {
        return self.bohLookControl();
    }

    public static MoveControl bohMoveControl(BohMonster self) {
        return self.bohMoveControl();
    }

    public static GoalSelector bohGoals(BohMonster self) {
        return self.bohGoals();
    }

    public static GoalSelector bohTargets(BohMonster self) {
        return self.bohTargets();
    }

    public static PathNavigation bohNavigation(BohMonster self) {
        return self.bohNavigation();
    }

    public static EnumCreatureAttribute getCreatureAttribute(BohMonster self) {
        return self.getCreatureAttribute();
    }

    public static IEntityLivingData onSpawnWithEgg(BohMonster self, IEntityLivingData a0) {
        return self.onSpawnWithEgg(a0);
    }

    public static void addAdditionalSaveData(BohMonster self, NBTTagCompound a0) {
        self.addAdditionalSaveData(a0);
    }

    public static void aiStep(BohMonster self) {
        self.aiStep();
    }

    public static void awardKillScore(BohMonster self, Entity a0, int a1, DamageSource a2) {
        self.awardKillScore(a0, a1, a2);
    }

    public static void baseTick(BohMonster self) {
        self.baseTick();
    }

    public static void die(BohMonster self, DamageSource a0) {
        self.die(a0);
    }

    public static void moveEntityWithHeading(BohMonster self, float a0, float a1) {
        self.moveEntityWithHeading(a0, a1);
    }

    public static void onCollideWithPlayer(BohMonster self, EntityPlayer a0) {
        self.onCollideWithPlayer(a0);
    }

    public static void onDeath(BohMonster self, DamageSource a0) {
        self.onDeath(a0);
    }

    public static void onEntityUpdate(BohMonster self) {
        self.onEntityUpdate();
    }

    public static void onKillEntity(BohMonster self, EntityLivingBase a0) {
        self.onKillEntity(a0);
    }

    public static void onLivingUpdate(BohMonster self) {
        self.onLivingUpdate();
    }

    public static void onStruckByLightning(BohMonster self, EntityLightningBolt a0) {
        self.onStruckByLightning(a0);
    }

    public static void onUpdate(BohMonster self) {
        self.onUpdate();
    }

    public static void playerTouch(BohMonster self, EntityPlayer a0) {
        self.playerTouch(a0);
    }

    public static void readAdditionalSaveData(BohMonster self, NBTTagCompound a0) {
        self.readAdditionalSaveData(a0);
    }

    public static void readEntityFromNBT(BohMonster self, NBTTagCompound a0) {
        self.readEntityFromNBT(a0);
    }

    public static void setSizeCompat(BohMonster self, float a0, float a1) {
        self.setSizeCompat(a0, a1);
    }

    public static void startSeenByPlayer(BohMonster self, EntityPlayerMP a0) {
        self.startSeenByPlayer(a0);
    }

    public static void stopSeenByPlayer(BohMonster self, EntityPlayerMP a0) {
        self.stopSeenByPlayer(a0);
    }

    public static void thunderHit(BohMonster self, World a0, EntityLightningBolt a1) {
        self.thunderHit(a0, a1);
    }

    public static void tick(BohMonster self) {
        self.tick();
    }

    public static void travel(BohMonster self, Vec3 a0) {
        self.travel(a0);
    }

    public static void travelToDimension(BohMonster self, int a0) {
        self.travelToDimension(a0);
    }

    public static void writeEntityToNBT(BohMonster self, NBTTagCompound a0) {
        self.writeEntityToNBT(a0);
    }

    public static boolean attackEntityAsMob(BohPathfinderMob self, Entity a0) {
        return self.attackEntityAsMob(a0);
    }

    public static boolean attackEntityFrom(BohPathfinderMob self, DamageSource a0, float a1) {
        return self.attackEntityFrom(a0, a1);
    }

    public static boolean canBePushed(BohPathfinderMob self) {
        return self.canBePushed();
    }

    public static boolean canChangeDimensions(BohPathfinderMob self) {
        return self.canChangeDimensions();
    }

    public static boolean canCollideWith(BohPathfinderMob self, Entity a0) {
        return self.canCollideWith(a0);
    }

    public static boolean causeFallDamage(BohPathfinderMob self, float a0, float a1, DamageSource a2) {
        return self.causeFallDamage(a0, a1, a2);
    }

    public static boolean checkSpawnObstruction(BohPathfinderMob self, World a0) {
        return self.checkSpawnObstruction(a0);
    }

    public static boolean fireImmune(BohPathfinderMob self) {
        return self.fireImmune();
    }

    public static boolean getCanSpawnHere(BohPathfinderMob self) {
        return self.getCanSpawnHere();
    }

    public static boolean ignoreExplosion(BohPathfinderMob self) {
        return self.ignoreExplosion();
    }

    public static boolean interact(BohPathfinderMob self, EntityPlayer a0) {
        return self.interact(a0);
    }

    public static boolean isCreatureType(BohPathfinderMob self, EnumCreatureType a0, boolean a1) {
        return self.isCreatureType(a0, a1);
    }

    public static boolean isPushable(BohPathfinderMob self) {
        return self.isPushable();
    }

    public static boolean isPushedByFluid(BohPathfinderMob self) {
        return self.isPushedByFluid();
    }

    public static boolean isPushedByWater(BohPathfinderMob self) {
        return self.isPushedByWater();
    }

    public static boolean removeWhenFarAway(BohPathfinderMob self, double a0) {
        return self.removeWhenFarAway(a0);
    }

    public static double getMountedYOffset(BohPathfinderMob self) {
        return self.getMountedYOffset();
    }

    public static double getMyRidingOffset(BohPathfinderMob self) {
        return self.getMyRidingOffset();
    }

    public static double getPassengersRidingOffset(BohPathfinderMob self) {
        return self.getPassengersRidingOffset();
    }

    public static double getYOffset(BohPathfinderMob self) {
        return self.getYOffset();
    }

    public static int getTotalArmorValue(BohPathfinderMob self) {
        return self.getTotalArmorValue();
    }

    public static SynchedEntityData bohEntityData(BohPathfinderMob self) {
        return self.bohEntityData();
    }

    public static SynchedEntityData getEntityDataModern(BohPathfinderMob self) {
        return self.getEntityDataModern();
    }

    public static SoundEvent getAmbientSound(BohPathfinderMob self) {
        return self.getAmbientSound();
    }

    public static SoundEvent getDeathSoundEvent(BohPathfinderMob self) {
        return self.getDeathSoundEvent();
    }

    public static SoundEvent getHurtSound(BohPathfinderMob self, DamageSource a0) {
        return self.getHurtSound(a0);
    }

    public static InteractionResult mobInteract(BohPathfinderMob self, EntityPlayer a0, InteractionHand a1) {
        return self.mobInteract(a0, a1);
    }

    public static EntityDimensions getDimensions(BohPathfinderMob self, Pose a0) {
        return self.getDimensions(a0);
    }

    public static EntityType<?> bohType(BohPathfinderMob self) {
        return self.bohType();
    }

    public static MobType getMobType(BohPathfinderMob self) {
        return self.getMobType();
    }

    public static SpawnGroupData finalizeSpawn(BohPathfinderMob self, World a0, DifficultyInstance a1, MobSpawnType a2, SpawnGroupData a3, NBTTagCompound a4) {
        return self.finalizeSpawn(a0, a1, a2, a3, a4);
    }

    public static LookControl bohLookControl(BohPathfinderMob self) {
        return self.bohLookControl();
    }

    public static MoveControl bohMoveControl(BohPathfinderMob self) {
        return self.bohMoveControl();
    }

    public static GoalSelector bohGoals(BohPathfinderMob self) {
        return self.bohGoals();
    }

    public static GoalSelector bohTargets(BohPathfinderMob self) {
        return self.bohTargets();
    }

    public static PathNavigation bohNavigation(BohPathfinderMob self) {
        return self.bohNavigation();
    }

    public static EnumCreatureAttribute getCreatureAttribute(BohPathfinderMob self) {
        return self.getCreatureAttribute();
    }

    public static IEntityLivingData onSpawnWithEgg(BohPathfinderMob self, IEntityLivingData a0) {
        return self.onSpawnWithEgg(a0);
    }

    public static void addAdditionalSaveData(BohPathfinderMob self, NBTTagCompound a0) {
        self.addAdditionalSaveData(a0);
    }

    public static void aiStep(BohPathfinderMob self) {
        self.aiStep();
    }

    public static void awardKillScore(BohPathfinderMob self, Entity a0, int a1, DamageSource a2) {
        self.awardKillScore(a0, a1, a2);
    }

    public static void baseTick(BohPathfinderMob self) {
        self.baseTick();
    }

    public static void die(BohPathfinderMob self, DamageSource a0) {
        self.die(a0);
    }

    public static void moveEntityWithHeading(BohPathfinderMob self, float a0, float a1) {
        self.moveEntityWithHeading(a0, a1);
    }

    public static void onCollideWithPlayer(BohPathfinderMob self, EntityPlayer a0) {
        self.onCollideWithPlayer(a0);
    }

    public static void onDeath(BohPathfinderMob self, DamageSource a0) {
        self.onDeath(a0);
    }

    public static void onEntityUpdate(BohPathfinderMob self) {
        self.onEntityUpdate();
    }

    public static void onKillEntity(BohPathfinderMob self, EntityLivingBase a0) {
        self.onKillEntity(a0);
    }

    public static void onLivingUpdate(BohPathfinderMob self) {
        self.onLivingUpdate();
    }

    public static void onStruckByLightning(BohPathfinderMob self, EntityLightningBolt a0) {
        self.onStruckByLightning(a0);
    }

    public static void onUpdate(BohPathfinderMob self) {
        self.onUpdate();
    }

    public static void playerTouch(BohPathfinderMob self, EntityPlayer a0) {
        self.playerTouch(a0);
    }

    public static void readAdditionalSaveData(BohPathfinderMob self, NBTTagCompound a0) {
        self.readAdditionalSaveData(a0);
    }

    public static void readEntityFromNBT(BohPathfinderMob self, NBTTagCompound a0) {
        self.readEntityFromNBT(a0);
    }

    public static void setSizeCompat(BohPathfinderMob self, float a0, float a1) {
        self.setSizeCompat(a0, a1);
    }

    public static void startSeenByPlayer(BohPathfinderMob self, EntityPlayerMP a0) {
        self.startSeenByPlayer(a0);
    }

    public static void stopSeenByPlayer(BohPathfinderMob self, EntityPlayerMP a0) {
        self.stopSeenByPlayer(a0);
    }

    public static void thunderHit(BohPathfinderMob self, World a0, EntityLightningBolt a1) {
        self.thunderHit(a0, a1);
    }

    public static void tick(BohPathfinderMob self) {
        self.tick();
    }

    public static void travel(BohPathfinderMob self, Vec3 a0) {
        self.travel(a0);
    }

    public static void travelToDimension(BohPathfinderMob self, int a0) {
        self.travelToDimension(a0);
    }

    public static void writeEntityToNBT(BohPathfinderMob self, NBTTagCompound a0) {
        self.writeEntityToNBT(a0);
    }

    public static boolean attackEntityAsMob(BohSpider self, Entity a0) {
        return self.attackEntityAsMob(a0);
    }

    public static boolean attackEntityFrom(BohSpider self, DamageSource a0, float a1) {
        return self.attackEntityFrom(a0, a1);
    }

    public static boolean canBePushed(BohSpider self) {
        return self.canBePushed();
    }

    public static boolean canChangeDimensions(BohSpider self) {
        return self.canChangeDimensions();
    }

    public static boolean canCollideWith(BohSpider self, Entity a0) {
        return self.canCollideWith(a0);
    }

    public static boolean causeFallDamage(BohSpider self, float a0, float a1, DamageSource a2) {
        return self.causeFallDamage(a0, a1, a2);
    }

    public static boolean checkSpawnObstruction(BohSpider self, World a0) {
        return self.checkSpawnObstruction(a0);
    }

    public static boolean fireImmune(BohSpider self) {
        return self.fireImmune();
    }

    public static boolean getCanSpawnHere(BohSpider self) {
        return self.getCanSpawnHere();
    }

    public static boolean ignoreExplosion(BohSpider self) {
        return self.ignoreExplosion();
    }

    public static boolean interact(BohSpider self, EntityPlayer a0) {
        return self.interact(a0);
    }

    public static boolean isCreatureType(BohSpider self, EnumCreatureType a0, boolean a1) {
        return self.isCreatureType(a0, a1);
    }

    public static boolean isPushable(BohSpider self) {
        return self.isPushable();
    }

    public static boolean isPushedByFluid(BohSpider self) {
        return self.isPushedByFluid();
    }

    public static boolean isPushedByWater(BohSpider self) {
        return self.isPushedByWater();
    }

    public static boolean removeWhenFarAway(BohSpider self, double a0) {
        return self.removeWhenFarAway(a0);
    }

    public static double getMountedYOffset(BohSpider self) {
        return self.getMountedYOffset();
    }

    public static double getMyRidingOffset(BohSpider self) {
        return self.getMyRidingOffset();
    }

    public static double getPassengersRidingOffset(BohSpider self) {
        return self.getPassengersRidingOffset();
    }

    public static double getYOffset(BohSpider self) {
        return self.getYOffset();
    }

    public static int getTotalArmorValue(BohSpider self) {
        return self.getTotalArmorValue();
    }

    public static SynchedEntityData bohEntityData(BohSpider self) {
        return self.bohEntityData();
    }

    public static SynchedEntityData getEntityDataModern(BohSpider self) {
        return self.getEntityDataModern();
    }

    public static SoundEvent getAmbientSound(BohSpider self) {
        return self.getAmbientSound();
    }

    public static SoundEvent getDeathSoundEvent(BohSpider self) {
        return self.getDeathSoundEvent();
    }

    public static SoundEvent getHurtSound(BohSpider self, DamageSource a0) {
        return self.getHurtSound(a0);
    }

    public static InteractionResult mobInteract(BohSpider self, EntityPlayer a0, InteractionHand a1) {
        return self.mobInteract(a0, a1);
    }

    public static EntityDimensions getDimensions(BohSpider self, Pose a0) {
        return self.getDimensions(a0);
    }

    public static EntityType<?> bohType(BohSpider self) {
        return self.bohType();
    }

    public static MobType getMobType(BohSpider self) {
        return self.getMobType();
    }

    public static SpawnGroupData finalizeSpawn(BohSpider self, World a0, DifficultyInstance a1, MobSpawnType a2, SpawnGroupData a3, NBTTagCompound a4) {
        return self.finalizeSpawn(a0, a1, a2, a3, a4);
    }

    public static LookControl bohLookControl(BohSpider self) {
        return self.bohLookControl();
    }

    public static MoveControl bohMoveControl(BohSpider self) {
        return self.bohMoveControl();
    }

    public static GoalSelector bohGoals(BohSpider self) {
        return self.bohGoals();
    }

    public static GoalSelector bohTargets(BohSpider self) {
        return self.bohTargets();
    }

    public static PathNavigation bohNavigation(BohSpider self) {
        return self.bohNavigation();
    }

    public static EnumCreatureAttribute getCreatureAttribute(BohSpider self) {
        return self.getCreatureAttribute();
    }

    public static IEntityLivingData onSpawnWithEgg(BohSpider self, IEntityLivingData a0) {
        return self.onSpawnWithEgg(a0);
    }

    public static void addAdditionalSaveData(BohSpider self, NBTTagCompound a0) {
        self.addAdditionalSaveData(a0);
    }

    public static void aiStep(BohSpider self) {
        self.aiStep();
    }

    public static void awardKillScore(BohSpider self, Entity a0, int a1, DamageSource a2) {
        self.awardKillScore(a0, a1, a2);
    }

    public static void baseTick(BohSpider self) {
        self.baseTick();
    }

    public static void die(BohSpider self, DamageSource a0) {
        self.die(a0);
    }

    public static void moveEntityWithHeading(BohSpider self, float a0, float a1) {
        self.moveEntityWithHeading(a0, a1);
    }

    public static void onCollideWithPlayer(BohSpider self, EntityPlayer a0) {
        self.onCollideWithPlayer(a0);
    }

    public static void onDeath(BohSpider self, DamageSource a0) {
        self.onDeath(a0);
    }

    public static void onEntityUpdate(BohSpider self) {
        self.onEntityUpdate();
    }

    public static void onKillEntity(BohSpider self, EntityLivingBase a0) {
        self.onKillEntity(a0);
    }

    public static void onLivingUpdate(BohSpider self) {
        self.onLivingUpdate();
    }

    public static void onStruckByLightning(BohSpider self, EntityLightningBolt a0) {
        self.onStruckByLightning(a0);
    }

    public static void onUpdate(BohSpider self) {
        self.onUpdate();
    }

    public static void playerTouch(BohSpider self, EntityPlayer a0) {
        self.playerTouch(a0);
    }

    public static void readAdditionalSaveData(BohSpider self, NBTTagCompound a0) {
        self.readAdditionalSaveData(a0);
    }

    public static void readEntityFromNBT(BohSpider self, NBTTagCompound a0) {
        self.readEntityFromNBT(a0);
    }

    public static void setSizeCompat(BohSpider self, float a0, float a1) {
        self.setSizeCompat(a0, a1);
    }

    public static void startSeenByPlayer(BohSpider self, EntityPlayerMP a0) {
        self.startSeenByPlayer(a0);
    }

    public static void stopSeenByPlayer(BohSpider self, EntityPlayerMP a0) {
        self.stopSeenByPlayer(a0);
    }

    public static void thunderHit(BohSpider self, World a0, EntityLightningBolt a1) {
        self.thunderHit(a0, a1);
    }

    public static void tick(BohSpider self) {
        self.tick();
    }

    public static void travel(BohSpider self, Vec3 a0) {
        self.travel(a0);
    }

    public static void travelToDimension(BohSpider self, int a0) {
        self.travelToDimension(a0);
    }

    public static void writeEntityToNBT(BohSpider self, NBTTagCompound a0) {
        self.writeEntityToNBT(a0);
    }

    public static boolean attackEntityAsMob(BohTamableAnimal self, Entity a0) {
        return self.attackEntityAsMob(a0);
    }

    public static boolean attackEntityFrom(BohTamableAnimal self, DamageSource a0, float a1) {
        return self.attackEntityFrom(a0, a1);
    }

    public static boolean canBePushed(BohTamableAnimal self) {
        return self.canBePushed();
    }

    public static boolean canChangeDimensions(BohTamableAnimal self) {
        return self.canChangeDimensions();
    }

    public static boolean canCollideWith(BohTamableAnimal self, Entity a0) {
        return self.canCollideWith(a0);
    }

    public static boolean causeFallDamage(BohTamableAnimal self, float a0, float a1, DamageSource a2) {
        return self.causeFallDamage(a0, a1, a2);
    }

    public static boolean checkSpawnObstruction(BohTamableAnimal self, World a0) {
        return self.checkSpawnObstruction(a0);
    }

    public static boolean fireImmune(BohTamableAnimal self) {
        return self.fireImmune();
    }

    public static boolean getCanSpawnHere(BohTamableAnimal self) {
        return self.getCanSpawnHere();
    }

    public static boolean ignoreExplosion(BohTamableAnimal self) {
        return self.ignoreExplosion();
    }

    public static boolean interact(BohTamableAnimal self, EntityPlayer a0) {
        return self.interact(a0);
    }

    public static boolean isBreedingItem(BohTamableAnimal self, ItemStack a0) {
        return self.isBreedingItem(a0);
    }

    public static boolean isCreatureType(BohTamableAnimal self, EnumCreatureType a0, boolean a1) {
        return self.isCreatureType(a0, a1);
    }

    public static boolean isFood(BohTamableAnimal self, ItemStack a0) {
        return self.isFood(a0);
    }

    public static boolean isOrderedToSit(BohTamableAnimal self) {
        return self.isOrderedToSit();
    }

    public static boolean isPushable(BohTamableAnimal self) {
        return self.isPushable();
    }

    public static boolean isPushedByFluid(BohTamableAnimal self) {
        return self.isPushedByFluid();
    }

    public static boolean isPushedByWater(BohTamableAnimal self) {
        return self.isPushedByWater();
    }

    public static boolean removeWhenFarAway(BohTamableAnimal self, double a0) {
        return self.removeWhenFarAway(a0);
    }

    public static double getMountedYOffset(BohTamableAnimal self) {
        return self.getMountedYOffset();
    }

    public static double getMyRidingOffset(BohTamableAnimal self) {
        return self.getMyRidingOffset();
    }

    public static double getPassengersRidingOffset(BohTamableAnimal self) {
        return self.getPassengersRidingOffset();
    }

    public static double getYOffset(BohTamableAnimal self) {
        return self.getYOffset();
    }

    public static int getTotalArmorValue(BohTamableAnimal self) {
        return self.getTotalArmorValue();
    }

    public static SynchedEntityData bohEntityData(BohTamableAnimal self) {
        return self.bohEntityData();
    }

    public static SynchedEntityData getEntityDataModern(BohTamableAnimal self) {
        return self.getEntityDataModern();
    }

    public static SoundEvent getAmbientSound(BohTamableAnimal self) {
        return self.getAmbientSound();
    }

    public static SoundEvent getDeathSoundEvent(BohTamableAnimal self) {
        return self.getDeathSoundEvent();
    }

    public static SoundEvent getHurtSound(BohTamableAnimal self, DamageSource a0) {
        return self.getHurtSound(a0);
    }

    public static InteractionResult mobInteract(BohTamableAnimal self, EntityPlayer a0, InteractionHand a1) {
        return self.mobInteract(a0, a1);
    }

    public static EntityDimensions getDimensions(BohTamableAnimal self, Pose a0) {
        return self.getDimensions(a0);
    }

    public static EntityType<?> bohType(BohTamableAnimal self) {
        return self.bohType();
    }

    public static MobType getMobType(BohTamableAnimal self) {
        return self.getMobType();
    }

    public static SpawnGroupData finalizeSpawn(BohTamableAnimal self, World a0, DifficultyInstance a1, MobSpawnType a2, SpawnGroupData a3, NBTTagCompound a4) {
        return self.finalizeSpawn(a0, a1, a2, a3, a4);
    }

    public static LookControl bohLookControl(BohTamableAnimal self) {
        return self.bohLookControl();
    }

    public static MoveControl bohMoveControl(BohTamableAnimal self) {
        return self.bohMoveControl();
    }

    public static GoalSelector bohGoals(BohTamableAnimal self) {
        return self.bohGoals();
    }

    public static GoalSelector bohTargets(BohTamableAnimal self) {
        return self.bohTargets();
    }

    public static PathNavigation bohNavigation(BohTamableAnimal self) {
        return self.bohNavigation();
    }

    public static EntityAgeable createChild(BohTamableAnimal self, EntityAgeable a0) {
        return self.createChild(a0);
    }

    public static EntityAgeable getBreedOffspring(BohTamableAnimal self, World a0, EntityAgeable a1) {
        return self.getBreedOffspring(a0, a1);
    }

    public static EnumCreatureAttribute getCreatureAttribute(BohTamableAnimal self) {
        return self.getCreatureAttribute();
    }

    public static IEntityLivingData onSpawnWithEgg(BohTamableAnimal self, IEntityLivingData a0) {
        return self.onSpawnWithEgg(a0);
    }

    public static void addAdditionalSaveData(BohTamableAnimal self, NBTTagCompound a0) {
        self.addAdditionalSaveData(a0);
    }

    public static void aiStep(BohTamableAnimal self) {
        self.aiStep();
    }

    public static void awardKillScore(BohTamableAnimal self, Entity a0, int a1, DamageSource a2) {
        self.awardKillScore(a0, a1, a2);
    }

    public static void baseTick(BohTamableAnimal self) {
        self.baseTick();
    }

    public static void die(BohTamableAnimal self, DamageSource a0) {
        self.die(a0);
    }

    public static void moveEntityWithHeading(BohTamableAnimal self, float a0, float a1) {
        self.moveEntityWithHeading(a0, a1);
    }

    public static void onCollideWithPlayer(BohTamableAnimal self, EntityPlayer a0) {
        self.onCollideWithPlayer(a0);
    }

    public static void onDeath(BohTamableAnimal self, DamageSource a0) {
        self.onDeath(a0);
    }

    public static void onEntityUpdate(BohTamableAnimal self) {
        self.onEntityUpdate();
    }

    public static void onKillEntity(BohTamableAnimal self, EntityLivingBase a0) {
        self.onKillEntity(a0);
    }

    public static void onLivingUpdate(BohTamableAnimal self) {
        self.onLivingUpdate();
    }

    public static void onStruckByLightning(BohTamableAnimal self, EntityLightningBolt a0) {
        self.onStruckByLightning(a0);
    }

    public static void onUpdate(BohTamableAnimal self) {
        self.onUpdate();
    }

    public static void playerTouch(BohTamableAnimal self, EntityPlayer a0) {
        self.playerTouch(a0);
    }

    public static void readAdditionalSaveData(BohTamableAnimal self, NBTTagCompound a0) {
        self.readAdditionalSaveData(a0);
    }

    public static void readEntityFromNBT(BohTamableAnimal self, NBTTagCompound a0) {
        self.readEntityFromNBT(a0);
    }

    public static void setInSittingPose(BohTamableAnimal self, boolean a0) {
        self.setInSittingPose(a0);
    }

    public static void setOrderedToSit(BohTamableAnimal self, boolean a0) {
        self.setOrderedToSit(a0);
    }

    public static void setSizeCompat(BohTamableAnimal self, float a0, float a1) {
        self.setSizeCompat(a0, a1);
    }

    public static void startSeenByPlayer(BohTamableAnimal self, EntityPlayerMP a0) {
        self.startSeenByPlayer(a0);
    }

    public static void stopSeenByPlayer(BohTamableAnimal self, EntityPlayerMP a0) {
        self.stopSeenByPlayer(a0);
    }

    public static void thunderHit(BohTamableAnimal self, World a0, EntityLightningBolt a1) {
        self.thunderHit(a0, a1);
    }

    public static void tick(BohTamableAnimal self) {
        self.tick();
    }

    public static void travel(BohTamableAnimal self, Vec3 a0) {
        self.travel(a0);
    }

    public static void travelToDimension(BohTamableAnimal self, int a0) {
        self.travelToDimension(a0);
    }

    public static void writeEntityToNBT(BohTamableAnimal self, NBTTagCompound a0) {
        self.writeEntityToNBT(a0);
    }

    public static float getCooldownPercent(ItemCooldowns self, Item a0, float a1) {
        return self.getCooldownPercent(a0, a1);
    }

    public static void removeCooldown(ItemCooldowns self, Item a0) {
        self.removeCooldown(a0);
    }

    public static void onAttack(EventBridge self, LivingAttackEvent a0) {
        self.onAttack(a0);
    }

    public static void onChangedDimension(EventBridge self, PlayerChangedDimensionEvent a0) {
        self.onChangedDimension(a0);
    }

    public static void onInteract(EventBridge self, PlayerInteractEvent a0) {
        self.onInteract(a0);
    }

    public static void onSetTarget(EventBridge self, LivingSetAttackTargetEvent a0) {
        self.onSetTarget(a0);
    }

    public static double getPartialTick(ComputeFogColor self) {
        return self.getPartialTick();
    }

    public static float getBlue(ComputeFogColor self) {
        return self.getBlue();
    }

    public static float getGreen(ComputeFogColor self) {
        return self.getGreen();
    }

    public static float getRed(ComputeFogColor self) {
        return self.getRed();
    }

    public static Camera getCamera(ComputeFogColor self) {
        return self.getCamera();
    }

    public static void setBlue(ComputeFogColor self, float a0) {
        self.setBlue(a0);
    }

    public static void setGreen(ComputeFogColor self, float a0) {
        self.setGreen(a0);
    }

    public static void setRed(ComputeFogColor self, float a0) {
        self.setRed(a0);
    }

    public static float getPartialTick(net.mcreator.boh.compat.forge.client.event.Pre self) {
        return self.getPartialTick();
    }

    public static Window getWindow(net.mcreator.boh.compat.forge.client.event.Pre self) {
        return self.getWindow();
    }

    public static GuiGraphics getGuiGraphics(net.mcreator.boh.compat.forge.client.event.Pre self) {
        return self.getGuiGraphics();
    }

    public static Map<ResourceLocation, DimensionSpecialEffects> effects(RegisterDimensionSpecialEffectsEvent self) {
        return self.effects;
    }

    public static void register(RegisterDimensionSpecialEffectsEvent self, ResourceLocation a0, DimensionSpecialEffects a1) {
        self.register(a0, a1);
    }

    public static void registerLayerDefinition(RegisterLayerDefinitions self, ModelLayerLocation a0, Supplier<LayerDefinition> a1) {
        self.registerLayerDefinition(a0, a1);
    }

    public static <T> void registerSpriteSet(RegisterParticleProvidersEvent self, ParticleType<?> a0, Function<SpriteSet, ? extends ParticleProvider<T>> a1) {
        self.registerSpriteSet(a0, a1);
    }

    public static void registerBlockEntityRenderer(RegisterRenderers self, BlockEntityType<?> a0, Function<Context, ?> a1) {
        self.registerBlockEntityRenderer(a0, a1);
    }

    public static void registerEntityRenderer(RegisterRenderers self, EntityType<?> a0, Function<Context, ?> a1) {
        self.registerEntityRenderer(a0, a1);
    }

    public static double getPartialTick(RenderFog self) {
        return self.getPartialTick();
    }

    public static float getFarPlaneDistance(RenderFog self) {
        return self.getFarPlaneDistance();
    }

    public static float getNearPlaneDistance(RenderFog self) {
        return self.getNearPlaneDistance();
    }

    public static Camera getCamera(RenderFog self) {
        return self.getCamera();
    }

    public static FogMode getMode(RenderFog self) {
        return self.getMode();
    }

    public static FogShape getFogShape(RenderFog self) {
        return self.getFogShape();
    }

    public static void scaleFarPlaneDistance(RenderFog self, float a0) {
        self.scaleFarPlaneDistance(a0);
    }

    public static void scaleNearPlaneDistance(RenderFog self, float a0) {
        self.scaleNearPlaneDistance(a0);
    }

    public static void setFarPlaneDistance(RenderFog self, float a0) {
        self.setFarPlaneDistance(a0);
    }

    public static void setFogShape(RenderFog self, FogShape a0) {
        self.setFogShape(a0);
    }

    public static void setNearPlaneDistance(RenderFog self, float a0) {
        self.setNearPlaneDistance(a0);
    }

    public static int getGuiScaledHeight(Window self) {
        return self.getGuiScaledHeight();
    }

    public static int getGuiScaledWidth(Window self) {
        return self.getGuiScaledWidth();
    }

    public static int getScreenHeight(Window self) {
        return self.getScreenHeight();
    }

    public static int getScreenWidth(Window self) {
        return self.getScreenWidth();
    }

    public static Object getGenericArmorModel(IClientItemExtensions self, EntityLivingBase a0, ItemStack a1, EquipmentSlot a2, HumanoidModel<?> a3) {
        return self.getGenericArmorModel(a0, a1, a2, a3);
    }

    public static ArmPose getArmPose(IClientItemExtensions self, EntityLivingBase a0, InteractionHand a1, ItemStack a2) {
        return self.getArmPose(a0, a1, a2);
    }

    public static HumanoidModel<?> getHumanoidArmorModel(IClientItemExtensions self, EntityLivingBase a0, ItemStack a1, EquipmentSlot a2, HumanoidModel<?> a3) {
        return self.getHumanoidArmorModel(a0, a1, a2, a3);
    }

    public static BlockEntityWithoutLevelRenderer getCustomRenderer(IClientItemExtensions self) {
        return self.getCustomRenderer();
    }

    public static boolean isVisibleInGui(IClientMobEffectExtensions self, PotionEffect a0) {
        return self.isVisibleInGui(a0);
    }

    public static boolean isVisibleInInventory(IClientMobEffectExtensions self, PotionEffect a0) {
        return self.isVisibleInInventory(a0);
    }

    public static boolean renderGuiIcon(IClientMobEffectExtensions self, PotionEffect a0, Object a1, GuiGraphics a2, int a3, int a4, float a5, float a6) {
        return self.renderGuiIcon(a0, a1, a2, a3, a4, a5, a6);
    }

    public static boolean renderInventoryIcon(
        IClientMobEffectExtensions self, PotionEffect a0, EffectRenderingInventoryScreen<?> a1, GuiGraphics a2, int a3, int a4, int a5
    ) {
        return self.renderInventoryIcon(a0, a1, a2, a3, a4, a5);
    }

    public static boolean renderInventoryText(
        IClientMobEffectExtensions self, PotionEffect a0, EffectRenderingInventoryScreen<?> a1, GuiGraphics a2, int a3, int a4, int a5
    ) {
        return self.renderInventoryText(a0, a1, a2, a3, a4, a5);
    }

    public static ItemStack forSale(BasicItemListing self) {
        return self.forSale;
    }

    public static int maxTrades(BasicItemListing self) {
        return self.maxTrades;
    }

    public static ItemStack price(BasicItemListing self) {
        return self.price;
    }

    public static ItemStack price2(BasicItemListing self) {
        return self.price2;
    }

    public static float priceMult(BasicItemListing self) {
        return self.priceMult;
    }

    public static int xp(BasicItemListing self) {
        return self.xp;
    }

    public static MerchantRecipe toRecipe(BasicItemListing self) {
        return self.toRecipe();
    }

    public static boolean requiresMultipleRenderPasses(ForgeSpawnEggItem self) {
        return self.requiresMultipleRenderPasses();
    }

    public static int getColorFromItemStack(ForgeSpawnEggItem self, ItemStack a0, int a1) {
        return self.getColorFromItemStack(a0, a1);
    }

    public static String getItemStackDisplayName(ForgeSpawnEggItem self, ItemStack a0) {
        return self.getItemStackDisplayName(a0);
    }

    public static InteractionResult useOn(ForgeSpawnEggItem self, UseOnContext a0) {
        return self.useOn(a0);
    }

    public static EntityType<?> getType(ForgeSpawnEggItem self, Object a0) {
        return self.getType(a0);
    }

    public static IIcon getIconFromDamageForRenderPass(ForgeSpawnEggItem self, int a0, int a1) {
        return self.getIconFromDamageForRenderPass(a0, a1);
    }

    public static void onRegistered(ForgeSpawnEggItem self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static void registerIcons(ForgeSpawnEggItem self, IIconRegister a0) {
        self.registerIcons(a0);
    }

    public static boolean isIngredient(IBrewingRecipe self, ItemStack a0) {
        return self.isIngredient(a0);
    }

    public static boolean isInput(IBrewingRecipe self, ItemStack a0) {
        return self.isInput(a0);
    }

    public static ItemStack getOutput(IBrewingRecipe self, ItemStack a0, ItemStack a1) {
        return self.getOutput(a0, a1);
    }

    public static <CT> boolean isRegistered(Capability<CT> self) {
        return self.isRegistered();
    }

    public static <CT> String getName(Capability<CT> self) {
        return self.getName();
    }

    public static void construct(EntityCapabilities.Hooks self, EntityConstructing a0) {
        self.construct(a0);
    }

    public static void init(EntityCapabilities self, Entity a0, World a1) {
        self.init(a0, a1);
    }

    public static void loadNBTData(EntityCapabilities self, NBTTagCompound a0) {
        self.loadNBTData(a0);
    }

    public static void saveNBTData(EntityCapabilities self, NBTTagCompound a0) {
        self.saveNBTData(a0);
    }

    public static <T> LazyOptional<T> getCapability(ICapabilityProvider self, Capability<T> a0, Direction a1) {
        return self.getCapability(a0, a1);
    }

    public static <T> LazyOptional<T> getCapability(ICapabilityProvider self, Capability<T> a0) {
        return self.getCapability(a0);
    }

    public static <CT extends NBTBase> CT serializeNBT(ICapabilitySerializable<CT> self) {
        return self.serializeNBT();
    }

    public static <CT extends NBTBase> void deserializeNBT(ICapabilitySerializable<CT> self, CT a0) {
        self.deserializeNBT(a0);
    }

    public static <T> void register(RegisterCapabilitiesEvent self, Class<T> a0) {
        self.register(a0);
    }

    public static <CT, U> Optional<U> map(LazyOptional<CT> self, Function<? super CT, ? extends U> a0) {
        return self.map(a0);
    }

    public static <CT, U> LazyOptional<U> lazyMap(LazyOptional<CT> self, Function<? super CT, ? extends U> a0) {
        return self.lazyMap(a0);
    }

    public static <CT, X extends Throwable> CT orElseThrow(LazyOptional<CT> self, Supplier<? extends X> a0) throws Throwable {
        return self.orElseThrow(a0);
    }

    public static <CT, X> LazyOptional<X> cast(LazyOptional<CT> self) {
        return self.cast();
    }

    public static <CT> CT orElse(LazyOptional<CT> self, CT a0) {
        return self.orElse(a0);
    }

    public static <CT> CT orElseGet(LazyOptional<CT> self, Supplier<? extends CT> a0) {
        return self.orElseGet(a0);
    }

    public static <CT> boolean isPresent(LazyOptional<CT> self) {
        return self.isPresent();
    }

    public static <CT> Optional<CT> resolve(LazyOptional<CT> self) {
        return self.resolve();
    }

    public static <CT> void ifPresent(LazyOptional<CT> self, Consumer<? super CT> a0) {
        self.ifPresent(a0);
    }

    public static <CT> void invalidate(LazyOptional<CT> self) {
        self.invalidate();
    }

    public static <CT> CT get(NonNullSupplier<CT> self) {
        return self.get();
    }

    public static <CT> CT getObject(AttachCapabilitiesEvent<CT> self) {
        return self.getObject();
    }

    public static <CT> Map<ResourceLocation, ICapabilityProvider> getCapabilities(AttachCapabilitiesEvent<CT> self) {
        return self.getCapabilities();
    }

    public static <CT> void addCapability(AttachCapabilitiesEvent<CT> self, ResourceLocation a0, ICapabilityProvider a1) {
        self.addCapability(a0, a1);
    }

    public static <CT> void addListener(AttachCapabilitiesEvent<CT> self, Runnable a0) {
        self.addListener(a0);
    }

    public static Object getTabKey(BuildCreativeModeTabContentsEvent self) {
        return self.getTabKey();
    }

    public static void accept(BuildCreativeModeTabContentsEvent self, Object a0) {
        self.accept(a0);
    }

    public static Commands getDispatcher(RegisterCommandsEvent self) {
        return self.getDispatcher();
    }

    public static void put(EntityAttributeCreationEvent self, EntityType<?> a0, AttributeSupplier a1) {
        self.put(a0, a1);
    }

    public static EntityLivingBase getNewTarget(LivingChangeTargetEvent self) {
        return self.getNewTarget();
    }

    public static EntityLivingBase getOriginalTarget(LivingChangeTargetEvent self) {
        return self.getOriginalTarget();
    }

    public static void setNewTarget(LivingChangeTargetEvent self, EntityLivingBase a0) {
        self.setNewTarget(a0);
    }

    public static float getBlockedDamage(ShieldBlockEvent self) {
        return self.getBlockedDamage();
    }

    public static DamageSource getDamageSource(ShieldBlockEvent self) {
        return self.getDamageSource();
    }

    public static void setBlockedDamage(ShieldBlockEvent self, float a0) {
        self.setBlockedDamage(a0);
    }

    public static BlockPos getPos(LeftClickBlock self) {
        return self.getPos();
    }

    public static Direction getFace(LeftClickBlock self) {
        return self.getFace();
    }

    public static InteractionHand getHand(LeftClickBlock self) {
        return self.getHand();
    }

    public static ItemStack getItemStack(LeftClickBlock self) {
        return self.getItemStack();
    }

    public static World getLevel(LeftClickBlock self) {
        return self.getLevel();
    }

    public static BlockPos getPos(LeftClickEmpty self) {
        return self.getPos();
    }

    public static InteractionHand getHand(LeftClickEmpty self) {
        return self.getHand();
    }

    public static World getLevel(LeftClickEmpty self) {
        return self.getLevel();
    }

    public static BlockPos getPos(RightClickBlock self) {
        return self.getPos();
    }

    public static Direction getFace(RightClickBlock self) {
        return self.getFace();
    }

    public static InteractionHand getHand(RightClickBlock self) {
        return self.getHand();
    }

    public static ItemStack getItemStack(RightClickBlock self) {
        return self.getItemStack();
    }

    public static World getLevel(RightClickBlock self) {
        return self.getLevel();
    }

    public static Map<Integer, List<Object>> getTrades(VillagerTradesEvent self) {
        return self.getTrades();
    }

    public static VillagerProfession getType(VillagerTradesEvent self) {
        return self.getType();
    }

    public static void enqueueWork(FMLClientSetupEvent self, Runnable a0) {
        self.enqueueWork(a0);
    }

    public static void enqueueWork(FMLCommonSetupEvent self, Runnable a0) {
        self.enqueueWork(a0);
    }

    public static boolean isItemValid(IItemHandler self, int a0, ItemStack a1) {
        return self.isItemValid(a0, a1);
    }

    public static int getSlotLimit(IItemHandler self, int a0) {
        return self.getSlotLimit(a0);
    }

    public static int getSlots(IItemHandler self) {
        return self.getSlots();
    }

    public static ItemStack extractItem(IItemHandler self, int a0, int a1, boolean a2) {
        return self.extractItem(a0, a1, a2);
    }

    public static ItemStack getStackInSlot(IItemHandler self, int a0) {
        return self.getStackInSlot(a0);
    }

    public static ItemStack insertItem(IItemHandler self, int a0, ItemStack a1, boolean a2) {
        return self.insertItem(a0, a1, a2);
    }

    public static void setStackInSlot(IItemHandlerModifiable self, int a0, ItemStack a1) {
        self.setStackInSlot(a0, a1);
    }

    public static boolean isItemValid(ItemStackHandler self, int a0, ItemStack a1) {
        return self.isItemValid(a0, a1);
    }

    public static int getSlotLimit(ItemStackHandler self, int a0) {
        return self.getSlotLimit(a0);
    }

    public static int getSlots(ItemStackHandler self) {
        return self.getSlots();
    }

    public static ItemStack extractItem(ItemStackHandler self, int a0, int a1, boolean a2) {
        return self.extractItem(a0, a1, a2);
    }

    public static ItemStack getStackInSlot(ItemStackHandler self, int a0) {
        return self.getStackInSlot(a0);
    }

    public static ItemStack insertItem(ItemStackHandler self, int a0, ItemStack a1, boolean a2) {
        return self.insertItem(a0, a1, a2);
    }

    public static NBTTagCompound serializeNBT(ItemStackHandler self) {
        return self.serializeNBT();
    }

    public static void deserializeNBT(ItemStackHandler self, NBTTagCompound a0) {
        self.deserializeNBT(a0);
    }

    public static void setSize(ItemStackHandler self, int a0) {
        self.setSize(a0);
    }

    public static void setStackInSlot(ItemStackHandler self, int a0, ItemStack a1) {
        self.setStackInSlot(a0, a1);
    }

    public static boolean canTakeStack(SlotItemHandler self, EntityPlayer a0) {
        return self.canTakeStack(a0);
    }

    public static boolean isItemValid(SlotItemHandler self, ItemStack a0) {
        return self.isItemValid(a0);
    }

    public static boolean isSlotInInventory(SlotItemHandler self, IInventory a0, int a1) {
        return self.isSlotInInventory(a0, a1);
    }

    public static boolean mayPickup(SlotItemHandler self, EntityPlayer a0) {
        return self.mayPickup(a0);
    }

    public static boolean mayPlace(SlotItemHandler self, ItemStack a0) {
        return self.mayPlace(a0);
    }

    public static int getSlotStackLimit(SlotItemHandler self) {
        return self.getSlotStackLimit();
    }

    public static IItemHandler getItemHandler(SlotItemHandler self) {
        return self.getItemHandler();
    }

    public static ItemStack decrStackSize(SlotItemHandler self, int a0) {
        return self.decrStackSize(a0);
    }

    public static ItemStack getStack(SlotItemHandler self) {
        return self.getStack();
    }

    public static void onPickupFromSlot(SlotItemHandler self, EntityPlayer a0, ItemStack a1) {
        self.onPickupFromSlot(a0, a1);
    }

    public static void onTake(SlotItemHandler self, EntityPlayer a0, ItemStack a1) {
        self.onTake(a0, a1);
    }

    public static void putStack(SlotItemHandler self, ItemStack a0) {
        self.putStack(a0);
    }

    public static boolean isItemValid(InvWrapper self, int a0, ItemStack a1) {
        return self.isItemValid(a0, a1);
    }

    public static int getSlotLimit(InvWrapper self, int a0) {
        return self.getSlotLimit(a0);
    }

    public static int getSlots(InvWrapper self) {
        return self.getSlots();
    }

    public static IInventory getInv(InvWrapper self) {
        return self.getInv();
    }

    public static ItemStack extractItem(InvWrapper self, int a0, int a1, boolean a2) {
        return self.extractItem(a0, a1, a2);
    }

    public static ItemStack getStackInSlot(InvWrapper self, int a0) {
        return self.getStackInSlot(a0);
    }

    public static ItemStack insertItem(InvWrapper self, int a0, ItemStack a1, boolean a2) {
        return self.insertItem(a0, a1, a2);
    }

    public static void setStackInSlot(InvWrapper self, int a0, ItemStack a1) {
        self.setStackInSlot(a0, a1);
    }

    public static boolean getPacketHandled(net.mcreator.boh.compat.forge.network.Context self) {
        return self.getPacketHandled();
    }

    public static NetworkDirection getDirection(net.mcreator.boh.compat.forge.network.Context self) {
        return self.getDirection();
    }

    public static EntityPlayerMP getSender(net.mcreator.boh.compat.forge.network.Context self) {
        return self.getSender();
    }

    public static void enqueueWork(net.mcreator.boh.compat.forge.network.Context self, Runnable a0) {
        self.enqueueWork(a0);
    }

    public static void setPacketHandled(net.mcreator.boh.compat.forge.network.Context self, boolean a0) {
        self.setPacketHandled(a0);
    }

    public static boolean isClient(NetworkDirection.LogicalSide self) {
        return self.isClient();
    }

    public static boolean isServer(NetworkDirection.LogicalSide self) {
        return self.isServer();
    }

    public static NetworkDirection.LogicalSide getReceptionSide(NetworkDirection self) {
        return self.getReceptionSide();
    }

    public static Object arg(PacketDistributor.Target self) {
        return self.arg;
    }

    public static String kind(PacketDistributor.Target self) {
        return self.kind;
    }

    public static <CT> PacketDistributor.Target noArg(PacketDistributor<CT> self) {
        return self.noArg();
    }

    public static <CT> PacketDistributor.Target with(PacketDistributor<CT> self, Supplier<CT> a0) {
        return self.with(a0);
    }

    public static IMessage onMessage(SimpleChannel.ClientHandler self, SimpleChannel.Envelope a0, MessageContext a1) {
        return self.onMessage(a0, a1);
    }

    public static void fromBytes(SimpleChannel.Envelope self, ByteBuf a0) {
        self.fromBytes(a0);
    }

    public static void toBytes(SimpleChannel.Envelope self, ByteBuf a0) {
        self.toBytes(a0);
    }

    public static IMessage onMessage(SimpleChannel.ServerHandler self, SimpleChannel.Envelope a0, MessageContext a1) {
        return self.onMessage(a0, a1);
    }

    public static <M> void registerMessage(
        SimpleChannel self,
        int a0,
        Class<M> a1,
        BiConsumer<M, FriendlyByteBuf> a2,
        Function<FriendlyByteBuf, M> a3,
        BiConsumer<M, Supplier<net.mcreator.boh.compat.forge.network.Context>> a4
    ) {
        self.registerMessage(a0, a1, a2, a3, a4);
    }

    public static <M> void registerMessage(
        SimpleChannel self,
        int a0,
        Class<M> a1,
        BiConsumer<M, FriendlyByteBuf> a2,
        Function<FriendlyByteBuf, M> a3,
        BiConsumer<M, Supplier<net.mcreator.boh.compat.forge.network.Context>> a4,
        Optional<?> a5
    ) {
        self.registerMessage(a0, a1, a2, a3, a4, a5);
    }

    public static <M> void send(SimpleChannel self, PacketDistributor.Target a0, M a1) {
        self.send(a0, a1);
    }

    public static <M> void sendTo(SimpleChannel self, M a0, Object a1, Object a2) {
        self.sendTo(a0, a1, a2);
    }

    public static <M> void sendToServer(SimpleChannel self, M a0) {
        self.sendToServer(a0);
    }

    public static <CT> Collection<RegistryObject<CT>> getEntries(DeferredRegister<CT> self) {
        return self.getEntries();
    }

    public static <CT> IForgeRegistry<CT> getRegistry(DeferredRegister<CT> self) {
        return self.getRegistry();
    }

    public static <CT> void register(DeferredRegister<CT> self, Object a0) {
        self.register(a0);
    }

    public static <CT> CT getValue(IForgeRegistry<CT> self, ResourceLocation a0) {
        return self.getValue(a0);
    }

    public static <CT> CT register(IForgeRegistry<CT> self, ResourceLocation a0, CT a1) {
        return self.register(a0, a1);
    }

    public static <CT> boolean containsKey(IForgeRegistry<CT> self, ResourceLocation a0) {
        return self.containsKey(a0);
    }

    public static <CT> boolean containsValue(IForgeRegistry<CT> self, CT a0) {
        return self.containsValue(a0);
    }

    public static <CT> Collection<CT> getValues(IForgeRegistry<CT> self) {
        return self.getValues();
    }

    public static <CT> Set<Entry<ResourceLocation, CT>> getEntries(IForgeRegistry<CT> self) {
        return self.getEntries();
    }

    public static <CT> Set<ResourceLocation> getKeys(IForgeRegistry<CT> self) {
        return self.getKeys();
    }

    public static <CT> IForgeRegistry<CT> onRegister(IForgeRegistry<CT> self, BiFunction<ResourceLocation, CT, CT> a0) {
        return self.onRegister(a0);
    }

    public static <CT> IForgeRegistry<CT> withFallback(IForgeRegistry<CT> self, Function<ResourceLocation, CT> a0, Function<CT, ResourceLocation> a1) {
        return self.withFallback(a0, a1);
    }

    public static <CT> ResourceKey<?> getRegistryKey(IForgeRegistry<CT> self) {
        return self.getRegistryKey();
    }

    public static <CT> ResourceLocation getKey(IForgeRegistry<CT> self, CT a0) {
        return self.getKey(a0);
    }

    public static <CT> boolean contains(ITagManager.ITag<CT> self, CT a0) {
        return self.contains(a0);
    }

    public static <CT> boolean isEmpty(ITagManager.ITag<CT> self) {
        return self.isEmpty();
    }

    public static <CT> int size(ITagManager.ITag<CT> self) {
        return self.size();
    }

    public static <CT> Iterator<CT> iterator(ITagManager.ITag<CT> self) {
        return self.iterator();
    }

    public static <CT> Stream<CT> stream(ITagManager.ITag<CT> self) {
        return self.stream();
    }

    public static <CT> TagKey<CT> createTagKey(ITagManager<CT> self, ResourceLocation a0) {
        return self.createTagKey(a0);
    }

    public static <CT> void register(RegisterEvent.RegisterHelper<CT> self, Object a0, CT a1) {
        self.register(a0, a1);
    }

    public static <T> void register(RegisterEvent self, Object a0, Consumer<RegisterEvent.RegisterHelper<T>> a1) {
        self.register(a0, a1);
    }

    public static <CT> boolean isPresent(RegistryObject<CT> self) {
        return self.isPresent();
    }

    public static <CT> Optional<CT> getHolder(RegistryObject<CT> self) {
        return self.getHolder();
    }

    public static <CT> Optional<ResourceKey<CT>> getKey(RegistryObject<CT> self) {
        return self.getKey();
    }

    public static <CT> CT get(RegistryObject<CT> self) {
        return self.get();
    }

    public static <CT> void ifPresent(RegistryObject<CT> self, Consumer<? super CT> a0) {
        self.ifPresent(a0);
    }

    public static boolean onItemUse(BohBlockItem self, ItemStack a0, EntityPlayer a1, World a2, int a3, int a4, int a5, int a6, float a7, float a8, float a9) {
        return self.onItemUse(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9);
    }

    public static void initializeClient(BohBlockItem self, Consumer<IClientItemExtensions> a0) {
        self.initializeClient(a0);
    }

    public static boolean canApply(BohEnchantment self, ItemStack a0) {
        return self.canApply(a0);
    }

    public static boolean canApplyAtEnchantingTable(BohEnchantment self, ItemStack a0) {
        return self.canApplyAtEnchantingTable(a0);
    }

    public static boolean canApplyTogether(BohEnchantment self, Enchantment a0) {
        return self.canApplyTogether(a0);
    }

    public static boolean canEnchant(BohEnchantment self, ItemStack a0) {
        return self.canEnchant(a0);
    }

    public static boolean isAllowedOnBooks(BohEnchantment self) {
        return self.isAllowedOnBooks();
    }

    public static boolean isCurse(BohEnchantment self) {
        return self.isCurse();
    }

    public static boolean isDiscoverable(BohEnchantment self) {
        return self.isDiscoverable();
    }

    public static boolean isTradeable(BohEnchantment self) {
        return self.isTradeable();
    }

    public static boolean isTreasureOnly(BohEnchantment self) {
        return self.isTreasureOnly();
    }

    public static int getMaxCost(BohEnchantment self, int a0) {
        return self.getMaxCost(a0);
    }

    public static int getMaxEnchantability(BohEnchantment self, int a0) {
        return self.getMaxEnchantability(a0);
    }

    public static int getMaxLevel(BohEnchantment self) {
        return self.getMaxLevel();
    }

    public static int getMinCost(BohEnchantment self, int a0) {
        return self.getMinCost(a0);
    }

    public static int getMinEnchantability(BohEnchantment self, int a0) {
        return self.getMinEnchantability(a0);
    }

    public static void doPostAttack(BohEnchantment self, EntityLivingBase a0, Entity a1, int a2) {
        self.doPostAttack(a0, a1, a2);
    }

    public static void doPostHurt(BohEnchantment self, EntityLivingBase a0, Entity a1, int a2) {
        self.doPostHurt(a0, a1, a2);
    }

    public static void func_151367_b(BohEnchantment self, EntityLivingBase a0, Entity a1, int a2) {
        self.func_151367_b(a0, a1, a2);
    }

    public static void func_151368_a(BohEnchantment self, EntityLivingBase a0, Entity a1, int a2) {
        self.func_151368_a(a0, a1, a2);
    }

    public static boolean canAttackBlock(BohItem self, BlockState a0, World a1, BlockPos a2, EntityPlayer a3) {
        return self.canAttackBlock(a0, a1, a2, a3);
    }

    public static boolean canHarvestBlock(BohItem self, Block a0, ItemStack a1) {
        return self.canHarvestBlock(a0, a1);
    }

    public static boolean hitEntity(BohItem self, ItemStack a0, EntityLivingBase a1, EntityLivingBase a2) {
        return self.hitEntity(a0, a1, a2);
    }

    public static boolean hurtEnemy(BohItem self, ItemStack a0, EntityLivingBase a1, EntityLivingBase a2) {
        return self.hurtEnemy(a0, a1, a2);
    }

    public static boolean isCorrectToolForDrops(BohItem self, BlockState a0) {
        return self.isCorrectToolForDrops(a0);
    }

    public static boolean isEnchantable(BohItem self, ItemStack a0) {
        return self.isEnchantable(a0);
    }

    public static boolean isFoil(BohItem self, ItemStack a0) {
        return self.isFoil(a0);
    }

    public static boolean isItemTool(BohItem self, ItemStack a0) {
        return self.isItemTool(a0);
    }

    public static boolean itemInteractionForEntity(BohItem self, ItemStack a0, EntityPlayer a1, EntityLivingBase a2) {
        return self.itemInteractionForEntity(a0, a1, a2);
    }

    public static boolean mineBlock(BohItem self, ItemStack a0, World a1, BlockState a2, BlockPos a3, EntityLivingBase a4) {
        return self.mineBlock(a0, a1, a2, a3, a4);
    }

    public static boolean onBlockDestroyed(BohItem self, ItemStack a0, World a1, Block a2, int a3, int a4, int a5, EntityLivingBase a6) {
        return self.onBlockDestroyed(a0, a1, a2, a3, a4, a5, a6);
    }

    public static boolean onEntitySwing(BohItem self, EntityLivingBase a0, ItemStack a1) {
        return self.onEntitySwing(a0, a1);
    }

    public static boolean onEntitySwing(BohItem self, ItemStack a0, EntityLivingBase a1) {
        return self.onEntitySwing(a0, a1);
    }

    public static boolean onItemUse(BohItem self, ItemStack a0, EntityPlayer a1, World a2, int a3, int a4, int a5, int a6, float a7, float a8, float a9) {
        return self.onItemUse(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9);
    }

    public static boolean shouldCauseReequipAnimation(BohItem self, ItemStack a0, ItemStack a1, boolean a2) {
        return self.shouldCauseReequipAnimation(a0, a1, a2);
    }

    public static Multimap getAttributeModifiers(BohItem self, ItemStack a0) {
        return self.getAttributeModifiers(a0);
    }

    public static Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(BohItem self, EquipmentSlot a0) {
        return self.getDefaultAttributeModifiers(a0);
    }

    public static float getDigSpeed(BohItem self, ItemStack a0, Block a1, int a2) {
        return self.getDigSpeed(a0, a1, a2);
    }

    public static int getEnchantmentValue(BohItem self) {
        return self.getEnchantmentValue();
    }

    public static int getItemEnchantability(BohItem self) {
        return self.getItemEnchantability();
    }

    public static int getMaxItemUseDuration(BohItem self, ItemStack a0) {
        return self.getMaxItemUseDuration(a0);
    }

    public static int getUseDuration(BohItem self, ItemStack a0) {
        return self.getUseDuration(a0);
    }

    public static InteractionResult interactLivingEntity(BohItem self, ItemStack a0, EntityPlayer a1, EntityLivingBase a2, InteractionHand a3) {
        return self.interactLivingEntity(a0, a1, a2, a3);
    }

    public static InteractionResult useOn(BohItem self, UseOnContext a0) {
        return self.useOn(a0);
    }

    public static InteractionResultHolder<ItemStack> use(BohItem self, World a0, EntityPlayer a1, InteractionHand a2) {
        return self.use(a0, a1, a2);
    }

    public static FoodProperties food(BohItem self) {
        return self.food();
    }

    public static UseAnim getUseAnimation(BohItem self, ItemStack a0) {
        return self.getUseAnimation(a0);
    }

    public static EnumAction getItemUseAction(BohItem self, ItemStack a0) {
        return self.getItemUseAction(a0);
    }

    public static EnumRarity getRarity(BohItem self, ItemStack a0) {
        return self.getRarity(a0);
    }

    public static ItemStack finishUsingItem(BohItem self, ItemStack a0, World a1, EntityLivingBase a2) {
        return self.finishUsingItem(a0, a1, a2);
    }

    public static ItemStack onEaten(BohItem self, ItemStack a0, World a1, EntityPlayer a2) {
        return self.onEaten(a0, a1, a2);
    }

    public static ItemStack onItemRightClick(BohItem self, ItemStack a0, World a1, EntityPlayer a2) {
        return self.onItemRightClick(a0, a1, a2);
    }

    public static ResourceLocation registryName(BohItem self) {
        return self.registryName();
    }

    public static void addInformation(BohItem self, ItemStack a0, EntityPlayer a1, List a2, boolean a3) {
        self.addInformation(a0, a1, a2, a3);
    }

    public static void appendHoverText(BohItem self, ItemStack a0, World a1, List<Component> a2, TooltipFlag a3) {
        self.appendHoverText(a0, a1, a2, a3);
    }

    public static void initializeClient(BohItem self, Consumer<IClientItemExtensions> a0) {
        self.initializeClient(a0);
    }

    public static void inventoryTick(BohItem self, ItemStack a0, World a1, Entity a2, int a3, boolean a4) {
        self.inventoryTick(a0, a1, a2, a3, a4);
    }

    public static void onPlayerStoppedUsing(BohItem self, ItemStack a0, World a1, EntityPlayer a2, int a3) {
        self.onPlayerStoppedUsing(a0, a1, a2, a3);
    }

    public static void onRegistered(BohItem self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static void onUpdate(BohItem self, ItemStack a0, World a1, Entity a2, int a3, boolean a4) {
        self.onUpdate(a0, a1, a2, a3, a4);
    }

    public static void onUseTick(BohItem self, World a0, EntityLivingBase a1, ItemStack a2, int a3) {
        self.onUseTick(a0, a1, a2, a3);
    }

    public static void onUsingTick(BohItem self, ItemStack a0, EntityPlayer a1, int a2) {
        self.onUsingTick(a0, a1, a2);
    }

    public static void registerIcons(BohItem self, IIconRegister a0) {
        self.registerIcons(a0);
    }

    public static void releaseUsing(BohItem self, ItemStack a0, World a1, EntityLivingBase a2, int a3) {
        self.releaseUsing(a0, a1, a2, a3);
    }

    public static boolean isColor(ChatFormatting self) {
        return self.isColor();
    }

    public static String getName(ChatFormatting self) {
        return self.getName();
    }

    public static EnumChatFormatting toVanilla(ChatFormatting self) {
        return self.toVanilla();
    }

    public static Achievement achievement(Advancement self) {
        return self.achievement;
    }

    public static String id(Advancement self) {
        return self.id;
    }

    public static Advancement parent(Advancement self) {
        return self.parent;
    }

    public static Advancement getParent(Advancement self) {
        return self.getParent();
    }

    public static ResourceLocation getId(Advancement self) {
        return self.getId();
    }

    public static boolean hasProgress(AdvancementProgress self) {
        return self.hasProgress();
    }

    public static boolean isDone(AdvancementProgress self) {
        return self.isDone();
    }

    public static Iterable<String> getCompletedCriteria(AdvancementProgress self) {
        return self.getCompletedCriteria();
    }

    public static Iterable<String> getRemainingCriteria(AdvancementProgress self) {
        return self.getRemainingCriteria();
    }

    public static boolean award(PlayerAdvancements self, Advancement a0, String a1) {
        return self.award(a0, a1);
    }

    public static boolean revoke(PlayerAdvancements self, Advancement a0, String a1) {
        return self.revoke(a0, a1);
    }

    public static AdvancementProgress getOrStartProgress(PlayerAdvancements self, Advancement a0) {
        return self.getOrStartProgress(a0);
    }

    public static boolean shouldCreateWorldFog(BossOverlay self) {
        return self.shouldCreateWorldFog();
    }

    public static boolean shouldDarkenScreen(BossOverlay self) {
        return self.shouldDarkenScreen();
    }

    public static boolean shouldPlayMusic(BossOverlay self) {
        return self.shouldPlayMusic();
    }

    public static BossOverlay getBossOverlay(BossOverlay self) {
        return self.getBossOverlay();
    }

    public static boolean isInitialized(Camera self) {
        return self.isInitialized();
    }

    public static float getXRot(Camera self) {
        return self.getXRot();
    }

    public static float getYRot(Camera self) {
        return self.getYRot();
    }

    public static BlockPos getBlockPosition(Camera self) {
        return self.getBlockPosition();
    }

    public static Vec3 getPosition(Camera self) {
        return self.getPosition();
    }

    public static Entity getEntity(Camera self) {
        return self.getEntity();
    }

    public static int drawString(GuiGraphics self, Object a0, String a1, int a2, int a3, int a4) {
        return self.drawString(a0, a1, a2, a3, a4);
    }

    public static int drawString(GuiGraphics self, Object a0, String a1, int a2, int a3, int a4, boolean a5) {
        return self.drawString(a0, a1, a2, a3, a4, a5);
    }

    public static int drawString(GuiGraphics self, Object a0, Component a1, int a2, int a3, int a4) {
        return self.drawString(a0, a1, a2, a3, a4);
    }

    public static int drawString(GuiGraphics self, Object a0, Component a1, int a2, int a3, int a4, boolean a5) {
        return self.drawString(a0, a1, a2, a3, a4, a5);
    }

    public static int guiHeight(GuiGraphics self) {
        return self.guiHeight();
    }

    public static int guiWidth(GuiGraphics self) {
        return self.guiWidth();
    }

    public static PoseStack pose(GuiGraphics self) {
        return self.pose();
    }

    public static void blit(GuiGraphics self, ResourceLocation a0, int a1, int a2, float a3, float a4, int a5, int a6, int a7, int a8) {
        self.blit(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static void blit(GuiGraphics self, ResourceLocation a0, int a1, int a2, int a3, int a4, float a5, float a6, int a7, int a8, int a9, int a10) {
        self.blit(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10);
    }

    public static void blit(GuiGraphics self, ResourceLocation a0, int a1, int a2, int a3, int a4, int a5, int a6) {
        self.blit(a0, a1, a2, a3, a4, a5, a6);
    }

    public static void drawCenteredString(GuiGraphics self, Object a0, String a1, int a2, int a3, int a4) {
        self.drawCenteredString(a0, a1, a2, a3, a4);
    }

    public static void drawCenteredString(GuiGraphics self, Object a0, Component a1, int a2, int a3, int a4) {
        self.drawCenteredString(a0, a1, a2, a3, a4);
    }

    public static void fill(GuiGraphics self, int a0, int a1, int a2, int a3, int a4) {
        self.fill(a0, a1, a2, a3, a4);
    }

    public static void renderItem(GuiGraphics self, ItemStack a0, int a1, int a2) {
        self.renderItem(a0, a1, a2);
    }

    public static Button build(Button.Builder self) {
        return self.build();
    }

    public static Button build(Button.Builder self, Function<Button.Builder, Button> a0) {
        return self.build(a0);
    }

    public static Button.Builder bounds(Button.Builder self, int a0, int a1, int a2, int a3) {
        return self.bounds(a0, a1, a2, a3);
    }

    public static Button.Builder pos(Button.Builder self, int a0, int a1) {
        return self.pos(a0, a1);
    }

    public static Button.Builder size(Button.Builder self, int a0, int a1) {
        return self.size(a0, a1);
    }

    public static Button.Builder tooltip(Button.Builder self, Object a0) {
        return self.tooltip(a0);
    }

    public static void onPress(Button.OnPress self, Button a0) {
        self.onPress(a0);
    }

    public static boolean isHoveredOrFocused(Button self) {
        return self.isHoveredOrFocused();
    }

    public static void drawButton(Button self, Minecraft a0, int a1, int a2) {
        self.drawButton(a0, a1, a2);
    }

    public static void onPress(Button self) {
        self.onPress();
    }

    public static void renderWidget(Button self, GuiGraphics a0, int a1, int a2, float a3) {
        self.renderWidget(a0, a1, a2, a3);
    }

    public static void setMessage(Button self, Component a0) {
        self.setMessage(a0);
    }

    public static String getValue(EditBox self) {
        return self.getValue();
    }

    public static void setSuggestion(EditBox self, String a0) {
        self.setSuggestion(a0);
    }

    public static void setValue(EditBox self, String a0) {
        self.setValue(a0);
    }

    public static void renderWidget(ImageButton self, GuiGraphics a0, int a1, int a2, float a3) {
        self.renderWidget(a0, a1, a2, a3);
    }

    public static <CM, CS> CS create(MenuScreens.ScreenConstructor<CM, CS> self, CM a0, InventoryPlayer a1, Component a2) {
        return self.create(a0, a1, a2);
    }

    public static <CT extends AbstractContainerMenu, W extends GuiButton> W addRenderableWidget(AbstractContainerScreen<CT> self, W a0) {
        return self.addRenderableWidget(a0);
    }

    public static <CT extends AbstractContainerMenu, W> W addWidget(AbstractContainerScreen<CT> self, W a0) {
        return self.addWidget(a0);
    }

    public static <CT extends AbstractContainerMenu> CT getMenu(AbstractContainerScreen<CT> self) {
        return self.getMenu();
    }

    public static <CT extends AbstractContainerMenu> boolean doesGuiPauseGame(AbstractContainerScreen<CT> self) {
        return self.doesGuiPauseGame();
    }

    public static <CT extends AbstractContainerMenu> boolean isPauseScreen(AbstractContainerScreen<CT> self) {
        return self.isPauseScreen();
    }

    public static <CT extends AbstractContainerMenu> boolean keyPressed(AbstractContainerScreen<CT> self, int a0, int a1, int a2) {
        return self.keyPressed(a0, a1, a2);
    }

    public static <CT extends AbstractContainerMenu> Component getTitle(AbstractContainerScreen<CT> self) {
        return self.getTitle();
    }

    public static <CT extends AbstractContainerMenu> void containerTick(AbstractContainerScreen<CT> self) {
        self.containerTick();
    }

    public static <CT extends AbstractContainerMenu> void drawScreen(AbstractContainerScreen<CT> self, int a0, int a1, float a2) {
        self.drawScreen(a0, a1, a2);
    }

    public static <CT extends AbstractContainerMenu> void initGui(AbstractContainerScreen<CT> self) {
        self.initGui();
    }

    public static <CT extends AbstractContainerMenu> void onClose(AbstractContainerScreen<CT> self) {
        self.onClose();
    }

    public static <CT extends AbstractContainerMenu> void render(AbstractContainerScreen<CT> self, GuiGraphics a0, int a1, int a2, float a3) {
        self.render(a0, a1, a2, a3);
    }

    public static <CT extends AbstractContainerMenu> void renderBackground(AbstractContainerScreen<CT> self, GuiGraphics a0) {
        self.renderBackground(a0);
    }

    public static <CT extends AbstractContainerMenu> void renderTooltip(AbstractContainerScreen<CT> self, GuiGraphics a0, int a1, int a2) {
        self.renderTooltip(a0, a1, a2);
    }

    public static <CT extends AbstractContainerMenu> void updateScreen(AbstractContainerScreen<CT> self) {
        self.updateScreen();
    }

    public static void apply(ArmPose.Transform self, HumanoidModel<?> a0, EntityLivingBase a1, HumanoidArm a2) {
        self.apply(a0, a1, a2);
    }

    public static String name(ArmPose self) {
        return self.name;
    }

    public static ArmPose.Transform transform(ArmPose self) {
        return self.transform;
    }

    public static boolean twoHanded(ArmPose self) {
        return self.twoHanded;
    }

    public static boolean isTwoHanded(ArmPose self) {
        return self.isTwoHanded();
    }

    public static <CT extends Entity> void renderToBuffer(
        EntityModel<CT> self, PoseStack a0, VertexConsumer a1, int a2, int a3, float a4, float a5, float a6, float a7
    ) {
        self.renderToBuffer(a0, a1, a2, a3, a4, a5, a6, a7);
    }

    public static <CT extends Entity> void setupAnim(EntityModel<CT> self, CT a0, float a1, float a2, float a3, float a4, float a5) {
        self.setupAnim(a0, a1, a2, a3, a4, a5);
    }

    public static <CT extends Entity> void prepareMobModel(EntityModel<CT> self, CT a0, float a1, float a2, float a3) {
        self.prepareMobModel(a0, a1, a2, a3);
    }

    public static boolean visible(HumanoidModel.Part self) {
        return self.visible;
    }

    public static void set_visible(HumanoidModel.Part self, boolean v) {
        self.visible = v;
    }

    public static float x(HumanoidModel.Part self) {
        return self.x;
    }

    public static void set_x(HumanoidModel.Part self, float v) {
        self.x = v;
    }

    public static float xRot(HumanoidModel.Part self) {
        return self.xRot;
    }

    public static void set_xRot(HumanoidModel.Part self, float v) {
        self.xRot = v;
    }

    public static float y(HumanoidModel.Part self) {
        return self.y;
    }

    public static void set_y(HumanoidModel.Part self, float v) {
        self.y = v;
    }

    public static float yRot(HumanoidModel.Part self) {
        return self.yRot;
    }

    public static void set_yRot(HumanoidModel.Part self, float v) {
        self.yRot = v;
    }

    public static float z(HumanoidModel.Part self) {
        return self.z;
    }

    public static void set_z(HumanoidModel.Part self, float v) {
        self.z = v;
    }

    public static float zRot(HumanoidModel.Part self) {
        return self.zRot;
    }

    public static void set_zRot(HumanoidModel.Part self, float v) {
        self.zRot = v;
    }

    public static <CT> void syncFromBiped(HumanoidModel<CT> self) {
        self.syncFromBiped();
    }

    public static String getLayer(ModelLayerLocation self) {
        return self.getLayer();
    }

    public static ResourceLocation getModel(ModelLayerLocation self) {
        return self.getModel();
    }

    public static float maxX(ModelPart.Cube self) {
        return self.maxX;
    }

    public static float maxY(ModelPart.Cube self) {
        return self.maxY;
    }

    public static float maxZ(ModelPart.Cube self) {
        return self.maxZ;
    }

    public static float minX(ModelPart.Cube self) {
        return self.minX;
    }

    public static float minY(ModelPart.Cube self) {
        return self.minY;
    }

    public static float minZ(ModelPart.Cube self) {
        return self.minZ;
    }

    public static boolean skipDraw(ModelPart self) {
        return self.skipDraw;
    }

    public static void set_skipDraw(ModelPart self, boolean v) {
        self.skipDraw = v;
    }

    public static boolean visible(ModelPart self) {
        return self.visible;
    }

    public static void set_visible(ModelPart self, boolean v) {
        self.visible = v;
    }

    public static float x(ModelPart self) {
        return self.x;
    }

    public static void set_x(ModelPart self, float v) {
        self.x = v;
    }

    public static float xRot(ModelPart self) {
        return self.xRot;
    }

    public static void set_xRot(ModelPart self, float v) {
        self.xRot = v;
    }

    public static float xScale(ModelPart self) {
        return self.xScale;
    }

    public static void set_xScale(ModelPart self, float v) {
        self.xScale = v;
    }

    public static float y(ModelPart self) {
        return self.y;
    }

    public static void set_y(ModelPart self, float v) {
        self.y = v;
    }

    public static float yRot(ModelPart self) {
        return self.yRot;
    }

    public static void set_yRot(ModelPart self, float v) {
        self.yRot = v;
    }

    public static float yScale(ModelPart self) {
        return self.yScale;
    }

    public static void set_yScale(ModelPart self, float v) {
        self.yScale = v;
    }

    public static float z(ModelPart self) {
        return self.z;
    }

    public static void set_z(ModelPart self, float v) {
        self.z = v;
    }

    public static float zRot(ModelPart self) {
        return self.zRot;
    }

    public static void set_zRot(ModelPart self, float v) {
        self.zRot = v;
    }

    public static float zScale(ModelPart self) {
        return self.zScale;
    }

    public static void set_zScale(ModelPart self, float v) {
        self.zScale = v;
    }

    public static boolean hasChild(ModelPart self, String a0) {
        return self.hasChild(a0);
    }

    public static boolean isEmpty(ModelPart self) {
        return self.isEmpty();
    }

    public static List<ModelPart> getAllParts(ModelPart self) {
        return self.getAllParts();
    }

    public static ModelPart getChild(ModelPart self, String a0) {
        return self.getChild(a0);
    }

    public static PartPose getInitialPose(ModelPart self) {
        return self.getInitialPose();
    }

    public static PartPose storePose(ModelPart self) {
        return self.storePose();
    }

    public static void copyFrom(ModelPart self, ModelPart a0) {
        self.copyFrom(a0);
    }

    public static void loadPose(ModelPart self, PartPose a0) {
        self.loadPose(a0);
    }

    public static void render(ModelPart self, PoseStack a0, VertexConsumer a1, int a2, int a3) {
        self.render(a0, a1, a2, a3);
    }

    public static void render(ModelPart self, PoseStack a0, VertexConsumer a1, int a2, int a3, float a4, float a5, float a6, float a7) {
        self.render(a0, a1, a2, a3, a4, a5, a6, a7);
    }

    public static void resetPose(ModelPart self) {
        self.resetPose();
    }

    public static void setInitialPose(ModelPart self, PartPose a0) {
        self.setInitialPose(a0);
    }

    public static void setPos(ModelPart self, float a0, float a1, float a2) {
        self.setPos(a0, a1, a2);
    }

    public static void setRotation(ModelPart self, float a0, float a1, float a2) {
        self.setRotation(a0, a1, a2);
    }

    public static void translateAndRotate(ModelPart self, PoseStack a0) {
        self.translateAndRotate(a0);
    }

    public static float x(PartPose self) {
        return self.x;
    }

    public static float xRot(PartPose self) {
        return self.xRot;
    }

    public static float y(PartPose self) {
        return self.y;
    }

    public static float yRot(PartPose self) {
        return self.yRot;
    }

    public static float z(PartPose self) {
        return self.z;
    }

    public static float zRot(PartPose self) {
        return self.zRot;
    }

    public static CubeDeformation extend(CubeDeformation self, float a0) {
        return self.extend(a0);
    }

    public static CubeDeformation extend(CubeDeformation self, float a0, float a1, float a2) {
        return self.extend(a0, a1, a2);
    }

    public static CubeListBuilder addBox(CubeListBuilder self, float a0, float a1, float a2, float a3, float a4, float a5) {
        return self.addBox(a0, a1, a2, a3, a4, a5);
    }

    public static CubeListBuilder addBox(CubeListBuilder self, float a0, float a1, float a2, float a3, float a4, float a5, boolean a6) {
        return self.addBox(a0, a1, a2, a3, a4, a5, a6);
    }

    public static CubeListBuilder addBox(CubeListBuilder self, float a0, float a1, float a2, float a3, float a4, float a5, CubeDeformation a6) {
        return self.addBox(a0, a1, a2, a3, a4, a5, a6);
    }

    public static CubeListBuilder addBox(CubeListBuilder self, String a0, float a1, float a2, float a3, float a4, float a5, float a6) {
        return self.addBox(a0, a1, a2, a3, a4, a5, a6);
    }

    public static CubeListBuilder addBox(CubeListBuilder self, String a0, float a1, float a2, float a3, float a4, float a5, float a6, CubeDeformation a7) {
        return self.addBox(a0, a1, a2, a3, a4, a5, a6, a7);
    }

    public static CubeListBuilder addBox(
        CubeListBuilder self, String a0, float a1, float a2, float a3, int a4, int a5, int a6, CubeDeformation a7, int a8, int a9
    ) {
        return self.addBox(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9);
    }

    public static CubeListBuilder mirror(CubeListBuilder self) {
        return self.mirror();
    }

    public static CubeListBuilder mirror(CubeListBuilder self, boolean a0) {
        return self.mirror(a0);
    }

    public static CubeListBuilder texOffs(CubeListBuilder self, int a0, int a1) {
        return self.texOffs(a0, a1);
    }

    public static ModelPart bakeRoot(LayerDefinition self) {
        return self.bakeRoot();
    }

    public static PartDefinition getRoot(MeshDefinition self) {
        return self.getRoot();
    }

    public static ModelPart bake(PartDefinition self, int a0, int a1) {
        return self.bake(a0, a1);
    }

    public static PartDefinition addOrReplaceChild(PartDefinition self, String a0, CubeListBuilder a1, PartPose a2) {
        return self.addOrReplaceChild(a0, a1, a2);
    }

    public static PartDefinition getChild(PartDefinition self, String a0) {
        return self.getChild(a0);
    }

    public static int age(Particle self) {
        return self.age;
    }

    public static void set_age(Particle self, int v) {
        self.age = v;
    }

    public static float alpha(Particle self) {
        return self.alpha;
    }

    public static void set_alpha(Particle self, float v) {
        self.alpha = v;
    }

    public static float bCol(Particle self) {
        return self.bCol;
    }

    public static void set_bCol(Particle self, float v) {
        self.bCol = v;
    }

    public static float friction(Particle self) {
        return self.friction;
    }

    public static void set_friction(Particle self, float v) {
        self.friction = v;
    }

    public static float gCol(Particle self) {
        return self.gCol;
    }

    public static void set_gCol(Particle self, float v) {
        self.gCol = v;
    }

    public static float gravity(Particle self) {
        return self.gravity;
    }

    public static void set_gravity(Particle self, float v) {
        self.gravity = v;
    }

    public static boolean hasPhysics(Particle self) {
        return self.hasPhysics;
    }

    public static void set_hasPhysics(Particle self, boolean v) {
        self.hasPhysics = v;
    }

    public static int lifetime(Particle self) {
        return self.lifetime;
    }

    public static void set_lifetime(Particle self, int v) {
        self.lifetime = v;
    }

    public static float oRoll(Particle self) {
        return self.oRoll;
    }

    public static void set_oRoll(Particle self, float v) {
        self.oRoll = v;
    }

    public static float quadSize(Particle self) {
        return self.quadSize;
    }

    public static void set_quadSize(Particle self, float v) {
        self.quadSize = v;
    }

    public static float rCol(Particle self) {
        return self.rCol;
    }

    public static void set_rCol(Particle self, float v) {
        self.rCol = v;
    }

    public static Random random(Particle self) {
        return self.random;
    }

    public static boolean removed(Particle self) {
        return self.removed;
    }

    public static void set_removed(Particle self, boolean v) {
        self.removed = v;
    }

    public static float roll(Particle self) {
        return self.roll;
    }

    public static void set_roll(Particle self, float v) {
        self.roll = v;
    }

    public static boolean speedUpWhenYMotionIsBlocked(Particle self) {
        return self.speedUpWhenYMotionIsBlocked;
    }

    public static void set_speedUpWhenYMotionIsBlocked(Particle self, boolean v) {
        self.speedUpWhenYMotionIsBlocked = v;
    }

    public static double x(Particle self) {
        return self.x;
    }

    public static void set_x(Particle self, double v) {
        self.x = v;
    }

    public static double xd(Particle self) {
        return self.xd;
    }

    public static void set_xd(Particle self, double v) {
        self.xd = v;
    }

    public static double xo(Particle self) {
        return self.xo;
    }

    public static void set_xo(Particle self, double v) {
        self.xo = v;
    }

    public static double y(Particle self) {
        return self.y;
    }

    public static void set_y(Particle self, double v) {
        self.y = v;
    }

    public static double yd(Particle self) {
        return self.yd;
    }

    public static void set_yd(Particle self, double v) {
        self.yd = v;
    }

    public static double yo(Particle self) {
        return self.yo;
    }

    public static void set_yo(Particle self, double v) {
        self.yo = v;
    }

    public static double z(Particle self) {
        return self.z;
    }

    public static void set_z(Particle self, double v) {
        self.z = v;
    }

    public static double zd(Particle self) {
        return self.zd;
    }

    public static void set_zd(Particle self, double v) {
        self.zd = v;
    }

    public static double zo(Particle self) {
        return self.zo;
    }

    public static void set_zo(Particle self, double v) {
        self.zo = v;
    }

    public static boolean isAlive(Particle self) {
        return self.isAlive();
    }

    public static float getQuadSize(Particle self, float a0) {
        return self.getQuadSize(a0);
    }

    public static int getBrightnessForRender(Particle self, float a0) {
        return self.getBrightnessForRender(a0);
    }

    public static int getFXLayer(Particle self) {
        return self.getFXLayer();
    }

    public static int getLifetime(Particle self) {
        return self.getLifetime();
    }

    public static int getLightColor(Particle self, float a0) {
        return self.getLightColor(a0);
    }

    public static Particle scale(Particle self, float a0) {
        return self.scale(a0);
    }

    public static Particle setPower(Particle self, float a0) {
        return self.setPower(a0);
    }

    public static ParticleRenderType getRenderType(Particle self) {
        return self.getRenderType();
    }

    public static AxisAlignedBB getBoundingBox(Particle self) {
        return self.getBoundingBox();
    }

    public static void move(Particle self, double a0, double a1, double a2) {
        self.move(a0, a1, a2);
    }

    public static void onUpdate(Particle self) {
        self.onUpdate();
    }

    public static void pickSprite(Particle self, SpriteSet a0) {
        self.pickSprite(a0);
    }

    public static void remove(Particle self) {
        self.remove();
    }

    public static void renderParticle(Particle self, Tessellator a0, float a1, float a2, float a3, float a4, float a5, float a6) {
        self.renderParticle(a0, a1, a2, a3, a4, a5, a6);
    }

    public static void setAlpha(Particle self, float a0) {
        self.setAlpha(a0);
    }

    public static void setColor(Particle self, float a0, float a1, float a2) {
        self.setColor(a0, a1, a2);
    }

    public static void setLifetime(Particle self, int a0) {
        self.setLifetime(a0);
    }

    public static void setSizeCompat(Particle self, float a0, float a1) {
        self.setSizeCompat(a0, a1);
    }

    public static void setSprite(Particle self, IIcon a0) {
        self.setSprite(a0);
    }

    public static void setSpriteFromAge(Particle self, SpriteSet a0) {
        self.setSpriteFromAge(a0);
    }

    public static void tick(Particle self) {
        self.tick();
    }

    public static <CT> Particle createParticle(
        ParticleProvider<CT> self, CT a0, WorldClient a1, double a2, double a3, double a4, double a5, double a6, double a7
    ) {
        return self.createParticle(a0, a1, a2, a3, a4, a5, a6, a7);
    }

    public static IIcon get(SpriteSet self, int a0, int a1) {
        return self.get(a0, a1);
    }

    public static IIcon get(SpriteSet self, Random a0) {
        return self.get(a0);
    }

    public static void onResourceManagerReload(BlockEntityWithoutLevelRenderer self, Object a0) {
        self.onResourceManagerReload(a0);
    }

    public static void renderByItem(
        BlockEntityWithoutLevelRenderer self, ItemStack a0, ItemDisplayContext a1, PoseStack a2, MultiBufferSource a3, int a4, int a5
    ) {
        self.renderByItem(a0, a1, a2, a3, a4, a5);
    }

    public static boolean isFoggyAt(DimensionSpecialEffects self, int a0, int a1) {
        return self.isFoggyAt(a0, a1);
    }

    public static Vec3 getBrightnessDependentFogColor(DimensionSpecialEffects self, Vec3 a0, float a1) {
        return self.getBrightnessDependentFogColor(a0, a1);
    }

    public static boolean constantAmbientLight(DimensionSpecialEffects self) {
        return self.constantAmbientLight();
    }

    public static boolean forceBrightLightmap(DimensionSpecialEffects self) {
        return self.forceBrightLightmap();
    }

    public static boolean hasGround(DimensionSpecialEffects self) {
        return self.hasGround();
    }

    public static boolean renderClouds(
        DimensionSpecialEffects self, WorldClient a0, int a1, float a2, PoseStack a3, double a4, double a5, double a6, Matrix4f a7
    ) {
        return self.renderClouds(a0, a1, a2, a3, a4, a5, a6, a7);
    }

    public static boolean renderSky(
        DimensionSpecialEffects self, WorldClient a0, int a1, float a2, PoseStack a3, Camera a4, Matrix4f a5, boolean a6, Runnable a7
    ) {
        return self.renderSky(a0, a1, a2, a3, a4, a5, a6, a7);
    }

    public static boolean renderSnowAndRain(DimensionSpecialEffects self, WorldClient a0, int a1, float a2, LightTexture a3, double a4, double a5, double a6) {
        return self.renderSnowAndRain(a0, a1, a2, a3, a4, a5, a6);
    }

    public static boolean tickRain(DimensionSpecialEffects self, WorldClient a0, int a1, Camera a2) {
        return self.tickRain(a0, a1, a2);
    }

    public static float getCloudHeight(DimensionSpecialEffects self) {
        return self.getCloudHeight();
    }

    public static float[] getSunriseColor(DimensionSpecialEffects self, float a0, float a1) {
        return self.getSunriseColor(a0, a1);
    }

    public static SkyType skyType(DimensionSpecialEffects self) {
        return self.skyType();
    }

    public static void adjustLightmapColors(DimensionSpecialEffects self, WorldClient a0, float a1, float a2, float a3, float a4, int a5, int a6, Vector3f a7) {
        self.adjustLightmapColors(a0, a1, a2, a3, a4, a5, a6, a7);
    }

    public static boolean colored(ShaderInstance self) {
        return self.colored;
    }

    public static boolean textured(ShaderInstance self) {
        return self.textured;
    }

    public static Object getBlockRenderDispatcher(Context self) {
        return self.getBlockRenderDispatcher();
    }

    public static Object getItemRenderer(Context self) {
        return self.getItemRenderer();
    }

    public static Object getModelManager(Context self) {
        return self.getModelManager();
    }

    public static ModelPart bakeLayer(Context self, ModelLayerLocation a0) {
        return self.bakeLayer(a0);
    }

    public static Object getRenderer(EntityRenderDispatcher self, Object a0) {
        return self.getRenderer(a0);
    }

    public static <CT extends Entity> ResourceLocation getTextureLocation(EntityRenderer<CT> self, CT a0) {
        return self.getTextureLocation(a0);
    }

    public static <CT extends Entity> void doRender(EntityRenderer<CT> self, Entity a0, double a1, double a2, double a3, float a4, float a5) {
        self.doRender(a0, a1, a2, a3, a4, a5);
    }

    public static <CT extends Entity> void render(EntityRenderer<CT> self, CT a0, float a1, float a2, PoseStack a3, MultiBufferSource a4, int a5) {
        self.render(a0, a1, a2, a3, a4, a5);
    }

    public static <CT> Object create(EntityRendererProvider<CT> self, Context a0) {
        return self.create(a0);
    }

    public static <CT extends EntityLiving, CM> ResourceLocation getTextureLocation(HumanoidMobRenderer<CT, CM> self, CT a0) {
        return self.getTextureLocation(a0);
    }

    public static <CT extends EntityLiving, CM> boolean addLayer(HumanoidMobRenderer<CT, CM> self, Object a0) {
        return self.addLayer(a0);
    }

    public static void doRender(ThrownItemRenderer self, Entity a0, double a1, double a2, double a3, float a4, float a5) {
        self.doRender(a0, a1, a2, a3, a4, a5);
    }

    public static PlayerModel getModel(PlayerRenderer self) {
        return self.getModel();
    }

    public static boolean hasPermission(CommandSourceStack self, int a0) {
        return self.hasPermission(a0);
    }

    public static String getTextName(CommandSourceStack self) {
        return self.getTextName();
    }

    public static CommandContext toContext(CommandSourceStack self) {
        return self.toContext();
    }

    public static CommandSourceStack withSuppressedOutput(CommandSourceStack self) {
        return self.withSuppressedOutput();
    }

    public static Component getDisplayName(CommandSourceStack self) {
        return self.getDisplayName();
    }

    public static Vec2 getRotation(CommandSourceStack self) {
        return self.getRotation();
    }

    public static Vec3 getPosition(CommandSourceStack self) {
        return self.getPosition();
    }

    public static Entity getEntity(CommandSourceStack self) {
        return self.getEntity();
    }

    public static MinecraftServer getServer(CommandSourceStack self) {
        return self.getServer();
    }

    public static World getLevel(CommandSourceStack self) {
        return self.getLevel();
    }

    public static World getUnsidedLevel(CommandSourceStack self) {
        return self.getUnsidedLevel();
    }

    public static void sendFailure(CommandSourceStack self, Component a0) {
        self.sendFailure(a0);
    }

    public static void sendSuccess(CommandSourceStack self, Supplier<Component> a0, boolean a1) {
        self.sendSuccess(a0, a1);
    }

    public static int performCommand(Commands self, Object a0, String a1) {
        return self.performCommand(a0, a1);
    }

    public static int performPrefixedCommand(Commands self, CommandSourceStack a0, String a1) {
        return self.performPrefixedCommand(a0, a1);
    }

    public static Object getDispatcher(Commands self) {
        return self.getDispatcher();
    }

    public static void register(Commands self, LiteralArgumentBuilder a0) {
        self.register(a0);
    }

    public static boolean isHorizontal(net.mcreator.boh.compat.mc.core.Axis self) {
        return self.isHorizontal();
    }

    public static boolean isVertical(net.mcreator.boh.compat.mc.core.Axis self) {
        return self.isVertical();
    }

    public static double choose(net.mcreator.boh.compat.mc.core.Axis self, double a0, double a1, double a2) {
        return self.choose(a0, a1, a2);
    }

    public static int choose(net.mcreator.boh.compat.mc.core.Axis self, int a0, int a1, int a2) {
        return self.choose(a0, a1, a2);
    }

    public static String getName(net.mcreator.boh.compat.mc.core.Axis self) {
        return self.getName();
    }

    public static String getSerializedName(net.mcreator.boh.compat.mc.core.Axis self) {
        return self.getSerializedName();
    }

    public static int getStep(AxisDirection self) {
        return self.getStep();
    }

    public static long asLong(BlockPos self) {
        return self.asLong();
    }

    public static BlockPos above(BlockPos self) {
        return self.above();
    }

    public static BlockPos above(BlockPos self, int a0) {
        return self.above(a0);
    }

    public static BlockPos atY(BlockPos self, int a0) {
        return self.atY(a0);
    }

    public static BlockPos below(BlockPos self) {
        return self.below();
    }

    public static BlockPos below(BlockPos self, int a0) {
        return self.below(a0);
    }

    public static BlockPos east(BlockPos self) {
        return self.east();
    }

    public static BlockPos east(BlockPos self, int a0) {
        return self.east(a0);
    }

    public static BlockPos immutable(BlockPos self) {
        return self.immutable();
    }

    public static BlockPos multiply(BlockPos self, int a0) {
        return self.multiply(a0);
    }

    public static BlockPos north(BlockPos self) {
        return self.north();
    }

    public static BlockPos north(BlockPos self, int a0) {
        return self.north(a0);
    }

    public static BlockPos offset(BlockPos self, double a0, double a1, double a2) {
        return self.offset(a0, a1, a2);
    }

    public static BlockPos offset(BlockPos self, int a0, int a1, int a2) {
        return self.offset(a0, a1, a2);
    }

    public static BlockPos offset(BlockPos self, Vec3i a0) {
        return self.offset(a0);
    }

    public static BlockPos relative(BlockPos self, Direction a0) {
        return self.relative(a0);
    }

    public static BlockPos relative(BlockPos self, Direction a0, int a1) {
        return self.relative(a0, a1);
    }

    public static BlockPos south(BlockPos self) {
        return self.south();
    }

    public static BlockPos south(BlockPos self, int a0) {
        return self.south(a0);
    }

    public static BlockPos subtract(BlockPos self, Vec3i a0) {
        return self.subtract(a0);
    }

    public static BlockPos west(BlockPos self) {
        return self.west();
    }

    public static BlockPos west(BlockPos self, int a0) {
        return self.west(a0);
    }

    public static BlockPos.MutableBlockPos mutable(BlockPos self) {
        return self.mutable();
    }

    public static Vec3 getCenter(BlockPos self) {
        return self.getCenter();
    }

    public static float toYRot(Direction self) {
        return self.toYRot();
    }

    public static int get2DDataValue(Direction self) {
        return self.get2DDataValue();
    }

    public static int get3DDataValue(Direction self) {
        return self.get3DDataValue();
    }

    public static int getStepX(Direction self) {
        return self.getStepX();
    }

    public static int getStepY(Direction self) {
        return self.getStepY();
    }

    public static int getStepZ(Direction self) {
        return self.getStepZ();
    }

    public static String getSerializedName(Direction self) {
        return self.getSerializedName();
    }

    public static net.mcreator.boh.compat.mc.core.Axis getAxis(Direction self) {
        return self.getAxis();
    }

    public static AxisDirection getAxisDirection(Direction self) {
        return self.getAxisDirection();
    }

    public static Direction getClockWise(Direction self) {
        return self.getClockWise();
    }

    public static Direction getCounterClockWise(Direction self) {
        return self.getCounterClockWise();
    }

    public static Vec3i getNormal(Direction self) {
        return self.getNormal();
    }

    public static <CT> CT value(Holder.Reference<CT> self) {
        return self.value();
    }

    public static <CT> boolean is(Holder.Reference<CT> self, ResourceKey<?> a0) {
        return self.is(a0);
    }

    public static <CT> ResourceKey<CT> key(Holder.Reference<CT> self) {
        return self.key();
    }

    public static <CT> CT get(Holder<CT> self) {
        return self.get();
    }

    public static <CE> CE get(NonNullList<CE> self, int a0) {
        return self.get(a0);
    }

    public static <CE> CE remove(NonNullList<CE> self, int a0) {
        return self.remove(a0);
    }

    public static <CE> CE set(NonNullList<CE> self, int a0, CE a1) {
        return self.set(a0, a1);
    }

    public static <CE> int size(NonNullList<CE> self) {
        return self.size();
    }

    public static <CE> void add(NonNullList<CE> self, int a0, CE a1) {
        self.add(a0, a1);
    }

    public static <CE> void clear(NonNullList<CE> self) {
        self.clear();
    }

    public static <CT> CT get(Registry<CT> self, ResourceKey<CT> a0) {
        return self.get(a0);
    }

    public static <CT> Optional<Holder.Reference<CT>> getHolder(Registry<CT> self, ResourceKey<CT> a0) {
        return self.getHolder(a0);
    }

    public static <CT> ResourceKey<?> key(Registry<CT> self) {
        return self.key();
    }

    public static boolean closerThan(Vec3i self, Vec3i a0, double a1) {
        return self.closerThan(a0, a1);
    }

    public static double distSqr(Vec3i self, Vec3i a0) {
        return self.distSqr(a0);
    }

    public static double distToCenterSqr(Vec3i self, double a0, double a1, double a2) {
        return self.distToCenterSqr(a0, a1, a2);
    }

    public static int distManhattan(Vec3i self, Vec3i a0) {
        return self.distManhattan(a0);
    }

    public static ParticleType<?> getType(ParticleOptions self) {
        return self.getType();
    }

    public static <CT extends ParticleOptions> boolean getOverrideLimiter(ParticleType<CT> self) {
        return self.getOverrideLimiter();
    }

    public static <CT extends ParticleOptions> String legacyName(ParticleType<CT> self) {
        return self.legacyName();
    }

    public static <CT extends ParticleOptions> ParticleType<CT> withLegacyName(ParticleType<CT> self, String a0) {
        return self.withLegacyName(a0);
    }

    public static <CT extends ParticleOptions> ResourceLocation getId(ParticleType<CT> self) {
        return self.getId();
    }

    public static <CT extends ParticleOptions> void setRegistryName(ParticleType<CT> self, ResourceLocation a0) {
        self.setRegistryName(a0);
    }

    public static SimpleParticleType getType(SimpleParticleType self) {
        return self.getType();
    }

    public static <T extends Enum<T>> T readEnum(FriendlyByteBuf self, Class<T> a0) {
        return self.readEnum(a0);
    }

    public static boolean readBoolean(FriendlyByteBuf self) {
        return self.readBoolean();
    }

    public static byte readByte(FriendlyByteBuf self) {
        return self.readByte();
    }

    public static double readDouble(FriendlyByteBuf self) {
        return self.readDouble();
    }

    public static float readFloat(FriendlyByteBuf self) {
        return self.readFloat();
    }

    public static int readInt(FriendlyByteBuf self) {
        return self.readInt();
    }

    public static int readVarInt(FriendlyByteBuf self) {
        return self.readVarInt();
    }

    public static int readableBytes(FriendlyByteBuf self) {
        return self.readableBytes();
    }

    public static ByteBuf unwrap(FriendlyByteBuf self) {
        return self.unwrap();
    }

    public static String readUtf(FriendlyByteBuf self) {
        return self.readUtf();
    }

    public static String readUtf(FriendlyByteBuf self, int a0) {
        return self.readUtf(a0);
    }

    public static long readLong(FriendlyByteBuf self) {
        return self.readLong();
    }

    public static BlockPos readBlockPos(FriendlyByteBuf self) {
        return self.readBlockPos();
    }

    public static FriendlyByteBuf writeBlockPos(FriendlyByteBuf self, BlockPos a0) {
        return self.writeBlockPos(a0);
    }

    public static FriendlyByteBuf writeBoolean(FriendlyByteBuf self, boolean a0) {
        return self.writeBoolean(a0);
    }

    public static FriendlyByteBuf writeByte(FriendlyByteBuf self, int a0) {
        return self.writeByte(a0);
    }

    public static FriendlyByteBuf writeComponent(FriendlyByteBuf self, Component a0) {
        return self.writeComponent(a0);
    }

    public static FriendlyByteBuf writeDouble(FriendlyByteBuf self, double a0) {
        return self.writeDouble(a0);
    }

    public static FriendlyByteBuf writeEnum(FriendlyByteBuf self, Enum<?> a0) {
        return self.writeEnum(a0);
    }

    public static FriendlyByteBuf writeFloat(FriendlyByteBuf self, float a0) {
        return self.writeFloat(a0);
    }

    public static FriendlyByteBuf writeInt(FriendlyByteBuf self, int a0) {
        return self.writeInt(a0);
    }

    public static FriendlyByteBuf writeItem(FriendlyByteBuf self, ItemStack a0) {
        return self.writeItem(a0);
    }

    public static FriendlyByteBuf writeLong(FriendlyByteBuf self, long a0) {
        return self.writeLong(a0);
    }

    public static FriendlyByteBuf writeNbt(FriendlyByteBuf self, NBTTagCompound a0) {
        return self.writeNbt(a0);
    }

    public static FriendlyByteBuf writeUtf(FriendlyByteBuf self, String a0) {
        return self.writeUtf(a0);
    }

    public static FriendlyByteBuf writeVarInt(FriendlyByteBuf self, int a0) {
        return self.writeVarInt(a0);
    }

    public static Component readComponent(FriendlyByteBuf self) {
        return self.readComponent();
    }

    public static ItemStack readItem(FriendlyByteBuf self) {
        return self.readItem();
    }

    public static NBTTagCompound readNbt(FriendlyByteBuf self) {
        return self.readNbt();
    }

    public static String getFormattedText(Component self) {
        return self.getFormattedText();
    }

    public static MutableComponent append(Component self, String a0) {
        return self.append(a0);
    }

    public static MutableComponent append(Component self, Component a0) {
        return self.append(a0);
    }

    public static MutableComponent plainCopy(Component self) {
        return self.plainCopy();
    }

    public static MutableComponent withStyle(Component self, ChatFormatting a0) {
        return self.withStyle(a0);
    }

    public static MutableComponent withStyle(Component self, ChatFormatting... a0) {
        return self.withStyle(a0);
    }

    public static IChatComponent toVanilla(Component self) {
        return self.toVanilla();
    }

    public static void sendTo(ClientboundGameEventPacket self, EntityPlayerMP a0) {
        self.sendTo(a0);
    }

    public static void sendTo(ClientboundLevelEventPacket self, EntityPlayerMP a0) {
        self.sendTo(a0);
    }

    public static void sendTo(ClientboundPlayerAbilitiesPacket self, EntityPlayerMP a0) {
        self.sendTo(a0);
    }

    public static void sendTo(ClientboundUpdateMobEffectPacket self, EntityPlayerMP a0) {
        self.sendTo(a0);
    }

    public static void sendTo(CompatPacket self, EntityPlayerMP a0) {
        self.sendTo(a0);
    }

    public static EntityPlayerMP player(PlayerConnection self) {
        return self.player;
    }

    public static void send(PlayerConnection self, Object a0) {
        self.send(a0);
    }

    public static <CT> int getId(EntityDataAccessor<CT> self) {
        return self.getId();
    }

    public static <CT> EntityDataSerializer<CT> getSerializer(EntityDataAccessor<CT> self) {
        return self.getSerializer();
    }

    public static <CT> Class<CT> type(EntityDataSerializer<CT> self) {
        return self.type();
    }

    public static <T> T get(SynchedEntityData self, EntityDataAccessor<T> a0) {
        return self.get(a0);
    }

    public static <T> void define(SynchedEntityData self, EntityDataAccessor<T> a0, T a1) {
        self.define(a0, a1);
    }

    public static Entity getEntity(SynchedEntityData self) {
        return self.getEntity();
    }

    public static <CT> boolean isFor(ResourceKey<CT> self, ResourceKey<?> a0) {
        return self.isFor(a0);
    }

    public static <CT> ResourceLocation registry(ResourceKey<CT> self) {
        return self.registry();
    }

    public static Advancement getAdvancement(ServerAdvancementManager self, ResourceLocation a0) {
        return self.getAdvancement(a0);
    }

    public static boolean isVisible(ServerBossEvent self) {
        return self.isVisible();
    }

    public static float getProgress(ServerBossEvent self) {
        return self.getProgress();
    }

    public static Set<EntityPlayerMP> getPlayers(ServerBossEvent self) {
        return self.getPlayers();
    }

    public static void addPlayer(ServerBossEvent self, EntityPlayerMP a0) {
        self.addPlayer(a0);
    }

    public static void removeAllPlayers(ServerBossEvent self) {
        self.removeAllPlayers();
    }

    public static void removePlayer(ServerBossEvent self, EntityPlayerMP a0) {
        self.removePlayer(a0);
    }

    public static void setCreateWorldFog(ServerBossEvent self, boolean a0) {
        self.setCreateWorldFog(a0);
    }

    public static void setDarkenScreen(ServerBossEvent self, boolean a0) {
        self.setDarkenScreen(a0);
    }

    public static void setName(ServerBossEvent self, Component a0) {
        self.setName(a0);
    }

    public static void setPlayBossMusic(ServerBossEvent self, boolean a0) {
        self.setPlayBossMusic(a0);
    }

    public static void setProgress(ServerBossEvent self, float a0) {
        self.setProgress(a0);
    }

    public static void setVisible(ServerBossEvent self, boolean a0) {
        self.setVisible(a0);
    }

    public static String legacyName(SoundEvent self) {
        return self.legacyName();
    }

    public static ResourceLocation getLocation(SoundEvent self) {
        return self.getLocation();
    }

    public static String getName(SoundSource self) {
        return self.getName();
    }

    public static <CT> boolean contains(TagKey<CT> self, String a0) {
        return self.contains(a0);
    }

    public static <CT> boolean contains(TagKey<CT> self, ResourceLocation a0) {
        return self.contains(a0);
    }

    public static <CT> String kind(TagKey<CT> self) {
        return self.kind();
    }

    public static <CT> Set<String> members(TagKey<CT> self) {
        return self.members();
    }

    public static <CT> ResourceLocation location(TagKey<CT> self) {
        return self.location();
    }

    public static boolean nextBoolean(RandomSource self) {
        return self.nextBoolean();
    }

    public static double nextDouble(RandomSource self) {
        return self.nextDouble();
    }

    public static double triangle(RandomSource self, double a0, double a1) {
        return self.triangle(a0, a1);
    }

    public static float nextFloat(RandomSource self) {
        return self.nextFloat();
    }

    public static int nextInt(RandomSource self) {
        return self.nextInt();
    }

    public static int nextInt(RandomSource self, int a0) {
        return self.nextInt(a0);
    }

    public static int nextInt(RandomSource self, int a0, int a1) {
        return self.nextInt(a0, a1);
    }

    public static int nextIntBetweenInclusive(RandomSource self, int a0, int a1) {
        return self.nextIntBetweenInclusive(a0, a1);
    }

    public static long nextLong(RandomSource self) {
        return self.nextLong();
    }

    public static RandomSource fork(RandomSource self) {
        return self.fork();
    }

    public static double nextGaussian(RandomSource self) {
        return self.nextGaussian();
    }

    public static void setSeed(RandomSource self, long a0) {
        self.setSeed(a0);
    }

    public static int rgb(BossBarColor self) {
        return self.rgb;
    }

    public static int notches(BossBarOverlay self) {
        return self.notches;
    }

    public static int getId(Difficulty self) {
        return self.getId();
    }

    public static EnumDifficulty toVanilla(Difficulty self) {
        return self.toVanilla();
    }

    public static boolean isHard(DifficultyInstance self) {
        return self.isHard();
    }

    public static boolean isHarderThan(DifficultyInstance self, float a0) {
        return self.isHarderThan(a0);
    }

    public static float getEffectiveDifficulty(DifficultyInstance self) {
        return self.getEffectiveDifficulty();
    }

    public static float getSpecialMultiplier(DifficultyInstance self) {
        return self.getSpecialMultiplier();
    }

    public static Difficulty getDifficulty(DifficultyInstance self) {
        return self.getDifficulty();
    }

    public static boolean consumesAction(InteractionResult self) {
        return self.consumesAction();
    }

    public static boolean shouldAwardStats(InteractionResult self) {
        return self.shouldAwardStats();
    }

    public static boolean shouldSwing(InteractionResult self) {
        return self.shouldSwing();
    }

    public static <CT> CT getObject(InteractionResultHolder<CT> self) {
        return self.getObject();
    }

    public static <CT> InteractionResult getResult(InteractionResultHolder<CT> self) {
        return self.getResult();
    }

    public static Component getDisplayName(MenuProvider self) {
        return self.getDisplayName();
    }

    public static Container createMenu(MenuProvider self, int a0, InventoryPlayer a1, EntityPlayer a2) {
        return self.createMenu(a0, a1, a2);
    }

    public static boolean canPlaceItemThroughFace(WorldlyContainer self, int a0, ItemStack a1, Direction a2) {
        return self.canPlaceItemThroughFace(a0, a1, a2);
    }

    public static boolean canTakeItemThroughFace(WorldlyContainer self, int a0, ItemStack a1, Direction a2) {
        return self.canTakeItemThroughFace(a0, a1, a2);
    }

    public static int[] getSlotsForFace(WorldlyContainer self, Direction a0) {
        return self.getSlotsForFace(a0);
    }

    public static boolean canExtractItem(WorldlyContainer self, int a0, ItemStack a1, int a2) {
        return self.canExtractItem(a0, a1, a2);
    }

    public static boolean canInsertItem(WorldlyContainer self, int a0, ItemStack a1, int a2) {
        return self.canInsertItem(a0, a1, a2);
    }

    public static int[] getAccessibleSlotsFromSide(WorldlyContainer self, int a0) {
        return self.getAccessibleSlotsFromSide(a0);
    }

    public static <CT extends Entity> Builder<CT> canSpawnFarFromPlayer(Builder<CT> self) {
        return self.canSpawnFarFromPlayer();
    }

    public static <CT extends Entity> Builder<CT> clientTrackingRange(Builder<CT> self, int a0) {
        return self.clientTrackingRange(a0);
    }

    public static <CT extends Entity> Builder<CT> fireImmune(Builder<CT> self) {
        return self.fireImmune();
    }

    public static <CT extends Entity> Builder<CT> noSave(Builder<CT> self) {
        return self.noSave();
    }

    public static <CT extends Entity> Builder<CT> noSummon(Builder<CT> self) {
        return self.noSummon();
    }

    public static <CT extends Entity> Builder<CT> setCustomClientFactory(Builder<CT> self, Function<World, CT> a0) {
        return self.setCustomClientFactory(a0);
    }

    public static <CT extends Entity> Builder<CT> setShouldReceiveVelocityUpdates(Builder<CT> self, boolean a0) {
        return self.setShouldReceiveVelocityUpdates(a0);
    }

    public static <CT extends Entity> Builder<CT> setTrackingRange(Builder<CT> self, int a0) {
        return self.setTrackingRange(a0);
    }

    public static <CT extends Entity> Builder<CT> setUpdateInterval(Builder<CT> self, int a0) {
        return self.setUpdateInterval(a0);
    }

    public static <CT extends Entity> Builder<CT> sized(Builder<CT> self, float a0, float a1) {
        return self.sized(a0, a1);
    }

    public static <CT extends Entity> Builder<CT> updateInterval(Builder<CT> self, int a0) {
        return self.updateInterval(a0);
    }

    public static <CT extends Entity> EntityType<CT> build(Builder<CT> self, String a0) {
        return self.build(a0);
    }

    public static boolean fixed(EntityDimensions self) {
        return self.fixed;
    }

    public static float height(EntityDimensions self) {
        return self.height;
    }

    public static float width(EntityDimensions self) {
        return self.width;
    }

    public static EntityDimensions scale(EntityDimensions self, float a0) {
        return self.scale(a0);
    }

    public static EntityDimensions scale(EntityDimensions self, float a0, float a1) {
        return self.scale(a0, a1);
    }

    public static <CT extends Entity> CT create(EntityType.EntityFactory<CT> self, EntityType<CT> a0, World a1) {
        return self.create(a0, a1);
    }

    public static <CT extends Entity> CT spawn(EntityType<CT> self, World a0, Object a1, Object a2, BlockPos a3, MobSpawnType a4, boolean a5, boolean a6) {
        return self.spawn(a0, a1, a2, a3, a4, a5, a6);
    }

    public static <CT extends Entity> boolean fireImmune(EntityType<CT> self) {
        return self.fireImmune();
    }

    public static <CT extends Entity> boolean is(EntityType<CT> self, TagKey<?> a0) {
        return self.is(a0);
    }

    public static <CT extends Entity> boolean is(EntityType<CT> self, EntityType<?> a0) {
        return self.is(a0);
    }

    public static <CT extends Entity> boolean trackDeltas(EntityType<CT> self) {
        return self.trackDeltas();
    }

    public static <CT extends Entity> float getHeight(EntityType<CT> self) {
        return self.getHeight();
    }

    public static <CT extends Entity> float getWidth(EntityType<CT> self) {
        return self.getWidth();
    }

    public static <CT extends Entity> int clientTrackingRange(EntityType<CT> self) {
        return self.clientTrackingRange();
    }

    public static <CT extends Entity> int updateInterval(EntityType<CT> self) {
        return self.updateInterval();
    }

    public static <CT extends Entity> Class<CT> getEntityClass(EntityType<CT> self) {
        return self.getEntityClass();
    }

    public static <CT extends Entity> String getDescriptionId(EntityType<CT> self) {
        return self.getDescriptionId();
    }

    public static <CT extends Entity> Component getDescription(EntityType<CT> self) {
        return self.getDescription();
    }

    public static <CT extends Entity> EntityDimensions getDimensions(EntityType<CT> self) {
        return self.getDimensions();
    }

    public static <CT extends Entity> MobCategory getCategory(EntityType<CT> self) {
        return self.getCategory();
    }

    public static <CT extends Entity> ResourceLocation getId(EntityType<CT> self) {
        return self.getId();
    }

    public static <CT extends Entity> void setRegistryName(EntityType<CT> self, ResourceLocation a0) {
        self.setRegistryName(a0);
    }

    public static boolean isArmor(EquipmentSlot self) {
        return self.isArmor();
    }

    public static int armorInventoryIndex(EquipmentSlot self) {
        return self.armorInventoryIndex();
    }

    public static int getFilterFlag(EquipmentSlot self) {
        return self.getFilterFlag();
    }

    public static int getIndex(EquipmentSlot self) {
        return self.getIndex();
    }

    public static int legacyIndex(EquipmentSlot self) {
        return self.legacyIndex();
    }

    public static String getName(EquipmentSlot self) {
        return self.getName();
    }

    public static EquipmentSlot.Type getType(EquipmentSlot self) {
        return self.getType();
    }

    public static HumanoidArm getOpposite(HumanoidArm self) {
        return self.getOpposite();
    }

    public static String getName(MobCategory self) {
        return self.getName();
    }

    public static EnumCreatureType toVanilla(MobCategory self) {
        return self.toVanilla();
    }

    public static EnumCreatureAttribute toVanilla(MobType self) {
        return self.toVanilla();
    }

    public static boolean shouldDestroy(RemovalReason self) {
        return self.shouldDestroy();
    }

    public static Types heightmap(SpawnPlacements.Data self) {
        return self.heightmap;
    }

    public static Type placement(SpawnPlacements.Data self) {
        return self.placement;
    }

    public static SpawnPlacements.SpawnPredicate<?> predicate(SpawnPlacements.Data self) {
        return self.predicate;
    }

    public static <CT extends Entity> boolean test(
        SpawnPlacements.SpawnPredicate<CT> self, EntityType<CT> a0, World a1, MobSpawnType a2, BlockPos a3, RandomSource a4
    ) {
        return self.test(a0, a1, a2, a3, a4);
    }

    public static boolean isMoving(WalkAnimationState self) {
        return self.isMoving();
    }

    public static void setSpeed(WalkAnimationState self, float a0) {
        self.setSpeed(a0);
    }

    public static boolean hasModifier(AttributeInstance self, AttributeModifier a0) {
        return self.hasModifier(a0);
    }

    public static double getBaseValue(AttributeInstance self) {
        return self.getBaseValue();
    }

    public static double getValue(AttributeInstance self) {
        return self.getValue();
    }

    public static AttributeModifier getModifier(AttributeInstance self, UUID a0) {
        return self.getModifier(a0);
    }

    public static IAttributeInstance vanilla(AttributeInstance self) {
        return self.vanilla();
    }

    public static void addPermanentModifier(AttributeInstance self, AttributeModifier a0) {
        self.addPermanentModifier(a0);
    }

    public static void addTransientModifier(AttributeInstance self, AttributeModifier a0) {
        self.addTransientModifier(a0);
    }

    public static void removeModifier(AttributeInstance self, UUID a0) {
        self.removeModifier(a0);
    }

    public static void removeModifier(AttributeInstance self, AttributeModifier a0) {
        self.removeModifier(a0);
    }

    public static void setBaseValue(AttributeInstance self, double a0) {
        self.setBaseValue(a0);
    }

    public static boolean hasAttribute(AttributeSupplier self, IAttribute a0) {
        return self.hasAttribute(a0);
    }

    public static double getBaseValue(AttributeSupplier self, IAttribute a0) {
        return self.getBaseValue(a0);
    }

    public static void applyTo(AttributeSupplier self, EntityLivingBase a0) {
        self.applyTo(a0);
    }

    public static AttributeSupplier build(net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder self) {
        return self.build();
    }

    public static net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder add(
        net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder self, IAttribute a0
    ) {
        return self.add(a0);
    }

    public static net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder add(
        net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder self, IAttribute a0, double a1
    ) {
        return self.add(a0, a1);
    }

    public static net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder combine(
        net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder self, net.mcreator.boh.compat.mc.world.entity.ai.attributes.Builder a0
    ) {
        return self.combine(a0);
    }

    public static boolean hasWanted(FlyingMoveControl self) {
        return self.hasWanted();
    }

    public static void setWantedPosition(FlyingMoveControl self, double a0, double a1, double a2, double a3) {
        self.setWantedPosition(a0, a1, a2, a3);
    }

    public static void tick(FlyingMoveControl self) {
        self.tick();
    }

    public static boolean isLookingAtTarget(LookControl self) {
        return self.isLookingAtTarget();
    }

    public static double getWantedX(LookControl self) {
        return self.getWantedX();
    }

    public static double getWantedY(LookControl self) {
        return self.getWantedY();
    }

    public static double getWantedZ(LookControl self) {
        return self.getWantedZ();
    }

    public static void setLookAt(LookControl self, double a0, double a1, double a2) {
        self.setLookAt(a0, a1, a2);
    }

    public static void setLookAt(LookControl self, double a0, double a1, double a2, float a3, float a4) {
        self.setLookAt(a0, a1, a2, a3, a4);
    }

    public static void setLookAt(LookControl self, Vec3 a0) {
        self.setLookAt(a0);
    }

    public static void setLookAt(LookControl self, Entity a0) {
        self.setLookAt(a0);
    }

    public static void setLookAt(LookControl self, Entity a0, float a1, float a2) {
        self.setLookAt(a0, a1, a2);
    }

    public static double wantedX(MoveControl self) {
        return self.wantedX;
    }

    public static void set_wantedX(MoveControl self, double v) {
        self.wantedX = v;
    }

    public static double wantedY(MoveControl self) {
        return self.wantedY;
    }

    public static void set_wantedY(MoveControl self, double v) {
        self.wantedY = v;
    }

    public static double wantedZ(MoveControl self) {
        return self.wantedZ;
    }

    public static void set_wantedZ(MoveControl self, double v) {
        self.wantedZ = v;
    }

    public static boolean hasWanted(MoveControl self) {
        return self.hasWanted();
    }

    public static double getSpeedModifier(MoveControl self) {
        return self.getSpeedModifier();
    }

    public static double getWantedX(MoveControl self) {
        return self.getWantedX();
    }

    public static double getWantedY(MoveControl self) {
        return self.getWantedY();
    }

    public static double getWantedZ(MoveControl self) {
        return self.getWantedZ();
    }

    public static void setWantedPosition(MoveControl self, double a0, double a1, double a2, double a3) {
        self.setWantedPosition(a0, a1, a2, a3);
    }

    public static void strafe(MoveControl self, float a0, float a1) {
        self.strafe(a0, a1);
    }

    public static void tick(MoveControl self) {
        self.tick();
    }

    public static boolean canUse(Goal self) {
        return self.canUse();
    }

    public static boolean canContinueToUse(Goal self) {
        return self.canContinueToUse();
    }

    public static boolean isInterruptable(Goal self) {
        return self.isInterruptable();
    }

    public static boolean requiresUpdateEveryTick(Goal self) {
        return self.requiresUpdateEveryTick();
    }

    public static boolean continueExecuting(Goal self) {
        return self.continueExecuting();
    }

    public static boolean isInterruptible(Goal self) {
        return self.isInterruptible();
    }

    public static boolean shouldExecute(Goal self) {
        return self.shouldExecute();
    }

    public static void resetTask(Goal self) {
        self.resetTask();
    }

    public static void startExecuting(Goal self) {
        self.startExecuting();
    }

    public static void updateTask(Goal self) {
        self.updateTask();
    }

    public static EnumSet<Flag> getFlags(Goal self) {
        return self.getFlags();
    }

    public static void setFlags(Goal self, EnumSet<Flag> a0) {
        self.setFlags(a0);
    }

    public static void start(Goal self) {
        self.start();
    }

    public static void stop(Goal self) {
        self.stop();
    }

    public static void tick(Goal self) {
        self.tick();
    }

    public static Set<EntityAIBase> getAvailableGoals(GoalSelector self) {
        return self.getAvailableGoals();
    }

    public static Stream<EntityAIBase> getRunningGoals(GoalSelector self) {
        return self.getRunningGoals();
    }

    public static EntityAITasks tasks(GoalSelector self) {
        return self.tasks();
    }

    public static void addGoal(GoalSelector self, int a0, EntityAIBase a1) {
        self.addGoal(a0, a1);
    }

    public static void removeAllGoals(GoalSelector self) {
        self.removeAllGoals();
    }

    public static void removeAllGoals(GoalSelector self, Predicate<EntityAIBase> a0) {
        self.removeAllGoals(a0);
    }

    public static void removeGoal(GoalSelector self, EntityAIBase a0) {
        self.removeGoal(a0);
    }

    public static void tick(GoalSelector self) {
        self.tick();
    }

    public static boolean canContinueToUse(MeleeAttackGoal self) {
        return self.canContinueToUse();
    }

    public static boolean canUse(MeleeAttackGoal self) {
        return self.canUse();
    }

    public static boolean requiresUpdateEveryTick(MeleeAttackGoal self) {
        return self.requiresUpdateEveryTick();
    }

    public static void start(MeleeAttackGoal self) {
        self.start();
    }

    public static void stop(MeleeAttackGoal self) {
        self.stop();
    }

    public static void tick(MeleeAttackGoal self) {
        self.tick();
    }

    public static boolean canContinueToUse(RandomStrollGoal self) {
        return self.canContinueToUse();
    }

    public static boolean canUse(RandomStrollGoal self) {
        return self.canUse();
    }

    public static void setInterval(RandomStrollGoal self, int a0) {
        self.setInterval(a0);
    }

    public static void start(RandomStrollGoal self) {
        self.start();
    }

    public static void stop(RandomStrollGoal self) {
        self.stop();
    }

    public static void trigger(RandomStrollGoal self) {
        self.trigger();
    }

    public static boolean canContinueToUse(RemoveBlockGoal self) {
        return self.canContinueToUse();
    }

    public static boolean canUse(RemoveBlockGoal self) {
        return self.canUse();
    }

    public static void start(RemoveBlockGoal self) {
        self.start();
    }

    public static void stop(RemoveBlockGoal self) {
        self.stop();
    }

    public static void tick(RemoveBlockGoal self) {
        self.tick();
    }

    public static boolean canContinueToUse(WrappedGoal self) {
        return self.canContinueToUse();
    }

    public static boolean canUse(WrappedGoal self) {
        return self.canUse();
    }

    public static boolean isInterruptable(WrappedGoal self) {
        return self.isInterruptable();
    }

    public static EntityAIBase getGoal(WrappedGoal self) {
        return self.getGoal();
    }

    public static void start(WrappedGoal self) {
        self.start();
    }

    public static void stop(WrappedGoal self) {
        self.stop();
    }

    public static void tick(WrappedGoal self) {
        self.tick();
    }

    public static HurtByTargetGoal setAlertOthers(HurtByTargetGoal self, Class<?>... a0) {
        return self.setAlertOthers(a0);
    }

    public static boolean noPath(FlyingNavigator self) {
        return self.noPath();
    }

    public static boolean setPath(FlyingNavigator self, PathEntity a0, double a1) {
        return self.setPath(a0, a1);
    }

    public static boolean tryMoveToEntityLiving(FlyingNavigator self, Entity a0, double a1) {
        return self.tryMoveToEntityLiving(a0, a1);
    }

    public static boolean tryMoveToXYZ(FlyingNavigator self, double a0, double a1, double a2, double a3) {
        return self.tryMoveToXYZ(a0, a1, a2, a3);
    }

    public static PathEntity getPath(FlyingNavigator self) {
        return self.getPath();
    }

    public static PathEntity getPathToEntityLiving(FlyingNavigator self, Entity a0) {
        return self.getPathToEntityLiving(a0);
    }

    public static PathEntity getPathToXYZ(FlyingNavigator self, double a0, double a1, double a2) {
        return self.getPathToXYZ(a0, a1, a2);
    }

    public static void clearPathEntity(FlyingNavigator self) {
        self.clearPathEntity();
    }

    public static void onUpdateNavigation(FlyingNavigator self) {
        self.onUpdateNavigation();
    }

    public static void setSpeed(FlyingNavigator self, double a0) {
        self.setSpeed(a0);
    }

    public static boolean canFloat(PathNavigation self) {
        return self.canFloat();
    }

    public static boolean isDone(PathNavigation self) {
        return self.isDone();
    }

    public static boolean isInProgress(PathNavigation self) {
        return self.isInProgress();
    }

    public static boolean isStableDestination(PathNavigation self, BlockPos a0) {
        return self.isStableDestination(a0);
    }

    public static boolean moveTo(PathNavigation self, double a0, double a1, double a2, double a3) {
        return self.moveTo(a0, a1, a2, a3);
    }

    public static boolean moveTo(PathNavigation self, Path a0, double a1) {
        return self.moveTo(a0, a1);
    }

    public static boolean moveTo(PathNavigation self, Entity a0, double a1) {
        return self.moveTo(a0, a1);
    }

    public static Path createPath(PathNavigation self, double a0, double a1, double a2, int a3) {
        return self.createPath(a0, a1, a2, a3);
    }

    public static Path createPath(PathNavigation self, BlockPos a0, int a1) {
        return self.createPath(a0, a1);
    }

    public static Path createPath(PathNavigation self, Entity a0, int a1) {
        return self.createPath(a0, a1);
    }

    public static Path getPath(PathNavigation self) {
        return self.getPath();
    }

    public static PathNavigate vanilla(PathNavigation self) {
        return self.vanilla();
    }

    public static void recomputePath(PathNavigation self) {
        self.recomputePath();
    }

    public static void setCanFloat(PathNavigation self, boolean a0) {
        self.setCanFloat(a0);
    }

    public static void setCanOpenDoors(PathNavigation self, boolean a0) {
        self.setCanOpenDoors(a0);
    }

    public static void setCanPassDoors(PathNavigation self, boolean a0) {
        self.setCanPassDoors(a0);
    }

    public static void setSpeedModifier(PathNavigation self, double a0) {
        self.setSpeedModifier(a0);
    }

    public static void stop(PathNavigation self) {
        self.stop();
    }

    public static void tick(PathNavigation self) {
        self.tick();
    }

    public static boolean hasLineOfSight(Sensing self, Entity a0) {
        return self.hasLineOfSight(a0);
    }

    public static Set<?> states(PoiType self) {
        return self.states();
    }

    public static int getHeight(PaintingVariant self) {
        return self.getHeight();
    }

    public static int getWidth(PaintingVariant self) {
        return self.getWidth();
    }

    public static ResourceLocation getId(PaintingVariant self) {
        return self.getId();
    }

    public static void setRegistryName(PaintingVariant self, ResourceLocation a0) {
        self.setRegistryName(a0);
    }

    public static void performRangedAttack(RangedAttackMob self, EntityLivingBase a0, float a1) {
        self.performRangedAttack(a0, a1);
    }

    public static void attackEntityWithRangedAttack(RangedAttackMob self, EntityLivingBase a0, float a1) {
        self.attackEntityWithRangedAttack(a0, a1);
    }

    public static int legacyId(VillagerProfession self) {
        return self.legacyId();
    }

    public static SoundEvent workSound(VillagerProfession self) {
        return self.workSound();
    }

    public static void setLegacyId(VillagerProfession self, int a0) {
        self.setLegacyId(a0);
    }

    public static ItemStack getItem(ItemSupplier self) {
        return self.getItem();
    }

    public static net.mcreator.boh.compat.mc.world.food.Builder alwaysEat(net.mcreator.boh.compat.mc.world.food.Builder self) {
        return self.alwaysEat();
    }

    public static net.mcreator.boh.compat.mc.world.food.Builder effect(net.mcreator.boh.compat.mc.world.food.Builder self, Supplier<PotionEffect> a0, float a1) {
        return self.effect(a0, a1);
    }

    public static net.mcreator.boh.compat.mc.world.food.Builder effect(net.mcreator.boh.compat.mc.world.food.Builder self, PotionEffect a0, float a1) {
        return self.effect(a0, a1);
    }

    public static net.mcreator.boh.compat.mc.world.food.Builder fast(net.mcreator.boh.compat.mc.world.food.Builder self) {
        return self.fast();
    }

    public static net.mcreator.boh.compat.mc.world.food.Builder meat(net.mcreator.boh.compat.mc.world.food.Builder self) {
        return self.meat();
    }

    public static net.mcreator.boh.compat.mc.world.food.Builder nutrition(net.mcreator.boh.compat.mc.world.food.Builder self, int a0) {
        return self.nutrition(a0);
    }

    public static net.mcreator.boh.compat.mc.world.food.Builder saturationMod(net.mcreator.boh.compat.mc.world.food.Builder self, float a0) {
        return self.saturationMod(a0);
    }

    public static FoodProperties build(net.mcreator.boh.compat.mc.world.food.Builder self) {
        return self.build();
    }

    public static boolean canAlwaysEat(FoodProperties self) {
        return self.canAlwaysEat();
    }

    public static boolean isFastFood(FoodProperties self) {
        return self.isFastFood();
    }

    public static boolean isMeat(FoodProperties self) {
        return self.isMeat();
    }

    public static List<Object[]> getEffects(FoodProperties self) {
        return self.getEffects();
    }

    public static int containerId(AbstractContainerMenu self) {
        return self.containerId;
    }

    public static void set_containerId(AbstractContainerMenu self, int v) {
        self.containerId = v;
    }

    public static List<Slot> slots(AbstractContainerMenu self) {
        return self.slots;
    }

    public static boolean stillValid(AbstractContainerMenu self, EntityPlayer a0) {
        return self.stillValid(a0);
    }

    public static boolean canInteractWith(AbstractContainerMenu self, EntityPlayer a0) {
        return self.canInteractWith(a0);
    }

    public static MenuType<?> getType(AbstractContainerMenu self) {
        return self.getType();
    }

    public static Slot addSlot(AbstractContainerMenu self, Slot a0) {
        return self.addSlot(a0);
    }

    public static ItemStack quickMoveStack(AbstractContainerMenu self, EntityPlayer a0, int a1) {
        return self.quickMoveStack(a0, a1);
    }

    public static ItemStack transferStackInSlot(AbstractContainerMenu self, EntityPlayer a0, int a1) {
        return self.transferStackInSlot(a0, a1);
    }

    public static void broadcastChanges(AbstractContainerMenu self) {
        self.broadcastChanges();
    }

    public static void onContainerClosed(AbstractContainerMenu self, EntityPlayer a0) {
        self.onContainerClosed(a0);
    }

    public static void removed(AbstractContainerMenu self, EntityPlayer a0) {
        self.removed(a0);
    }

    public static IInventory container(ChestMenu self) {
        return self.container;
    }

    public static boolean stillValid(ChestMenu self, EntityPlayer a0) {
        return self.stillValid(a0);
    }

    public static BlockPos pos(ContainerLevelAccess self) {
        return self.pos;
    }

    public static World world(ContainerLevelAccess self) {
        return self.world;
    }

    public static <CT> CT create(MenuType.MenuFactory<CT> self, int a0, InventoryPlayer a1, FriendlyByteBuf a2) {
        return self.create(a0, a1, a2);
    }

    public static <CT> CT create(MenuType<CT> self, int a0, InventoryPlayer a1, FriendlyByteBuf a2) {
        return self.create(a0, a1, a2);
    }

    public static <CT> int guiId(MenuType<CT> self) {
        return self.guiId();
    }

    public static <CT> ResourceLocation getId(MenuType<CT> self) {
        return self.getId();
    }

    public static <CT> void bind(MenuType<CT> self, ResourceLocation a0, int a1) {
        self.bind(a0, a1);
    }

    public static boolean isValidArmor(ArmorItem self, ItemStack a0, int a1, Entity a2) {
        return self.isValidArmor(a0, a1, a2);
    }

    public static int getArmorDisplay(ArmorItem self, EntityPlayer a0, ItemStack a1, int a2) {
        return self.getArmorDisplay(a0, a1, a2);
    }

    public static int getDefense(ArmorItem self) {
        return self.getDefense();
    }

    public static int getEnchantmentValue(ArmorItem self) {
        return self.getEnchantmentValue();
    }

    public static String getArmorTexture(ArmorItem self, ItemStack a0, Entity a1, int a2, String a3) {
        return self.getArmorTexture(a0, a1, a2, a3);
    }

    public static String getArmorTexture(ArmorItem self, ItemStack a0, Entity a1, EquipmentSlot a2, String a3) {
        return self.getArmorTexture(a0, a1, a2, a3);
    }

    public static ArmorMaterial getMaterial(ArmorItem self) {
        return self.getMaterial();
    }

    public static net.mcreator.boh.compat.mc.world.item.Type getType(ArmorItem self) {
        return self.getType();
    }

    public static ModelBiped getArmorModel(ArmorItem self, EntityLivingBase a0, ItemStack a1, int a2) {
        return self.getArmorModel(a0, a1, a2);
    }

    public static ArmorProperties getProperties(ArmorItem self, EntityLivingBase a0, ItemStack a1, DamageSource a2, double a3, int a4) {
        return self.getProperties(a0, a1, a2, a3, a4);
    }

    public static void damageArmor(ArmorItem self, EntityLivingBase a0, ItemStack a1, DamageSource a2, int a3, int a4) {
        self.damageArmor(a0, a1, a2, a3, a4);
    }

    public static float getKnockbackResistance(ArmorMaterial self) {
        return self.getKnockbackResistance();
    }

    public static float getToughness(ArmorMaterial self) {
        return self.getToughness();
    }

    public static int getDefenseForType(ArmorMaterial self, net.mcreator.boh.compat.mc.world.item.Type a0) {
        return self.getDefenseForType(a0);
    }

    public static int getDurabilityForType(ArmorMaterial self, net.mcreator.boh.compat.mc.world.item.Type a0) {
        return self.getDurabilityForType(a0);
    }

    public static int getEnchantmentValue(ArmorMaterial self) {
        return self.getEnchantmentValue();
    }

    public static String getName(ArmorMaterial self) {
        return self.getName();
    }

    public static SoundEvent getEquipSound(ArmorMaterial self) {
        return self.getEquipSound();
    }

    public static Ingredient getRepairIngredient(ArmorMaterial self) {
        return self.getRepairIngredient();
    }

    public static net.mcreator.boh.compat.mc.world.item.Builder displayItems(
        net.mcreator.boh.compat.mc.world.item.Builder self, CreativeModeTab.DisplayItemsGenerator a0
    ) {
        return self.displayItems(a0);
    }

    public static net.mcreator.boh.compat.mc.world.item.Builder icon(net.mcreator.boh.compat.mc.world.item.Builder self, Supplier<ItemStack> a0) {
        return self.icon(a0);
    }

    public static net.mcreator.boh.compat.mc.world.item.Builder title(net.mcreator.boh.compat.mc.world.item.Builder self, Component a0) {
        return self.title(a0);
    }

    public static net.mcreator.boh.compat.mc.world.item.Builder withSearchBar(net.mcreator.boh.compat.mc.world.item.Builder self) {
        return self.withSearchBar();
    }

    public static net.mcreator.boh.compat.mc.world.item.Builder withSearchBar(net.mcreator.boh.compat.mc.world.item.Builder self, int a0) {
        return self.withSearchBar(a0);
    }

    public static net.mcreator.boh.compat.mc.world.item.Builder withTabsAfter(net.mcreator.boh.compat.mc.world.item.Builder self, Object... a0) {
        return self.withTabsAfter(a0);
    }

    public static net.mcreator.boh.compat.mc.world.item.Builder withTabsBefore(net.mcreator.boh.compat.mc.world.item.Builder self, Object... a0) {
        return self.withTabsBefore(a0);
    }

    public static CreativeModeTab build(net.mcreator.boh.compat.mc.world.item.Builder self) {
        return self.build();
    }

    public static void accept(CreativeModeTab.DisplayItemsGenerator self, Object a0, CreativeModeTab.Output a1) {
        self.accept(a0, a1);
    }

    public static void accept(CreativeModeTab.Output self, Object a0) {
        self.accept(a0);
    }

    public static List<ItemStack> displayItems(CreativeModeTab self) {
        return self.displayItems();
    }

    public static CreativeTabs toVanilla(CreativeModeTab self, String a0) {
        return self.toVanilla(a0);
    }

    public static CreativeTabs vanilla(CreativeModeTab self) {
        return self.vanilla();
    }

    public static boolean firstPerson(ItemDisplayContext self) {
        return self.firstPerson();
    }

    public static boolean hurtEnemy(PickaxeItem self, ItemStack a0, EntityLivingBase a1, EntityLivingBase a2) {
        return self.hurtEnemy(a0, a1, a2);
    }

    public static boolean isCorrectToolForDrops(PickaxeItem self, BlockState a0) {
        return self.isCorrectToolForDrops(a0);
    }

    public static boolean isFull3D(PickaxeItem self) {
        return self.isFull3D();
    }

    public static Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(PickaxeItem self, EquipmentSlot a0) {
        return self.getDefaultAttributeModifiers(a0);
    }

    public static float getDestroySpeed(PickaxeItem self, ItemStack a0, BlockState a1) {
        return self.getDestroySpeed(a0, a1);
    }

    public static int getEnchantmentValue(PickaxeItem self) {
        return self.getEnchantmentValue();
    }

    public static boolean canRepair(Properties self) {
        return self.canRepair;
    }

    public static void set_canRepair(Properties self, boolean v) {
        self.canRepair = v;
    }

    public static Item craftingRemainder(Properties self) {
        return self.craftingRemainder;
    }

    public static void set_craftingRemainder(Properties self, Item v) {
        self.craftingRemainder = v;
    }

    public static boolean fireResistant(Properties self) {
        return self.fireResistant;
    }

    public static void set_fireResistant(Properties self, boolean v) {
        self.fireResistant = v;
    }

    public static FoodProperties food(Properties self) {
        return self.food;
    }

    public static void set_food(Properties self, FoodProperties v) {
        self.food = v;
    }

    public static int maxDamage(Properties self) {
        return self.maxDamage;
    }

    public static void set_maxDamage(Properties self, int v) {
        self.maxDamage = v;
    }

    public static int maxStackSize(Properties self) {
        return self.maxStackSize;
    }

    public static void set_maxStackSize(Properties self, int v) {
        self.maxStackSize = v;
    }

    public static Rarity rarity(Properties self) {
        return self.rarity;
    }

    public static void set_rarity(Properties self, Rarity v) {
        self.rarity = v;
    }

    public static Properties craftRemainder(Properties self, Item a0) {
        return self.craftRemainder(a0);
    }

    public static Properties defaultDurability(Properties self, int a0) {
        return self.defaultDurability(a0);
    }

    public static Properties durability(Properties self, int a0) {
        return self.durability(a0);
    }

    public static Properties food(Properties self, FoodProperties a0) {
        return self.food(a0);
    }

    public static Properties rarity(Properties self, Rarity a0) {
        return self.rarity(a0);
    }

    public static Properties setNoRepair(Properties self) {
        return self.setNoRepair();
    }

    public static Properties stacksTo(Properties self, int a0) {
        return self.stacksTo(a0);
    }

    public static EnumRarity toVanilla(Rarity self) {
        return self.toVanilla();
    }

    public static String getRecordNameLocal(RecordItem self) {
        return self.getRecordNameLocal();
    }

    public static ResourceLocation getRecordResource(RecordItem self, String a0) {
        return self.getRecordResource(a0);
    }

    public static boolean hurtEnemy(SwordItem self, ItemStack a0, EntityLivingBase a1, EntityLivingBase a2) {
        return self.hurtEnemy(a0, a1, a2);
    }

    public static boolean isCorrectToolForDrops(SwordItem self, BlockState a0) {
        return self.isCorrectToolForDrops(a0);
    }

    public static boolean isFull3D(SwordItem self) {
        return self.isFull3D();
    }

    public static Multimap<IAttribute, AttributeModifier> getDefaultAttributeModifiers(SwordItem self, EquipmentSlot a0) {
        return self.getDefaultAttributeModifiers(a0);
    }

    public static float getDamage(SwordItem self) {
        return self.getDamage();
    }

    public static float getDestroySpeed(SwordItem self, ItemStack a0, BlockState a1) {
        return self.getDestroySpeed(a0, a1);
    }

    public static int getEnchantmentValue(SwordItem self) {
        return self.getEnchantmentValue();
    }

    public static Tier getTier(SwordItem self) {
        return self.getTier();
    }

    public static float getAttackDamageBonus(Tier self) {
        return self.getAttackDamageBonus();
    }

    public static float getSpeed(Tier self) {
        return self.getSpeed();
    }

    public static int getEnchantmentValue(Tier self) {
        return self.getEnchantmentValue();
    }

    public static int getLevel(Tier self) {
        return self.getLevel();
    }

    public static int getUses(Tier self) {
        return self.getUses();
    }

    public static Ingredient getRepairIngredient(Tier self) {
        return self.getRepairIngredient();
    }

    public static boolean isAdvanced(TooltipFlag self) {
        return self.isAdvanced();
    }

    public static int legacyArmorType(net.mcreator.boh.compat.mc.world.item.Type self) {
        return self.legacyArmorType();
    }

    public static String getName(net.mcreator.boh.compat.mc.world.item.Type self) {
        return self.getName();
    }

    public static EquipmentSlot getSlot(net.mcreator.boh.compat.mc.world.item.Type self) {
        return self.getSlot();
    }

    public static EnumAction toVanilla(UseAnim self) {
        return self.toVanilla();
    }

    public static List<PotionEffect> getEffects(BrewPotion self) {
        return self.getEffects();
    }

    public static ResourceLocation getId(BrewPotion self) {
        return self.getId();
    }

    public static void setRegistryName(BrewPotion self, ResourceLocation a0) {
        self.setRegistryName(a0);
    }

    public static boolean canPlace(BlockPlaceContext self) {
        return self.canPlace();
    }

    public static boolean replacingClickedOnBlock(BlockPlaceContext self) {
        return self.replacingClickedOnBlock();
    }

    public static Direction getNearestLookingDirection(BlockPlaceContext self) {
        return self.getNearestLookingDirection();
    }

    public static Direction[] getNearestLookingDirections(BlockPlaceContext self) {
        return self.getNearestLookingDirections();
    }

    public static boolean isInside(UseOnContext self) {
        return self.isInside();
    }

    public static boolean isSecondaryUseActive(UseOnContext self) {
        return self.isSecondaryUseActive();
    }

    public static float getRotation(UseOnContext self) {
        return self.getRotation();
    }

    public static BlockPos getClickedPos(UseOnContext self) {
        return self.getClickedPos();
    }

    public static Direction getClickedFace(UseOnContext self) {
        return self.getClickedFace();
    }

    public static Direction getHorizontalDirection(UseOnContext self) {
        return self.getHorizontalDirection();
    }

    public static InteractionHand getHand(UseOnContext self) {
        return self.getHand();
    }

    public static BlockHitResult getHitResult(UseOnContext self) {
        return self.getHitResult();
    }

    public static Vec3 getClickLocation(UseOnContext self) {
        return self.getClickLocation();
    }

    public static EntityPlayer getPlayer(UseOnContext self) {
        return self.getPlayer();
    }

    public static ItemStack getItemInHand(UseOnContext self) {
        return self.getItemInHand();
    }

    public static World getLevel(UseOnContext self) {
        return self.getLevel();
    }

    public static boolean isEmpty(Ingredient self) {
        return self.isEmpty();
    }

    public static boolean test(Ingredient self, ItemStack a0) {
        return self.test(a0);
    }

    public static Item firstItem(Ingredient self) {
        return self.firstItem();
    }

    public static ItemStack[] getItems(Ingredient self) {
        return self.getItems();
    }

    public static EnumEnchantmentType legacy(EnchantmentCategory self) {
        return self.legacy;
    }

    public static Predicate<Item> predicate(EnchantmentCategory self) {
        return self.predicate;
    }

    public static boolean canEnchant(EnchantmentCategory self, Item a0) {
        return self.canEnchant(a0);
    }

    public static int weight(net.mcreator.boh.compat.mc.world.item.enchantment.Rarity self) {
        return self.weight;
    }

    public static ClipBlock block(ClipContext self) {
        return self.block;
    }

    public static ClipFluid fluid(ClipContext self) {
        return self.fluid;
    }

    public static Vec3 from(ClipContext self) {
        return self.from;
    }

    public static Vec3 to(ClipContext self) {
        return self.to;
    }

    public static Vec3 getFrom(ClipContext self) {
        return self.getFrom();
    }

    public static Vec3 getTo(ClipContext self) {
        return self.getTo();
    }

    public static boolean isCreative(GameType self) {
        return self.isCreative();
    }

    public static boolean isSurvival(GameType self) {
        return self.isSurvival();
    }

    public static String getName(GameType self) {
        return self.getName();
    }

    public static net.minecraft.world.WorldSettings.GameType toVanilla(GameType self) {
        return self.toVanilla();
    }

    public static EnumSkyBlock toVanilla(LightLayer self) {
        return self.toVanilla();
    }

    public static boolean isOpaqueCube(BaseEntityBlock self) {
        return self.isOpaqueCube();
    }

    public static boolean renderAsNormalBlock(BaseEntityBlock self) {
        return self.renderAsNormalBlock();
    }

    public static int getRenderType(BaseEntityBlock self) {
        return self.getRenderType();
    }

    public static RenderShape getRenderShape(BaseEntityBlock self, BlockState a0) {
        return self.getRenderShape(a0);
    }

    public static boolean isFlammable(ButtonBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.isFlammable(a0, a1, a2, a3, a4);
    }

    public static int getFlammability(ButtonBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Direction a3) {
        return self.getFlammability(a0, a1, a2, a3);
    }

    public static int getFlammability(ButtonBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.getFlammability(a0, a1, a2, a3, a4);
    }

    public static int getLightBlock(ButtonBlock self, BlockState a0, IBlockAccess a1, BlockPos a2) {
        return self.getLightBlock(a0, a1, a2);
    }

    public static int tickRate(ButtonBlock self, World a0) {
        return self.tickRate(a0);
    }

    public static IIcon getIcon(ButtonBlock self, int a0, int a1) {
        return self.getIcon(a0, a1);
    }

    public static void onRegistered(ButtonBlock self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static void registerBlockIcons(ButtonBlock self, IIconRegister a0) {
        self.registerBlockIcons(a0);
    }

    public static boolean isFlammable(DoorBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.isFlammable(a0, a1, a2, a3, a4);
    }

    public static boolean onBlockActivated(DoorBlock self, World a0, int a1, int a2, int a3, EntityPlayer a4, int a5, float a6, float a7, float a8) {
        return self.onBlockActivated(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static int getFlammability(DoorBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Direction a3) {
        return self.getFlammability(a0, a1, a2, a3);
    }

    public static int getFlammability(DoorBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.getFlammability(a0, a1, a2, a3, a4);
    }

    public static int getLightBlock(DoorBlock self, BlockState a0, IBlockAccess a1, BlockPos a2) {
        return self.getLightBlock(a0, a1, a2);
    }

    public static String getItemIconName(DoorBlock self) {
        return self.getItemIconName();
    }

    public static InteractionResult use(DoorBlock self, BlockState a0, World a1, BlockPos a2, EntityPlayer a3, InteractionHand a4, BlockHitResult a5) {
        return self.use(a0, a1, a2, a3, a4, a5);
    }

    public static BlockSetType type(DoorBlock self) {
        return self.type();
    }

    public static Item getItem(DoorBlock self, World a0, int a1, int a2, int a3) {
        return self.getItem(a0, a1, a2, a3);
    }

    public static Item getItemDropped(DoorBlock self, int a0, Random a1, int a2) {
        return self.getItemDropped(a0, a1, a2);
    }

    public static IIcon getIcon(DoorBlock self, int a0, int a1) {
        return self.getIcon(a0, a1);
    }

    public static IIcon getIcon(DoorBlock self, IBlockAccess a0, int a1, int a2, int a3, int a4) {
        return self.getIcon(a0, a1, a2, a3, a4);
    }

    public static void onRegistered(DoorBlock self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static void registerBlockIcons(DoorBlock self, IIconRegister a0) {
        self.registerBlockIcons(a0);
    }

    public static TileEntity newBlockEntity(EntityBlock self, BlockPos a0, BlockState a1) {
        return self.newBlockEntity(a0, a1);
    }

    public static void neighborChanged(FallingBlock self, BlockState a0, World a1, BlockPos a2, Block a3, BlockPos a4, boolean a5) {
        self.neighborChanged(a0, a1, a2, a3, a4, a5);
    }

    public static void onPlace(FallingBlock self, BlockState a0, World a1, BlockPos a2, BlockState a3, boolean a4) {
        self.onPlace(a0, a1, a2, a3, a4);
    }

    public static void tick(FallingBlock self, BlockState a0, WorldServer a1, BlockPos a2, RandomSource a3) {
        self.tick(a0, a1, a2, a3);
    }

    public static boolean canConnectFenceTo(FenceBlock self, IBlockAccess a0, int a1, int a2, int a3) {
        return self.canConnectFenceTo(a0, a1, a2, a3);
    }

    public static boolean isFlammable(FenceBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.isFlammable(a0, a1, a2, a3, a4);
    }

    public static int getFlammability(FenceBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Direction a3) {
        return self.getFlammability(a0, a1, a2, a3);
    }

    public static int getFlammability(FenceBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.getFlammability(a0, a1, a2, a3, a4);
    }

    public static int getLightBlock(FenceBlock self, BlockState a0, IBlockAccess a1, BlockPos a2) {
        return self.getLightBlock(a0, a1, a2);
    }

    public static IIcon getIcon(FenceBlock self, int a0, int a1) {
        return self.getIcon(a0, a1);
    }

    public static void onRegistered(FenceBlock self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static void registerBlockIcons(FenceBlock self, IIconRegister a0) {
        self.registerBlockIcons(a0);
    }

    public static boolean isFlammable(FenceGateBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.isFlammable(a0, a1, a2, a3, a4);
    }

    public static int getFlammability(FenceGateBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Direction a3) {
        return self.getFlammability(a0, a1, a2, a3);
    }

    public static int getFlammability(FenceGateBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.getFlammability(a0, a1, a2, a3, a4);
    }

    public static int getLightBlock(FenceGateBlock self, BlockState a0, IBlockAccess a1, BlockPos a2) {
        return self.getLightBlock(a0, a1, a2);
    }

    public static IIcon getIcon(FenceGateBlock self, int a0, int a1) {
        return self.getIcon(a0, a1);
    }

    public static void onRegistered(FenceGateBlock self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static void registerBlockIcons(FenceGateBlock self, IIconRegister a0) {
        self.registerBlockIcons(a0);
    }

    public static boolean canBlockStay(FlowerBlock self, World a0, int a1, int a2, int a3) {
        return self.canBlockStay(a0, a1, a2, a3);
    }

    public static boolean canPlaceBlockAt(FlowerBlock self, World a0, int a1, int a2, int a3) {
        return self.canPlaceBlockAt(a0, a1, a2, a3);
    }

    public static int getEffectDuration(FlowerBlock self) {
        return self.getEffectDuration();
    }

    public static VoxelShape getShape(FlowerBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, CollisionContext a3) {
        return self.getShape(a0, a1, a2, a3);
    }

    public static Potion getSuspiciousEffect(FlowerBlock self) {
        return self.getSuspiciousEffect();
    }

    public static void onNeighborBlockChange(FlowerBlock self, World a0, int a1, int a2, int a3, Block a4) {
        self.onNeighborBlockChange(a0, a1, a2, a3, a4);
    }

    public static boolean isLeaves(LeavesBlock self, IBlockAccess a0, int a1, int a2, int a3) {
        return self.isLeaves(a0, a1, a2, a3);
    }

    public static boolean isOpaqueCube(LeavesBlock self) {
        return self.isOpaqueCube();
    }

    public static boolean renderAsNormalBlock(LeavesBlock self) {
        return self.renderAsNormalBlock();
    }

    public static boolean shouldSideBeRendered(LeavesBlock self, IBlockAccess a0, int a1, int a2, int a3, int a4) {
        return self.shouldSideBeRendered(a0, a1, a2, a3, a4);
    }

    public static int quantityDroppedBase(LeavesBlock self, Random a0) {
        return self.quantityDroppedBase(a0);
    }

    public static BlockState getStateForPlacement(LeavesBlock self, BlockPlaceContext a0) {
        return self.getStateForPlacement(a0);
    }

    public static void beginLeavesDecay(LeavesBlock self, World a0, int a1, int a2, int a3) {
        self.beginLeavesDecay(a0, a1, a2, a3);
    }

    public static void randomTick(LeavesBlock self, BlockState a0, WorldServer a1, BlockPos a2, RandomSource a3) {
        self.randomTick(a0, a1, a2, a3);
    }

    public static Direction mirror(Mirror self, Direction a0) {
        return self.mirror(a0);
    }

    public static Rotation getRotation(Mirror self, Direction a0) {
        return self.getRotation(a0);
    }

    public static boolean isFlammable(PressurePlateBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.isFlammable(a0, a1, a2, a3, a4);
    }

    public static int getFlammability(PressurePlateBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Direction a3) {
        return self.getFlammability(a0, a1, a2, a3);
    }

    public static int getFlammability(PressurePlateBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.getFlammability(a0, a1, a2, a3, a4);
    }

    public static int getLightBlock(PressurePlateBlock self, BlockState a0, IBlockAccess a1, BlockPos a2) {
        return self.getLightBlock(a0, a1, a2);
    }

    public static IIcon getIcon(PressurePlateBlock self, int a0, int a1) {
        return self.getIcon(a0, a1);
    }

    public static void onRegistered(PressurePlateBlock self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static void registerBlockIcons(PressurePlateBlock self, IIconRegister a0) {
        self.registerBlockIcons(a0);
    }

    public static Direction rotate(Rotation self, Direction a0) {
        return self.rotate(a0);
    }

    public static Rotation getRotated(Rotation self, Rotation a0) {
        return self.getRotated(a0);
    }

    public static boolean canBlockStay(SaplingBlock self, World a0, int a1, int a2, int a3) {
        return self.canBlockStay(a0, a1, a2, a3);
    }

    public static boolean canPlaceBlockAt(SaplingBlock self, World a0, int a1, int a2, int a3) {
        return self.canPlaceBlockAt(a0, a1, a2, a3);
    }

    public static boolean func_149851_a(SaplingBlock self, World a0, int a1, int a2, int a3, boolean a4) {
        return self.func_149851_a(a0, a1, a2, a3, a4);
    }

    public static boolean func_149852_a(SaplingBlock self, World a0, Random a1, int a2, int a3, int a4) {
        return self.func_149852_a(a0, a1, a2, a3, a4);
    }

    public static int getFireSpreadSpeed(SaplingBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Direction a3) {
        return self.getFireSpreadSpeed(a0, a1, a2, a3);
    }

    public static VoxelShape getShape(SaplingBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, CollisionContext a3) {
        return self.getShape(a0, a1, a2, a3);
    }

    public static void advanceTree(SaplingBlock self, World a0, BlockPos a1, BlockState a2, RandomSource a3) {
        self.advanceTree(a0, a1, a2, a3);
    }

    public static void func_149853_b(SaplingBlock self, World a0, Random a1, int a2, int a3, int a4) {
        self.func_149853_b(a0, a1, a2, a3, a4);
    }

    public static void onNeighborBlockChange(SaplingBlock self, World a0, int a1, int a2, int a3, Block a4) {
        self.onNeighborBlockChange(a0, a1, a2, a3, a4);
    }

    public static void randomTick(SaplingBlock self, BlockState a0, WorldServer a1, BlockPos a2, RandomSource a3) {
        self.randomTick(a0, a1, a2, a3);
    }

    public static boolean isOpaqueCube(SlabBlock self) {
        return self.isOpaqueCube();
    }

    public static boolean renderAsNormalBlock(SlabBlock self) {
        return self.renderAsNormalBlock();
    }

    public static boolean shouldSideBeRendered(SlabBlock self, IBlockAccess a0, int a1, int a2, int a3, int a4) {
        return self.shouldSideBeRendered(a0, a1, a2, a3, a4);
    }

    public static boolean tryMerge(SlabBlock self, ItemStack a0, EntityPlayer a1, World a2, int a3, int a4, int a5, int a6, float a7) {
        return self.tryMerge(a0, a1, a2, a3, a4, a5, a6, a7);
    }

    public static int quantityDropped(SlabBlock self, int a0, int a1, Random a2) {
        return self.quantityDropped(a0, a1, a2);
    }

    public static BlockState getStateForPlacement(SlabBlock self, BlockPlaceContext a0) {
        return self.getStateForPlacement(a0);
    }

    public static VoxelShape getShape(SlabBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, CollisionContext a3) {
        return self.getShape(a0, a1, a2, a3);
    }

    public static float getPitch(SoundType self) {
        return self.getPitch();
    }

    public static float getVolume(SoundType self) {
        return self.getVolume();
    }

    public static String materialHint(SoundType self) {
        return self.materialHint();
    }

    public static net.minecraft.block.Block.SoundType toVanilla(SoundType self) {
        return self.toVanilla();
    }

    public static boolean isFlammable(StairBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.isFlammable(a0, a1, a2, a3, a4);
    }

    public static boolean isRandomlyTicking(StairBlock self, BlockState a0) {
        return self.isRandomlyTicking(a0);
    }

    public static float getExplosionResistance(StairBlock self) {
        return self.getExplosionResistance();
    }

    public static float getExplosionResistance(StairBlock self, Entity a0) {
        return self.getExplosionResistance(a0);
    }

    public static int getFlammability(StairBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Direction a3) {
        return self.getFlammability(a0, a1, a2, a3);
    }

    public static int getFlammability(StairBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.getFlammability(a0, a1, a2, a3, a4);
    }

    public static int getLightBlock(StairBlock self, BlockState a0, IBlockAccess a1, BlockPos a2) {
        return self.getLightBlock(a0, a1, a2);
    }

    public static IIcon getIcon(StairBlock self, int a0, int a1) {
        return self.getIcon(a0, a1);
    }

    public static void onRegistered(StairBlock self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static void registerBlockIcons(StairBlock self, IIconRegister a0) {
        self.registerBlockIcons(a0);
    }

    public static boolean isFlammable(TrapDoorBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.isFlammable(a0, a1, a2, a3, a4);
    }

    public static boolean onBlockActivated(TrapDoorBlock self, World a0, int a1, int a2, int a3, EntityPlayer a4, int a5, float a6, float a7, float a8) {
        return self.onBlockActivated(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static int getFlammability(TrapDoorBlock self, BlockState a0, IBlockAccess a1, BlockPos a2, Direction a3) {
        return self.getFlammability(a0, a1, a2, a3);
    }

    public static int getFlammability(TrapDoorBlock self, IBlockAccess a0, int a1, int a2, int a3, ForgeDirection a4) {
        return self.getFlammability(a0, a1, a2, a3, a4);
    }

    public static IIcon getIcon(TrapDoorBlock self, int a0, int a1) {
        return self.getIcon(a0, a1);
    }

    public static void onRegistered(TrapDoorBlock self, ResourceLocation a0) {
        self.onRegistered(a0);
    }

    public static void registerBlockIcons(TrapDoorBlock self, IIconRegister a0) {
        self.registerBlockIcons(a0);
    }

    public static <CT extends TileEntity> CT create(BlockEntitySupplier<CT> self, BlockPos a0, BlockState a1) {
        return self.create(a0, a1);
    }

    public static <CT extends TileEntity> CT create(BlockEntityType<CT> self, BlockPos a0, BlockState a1) {
        return self.create(a0, a1);
    }

    public static <CT extends TileEntity> boolean isValid(BlockEntityType<CT> self, BlockState a0) {
        return self.isValid(a0);
    }

    public static <CT extends TileEntity> Class<? extends TileEntity> tileClass(BlockEntityType<CT> self) {
        return self.tileClass();
    }

    public static <CT extends TileEntity> Set<Block> getValidBlocks(BlockEntityType<CT> self) {
        return self.getValidBlocks();
    }

    public static <CT extends TileEntity> ResourceLocation getId(BlockEntityType<CT> self) {
        return self.getId();
    }

    public static <CT extends TileEntity> void setRegistryName(BlockEntityType<CT> self, ResourceLocation a0) {
        self.setRegistryName(a0);
    }

    public static <CT extends TileEntity> BlockEntityType<CT> build(net.mcreator.boh.compat.mc.world.level.block.entity.Builder<CT> self, Object a0) {
        return self.build(a0);
    }

    public static <T> LazyOptional<T> getCapability(RandomizableContainerBlockEntity self, Capability<T> a0) {
        return self.getCapability(a0);
    }

    public static <T> LazyOptional<T> getCapability(RandomizableContainerBlockEntity self, Capability<T> a0, Direction a1) {
        return self.getCapability(a0, a1);
    }

    public static int getContainerSize(RandomizableContainerBlockEntity self) {
        return self.getContainerSize();
    }

    public static boolean canPlaceItem(RandomizableContainerBlockEntity self, int a0, ItemStack a1) {
        return self.canPlaceItem(a0, a1);
    }

    public static boolean hasCustomInventoryName(RandomizableContainerBlockEntity self) {
        return self.hasCustomInventoryName();
    }

    public static boolean hasLevel(RandomizableContainerBlockEntity self) {
        return self.hasLevel();
    }

    public static boolean isEmpty(RandomizableContainerBlockEntity self) {
        return self.isEmpty();
    }

    public static boolean isItemValidForSlot(RandomizableContainerBlockEntity self, int a0, ItemStack a1) {
        return self.isItemValidForSlot(a0, a1);
    }

    public static boolean isRemoved(RandomizableContainerBlockEntity self) {
        return self.isRemoved();
    }

    public static boolean isUseableByPlayer(RandomizableContainerBlockEntity self, EntityPlayer a0) {
        return self.isUseableByPlayer(a0);
    }

    public static boolean stillValid(RandomizableContainerBlockEntity self, EntityPlayer a0) {
        return self.stillValid(a0);
    }

    public static boolean tryLoadLootTable(RandomizableContainerBlockEntity self, NBTTagCompound a0) {
        return self.tryLoadLootTable(a0);
    }

    public static boolean trySaveLootTable(RandomizableContainerBlockEntity self, NBTTagCompound a0) {
        return self.trySaveLootTable(a0);
    }

    public static int getInventoryStackLimit(RandomizableContainerBlockEntity self) {
        return self.getInventoryStackLimit();
    }

    public static int getMaxStackSize(RandomizableContainerBlockEntity self) {
        return self.getMaxStackSize();
    }

    public static int getSizeInventory(RandomizableContainerBlockEntity self) {
        return self.getSizeInventory();
    }

    public static Object getUpdatePacket(RandomizableContainerBlockEntity self) {
        return self.getUpdatePacket();
    }

    public static String getInventoryName(RandomizableContainerBlockEntity self) {
        return self.getInventoryName();
    }

    public static BlockPos getBlockPos(RandomizableContainerBlockEntity self) {
        return self.getBlockPos();
    }

    public static BlockPos worldPosition(RandomizableContainerBlockEntity self) {
        return self.worldPosition();
    }

    public static Component getDisplayName(RandomizableContainerBlockEntity self) {
        return self.getDisplayName();
    }

    public static Component getName(RandomizableContainerBlockEntity self) {
        return self.getName();
    }

    public static BlockEntityType<?> getType(RandomizableContainerBlockEntity self) {
        return self.getType();
    }

    public static BlockState getBlockState(RandomizableContainerBlockEntity self) {
        return self.getBlockState();
    }

    public static Container createMenu(RandomizableContainerBlockEntity self, int a0, InventoryPlayer a1, EntityPlayer a2) {
        return self.createMenu(a0, a1, a2);
    }

    public static ItemStack decrStackSize(RandomizableContainerBlockEntity self, int a0, int a1) {
        return self.decrStackSize(a0, a1);
    }

    public static ItemStack getItem(RandomizableContainerBlockEntity self, int a0) {
        return self.getItem(a0);
    }

    public static ItemStack getStackInSlot(RandomizableContainerBlockEntity self, int a0) {
        return self.getStackInSlot(a0);
    }

    public static ItemStack getStackInSlotOnClosing(RandomizableContainerBlockEntity self, int a0) {
        return self.getStackInSlotOnClosing(a0);
    }

    public static ItemStack removeItem(RandomizableContainerBlockEntity self, int a0, int a1) {
        return self.removeItem(a0, a1);
    }

    public static NBTTagCompound getPersistentData(RandomizableContainerBlockEntity self) {
        return self.getPersistentData();
    }

    public static NBTTagCompound getUpdateTag(RandomizableContainerBlockEntity self) {
        return self.getUpdateTag();
    }

    public static NBTTagCompound saveWithFullMetadata(RandomizableContainerBlockEntity self) {
        return self.saveWithFullMetadata();
    }

    public static NBTTagCompound saveWithoutMetadata(RandomizableContainerBlockEntity self) {
        return self.saveWithoutMetadata();
    }

    public static Packet getDescriptionPacket(RandomizableContainerBlockEntity self) {
        return self.getDescriptionPacket();
    }

    public static World getLevel(RandomizableContainerBlockEntity self) {
        return self.getLevel();
    }

    public static void clearContent(RandomizableContainerBlockEntity self) {
        self.clearContent();
    }

    public static void clearRemoved(RandomizableContainerBlockEntity self) {
        self.clearRemoved();
    }

    public static void closeInventory(RandomizableContainerBlockEntity self) {
        self.closeInventory();
    }

    public static void invalidate(RandomizableContainerBlockEntity self) {
        self.invalidate();
    }

    public static void load(RandomizableContainerBlockEntity self, NBTTagCompound a0) {
        self.load(a0);
    }

    public static void onDataPacket(RandomizableContainerBlockEntity self, NetworkManager a0, S35PacketUpdateTileEntity a1) {
        self.onDataPacket(a0, a1);
    }

    public static void openInventory(RandomizableContainerBlockEntity self) {
        self.openInventory();
    }

    public static void readFromNBT(RandomizableContainerBlockEntity self, NBTTagCompound a0) {
        self.readFromNBT(a0);
    }

    public static void setChanged(RandomizableContainerBlockEntity self) {
        self.setChanged();
    }

    public static void setInventorySlotContents(RandomizableContainerBlockEntity self, int a0, ItemStack a1) {
        self.setInventorySlotContents(a0, a1);
    }

    public static void setItem(RandomizableContainerBlockEntity self, int a0, ItemStack a1) {
        self.setItem(a0, a1);
    }

    public static void setLootTable(RandomizableContainerBlockEntity self, ResourceLocation a0, long a1) {
        self.setLootTable(a0, a1);
    }

    public static void setRemoved(RandomizableContainerBlockEntity self) {
        self.setRemoved();
    }

    public static void unpackLootTable(RandomizableContainerBlockEntity self, EntityPlayer a0) {
        self.unpackLootTable(a0);
    }

    public static void validate(RandomizableContainerBlockEntity self) {
        self.validate();
    }

    public static void writeToNBT(RandomizableContainerBlockEntity self, NBTTagCompound a0) {
        self.writeToNBT(a0);
    }

    public static boolean growTree(AbstractTreeGrower self, World a0, Object a1, BlockPos a2, BlockState a3, RandomSource a4) {
        return self.growTree(a0, a1, a2, a3, a4);
    }

    public static StateDefinition getStateDefinition(BlockState.HasStateDefinition self) {
        return self.getStateDefinition();
    }

    public static <T extends Comparable<T>> T getValue(BlockState self, Property<T> a0) {
        return self.getValue(a0);
    }

    public static <T extends Comparable<T>> Optional<T> getOptionalValue(BlockState self, Property<T> a0) {
        return self.getOptionalValue(a0);
    }

    public static <T extends Comparable<T>> BlockState cycle(BlockState self, Property<T> a0) {
        return self.cycle(a0);
    }

    public static boolean blocksMotion(BlockState self) {
        return self.blocksMotion();
    }

    public static boolean hasProperty(BlockState self, Property<?> a0) {
        return self.hasProperty(a0);
    }

    public static boolean ignitedByLava(BlockState self) {
        return self.ignitedByLava();
    }

    public static boolean isCollisionShapeFullBlock(BlockState self, Object a0, Object a1) {
        return self.isCollisionShapeFullBlock(a0, a1);
    }

    public static int ext(BlockState self) {
        return self.ext();
    }

    public static int meta(BlockState self) {
        return self.meta();
    }

    public static Collection<Property<?>> getProperties(BlockState self) {
        return self.getProperties();
    }

    public static StateDefinition definition(BlockState self) {
        return self.definition();
    }

    public static <CO, CS> net.mcreator.boh.compat.mc.world.level.block.state.Builder<CO, CS> add(
        net.mcreator.boh.compat.mc.world.level.block.state.Builder<CO, CS> self, Property<?>... a0
    ) {
        return self.add(a0);
    }

    public static <CO, CS> StateDefinition create(net.mcreator.boh.compat.mc.world.level.block.state.Builder<CO, CS> self) {
        return self.create();
    }

    public static int getExt(ExtendedStateStore self, int a0, int a1, int a2) {
        return self.getExt(a0, a1, a2);
    }

    public static void readFromNBT(ExtendedStateStore self, NBTTagCompound a0) {
        self.readFromNBT(a0);
    }

    public static void setExt(ExtendedStateStore self, int a0, int a1, int a2, int a3) {
        self.setExt(a0, a1, a2, a3);
    }

    public static void writeToNBT(ExtendedStateStore self, NBTTagCompound a0) {
        self.writeToNBT(a0);
    }

    public static boolean test(net.mcreator.boh.compat.mc.world.level.block.state.Properties.StatePredicate self, BlockState a0, Object a1, Object a2) {
        return self.test(a0, a1, a2);
    }

    public static boolean air(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.air;
    }

    public static void set_air(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.air = v;
    }

    public static boolean dynamicShape(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.dynamicShape;
    }

    public static void set_dynamicShape(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.dynamicShape = v;
    }

    public static boolean emissive(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.emissive;
    }

    public static void set_emissive(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.emissive = v;
    }

    public static boolean forceSolidOn(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.forceSolidOn;
    }

    public static void set_forceSolidOn(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.forceSolidOn = v;
    }

    public static float friction(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.friction;
    }

    public static void set_friction(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float v) {
        self.friction = v;
    }

    public static float hardness(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.hardness;
    }

    public static void set_hardness(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float v) {
        self.hardness = v;
    }

    public static boolean ignitedByLava(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.ignitedByLava;
    }

    public static void set_ignitedByLava(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.ignitedByLava = v;
    }

    public static boolean instabreak(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.instabreak;
    }

    public static void set_instabreak(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.instabreak = v;
    }

    public static float jumpFactor(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.jumpFactor;
    }

    public static void set_jumpFactor(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float v) {
        self.jumpFactor = v;
    }

    public static ToIntFunction<BlockState> lightLevel(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.lightLevel;
    }

    public static void set_lightLevel(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, ToIntFunction<BlockState> v) {
        self.lightLevel = v;
    }

    public static boolean liquid(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.liquid;
    }

    public static void set_liquid(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.liquid = v;
    }

    public static MapColor mapColor(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.mapColor;
    }

    public static void set_mapColor(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, MapColor v) {
        self.mapColor = v;
    }

    public static boolean noCollission(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.noCollission;
    }

    public static void set_noCollission(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.noCollission = v;
    }

    public static boolean noLootTable(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.noLootTable;
    }

    public static void set_noLootTable(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.noLootTable = v;
    }

    public static boolean noOcclusion(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.noOcclusion;
    }

    public static void set_noOcclusion(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.noOcclusion = v;
    }

    public static OffsetType offsetType(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.offsetType;
    }

    public static void set_offsetType(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, OffsetType v) {
        self.offsetType = v;
    }

    public static PushReaction pushReaction(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.pushReaction;
    }

    public static void set_pushReaction(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, PushReaction v) {
        self.pushReaction = v;
    }

    public static boolean randomTicks(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.randomTicks;
    }

    public static void set_randomTicks(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.randomTicks = v;
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties.StatePredicate redstoneConductor(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self
    ) {
        return self.redstoneConductor;
    }

    public static void set_redstoneConductor(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, net.mcreator.boh.compat.mc.world.level.block.state.Properties.StatePredicate v
    ) {
        self.redstoneConductor = v;
    }

    public static boolean replaceable(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.replaceable;
    }

    public static void set_replaceable(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.replaceable = v;
    }

    public static boolean requiresCorrectTool(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.requiresCorrectTool;
    }

    public static void set_requiresCorrectTool(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean v) {
        self.requiresCorrectTool = v;
    }

    public static float resistance(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.resistance;
    }

    public static void set_resistance(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float v) {
        self.resistance = v;
    }

    public static SoundType sound(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.sound;
    }

    public static void set_sound(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, SoundType v) {
        self.sound = v;
    }

    public static float speedFactor(net.mcreator.boh.compat.mc.world.level.block.state.Properties self) {
        return self.speedFactor;
    }

    public static void set_speedFactor(net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float v) {
        self.speedFactor = v;
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties destroyTime(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float a0
    ) {
        return self.destroyTime(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties emissiveRendering(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, net.mcreator.boh.compat.mc.world.level.block.state.Properties.StatePredicate a0
    ) {
        return self.emissiveRendering(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties explosionResistance(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float a0
    ) {
        return self.explosionResistance(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties forceSolidOff(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self
    ) {
        return self.forceSolidOff();
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties friction(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float a0
    ) {
        return self.friction(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties hasPostProcess(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, net.mcreator.boh.compat.mc.world.level.block.state.Properties.StatePredicate a0
    ) {
        return self.hasPostProcess(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties instrument(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, NoteBlockInstrument a0
    ) {
        return self.instrument(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties isRedstoneConductor(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, net.mcreator.boh.compat.mc.world.level.block.state.Properties.StatePredicate a0
    ) {
        return self.isRedstoneConductor(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties isSuffocating(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, net.mcreator.boh.compat.mc.world.level.block.state.Properties.StatePredicate a0
    ) {
        return self.isSuffocating(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties isValidSpawn(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, Object a0
    ) {
        return self.isValidSpawn(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties isViewBlocking(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, net.mcreator.boh.compat.mc.world.level.block.state.Properties.StatePredicate a0
    ) {
        return self.isViewBlocking(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties jumpFactor(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float a0
    ) {
        return self.jumpFactor(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties lightLevel(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, ToIntFunction<BlockState> a0
    ) {
        return self.lightLevel(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties mapColor(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, Object a0
    ) {
        return self.mapColor(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties mapColor(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, MapColor a0
    ) {
        return self.mapColor(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties noParticlesOnBreak(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self
    ) {
        return self.noParticlesOnBreak();
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties offsetType(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, OffsetType a0
    ) {
        return self.offsetType(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties pushReaction(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, PushReaction a0
    ) {
        return self.pushReaction(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties randomTicks(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, boolean a0
    ) {
        return self.randomTicks(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties requiresCorrectToolForDrops(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self
    ) {
        return self.requiresCorrectToolForDrops();
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties sound(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, SoundType a0
    ) {
        return self.sound(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties speedFactor(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float a0
    ) {
        return self.speedFactor(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties strength(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float a0
    ) {
        return self.strength(a0);
    }

    public static net.mcreator.boh.compat.mc.world.level.block.state.Properties strength(
        net.mcreator.boh.compat.mc.world.level.block.state.Properties self, float a0, float a1
    ) {
        return self.strength(a0, a1);
    }

    public static boolean has(StateDefinition self, Property<?> a0) {
        return self.has(a0);
    }

    public static boolean needsExtended(StateDefinition self) {
        return self.needsExtended();
    }

    public static int indexOf(StateDefinition self, Property<?> a0) {
        return self.indexOf(a0);
    }

    public static int[] decode(StateDefinition self, int a0, int a1) {
        return self.decode(a0, a1);
    }

    public static int[] encode(StateDefinition self, int[] a0) {
        return self.encode(a0);
    }

    public static Block getOwner(StateDefinition self) {
        return self.getOwner();
    }

    public static String name(BlockSetType self) {
        return self.name;
    }

    public static boolean wooden(BlockSetType self) {
        return self.wooden;
    }

    public static String getName(BooleanProperty self, Boolean a0) {
        return self.getName(a0);
    }

    public static <CT extends Enum<CT>> String getName(EnumProperty<CT> self, CT a0) {
        return self.getName(a0);
    }

    public static int indexOf(IntegerProperty self, Integer a0) {
        return self.indexOf(a0);
    }

    public static Integer byIndex(IntegerProperty self, int a0) {
        return self.byIndex(a0);
    }

    public static String getName(IntegerProperty self, Integer a0) {
        return self.getName(a0);
    }

    public static <CT extends Comparable<CT>> CT byIndex(Property<CT> self, int a0) {
        return self.byIndex(a0);
    }

    public static <CT extends Comparable<CT>> String getName(Property<CT> self, CT a0) {
        return self.getName(a0);
    }

    public static <CT extends Comparable<CT>> int indexOf(Property<CT> self, CT a0) {
        return self.indexOf(a0);
    }

    public static <CT extends Comparable<CT>> int size(Property<CT> self) {
        return self.size();
    }

    public static <CT extends Comparable<CT>> Class<CT> getValueClass(Property<CT> self) {
        return self.getValueClass();
    }

    public static <CT extends Comparable<CT>> Optional<CT> getValue(Property<CT> self, String a0) {
        return self.getValue(a0);
    }

    public static String name(WoodType self) {
        return self.name;
    }

    public static <CC> ResourceLocation getId(Feature<CC> self) {
        return self.getId();
    }

    public static <CC> void setRegistryName(Feature<CC> self, ResourceLocation a0) {
        self.setRegistryName(a0);
    }

    public static boolean ignores(BlockIgnoreProcessor self, String a0) {
        return self.ignores(a0);
    }

    public static Mirror getMirror(StructurePlaceSettings self) {
        return self.getMirror();
    }

    public static Rotation getRotation(StructurePlaceSettings self) {
        return self.getRotation();
    }

    public static StructurePlaceSettings addProcessor(StructurePlaceSettings self, StructureProcessor a0) {
        return self.addProcessor(a0);
    }

    public static StructurePlaceSettings setFinalizeEntities(StructurePlaceSettings self, boolean a0) {
        return self.setFinalizeEntities(a0);
    }

    public static StructurePlaceSettings setIgnoreEntities(StructurePlaceSettings self, boolean a0) {
        return self.setIgnoreEntities(a0);
    }

    public static StructurePlaceSettings setKnownShape(StructurePlaceSettings self, boolean a0) {
        return self.setKnownShape(a0);
    }

    public static StructurePlaceSettings setMirror(StructurePlaceSettings self, Mirror a0) {
        return self.setMirror(a0);
    }

    public static StructurePlaceSettings setRandom(StructurePlaceSettings self, Object a0) {
        return self.setRandom(a0);
    }

    public static StructurePlaceSettings setRotation(StructurePlaceSettings self, Rotation a0) {
        return self.setRotation(a0);
    }

    public static boolean ignores(StructureProcessor self, String a0) {
        return self.ignores(a0);
    }

    public static boolean isEmpty(StructureTemplate self) {
        return self.isEmpty();
    }

    public static boolean placeInWorld(StructureTemplate self, World a0, BlockPos a1, BlockPos a2, StructurePlaceSettings a3, Object a4, int a5) {
        return self.placeInWorld(a0, a1, a2, a3, a4, a5);
    }

    public static boolean placeInWorld(StructureTemplate self, World a0, BlockPos a1, BlockPos a2, StructurePlaceSettings a3, Random a4, int a5) {
        return self.placeInWorld(a0, a1, a2, a3, a4, a5);
    }

    public static Vec3i getSize(StructureTemplate self) {
        return self.getSize();
    }

    public static Vec3i getSize(StructureTemplate self, Rotation a0) {
        return self.getSize(a0);
    }

    public static Optional<StructureTemplate> get(StructureTemplateManager self, ResourceLocation a0) {
        return self.get(a0);
    }

    public static StructureTemplate getOrCreate(StructureTemplateManager self, ResourceLocation a0) {
        return self.getOrCreate(a0);
    }

    public static boolean isSame(Fluid self, Fluid a0) {
        return self.isSame(a0);
    }

    public static int getTickDelay(Fluid self, Object a0) {
        return self.getTickDelay(a0);
    }

    public static FluidState defaultFluidState(Fluid self) {
        return self.defaultFluidState();
    }

    public static FluidState getSource(Fluid self, boolean a0) {
        return self.getSource(a0);
    }

    public static boolean is(FluidState self, Fluid a0) {
        return self.is(a0);
    }

    public static int getAmount(FluidState self) {
        return self.getAmount();
    }

    public static BlockState createLegacyBlock(FluidState self) {
        return self.createLegacyBlock();
    }

    public static boolean canReach(Path self) {
        return self.canReach();
    }

    public static boolean isDone(Path self) {
        return self.isDone();
    }

    public static int getNodeCount(Path self) {
        return self.getNodeCount();
    }

    public static BlockPos getEndNodePos(Path self) {
        return self.getEndNodePos();
    }

    public static BlockPos getTarget(Path self) {
        return self.getTarget();
    }

    public static PathEntity toVanilla(Path self) {
        return self.toVanilla();
    }

    public static void readFromNBT(DataHolder self, NBTTagCompound a0) {
        self.readFromNBT(a0);
    }

    public static void writeToNBT(DataHolder self, NBTTagCompound a0) {
        self.writeToNBT(a0);
    }

    public static <T extends SavedData> T computeIfAbsent(DimensionDataStorage self, Function<NBTTagCompound, T> a0, Supplier<T> a1, String a2) {
        return self.computeIfAbsent(a0, a1, a2);
    }

    public static <T extends SavedData> T get(DimensionDataStorage self, Function<NBTTagCompound, T> a0, String a1) {
        return self.get(a0, a1);
    }

    public static NBTTagCompound save(SavedData self, NBTTagCompound a0) {
        return self.save(a0);
    }

    public static boolean isDirty(SavedData self) {
        return self.isDirty();
    }

    public static void setDirty(SavedData self) {
        self.setDirty();
    }

    public static void setDirty(SavedData self, boolean a0) {
        self.setDirty(a0);
    }

    public static double maxX(AABB self) {
        return self.maxX;
    }

    public static double maxY(AABB self) {
        return self.maxY;
    }

    public static double maxZ(AABB self) {
        return self.maxZ;
    }

    public static double minX(AABB self) {
        return self.minX;
    }

    public static double minY(AABB self) {
        return self.minY;
    }

    public static double minZ(AABB self) {
        return self.minZ;
    }

    public static boolean intersects(AABB self, double a0, double a1, double a2, double a3, double a4, double a5) {
        return self.intersects(a0, a1, a2, a3, a4, a5);
    }

    public static boolean intersects(AABB self, AABB a0) {
        return self.intersects(a0);
    }

    public static double getSize(AABB self) {
        return self.getSize();
    }

    public static double getXsize(AABB self) {
        return self.getXsize();
    }

    public static double getYsize(AABB self) {
        return self.getYsize();
    }

    public static double getZsize(AABB self) {
        return self.getZsize();
    }

    public static AABB contract(AABB self, double a0, double a1, double a2) {
        return self.contract(a0, a1, a2);
    }

    public static AABB deflate(AABB self, double a0) {
        return self.deflate(a0);
    }

    public static AABB expandTowards(AABB self, double a0, double a1, double a2) {
        return self.expandTowards(a0, a1, a2);
    }

    public static AABB expandTowards(AABB self, Vec3 a0) {
        return self.expandTowards(a0);
    }

    public static AABB inflate(AABB self, double a0) {
        return self.inflate(a0);
    }

    public static AABB inflate(AABB self, double a0, double a1, double a2) {
        return self.inflate(a0, a1, a2);
    }

    public static AABB minmax(AABB self, AABB a0) {
        return self.minmax(a0);
    }

    public static AABB move(AABB self, BlockPos a0) {
        return self.move(a0);
    }

    public static AABB move(AABB self, Vec3 a0) {
        return self.move(a0);
    }

    public static Vec3 getCenter(AABB self) {
        return self.getCenter();
    }

    public static AxisAlignedBB toVanilla(AABB self) {
        return self.toVanilla();
    }

    public static boolean isInside(BlockHitResult self) {
        return self.isInside();
    }

    public static BlockPos getBlockPos(BlockHitResult self) {
        return self.getBlockPos();
    }

    public static Direction getDirection(BlockHitResult self) {
        return self.getDirection();
    }

    public static HitResultType getType(BlockHitResult self) {
        return self.getType();
    }

    public static HitResultType getType(EntityHitResult self) {
        return self.getType();
    }

    public static Entity getEntity(EntityHitResult self) {
        return self.getEntity();
    }

    public static HitResultType getType(HitResult self) {
        return self.getType();
    }

    public static double distanceTo(HitResult self, Entity a0) {
        return self.distanceTo(a0);
    }

    public static Vec3 getLocation(HitResult self) {
        return self.getLocation();
    }

    public static float x(Vec2 self) {
        return self.x;
    }

    public static float y(Vec2 self) {
        return self.y;
    }

    public static Vec2 add(Vec2 self, Vec2 a0) {
        return self.add(a0);
    }

    public static Vec2 scale(Vec2 self, float a0) {
        return self.scale(a0);
    }

    public static double x(Vec3 self) {
        return self.x;
    }

    public static double y(Vec3 self) {
        return self.y;
    }

    public static double z(Vec3 self) {
        return self.z;
    }

    public static boolean closerThan(Vec3 self, Vec3 a0, double a1) {
        return self.closerThan(a0, a1);
    }

    public static double distanceTo(Vec3 self, Vec3 a0) {
        return self.distanceTo(a0);
    }

    public static double dot(Vec3 self, Vec3 a0) {
        return self.dot(a0);
    }

    public static double get(Vec3 self, net.mcreator.boh.compat.mc.core.Axis a0) {
        return self.get(a0);
    }

    public static double horizontalDistance(Vec3 self) {
        return self.horizontalDistance();
    }

    public static double horizontalDistanceSqr(Vec3 self) {
        return self.horizontalDistanceSqr();
    }

    public static double lengthSqr(Vec3 self) {
        return self.lengthSqr();
    }

    public static Vec3 cross(Vec3 self, Vec3 a0) {
        return self.cross(a0);
    }

    public static Vec3 lerp(Vec3 self, Vec3 a0, double a1) {
        return self.lerp(a0, a1);
    }

    public static Vec3 normalize(Vec3 self) {
        return self.normalize();
    }

    public static Vec3 reverse(Vec3 self) {
        return self.reverse();
    }

    public static Vec3 scale(Vec3 self, double a0) {
        return self.scale(a0);
    }

    public static Vec3 subtract(Vec3 self, double a0, double a1, double a2) {
        return self.subtract(a0, a1, a2);
    }

    public static Vec3 subtract(Vec3 self, Vec3 a0) {
        return self.subtract(a0);
    }

    public static Vec3 vectorTo(Vec3 self, Vec3 a0) {
        return self.vectorTo(a0);
    }

    public static Vec3 with(Vec3 self, net.mcreator.boh.compat.mc.core.Axis a0, double a1) {
        return self.with(a0, a1);
    }

    public static Vec3 xRot(Vec3 self, float a0) {
        return self.xRot(a0);
    }

    public static Vec3 yRot(Vec3 self, float a0) {
        return self.yRot(a0);
    }

    public static Vec3 zRot(Vec3 self, float a0) {
        return self.zRot(a0);
    }

    public static net.minecraft.util.Vec3 toVanilla(Vec3 self) {
        return self.toVanilla();
    }

    public static boolean isDescending(CollisionContext self) {
        return self.isDescending();
    }

    public static Entity getEntity(CollisionContext self) {
        return self.getEntity();
    }

    public static boolean isEmpty(VoxelShape self) {
        return self.isEmpty();
    }

    public static boolean isFullBlock(VoxelShape self) {
        return self.isFullBlock();
    }

    public static double max(VoxelShape self, net.mcreator.boh.compat.mc.core.Axis a0) {
        return self.max(a0);
    }

    public static double min(VoxelShape self, net.mcreator.boh.compat.mc.core.Axis a0) {
        return self.min(a0);
    }

    public static List<AABB> toAabbs(VoxelShape self) {
        return self.toAabbs();
    }

    public static AABB bounds(VoxelShape self) {
        return self.bounds();
    }

    public static VoxelShape move(VoxelShape self, double a0, double a1, double a2) {
        return self.move(a0, a1, a2);
    }

    public static VoxelShape optimize(VoxelShape self) {
        return self.optimize();
    }

    public static int value(DestFactor self) {
        return self.value;
    }

    public static int value(SourceFactor self) {
        return self.value;
    }

    public static boolean building(BufferBuilder self) {
        return self.building();
    }

    public static VertexConsumer color(BufferBuilder self, float a0, float a1, float a2, float a3) {
        return self.color(a0, a1, a2, a3);
    }

    public static VertexConsumer normal(BufferBuilder self, float a0, float a1, float a2) {
        return self.normal(a0, a1, a2);
    }

    public static VertexConsumer overlayCoords(BufferBuilder self, int a0) {
        return self.overlayCoords(a0);
    }

    public static VertexConsumer uv(BufferBuilder self, float a0, float a1) {
        return self.uv(a0, a1);
    }

    public static VertexConsumer uv2(BufferBuilder self, int a0) {
        return self.uv2(a0);
    }

    public static VertexConsumer vertex(BufferBuilder self, double a0, double a1, double a2) {
        return self.vertex(a0, a1, a2);
    }

    public static RenderedBuffer end(BufferBuilder self) {
        return self.end();
    }

    public static RenderedBuffer endOrDiscardIfEmpty(BufferBuilder self) {
        return self.endOrDiscardIfEmpty();
    }

    public static void begin(BufferBuilder self, Mode a0, DefaultVertexFormat a1) {
        self.begin(a0, a1);
    }

    public static void endVertex(BufferBuilder self) {
        self.endVertex();
    }

    public static int gl(Mode self) {
        return self.gl;
    }

    public static void release(RenderedBuffer self) {
        self.release();
    }

    public static BufferBuilder getBuilder(Tesselator self) {
        return self.getBuilder();
    }

    public static void end(Tesselator self) {
        self.end();
    }

    public static void bind(VertexBuffer self) {
        self.bind();
    }

    public static void close(VertexBuffer self) {
        self.close();
    }

    public static void draw(VertexBuffer self) {
        self.draw();
    }

    public static void drawWithShader(VertexBuffer self, Matrix4f a0, Matrix4f a1, ShaderInstance a2) {
        self.drawWithShader(a0, a1, a2);
    }

    public static void upload(VertexBuffer self, RenderedBuffer a0) {
        self.upload(a0);
    }

    public static CommandSourceStack getSource(LiteralArgumentBuilder.CommandArguments self) {
        return self.getSource();
    }

    public static int run(LiteralArgumentBuilder.Executor self, LiteralArgumentBuilder.CommandArguments a0) throws Exception {
        return self.run(a0);
    }

    public static LiteralArgumentBuilder.Executor executor(LiteralArgumentBuilder self) {
        return self.executor;
    }

    public static void set_executor(LiteralArgumentBuilder self, LiteralArgumentBuilder.Executor v) {
        self.executor = v;
    }

    public static String name(LiteralArgumentBuilder self) {
        return self.name;
    }

    public static Predicate<CommandSourceStack> requirement(LiteralArgumentBuilder self) {
        return self.requirement;
    }

    public static void set_requirement(LiteralArgumentBuilder self, Predicate<CommandSourceStack> v) {
        self.requirement = v;
    }

    public static LiteralArgumentBuilder executes(LiteralArgumentBuilder self, LiteralArgumentBuilder.Executor a0) {
        return self.executes(a0);
    }

    public static LiteralArgumentBuilder requires(LiteralArgumentBuilder self, Predicate<CommandSourceStack> a0) {
        return self.requires(a0);
    }

    public static LiteralArgumentBuilder then(LiteralArgumentBuilder self, Object a0) {
        return self.then(a0);
    }

    public static float x(Vector3f self) {
        return self.x;
    }

    public static void set_x(Vector3f self, float v) {
        self.x = v;
    }

    public static float y(Vector3f self) {
        return self.y;
    }

    public static void set_y(Vector3f self, float v) {
        self.y = v;
    }

    public static float z(Vector3f self) {
        return self.z;
    }

    public static void set_z(Vector3f self, float v) {
        self.z = v;
    }

    public static Vector3f lerp(Vector3f self, Vector3f a0, float a1) {
        return self.lerp(a0, a1);
    }

    public static Vector3f mul(Vector3f self, float a0) {
        return self.mul(a0);
    }

    public static Vector3f set(Vector3f self, float a0, float a1, float a2) {
        return self.set(a0, a1, a2);
    }

    public static <T extends GeoAnimatable> AnimatableManager<T> getManagerForId(AnimatableInstanceCache self, long a0) {
        return self.getManagerForId(a0);
    }

    public static AnimatableManager.ControllerRegistrar add(AnimatableManager.ControllerRegistrar self, AnimationController<?> a0) {
        return self.add(a0);
    }

    public static AnimatableManager.ControllerRegistrar add(AnimatableManager.ControllerRegistrar self, AnimationController<?>... a0) {
        return self.add(a0);
    }

    public static AnimatableManager.ControllerRegistrar remove(AnimatableManager.ControllerRegistrar self, String a0) {
        return self.remove(a0);
    }

    public static <CT extends GeoAnimatable, D> D getData(AnimatableManager<CT> self, DataTicket<D> a0) {
        return self.getData(a0);
    }

    public static <CT extends GeoAnimatable, D> void setData(AnimatableManager<CT> self, DataTicket<D> a0, D a1) {
        self.setData(a0, a1);
    }

    public static <CT extends GeoAnimatable> double getFirstTickTime(AnimatableManager<CT> self) {
        return self.getFirstTickTime();
    }

    public static <CT extends GeoAnimatable> double getLastUpdateTime(AnimatableManager<CT> self) {
        return self.getLastUpdateTime();
    }

    public static <CT extends GeoAnimatable> Map<String, AnimationController<CT>> getAnimationControllers(AnimatableManager<CT> self) {
        return self.getAnimationControllers();
    }

    public static <CT extends GeoAnimatable> Map<String, BoneSnapshot> getBoneSnapshotCollection(AnimatableManager<CT> self) {
        return self.getBoneSnapshotCollection();
    }

    public static <CT extends GeoAnimatable> void addController(AnimatableManager<CT> self, AnimationController<CT> a0) {
        self.addController(a0);
    }

    public static <CT extends GeoAnimatable> void clearSnapshotCache(AnimatableManager<CT> self) {
        self.clearSnapshotCache();
    }

    public static <CT extends GeoAnimatable> void removeController(AnimatableManager<CT> self, String a0) {
        self.removeController(a0);
    }

    public static <CT extends GeoAnimatable> void tryTriggerAnimation(AnimatableManager<CT> self, String a0) {
        self.tryTriggerAnimation(a0);
    }

    public static <CT extends GeoAnimatable> void tryTriggerAnimation(AnimatableManager<CT> self, String a0, String a1) {
        self.tryTriggerAnimation(a0, a1);
    }

    public static <CT extends GeoAnimatable> void updatedAt(AnimatableManager<CT> self, double a0) {
        self.updatedAt(a0);
    }

    public static boolean shouldPlayAgain(Animation.LoopType self, GeoAnimatable a0, AnimationController<?> a1, Animation a2) {
        return self.shouldPlayAgain(a0, a1, a2);
    }

    public static double length(Animation self) {
        return self.length();
    }

    public static Animation.LoopType loopType(Animation self) {
        return self.loopType();
    }

    public static BoneAnimation[] boneAnimations(Animation self) {
        return self.boneAnimations();
    }

    public static <CA extends GeoAnimatable> PlayState handle(AnimationController.AnimationStateHandler<CA> self, AnimationState<CA> a0) {
        return self.handle(a0);
    }

    public static <CT extends GeoAnimatable> boolean hasAnimationFinished(AnimationController<CT> self) {
        return self.hasAnimationFinished();
    }

    public static <CT extends GeoAnimatable> boolean isPlayingTriggeredAnimation(AnimationController<CT> self) {
        return self.isPlayingTriggeredAnimation();
    }

    public static <CT extends GeoAnimatable> boolean tryTriggerAnimation(AnimationController<CT> self, String a0) {
        return self.tryTriggerAnimation(a0);
    }

    public static <CT extends GeoAnimatable> double getAnimationSpeed(AnimationController<CT> self) {
        return self.getAnimationSpeed();
    }

    public static <CT extends GeoAnimatable> String getName(AnimationController<CT> self) {
        return self.getName();
    }

    public static <CT extends GeoAnimatable> AnimationController.AnimationStateHandler<CT> getStateHandler(AnimationController<CT> self) {
        return self.getStateHandler();
    }

    public static <CT extends GeoAnimatable> AnimationController.State getAnimationState(AnimationController<CT> self) {
        return self.getAnimationState();
    }

    public static <CT extends GeoAnimatable> AnimationController<CT> receiveTriggeredAnimations(AnimationController<CT> self) {
        return self.receiveTriggeredAnimations();
    }

    public static <CT extends GeoAnimatable> AnimationController<CT> setAnimationSpeed(AnimationController<CT> self, double a0) {
        return self.setAnimationSpeed(a0);
    }

    public static <CT extends GeoAnimatable> AnimationController<CT> setAnimationSpeedHandler(AnimationController<CT> self, Function<CT, Double> a0) {
        return self.setAnimationSpeedHandler(a0);
    }

    public static <CT extends GeoAnimatable> AnimationController<CT> setCustomInstructionKeyframeHandler(AnimationController<CT> self, Object a0) {
        return self.setCustomInstructionKeyframeHandler(a0);
    }

    public static <CT extends GeoAnimatable> AnimationController<CT> setOverrideEasingType(AnimationController<CT> self, EasingType a0) {
        return self.setOverrideEasingType(a0);
    }

    public static <CT extends GeoAnimatable> AnimationController<CT> setParticleKeyframeHandler(AnimationController<CT> self, Object a0) {
        return self.setParticleKeyframeHandler(a0);
    }

    public static <CT extends GeoAnimatable> AnimationController<CT> setSoundKeyframeHandler(AnimationController<CT> self, Object a0) {
        return self.setSoundKeyframeHandler(a0);
    }

    public static <CT extends GeoAnimatable> AnimationController<CT> transitionLength(AnimationController<CT> self, int a0) {
        return self.transitionLength(a0);
    }

    public static <CT extends GeoAnimatable> AnimationController<CT> triggerableAnim(AnimationController<CT> self, String a0, RawAnimation a1) {
        return self.triggerableAnim(a0, a1);
    }

    public static <CT extends GeoAnimatable> AnimationProcessor.QueuedAnimation getCurrentAnimation(AnimationController<CT> self) {
        return self.getCurrentAnimation();
    }

    public static <CT extends GeoAnimatable> RawAnimation getCurrentRawAnimation(AnimationController<CT> self) {
        return self.getCurrentRawAnimation();
    }

    public static <CT extends GeoAnimatable> void forceAnimationReset(AnimationController<CT> self) {
        self.forceAnimationReset();
    }

    public static <CT extends GeoAnimatable> void process(
        AnimationController<CT> self, GeoModel<CT> a0, AnimationState<CT> a1, Map<String, CoreGeoBone> a2, Map<String, BoneSnapshot> a3, double a4, boolean a5
    ) {
        self.process(a0, a1, a2, a3, a4, a5);
    }

    public static <CT extends GeoAnimatable> void setAnimation(AnimationController<CT> self, RawAnimation a0) {
        self.setAnimation(a0);
    }

    public static <CT extends GeoAnimatable> void setTransitionLength(AnimationController<CT> self, int a0) {
        self.setTransitionLength(a0);
    }

    public static <CT extends GeoAnimatable> void stop(AnimationController<CT> self) {
        self.stop();
    }

    public static Animation animation(AnimationProcessor.QueuedAnimation self) {
        return self.animation();
    }

    public static Animation.LoopType loopType(AnimationProcessor.QueuedAnimation self) {
        return self.loopType();
    }

    public static <CT extends GeoAnimatable> Collection<CoreGeoBone> getRegisteredBones(AnimationProcessor<CT> self) {
        return self.getRegisteredBones();
    }

    public static <CT extends GeoAnimatable> Queue<AnimationProcessor.QueuedAnimation> buildAnimationQueue(AnimationProcessor<CT> self, CT a0, RawAnimation a1) {
        return self.buildAnimationQueue(a0, a1);
    }

    public static <CT extends GeoAnimatable> CoreGeoBone getBone(AnimationProcessor<CT> self, String a0) {
        return self.getBone(a0);
    }

    public static <CT extends GeoAnimatable> void registerGeoBone(AnimationProcessor<CT> self, CoreGeoBone a0) {
        self.registerGeoBone(a0);
    }

    public static <CT extends GeoAnimatable> void setActiveModel(AnimationProcessor<CT> self, BakedGeoModel a0) {
        self.setActiveModel(a0);
    }

    public static <CT extends GeoAnimatable> void tickAnimation(
        AnimationProcessor<CT> self, CT a0, GeoModel<CT> a1, AnimatableManager<CT> a2, double a3, AnimationState<CT> a4, boolean a5
    ) {
        self.tickAnimation(a0, a1, a2, a3, a4, a5);
    }

    public static <CT extends GeoAnimatable, D> D getData(AnimationState<CT> self, DataTicket<D> a0) {
        return self.getData(a0);
    }

    public static <CT extends GeoAnimatable, D> void setData(AnimationState<CT> self, DataTicket<D> a0, D a1) {
        self.setData(a0, a1);
    }

    public static <CT extends GeoAnimatable> CT getAnimatable(AnimationState<CT> self) {
        return self.getAnimatable();
    }

    public static <CT extends GeoAnimatable> boolean isCurrentAnimation(AnimationState<CT> self, RawAnimation a0) {
        return self.isCurrentAnimation(a0);
    }

    public static <CT extends GeoAnimatable> boolean isCurrentAnimationStage(AnimationState<CT> self, String a0) {
        return self.isCurrentAnimationStage(a0);
    }

    public static <CT extends GeoAnimatable> boolean isMoving(AnimationState<CT> self) {
        return self.isMoving();
    }

    public static <CT extends GeoAnimatable> double getAnimationTick(AnimationState<CT> self) {
        return self.getAnimationTick();
    }

    public static <CT extends GeoAnimatable> float getLimbSwing(AnimationState<CT> self) {
        return self.getLimbSwing();
    }

    public static <CT extends GeoAnimatable> float getLimbSwingAmount(AnimationState<CT> self) {
        return self.getLimbSwingAmount();
    }

    public static <CT extends GeoAnimatable> float getPartialTick(AnimationState<CT> self) {
        return self.getPartialTick();
    }

    public static <CT extends GeoAnimatable> AnimationController<CT> getController(AnimationState<CT> self) {
        return self.getController();
    }

    public static <CT extends GeoAnimatable> AnimationState<CT> withController(AnimationState<CT> self, AnimationController<CT> a0) {
        return self.withController(a0);
    }

    public static <CT extends GeoAnimatable> PlayState setAndContinue(AnimationState<CT> self, RawAnimation a0) {
        return self.setAndContinue(a0);
    }

    public static <CT extends GeoAnimatable> void resetCurrentAnimation(AnimationState<CT> self) {
        self.resetCurrentAnimation();
    }

    public static <CT extends GeoAnimatable> void setAnimation(AnimationState<CT> self, RawAnimation a0) {
        self.setAnimation(a0);
    }

    public static <CT extends GeoAnimatable> void setControllerSpeed(AnimationState<CT> self, float a0) {
        self.setControllerSpeed(a0);
    }

    public static List<GeoBone> getBones(BakedGeoModel self) {
        return self.getBones();
    }

    public static List<GeoBone> topLevelBones(BakedGeoModel self) {
        return self.topLevelBones();
    }

    public static GeoBone getBone(BakedGeoModel self, String a0) {
        return self.getBone(a0);
    }

    public static String boneName(BoneAnimation self) {
        return self.boneName();
    }

    public static boolean isPosAnimInProgress(BoneSnapshot self) {
        return self.isPosAnimInProgress();
    }

    public static boolean isRotAnimInProgress(BoneSnapshot self) {
        return self.isRotAnimInProgress();
    }

    public static boolean isScaleAnimInProgress(BoneSnapshot self) {
        return self.isScaleAnimInProgress();
    }

    public static double getLastResetPositionTick(BoneSnapshot self) {
        return self.getLastResetPositionTick();
    }

    public static double getLastResetRotationTick(BoneSnapshot self) {
        return self.getLastResetRotationTick();
    }

    public static double getLastResetScaleTick(BoneSnapshot self) {
        return self.getLastResetScaleTick();
    }

    public static float getOffsetX(BoneSnapshot self) {
        return self.getOffsetX();
    }

    public static float getOffsetY(BoneSnapshot self) {
        return self.getOffsetY();
    }

    public static float getOffsetZ(BoneSnapshot self) {
        return self.getOffsetZ();
    }

    public static float getRotX(BoneSnapshot self) {
        return self.getRotX();
    }

    public static float getRotY(BoneSnapshot self) {
        return self.getRotY();
    }

    public static float getRotZ(BoneSnapshot self) {
        return self.getRotZ();
    }

    public static float getScaleX(BoneSnapshot self) {
        return self.getScaleX();
    }

    public static float getScaleY(BoneSnapshot self) {
        return self.getScaleY();
    }

    public static float getScaleZ(BoneSnapshot self) {
        return self.getScaleZ();
    }

    public static CoreGeoBone getBone(BoneSnapshot self) {
        return self.getBone();
    }

    public static void startPosAnim(BoneSnapshot self) {
        self.startPosAnim();
    }

    public static void startRotAnim(BoneSnapshot self) {
        self.startRotAnim();
    }

    public static void startScaleAnim(BoneSnapshot self) {
        self.startScaleAnim();
    }

    public static void stopPosAnim(BoneSnapshot self, double a0) {
        self.stopPosAnim(a0);
    }

    public static void stopRotAnim(BoneSnapshot self, double a0) {
        self.stopRotAnim(a0);
    }

    public static void stopScaleAnim(BoneSnapshot self, double a0) {
        self.stopScaleAnim(a0);
    }

    public static void updateOffset(BoneSnapshot self, float a0, float a1, float a2) {
        self.updateOffset(a0, a1, a2);
    }

    public static void updateRotation(BoneSnapshot self, float a0, float a1, float a2) {
        self.updateRotation(a0, a1, a2);
    }

    public static void updateScale(BoneSnapshot self, float a0, float a1, float a2) {
        self.updateScale(a0, a1, a2);
    }

    public static boolean hasPositionChanged(CoreGeoBone self) {
        return self.hasPositionChanged();
    }

    public static boolean hasRotationChanged(CoreGeoBone self) {
        return self.hasRotationChanged();
    }

    public static boolean hasScaleChanged(CoreGeoBone self) {
        return self.hasScaleChanged();
    }

    public static boolean isHidden(CoreGeoBone self) {
        return self.isHidden();
    }

    public static boolean isHidingChildren(CoreGeoBone self) {
        return self.isHidingChildren();
    }

    public static float getPivotX(CoreGeoBone self) {
        return self.getPivotX();
    }

    public static float getPivotY(CoreGeoBone self) {
        return self.getPivotY();
    }

    public static float getPivotZ(CoreGeoBone self) {
        return self.getPivotZ();
    }

    public static float getPosX(CoreGeoBone self) {
        return self.getPosX();
    }

    public static float getPosY(CoreGeoBone self) {
        return self.getPosY();
    }

    public static float getPosZ(CoreGeoBone self) {
        return self.getPosZ();
    }

    public static float getRotX(CoreGeoBone self) {
        return self.getRotX();
    }

    public static float getRotY(CoreGeoBone self) {
        return self.getRotY();
    }

    public static float getRotZ(CoreGeoBone self) {
        return self.getRotZ();
    }

    public static float getScaleX(CoreGeoBone self) {
        return self.getScaleX();
    }

    public static float getScaleY(CoreGeoBone self) {
        return self.getScaleY();
    }

    public static float getScaleZ(CoreGeoBone self) {
        return self.getScaleZ();
    }

    public static String getName(CoreGeoBone self) {
        return self.getName();
    }

    public static List<? extends CoreGeoBone> getChildBones(CoreGeoBone self) {
        return self.getChildBones();
    }

    public static BoneSnapshot getInitialSnapshot(CoreGeoBone self) {
        return self.getInitialSnapshot();
    }

    public static CoreGeoBone getParent(CoreGeoBone self) {
        return self.getParent();
    }

    public static void markPositionAsChanged(CoreGeoBone self) {
        self.markPositionAsChanged();
    }

    public static void markRotationAsChanged(CoreGeoBone self) {
        self.markRotationAsChanged();
    }

    public static void markScaleAsChanged(CoreGeoBone self) {
        self.markScaleAsChanged();
    }

    public static void resetStateChanges(CoreGeoBone self) {
        self.resetStateChanges();
    }

    public static void saveInitialSnapshot(CoreGeoBone self) {
        self.saveInitialSnapshot();
    }

    public static void setChildrenHidden(CoreGeoBone self, boolean a0) {
        self.setChildrenHidden(a0);
    }

    public static void setHidden(CoreGeoBone self, boolean a0) {
        self.setHidden(a0);
    }

    public static void setPivotX(CoreGeoBone self, float a0) {
        self.setPivotX(a0);
    }

    public static void setPivotY(CoreGeoBone self, float a0) {
        self.setPivotY(a0);
    }

    public static void setPivotZ(CoreGeoBone self, float a0) {
        self.setPivotZ(a0);
    }

    public static void setPosX(CoreGeoBone self, float a0) {
        self.setPosX(a0);
    }

    public static void setPosY(CoreGeoBone self, float a0) {
        self.setPosY(a0);
    }

    public static void setPosZ(CoreGeoBone self, float a0) {
        self.setPosZ(a0);
    }

    public static void setRotX(CoreGeoBone self, float a0) {
        self.setRotX(a0);
    }

    public static void setRotY(CoreGeoBone self, float a0) {
        self.setRotY(a0);
    }

    public static void setRotZ(CoreGeoBone self, float a0) {
        self.setRotZ(a0);
    }

    public static void setScaleX(CoreGeoBone self, float a0) {
        self.setScaleX(a0);
    }

    public static void setScaleY(CoreGeoBone self, float a0) {
        self.setScaleY(a0);
    }

    public static void setScaleZ(CoreGeoBone self, float a0) {
        self.setScaleZ(a0);
    }

    public static void updatePivot(CoreGeoBone self, float a0, float a1, float a2) {
        self.updatePivot(a0, a1, a2);
    }

    public static void updatePosition(CoreGeoBone self, float a0, float a1, float a2) {
        self.updatePosition(a0, a1, a2);
    }

    public static void updateRotation(CoreGeoBone self, float a0, float a1, float a2) {
        self.updateRotation(a0, a1, a2);
    }

    public static void updateScale(CoreGeoBone self, float a0, float a1, float a2) {
        self.updateScale(a0, a1, a2);
    }

    public static <CD> String id(DataTicket<CD> self) {
        return self.id();
    }

    public static DoubleUnaryOperator buildTransformer(EasingType.CatmullRomEasing self, Double a0) {
        return self.buildTransformer(a0);
    }

    public static DoubleUnaryOperator buildTransformer(EasingType self, Double a0) {
        return self.buildTransformer(a0);
    }

    public static boolean isChild(EntityModelData self) {
        return self.isChild();
    }

    public static boolean isSitting(EntityModelData self) {
        return self.isSitting();
    }

    public static float headPitch(EntityModelData self) {
        return self.headPitch();
    }

    public static float netHeadYaw(EntityModelData self) {
        return self.netHeadYaw();
    }

    public static double getTick(GeoAnimatable self, Object a0) {
        return self.getTick(a0);
    }

    public static AnimatableInstanceCache getAnimatableInstanceCache(GeoAnimatable self) {
        return self.getAnimatableInstanceCache();
    }

    public static void registerControllers(GeoAnimatable self, AnimatableManager.ControllerRegistrar a0) {
        self.registerControllers(a0);
    }

    public static boolean shouldPlayAnimsWhileGamePaused(GeoAnimatable self) {
        return self.shouldPlayAnimsWhileGamePaused();
    }

    public static double getBoneResetTime(GeoAnimatable self) {
        return self.getBoneResetTime();
    }

    public static <CT extends Item & GeoAnimatable> CT getAnimatable(GeoArmorRenderer<CT> self) {
        return self.getAnimatable();
    }

    public static <CT extends Item & GeoAnimatable> List<GeoRenderLayer<CT>> getRenderLayers(GeoArmorRenderer<CT> self) {
        return self.getRenderLayers();
    }

    public static <CT extends Item & GeoAnimatable> long getInstanceId(GeoArmorRenderer<CT> self, CT a0) {
        return self.getInstanceId(a0);
    }

    public static <CT extends Item & GeoAnimatable> GeoArmorRenderer<CT> addRenderLayer(GeoArmorRenderer<CT> self, GeoRenderLayer<CT> a0) {
        return self.addRenderLayer(a0);
    }

    public static <CT extends Item & GeoAnimatable> GeoBone getBodyBone(GeoArmorRenderer<CT> self) {
        return self.getBodyBone();
    }

    public static <CT extends Item & GeoAnimatable> GeoBone getHeadBone(GeoArmorRenderer<CT> self) {
        return self.getHeadBone();
    }

    public static <CT extends Item & GeoAnimatable> GeoBone getLeftArmBone(GeoArmorRenderer<CT> self) {
        return self.getLeftArmBone();
    }

    public static <CT extends Item & GeoAnimatable> GeoBone getLeftBootBone(GeoArmorRenderer<CT> self) {
        return self.getLeftBootBone();
    }

    public static <CT extends Item & GeoAnimatable> GeoBone getLeftLegBone(GeoArmorRenderer<CT> self) {
        return self.getLeftLegBone();
    }

    public static <CT extends Item & GeoAnimatable> GeoBone getRightArmBone(GeoArmorRenderer<CT> self) {
        return self.getRightArmBone();
    }

    public static <CT extends Item & GeoAnimatable> GeoBone getRightBootBone(GeoArmorRenderer<CT> self) {
        return self.getRightBootBone();
    }

    public static <CT extends Item & GeoAnimatable> GeoBone getRightLegBone(GeoArmorRenderer<CT> self) {
        return self.getRightLegBone();
    }

    public static <CT extends Item & GeoAnimatable> GeoModel<CT> getGeoModel(GeoArmorRenderer<CT> self) {
        return self.getGeoModel();
    }

    public static <CT extends Item & GeoAnimatable> ResourceLocation getTextureLocation(GeoArmorRenderer<CT> self, CT a0) {
        return self.getTextureLocation(a0);
    }

    public static <CT extends Item & GeoAnimatable> void actuallyRender(
        GeoArmorRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        RenderType a3,
        MultiBufferSource a4,
        VertexConsumer a5,
        boolean a6,
        float a7,
        int a8,
        int a9,
        float a10,
        float a11,
        float a12,
        float a13
    ) {
        self.actuallyRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13);
    }

    public static <CT extends Item & GeoAnimatable> void preRender(
        GeoArmorRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        MultiBufferSource a3,
        VertexConsumer a4,
        boolean a5,
        float a6,
        int a7,
        int a8,
        float a9,
        float a10,
        float a11,
        float a12
    ) {
        self.preRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
    }

    public static <CT extends Item & GeoAnimatable> void prepForRender(
        GeoArmorRenderer<CT> self, Entity a0, ItemStack a1, EquipmentSlot a2, HumanoidModel<?> a3
    ) {
        self.prepForRender(a0, a1, a2, a3);
    }

    public static <CT extends Item & GeoAnimatable> void render(
        GeoArmorRenderer<CT> self, Entity a0, float a1, float a2, float a3, float a4, float a5, float a6
    ) {
        self.render(a0, a1, a2, a3, a4, a5, a6);
    }

    public static double getTick(GeoBlockEntity self, Object a0) {
        return self.getTick(a0);
    }

    public static <CT extends TileEntity & GeoAnimatable> CT getAnimatable(GeoBlockRenderer<CT> self) {
        return self.getAnimatable();
    }

    public static <CT extends TileEntity & GeoAnimatable> List<GeoRenderLayer<CT>> getRenderLayers(GeoBlockRenderer<CT> self) {
        return self.getRenderLayers();
    }

    public static <CT extends TileEntity & GeoAnimatable> long getInstanceId(GeoBlockRenderer<CT> self, CT a0) {
        return self.getInstanceId(a0);
    }

    public static <CT extends TileEntity & GeoAnimatable> GeoBlockRenderer<CT> addRenderLayer(GeoBlockRenderer<CT> self, GeoRenderLayer<CT> a0) {
        return self.addRenderLayer(a0);
    }

    public static <CT extends TileEntity & GeoAnimatable> GeoModel<CT> getGeoModel(GeoBlockRenderer<CT> self) {
        return self.getGeoModel();
    }

    public static <CT extends TileEntity & GeoAnimatable> ResourceLocation getTextureLocation(GeoBlockRenderer<CT> self, CT a0) {
        return self.getTextureLocation(a0);
    }

    public static <CT extends TileEntity & GeoAnimatable> void actuallyRender(
        GeoBlockRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        RenderType a3,
        MultiBufferSource a4,
        VertexConsumer a5,
        boolean a6,
        float a7,
        int a8,
        int a9,
        float a10,
        float a11,
        float a12,
        float a13
    ) {
        self.actuallyRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13);
    }

    public static <CT extends TileEntity & GeoAnimatable> void preRender(
        GeoBlockRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        MultiBufferSource a3,
        VertexConsumer a4,
        boolean a5,
        float a6,
        int a7,
        int a8,
        float a9,
        float a10,
        float a11,
        float a12
    ) {
        self.preRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
    }

    public static <CT extends TileEntity & GeoAnimatable> void renderTileEntityAt(
        GeoBlockRenderer<CT> self, TileEntity a0, double a1, double a2, double a3, float a4
    ) {
        self.renderTileEntityAt(a0, a1, a2, a3, a4);
    }

    public static boolean hasPositionChanged(GeoBone self) {
        return self.hasPositionChanged();
    }

    public static boolean hasRotationChanged(GeoBone self) {
        return self.hasRotationChanged();
    }

    public static boolean hasScaleChanged(GeoBone self) {
        return self.hasScaleChanged();
    }

    public static boolean isHidden(GeoBone self) {
        return self.isHidden();
    }

    public static boolean isHidingChildren(GeoBone self) {
        return self.isHidingChildren();
    }

    public static float getPivotX(GeoBone self) {
        return self.getPivotX();
    }

    public static float getPivotY(GeoBone self) {
        return self.getPivotY();
    }

    public static float getPivotZ(GeoBone self) {
        return self.getPivotZ();
    }

    public static float getPosX(GeoBone self) {
        return self.getPosX();
    }

    public static float getPosY(GeoBone self) {
        return self.getPosY();
    }

    public static float getPosZ(GeoBone self) {
        return self.getPosZ();
    }

    public static float getRotX(GeoBone self) {
        return self.getRotX();
    }

    public static float getRotY(GeoBone self) {
        return self.getRotY();
    }

    public static float getRotZ(GeoBone self) {
        return self.getRotZ();
    }

    public static float getScaleX(GeoBone self) {
        return self.getScaleX();
    }

    public static float getScaleY(GeoBone self) {
        return self.getScaleY();
    }

    public static float getScaleZ(GeoBone self) {
        return self.getScaleZ();
    }

    public static Boolean getMirror(GeoBone self) {
        return self.getMirror();
    }

    public static Boolean getReset(GeoBone self) {
        return self.getReset();
    }

    public static Boolean shouldNeverRender(GeoBone self) {
        return self.shouldNeverRender();
    }

    public static Double getInflate(GeoBone self) {
        return self.getInflate();
    }

    public static String getName(GeoBone self) {
        return self.getName();
    }

    public static List<GeoBone> getChildBones(GeoBone self) {
        return self.getChildBones();
    }

    public static List<GeoCube> getCubes(GeoBone self) {
        return self.getCubes();
    }

    public static BoneSnapshot getInitialSnapshot(GeoBone self) {
        return self.getInitialSnapshot();
    }

    public static GeoBone getParent(GeoBone self) {
        return self.getParent();
    }

    public static void markPositionAsChanged(GeoBone self) {
        self.markPositionAsChanged();
    }

    public static void markRotationAsChanged(GeoBone self) {
        self.markRotationAsChanged();
    }

    public static void markScaleAsChanged(GeoBone self) {
        self.markScaleAsChanged();
    }

    public static void resetStateChanges(GeoBone self) {
        self.resetStateChanges();
    }

    public static void saveInitialSnapshot(GeoBone self) {
        self.saveInitialSnapshot();
    }

    public static void setChildrenHidden(GeoBone self, boolean a0) {
        self.setChildrenHidden(a0);
    }

    public static void setHidden(GeoBone self, boolean a0) {
        self.setHidden(a0);
    }

    public static void setPivotX(GeoBone self, float a0) {
        self.setPivotX(a0);
    }

    public static void setPivotY(GeoBone self, float a0) {
        self.setPivotY(a0);
    }

    public static void setPivotZ(GeoBone self, float a0) {
        self.setPivotZ(a0);
    }

    public static void setPosX(GeoBone self, float a0) {
        self.setPosX(a0);
    }

    public static void setPosY(GeoBone self, float a0) {
        self.setPosY(a0);
    }

    public static void setPosZ(GeoBone self, float a0) {
        self.setPosZ(a0);
    }

    public static void setRotX(GeoBone self, float a0) {
        self.setRotX(a0);
    }

    public static void setRotY(GeoBone self, float a0) {
        self.setRotY(a0);
    }

    public static void setRotZ(GeoBone self, float a0) {
        self.setRotZ(a0);
    }

    public static void setScaleX(GeoBone self, float a0) {
        self.setScaleX(a0);
    }

    public static void setScaleY(GeoBone self, float a0) {
        self.setScaleY(a0);
    }

    public static void setScaleZ(GeoBone self, float a0) {
        self.setScaleZ(a0);
    }

    public static double getTick(GeoEntity self, Object a0) {
        return self.getTick(a0);
    }

    public static <CT extends Entity & GeoAnimatable> CT getAnimatable(GeoEntityRenderer<CT> self) {
        return self.getAnimatable();
    }

    public static <CT extends Entity & GeoAnimatable> List<GeoRenderLayer<CT>> getRenderLayers(GeoEntityRenderer<CT> self) {
        return self.getRenderLayers();
    }

    public static <CT extends Entity & GeoAnimatable> long getInstanceId(GeoEntityRenderer<CT> self, CT a0) {
        return self.getInstanceId(a0);
    }

    public static <CT extends Entity & GeoAnimatable> RenderType getRenderType(
        GeoEntityRenderer<CT> self, CT a0, ResourceLocation a1, MultiBufferSource a2, float a3
    ) {
        return self.getRenderType(a0, a1, a2, a3);
    }

    public static <CT extends Entity & GeoAnimatable> GeoEntityRenderer<CT> addRenderLayer(GeoEntityRenderer<CT> self, GeoRenderLayer<CT> a0) {
        return self.addRenderLayer(a0);
    }

    public static <CT extends Entity & GeoAnimatable> GeoEntityRenderer<CT> withScale(GeoEntityRenderer<CT> self, float a0) {
        return self.withScale(a0);
    }

    public static <CT extends Entity & GeoAnimatable> GeoEntityRenderer<CT> withScale(GeoEntityRenderer<CT> self, float a0, float a1) {
        return self.withScale(a0, a1);
    }

    public static <CT extends Entity & GeoAnimatable> GeoModel<CT> getGeoModel(GeoEntityRenderer<CT> self) {
        return self.getGeoModel();
    }

    public static <CT extends Entity & GeoAnimatable> RenderManager getRenderManager(GeoEntityRenderer<CT> self) {
        return self.getRenderManager();
    }

    public static <CT extends Entity & GeoAnimatable> ResourceLocation getTextureLocation(GeoEntityRenderer<CT> self, CT a0) {
        return self.getTextureLocation(a0);
    }

    public static <CT extends Entity & GeoAnimatable> void actuallyRender(
        GeoEntityRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        RenderType a3,
        MultiBufferSource a4,
        VertexConsumer a5,
        boolean a6,
        float a7,
        int a8,
        int a9,
        float a10,
        float a11,
        float a12,
        float a13
    ) {
        self.actuallyRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13);
    }

    public static <CT extends Entity & GeoAnimatable> void doRender(GeoEntityRenderer<CT> self, Entity a0, double a1, double a2, double a3, float a4, float a5) {
        self.doRender(a0, a1, a2, a3, a4, a5);
    }

    public static <CT extends Entity & GeoAnimatable> void preRender(
        GeoEntityRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        MultiBufferSource a3,
        VertexConsumer a4,
        boolean a5,
        float a6,
        int a7,
        int a8,
        float a9,
        float a10,
        float a11,
        float a12
    ) {
        self.preRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
    }

    public static double getTick(GeoItem self, Object a0) {
        return self.getTick(a0);
    }

    public static <CT extends Item & GeoAnimatable> CT getAnimatable(GeoItemRenderer<CT> self) {
        return self.getAnimatable();
    }

    public static <CT extends Item & GeoAnimatable> List<GeoRenderLayer<CT>> getRenderLayers(GeoItemRenderer<CT> self) {
        return self.getRenderLayers();
    }

    public static <CT extends Item & GeoAnimatable> long getInstanceId(GeoItemRenderer<CT> self, CT a0) {
        return self.getInstanceId(a0);
    }

    public static <CT extends Item & GeoAnimatable> GeoItemRenderer<CT> addRenderLayer(GeoItemRenderer<CT> self, GeoRenderLayer<CT> a0) {
        return self.addRenderLayer(a0);
    }

    public static <CT extends Item & GeoAnimatable> GeoItemRenderer<CT> withScale(GeoItemRenderer<CT> self, float a0) {
        return self.withScale(a0);
    }

    public static <CT extends Item & GeoAnimatable> GeoItemRenderer<CT> withScale(GeoItemRenderer<CT> self, float a0, float a1) {
        return self.withScale(a0, a1);
    }

    public static <CT extends Item & GeoAnimatable> GeoModel<CT> getGeoModel(GeoItemRenderer<CT> self) {
        return self.getGeoModel();
    }

    public static <CT extends Item & GeoAnimatable> ItemStack getCurrentItemStack(GeoItemRenderer<CT> self) {
        return self.getCurrentItemStack();
    }

    public static <CT extends Item & GeoAnimatable> ResourceLocation getTextureLocation(GeoItemRenderer<CT> self, CT a0) {
        return self.getTextureLocation(a0);
    }

    public static <CT extends Item & GeoAnimatable> void actuallyRender(
        GeoItemRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        RenderType a3,
        MultiBufferSource a4,
        VertexConsumer a5,
        boolean a6,
        float a7,
        int a8,
        int a9,
        float a10,
        float a11,
        float a12,
        float a13
    ) {
        self.actuallyRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13);
    }

    public static <CT extends Item & GeoAnimatable> void preRender(
        GeoItemRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        MultiBufferSource a3,
        VertexConsumer a4,
        boolean a5,
        float a6,
        int a7,
        int a8,
        float a9,
        float a10,
        float a11,
        float a12
    ) {
        self.preRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
    }

    public static <CT extends Item & GeoAnimatable> void renderByItem(
        GeoItemRenderer<CT> self, ItemStack a0, ItemDisplayContext a1, PoseStack a2, MultiBufferSource a3, int a4, int a5
    ) {
        self.renderByItem(a0, a1, a2, a3, a4, a5);
    }

    public static <CT extends GeoAnimatable> ResourceLocation getAnimationResource(GeoModel<CT> self, CT a0) {
        return self.getAnimationResource(a0);
    }

    public static <CT extends GeoAnimatable> ResourceLocation getModelResource(GeoModel<CT> self, CT a0) {
        return self.getModelResource(a0);
    }

    public static <CT extends GeoAnimatable> ResourceLocation getTextureResource(GeoModel<CT> self, CT a0) {
        return self.getTextureResource(a0);
    }

    public static <CT extends GeoAnimatable> boolean crashIfBoneMissing(GeoModel<CT> self) {
        return self.crashIfBoneMissing();
    }

    public static <CT extends GeoAnimatable> BakedGeoModel getBakedModel(GeoModel<CT> self, ResourceLocation a0) {
        return self.getBakedModel(a0);
    }

    public static <CT extends GeoAnimatable> Animation getAnimation(GeoModel<CT> self, CT a0, String a1) {
        return self.getAnimation(a0, a1);
    }

    public static <CT extends GeoAnimatable> AnimationProcessor<CT> getAnimationProcessor(GeoModel<CT> self) {
        return self.getAnimationProcessor();
    }

    public static <CT extends GeoAnimatable> CoreGeoBone getBone(GeoModel<CT> self, String a0) {
        return self.getBone(a0);
    }

    public static <CT extends GeoAnimatable> void addAdditionalStateData(GeoModel<CT> self, CT a0, long a1, AnimationState<CT> a2) {
        self.addAdditionalStateData(a0, a1, a2);
    }

    public static <CT extends GeoAnimatable> void handleAnimations(GeoModel<CT> self, CT a0, long a1, AnimationState<CT> a2) {
        self.handleAnimations(a0, a1, a2);
    }

    public static <CT extends GeoAnimatable> void setCustomAnimations(GeoModel<CT> self, CT a0, long a1, AnimationState<CT> a2) {
        self.setCustomAnimations(a0, a1, a2);
    }

    public static <CT extends GeoAnimatable> GeoModel<CT> getGeoModel(GeoRenderLayer<CT> self) {
        return self.getGeoModel();
    }

    public static <CT extends GeoAnimatable> GeoRenderer<CT> getRenderer(GeoRenderLayer<CT> self) {
        return self.getRenderer();
    }

    public static <CT extends GeoAnimatable> void preRender(
        GeoRenderLayer<CT> self, PoseStack a0, CT a1, BakedGeoModel a2, RenderType a3, MultiBufferSource a4, VertexConsumer a5, float a6, int a7, int a8
    ) {
        self.preRender(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static <CT extends GeoAnimatable> void render(
        GeoRenderLayer<CT> self, PoseStack a0, CT a1, BakedGeoModel a2, RenderType a3, MultiBufferSource a4, VertexConsumer a5, float a6, int a7, int a8
    ) {
        self.render(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static <CT extends GeoAnimatable> void renderForBone(
        GeoRenderLayer<CT> self, PoseStack a0, CT a1, GeoBone a2, RenderType a3, MultiBufferSource a4, VertexConsumer a5, float a6, int a7, int a8
    ) {
        self.renderForBone(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static <CT extends GeoAnimatable> CT getAnimatable(GeoRenderer<CT> self) {
        return self.getAnimatable();
    }

    public static <CT extends GeoAnimatable> List<GeoRenderLayer<CT>> getRenderLayers(GeoRenderer<CT> self) {
        return self.getRenderLayers();
    }

    public static <CT extends GeoAnimatable> GeoModel<CT> getGeoModel(GeoRenderer<CT> self) {
        return self.getGeoModel();
    }

    public static <CT extends GeoAnimatable> long getInstanceId(GeoRenderer<CT> self, CT a0) {
        return self.getInstanceId(a0);
    }

    public static <CT extends GeoAnimatable> RenderType getRenderType(GeoRenderer<CT> self, CT a0, ResourceLocation a1, MultiBufferSource a2, float a3) {
        return self.getRenderType(a0, a1, a2, a3);
    }

    public static <CT extends GeoAnimatable> ResourceLocation getTextureLocation(GeoRenderer<CT> self, CT a0) {
        return self.getTextureLocation(a0);
    }

    public static <CT extends GeoAnimatable> void actuallyRender(
        GeoRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        RenderType a3,
        MultiBufferSource a4,
        VertexConsumer a5,
        boolean a6,
        float a7,
        int a8,
        int a9,
        float a10,
        float a11,
        float a12,
        float a13
    ) {
        self.actuallyRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13);
    }

    public static <CT extends GeoAnimatable> void applyRenderLayers(
        GeoRenderer<CT> self, PoseStack a0, CT a1, BakedGeoModel a2, RenderType a3, MultiBufferSource a4, VertexConsumer a5, float a6, int a7, int a8
    ) {
        self.applyRenderLayers(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static <CT extends GeoAnimatable> void applyRenderLayersForBone(
        GeoRenderer<CT> self, PoseStack a0, CT a1, GeoBone a2, RenderType a3, MultiBufferSource a4, VertexConsumer a5, float a6, int a7, int a8
    ) {
        self.applyRenderLayersForBone(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static <CT extends GeoAnimatable> void defaultRender(
        GeoRenderer<CT> self,
        PoseStack a0,
        CT a1,
        MultiBufferSource a2,
        RenderType a3,
        VertexConsumer a4,
        float a5,
        float a6,
        int a7,
        int a8,
        float a9,
        float a10,
        float a11,
        float a12
    ) {
        self.defaultRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
    }

    public static <CT extends GeoAnimatable> void postRender(
        GeoRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        MultiBufferSource a3,
        VertexConsumer a4,
        boolean a5,
        float a6,
        int a7,
        int a8,
        float a9,
        float a10,
        float a11,
        float a12
    ) {
        self.postRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
    }

    public static <CT extends GeoAnimatable> void preRender(
        GeoRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        MultiBufferSource a3,
        VertexConsumer a4,
        boolean a5,
        float a6,
        int a7,
        int a8,
        float a9,
        float a10,
        float a11,
        float a12
    ) {
        self.preRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
    }

    public static <CT extends GeoAnimatable> void reRender(
        GeoRenderer<CT> self,
        BakedGeoModel a0,
        PoseStack a1,
        MultiBufferSource a2,
        CT a3,
        RenderType a4,
        VertexConsumer a5,
        float a6,
        int a7,
        int a8,
        float a9,
        float a10,
        float a11,
        float a12
    ) {
        self.reRender(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
    }

    public static <CT extends GeoAnimatable> void renderChildBones(
        GeoRenderer<CT> self,
        PoseStack a0,
        CT a1,
        GeoBone a2,
        RenderType a3,
        MultiBufferSource a4,
        VertexConsumer a5,
        boolean a6,
        float a7,
        int a8,
        int a9,
        float a10,
        float a11,
        float a12,
        float a13
    ) {
        self.renderChildBones(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13);
    }

    public static <CT extends GeoAnimatable> void renderCube(
        GeoRenderer<CT> self, PoseStack a0, GeoCube a1, VertexConsumer a2, int a3, int a4, float a5, float a6, float a7, float a8
    ) {
        self.renderCube(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static <CT extends GeoAnimatable> void renderCubesOfBone(
        GeoRenderer<CT> self, PoseStack a0, GeoBone a1, VertexConsumer a2, int a3, int a4, float a5, float a6, float a7, float a8
    ) {
        self.renderCubesOfBone(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    }

    public static <CT extends GeoAnimatable> void renderFinal(
        GeoRenderer<CT> self,
        PoseStack a0,
        CT a1,
        BakedGeoModel a2,
        MultiBufferSource a3,
        VertexConsumer a4,
        float a5,
        int a6,
        int a7,
        float a8,
        float a9,
        float a10,
        float a11
    ) {
        self.renderFinal(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11);
    }

    public static <CT extends GeoAnimatable> void renderRecursively(
        GeoRenderer<CT> self,
        PoseStack a0,
        CT a1,
        GeoBone a2,
        RenderType a3,
        MultiBufferSource a4,
        VertexConsumer a5,
        boolean a6,
        float a7,
        int a8,
        int a9,
        float a10,
        float a11,
        float a12,
        float a13
    ) {
        self.renderRecursively(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13);
    }

    public static double length(Keyframe self) {
        return self.length();
    }

    public static EasingType easingType(Keyframe self) {
        return self.easingType();
    }

    public static double getLastKeyframeTime(KeyframeStack self) {
        return self.getLastKeyframeTime();
    }

    public static String animationName(RawAnimation.Stage self) {
        return self.animationName();
    }

    public static List<RawAnimation.Stage> getAnimationStages(RawAnimation self) {
        return self.getAnimationStages();
    }

    public static RawAnimation then(RawAnimation self, String a0, Animation.LoopType a1) {
        return self.then(a0, a1);
    }

    public static RawAnimation thenLoop(RawAnimation self, String a0) {
        return self.thenLoop(a0);
    }

    public static RawAnimation thenPlay(RawAnimation self, String a0) {
        return self.thenPlay(a0);
    }

    public static RawAnimation thenPlayAndHold(RawAnimation self, String a0) {
        return self.thenPlayAndHold(a0);
    }

    public static RawAnimation thenPlayXTimes(RawAnimation self, String a0, int a1) {
        return self.thenPlayXTimes(a0, a1);
    }

    public static RawAnimation thenWait(RawAnimation self, int a0) {
        return self.thenWait(a0);
    }
}
