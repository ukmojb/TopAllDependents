package com.wdcftgg.topalldependents;

import com.wdcftgg.topalldependents.config.Config;
import com.wdcftgg.topalldependents.mods.topaddons.TOPRegistrar;
import com.wdcftgg.topalldependents.mods.topaddons.config.ConfigClient;
import com.wdcftgg.topalldependents.mods.topaddons.config.HelmetConfig;
import com.wdcftgg.topalldependents.mods.topaddons.config.capabilities.CapEvents;
import com.wdcftgg.topalldependents.mods.topaddons.config.capabilities.ClientOptsCapability;
import com.wdcftgg.topalldependents.mods.topaddons.config.capabilities.IClientOptsCapability;
import com.wdcftgg.topalldependents.mods.topaddons.helmets.CommandTOPHelmet;
import com.wdcftgg.topalldependents.mods.topaddons.network.PacketHandler;
import com.wdcftgg.topalldependents.mods.topaddons.reference.Reference;
import com.wdcftgg.topalldependents.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = TopAllDependents.MODID, name = TopAllDependents.NAME, version = TopAllDependents.VERSION,
        dependencies = "required-after:theoneprobe@[1.4.29,);" +
        "after:forestry@[1.12.2-5.7.0.204,);" +
        "after:tconstruct;" +
        "after:bloodmagic;" +
        "after:storagedrawers;" +
        "after:botania;" +
        "after:ic2;" +
        "after:neotech;" +
        "after:lycanytesmobs;" +
        "after:opencomputers;" +
        "after:stevescarts;" +
        "after:dynamictrees;" +
        "after:embers;" +
        "after:botaniverse;" +
        "after:botanicadds;" +
        "after:extrabotany;" +
        "after:projecte;" +
        "after:thaumcraft;" +
        "after:mysticalagriculture")
public class TopAllDependents {
    public static final String MODID = "topalldependents";
    public static final String NAME = "TopAllDependents";
    public static final String VERSION = "1.4";
    public static Logger logger;
    public static File modConfigDir;

    @Mod.Instance
    public static TopAllDependents instance;

    public static final String CLIENT_PROXY_CLASS = "com.wdcftgg.topalldependents.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.wdcftgg.topalldependents.proxy.ServerProxy";

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @CapabilityInject(IClientOptsCapability.class)
    public static final Capability<IClientOptsCapability> OPTS_CAP = null;


    public static Configuration config;
    public static Configuration configClient = null;

    public static boolean ic2Loaded = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.onPreInit();

        modConfigDir = event.getModConfigurationDirectory();

        config = new Configuration(new File(modConfigDir.getPath(),  TopAllDependents.MODID + ".cfg"));
        Config.init(config);

        config.load();
        cleanConfig();

        if (event.getSide() == Side.CLIENT) {
            configClient = new Configuration(new File(event.getModConfigurationDirectory().getPath(), TopAllDependents.MODID + "_client.cfg"), "1");
            //noinspection MethodCallSideOnly
            ConfigClient.init(configClient);
            MinecraftForge.EVENT_BUS.register(ConfigClient.class);
            MinecraftForge.EVENT_BUS.register(this);
        }

        CapabilityManager.INSTANCE.register(IClientOptsCapability.class, new ClientOptsCapability.Storage(), ClientOptsCapability.class);
        MinecraftForge.EVENT_BUS.register(new CapEvents());

        PacketHandler.init();

        AddonManager.preInit(event);
        if (AddonManager.ADDONS.size() > 0) {
            TOPRegistrar.register();
            AddonManager.ADDONS.forEach(a -> a.updateConfigs(config));
            if (config.hasChanged()) {
                config.save();
            }
        }

        ic2Loaded = Loader.isModLoaded("ic2");

    }


    @Mod.EventHandler
    public static void Init(FMLInitializationEvent event) {
        proxy.onInit();

        TOPHandler.registerTips();

        HelmetConfig.loadHelmetBlacklist(config);

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.onPostInit();
    }


    @Mod.EventHandler
    public static void serverInit(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandTOPHelmet());
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            AddonManager.ADDONS.forEach(a -> a.updateConfigs(config));
            HelmetConfig.loadHelmetBlacklist(config);
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    private void cleanConfig() {
        ConfigCategory oldBm = config.getCategory("blood magic");
        if (oldBm != null) {
            config.removeCategory(oldBm);
        }

        ConfigCategory oldForge = config.getCategory("forge");
        if (oldForge != null) {
            config.removeCategory(oldForge);
        }

        ConfigCategory oldVanilla = config.getCategory("vanilla");
        if (oldVanilla != null) {
            config.removeCategory(oldVanilla);
        }
    }

    public static void LogWarning(String str, Object... args) {
        logger.warn(String.format(str, args));
    }

    public static void LogWarning(String str) {
        logger.warn(str);
    }

    public static void Log(String str) {
        logger.info(str);
    }

    public static void Log(String str, Object... args) {
        logger.info(String.format(str, args));
    }
}
