package com.wdcftgg.topalldependents.config;


import com.wdcftgg.topalldependents.TopAllDependents;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.IOverlayStyle;
import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.apiimpl.ProbeConfig;
import mcjty.theoneprobe.apiimpl.styles.DefaultOverlayStyle;
import mcjty.theoneprobe.setup.ModSetup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.wdcftgg.topalldependents.TopAllDependents.modConfigDir;
import static mcjty.theoneprobe.api.TextStyleClass.*;

public class Config {

    public static Configuration mainConfig;

    public static String CATEGORY_BOTANIA = "Botania";

    public static String CATEGORY_THAUMCRAFT = "Thaumcraft";

    public static boolean Botaniatop = true;

    public static boolean showBotaniaprogress = true;

    public static boolean textinprogress = true;

    public static boolean requireGoggles = false;

    private static void cfgInit(Configuration cfg) {

        Botaniatop = cfg.getBoolean("Botaniatop", CATEGORY_BOTANIA, Botaniatop, "Whether to enable Botania's top display");
        showBotaniaprogress = cfg.getBoolean("showBotaniaprogress", CATEGORY_BOTANIA, showBotaniaprogress, "Whether to show Botania's mana");
        textinprogress = cfg.getBoolean("textinprogress", CATEGORY_BOTANIA, textinprogress, "Whether the mana value should be displayed in or below the mana bar");

        requireGoggles = cfg.getBoolean("requireGoggles", CATEGORY_THAUMCRAFT, requireGoggles, "Should the goggles of revealing be required to see Thaumcraft information?");

    }

    public static void init(Configuration config) {

        try {
            config.load();
            config.addCustomCategoryComment(CATEGORY_BOTANIA, "botania settings");
            config.addCustomCategoryComment(CATEGORY_THAUMCRAFT, "thaumcraft settings");
            cfgInit(config);
        } catch (Exception e1) {
            TheOneProbe.setup.getLogger().log(Level.ERROR, "Problem loading config file!", e1);
        }
    }

    public Config() {
    }

//    public static void init(File configurationFile) {
//        Configuration config = new Configuration(configurationFile);
//
//
//        try {
//            config.load();
//            dimInit(config);
//            bossInit(config);
//            timeInit(config);
//            potionInit(config);
//        } catch (Exception var11) {
//        } finally {
//            config.save();
//        }
//
//    }
}


