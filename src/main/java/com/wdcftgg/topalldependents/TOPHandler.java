package com.wdcftgg.topalldependents;

import com.wdcftgg.topalldependents.config.Config;
import com.wdcftgg.topalldependents.mods.BotanicAdditions.DreamingManaPool;
import com.wdcftgg.topalldependents.mods.BotanicAdditions.ElvenAltar;
import com.wdcftgg.topalldependents.mods.ExtraBotany.ManaBuffer;
import com.wdcftgg.topalldependents.mods.ExtraBotany.QuantumManaBuffer;
import com.wdcftgg.topalldependents.mods.botania.*;
import com.wdcftgg.topalldependents.mods.botaniverse.MoreManaPool;
import com.wdcftgg.topalldependents.mods.botaniverse.MoreSpark;
import com.wdcftgg.topalldependents.mods.dynamictrees.DynamicTreesInfoProvider;
import com.wdcftgg.topalldependents.mods.ember_top.*;
import com.wdcftgg.topalldependents.mods.projecte.ProjectEInfoProvider;
import com.wdcftgg.topalldependents.mods.thaumcraft.AspectElement;
import com.wdcftgg.topalldependents.mods.thaumcraft.Smelter;
import com.wdcftgg.topalldependents.mods.thaumcraft.ThaumHighlightInfoProvider;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.apiimpl.TheOneProbeImp;
import net.minecraftforge.fml.common.Loader;

public class TOPHandler {
    public static void registerTips() {
        TheOneProbeImp theOneProbeImp = TheOneProbe.theOneProbeImp;
        if (Config.Botaniatop) {
            if (Loader.isModLoaded("botania")) {
                theOneProbeImp.registerProvider(new ManaPool());
                theOneProbeImp.registerProvider(new Spreader());
                theOneProbeImp.registerProvider(new TerraPlate());
                theOneProbeImp.registerProvider(new RuneAltar());
                theOneProbeImp.registerEntityProvider(new Spark());
            }
            if (Loader.isModLoaded("extrabotany")) {
                theOneProbeImp.registerProvider(new ManaBuffer());
                theOneProbeImp.registerProvider(new QuantumManaBuffer());
            }
            if (Loader.isModLoaded("botaniverse")) {
                theOneProbeImp.registerProvider(new MoreManaPool());
                theOneProbeImp.registerEntityProvider(new MoreSpark());
            }
            if (Loader.isModLoaded("botanicadds")) {
                theOneProbeImp.registerProvider(new DreamingManaPool());
                theOneProbeImp.registerProvider(new ElvenAltar());
            }
        }
        if (Loader.isModLoaded("embers")) {
            theOneProbeImp.registerProvider(new ember_coppercell());
            theOneProbeImp.registerProvider(new ember_auto_hummer());
            theOneProbeImp.registerProvider(new ember_beam_cannon());
            theOneProbeImp.registerProvider(new ember_cinder_plinth());
            theOneProbeImp.registerProvider(new ember_furnace());
            theOneProbeImp.registerProvider(new ember_mixer());
            theOneProbeImp.registerProvider(new ember_stamper());
            theOneProbeImp.registerProvider(new ember_crystal_cell());
            theOneProbeImp.registerProvider(new ember_emitter());
            theOneProbeImp.registerProvider(new ember_receiver());
            theOneProbeImp.registerProvider(new ember_activator());
            theOneProbeImp.registerProvider(new ember_BeamSplitter());
            theOneProbeImp.registerProvider(new ember_alchemy_pedestal());
            theOneProbeImp.registerProvider(new ember_emberinjector());
            theOneProbeImp.registerProvider(new ember_reactor());
            theOneProbeImp.registerProvider(new ember_pulser());
            theOneProbeImp.registerProvider(new ember_funnel());
            theOneProbeImp.registerProvider(new ember_boiler());
        }
        if (Loader.isModLoaded("projecte")) {
            theOneProbeImp.registerProvider(new ProjectEInfoProvider());
        }
        if (Loader.isModLoaded("dynamictrees")) {
            theOneProbeImp.registerProvider(new DynamicTreesInfoProvider());
        }
        if (Loader.isModLoaded("thaumcraft")) {
            TheOneProbe.ELEM_ID_ASPECT = TheOneProbe.theOneProbeImp.registerElementFactory(new AspectElement.Factory());
            theOneProbeImp.registerProvider(new ThaumHighlightInfoProvider());
            theOneProbeImp.registerProvider(new Smelter());
        }
    }
}

