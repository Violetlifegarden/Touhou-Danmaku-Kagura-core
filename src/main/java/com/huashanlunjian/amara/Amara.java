package com.huashanlunjian.amara;

import com.huashanlunjian.amara.client.model.EntityFairyModel;
import com.huashanlunjian.amara.client.model.NewEntityFairyModel;
import com.huashanlunjian.amara.client.renderer.entity.EasternProjectBlockRenderer;
import com.huashanlunjian.amara.client.renderer.entity.SongsEntityRenderer;
import com.huashanlunjian.amara.client.renderer.entity.TapRenderer;
import com.huashanlunjian.amara.entity.Tap;
import com.huashanlunjian.amara.entity.songs.Boss;
import com.huashanlunjian.amara.init.InitEntities;
import com.huashanlunjian.amara.item.CreativeModTab;
import com.huashanlunjian.amara.item.ModItems;
import com.huashanlunjian.amara.network.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;

import java.util.Locale;

@Mod(Amara.MOD_ID)
public class Amara {
    public static final String MOD_ID = "amara";
    public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(MOD_ID);
    private static final String MODEL_DIR = "textures/entity/";
    private static final String GUI_DIR = "textures/gui/";

    public Amara(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);


        ModItems.register(modEventBus);
        CreativeModTab.register(modEventBus);
        initRegister(modEventBus);
        modEventBus.addListener(NetworkHandler::registerPacket);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }




    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
    private static void initRegister(IEventBus eventBus) {
        InitEntities.ENTITIES.register(eventBus);
        InitEntities.ATTRIBUTES.register(eventBus);
        InitEntities.MEMORY_MODULE_TYPES.register(eventBus);
        InitEntities.SENSOR_TYPES.register(eventBus);
        InitEntities.SCHEDULES.register(eventBus);
        InitEntities.DATA_SERIALIZERS.register(eventBus);
        InitEntities.ACTIVITIES.register(eventBus);
    }
    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation getModelTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, MODEL_DIR + name);
    }

    public static ResourceLocation getGuiTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, GUI_DIR + name);
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(InitEntities.EASTERNPROJECTBLOCK.get(), EasternProjectBlockRenderer::new);
            event.registerEntityRenderer(InitEntities.TAP.get(), TapRenderer::new);
            event.registerEntityRenderer(InitEntities.DEMOSONG.get(), SongsEntityRenderer::new);

        }
        @SubscribeEvent
        public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(EntityFairyModel.LAYER, EntityFairyModel::createBodyLayer);
            event.registerLayerDefinition(NewEntityFairyModel.LAYER, NewEntityFairyModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void addEntityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(InitEntities.DEMOSONG.get(), Boss.createMobAttributes().build());
        }
    }
}
